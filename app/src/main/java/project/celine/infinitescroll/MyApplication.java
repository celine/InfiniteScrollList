package project.celine.infinitescroll;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by celine on 2015/10/24.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
