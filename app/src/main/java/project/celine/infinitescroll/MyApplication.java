package project.celine.infinitescroll;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by celine on 2015/10/24.
 */
public class MyApplication extends Application {
    private static final String LOG_TAG = MyApplication.class.getSimpleName();
    static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ActiveAndroid.initialize(this);
    }

}
