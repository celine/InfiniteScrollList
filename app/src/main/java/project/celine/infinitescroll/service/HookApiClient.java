package project.celine.infinitescroll.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;
import java.util.List;

import project.celine.infinitescroll.data.Record;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by celine on 2015/10/24.
 */
//https://hook.io/syshen/infinite-list
    //startIndex=10&num=2
public class HookApiClient {
public static HookApiService createService(){

    RestAdapter retrofit = new RestAdapter.Builder().setEndpoint("https://hook.io/syshen").setLogLevel(RestAdapter.LogLevel.FULL).setConverter(new GsonConverter(getGson())).build();
    return retrofit.create(HookApiService.class);
}
    public interface HookApiService {
        @GET("/infinite-list")
        List<Record> listRecords(@Query("startIndex") long startIndex, @Query("num") int num);
    }
    public static Gson getGson(){
        return new GsonBuilder().registerTypeAdapter(Date.class, new MyDateTypeAdapter()).create();
    }
}
