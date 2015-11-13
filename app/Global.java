import libs.db.MongoDB;
import libs.json.ErrorHandler;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Request;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;

import java.lang.reflect.Method;

import static play.mvc.Results.notFound;

public class Global extends GlobalSettings {

    private final Logger.ALogger accessLogger = Logger.of("access");

    @Override
    @SuppressWarnings("rawtypes")
    public Action onRequest(Request request, Method method) {
        accessLogger.info("method=" + request.method() + " uri=" + request.uri() + " remote-address=" + request.remoteAddress());

        return super.onRequest(request, method);
    }

    @Override
    public void onStart(Application app) {
        Logger.info("Application started!");
        MongoDB.connect();
        Logger.info("Connected to MongoDB!");
    }

    @Override
    public void onStop(Application app) {
        Logger.info("Application stopped!");
        MongoDB.disconnect();
        Logger.info("Disconnected from MongoDB!");
    }

    @Override
    public Promise<Result> onBadRequest(RequestHeader request, String error) {
        ErrorHandler e = new ErrorHandler();
        e.setError(400, "Bad request");
        return Promise.<Result>pure(Results.badRequest(e.pretty()).as("application/json; charset=utf-8"));
    }

    @Override
    public Promise<Result> onHandlerNotFound(RequestHeader request) {
        ErrorHandler e = new ErrorHandler();
        e.setError(404, "Unsupported request");
        return Promise.<Result>pure(notFound(e.pretty()).as("application/json; charset=utf-8"));
    }

}
