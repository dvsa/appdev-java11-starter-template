package starter.template.resources;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import mockit.Mock;
import mockit.MockUp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import starter.template.ApplicationHandler;
import starter.template.TestBase;
import starter.template.services.HelloWorldService;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Hello World Resource Tests")
public class HelloWorldResourceTest extends TestBase {
    private static final String TARGET_URL = "/1.0/helloworld";
    private final HashMap<String, String> QUERY_STRINGS = new HashMap<>();

    @BeforeAll
    static void setUp() {
        handler = new ApplicationHandler();
        lambdaContext = new MockLambdaContext();
    }

    @Test
    @DisplayName("test Hello World Resource")
    void testHelloWorldResource() {
        AwsProxyResponse response = invokeRequest(TARGET_URL, null, QUERY_STRINGS);

        assertEquals(200, response.getStatusCode());
        assertEquals("application/json", response.getMultiValueHeaders().getFirst("Content-Type"));

        assertEquals("Hello World !!", response.getBody());

        System.out.println("dsds");
    }

    @Test
    @DisplayName("test Exception Thrown In Hello World Service Returns 500 Response")
    void testExceptionThrownInHelloWorldServiceReturns500Response() {
        new MockUp<HelloWorldService>() {
            @Mock
            public String callHelloWorld() throws Exception {
                throw new Exception("Forced exception");
            }
        };

        AwsProxyResponse response = invokeRequest(TARGET_URL, null, QUERY_STRINGS);
        assertEquals(500, response.getStatusCode());
    }
}
