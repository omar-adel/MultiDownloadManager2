package modules.basemvp;

public interface Base {
    /**
     * Main contract between the Android classes
     */

    interface AndroidActView<P extends IPresenter> {
        /* return your view's layout resource */
        int getLayoutResource();//used all and in splash where  getExtraLayout and getContainerID = 0

        int getExtraLayout();//used when need to use activity not fragment

        int getContainerID();//used when need to use fragment not activity

        /* configure everything related with your mapped ui (adapters, click listeners, etc) */
        void configureUI();

        /* build up your presenter with all its necessary dependencies in here */
        P injectDependencies();
    }

    interface AndroidFrgView<P extends IPresenter> {
        /* return your view's layout resource */
        int getLayoutResource();

        /* configure everything related with your mapped ui (adapters, click listeners, etc) */
        void configureUI();

        /* build up your presenter with all its necessary dependencies in here */
        P injectDependencies();
    }


    interface IViewAct<P extends IPresenter> extends AndroidActView<P> {
        /* getter to the view's presenter */
        P getPresenter();
    }

    interface IViewFrg<P extends IPresenter> extends AndroidFrgView<P> {
        /* getter to the view's presenter */
        P getPresenter();
    }

    interface IPresenter {


     }
}