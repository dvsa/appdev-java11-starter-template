package starter.template;

import dvsa.common.lib.secrets.SecretsFactory;
import dvsa.common.lib.secrets.SecretsUtility;
import starter.template.models.Secret;

/**
 * ConnectionBase Class is used as a static class to allow others to access MC credentials needed for
 * access the DVLA network.
 */
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
        // SecretsUtility secretsUtility = SecretsFactory.createInstance(System.getenv("secretkey"));
        // templateSecret = secretsUtility.getSecrets(Secret.class);
        templateSecret = null;
    }

    /**
     * Allows retrieval of Secret. The getJWT method utilises a number of private methods in this class to add a valid
     * token to the values that are already in the secret, so that it can be used at the provider level when making a
     * DVLA driver request.
     *
     * @return Secret
     */
    protected Secret getSecret() {
        return templateSecret;
    }
}
