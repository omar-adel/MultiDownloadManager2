package modules.general.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * Created by Net22 on 12/18/2017.
 */

public class GeneralUtil {

    private Context context;

    public GeneralUtil(   ) {
    }

    public GeneralUtil(Context context) {
        this.context = context;
    }

    public Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());
        return null;
    }


}
