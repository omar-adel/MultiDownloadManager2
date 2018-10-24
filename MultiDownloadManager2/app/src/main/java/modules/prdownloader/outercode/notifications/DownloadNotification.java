package modules.prdownloader.outercode.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Iterator;

import modules.general.utils.NotificationUtils;
import modules.prdownloader.library.Error;
import modules.prdownloader.library.Status;
import modules.prdownloader.library.internal.ComponentHolder;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import modules.prdownloader.outercode.notifications.BroadCastReceivers.NotificationsBroadCast;
import omar.apps923.downloadmanager.R;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static modules.prdownloader.outercode.notifications.BroadCastReceivers.NotificationsBroadCast.ACTION_NOTIFICATION_CANCEL;
import static modules.prdownloader.outercode.notifications.BroadCastReceivers.NotificationsBroadCast.ACTION_NOTIFICATION_OPEN_APP;
import static modules.prdownloader.outercode.notifications.BroadCastReceivers.NotificationsBroadCast.ACTION_NOTIFICATION_PAUSE_RESUME;
import static modules.prdownloader.outercode.notifications.BroadCastReceivers.NotificationsBroadCast.EXTRA_DOWNLOAD_INFO;

public class DownloadNotification {


    public NotificationCompat.Builder mBuilder;
    NotificationUtils mNotificationUtils;

    public RemoteViews mNotificationView;
    public Context mContext;


    public DownloadNotification(Context context)

    {
        this.mContext = context;
        mNotificationUtils = new NotificationUtils(mContext);
        mBuilder = new NotificationCompat.Builder(
                mContext, NotificationUtils.ANDROID_CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.download_ic_stat_name);

        mNotificationView = new RemoteViews(
                mContext.getPackageName(),
                R.layout.download_notification);
        mBuilder.setContent(mNotificationView);
        mBuilder.setOngoing(false);

    }


    private boolean getIsShowNotificationFromConfig() {
        return ComponentHolder.getInstance().isShowNotification();
    }

    public void onConnecting(Download download) {

         if (getIsShowNotificationFromConfig())
        {
            startNotification(download);

            mNotificationView.setTextViewText(R.id.txtvName, download.getFileName());
            mNotificationView.setTextViewText(R.id.txtvProgress, "Start download " + ": "
                    + download.getProgress() + "%");

            mNotificationView.setImageViewResource(R.id.imgvPauseResume,
                    R.drawable.download_notification_pause);

            updateNotification(download);
        }
    }

    private void startNotification(Download download) {

        if (download.getDownloadId() != null) {

        Intent ACTION_OPEN_APP_INTENT = new Intent(mContext
                , NotificationsBroadCast.class);

        Intent ACTION_PAUSE_RESUME_INTENT = new Intent(mContext
                , NotificationsBroadCast.class);


        Intent ACTION_CANCEL_INTENT = new Intent(mContext
                , NotificationsBroadCast.class);

        PendingIntent openApp_pendingIntent, pause_resume_pendingIntent, cancel_pendingIntent;

        ACTION_OPEN_APP_INTENT.setAction(ACTION_NOTIFICATION_OPEN_APP);
        ACTION_OPEN_APP_INTENT.putExtra(EXTRA_DOWNLOAD_INFO, download);
        ACTION_PAUSE_RESUME_INTENT.putExtra(EXTRA_DOWNLOAD_INFO, download);
        ACTION_CANCEL_INTENT.putExtra(EXTRA_DOWNLOAD_INFO, download);

        ACTION_PAUSE_RESUME_INTENT.setAction(
                ACTION_NOTIFICATION_PAUSE_RESUME + download.getDownloadId());

        ACTION_CANCEL_INTENT.setAction(
                ACTION_NOTIFICATION_CANCEL + download.getDownloadId());

        openApp_pendingIntent = PendingIntent.getBroadcast(
                mContext, Integer.valueOf(download.getDownloadId()),
                ACTION_OPEN_APP_INTENT,
                FLAG_UPDATE_CURRENT);

        pause_resume_pendingIntent = PendingIntent.getBroadcast(
                mContext, Integer.valueOf(download.getDownloadId()),
                ACTION_PAUSE_RESUME_INTENT,
                FLAG_UPDATE_CURRENT);

        cancel_pendingIntent = PendingIntent.getBroadcast(
                mContext, Integer.valueOf(download.getDownloadId()),
                ACTION_CANCEL_INTENT,
                FLAG_UPDATE_CURRENT);


        mNotificationView.setOnClickPendingIntent(R.id.rlInfo, openApp_pendingIntent);

        mNotificationView.setOnClickPendingIntent(R.id.imgvPauseResume,
                pause_resume_pendingIntent);

        mNotificationView.setOnClickPendingIntent(R.id.imgvCancel,
                cancel_pendingIntent);

        }
    }

