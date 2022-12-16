package starter.template.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import starter.template.ConnectionBase;
import starter.template.components.DaggerHelloWorldComponent;
import starter.template.services.HelloWorldService;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/1.0")
public class HelloWorldResource extends ConnectionBase {
    @Context
    ContainerRequestContext requestContext;

    private static final Logger LOGGER = LogManager.getLogger(HelloWorldResource.class);

    public HelloWorldResource() {
        // Zero argument constructor
    }

    @GET
    @Path("/helloworld")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Hello World resource.",
            description = "Simple example of a GET point that returns a Hello world message",
            tags = {"HelloWorld"},
            responses = {
                    @ApiResponse(description = "String",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)))
            })
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
            scopes = {"read:image_signature"}
    )
    @PermitAll
    public Response getHelloWorld() {
        try {
            HelloWorldService helloWorldService = DaggerHelloWorldComponent.builder()
                    .withSecret(getSecret()).build().buildHelloWorldService();

            String message = helloWorldService.callHelloWorld();

            return Response.status(Response.Status.OK).entity(message).build();
        } catch (Exception e) {
            LOGGER.warn("Application Error: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error").build();
        }
    }
}
