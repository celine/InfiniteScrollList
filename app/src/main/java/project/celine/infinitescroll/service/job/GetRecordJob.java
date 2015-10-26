package project.celine.infinitescroll.service.job;

import android.content.Context;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.List;

import de.greenrobot.event.EventBus;
import project.celine.infinitescroll.MyApplication;
import project.celine.infinitescroll.data.Record;
import project.celine.infinitescroll.data.RecordEvent;
import project.celine.infinitescroll.model.RecordEntity;
import project.celine.infinitescroll.model.db.RecordModel;
import project.celine.infinitescroll.service.HookApiClient;

/**
 * Created by celine on 2015/10/25.
 */
public class GetRecordJob extends Job {
    long start;
    int num;
    boolean cancelJob;
    RecordModel recordModel;
    public GetRecordJob(long start,int num) {
        super(new Params(1).requireNetwork());
        this.start = start;
        this.num = num;
        cancelJob = false;
    }

    @Override
    public void onAdded() {
        Context context = MyApplication.getInstance();
        recordModel = new RecordModel(context);
        List<RecordEntity> recordEntityList = recordModel.getRecordEntities(start, start + num);
        if(recordEntityList != null && recordEntityList.size() == num){
            cancelJob = true;
            EventBus.getDefault().post(new RecordEvent(start, recordEntityList));
        }
    }

    @Override
    public void onRun() throws Throwable {
        if(cancelJob){
            return;
        }
        HookApiClient.HookApiService service = HookApiClient.createService();
        List<Record>recordList=service.listRecords(start, num);
        List<RecordEntity> recordEntityList = recordModel.saveRecordList(recordList);
        EventBus.getDefault().post(new RecordEvent(start,recordEntityList));
        //not keep instance
        recordModel = null;
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
