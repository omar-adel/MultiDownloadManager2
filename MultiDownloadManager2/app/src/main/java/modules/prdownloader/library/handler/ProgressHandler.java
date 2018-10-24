/*
 *    Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package modules.prdownloader.library.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;

import modules.prdownloader.library.Constants;
import modules.prdownloader.library.OnDownloadListeners;
import modules.prdownloader.library.Progress;
import modules.prdownloader.library.Status;
import modules.prdownloader.library.request.DownloadRequest;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import modules.prdownloader.outercode.notifications.DownloadNotification;

/**
 * Created by amitshekhar on 13/11/17.
 */

public class ProgressHandler extends Handler {

    private   String url="" ;
    private final String downloadId ;
    private  OnDownloadListeners downloadListeners;
    private DownloadNotification mDownloadNotification ;
    private Context context ;
    private Download download;
    private long mLastTime;

    public ProgressHandler(DownloadRequest downloadRequest) {
        super(Looper.getMainLooper());
        this.context = downloadRequest.getContext();
        this.downloadId = downloadRequest.getDownloadId();
        this.url = downloadRequest.getUrl();
        this.downloadListeners = downloadRequest.getOnDownloadListeners();
        this.download = downloadRequest.getDownload();
        mDownloadNotification=new DownloadNotification(context);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Constants.UPDATE:
                if (mLastTime == 0) {
                    mLastTime = System.currentTimeMillis();
                }

                long currentTime = System.currentTimeMillis();
                long progressPercent=0;
                if (downloadListeners != null) {
                    final Progress progress = (Progress) msg.obj;
                    progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                    download.setDownloadedBytes(progress.currentBytes);
                    download.setProgress((int) progressPercent);
                    download.setStatus(Status.RUNNING);
                    downloadListeners.onProgress(downloadId,url,progress);
                }

                if (currentTime - mLastTime > 1000) {

                    download.update();
                    if (downloadListeners != null) {
                        mDownloadNotification.onProgress((int)progressPercent, download);
                    }

                    sendBroadCast(download);
                    mLastTime = currentTime;
                }


                break;
            default:
                super.handleMessage(msg);
                break;
        }
    }
    private void sendBroadCast(Download download) {

        EventBus.getDefault().post(download);
    }
}
