package cheetatech.com.colorhub.realm;

import android.util.Log;

import java.util.List;

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

    public static Realm realm(){
        if(realm == null)
            getRealm();
        return realm;
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

    public static RealmResults<SavedObject> getObject(){
        return realm.where(SavedObject.class).findAll();
    }

    public static SavedObject getObject(String name){
        return realm.where(SavedObject.class)
                .equalTo("name",name)
                .findFirst();
    }

    public static void setList(final String name, List<Model> list){
        final RealmList<Model> realmList = new RealmList<>();
        realmList.addAll(list);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SavedObject res = realm.where(SavedObject.class)
                        .equalTo("name",name)
                        .findFirst();
                res.setList(realmList);
                Log.e("TAG", "execute: execute" );
            }
        });
    }

    public static void deleteObject(final String name) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<SavedObject> list = realm.where(SavedObject.class).equalTo("name",name).findAll();
                list.deleteAllFromRealm();
            }
        });
    }
}
