package cheetatech.com.colorhub.retrofit;

import java.util.Map;

import cheetatech.com.colorhub.pojos.ColorModel;
import cheetatech.com.colorhub.pojos.DefaultModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by coderkan on 06.11.2017.
 */

public interface RetrofitInterface {
    @POST("./")
    Call<ColorModel> getColors(@Body String body);

    @POST("./")
    Call<ColorModel> getColors(@Body DefaultModel body);

}