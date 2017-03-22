package cheetatech.com.colorhub;

import android.app.Application;

import cheetatech.com.colorhub.ads.AdsUtils;
import io.realm.Realm;

/**
 * Created by erkan on 20.03.2017.
 */

public class ColorHubApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        // Initialize for first time...
        AdsUtils.getInstance().setContext(this);
    }

}
