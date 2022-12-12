package starter.template.services;

import starter.template.models.VersionResponse;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class VersionService {

    @Inject
    public VersionService() {
        //Zero argument constructor
    }

    /**
     * This function sets the required fields to display the version of the API.
     *
     * @return VersionResponse
     * @throws IOException IOException
     */
    public VersionResponse getVersion() throws IOException {
        Properties properties = new Properties();
        VersionResponse response = new VersionResponse();

        URL url = VersionService.class.getResource("/maven.properties");
        String version = null;
        String buildDateTime = null;

        if (url != null) {
            properties.load(url.openStream());
            version = properties.getProperty("version");
            buildDateTime = properties.getProperty("buildDateTime");
        }

        response.setName("Starter Template API");
        response.setVersion(version);
        response.setBuildDateTime(buildDateTime);

        return response;
    }
}
