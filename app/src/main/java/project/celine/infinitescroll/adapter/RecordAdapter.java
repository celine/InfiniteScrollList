package project.celine.infinitescroll.adapter;

import android.content.res.Resources;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import project.celine.infinitescroll.Constants;
import project.celine.infinitescroll.R;
import project.celine.infinitescroll.model.RecordEntity;

/**
 * Created by celine on 2015/10/26.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> implements Constants {
    private static final String LOG_TAG = RecordAdapter.class.getSimpleName();
    LruCache<Integer, List<RecordEntity>> recordsCache;
    Resources mResources;
    private int mCount;

    public RecordAdapter(Resources res, LruCache<Integer, List<RecordEntity>> recordsCache) {
        this.recordsCache = recordsCache;
        mResources = res;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecordViewHolder.createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        int offset = position / FETCH_RECORD_NUM;
        int mod = position % FETCH_RECORD_NUM;
        List<RecordEntity> recordEntities = recordsCache.get(offset);
        if (recordEntities != null) {
            RecordEntity recordEntity = recordEntities.get(mod);
            holder.getTitle().setText(mResources.getString(R.string.title_format, recordEntity.getSender(),
                    recordEntity.getAmount(), recordEntity.getCurrency(), recordEntity.getRecipient()));
            holder.getNote().setText(recordEntity.getNote());
            SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
            String dateTime = format.format(recordEntity.getCreated());
            holder.getDate().setText(dateTime);
            holder.getLoadingView().setVisibility(View.GONE);
            holder.getTitle().setVisibility(View.VISIBLE);
            holder.getNote().setVisibility(View.VISIBLE);
            holder.getDate().setVisibility(View.VISIBLE);

        } else {
            holder.getLoadingView().setVisibility(View.VISIBLE);
            holder.getTitle().setVisibility(View.GONE);
            holder.getNote().setVisibility(View.GONE);
            holder.getDate().setVisibility(View.GONE);
        }

    }

    public void updateData(int count) {
        mCount = count;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "getItemCount " + mCount);
        return mCount;
    }
}
