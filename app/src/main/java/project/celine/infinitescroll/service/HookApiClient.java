package project.celine.infinitescroll.service;

import java.util.List;

import project.celine.infinitescroll.data.Record;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by celine on 2015/10/24.
 */
//https://hook.io/syshen/infinite-list
    //startIndex=10&num=2
public class HookApiClient {
public static HookApiService createService(){
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://hook.io/syshen").build();
    return retrofit.create(HookApiService.class);
}
    public interface HookApiService {
        @GET("/infinite-list")
        List<Record> listRecords(@Query("startIndex") int startIndex, @Query("num") int num);
    }
}
