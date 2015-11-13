package libs.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import libs.helper.Helper;
import libs.json.ErrorHandler;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.xml.bind.DatatypeConverter;

public class ActionAuthenticator extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        String authorization = getValueFromHeader(ctx, "Authorization");
        if (authorization != null && authorization.substring(0, 6).equals("Bearer")) {
            String jwt = authorization.substring(7, authorization.length());
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(Helper.getConf("jwt.secret")))
                        .parseClaimsJws(jwt).getBody();
                String userId = claims.getSubject();
                return userId; // Reachable from: request().username()
            } catch (Exception e) {
                //
            }
        }
        return null; // Don't trust the JWT!
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        ErrorHandler e = new ErrorHandler();
        e.setError(401, "Unauthorized");
        return unauthorized(e.pretty()).as("application/json; charset=utf-8");
    }

    private String getValueFromHeader(Http.Context ctx, String key) {
        String[] headers = ctx.request().headers().get(key);
        if (headers != null && headers.length == 1) {
            return headers[0];
        }
        return null;
    }

}