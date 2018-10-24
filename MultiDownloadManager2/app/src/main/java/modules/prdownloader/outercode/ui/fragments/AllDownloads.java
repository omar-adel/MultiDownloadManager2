package modules.prdownloader.outercode.ui.fragments;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.BindView;
import modules.general.ui.utils.GUI.RecyclerViewEmptySupport;
import modules.general.ui.utils.adapters.CustomRecyclerViewAdapter;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import modules.prdownloader.outercode.ui.fragments.presenters.DownloadsFrgPresenter;
import modules.prdownloader.outercode.ui.fragments.presenters.IDownloadsFrgContract;
import omar.apps923.downloadmanager.R;

import static modules.general.ui.utils.adapters.CustomRecyclerViewAdapter.AllDownloadsVHType;


public class AllDownloads extends BaseDownloadsFragment<DownloadsFrgPresenter> implements
        IDownloadsFrgContract.IDownloadsFrgView

{

    @BindView(R.id.rv)
    RecyclerViewEmptySupport rv;
    @BindView(R.id.txtvEmpty)
    TextView txtvEmpty;


    @Override
    public void loadData() {
        getDownloadsFrgPresenter().loadData(this.getClass().getName());
    }

    @Override
    public void updateList() {
        rv.setAdapter(downloadsAdapter);
    }

    @Override
    public void updateListWithItem(Download download) {

        getDownloadsFrgPresenter().updateListWithItem(this.getClass().getName(), download);

    }


    @Override
    public void notifyListWithItem() {
        getDownloadsFrgPresenter().notifyList();
     }


    @Override
    public int getLayoutResource() {
        return R.layout.download_frg_downloads;
    }

    @Override
    public void configureUI() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContainerActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);

        downloadsAdapter = new CustomRecyclerViewAdapter<Download>(getContainerActivity(), AllDownloadsVHType);
        rv.setEmptyView(txtvEmpty);

        rv.setAdapter(downloadsAdapter);

        rv.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContainerActivity())
                        .color(Color.GRAY)
                        .sizeResId(R.dimen.download_divider)
                        .build());

    }



}
