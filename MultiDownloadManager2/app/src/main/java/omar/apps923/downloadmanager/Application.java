package omar.apps923.downloadmanager;


import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import modules.prdownloader.library.PRDownloader;
import modules.prdownloader.library.PRDownloaderConfig;
import omar.apps923.downloadmanager.model.DataManager;

public class Application extends android.app.Application {



    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    //    MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

       EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .setParallel(true)
                .setShowNotification(true)
                .build();
        PRDownloader.initialize(this, config);
        (DataManager.getInstance(this)).initData();

    }


}