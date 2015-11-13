
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.*;
import org.junit.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;
import static play.test.Helpers.*;
import static org.junit.Assert.*;

/**
 * Application Tests
 */
public class ApplicationTest extends WithApplication {

    private static String token;

    @Override
    protected FakeApplication provideFakeApplication() {
        return fakeApplication();
    }

    private Result login() {
        ObjectNode node = Json.newObject();
        node.put("email", "merchant@test.com");
        node.put("password", "123*-+");
        return route(new RequestBuilder().method(POST).uri("/api/v3/merchant/user/login").bodyJson(node));
    }

    /**
     * merchant/user/login test
     */
    @Test
    public void test1() {
        Result result = login();
        String content = contentAsString(result);
        JsonNode jsonContent = Json.parse(content);
        ApplicationTest.token = jsonContent.findPath("token").textValue();
        assertEquals(OK, result.status());
        assertTrue(content.contains("APPROVED"));
    }

    /**
     * merchant/get?id test
     */
    @Test
    public void test2() {
        Result result = route(new RequestBuilder().method(GET).uri("/api/v3/merchant/get?id=3")
                .header("Authorization", "Bearer " + ApplicationTest.token));
        assertEquals(OK, result.status());
    }

    /**
     * merchant?transactionId test
     */
    @Test
    public void test3() {
        Result result = route(new RequestBuilder().method(GET).uri("/api/v3/merchant?transactionId=1-1444392550-1")
                .header("Authorization", "Bearer " + ApplicationTest.token));
        assertEquals(OK, result.status());
    }

}