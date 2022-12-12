package starter.template.resources;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mockit.Mock;
import mockit.MockUp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import starter.template.ApplicationHandler;
import starter.template.TestBase;
import starter.template.models.VersionResponse;
import starter.template.services.VersionService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Version Resource Tests")
class VersionResourceTest extends TestBase {
    private static final String TARGET_URL = "/1.0/version";
    private final HashMap<String, String> QUERY_STRINGS = new HashMap<>();

    @BeforeAll
    static void setUp() {
        handler = new ApplicationHandler();
        lambdaContext = new MockLambdaContext();
    }

    @Test
    @DisplayName("test Version Resource")
    void testVersionResource() {
        AwsProxyResponse response = invokeRequest(TARGET_URL, null, QUERY_STRINGS);

        assertEquals(200, response.getStatusCode());
        assertEquals("application/json", response.getMultiValueHeaders().getFirst("Content-Type"));
    }

    @Test
    @DisplayName("test Correct Data Is Returned By Version")
    void testCorrectDataIsReturnedByVersion() throws IOException {
        Properties properties = new Properties();
        java.net.URL url = VersionService.class.getResource("/maven.properties");
        String version = null;
        String buildDateTime = null;

        if (url != null) {
            properties.load(url.openStream());
            version = properties.getProperty("version");
            buildDateTime = properties.getProperty("buildDateTime");
        }

        AwsProxyResponse response = invokeRequest(TARGET_URL, null, QUERY_STRINGS);
        String output = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(output, JsonNode.class);

        assertEquals("Starter Template API", jsonNode.get("name").asText());
        assertEquals(buildDateTime, jsonNode.get("buildDateTime").asText());
        assertEquals(version, jsonNode.get("version").asText());
    }

    @Test
    @DisplayName("test Exception Thrown In Version Service Returns 500 Response")
    void testExceptionThrownInVersionServiceReturns500Response() {
        new MockUp<VersionService>() {
            @Mock
            public VersionResponse getVersion() throws Exception {
                throw new Exception("Forced exception");
            }
        };

        AwsProxyResponse response = invokeRequest(TARGET_URL, null, QUERY_STRINGS);
        assertEquals(500, response.getStatusCode());
    }
}
