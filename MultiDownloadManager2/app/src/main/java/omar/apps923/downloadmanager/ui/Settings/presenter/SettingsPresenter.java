package omar.apps923.downloadmanager.ui.Settings.presenter;

import android.content.Context;

/**
 * Created by Net22 on 12/17/2017.
 */

public class SettingsPresenter implements ISettingsContract.ISettingsPresenter

{

    private final Context mContext;
    ISettingsContract.ISettingsView mView;

    public SettingsPresenter(Context context, ISettingsContract.ISettingsView view) {
        mView = view;
        mContext = context;
    }


}
