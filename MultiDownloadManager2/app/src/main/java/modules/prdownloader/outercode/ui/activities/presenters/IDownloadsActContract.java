package modules.prdownloader.outercode.ui.activities.presenters;

import modules.basemvp.Base;
import modules.prdownloader.outercode.model.bean.sqlite.Download;

/**
 * Created by Net22 on 12/14/2017.
 */

public interface IDownloadsActContract {

    public interface IDownloadsActView {
    }

    public interface IDownloadsActPresenter extends Base.IPresenter {
         void resumeAll();
        void cancelAll();
        void removeAll();
       void updateDownloadItem(Download download) ;

    }


}
