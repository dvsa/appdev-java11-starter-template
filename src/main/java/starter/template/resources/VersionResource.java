package starter.template.resources;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import starter.template.ConnectionBase;
import starter.template.components.DaggerVersionComponent;
import starter.template.models.VersionResponse;
import starter.template.services.VersionService;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class holds the method for getting the API's latest version.
 */
@Path("/1.0/version")
@OpenAPIDefinition(
        info = @Info(
                title = "Starter Template API",
                version = "0.1.0",
                description = "App Development API for retrieving <your description goes here>",
                contact = @Contact(url = "http://dvsa.gov.uk", name = "DVSA")
        ),
        servers = {
                @Server(
                        description = "Test Server",
                        url = "https://api.server.address.test.dvsacloud.uk")
        }
)
@SecurityScheme(name = "jwt",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "jwt")
@SecurityScheme(name = "api_key", paramName = "x-api-key",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER)
public class VersionResource extends ConnectionBase {
    private static final Logger LOGGER = LogManager.getLogger(VersionResource.class);

    /**
     * This method returns all details related to the version of this current API microservice.
     * If there is an error, then a 500 response is returned, but if the call is successful then a 200 response
     * is returned and a populated VersionResponse object is displayed to the user.
     *
     * @return Response
     */
    @GET
    @Operation(summary = "Display API Version",
            description = "When a HTTP:GET request is made to this endpoint the Version of the service will be returned. " +
                    "This API will then return this data in Json format.",
            tags = {"version"},
            responses = {
                    @ApiResponse(description = "API Version",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VersionResponse.class)))
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "502", description = "Bad gateway"),
            @ApiResponse(responseCode = "504", description = "Gateway timeout"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @SecurityRequirement(name = "api_key",
            scopes = {"read:version"}
    )
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getVersion() {
        try {
            VersionService versionService = DaggerVersionComponent
                    .builder().build().buildVersionService();
            VersionResponse versionResponse = versionService.getVersion();

            return Response.status(Response.Status.OK).entity(versionResponse).build();
        } catch (Exception e) {
            LOGGER.warn("Application Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error").build();
        }
    }
}
