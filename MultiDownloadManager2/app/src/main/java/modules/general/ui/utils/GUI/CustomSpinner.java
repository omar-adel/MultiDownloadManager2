package modules.general.ui.utils.GUI;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import modules.general.ui.utils.adapters.CustomSpinnerAdapter;


public class CustomSpinner<T> extends AppCompatSpinner {

    private int prevSelectedPosition = 0;

    public int getPrevSelectedPosition() {
        return prevSelectedPosition;
    }

    public void setPrevSelectedPosition(int prevSelectedPosition) {
        this.prevSelectedPosition = prevSelectedPosition;
    }

    private T selectedItem;
    ArrayList<Object> items;

    private int selectedPosition = 0;

    public CustomSpinner(Context context) {
        super(context);
        initLayout();
    }

    public CustomSpinner(Context context, int mode) {
        super(context, mode);
        initLayout();
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        initLayout();
    }



    public void initLayout()

    {
//        ArrayList<Object> arrayList = new ArrayList<>();
//        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(getContext(), arrayList,
//                R.layout.spinner_item_chosers_white_black_arrow, 0, true);
//        setAdapter(spinnerAdapter);

    }

    public void setData(final CustomSpinner customSpinner, final ArrayList<Object> arrayList
            , final SpinnerListener spinnerListener
            , int layoutId, int dropDownLayoutId, int spinnerAdapterType, boolean hideFirstItem) {
        final CustomSpinnerAdapter spinnerAdapter;

        spinnerAdapter = new CustomSpinnerAdapter(getContext(), arrayList,
                layoutId, spinnerAdapterType, hideFirstItem);

        if(customSpinner.getTag()==null)
        {
            setTag(String.valueOf(customSpinner.getId()));
        }
        spinnerAdapter.setDropDownViewResource(dropDownLayoutId);
        setSelectedListener(customSpinner, spinnerListener);


        //setAdapter(spinnerAdapter);
        //setItems(arrayList);

        ((AppCompatActivity)getContext())
                .runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter(spinnerAdapter);
                        setItems(arrayList);
                    }
                });

    }


    private void setSelectedListener(final CustomSpinner customSpinner, final SpinnerListener spinnerListener
    ) {

        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                setSelectedObject(pos);
                if (pos != prevSelectedPosition) {
                    prevSelectedPosition = pos;
                    spinnerListener.onSpinnerItemSelected(customSpinner.getTag().toString(),getItems(), pos);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }


    public void setSelectedObject(final int pos) {
        if(getItems()!=null)
        {
            setSelectedPosition(pos);
            setSelectedItem((T) getItemAtPosition(pos));

            //setSelection(selectedPosition);
            ((AppCompatActivity)getContext())
                    .runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                             setSelection(selectedPosition);
                        }
                    });
        }

    }


    public ArrayList<Object> getItems() {
        return items;
    }

    public void setItems(ArrayList<Object> items) {
        this.items = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            this.items.add(items.get(i));
        }
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public interface SpinnerListener {

         public void onSpinnerItemSelected(String tag, ArrayList<Object> items, int pos);
    }

}
