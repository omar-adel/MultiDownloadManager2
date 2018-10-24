package omar.apps923.downloadmanager.ui.Main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import modules.general.ui.parentview.ParentActivity;
import modules.prdownloader.library.Error;
import modules.prdownloader.library.OnDownloadListeners;
import modules.prdownloader.library.Progress;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import modules.prdownloader.outercode.ui.activities.DownloadsActivity;
import omar.apps923.downloadmanager.R;
import omar.apps923.downloadmanager.ui.License.LicenseActivity;
import omar.apps923.downloadmanager.ui.Main.presenter.IMainContract;
import omar.apps923.downloadmanager.ui.Main.presenter.MainPresenter;
import omar.apps923.downloadmanager.ui.Settings.SettingsActivity;

public class MainActivity extends ParentActivity<MainPresenter>
        implements IMainContract.IMainView, PopupMenu.OnMenuItemClickListener
    , OnDownloadListeners

{


    String urlDownload;


    @BindView(R.id.edUrl)
    EditText edUrl;
    @BindView(R.id.btnDownload)
    Button btnDownload;
    @BindView(R.id.rlDownload)
    RelativeLayout rlDownload;
    @BindView(R.id.btnDownloads)
     Button btnDownloads;

    public final static int PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE=58;

    public String getUrlDownload() {
        return urlDownload;
    }

    public void setUrlDownload(String urlDownload) {
        this.urlDownload = urlDownload;
    }

    @Override
    public int getExtraLayout() {
        return R.layout.act_main;
    }


    @Override
    public void configureUI() {

        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().updateTitle(getString(R.string.app_name));
        getCsTitle().hideMenuAndBack();
        getCsTitle().showSettings();
        getCsTitle().getBtnSettings().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.setOnMenuItemClickListener(MainActivity.this);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
            }
        });


        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                urlDownload = edUrl.getText().toString();

                if (!urlDownload.isEmpty()) {
                    if (URLUtil.isValidUrl(urlDownload)) {
                        addDownload();
                    } else {
                        customAlertDialog.alertDialog(getString(R.string.invalidLINK));
                    }
                } else {
                    customAlertDialog.alertDialog(getString(R.string.enterLinkWarn));

                }


            }
        });


        btnDownloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this, DownloadsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public MainPresenter injectDependencies() {
        return new MainPresenter(this, this);
    }

    public MainPresenter getMainPresenter(){
        return ((MainPresenter)getPresenter());
    }
    public void addDownload() {
        getMainPresenter().addDownload();

    }




    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSettings:
                Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                return true;
            case R.id.itemLicense:
                Intent intentLicense = new Intent(MainActivity.this, LicenseActivity.class);
                startActivity(intentLicense);
                return true;
            default:
                return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                try {
                    addDownload();
                } catch (Exception e) {
                    Log.e("ExceptionDownload", e.toString());
                }

            }

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateDownloadItem(Download download) {

        if (download.getProgress() >= 100)

        {
            Toast.makeText(getApplicationContext(), getString(R.string.downloadOf)
                    + " " + download.getFileName() + " " + getString(R.string.completed), Toast.LENGTH_LONG).show();

        }

    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
