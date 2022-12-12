package starter.template.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class and its fields are used to map the values to be returned by the /version endpoint.
 */
@JsonPropertyOrder({
        "name",
        "buildDateTime",
        "version"
})
public class VersionResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("buildDateTime")
    private String buildDateTime;
    @JsonProperty("version")
    private String version;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("buildDateTime")
    public String getBuildDateTime() {
        return buildDateTime;
    }

    @JsonProperty("buildDateTime")
    public void setBuildDateTime(String buildDateTime) {
        this.buildDateTime = buildDateTime;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }
}
