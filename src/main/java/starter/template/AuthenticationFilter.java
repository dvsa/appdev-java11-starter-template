package starter.template;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public final class AuthenticationFilter extends ConnectionBase implements ContainerRequestFilter {

    // Add you Authentication Filter implementation below.  See other SMC API's for reference

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

    }

}
