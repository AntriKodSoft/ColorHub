package cheetatech.com.colorhub.dagger2;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by coderkan on 06.11.2017.
 */
@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application){
        this.mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return this.mApplication;
    };

}
