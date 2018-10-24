package omar.apps923.downloadmanager.ui.License.presenter;

import android.content.Context;

/**
 * Created by Net22 on 12/17/2017.
 */

public class LicensePresenter implements ILicenseContract.ILicensePresenter

{

    private final Context mContext;
    ILicenseContract.ILicenseView mView;

    public LicensePresenter(Context context, ILicenseContract.ILicenseView view) {
        mView = view;
        mContext = context;
    }

}
