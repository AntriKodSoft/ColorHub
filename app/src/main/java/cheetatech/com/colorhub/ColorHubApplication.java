package cheetatech.com.colorhub;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by erkan on 20.03.2017.
 */

public class ColorHubApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
    }

}
