package io.nothing.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;



import java.util.List;

/**
 * @author: Sanvi
 * @date: 9/22/14
 * @email: sanvibyfish@gmail.com
 */
public class NAlertDialog {

  protected OnItemClickListener onItemClickListener;
  protected Context context;
  protected AlertDialog.Builder builder;
  protected Dialog dialog;
  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }


  public interface OnItemClickListener {
    public void onItemClick(DialogInterface dialog, int which);
  }

  public NAlertDialog(Context context){
    this.context = context;
    builder = new AlertDialog.Builder((Activity)context);
    initDialog();
  }

  protected void initDialog(){
    setDialog(builder.create());
  }

  protected void setDialog(Dialog dialog){
    this.dialog = dialog;
  }

  public void show(){
    dialog.show();
  }

  public void dismiss(){
    dialog.dismiss();
  }


}
