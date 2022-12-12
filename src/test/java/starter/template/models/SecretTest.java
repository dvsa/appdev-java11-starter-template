package starter.template.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import starter.template.ModelTestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Secret Tests")
class SecretTest extends ModelTestBase {

    @Test
    @DisplayName("test Secret Model")
    void testSecretModel() {
        Secret secret = generateSecretModel();

        assertEquals(DATA, secret.getSomeSecretValue());

    }
}
