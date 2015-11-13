package libs.json;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class ErrorHandler {

    private ArrayNode errors = Json.newArray();

    public void setError(Integer code, String message) {
        ObjectNode error = Json.newObject();
        error.put("code", code);
        error.put("message", message);
        errors.add(error);
    }

    public ObjectNode getError() {
        ObjectNode object = Json.newObject();
        object.set("errors", errors);
        return object;
    }

    public String pretty() {
        return Json.prettyPrint(getError());
    }

}
