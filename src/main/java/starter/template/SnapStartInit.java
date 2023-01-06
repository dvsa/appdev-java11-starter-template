package starter.template;

import starter.template.components.DaggerVersionComponent;
import starter.template.models.VersionResponse;
import starter.template.services.VersionService;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This class is invoked through the ApplicationHandlers beforeCheckpoint method at the time of snapshot.
 */
public class SnapStartInit extends ConnectionBase {

    /**
     * Constructor sets up connection details to the database.
     *
     * @throws IOException  IOException
     * @throws SQLException SQLException
     */
    protected SnapStartInit() throws IOException, SQLException {
        // Null constructor
    }

    public void init() throws IOException {
        // Call Version endpoint to precompile code before Snapshot is taken
        VersionService versionService = DaggerVersionComponent
                .builder().build().buildVersionService();
        VersionResponse versionResponse = versionService.getVersion();
    }
}
