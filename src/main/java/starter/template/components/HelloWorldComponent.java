package starter.template.components;

import dagger.BindsInstance;
import dagger.Component;
import starter.template.models.Secret;
import starter.template.modules.HelloWorldModule;
import starter.template.services.HelloWorldService;

import javax.inject.Singleton;

@Singleton
@Component(modules = {HelloWorldModule.class})
public interface HelloWorldComponent {
    HelloWorldService buildHelloWorldService();
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder withSecret(Secret secret);

        HelloWorldComponent build();
    }
}
