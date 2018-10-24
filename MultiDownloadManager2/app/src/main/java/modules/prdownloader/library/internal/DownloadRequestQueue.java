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

package modules.prdownloader.library.internal;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import modules.prdownloader.library.PRDownloader;
import modules.prdownloader.library.Status;
import modules.prdownloader.library.core.Core;
import modules.prdownloader.library.request.DownloadRequest;
import omar.apps923.downloadmanager.R;

/**
 * Created by amitshekhar on 13/11/17.
 */

public class DownloadRequestQueue {

    private static DownloadRequestQueue instance;
    private final Map<String, DownloadRequest> currentRequestMap;
    private final ArrayList<String> downloadIds;

    private final AtomicInteger sequenceGenerator;


    private DownloadRequestQueue() {
        currentRequestMap = new ConcurrentHashMap<>();
        downloadIds = new ArrayList<>();
        sequenceGenerator = new AtomicInteger();
    }


    public static void initialize() {
        getInstance();
    }

    public static DownloadRequestQueue getInstance() {
        if (instance == null) {
            synchronized (DownloadRequestQueue.class) {
                if (instance == null) {
                    instance = new DownloadRequestQueue();
                }
            }
        }
        return instance;
    }

    public Map<String, DownloadRequest> getCurrentRequestMap() {
        return currentRequestMap;
    }

    private int getSequenceNumber() {
        return sequenceGenerator.incrementAndGet();
    }

    public void pause_resume(String downloadId) {
        if (Status.RUNNING == PRDownloader.getStatusById(downloadId)) {
            PRDownloader.pause(downloadId);
        }

        else if (Status.PAUSED == PRDownloader.getStatusById(downloadId)) {
            PRDownloader.resume(downloadId);
        }
    }


    public void pause(String downloadId) {
        DownloadRequest request = currentRequestMap.get(downloadId);
        if (request != null) {
            request.setStatus(Status.PAUSED);
        }
    }

    public void resume(String downloadId) {
        DownloadRequest request = currentRequestMap.get(downloadId);
        if (request != null) {
            request.setStatus(Status.QUEUED);
            request.setFuture(Core.getInstance()
                    .getExecutorSupplier()
                    .forDownloadTasks()
                    .submit(new DownloadRunnable(request)));
        }
    }

    private void cancelAndRemoveFromMap(DownloadRequest request) {
        if (request != null) {
            downloadIds.remove(request.getDownloadId());
            currentRequestMap.remove(request.getDownloadId());
            request.cancel();
        }
    }

    public void cancel(String downloadId) {
        DownloadRequest request = currentRequestMap.get(downloadId);
        cancelAndRemoveFromMap(request);
    }

    public void cancel(Object tag) {
        for (Map.Entry<String, DownloadRequest> currentRequestMapEntry : currentRequestMap.entrySet()) {
            DownloadRequest request = currentRequestMapEntry.getValue();
            if (request.getTag() instanceof String && tag instanceof String) {
                final String tempRequestTag = (String) request.getTag();
                final String tempTag = (String) tag;
                if (tempRequestTag.equals(tempTag)) {
                    cancelAndRemoveFromMap(request);
                }
            } else if (request.getTag().equals(tag)) {
                cancelAndRemoveFromMap(request);
            }
        }
    }

    public void pauseAll() {
        for (Map.Entry<String, DownloadRequest> currentRequestMapEntry : currentRequestMap.entrySet()) {
            DownloadRequest request = currentRequestMapEntry.getValue();
            if (request != null) {
                request.setStatus(Status.PAUSED);
            }
        }
    }

    public void resumeAll() {
        for (Map.Entry<String, DownloadRequest> currentRequestMapEntry : currentRequestMap.entrySet()) {
            DownloadRequest request = currentRequestMapEntry.getValue();
            if (request != null) {
                request.setStatus(Status.QUEUED);
                request.setFuture(Core.getInstance()
                        .getExecutorSupplier()
                        .forDownloadTasks()
                        .submit(new DownloadRunnable(request)));
            }
        }
    }


    public void cancelAll() {
        for (Map.Entry<String, DownloadRequest> currentRequestMapEntry : currentRequestMap.entrySet()) {
            DownloadRequest request = currentRequestMapEntry.getValue();
            cancelAndRemoveFromMap(request);
        }
    }

    public Status getStatusById(String downloadId) {
        DownloadRequest request = currentRequestMap.get(downloadId);
        if (request != null) {
            return request.getStatus();
        }
        return Status.UNKNOWN;
    }


    public void addRequest(DownloadRequest request) {
        if(request.isParallel())
        {
            downloadIds.add(request.getDownloadId());
            currentRequestMap.put(request.getDownloadId(), request);
            request.setStatus(Status.QUEUED);
            request.setSequenceNumber(getSequenceNumber());
            request.setFuture(Core.getInstance()
                    .getExecutorSupplier()
                    .forDownloadTasks()
                    .submit(new DownloadRunnable(request)));
        }
        else
        {
            if(currentRequestMap.size()==0)
            {
                downloadIds.add(request.getDownloadId());
                currentRequestMap.put(request.getDownloadId(), request);
                request.setStatus(Status.QUEUED);
                request.setSequenceNumber(getSequenceNumber());
                request.setFuture(Core.getInstance()
                        .getExecutorSupplier()
                        .forDownloadTasks()
                        .submit(new DownloadRunnable(request)));
            }
            else  if(currentRequestMap.size()>=1)
            {
                if(downloadIds.contains(request.getDownloadId()))
                {

                    Toast.makeText(request.getContext(), request.getContext().getString(
                            R.string.download_found_Before),Toast.LENGTH_LONG).show();
                }
                else
                {
                    downloadIds.add(request.getDownloadId());
                    currentRequestMap.put(request.getDownloadId(), request);
                    Toast.makeText(request.getContext(), request.getContext().getString(
                            R.string.download_added_to_queue),Toast.LENGTH_LONG).show();
                }

            }
        }

    }

    public void addNextSerialRequest()
    {
        if(currentRequestMap.size()>0)
        {
            //String downloadId   = (String)currentRequestMap.keySet().toArray()[0];
            String downloadId   = downloadIds.get(0);
            if(downloadId!=null)
            {
                DownloadRequest request = currentRequestMap.get(downloadId);
                if(request!=null)
                {
                    request.setStatus(Status.QUEUED);
                    request.setSequenceNumber(getSequenceNumber());
                    request.setFuture(Core.getInstance()
                            .getExecutorSupplier()
                            .forDownloadTasks()
                            .submit(new DownloadRunnable(request)));
                }
            }
        }

    }
    public void finish(DownloadRequest request) {
        downloadIds.remove(request.getDownloadId());
        currentRequestMap.remove(request.getDownloadId());
        if(!request.isParallel())
        {
            addNextSerialRequest();
        }
    }
}
