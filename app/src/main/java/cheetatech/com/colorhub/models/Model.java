package cheetatech.com.colorhub.models;

import cheetatech.com.colorhub.realm.RealmX;
import io.realm.RealmObject;

/**
 * Created by erkan on 17.03.2017.
 */

public class Model extends RealmObject{
    private String colorCode;

    public Model(){}
    public Model(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        RealmX.realm().beginTransaction();
        this.colorCode = colorCode;
        RealmX.realm().commitTransaction();
    }

}
