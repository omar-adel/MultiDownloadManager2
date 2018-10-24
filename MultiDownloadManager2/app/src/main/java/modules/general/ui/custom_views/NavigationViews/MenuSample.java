package modules.general.ui.custom_views.NavigationViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import omar.apps923.downloadmanager.R;

/**
 * Created by Net22 on 9/14/2017.
 */

public class MenuSample extends LinearLayout {

    @BindView(R.id.btn1) Button btn1;
    @BindView(R.id.btn2) Button btn2;
     public MenuSample(Context context) {
        super(context);
        initView(context);
        doWork();
    }



    public MenuSample(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        doWork();
    }

    public MenuSample(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        doWork();

    }
    private void initView(Context context) {
         LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.menu_sample, this, true);
        ButterKnife.bind(this);  //true also
    }

    private void doWork() {

        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Button 1 clicked",Toast.LENGTH_LONG).show();
            }
        });

        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Button 2 clicked",Toast.LENGTH_LONG).show();
            }
        });

    }

}
