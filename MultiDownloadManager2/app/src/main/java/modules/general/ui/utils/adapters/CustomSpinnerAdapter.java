package modules.general.ui.utils.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Net22 on 9/25/2017.
 */

public class CustomSpinnerAdapter extends ArrayAdapter {

    public int spinnerAdapterType = 0;
    public Context context;
    ArrayList<Object> objects = new ArrayList<>();

    boolean hideFirstItem;

    public CustomSpinnerAdapter(Context context,
                                ArrayList<Object> objects, int layoutId
            , int spinnerAdapterType, boolean hideFirstItem
    ) {
        super(context, layoutId, objects);
        this.hideFirstItem = hideFirstItem;
        this.context = context;
        this.spinnerAdapterType = spinnerAdapterType;
        this.objects.clear();
        for (int i = 0; i < objects.size(); i++) {
            this.objects.add(objects.get(i));
        }


    }

    // It gets a View that displays in the drop down popup the data at the specified position
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomDropDownView(position);
    }

    // It gets a View that displays the data at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent)

    {
        return getCustomView(position);
    }


    private View getCustomView(int position) {
//        switch (spinnerAdapterType) {
//            case 0:
//                return new CategoryRestApiSPView(context, objects.get(position), 0);
//            case 1:
//                return new CategorySqliteSPView(context, objects.get(position), 0);
//            default:
//                break;
//        }

        return null;
    }

    public View getCustomDropDownView(int position) {

        View view = null;

//        switch (spinnerAdapterType) {
//            case 0:
//                view = new CategoryRestApiSPView(this.context, objects.get(position), 1);
//                view = setUpZeroHeightView(view, position);
//                break;
//            case 1:
//                view = new CategorySqliteSPView(this.context, objects.get(position), 1);
//                view = setUpZeroHeightView(view, position);
//                break;
//
//            default:
//                break;
//        }

        return view;
    }

    private View createZeroHeightView() {
        TextView tv = new TextView(getContext());
        tv.setVisibility(View.GONE);
        tv.setHeight(0);

        return tv;
    }


    public View setUpZeroHeightView(View view, int position) {
        if (hideFirstItem) {
            if (position == 0) {
                view = createZeroHeightView();
                view.setVisibility(View.GONE);
            }
        }

        return view;
    }
}



