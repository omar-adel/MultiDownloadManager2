package modules.general.ui.parentview;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.LinearLayout;

import butterknife.BindView;
import modules.basemvp.Base;
import modules.basemvp.BaseAppCompatActivity;
import modules.basemvp.BaseSupportFragment;
import modules.general.ui.custom_views.CustomTitle;
import modules.general.ui.utils.GUI.CustomAlertDialog;
import modules.general.utils.KeyBoardUtil;
import omar.apps923.downloadmanager.R;
public class ParentActivity<P extends Base.IPresenter> extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , CustomAlertDialog.CustomAlertDialogInterface {
    @BindView(R.id.parentview_cs_title)
    CustomTitle csTitle;
    @BindView(R.id.parentview_lnr_content)
    LinearLayout lnrContent;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    public CustomAlertDialog customAlertDialog ;

    public CustomAlertDialog getCustomAlertDialog() {
        return customAlertDialog;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    @Override
    public void setTheme(int resid) {
        super.setTheme(resid);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.parent_view;
    }

    @Override
    public int getExtraLayout() {
        return 0;
    }


    @Override
    public int getContainerID() {
        return R.id.parentview_lnr_content;
    }

    @Override
    public void configureUI() {

        customAlertDialog=new CustomAlertDialog(this,this);
    }

    public void onMenuPress() {
        if (!drawer.isDrawerOpen(GravityCompat.END)) {
            KeyBoardUtil.hideSoftKeyboard(this);
            drawer.openDrawer(GravityCompat.END);
        } else
            closeDrawer();
    }
    public void onBack() {
        finish();
    }
    @Override
    public Base.IPresenter injectDependencies() {
        return null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void openDrawer() {
        if (!drawer.isDrawerOpen(GravityCompat.END)) {
            KeyBoardUtil.hideSoftKeyboard(this);
            drawer.openDrawer(GravityCompat.END);
        }
    }

    public void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.END))
            drawer.closeDrawer(GravityCompat.END);
    }

    public LinearLayout getContentView() {
        return lnrContent;
    }

    public int getContentRes() {
        return R.id.parentview_lnr_content;
    }

    public CustomTitle getCsTitle() {
        return csTitle;
    }

    public void disableDrawerSwipe()
    {
         drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeDrawer();
        finish();
    }

    @Override
    public void onPositiveButtonClicked() {

    }

    public BaseSupportFragment getCurrentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        BaseSupportFragment fragment = (BaseSupportFragment)
                fm.findFragmentById(getContainerID());
        return fragment;
    }

}