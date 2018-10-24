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

package modules.prdownloader.library.request;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

import modules.prdownloader.library.Error;
import modules.prdownloader.library.OnDownloadListeners;
import modules.prdownloader.library.Priority;
import modules.prdownloader.library.Status;
import modules.prdownloader.library.core.Core;
import modules.prdownloader.library.internal.ComponentHolder;
import modules.prdownloader.library.internal.DownloadRequestQueue;
import modules.prdownloader.library.utils.Utils;
import modules.prdownloader.outercode.model.DataManager;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import modules.prdownloader.outercode.notifications.DownloadNotification;

/**
 * Created by amitshekhar on 13/11/17.
 */

public class DownloadRequest {

    private Priority priority;
    private Object tag;
    private String url;
    private String dirPath;
    private String fileName;
    private int sequenceNumber;
    private Future future;
    private long downloadedBytes;
    private long totalBytes;
    private int readTimeout;
    private int connectTimeout;
    private String userAgent;
    private OnDownloadListeners onDownloadListeners;
    private String downloadId;
    private HashMap<String, List<String>> headerMap;
    private Status status;
    private DownloadNotification mDownloadNotification ;
    private Context context ;
    private Download download;
    private DataManager dataManager;
    private boolean isParallel = true;

    public boolean isParallel() {
        return isParallel;
    }



    public Download getDownload() {
        return download;
    }

    public Context getContext() {
        return context;
    }

    DownloadRequest(DownloadRequestBuilder builder) {
        this.context=builder.context;
        this.isParallel=getIsParallelFromConfig();
        mDownloadNotification=new DownloadNotification(context);
        this.url = builder.url;
        this.dirPath = builder.dirPath;
        this.fileName = builder.fileName;
        this.headerMap = builder.headerMap;
        this.priority = builder.priority;
        this.tag = builder.tag;
        this.readTimeout =
                builder.readTimeout != 0 ?
                        builder.readTimeout :
                        getReadTimeoutFromConfig();
        this.connectTimeout =
                builder.connectTimeout != 0 ?
                        builder.connectTimeout :
                        getConnectTimeoutFromConfig();
        this.userAgent = builder.userAgent;
        download =new Download(downloadId,url,dirPath,fileName,0,
                Status.QUEUED,dirPath,
                downloadedBytes,totalBytes);
        dataManager=DataManager.getInstance(this.context);

    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public HashMap<String, List<String>> getHeaders() {
        return headerMap;
    }

    public Future getFuture() {
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    public long getDownloadedBytes() {
        return downloadedBytes;
    }

    public void setDownloadedBytes(long downloadedBytes) {
        this.downloadedBytes = downloadedBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getUserAgent() {
        if (userAgent == null) {
            userAgent = ComponentHolder.getInstance().getUserAgent();
        }
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getDownloadId() {
        return downloadId;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public DownloadRequest setOnDownloadListeners(OnDownloadListeners onDownloadListeners) {
        this.onDownloadListeners = onDownloadListeners;
        return this;
    }

    public OnDownloadListeners getOnDownloadListeners( ) {
        return onDownloadListeners;
    }

    public String start(OnDownloadListeners onDownloadListeners) {
        this.onDownloadListeners = onDownloadListeners;
        downloadId = Utils.getUniqueId(url, dirPath, fileName);
        DownloadRequestQueue.getInstance().addRequest(this);
        return downloadId;
    }

    public void deliverError(final Error error) {
        if (status != Status.CANCELLED) {
            Core.getInstance().getExecutorSupplier().forMainThreadTasks()
                    .execute(new Runnable() {
                        public void run() {
                            if (onDownloadListeners != null) {
                                download.setStatus(Status.ERROR);
                                dataManager.updateData(download);
                                mDownloadNotification.onFailed(error, download);
                                onDownloadListeners.onError(download,error);
                            }

                            sendBroadCast(download);

                            finish();
                        }
                    });
        }
    }

    public void deliverSuccess() {
        if (status != Status.CANCELLED) {
            setStatus(Status.COMPLETED);
            Core.getInstance().getExecutorSupplier().forMainThreadTasks()
                    .execute(new Runnable() {
                        public void run() {
                            if (onDownloadListeners != null) {
                                download.setStatus(Status.COMPLETED);
                                dataManager.updateData(download);
                                mDownloadNotification.onCompleted(download);
                                onDownloadListeners.onDownloadComplete( download );
                            }
                            sendBroadCast(download);
                            finish();
                        }
                    });
        }
    }

    public void deliverStartEvent() {
        if (status != Status.CANCELLED) {
            Core.getInstance().getExecutorSupplier().forMainThreadTasks()
                    .execute(new Runnable() {
                        public void run() {
                            if (onDownloadListeners != null) {
                                download.setDownloadId(downloadId);
                                download.setDownloadedBytes(downloadedBytes);
                                download.setTotalBytes(totalBytes);
                                dataManager.saveData(download);
                                mDownloadNotification.onConnecting(download);
                                 onDownloadListeners.onStartOrResume(download);
                            }
                            sendBroadCast(download);
                        }

                    });
        }
    }

    public void deliverPauseEvent() {
        if (status != Status.CANCELLED) {
            Core.getInstance().getExecutorSupplier().forMainThreadTasks()
                    .execute(new Runnable() {
                        public void run() {
                            if (onDownloadListeners != null) {
                                download.setStatus(Status.PAUSED);
                                dataManager.updateData(download);
                                mDownloadNotification.onDownloadPaused(download);
                                onDownloadListeners.onPause(download);
                            }
                            sendBroadCast(download);
                        }
                    });
        }
    }

    private void deliverCancelEvent() {
        Core.getInstance().getExecutorSupplier().forMainThreadTasks()
                .execute(new Runnable() {
                    public void run() {
                        if (onDownloadListeners != null) {
                            download.setStatus(Status.CANCELLED);
                            dataManager.deleteData(download);
                            mDownloadNotification.onDownloadCanceled(download);
                            onDownloadListeners.onCancel(download);
                        }
                        sendBroadCast(download);

                        if(!isParallel)
                        {
                            DownloadRequestQueue.getInstance().addNextSerialRequest();
                        }

                    }
                });
    }


    public void cancel() {
        status = Status.CANCELLED;
        if (future != null) {
            future.cancel(true);
        }
        deliverCancelEvent();
        Utils.deleteTempFileAndDatabaseEntryInBackground(Utils.getTempPath(dirPath, fileName), downloadId);
    }

    private void finish() {
        destroy();
        DownloadRequestQueue.getInstance().finish(this);
    }

    private void destroy() {
        this.onDownloadListeners = null;
    }

    private int getReadTimeoutFromConfig() {
        return ComponentHolder.getInstance().getReadTimeout();
    }

    private int getConnectTimeoutFromConfig() {
        return ComponentHolder.getInstance().getConnectTimeout();
    }

    private boolean getIsParallelFromConfig() {
        return ComponentHolder.getInstance().isParallel();
    }



    private void sendBroadCast(Download download) {

        EventBus.getDefault().post(download);
    }


}
