package omar.apps923.downloadmanager.ui.Main.presenter;


import modules.basemvp.Base;

/**
 * Created by Net22 on 12/17/2017.
 */

public class IMainContract {

    public interface IMainView {

    }

    public interface IMainPresenter extends Base.IPresenter {

        void addDownload();
    }
}
