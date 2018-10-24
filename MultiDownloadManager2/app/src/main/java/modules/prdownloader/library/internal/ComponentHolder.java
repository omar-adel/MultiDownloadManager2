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

import android.content.Context;

import java.util.HashSet;

import modules.prdownloader.library.Constants;
import modules.prdownloader.library.PRDownloader;
import modules.prdownloader.library.PRDownloaderConfig;
import modules.prdownloader.library.database.AppDbHelper;
import modules.prdownloader.library.database.DbHelper;
import modules.prdownloader.library.database.NoOpsDbHelper;
import modules.prdownloader.library.httpclient.DefaultHttpClient;
import modules.prdownloader.library.httpclient.HttpClient;

/**
 * Created by amitshekhar on 14/11/17.
 */

public class ComponentHolder {

    private static ComponentHolder INSTANCE;
    //    private final static ComponentHolder INSTANCE = new ComponentHolder();
    private int readTimeout;
    private int connectTimeout;
    private String userAgent;
    private HttpClient httpClient;
    private DbHelper dbHelper;
    private boolean isParallel = true;
    private boolean showNotification = true;
    private HashSet<Integer> notification_ids ;



    //    public static ComponentHolder getInstance() {
//        return INSTANCE;
//    }
    public static ComponentHolder getInstance( ) {
        if (INSTANCE == null) {
            INSTANCE = new ComponentHolder();
        }
        return INSTANCE;
    }

    private ComponentHolder( ) {
        notification_ids = new HashSet<Integer>();
    }

    public void init(Context context, PRDownloaderConfig config) {
        this.isParallel = config.isParallel();
        this.showNotification = config.isShowNotification();
        this.readTimeout = config.getReadTimeout();
        this.connectTimeout = config.getConnectTimeout();
        this.userAgent = config.getUserAgent();
        this.httpClient = config.getHttpClient();
        this.dbHelper = config.isDatabaseEnabled() ? new AppDbHelper(context) : new NoOpsDbHelper();
        if (config.isDatabaseEnabled()) {
            PRDownloader.cleanUp(30);
        }
    }

    public boolean isParallel() {
        return isParallel;
    }

    public boolean isShowNotification() {
        return showNotification;
    }

    public HashSet<Integer> getNotification_ids() {
        if (notification_ids == null) {
            notification_ids = new HashSet<Integer>();
        }

        return notification_ids;
    }

    public int getReadTimeout() {
        if (readTimeout == 0) {
            synchronized (ComponentHolder.class) {
                if (readTimeout == 0) {
                    readTimeout = Constants.DEFAULT_READ_TIMEOUT_IN_MILLS;
                }
            }
        }
        return readTimeout;
    }

    public int getConnectTimeout() {
        if (connectTimeout == 0) {
            synchronized (ComponentHolder.class) {
                if (connectTimeout == 0) {
                    connectTimeout = Constants.DEFAULT_CONNECT_TIMEOUT_IN_MILLS;
                }
            }
        }
        return connectTimeout;
    }

    public String getUserAgent() {
        if (userAgent == null) {
            synchronized (ComponentHolder.class) {
                if (userAgent == null) {
                    userAgent = Constants.DEFAULT_USER_AGENT;
                }
            }
        }
        return userAgent;
    }

    public DbHelper getDbHelper() {
        if (dbHelper == null) {
            synchronized (ComponentHolder.class) {
                if (dbHelper == null) {
                    dbHelper = new NoOpsDbHelper();
                }
            }
        }
        return dbHelper;
    }

    public HttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (ComponentHolder.class) {
                if (httpClient == null) {
                    httpClient = new DefaultHttpClient();
                }
            }
        }
        return httpClient.clone();
    }

}
