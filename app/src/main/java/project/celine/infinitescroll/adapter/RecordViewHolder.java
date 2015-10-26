package project.celine.infinitescroll.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import project.celine.infinitescroll.R;

/**
 * Created by celine on 2015/10/26.
 */
public class RecordViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    View loadingView;
    TextView note;
    TextView date;

    public RecordViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        note = (TextView) itemView.findViewById(R.id.note);
        date = (TextView) itemView.findViewById(R.id.date);
        loadingView = itemView.findViewById(R.id.loading);

    }

    public static RecordViewHolder createViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_record, parent, false);
        return new RecordViewHolder(view);
    }

    public View getLoadingView() {
        return loadingView;
    }

    public TextView getNote() {
        return note;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getDate() {
        return date;
    }

}