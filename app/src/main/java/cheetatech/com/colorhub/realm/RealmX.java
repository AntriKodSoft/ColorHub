package cheetatech.com.colorhub.realm;

import android.util.Log;

import cheetatech.com.colorhub.models.Model;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by erkan on 20.03.2017.
 */

public class RealmX {

    private static Realm realm;

    private static void getRealm(){
        try {
            realm = Realm.getDefaultInstance();
        }catch (Exception e){
            Log.e("TAG","Exception Exception " + e.getMessage());
//            RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
//            realm = Realm.getInstance(config);
        }
    }

    public static void closeRealm(){
        if(realm != null){
            if(!realm.isClosed()){
                realm.close();
            }
        }
    }


    public static void save(final SavedObject object) {
        getRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(object);
                //closeRealm();
            }
        });
    }

    public static void list(){
        getRealm();
        RealmResults<SavedObject> res = realm.where(SavedObject.class)
                .findAll();

        for (SavedObject s : res ) {
            Log.e("TAG", "list: " + s.getName() );
            for (Model m : s.getList() ) {
                Log.e("TAG", "list::: " +  m.getColorCode() );
            }
            Log.e("TAG", "---------------------" );
        }

        //closeRealm();
    }

    public static RealmResults<SavedObject> getObject(String name){
        RealmResults<SavedObject> res = realm.where(SavedObject.class).findAll();
        return res;
    }

}
