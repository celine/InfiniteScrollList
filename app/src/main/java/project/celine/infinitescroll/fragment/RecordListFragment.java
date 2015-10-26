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
    RecyclerView mRecyclerView;
    LruCache<Integer, List<RecordEntity>> recordsCache;
    RecordAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    RecordJobManager recordJobManager ;
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
        recordJobManager= new RecordJobManager(getActivity());
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
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisible = mLayoutManager.findFirstVisibleItemPosition();
                int visibleItemCount = mLayoutManager.getChildCount();
                if (firstVisible + visibleItemCount + FETCH_RECORD_NUM > totalItemCount) {
                    mAdapter.updateData(totalItemCount + 2 * FETCH_RECORD_NUM);
                }
                int fromOffset = Math.max(0, firstVisible - 2 * FETCH_RECORD_NUM) / FETCH_RECORD_NUM;
                int toOffset = (firstVisible + 2 * FETCH_RECORD_NUM) / FETCH_RECORD_NUM;
                for(int offset = fromOffset; offset <= toOffset;offset++){
                    if(recordsCache.get(offset) == null){
                        GetRecordJob getRecordJob = new GetRecordJob(offset*FETCH_RECORD_NUM, FETCH_RECORD_NUM);
                        recordJobManager.addJob(getRecordJob);
                    }
                }
            }
        });
        GetRecordJob getRecordJob = new GetRecordJob(0, FETCH_RECORD_NUM);
        recordJobManager.addJob(getRecordJob);
    }
    private static final String LOG_TAG = RecordListFragment.class.getSimpleName();
     public void onEventMainThread(RecordEvent recordEvent) {
        int offset = recordEvent.getStart() / FETCH_RECORD_NUM;
        recordsCache.put(offset, recordEvent.getRecordEntities());
        int currentCount = mAdapter.getItemCount();
        int newCount =  (offset+1)*FETCH_RECORD_NUM;
        Log.d(LOG_TAG, "newCount " + newCount);
        if(currentCount < newCount){
            mAdapter.updateData(newCount);
        }else {
            mAdapter.notifyDataSetChanged();
        }
    }

}
