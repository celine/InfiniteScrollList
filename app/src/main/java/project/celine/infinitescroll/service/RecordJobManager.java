package project.celine.infinitescroll.service;

import android.content.Context;
import android.util.Log;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

/**
 * Created by celine on 2015/10/26.
 */
public class RecordJobManager extends JobManager {
    private static final String LOG_TAG = RecordJobManager.class.getSimpleName();
    public RecordJobManager(Context context) {
        super(context);
        Configuration configuration = new Configuration.Builder(context).customLogger(new CustomLogger() {
            @Override
            public boolean isDebugEnabled() {
                return true;
            }

            @Override
            public void d(String text, Object... args) {
                Log.d(LOG_TAG, String.format(text, args));
            }

            @Override
            public void e(Throwable t, String text, Object... args) {
                Log.e(LOG_TAG, String.format(text, args), t);

            }

            @Override
            public void e(String text, Object... args) {
                Log.e(LOG_TAG, String.format(text, args));


            }
        }).minConsumerCount(1).maxConsumerCount(8).consumerKeepAlive(120).build();
    }
}
