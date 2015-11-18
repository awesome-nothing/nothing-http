package io.nothing.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;


/**
 * @author: Sanvi
 * @date: 9/24/14
 * @email: sanvibyfish@gmail.com
 */
public class NListAlertDialog extends NAlertDialog {

  private String[] array;
  private String title;

  public NListAlertDialog(Context context, String[] array, int titleResId) {
    super(context);
    initViews(array, context.getResources().getString(titleResId));
  }

  public NListAlertDialog(Context context, String[] array, String title) {
    super(context);
    initViews(array, title);
  }

  private void initViews(String[] array, String title) {
    this.array = array;
    this.title = title;
    initDialog();
  }

  @Override
  protected void initDialog() {
    AlertDialog.Builder materialDialog = builder.setItems(array, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        if (onItemClickListener != null) {
          onItemClickListener.onItemClick(dialog, which);
        }
        dialog.dismiss();
      }
    }).setTitle(title);
    setDialog(materialDialog.create());
  }
}
