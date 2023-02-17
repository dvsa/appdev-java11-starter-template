package starter.template;

import appdev.api.common.lib.authorisation.Factory;
import appdev.api.common.lib.helpers.HelperLib;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;

import javax.annotation.security.PermitAll;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.List;

@Provider
public final class AuthenticationFilter extends ConnectionBase implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String token;
        Method method = resourceInfo.getResourceMethod();

        if (!method.isAnnotationPresent(PermitAll.class)) {
            token = retrieveHeaderField(requestContext);

            if (token == null) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            try {
                // Validate the JWT and process it to the Claim so that the claim can be checked further if needed
                // An exception will be thrown if the JWT is invalid
                JwtClaims jwtClaims = Factory.getAuthoriser(token,
                                HelperLib.getEnvironmentVariable("azure_client_id"),
                                HelperLib.getEnvironmentVariable("azure_tenant_id"))
                        .validateToken();

            } catch (InvalidJwtException exception) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                LOGGER.warn(exception.getErrorDetails());
            }
        }
    }

    /**
     * Return a field contained within the header.  The name of the field is passed into this method.
     *
     * @param requestContext request context
     * @return String
     */
    private String retrieveHeaderField(ContainerRequestContext requestContext) {
        List<String> field = requestContext.getHeaders().get(AuthenticationFilter.AUTHORIZATION);
        return (field == null) ? null : field.get(0);
    }
}
