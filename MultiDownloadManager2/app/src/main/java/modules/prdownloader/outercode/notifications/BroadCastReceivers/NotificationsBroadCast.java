package modules.prdownloader.outercode.notifications.BroadCastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import modules.prdownloader.library.Error;
import modules.prdownloader.library.OnDownloadListeners;
import modules.prdownloader.library.PRDownloader;
import modules.prdownloader.library.Progress;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import omar.apps923.downloadmanager.R;
import omar.apps923.downloadmanager.ui.Main.MainActivity;

/**
 * Created by Net22 on 11/23/2017.
 */

public class NotificationsBroadCast extends BroadcastReceiver implements OnDownloadListeners {

    private Context context ;
    public static final String ACTION_DOWNLOAD = "modules.prdownloader.outercode.notifications.BroadCastReceivers.action_download";
    public static final String ACTION_PAUSE = "modules.prdownloader.outercode.notifications.BroadCastReceivers.action_pause";
    public static final String ACTION_RESUME = "modules.prdownloader.outercode.notifications.BroadCastReceivers.action_resume";
    public static final String ACTION_NOTIFICATION_PAUSE_RESUME = "modules.prdownloader.outercode.notifications.BroadCastReceivers.action_notification_pause_resume";
    public static final String ACTION_NOTIFICATION_CANCEL = "modules.prdownloader.outercode.notifications.BroadCastReceivers.action_notification_cancel";
    public static final String ACTION_CANCEL = "modules.prdownloader.outercode.notifications.BroadCastReceivers.action_cancel";
    public static final String ACTION_PAUSE_ALL = "modules.prdownloader.outercode.notifications.BroadCastReceivers.action_pause_all";
    public static final String ACTION_RESUME_ALL = "modules.prdownloader.outercode.notifications.BroadCastReceivers.action_resume_all";
    public static final String ACTION_CANCEL_ALL = "modules.prdownloader.outercode.notifications.BroadCastReceivers.action_cancel_all";
    public static final String ACTION_NOTIFICATION_OPEN_APP = "modules.prdownloader.outercode.notifications.BroadCastReceivers.action_notification_open_app";
     public static final String EXTRA_DOWNLOAD_INFO = "EXTRA_DOWNLOAD_INFO";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                this.context=context;
                Download download =  intent.getParcelableExtra(EXTRA_DOWNLOAD_INFO);
                String downloadId = download.getDownloadId();

                if (action.contains(ACTION_NOTIFICATION_PAUSE_RESUME)) {
                     pause_resume(download);

                } else if (action.contains(ACTION_NOTIFICATION_CANCEL)) {
                    cancel(downloadId);

                } else {
                    switch (action) {
                        case ACTION_PAUSE:
                            pause(downloadId);
                            break;
                        case ACTION_NOTIFICATION_PAUSE_RESUME:
                            pause_resume(download);
                            break;
                        case ACTION_NOTIFICATION_CANCEL:
                            cancel(downloadId);
                            break;
                        case ACTION_CANCEL:
                            cancel(downloadId);
                            break;
                        case ACTION_RESUME:
                            resume(download);
                            break;

                        case ACTION_PAUSE_ALL:
                            pauseAll();
                            break;
                        case ACTION_CANCEL_ALL:
                            cancelAll();
                            break;
                        case ACTION_RESUME_ALL:
                            resumeAll();
                            break;
                        case ACTION_NOTIFICATION_OPEN_APP:
                            openApp(download);
                            break;

                    }
                }
            }
        }
    }


    private void pause_resume(Download download) {

        PRDownloader.pause_resume(download.getDownloadId());
    }
    private void pause(String downloadId) {
        PRDownloader.pause(downloadId);
    }

    private void cancel(String downloadId) {
        PRDownloader.cancel(downloadId);
    }



    private void resume(Download download) {
        if(download.getDownloadId()!=null)
        {
            PRDownloader.resume(download.getDownloadId());
        }
        else
        {
            String downloadId = PRDownloader.download(context,download.getUrl(),
                    download.getDirPath(), download.getFileName())
                    .build()
                    .start(this);
            download.setDownloadId(downloadId);
            download.update();
        }
     }



    private void resumeAll() {
        PRDownloader.resumeAll();
     }


    private void pauseAll() {
        PRDownloader.pauseAll();
    }

    private void cancelAll() {
        PRDownloader.cancelAll();
    }

    private void openApp(Download download) {

       Intent intent=new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
       context.startActivity(intent);
    }



    @Override
    public void onStartOrResume(Download download) {

    }

    @Override
    public void onPause(Download download) {

    }

    @Override
    public void onCancel(Download download) {

    }

    @Override
    public void onProgress(String downloadId, String url, Progress progress) {

    }

    @Override
    public void onDownloadComplete(Download download) {

    }

    @Override
    public void onError(Download download, Error error) {
        Toast.makeText(context.getApplicationContext(),
                context.getString(R.string.download_downloadError) + " ",
                Toast.LENGTH_LONG).show();
    }


}
