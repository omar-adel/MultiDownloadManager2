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

public class AllDownloadsVH extends RecyclerView.ViewHolder implements OnDownloadListeners {

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

    public AllDownloadsVH(Context context, View itemView, CustomRecyclerViewAdapter customRecyclerViewAdapter) {
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

            switch (download.getStatus()) {
                case QUEUED:
                    imgvPauseResume.setVisibility(View.GONE);
                    imgvCancel.setVisibility(View.VISIBLE);
                    break;

                case RUNNING:

                    imgvPauseResume.setVisibility(View.VISIBLE);
                    imgvCancel.setVisibility(View.VISIBLE);

                    imgvPauseResume.setBackgroundResource(R.drawable.download_ic_pause_circle_outline_black_36dp);

                    break;
                case COMPLETED:
                    imgvPauseResume.setVisibility(View.GONE);
                    imgvCancel.setVisibility(View.VISIBLE);
                    break;


                case PAUSED:
                    imgvPauseResume.setVisibility(View.VISIBLE);
                    imgvCancel.setVisibility(View.VISIBLE);

                    imgvPauseResume.setBackgroundResource(R.drawable.download_ic_play_circle_outline_black_36dp);

                    break;

                case UNKNOWN:
                    imgvPauseResume.setVisibility(View.VISIBLE);
                    imgvCancel.setVisibility(View.VISIBLE);

                    imgvPauseResume.setBackgroundResource(R.drawable.download_ic_play_circle_outline_black_36dp);

                    break;
                case ERROR:
                    imgvPauseResume.setVisibility(View.VISIBLE);
                    imgvCancel.setVisibility(View.VISIBLE);

                    imgvPauseResume.setBackgroundResource(R.drawable.download_ic_play_circle_outline_black_36dp);

                    break;

                default:
                    imgvPauseResume.setVisibility(View.VISIBLE);
                    imgvCancel.setVisibility(View.VISIBLE);
                    imgvPauseResume.setBackgroundResource(R.drawable.download_ic_play_circle_outline_black_36dp);

                    break;
            }

            progressBar.setProgress(download.getProgress());
            txtvProgress.setText(download.getStatusText() + ": " + download.getProgress() + "%");


            imgvPauseResume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (download.getStatus()) {

                        case RUNNING:

                            imgvPauseResume.setBackgroundResource(R.drawable.download_ic_play_circle_outline_black_36dp);
                            pauseDownload(download);

                            break;
                        case PAUSED:

                            imgvPauseResume.setBackgroundResource(R.drawable.download_ic_pause_circle_outline_black_36dp);
                            resumeDownload(download);

                            break;

                        case UNKNOWN:

                            imgvPauseResume.setBackgroundResource(R.drawable.download_ic_play_circle_outline_black_36dp);
                            startDownload(download);

                            break;
                        case ERROR:

                            imgvPauseResume.setBackgroundResource(R.drawable.download_ic_play_circle_outline_black_36dp);
                            startDownload(download);

                            break;

                        default:

                            imgvPauseResume.setBackgroundResource(R.drawable.download_ic_play_circle_outline_black_36dp);
                            startDownload(download);

                            break;
                    }


                }
            });

            imgvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (download.getStatus()) {


                        case RUNNING:

                            removeDownload(position);
                            cancelDownload(download);

                            break;


                        case PAUSED:
                            removeDownload(position);
                            cancelDownload(download);

                            break;

                        case COMPLETED:

                            removeDownload(position);
                            cancelDownload(download);

                            break;


                        case UNKNOWN:

                            removeDownload(position);
                            deleteDownload(download);

                            break;
                        case ERROR:

                            removeDownload(position);
                            deleteDownload(download);

                            break;

                        default:
                            removeDownload(position);
                            cancelDownload(download);

                            break;
                    }


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


    public void pauseDownload(Download download) {

        PRDownloader.pause(download.getDownloadId());
    }

    public void resumeDownload(Download download) {
        PRDownloader.resume(download.getDownloadId());
    }

    public void startDownload(Download download) {
        String downloadId = PRDownloader.download(context, download.getUrl(),
                download.getDirPath(), download.getFileName())
                .build()
                .start(this);
        download.setDownloadId(downloadId);
        download.save();
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
        if (download.getDownloadId() != null) {
            mNotificationUtils.getManager().cancel(
                    Integer.valueOf(download.getDownloadId()) + 1000
            );
        }


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
