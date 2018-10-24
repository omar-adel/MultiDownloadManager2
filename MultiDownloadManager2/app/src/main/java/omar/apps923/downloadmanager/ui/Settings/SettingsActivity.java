package omar.apps923.downloadmanager.ui.Settings;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;

import butterknife.BindView;
import modules.general.model.shareddata.Prefs;
import modules.general.ui.parentview.ParentActivity;
import modules.general.utils.GeneralUtil;
import modules.prdownloader.library.PRDownloader;
import modules.prdownloader.library.PRDownloaderConfig;
import modules.prdownloader.library.internal.ComponentHolder;
import modules.prdownloader.library.internal.DownloadRequestQueue;
import modules.prdownloader.outercode.notifications.DownloadNotification;
import omar.apps923.downloadmanager.R;
import omar.apps923.downloadmanager.ui.Settings.presenter.ISettingsContract;
import omar.apps923.downloadmanager.ui.Settings.presenter.SettingsPresenter;


public class SettingsActivity extends ParentActivity<SettingsPresenter> implements
        ISettingsContract.ISettingsView {


    DialogProperties properties;
    FilePickerDialog filePickerDialog;
    GeneralUtil generalUtil;
    String[] selectedPaths;
    @BindView(R.id.txtvDirectory)
    TextView txtvDirectory;
    @BindView(R.id.llDirectory)
    LinearLayout llDirectory;
    @BindView(R.id.txtvType)
    TextView txtvType;
    @BindView(R.id.rb_parallel)
    RadioButton rbParallel;
    @BindView(R.id.rb_queue)
    RadioButton rbQueue;
    @BindView(R.id.rgType)
    RadioGroup rgType;
    @BindView(R.id.llType)
    LinearLayout llType;
    @BindView(R.id.txtvNotification)
    TextView txtvNotification;
    @BindView(R.id.switchNotification)
    Switch switchNotification;
    @BindView(R.id.llNotification)
    LinearLayout llNotification;
    @BindView(R.id.txtvClearNotification)
    TextView txtvClearNotification;


    @Override
    public int getExtraLayout() {
        return R.layout.act_settings;
    }


    @Override
    public void configureUI() {

        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().updateTitle(getString(R.string.settings));
        getCsTitle().hideMenuAndSettingsAndBack();
        generalUtil = new GeneralUtil(this);

        txtvDirectory.setText(Prefs.getString(getString(R.string.prefsDirectoryLocation),
                Environment.getExternalStorageDirectory().getAbsolutePath()));

        setType();
        setNotifications();

        llDirectory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDirectoryChooser();
                    }
                });

        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                boolean isParallel=false;
                switch (checkedId)
                {
                    case R.id.rb_parallel:
                        isParallel=true;
                        break;
                    case R.id.rb_queue:
                        isParallel=false;
                        break;
                }

                if(DownloadRequestQueue.getInstance().getCurrentRequestMap().size()==0)
                {


                    PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                            .setDatabaseEnabled(true)
                            .setParallel(isParallel)
                            .setShowNotification(ComponentHolder.getInstance().isShowNotification())
                            .build();
                    PRDownloader.initialize(SettingsActivity.this, config);
                }
                else
                {
                    if(ComponentHolder.getInstance().isParallel()!=isParallel)
                    {
                        customAlertDialog.alertDialog(getString(R.string.cancel_all_downloads_first));
                        setType();
                    }

                }

            }
        });

        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked ) {

                PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                        .setDatabaseEnabled(true)
                        .setParallel(ComponentHolder.getInstance().isParallel())
                        .setShowNotification(isChecked)
                        .build();
                PRDownloader.initialize(SettingsActivity.this, config);
                if(!isChecked)
                {
                    new DownloadNotification(SettingsActivity.this).clearAllDownloadNotifications();
                }

             }
        });

        txtvClearNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadNotification(SettingsActivity.this).clearAllDownloadNotifications();
            }
        });
    }

    private void setType() {
        if(ComponentHolder.getInstance().isParallel())
        {
            rbParallel.setChecked(true);
        }
        else
        {
            rbQueue.setChecked(true);
        }

    }


    private void setNotifications() {
        if(ComponentHolder.getInstance().isShowNotification())
        {
            switchNotification.setChecked(true);
        }
        else
        {
            switchNotification.setChecked(false);
        }

    }

    public void showDirectoryChooser() {
        initalizeProperities();
        filePickerDialog = new FilePickerDialog(generalUtil.scanForActivity(this), properties);
        filePickerDialog.setTitle(this.getString(R.string.select_directory));
        filePickerDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] paths) {
                selectedPaths = paths;
                if (selectedPaths != null) {
                    if (selectedPaths.length > 0) {
                        String path = selectedPaths[0];
                        Prefs.putString(getString(R.string.prefsDirectoryLocation), path);
                        txtvDirectory.setText(path);
                    }
                }
            }
        });
        filePickerDialog.show();
    }

    @Override
    public SettingsPresenter injectDependencies() {
        return new SettingsPresenter(this, this);
    }


    private void initalizeProperities() {
        properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.DIR_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(Prefs.getString(getString(R.string.prefsDirectoryLocation),
                Environment.getExternalStorageDirectory().getAbsolutePath()));

        properties.extensions = null;
    }

    //Add this method to show Dialog when the required permission has been granted to the app.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (filePickerDialog != null) {   //Show dialog if the read permission has been granted.
                        filePickerDialog.show();
                    }
                } else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(this, "Permission is Required for getting list of files",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
