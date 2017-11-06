package cheetatech.com.colorhub.pojos;

/**
 * Created by coderkan on 06.11.2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ColorModel {

    @SerializedName("result")
    @Expose
    private List<List<Integer>> result = null;

    public List<List<Integer>> getResult() {
        return result;
    }

    public void setResult(List<List<Integer>> result) {
        this.result = result;
    }

}