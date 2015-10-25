package project.celine.infinitescroll.service.job;

import android.content.Context;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.List;

import de.greenrobot.event.EventBus;
import project.celine.infinitescroll.MyApplication;
import project.celine.infinitescroll.data.RecordEvent;
import project.celine.infinitescroll.model.RecordEntity;
import project.celine.infinitescroll.model.db.RecordModel;
import project.celine.infinitescroll.service.HookApiClient;

/**
 * Created by celine on 2015/10/25.
 */
public class GetRecordJob extends Job {
    int start;
    int num;
    public GetRecordJob(int start,int num) {
        super(new Params(1).requireNetwork());
        this.start = start;
        this.num = num;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Context context = MyApplication.getInstance();
        RecordModel recordModel = new RecordModel(context);
        List<RecordEntity> recordEntityList = recordModel.getRecordEntities(start, start + num);
        if(recordEntityList != null && recordEntityList.size() == num){
            EventBus.getDefault().post(new RecordEvent(recordEntityList));
        }
        HookApiClient.HookApiService service = HookApiClient.createService();
        service.listRecords(start,num);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
