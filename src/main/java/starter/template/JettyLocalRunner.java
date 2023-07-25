package starter.template;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class JettyLocalRunner extends ResourceConfig {
    private static final Logger LOGGER = LogManager.getLogger(JettyLocalRunner.class);

    public JettyLocalRunner() {
        packages("starter.template"); // <-- Set location of your Jersey resource packages
        // allows the validation errors to be sent to the client.
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        // avoid errors from the @ValidateOnExecution in subclasses
        property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
    }

    private void startServer() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8000);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("javax.ws.rs.Application", this.getClass().getName());

        try {
            jettyServer.start();
            jettyServer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Re-interrupt the thread
            LOGGER.warn("Application error: {0}", e);
        } finally {
            jettyServer.destroy();
        }
    }

    public static void main(String[] args) {
        JettyLocalRunner server = new JettyLocalRunner();
        try {
            server.startServer();
        } catch (Exception e) {
            LOGGER.error("Failed to start the server: {}", e.getMessage());
        }
    }
}
