package starter.template;

import appdev.api.common.lib.cors.Cors;
import appdev.api.common.lib.cors.CorsFactory;
import appdev.api.common.lib.logging.LogOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ApplicationResponseFilter implements ContainerResponseFilter {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationResponseFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        Cors cors = CorsFactory.createInstance(responseContext, requestContext);
        cors.setCorsOptions();

        if (!requestContext.getUriInfo().getRequestUri().toString().contains("/version")) {
            String logOutput = new LogOutput.LogOutputBuilder()
                    .apiName("Starter Template API")
                    .containerRequestContext(requestContext)
                    .containerResponseContext(responseContext)
                    .buildLogOutput();
            LOGGER.info(logOutput);
        }
    }
}
