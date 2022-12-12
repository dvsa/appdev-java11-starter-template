package starter.template;

import org.glassfish.jersey.server.ServerProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Jetty Settings Tests")
class JettySettingsTest {

    @Test
    @DisplayName("Test JettySettings")
    void testJettySettings() {
        JettySettings jettySettings = new JettySettings();
        jettySettings.packages("test");
        jettySettings.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        jettySettings.property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);

        assertNotNull(jettySettings);
    }
}
