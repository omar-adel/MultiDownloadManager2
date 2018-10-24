package omar.apps923.downloadmanager.ui.Main.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

import modules.general.model.db.SqliteCallBack;
import modules.general.model.shareddata.Prefs;
import modules.prdownloader.library.PRDownloader;
import modules.prdownloader.library.Status;
import modules.prdownloader.outercode.model.bean.DownloadRequestIdStatus;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import omar.apps923.downloadmanager.R;
import omar.apps923.downloadmanager.model.DataManager;
import omar.apps923.downloadmanager.ui.Main.MainActivity;

import static omar.apps923.downloadmanager.ui.Main.MainActivity.PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE;


/**
 * Created by Net22 on 12/17/2017.
 */

public class MainPresenter extends  SqliteCallBack implements IMainContract.IMainPresenter

{

    private final Context mContext;
    IMainContract.IMainView mView;
    private DataManager mDataManager;

    String urlDownload;
    String dirPath ;
    String fileName ;


    public MainPresenter(Context context, IMainContract.IMainView view) {
        mView = view;
        mContext = context;
        mDataManager =  DataManager.getInstance(mContext);
        mDataManager.setPresenterSqliteCallBack(this);
    }



    private void downloadFileOperation() {
        PRDownloader.download(mContext, urlDownload, dirPath,
                fileName)
                .build()
                .start(((MainActivity)mContext));
    }


    @Override
    public void addDownload( ) {

        urlDownload=((MainActivity)mContext).getUrlDownload();
        dirPath =  Prefs.getString(mContext
                        .getString(R.string.prefsDirectoryLocation)
                , Environment.getExternalStorageDirectory().getAbsolutePath()) ;
        String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(urlDownload);
        fileName = URLUtil.guessFileName(urlDownload, null, fileExtenstion);

        if (checkIfLocal()
                ) {
            Toast.makeText(mContext, mContext.getString(R.string.file_downloaded_before),
                    Toast.LENGTH_SHORT).show();

        } else {
             if (checkWriteStoragePermission()) {
                 DownloadRequestIdStatus downloadRequestIdStatus = PRDownloader.getIdAndStatusByUrl(
                         urlDownload,
                         dirPath
                         ,fileName);

                 if (Status.RUNNING == downloadRequestIdStatus.getStatus()) {
                     PRDownloader.pause(downloadRequestIdStatus.getDownloadId());
                     return;
                 }


                 if (Status.PAUSED == downloadRequestIdStatus.getStatus()) {
                     PRDownloader.resume(downloadRequestIdStatus.getDownloadId());
                     return;
                 }

                 Download download = new Download();
                 HashMap hashMap = new HashMap<String, String>();
                 hashMap.put("downloadId", String.valueOf(downloadRequestIdStatus.getDownloadId()));
                 download.setItemWithCustomData(hashMap);
                 download = (Download) mDataManager.getItemWithCustomData(
                         download);
                  if (download == null) {
                     downloadFileOperation();
                 } else {

                     Toast.makeText(mContext, mContext.getString(
                             R.string.download_found_Before),Toast.LENGTH_LONG).show();
                 }

            } else {
                requestWriteStoragePermission();
            }
        }
    }

    public boolean checkIfLocal() {
        File file = new File(Prefs.getString(mContext
                        .getString(R.string.prefsDirectoryLocation)
                , Environment.getExternalStorageDirectory().getAbsolutePath())
                + File.separator +
                fileName
        );

        if (file.exists() && file.length() != 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkWriteStoragePermission() {
        int result = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;
        }
    }

    private void requestWriteStoragePermission() {

        ((MainActivity)mContext).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);

    }

}
