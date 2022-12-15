package starter.template.modules;

import dagger.Module;
import dagger.Provides;
import starter.template.models.Secret;
import starter.template.provider.HelloWorld;

@Module
public class HelloWorldModule {
    @Provides
    HelloWorld provideHelloWorld(Secret secret) {
        return new HelloWorld(secret);
    }
}
