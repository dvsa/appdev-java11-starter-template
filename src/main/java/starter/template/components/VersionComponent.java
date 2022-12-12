package starter.template.components;

import dagger.Component;
import starter.template.services.VersionService;

import javax.inject.Singleton;

@Singleton
@Component()
public interface VersionComponent {
    VersionService buildVersionService();

    @Component.Builder
    interface Builder {

        VersionComponent build();
    }
}
