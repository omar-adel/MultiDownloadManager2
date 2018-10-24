package modules.prdownloader.outercode.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import modules.general.ui.parentview.ParentActivity;
import modules.prdownloader.outercode.model.bean.sqlite.Download;
import modules.prdownloader.outercode.ui.activities.presenters.DownloadsActPresenter;
import modules.prdownloader.outercode.ui.activities.presenters.IDownloadsActContract;
import modules.prdownloader.outercode.ui.adapters.DownloadsViewPagerAdapter;
import modules.prdownloader.outercode.ui.fragments.BaseDownloadsFragment;
import omar.apps923.downloadmanager.R;

public class DownloadsActivity extends ParentActivity<DownloadsActPresenter>
        implements IDownloadsActContract.IDownloadsActView {

    CharSequence Titles[] = new CharSequence[3];

    DownloadsViewPagerAdapter downloadsViewPagerAdapter;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.txtvStartAll)
    TextView txtvStartAll;
    @BindView(R.id.txtvCancelAll)
    TextView txtvCancelAll;
    @BindView(R.id.txtvRemoveAll)
    TextView txtvRemoveAll;
    @BindView(R.id.llActions)
    LinearLayout llActions;

    @Override
    public int getExtraLayout() {
        return R.layout.download_act_downloads;
    }



    @Override
    public void configureUI() {
        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().updateTitle(getString(R.string.download_downloads));
        getCsTitle().hideMenuAndBack();

        Titles[0] = getString(R.string.download_allDownloads);
        Titles[1] = getString(R.string.download_pausedDownloads);
        Titles[2] = getString(R.string.download_completedDownloads);

        downloadsViewPagerAdapter = new DownloadsViewPagerAdapter(
                getSupportFragmentManager(), Titles);

        viewpager.setOffscreenPageLimit(1);
        viewpager.setAdapter(downloadsViewPagerAdapter);

        viewpagertab.setViewPager(viewpager);

        txtvStartAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                resumeAll();

            }
        });


        txtvCancelAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                cancelAll();
            }
        });


        txtvRemoveAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                removeAll();

            }
        });


        viewpagertab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                BaseDownloadsFragment currentFragment =
                        (BaseDownloadsFragment) getCurrentFragmentInPager();
                  if (currentFragment != null) {
                    currentFragment.onResume();
                }
             }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void resumeAll() {

        getDownloadsActPresenter().resumeAll();


    }


    private void cancelAll() {
        getDownloadsActPresenter().cancelAll();

    }

    private void removeAll()

    {
        getDownloadsActPresenter().removeAll();


    }


    public DownloadsActPresenter getDownloadsActPresenter()
    {
        return ((DownloadsActPresenter)getPresenter());
    }
    @Override
    public DownloadsActPresenter injectDependencies() {
        return new DownloadsActPresenter(this, this);
    }



    public Fragment getCurrentFragmentInPager() {

       return downloadsViewPagerAdapter.getCurrentFragment();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateDownloadItem(Download download) {
        getDownloadsActPresenter().updateDownloadItem(download);

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



}
