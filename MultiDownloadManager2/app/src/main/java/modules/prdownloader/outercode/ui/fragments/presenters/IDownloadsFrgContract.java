package modules.prdownloader.outercode.ui.fragments.presenters;


import modules.basemvp.Base;
import modules.prdownloader.outercode.model.bean.sqlite.Download;

/**
 * Created by Net22 on 12/14/2017.
 */

public interface IDownloadsFrgContract {

    public interface IDownloadsFrgView {
        void  loadData();
        void  updateList();
        void  updateListWithItem(Download download);
        void  notifyListWithItem();
    }

    public interface IDownloadsFrgPresenter extends Base.IPresenter {
        void  loadData(String source);
        void  updateListWithItem(String source, Download download);
        void  notifyList();
    }

}
