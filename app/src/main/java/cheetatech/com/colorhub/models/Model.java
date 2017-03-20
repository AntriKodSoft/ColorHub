package cheetatech.com.colorhub.models;

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
        this.colorCode = colorCode;
    }

}
