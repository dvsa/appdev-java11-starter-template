package starter.template;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import org.apache.logging.log4j.core.Logger;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.api.HK2RuntimeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Jetty Local Runner Tests")
class JettyLocalRunnerTest {

    @Test
    @DisplayName("Test Jetty Server Creates Mocked Server")
    void testMainMethod() {
        new MockUp<JettyLocalRunner>() {
            @Mock
            public void runJettyServer(Server jettyServer) {
                // Do Nothing
            }
        };

        assertDoesNotThrow(() -> JettyLocalRunner.main(null));
    }

    @Test
    @DisplayName("Test Exception Is Caught In the Application Main Method")
    void testExceptionIsCaughtMain(@Mocked final Logger mockLogger) {
        new MockUp<JettyLocalRunner>() {
            @Mock
            public void runJettyServer(Server jettyServer) {
                throw new HK2RuntimeException("Forced exception for testing ");
            }
        };

        assertDoesNotThrow(() -> JettyLocalRunner.main(null));
    }
}
