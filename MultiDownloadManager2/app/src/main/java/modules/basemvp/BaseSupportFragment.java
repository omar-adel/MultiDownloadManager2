package modules.basemvp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseSupportFragment<P extends Base.IPresenter>
        extends Fragment
        implements Base.IViewFrg<P> {
    public P mPresenter;
    private View view;

    public FragmentActivity mActivity;

    public FragmentActivity getContainerActivity()
    {
        //return getActivity();
        return mActivity;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
         if (context instanceof FragmentActivity){
            mActivity=(FragmentActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setRetainInstance(false);
        setView(inflater.inflate(getLayoutResource(), container, false));
        ButterKnife.bind(this, getView());
        mPresenter = injectDependencies();

        if (getPresenter() == null) {
            throw new IllegalArgumentException("You must inject the " +
                    "dependencies before retrieving the presenter");
        } else {

        }

        return getView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         configureUI();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
     }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @android.support.annotation.Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }


}