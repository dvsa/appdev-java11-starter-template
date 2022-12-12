package starter.template;

import starter.template.models.Secret;

/**
 * This class generates models to make coding easier
 */
public class ModelTestBase {
    public static final String DATA = "DATA";

    /**
     * This method generates a Secret object
     *
     * @return Secret
     */
    public static Secret generateSecretModel() {
        Secret secret = new Secret();
        secret.setSomeSecretValue(DATA);
        return secret;
    }
}
