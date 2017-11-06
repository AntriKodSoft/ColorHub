package cheetatech.com.colorhub.dagger2;

import javax.inject.Singleton;

import cheetatech.com.colorhub.MainActivity;
import dagger.Component;

/**
 * Created by coderkan on 06.11.2017.
 */

@Singleton
@Component(modules = { AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity loginActivity);
}
