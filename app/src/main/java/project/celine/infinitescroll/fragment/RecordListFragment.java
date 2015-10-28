package project.celine.infinitescroll.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.path.android.jobqueue.CancelResult;
import com.path.android.jobqueue.TagConstraint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.greenrobot.event.EventBus;
import project.celine.infinitescroll.Constants;
import project.celine.infinitescroll.R;
import project.celine.infinitescroll.adapter.RecordAdapter;
import project.celine.infinitescroll.data.RecordEvent;
import project.celine.infinitescroll.model.RecordEntity;
import project.celine.infinitescroll.service.RecordJobManager;
import project.celine.infinitescroll.service.job.GetRecordJob;

/**
 * Created by celine on 2015/10/26.
 */
public class RecordListFragment extends Fragment implements Constants {
    private static final int MAX_JOB_QUEUE = 10;
    private static final String LOG_TAG = RecordListFragment.class.getSimpleName();
    RecyclerView mRecyclerView;
    LruCache<Integer, List<RecordEntity>> recordsCache;
    RecordAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    RecordJobManager recordJobManager;
    List<String> jobTaskQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_record, null, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(false);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        jobTaskQueue = new ArrayList<>();
        recordJobManager = RecordJobManager.getJobManager(getActivity());
        recordsCache = new LruCache<Integer, List<RecordEntity>>(CACHE_SIZE) {
            @Override
            protected int sizeOf(Integer key, List<RecordEntity> value) {
                //rough size count
                return value.size();
            }
        };

        mAdapter = new RecordAdapter(getResources(), recordsCache);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //    int totalItemCount = mLayoutManager.getItemCount();
                int firstVisible = mLayoutManager.findFirstVisibleItemPosition();
             //   int visibleItemCount = mLayoutManager.getChildCount();

                int fromOffset = Math.max(0, firstVisible - 2 * FETCH_RECORD_NUM) / FETCH_RECORD_NUM;
                int toOffset = (firstVisible + 2 * FETCH_RECORD_NUM) / FETCH_RECORD_NUM;
                for (int offset = fromOffset; offset <= toOffset; offset++) {
                    if (recordsCache.get(offset) == null) {
                        String jobTagId = generateJobTag(offset);
                        if (jobTaskQueue.contains(jobTagId)) {
                            continue;
                        }
                        GetRecordJob getRecordJob = new GetRecordJob(offset * FETCH_RECORD_NUM, FETCH_RECORD_NUM, jobTagId);
                        recordJobManager.addJob(getRecordJob);

                        jobTaskQueue.add(jobTagId);
                    }
                }
                int currentSize = jobTaskQueue.size();
                if (currentSize > MAX_JOB_QUEUE) {

                    Iterator<String> jobTaskIterator = jobTaskQueue.iterator();
                    while (jobTaskIterator.hasNext()) {
                        String jobTagId = jobTaskIterator.next();
                        recordJobManager.cancelJobsInBackground(new CancelResult.AsyncCancelCallback(){
                            @Override
                            public void onCancelled(CancelResult cancelResult) {

                            }
                        }, TagConstraint.ANY, jobTagId);
                        jobTaskIterator.remove();
                        currentSize--;
                        if (currentSize < MAX_JOB_QUEUE) {
                            break;
                        }
                    }
                }

            }
        });
        GetRecordJob getRecordJob = new GetRecordJob(0, FETCH_RECORD_NUM, generateJobTag(0));
        recordJobManager.addJob(getRecordJob);
    }

    public String generateJobTag(int offset) {
        return "jobTag#" + offset;
    }

    public void onEventMainThread(RecordEvent recordEvent) {
        int offset = recordEvent.getStart() / FETCH_RECORD_NUM;
        recordsCache.put(offset, recordEvent.getRecordEntities());
        Log.d(LOG_TAG,"cache size " + recordsCache.size());
        int currentCount = mAdapter.getItemCount();
        int newCount = (offset + 1) * FETCH_RECORD_NUM;
        Log.d(LOG_TAG, "newCount " + newCount);
        mAdapter.notifyDataSetChanged();
        String jobTagId = generateJobTag(offset);
        if (jobTaskQueue.contains(jobTagId)) {
            jobTaskQueue.remove(jobTagId);
        }
    }

}
