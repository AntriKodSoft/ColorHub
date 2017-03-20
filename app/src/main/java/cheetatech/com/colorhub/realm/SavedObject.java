package cheetatech.com.colorhub.realm;

import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.Display;

import java.util.List;

import cheetatech.com.colorhub.models.Model;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by erkan on 20.03.2017.
 */

public class SavedObject extends RealmObject {

    private String name;
    private RealmList<Model> list;

    public SavedObject(){}

    public SavedObject(String name, RealmList<Model> list){
        setName(name);
        setList(list);
    }
    public SavedObject(RealmList<Model> list){
        setList(list);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Model> getList() {
        return list;
    }

    public void setList(RealmList<Model> list) {
        this.list = list;
    }

}
