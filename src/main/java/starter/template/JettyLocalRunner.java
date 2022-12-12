package starter.template;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyLocalRunner {
    private static final Logger LOGGER = LogManager.getLogger(JettyLocalRunner.class);

    protected JettyLocalRunner() {
        // add comment for sonarlint
    }

    public static void main(String[] args) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8000);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class,
                "/*");

        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter("javax.ws.rs.Application", "starter.template.JettySettings");

        try {
            runJettyServer(jettyServer);
        } catch (Exception e) {
            LOGGER.warn("Application error : " , e);
        } finally {
            jettyServer.destroy();
        }
    }

    /**
     * Method used to Start the Jetty Server
     *
     * @param jettyServer Jetty Server
     * @throws Exception Exception
     */
    private static void runJettyServer(Server jettyServer) throws Exception {
        // These lines can never be tested or a Jersey server will start up
        jettyServer.start();
        jettyServer.join();
    }
}
