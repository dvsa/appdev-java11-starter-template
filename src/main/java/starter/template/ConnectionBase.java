package starter.template;

import starter.template.models.Secret;

public abstract class ConnectionBase {
    private static Secret templateSecret;

    protected ConnectionBase() {
        getSecretFromSecretManager();
    }

    // Method to allow Secret to be retrieved in AuthenticationFilter
    static {
        getSecretFromSecretManager();
    }

    /**
     * This method will retrieve the Secret from SecretsManager, using the Common Class. Caching is
     * implemented using Static variables.
     */
    private static void getSecretFromSecretManager() {
        // Comment these lines out when connecting to an actual AWS Secret Manage
//         SecretsUtility secretsUtility = SecretsFactory.createInstance(System.getenv("secretkey"));
//         templateSecret = secretsUtility.getSecrets(Secret.class);
        templateSecret = new Secret();  // set temporally
    }

    /**
     * Allows retrieval of Secret.
     *
     * @return Secret
     */
    protected Secret getSecret() {
        return templateSecret;
    }
}
