package starter.template.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import starter.template.provider.HelloWorld;

import javax.inject.Inject;

public class HelloWorldService {

    private final HelloWorld helloWorld;

    private static final Logger LOGGER = LogManager.getLogger(HelloWorldService.class);

    @Inject
    public HelloWorldService(HelloWorld helloWorld) {
        this.helloWorld = helloWorld;
    }

    public String callHelloWorld() {
        try {
            return helloWorld.getMessage();
        } catch (Exception e) {
            LOGGER.error("Hello World request has thrown an exception: ", e);
            return null;
        }
    }
}
