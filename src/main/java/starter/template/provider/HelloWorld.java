package starter.template.provider;

import starter.template.models.Secret;

public class HelloWorld {

    private final Secret starterSecret;

    public HelloWorld(Secret starterSecret) {
        this.starterSecret = starterSecret;
    }

    public String getMessage() {
        return "Hello World !!";
    }
}
