package cheetatech.com.colorhub;

import android.app.Application;

import cheetatech.com.colorhub.ads.AdsUtils;
import cheetatech.com.colorhub.dagger2.AppModule;
import cheetatech.com.colorhub.dagger2.DaggerNetComponent;
import cheetatech.com.colorhub.dagger2.NetComponent;
import cheetatech.com.colorhub.dagger2.NetModule;
import io.realm.Realm;

/**
 * Created by erkan on 20.03.2017.
 */

public class ColorHubApplication extends Application {

    private NetComponent component;
    private String baseUrl = "http://colormind.io/api/";

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        // Initialize for first time...
        AdsUtils.getInstance().setContext(this);
        component = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(baseUrl))
                .build();
    }

    public NetComponent getNetComponent(){
        return this.component;
    }

}
