package modules.prdownloader.outercode.ui.ViewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import modules.general.ui.utils.adapters.CustomRecyclerViewAdapter;
import modules.general.utils.NotificationUtils;
import modules.prdownloader.library.PRDownloader;
import modules.prdownloader.outercode.model.DataManager;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import omar.apps923.downloadmanager.R;


/**
 * Created by Net22 on 9/14/2017.
 */

public class CompletedDownloadsVH extends RecyclerView.ViewHolder {

    View itemView;
     Context context;
    CustomRecyclerViewAdapter customRecyclerViewAdapter;
    @BindView(R.id.txtvName)
    TextView txtvName;
    @BindView(R.id.txtvProgress)
    TextView txtvProgress;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rlInfo)
    RelativeLayout rlInfo;
    @BindView(R.id.imgvPauseResume)
    ImageView imgvPauseResume;
    @BindView(R.id.imgvCancel)
    ImageView imgvCancel;

    NotificationUtils mNotificationUtils ;

    public CompletedDownloadsVH(Context context, View itemView , CustomRecyclerViewAdapter customRecyclerViewAdapter) {
        super(itemView);
        this.context = context;
        this.customRecyclerViewAdapter = customRecyclerViewAdapter;
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
        mNotificationUtils=new NotificationUtils(this.context);
     }

    public void bindData(final Object item, final int position) {

        final Download download = (Download) item;
            txtvName.setText(download.getFileName());
            imgvPauseResume.setVisibility(View.GONE);
            imgvCancel.setVisibility(View.VISIBLE);

            progressBar.setProgress(download.getProgress());
            txtvProgress.setText(download.getStatusText() + ": " + download.getProgress() + "%");


            imgvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    removeDownload(position);
                    cancelDownload(download);

                }
            });

    }

    public static View getView(Context context, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.download_row_download, viewGroup, false);
    }


    private void removeDownload(int position) {
        customRecyclerViewAdapter.removeItem(position);
    }




    public void cancelDownload(Download download)
    {

        deleteDownload(download);


        if(download.getProgress() < 100)
        {
            PRDownloader.cancel(download.getDownloadId());
        }

    }

    private void deleteDownload(Download download) {

//        SQLite.delete().from(Download.class)
//                .where(Download_Table.downloadId.eq(download.getDownloadId()))
//                .execute();

        download.delete();
        mNotificationUtils.getManager().cancel(
                Integer.valueOf(download.getDownloadId()) + 1000
        );

    }

}
