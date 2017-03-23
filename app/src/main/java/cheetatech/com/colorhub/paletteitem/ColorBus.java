package cheetatech.com.colorhub.paletteitem;

import cheetatech.com.colorhub.realm.SavedObject;

/**
 * Created by erkan on 22.03.2017.
 */
public class ColorBus {

    private SavedObject savedObject = null;

    private static ColorBus ourInstance = null;

    public static ColorBus getInstance() {
        if(ourInstance == null)
            ourInstance = new ColorBus();
        return ourInstance;
    }

    private ColorBus() {
    }

    public void setSavedObject(SavedObject object){
        this.savedObject = object;
    }

    public SavedObject getSavedObject(){
        return this.savedObject;
    }
}
