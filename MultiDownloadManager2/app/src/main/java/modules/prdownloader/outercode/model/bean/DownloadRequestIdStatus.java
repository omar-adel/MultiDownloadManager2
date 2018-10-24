package modules.prdownloader.outercode.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import modules.prdownloader.library.Status;

/**
 * Created by Net22 on 12/17/2017.
 */

public class DownloadRequestIdStatus implements Parcelable {
    private String downloadId;
    private Status status;

    public DownloadRequestIdStatus() {

    }

    protected DownloadRequestIdStatus(Parcel in) {
        downloadId = in.readString();
    }

    public static final Creator<DownloadRequestIdStatus> CREATOR = new Creator<DownloadRequestIdStatus>() {
        @Override
        public DownloadRequestIdStatus createFromParcel(Parcel in) {
            return new DownloadRequestIdStatus(in);
        }

        @Override
        public DownloadRequestIdStatus[] newArray(int size) {
            return new DownloadRequestIdStatus[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(downloadId);
    }

    public String getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
