package cheetatech.com.colorhub.dagger2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import cheetatech.com.colorhub.retrofit.RetrofitInterface;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by coderkan on 06.11.2017.
 */

@Module
public class NetModule {
    private String baseUrl;

    public NetModule(String baseUrl){
        this.baseUrl = baseUrl;
    }


    @Provides
    @Singleton
    OkHttpClient provideClient(){
        OkHttpClient client = new OkHttpClient
                .Builder()
                .build();
        return client;
    }

    @Singleton
    @Provides
    Gson provideGson(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return gson;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client, Gson gson){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    @Provides
    @Singleton
    RetrofitInterface provideRetrofitInterface(Retrofit retrofit){
        return retrofit.create(RetrofitInterface.class);
    }


}
