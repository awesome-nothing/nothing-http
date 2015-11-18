package io.nothing.android;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by sanvi on 9/28/15.
 */
public class NothingRecyclerViewHolder extends RecyclerView.ViewHolder {
    public NothingRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
