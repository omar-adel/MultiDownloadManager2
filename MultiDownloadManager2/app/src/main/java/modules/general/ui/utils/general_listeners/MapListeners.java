package modules.general.ui.utils.general_listeners;

import android.location.Location;

/**
 * Created by Net22 on 11/13/2017.
 */

public interface MapListeners {

    public void prepareMapAddress();
    public void gotLocationChanged(Location newLocation);
    public void doWork();
    public void mapLocAddressAtPoint();


}
