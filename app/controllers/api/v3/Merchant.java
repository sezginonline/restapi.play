package controllers.api.v3;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import libs.auth.ActionAuthenticator;
import libs.db.MorphiaObject;
import libs.helper.Helper;
import libs.json.ErrorHandler;
import models.MongoMerchant;
import models.MongoUser;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.query.Query;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * Merchant Controller
 */
public class Merchant extends Controller {

    /**
     * User Login Endpoint
     * Token is valid for 10 minutes
     * @return Json Result
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result userLogin() {

        JsonNode json = request().body().asJson();
        String email = json.findPath("email").textValue();
        String password = json.findPath("password").textValue();

        // Get user
        Query q = MorphiaObject.datastore.createQuery(MongoUser.class);
        q.filter("email", email);
        MongoUser user = (MongoUser) q.get();
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            ErrorHandler e = new ErrorHandler();
            e.setError(401, "Unauthorized");
            return status(401, e.pretty());
        }

        String jwt = createJWT(user.getIdString(), 1000 * 60 * 10);

        ObjectNode result = Json.newObject();
        result.set("token", Json.toJson(jwt));
        result.set("status", Json.toJson("APPROVED"));
        return ok(Json.prettyPrint(result)).as("application/json; charset=utf-8");
    }

    /**
     * Authenticated Merchant Get Endpoint
     * @return Json Result
     */
    @Security.Authenticated(ActionAuthenticator.class)
    public Result get() {

        Integer id = null;
        try {
            id = Integer.parseInt(request().getQueryString("id"));
        } catch (Exception ex) {
            ErrorHandler e = new ErrorHandler();
            e.setError(403, "Bad request");
            return status(403, e.pretty());
        }

        // Get merchant
        Query q = MorphiaObject.datastore.createQuery(MongoMerchant.class);
        q.filter("id", id);
        MongoMerchant merchant = (MongoMerchant) q.get();
        if (merchant == null) {
            ErrorHandler e = new ErrorHandler();
            e.setError(404, "Not found");
            return status(404, e.pretty());
        }

        ObjectNode result = Json.newObject();
        result.set("status", Json.toJson("APPROVED"));
        result.set("message", Json.toJson("Approved"));
        result.set("merchant", Json.toJson(merchant));
        return ok(Json.prettyPrint(result)).as("application/json; charset=utf-8");
    }

    /**
     * Authenticated Merchant Endpoint
     * @return Json Result
     */
    @Security.Authenticated(ActionAuthenticator.class)
    public Result index() {

        String transactionId = request().getQueryString("transactionId");
        if (transactionId == null || transactionId.isEmpty()) {
            ErrorHandler e = new ErrorHandler();
            e.setError(403, "Transaction id required");
            return status(403, e.pretty());
        }

        // Get merchant
        Query q = MorphiaObject.datastore.createQuery(MongoMerchant.class);
        q.filter("transactionId", transactionId);
        MongoMerchant merchant = (MongoMerchant) q.get();
        if (merchant == null) {
            ErrorHandler e = new ErrorHandler();
            e.setError(404, "Not found");
            return status(404, e.pretty());
        }

        ObjectNode result = Json.newObject();
        result.set("merchant", Json.toJson(merchant));
        return ok(Json.prettyPrint(result)).as("application/json; charset=utf-8");
    }

    private String createJWT(String subject, long ttlMillis) {

        // The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Helper.getConf("jwt.secret"));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(Helper.getConf("api.url"))
                // .claim("context", context)
                .signWith(signatureAlgorithm, signingKey);

        // If it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

}
