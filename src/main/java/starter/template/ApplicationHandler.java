package starter.template;

import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.crac.Core;
import org.crac.Resource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Entry point for AWS lambda, this class is used to set properties and sources to be used.
 */
public class ApplicationHandler implements RequestStreamHandler, Resource {

    public ApplicationHandler() {
        Core.getGlobalContext().register(this); // ## Required for AWS SnapStart
    }

    private static final ResourceConfig jerseyApplication = new ResourceConfig()
            .packages("starter.template")
            .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
            .property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true)
            .register(ApplicationResponseFilter.class)
            .register(JacksonFeature.class)
            .register(AuthenticationFilter.class);

    private static final JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler =
            JerseyLambdaContainerHandler.getAwsProxyHandler(jerseyApplication);

    /**
     * This lambda function doesn't call any methods - it instead calls the jersey config which determines which
     * methods will be called.
     *
     * @param input   InputStream
     * @param output  OutputStream
     * @param context Context
     * @throws IOException exception
     */
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        handler.proxyStream(input, output, context);
    }

    @Override
    public void beforeCheckpoint(org.crac.Context<? extends Resource> context) throws Exception {
        // Call SnapStartInit class to initialise code before snapshot is taken
        SnapStartInit snapStartInit = new SnapStartInit();
        snapStartInit.init();
    }

    @Override
    public void afterRestore(org.crac.Context<? extends Resource> context) throws Exception {

    }
}
