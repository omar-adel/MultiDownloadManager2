package modules.general.ui.utils.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import omar.apps923.downloadmanager.R;


/**
 * Created by Shico on 9/17/2015.
 */
public class CustomAlertDialog {



    String alertnegbutton="";

    private CustomAlertDialogInterface alertDialogInterface;




    Activity activity;
    String alerttitle,alertbutton;
     public CustomAlertDialog(Activity activity) {
        this.activity = activity;
           alerttitle = activity.getResources().getString(R.string.alert);
         alertbutton = activity.getResources().getString(R.string.done);
    }



    public CustomAlertDialog(CustomAlertDialogInterface alertDialogInterface, Activity activity)

    {

        this.alertDialogInterface = alertDialogInterface;
        this.activity = activity;
        alerttitle = activity.getResources().getString(R.string.alert);
        alertbutton = activity.getResources().getString(R.string.done);
    }



    public void alertDialogWithIntent(String alertmessage, final Class cls){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setMessage(alertmessage);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setTitle(alerttitle);
            alertDialog.setButton(alertbutton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent myIntent = new Intent(activity, cls);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    activity.startActivity(myIntent);
                    activity.finish();
                }
            });

        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(20);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(R.color.colorPrimary));
    }
    public void alertDialog(String alertmessage){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage(alertmessage);
            alertDialog.setTitle( alerttitle);
            alertDialog.setButton(alertbutton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        alertDialog.show();
      //  alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(R.color.darkPurple));
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(20);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(R.color.colorPrimary));
    }

    public void alertDialogFinish(String alertmessage){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage(alertmessage);
        alertDialog.setTitle( alerttitle);
        alertDialog.setButton(alertbutton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                activity.finish();
            }
        });

        alertDialog.show();
        //  alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(R.color.darkPurple));
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(20);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(R.color.colorPrimary));
    }
//    public void alertDialogLanguage() {
//        AlertDialog.Builder alert_builder = new AlertDialog.Builder(activity)
//                .setCancelable(false)
//                .setTitle(activity.getResources().getString(R.string.app_name))
//                .setMessage(activity.getResources().getString(R.string.changin_langauge_will_refresh_the_app))
//                .setPositiveButton(activity.getResources().getString(R.string.ok),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                                dialog.cancel();
//                                Intent intent = new Intent(activity.getApplicationContext(), Splash.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                                activity.startActivity(intent);
//                                //activity.finish();
//
//                            }
//                        })
//                .setNegativeButton(activity.getResources().getString(R.string.no),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                                dialog.cancel();
//
//                            }
//                        });
//
//        AlertDialog alert = alert_builder.show();
//    }

    public void alertDialogConfirm(String alertmessage)

    {


        {

            alerttitle = activity.getResources().getString(R.string.alert);
            alertbutton = activity.getResources().getString(R.string.yes);
            alertnegbutton = activity.getResources().getString(R.string.noalert);


        }



        AlertDialog.Builder alert_builder = new AlertDialog.Builder(
                activity)
                .setCancelable(false)
                .setTitle(alerttitle)
                .setMessage(
                        alertmessage)
                .setPositiveButton(alertbutton,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                                alertDialogInterface.onPositiveButtonClicked();

                            }
                        })
                .setNegativeButton(alertnegbutton,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                dialog.cancel();

                            }
                        });

        AlertDialog alert = alert_builder.show();
        alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(android.R.color.holo_red_dark));
        alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(20);

    }

    public interface CustomAlertDialogInterface {

        public void onPositiveButtonClicked();


    }


}
