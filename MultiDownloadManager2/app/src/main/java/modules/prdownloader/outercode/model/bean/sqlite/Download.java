package modules.prdownloader.outercode.model.bean.sqlite;


import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

import modules.general.model.bean.sqlite.BaseModelWithIData;
import modules.general.model.db.dbFlowDatabases.DatabaseModule;
import modules.prdownloader.library.Status;


@Table(database = DatabaseModule.class)
public class Download extends BaseModelWithIData
        implements Parcelable
{
    @Column
    @PrimaryKey(autoincrement = true)
    private Long id = 0l;

    @Column
    private String downloadId;
    @Column
    public String url;
    @Column
    private String dirPath;
    @Column
    private String fileName;
    @Column
    private int progress;
    @Column
    private Status status;
    @Column
    private String directoryPath;
    @Column
    private long downloadedBytes;
    @Column
    private long totalBytes;

    public Download() {
    }


    public Download(String downloadId ,

                    String url, String dirPath, String fileName, int progress,
                    Status status, String directoryPath, long downloadedBytes, long totalBytes) {
        this.downloadId = downloadId;
        this.url = url;
        this.dirPath = dirPath;
        this.fileName = fileName;
        this.progress = progress;
        this.status = status;
        this.directoryPath = directoryPath;
        this.downloadedBytes = downloadedBytes;
        this.totalBytes = totalBytes;
    }


    protected Download(Parcel in) {
        downloadId = in.readString();
        url = in.readString();
        dirPath = in.readString();
        fileName = in.readString();
        progress = in.readInt();
        directoryPath = in.readString();
        downloadedBytes = in.readLong();
        totalBytes = in.readLong();
    }

    public static final Creator<Download> CREATOR = new Creator<Download>() {
        @Override
        public Download createFromParcel(Parcel in) {
            return new Download(in);
        }

        @Override
        public Download[] newArray(int size) {
            return new Download[size];
        }
    };


    public Long getId() {
        return id;
    }

    public void setId(long id) {
     this.id=id;
    }

    public String getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public long getDownloadedBytes() {
        return downloadedBytes;
    }

    public void setDownloadedBytes(long downloadedBytes) {
        this.downloadedBytes = downloadedBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(downloadId);
        parcel.writeString(url);
        parcel.writeString(dirPath);
        parcel.writeString(fileName);
        parcel.writeInt(progress);
        parcel.writeString(directoryPath);
        parcel.writeLong(downloadedBytes);
        parcel.writeLong(totalBytes);
    }

    public String getStatusText() {
        switch (status) {
            case UNKNOWN:
                return "Not Downloaded";
            case QUEUED:
                return "Connecting";
            case RUNNING:
                return "Downloading";
            case PAUSED:
                return "Paused";
            case CANCELLED:
                return "Cancelled";
                case ERROR:
                return "Download Error";
            case COMPLETED:
                return "Completed";
            default:
                return "Not Downloaded";
        }
    }

    @Override
    public void insertData(Object object ) {
        ((Download)object).insert();
    }

    @Override
    public void updateData(  Object object  ) {

        ((Download)object).update();
    }

    @Override
    public void saveData(Object object ) {
        ((Download)object).save();
    }

    @Override
    public void deleteData(  Object object  ) {
        ((Download)object).delete();
    }

    @Override
    public void deleteAll(     ) {

        SQLite.delete().from(Download.class)
                .execute();
     }

    @Override
    public Object getItemByID( int id ) {

        Download download = SQLite.select()
                .from(Download.class)
                .where(Download_Table.id.eq(Long.valueOf(id)))
                .querySingle();

        return download;
    }

    @Override
    public ArrayList getAll(  ) {

        ArrayList<Download> downloadArrayList = (ArrayList<Download>) SQLite.select().from(Download.class)
                .queryList();
        return downloadArrayList ;

    }
}
