package starter.template;

import com.amazonaws.serverless.proxy.internal.LambdaContainerHandler;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Test Base")
public abstract class TestBase {
    protected static ApplicationHandler handler;
    protected static Context lambdaContext;
    protected static String token;
    protected static Map<String, Object> configMap;
    protected static Map<String, Object> configMapImagePhotograph;
    protected static Map<String, Object> configMapImageSignature;
    protected static Map<String, Object> configMapFindDriver;
    protected static Map<String, Object> configMapEnhancedDriver;
    protected static Map<String, Object> configMapStandardDriver;
    protected static Map<String, Object> configTachographCard;
    protected static Map<String, Object> configTachographHolder;
    protected static Map<String, Object> configMapCpcDriver;
    protected static Map<String, Object> configMapDqcDriver;

    protected static final Map<String, String> fixedHeaders = buildHeaders();
    protected static final String HTTP_METHOD = "httpMethod";
    protected static final String URL = "url";
    protected static final String FIND_DRIVER_URL = "1.0/driver/find";
    protected static final String PHOTOGRAPH_URL = "1.0/image/photograph/";
    protected static final String SIGNATURE_URL = "1.0/image/signature/";
    protected static final String ENHANCED_DRIVER_URL = "1.0/driver/enhanced/";
    protected static final String STANDARD_DRIVER_URL = "1.0/driver/standard/";
    protected static final String TACHOGRAPH_CARD_URL = "1.0/tachograph/cards";
    protected static final String TACHOGRAPH_HOLDERS_URL = "1.0/tachograph/holders";
    protected static final String CPC_DRIVER_URL = "1.0/driver/cpc";
    protected static final String DQC_DRIVER_URL = "1.0/driver/dqc";
    private static final String HEADER_PARAM = "Authorization";

    /**
     * This method will load a Json object into the corresponding POJO and return it.  It is used to MOCK the provider responses
     *
     * @param fileName   Filename of the Json to Load
     * @param className  Name of the class to load the Json object into
     * @param dateFormat Format of the dates in the above Json object so they can be converted to LocalDate
     * @param <T>        Returning Object Class
     * @return class name Object
     */
//    @SuppressWarnings("unchecked")
//    public <T> T getObjectFromJsonFile(String fileName, Class<?> className, String dateFormat) {
//
//        try {
//            DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
//            InputStream stream = TestBase.class.getResourceAsStream("/".concat(fileName));
//
//            ObjectMapper mapper = new ObjectMapper()
//                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                    .registerModule(new SimpleModule().addDeserializer(LocalDate.class, new JsonDeserializer<>() {
//                                        @Override
//                                        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//                                            return LocalDate.parse(p.getText(), formatter);
//                                        }
//                                    })
//                                    .addDeserializer(LocalDateTime.class, new JsonDeserializer<>() {
//                                        @Override
//                                        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//                                            return LocalDateTime.parse(p.getText(), localDateTimeFormatter);
//                                        }
//                                    })
//                    );
//
//            assert stream != null;
//            Object objectResponse = mapper.readValue(IOUtils.toString(stream), className);
//            stream.close();
//
//            return (T) objectResponse;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public AwsProxyResponse invokeRequest(Map<String, Object> configMap, Object payload) {
        return this.invokeRequest(configMap, null, payload);
    }

    public AwsProxyResponse invokeRequest(String url, String token, HashMap<String, String> query) {
        InputStream requestStream;
        AwsProxyRequestBuilder awsProxyRequestBuilder = new AwsProxyRequestBuilder(url, HttpMethod.GET);

        // Add token if needed
        if (token != null) {
            awsProxyRequestBuilder.header(HEADER_PARAM, token);
        }

        // Add SearchQuery Params passed in through HashMap to header
        if (query != null) {
            for (Map.Entry<String, String> entry : query.entrySet()) {
                awsProxyRequestBuilder.queryString(entry.getKey(), entry.getValue());
            }
        }

        awsProxyRequestBuilder.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

        requestStream = awsProxyRequestBuilder.buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        handle(requestStream, responseStream);
        return readResponse(responseStream);
    }

    public AwsProxyResponse invokeRequest(Map<String, Object> configMap, HashMap<String, String> query) {
        return this.invokeRequest(configMap, query, null);
    }

    public AwsProxyResponse invokeRequest(Map<String, Object> configMap, HashMap<String, String> query, Object payload) {
        InputStream requestStream;
        AwsProxyRequestBuilder awsProxyRequestBuilder = new AwsProxyRequestBuilder(configMap.get("url").toString(),
                configMap.get("httpMethod").toString()).json();

        // Add token if needed
        if (configMap.get("token") != null) {
            awsProxyRequestBuilder.header(HEADER_PARAM, configMap.get("token").toString());
        }

        // Add Query Params passed in through HashMap to header
        if (query != null) {
            for (Map.Entry<String, String> entry : query.entrySet()) {
                awsProxyRequestBuilder.queryString(entry.getKey(), entry.getValue());
            }
        }

        if (payload != null) {
            awsProxyRequestBuilder.body(payload);
        }

        if (configMap.get("fixedHeaders") instanceof Map && !configMap.isEmpty()) {
            Map<String, String> fixedHeaders = (Map<String, String>) configMap.get("fixedHeaders");
            fixedHeaders.forEach(awsProxyRequestBuilder::header);
        }

        awsProxyRequestBuilder.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        awsProxyRequestBuilder.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

        requestStream = awsProxyRequestBuilder.buildStream();
        try (ByteArrayOutputStream responseStream = new ByteArrayOutputStream()) {
            handle(requestStream, responseStream);

            return readResponse(responseStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void handle(InputStream is, ByteArrayOutputStream os) {
        try {
            handler.handleRequest(is, os, lambdaContext);
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    protected AwsProxyResponse readResponse(ByteArrayOutputStream responseStream) {
        try {
            return LambdaContainerHandler.getObjectMapper().readValue(responseStream.toByteArray(),
                    AwsProxyResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Error while parsing response: " + e.getMessage());
        }
        return null;
    }

    //<----------------------- Non-test methods ---------------------------->

    /**
     * This method puts the required headers into the fixedHeaders Map object.
     *
     * @return Map
     */
    public static Map<String, String> buildHeaders() {
        Map<String, String> fixedHeaders = new HashMap<>();
        fixedHeaders.put("X-API-key", "123456");
        fixedHeaders.put("Authorization", "TESTING");

        return fixedHeaders;
    }

    /**
     * This method creates the config map when provided with the url and method.
     *
     * @param url    String
     * @param method String
     * @return Hashmap
     */
    protected Map<String, Object> createConfigMap(String url, String method) {
        configMap = new HashMap<>() {{
            put("token", token);
            put(URL, url);
            put(HTTP_METHOD, method);
            put("fixedHeaders", fixedHeaders);
        }};
        return configMap;
    }
}