    public void onProgress(int progress, Download download) {

        if (getIsShowNotificationFromConfig()) {
            startNotification(download);
            mNotificationView.setViewVisibility(R.id.imgvPauseResume, VISIBLE);
            mNotificationView.setViewVisibility(R.id.imgvCancel, VISIBLE);

            mNotificationView.setTextViewText(R.id.txtvName, download.getFileName());


            if (download.getProgress() >= 100) {
                mNotificationView.setTextViewText(R.id.txtvProgress,
                        mContext.getString(R.string.download_Complete));

                mNotificationView.setProgressBar(R.id.progressBar, 100, 100, false);

            } else {

                mNotificationView.setTextViewText(R.id.txtvProgress,
                        mContext.getString(R.string.download_downloading) + ": "
                                + download.getProgress() + "%");

                mNotificationView.setProgressBar(R.id.progressBar, 100, (int) progress, false);


                if (download.getStatus() == Status.PAUSED)
                    mNotificationView.setImageViewResource(R.id.imgvPauseResume,
                            R.drawable.download_notification_resume);
                else
                    mNotificationView.setImageViewResource(R.id.imgvPauseResume,
                            R.drawable.download_notification_pause);
            }
            updateNotification(download);
        }
    }

    public void onDownloadPaused(Download download) {

        if (getIsShowNotificationFromConfig()) {
            startNotification(download);

            mNotificationView.setViewVisibility(R.id.imgvPauseResume, VISIBLE);
            mNotificationView.setViewVisibility(R.id.imgvCancel, VISIBLE);

            mNotificationView.setTextViewText(R.id.txtvName, download.getFileName());

            mNotificationView.setImageViewResource(R.id.imgvPauseResume,
                    R.drawable.download_notification_resume);

            mNotificationView.setTextViewText(R.id.txtvProgress,
                    mContext.getString(R.string.download_Paused)
                            + ": "
                            + download.getProgress() + "%");

            updateNotification(download);

        }
    }

    public void onCompleted(Download download) {

        if (getIsShowNotificationFromConfig()) {
            startNotification(download);

            mNotificationView.setViewVisibility(R.id.imgvCancel, INVISIBLE);

            mNotificationView.setTextViewText(R.id.txtvProgress,
                    mContext.getString(R.string.download_Complete) + ": "
                            + download.getProgress() + "%");
            mNotificationView.setTextViewText(R.id.txtvName, download.getFileName());
            mNotificationView.setViewVisibility(R.id.imgvPauseResume, GONE);
            mNotificationView.setProgressBar(R.id.progressBar, 100, 100, false);
            updateNotification(download);

        }
    }


    public void onDownloadCanceled(Download download) {
        if (getIsShowNotificationFromConfig()) {
            if (download.getDownloadId() != null) {
                mNotificationUtils.getManager().cancel(
                        Integer.valueOf(download.getDownloadId()) + 1000
                );
            }

        }
    }


    public void onFailed(Error error, Download download) {
        if (getIsShowNotificationFromConfig()) {
            startNotification(download);
            mNotificationView.setTextViewText(R.id.txtvName, download.getFileName());
            mNotificationView.setTextViewText(R.id.txtvProgress,
                    mContext.getString(R.string.download_downloadError));
            mNotificationView.setViewVisibility(R.id.imgvCancel, INVISIBLE);
            mNotificationView.setViewVisibility(R.id.imgvPauseResume, INVISIBLE);

            updateNotification(download);

        }
    }

    public void updateNotification(Download download) {

        if (download.getDownloadId() != null) {

            ComponentHolder.getInstance().getNotification_ids().add(Integer.valueOf(download.getDownloadId()) + 1000);
            mNotificationUtils.getManager().notify(
                    Integer.valueOf(download.getDownloadId()) + 1000
                    ,
                    mBuilder.build());
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.download_downloadError), Toast.LENGTH_LONG).show();
        }


    }


    public void clearAllDownloadNotifications() {
        Log.e("notification_ids.size()", "" + ComponentHolder.getInstance().getNotification_ids().size());
        Iterator<Integer> it = ComponentHolder.getInstance().getNotification_ids().iterator();
        while (it.hasNext()) {
            mNotificationUtils.getManager().cancel(it.next());
        }
        ComponentHolder.getInstance().getNotification_ids().clear();
    }

}
