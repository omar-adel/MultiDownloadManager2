package modules.prdownloader.library;


import modules.prdownloader.outercode.model.bean.sqlite.Download;

/**
 * Created by Net22 on 12/13/2017.
 */

public interface OnDownloadListeners {

    void onStartOrResume(Download download);

    void onPause(Download download);

    void onCancel(Download download);

    void onProgress(String downloadId,   String url ,Progress progress);

    void onDownloadComplete(Download download);

    void onError(Download download ,  Error error);

}
