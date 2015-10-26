package project.celine.infinitescroll;

/**
 * Created by celine on 2015/10/26.
 */
public interface Constants {
    int FETCH_RECORD_NUM = 50;
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    //the size must larger than 4*FETCH_RECORD_NUM
    int CACHE_SIZE = 1024*1024;//10MB
}
