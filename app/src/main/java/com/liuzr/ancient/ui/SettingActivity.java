
package com.liuzr.ancient.ui;

import android.os.Bundle;
import android.view.View;

import com.liuzr.ancient.R;
import com.liuzr.ancient.manager.UserManager;
import com.liuzr.ancient.prefs.UserPrefs;
import com.liuzr.ancient.ui.base.BaseActivity;
import com.liuzr.ancient.ui.widget.BgColorPickDialogFragment;

import androidx.appcompat.widget.SwitchCompat;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.vertical_write)
    SwitchCompat verticalWrite;

    @BindView(R.id.home_image_poem_switch)
    SwitchCompat homeImagePoemSwitch;

    @BindView(R.id.customize_bg_color)
    View customizeBgColor;

    UserPrefs userPrefs;

    UserManager userManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        userPrefs = new UserPrefs(this);
        userManager = UserManager.getInstance(this);

        verticalWrite.setChecked(userPrefs.getVerticalWrite());
        homeImagePoemSwitch.setChecked(userPrefs.getHomeImagePoemSetting());

    }


    @OnCheckedChanged(R.id.vertical_write)
    void chooseVerticalWrite() {
        userPrefs.setVerticalWrite(verticalWrite.isChecked());

    }

    @OnCheckedChanged(R.id.home_image_poem_switch)
    void checkHomeImagePoem() {
        userPrefs.setHomeImagePoem(homeImagePoemSwitch.isChecked());
    }


    @OnClick(R.id.customize_bg_color)
    void chooseBgColor() {
        BgColorPickDialogFragment bgColorPickDialogFragment = new BgColorPickDialogFragment();
        bgColorPickDialogFragment.setOnBackgroundColorChangedListener(
                newColorRes -> {
                    userPrefs.setBackgroundColor(newColorRes);
                    SettingActivity.this.setContainerBgColor(newColorRes);
                    setResult(RESULT_OK);
                });
        bgColorPickDialogFragment.show(getSupportFragmentManager(), null);
    }


    @OnClick(R.id.logout)
    void logout() {
//        userManager.logout(this);
        startActivity(SignupActivity.createIntent(this));
    }
}
