package modules.prdownloader.outercode.utils;

import java.util.HashMap;

import modules.prdownloader.outercode.model.DataManager;
import modules.prdownloader.outercode.model.bean.sqlite.Download;

public class Util {

    public static boolean checkIfDownloadInDatabase(Download download , DataManager dataManager )
    {
        Download  downloadObject= new Download();
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("downloadId", String.valueOf(download.getDownloadId()));
        downloadObject.setItemWithCustomData(hashMap);
        downloadObject = (Download) dataManager.getItemWithCustomData(downloadObject);
        if(downloadObject!=null)
        {
            return true ;
        }
        return false ;
    }
}
