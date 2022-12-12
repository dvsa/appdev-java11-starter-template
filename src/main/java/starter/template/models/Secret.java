package starter.template.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Secret {
    private String someSecretValue;

    public String getSomeSecretValue() {
        return someSecretValue;
    }

    public void setSomeSecretValue(String someSecretValue) {
        this.someSecretValue = someSecretValue;
    }
}
