package io.nothing.android;

/**
 * Created by sanvi on 6/15/15.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * @author: Sanvi
 * @date: 11/5/14
 * @email: sanvibyfish@gmail.com
 */
public abstract class NothingAdapter<T extends Object> extends android.widget.BaseAdapter {

  public final String TAG = getClass().getSimpleName();

  protected Context context;
  protected List<T> items;

  public NothingAdapter(Context context) {
    this.context = context;
  }

  public List<T> getItems() {
    return items;
  }

  @Override
  public int getCount() {
    int count = 0;
    if (items != null) {
      count = items.size();
    }
    return count;
  }

  @Override
  public T getItem(int position) {
    return items.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final NothingViewHolder holder;

    if (convertView == null) {
      convertView = View.inflate(context, getLayoutResource(), null);
      holder = getViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (NothingViewHolder) convertView.getTag();
    }

    T item = items.get(position);
    render(position,holder,item);
    return convertView;
  }

  public abstract void render(int position,NothingViewHolder baseHolder,T item);

  public abstract NothingViewHolder getViewHolder(View convertView);

  public abstract int getLayoutResource();
}
