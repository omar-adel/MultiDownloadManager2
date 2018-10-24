package modules.prdownloader.outercode.ui.fragments.presenters;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modules.general.model.db.SqliteCallBack;
import modules.prdownloader.library.Status;
import modules.prdownloader.outercode.model.DataManager;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import modules.prdownloader.outercode.ui.activities.DownloadsActivity;
import modules.prdownloader.outercode.ui.fragments.AllDownloads;
import modules.prdownloader.outercode.ui.fragments.BaseDownloadsFragment;
import modules.prdownloader.outercode.ui.fragments.CompletedDownloads;
import modules.prdownloader.outercode.ui.fragments.PausedDownloads;

import static modules.prdownloader.outercode.utils.Util.checkIfDownloadInDatabase;

/**
 * Created by Net22 on 12/14/2017.
 */

public class DownloadsFrgPresenter extends SqliteCallBack implements IDownloadsFrgContract.IDownloadsFrgPresenter
         {

    private Context mContext;
    IDownloadsFrgContract.IDownloadsFrgView mView;
    private DataManager mDataManager;

    public DownloadsFrgPresenter()
    {

    }
    public DownloadsFrgPresenter(Context context, IDownloadsFrgContract.IDownloadsFrgView view) {
        mView = view;
        mContext = context;
        mDataManager = DataManager.getInstance(mContext);
        mDataManager.setPresenterSqliteCallBack(this);
    }


    @Override
    public void loadData(String source) {

        ((BaseDownloadsFragment) mView).getDownloadsAdapter().clearData();
        if (source.equals(AllDownloads.class.getName())) {
            ArrayList<Download> downloadArrayList  =
                    (ArrayList<Download>) mDataManager.getAll(new Download());
            if(((DownloadsActivity)mContext).getCurrentFragmentInPager() instanceof AllDownloads)
            {
                setData(downloadArrayList);
            }
         } else if (source.equals(PausedDownloads.class.getName())) {

            Download download1=new Download();
            HashMap hashMap = new HashMap<String, String>();
            hashMap.put("status", Status.PAUSED);
            download1.setItemWithCustomData(hashMap);

            ArrayList<Download>downloadArrayList = (ArrayList<Download>)
                    mDataManager
                    .getItemsArrWithCustomData(
                            download1);

            if(((DownloadsActivity)mContext).getCurrentFragmentInPager() instanceof PausedDownloads)
            {
                setData(downloadArrayList);
            }

         } else if (source.equals(CompletedDownloads.class.getName())) {
            Download download1=new Download();
            HashMap hashMap = new HashMap<String, String>();
            hashMap.put("status", Status.COMPLETED);
            download1.setItemWithCustomData(hashMap);

            ArrayList<Download>downloadArrayList = (ArrayList<Download>)
                    mDataManager
                            .getItemsArrWithCustomData(
                                    download1);

            if(((DownloadsActivity)mContext).getCurrentFragmentInPager() instanceof CompletedDownloads)
            {
                setData(downloadArrayList);

            }

        }

    }

    @Override
    public void updateListWithItem(String source , Download download) {


        if (source.equals(AllDownloads.class.getName())) {

            boolean found = false;
            for (int i = 0; i < ((BaseDownloadsFragment) mView).getDownloadsAdapter().getItemCount(); i++) {

                if (download.getDownloadId().equals (((Download)
                        ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().get(i))
                        .getDownloadId())) {
                    found = true;

                    if(checkIfDownloadInDatabase(download,mDataManager)) {
                        ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().set(i, download);
                    }
                    else
                    {
                        ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().remove(i);
                    }

                    break;
                }

            }
            if (!found) {
                if (download.getStatus() != Status.CANCELLED) {
                    if (((BaseDownloadsFragment) mView).getDownloadsAdapter().getItemCount() > 0) {
                        ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().add(
                                ((BaseDownloadsFragment) mView).getDownloadsAdapter().getItemCount() - 1,
                                download);

                    } else {
                        ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().add(0, download);

                    }
                }

            }

        } else if (source.equals(PausedDownloads.class.getName())) {

            boolean found = false;
            for (int i = 0; i < ((BaseDownloadsFragment) mView).getDownloadsAdapter().getItemCount(); i++) {

                if (download.getDownloadId().equals(((Download) ((BaseDownloadsFragment) mView).getDownloadsAdapter()
                        .getList().get(i)).getDownloadId())) {
                    found = true;

                    if(checkIfDownloadInDatabase(download,mDataManager)) {
                        if (download.getStatus() != Status.PAUSED) {

                            ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().remove(i);
                        }
                        else {
                            if (download.getStatus() == Status.PAUSED) {

                                ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().set(i, download);
                            }
                        }
                    }
                    else
                    {
                        Log.e("cdscds","v dsddd"+ ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList()
                                .size());
                        ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().remove(i);
                        Log.e("cdscds","v ds"+ ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList()
                        .size());
                    }

                    break;
                }

            }
            if (!found) {
                if (download.getStatus() == Status.PAUSED) {

                    if(((BaseDownloadsFragment) mView).getDownloadsAdapter().getItemCount()>0)
                    {
                        ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().add(
                                ((BaseDownloadsFragment) mView).getDownloadsAdapter().getItemCount()-1, download);

                    }
                    else
                    {
                        ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().add(0, download);

                    }
                }

            }
        } else if (source.equals(CompletedDownloads.class.getName())) {

            boolean found = false;
            for (int i = 0; i < ((BaseDownloadsFragment) mView).getDownloadsAdapter().getItemCount(); i++) {

                if (download.getDownloadId().equals(
                        ((Download) ((BaseDownloadsFragment) mView).getDownloadsAdapter().
                        getList().get(i)).getDownloadId())
                        ) {
                    found = true;

                    if(checkIfDownloadInDatabase(download,mDataManager)) {
                        if (download.getStatus() != Status.COMPLETED) {

                            ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().remove(i);
                        }
                        else {
                            if (download.getStatus() == Status.COMPLETED) {

                                ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().set(i, download);
                            }

                        }
                    }
                    else
                    {
                        ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().remove(i);
                    }

                    break;
                }

            }
            if (!found) {
                if (download.getStatus() == Status.COMPLETED) {

                    if(((BaseDownloadsFragment) mView).getDownloadsAdapter().getItemCount()>0)
                    {
                        ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().add(
                                ((BaseDownloadsFragment) mView).getDownloadsAdapter().getItemCount()-1, download);

                    }
                    else
                    {
                        ((BaseDownloadsFragment) mView).getDownloadsAdapter().getList().add(0, download);
                    }
                }

            }

        }

        ((BaseDownloadsFragment) mView).notifyListWithItem();

    }

    @Override
    public void notifyList() {
        ((BaseDownloadsFragment) mView).getDownloadsAdapter().notifyDataSetChanged();
    }






    private void setData(List data) {
        ((BaseDownloadsFragment) mView).getDownloadsAdapter().clearData();
        for (int i = 0; i < data.size(); i++)

        {
            ((BaseDownloadsFragment) mView).getDownloadsAdapter().addItem(i, data.get(i));
        }
        ((BaseDownloadsFragment) mView).updateList();
    }


}
