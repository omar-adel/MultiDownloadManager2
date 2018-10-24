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
import modules.prdownloader.library.Error;
import modules.prdownloader.library.OnDownloadListeners;
import modules.prdownloader.library.PRDownloader;
import modules.prdownloader.library.Progress;
import modules.prdownloader.outercode.model.DataManager;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import omar.apps923.downloadmanager.R;


/**
 * Created by Net22 on 9/14/2017.
 */

public class PausedDownloadsVH extends RecyclerView.ViewHolder implements OnDownloadListeners {

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

    NotificationUtils mNotificationUtils;

    public PausedDownloadsVH(Context context, View itemView, CustomRecyclerViewAdapter customRecyclerViewAdapter) {
        super(itemView);
        this.context = context;
        this.customRecyclerViewAdapter = customRecyclerViewAdapter;
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
        mNotificationUtils = new NotificationUtils(this.context);
     }

    public void bindData(final Object item, final int position) {

        final Download download = (Download) item;

            txtvName.setText(download.getFileName());
            imgvPauseResume.setVisibility(View.VISIBLE);
            imgvCancel.setVisibility(View.VISIBLE);
            imgvPauseResume.setBackgroundResource(R.drawable.download_ic_play_circle_outline_black_36dp);
            progressBar.setProgress(download.getProgress());
            txtvProgress.setText(download.getStatusText() + ": " + download.getProgress() + "%");
            imgvPauseResume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgvPauseResume.setBackgroundResource(R.drawable.download_ic_pause_circle_outline_black_36dp);
                    resumeDownload(download);
                }
            });
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


    public void resumeDownload(Download download) {
        if(download.getDownloadId()!=null)
        {
            PRDownloader.resume(download.getDownloadId());
        }
        else
        {
            String downloadId = PRDownloader.download(context,download.getUrl(),
                    download.getDirPath(), download.getFileName())
                    .build()
                    .start(this);
            download.setDownloadId(downloadId);
            download.update();
        }
    }

    public void cancelDownload(Download download) {

        deleteDownload(download);

        if (download.getProgress() < 100) {
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



    @Override
    public void onStartOrResume(Download download) {

    }

    @Override
    public void onPause(Download download) {

    }

    @Override
    public void onCancel(Download download) {

    }

    @Override
    public void onProgress(String downloadId, String url, Progress progress) {

    }

    @Override
    public void onDownloadComplete(Download download) {

    }

    @Override
    public void onError(Download download, Error error) {

    }

}
