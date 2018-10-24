package modules.general.ui.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import modules.general.ui.utils.general_listeners.ITitleListener;
import omar.apps923.downloadmanager.R;

public class CustomTitle extends LinearLayout {
    @BindView(R.id.customtitle_btn_back)
    ImageButton btnBack;
    @BindView(R.id.custom_title_rl_main)
    RelativeLayout custom_title_rl_main;
    @BindView(R.id.customtitle_btn_menu)
    ImageButton btnMenuMain;
     @BindView(R.id.customtitle_btn_settings)
    ImageButton btnSettings;

    public ImageButton getBtnSettings() {
        return btnSettings;
    }

    @BindView(R.id.customtitle_txt_title)
    TextView txtTitle;
    ITitleListener listenerMain;

    public CustomTitle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_title, this);
        ButterKnife.bind(this);
    }

    public ImageButton getBtnMenuMain() {
        return btnMenuMain;
    }

    public void initalizeView(Context context, String title, ITitleListener titleListener) {
        txtTitle.setText(title);
         listenerMain = titleListener;
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerMain.onBackPressed();
            }
        });
        btnMenuMain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerMain.onMenuPressed();
            }
        });
        btnSettings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerMain.onSettingsPressed();
            }
        });
    }

    public void updateTitle(String title) {
        txtTitle.setText(title);
    }

    public void hideCustomTitle() {
        setVisibility(GONE);
    }

    public void showCustomTitle() {
        setVisibility(VISIBLE);
    }


    public void showMenu() {
        btnMenuMain.setVisibility(VISIBLE);
    }

    public void hideMenu() {
        btnMenuMain.setVisibility(GONE);
    }


    public void showSettings() {
        btnSettings.setVisibility(VISIBLE);
    }

    public void hideSettings() {
        btnSettings.setVisibility(GONE);
    }

    public void hideMenuAndSettings() {
        btnMenuMain.setVisibility(GONE);
        btnSettings.setVisibility(GONE);
    }

    public void hideMenuAndSettingsAndBack() {
        btnMenuMain.setVisibility(GONE);
        btnSettings.setVisibility(GONE);
        btnBack.setVisibility(GONE);
    }

    public void hideMenuAndBack() {
        btnMenuMain.setVisibility(GONE);
         btnBack.setVisibility(GONE);
    }

}