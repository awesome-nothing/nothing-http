package io.nothing.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by sanvi on 9/28/15.
 */
public abstract class NothingRecyclerAdapter<T> extends RecyclerView.Adapter {
    private final Context mContext;
    protected List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public NothingRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(getLayoutResource(viewType), parent, false);
        return getViewHolder(convertView, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if( items == null) {
            render(position, (NothingRecyclerViewHolder) holder, null);
        } else {
            render(position, (NothingRecyclerViewHolder) holder, items.get(position));
        }
    }

    public abstract NothingRecyclerViewHolder getViewHolder(View view, int viewType);

    public abstract void render(int position, NothingRecyclerViewHolder baseHolder, T item);

    public abstract int getLayoutResource(int viewType);


    @Override
    public int getItemCount() {
        int count = 0;
        if (items != null) {
            count = items.size();
        }
        return count;
    }
}
