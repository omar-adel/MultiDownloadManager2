package modules.prdownloader.outercode.ui.activities.presenters;

import android.content.Context;

import java.util.ArrayList;

import modules.general.model.db.SqliteCallBack;
import modules.general.utils.NotificationUtils;
import modules.prdownloader.library.Error;
import modules.prdownloader.library.OnDownloadListeners;
import modules.prdownloader.library.PRDownloader;
import modules.prdownloader.library.Progress;
import modules.prdownloader.library.Status;
import modules.prdownloader.outercode.model.DataManager;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import modules.prdownloader.outercode.ui.activities.DownloadsActivity;
import modules.prdownloader.outercode.ui.fragments.BaseDownloadsFragment;

/**
 * Created by Net22 on 12/14/2017.
 */

public class DownloadsActPresenter extends SqliteCallBack implements IDownloadsActContract.IDownloadsActPresenter
        , OnDownloadListeners {

    private final Context mContext;
    IDownloadsActContract.IDownloadsActView mView;
    private DataManager mDataManager;
    NotificationUtils mNotificationUtils;

    public DownloadsActPresenter(Context context, IDownloadsActContract.IDownloadsActView view) {
        mView = view;
        mContext = context;
        mDataManager = DataManager.getInstance(mContext);
        mDataManager.setPresenterSqliteCallBack(this);
        mNotificationUtils = new NotificationUtils(mContext);
    }



    @Override
    public void resumeAll() {

        ArrayList<Download> downloadArrayList  =
                (ArrayList<Download>) mDataManager.getAll(new Download());

        for (int i = 0; i < downloadArrayList.size(); i++)

        {
            Download download = (Download) ((downloadArrayList.get(i)));
            if (download.getStatus() == Status.PAUSED) {
                resumeDownload(download);
            }
            else if (download.getStatus() == Status.QUEUED) {

                resumeDownload(download);
            }
            if (download.getStatus() == Status.ERROR) {

                resumeDownload(download);
            }

        }
        ((BaseDownloadsFragment) ((DownloadsActivity) mContext).getCurrentFragmentInPager()).loadData();

    }

    @Override
    public void cancelAll() {

        PRDownloader.cancelAll();
        ArrayList<Download> downloadArrayList  =
                (ArrayList<Download>) mDataManager.getAll(new Download());

        for (int i = 0; i < downloadArrayList.size(); i++)

        {

            Download download = (Download) ((downloadArrayList.get(i)));
            if (download.getStatus() == Status.RUNNING) {
                deleteDownload(download);
            } else if (download.getStatus() == Status.PAUSED) {
                deleteDownload(download);
            }

        }

        ((BaseDownloadsFragment) ((DownloadsActivity) mContext).getCurrentFragmentInPager()).loadData();
    }

    @Override
    public void removeAll() {

        PRDownloader.cancelAll();
        mDataManager.deleteAll(new Download());
        ((BaseDownloadsFragment) ((DownloadsActivity) mContext).getCurrentFragmentInPager()).loadData();

    }

    @Override
    public void updateDownloadItem(Download download) {
         ((BaseDownloadsFragment)
                ((DownloadsActivity)mContext).getCurrentFragmentInPager())
                .updateListWithItem(download);
    }


    @Override
    public void onDBDataListLoaded(ArrayList data , String localDbOperation) {



    }

    @Override
    public void onDBDataObjectLoaded(Object data, String localDbOperation) {

    }

    private void resumeDownload(Download download) {
        if(download.getDownloadId()!=null)
        {
            PRDownloader.resume(download.getDownloadId());
        }
        else
        {
            startDownload(download);
        }

    }
    public void startDownload(Download download) {
        String downloadId = PRDownloader.download(mContext, download.getUrl(),
                download.getDirPath(), download.getFileName())
                .build()
                .start(this);
        download.setDownloadId(downloadId);

        mDataManager.saveData(download);

    }


    private void deleteDownload(Download download) {

        mDataManager.deleteData(download);

        mNotificationUtils.getManager().cancel(
                download.getId().intValue() + 1000
        );

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

    }

}
