package omar.apps923.downloadmanager.ui.License;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import modules.general.ui.parentview.ParentActivity;
import omar.apps923.downloadmanager.R;
import omar.apps923.downloadmanager.ui.License.presenter.ILicenseContract;
import omar.apps923.downloadmanager.ui.License.presenter.LicensePresenter;


public class LicenseActivity extends ParentActivity<LicensePresenter>
        implements ILicenseContract.ILicenseView {


    @BindView(R.id.txtvLicense1)
    TextView txtvLicense1;
    @BindView(R.id.txtvLicense2)
     TextView txtvLicense2;
    @BindView(R.id.txtvLicense3)
    TextView txtvLicense3;
    @BindView(R.id.txtvLicense4)
    TextView txtvLicense4;
    @BindView(R.id.txtvLicense5)
    TextView txtvLicense5;
    @BindView(R.id.txtvLicense6)
    TextView txtvLicense6;

    @Override
    public int getExtraLayout() {
        return R.layout.act_license;
    }


    @Override
    public void configureUI() {

        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().updateTitle(getString(R.string.license));
        getCsTitle().hideMenuAndSettingsAndBack();


        txtvLicense2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Uri uri = Uri.parse(getString(R.string.licenseText2));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    customAlertDialog.alertDialog(getString(R.string.notfindappsuitable));
                }

            }
        });

        txtvLicense5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Uri uri = Uri.parse(getString(R.string.licenseText5));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    customAlertDialog.alertDialog(getString(R.string.notfindappsuitable));
                }

            }
        });

    }

    @Override
    public LicensePresenter injectDependencies() {
        return new LicensePresenter(this, this);
    }


}
