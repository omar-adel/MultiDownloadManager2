package modules.prdownloader.outercode.ui.fragments;

import modules.basemvp.Base;
import modules.basemvp.BaseSupportFragment;
import modules.general.ui.utils.adapters.CustomRecyclerViewAdapter;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import modules.prdownloader.outercode.ui.fragments.presenters.DownloadsFrgPresenter;
import modules.prdownloader.outercode.ui.fragments.presenters.IDownloadsFrgContract;

/**
 * Created by Net22 on 12/17/2017.
 */

public class BaseDownloadsFragment <P extends Base.IPresenter> extends BaseSupportFragment
 implements IDownloadsFrgContract.IDownloadsFrgView{

    public CustomRecyclerViewAdapter downloadsAdapter;
    public CustomRecyclerViewAdapter getDownloadsAdapter() {
        return downloadsAdapter;
    }

    private boolean isVisible;
    private boolean isStarted;

    @Override
    public int getLayoutResource() {
        return 0;
    }

    @Override
    public void configureUI() {

    }


    @Override
    public DownloadsFrgPresenter injectDependencies() {
        return new DownloadsFrgPresenter(getContainerActivity(), this);
    }


    public DownloadsFrgPresenter getDownloadsFrgPresenter() {
        return ((DownloadsFrgPresenter) this.getPresenter());
    }


    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;

    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }


    @Override
    public void onStop() {
        super.onStop();
        isStarted = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isStarted) {
            loadData(); //your request method
        }

    }

    @Override
    public void loadData() {

    }

    @Override
    public void updateList() {

    }

    @Override
    public void updateListWithItem(Download download) {

    }

    @Override
    public void notifyListWithItem() {

    }

}
