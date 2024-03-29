package com.liuzr.ancient.global;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.liuzr.ancient.R;
import com.liuzr.ancient.prefs.UserPrefs;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    protected boolean isVisible = false;

    protected View containerView;
    protected String TAG = getClass().getSimpleName() + ": %s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        containerView = findViewById(R.id.layout_container);
    }

    protected void setContainerBgColorFromPrefs() {
        if (containerView != null) {
            containerView.setBackgroundResource(UserPrefs.getInstance(this).getBackgroundColor());
        }
    }

    protected void setContainerBgColor(int colorRes) {
        if (containerView != null) {
            containerView.setBackgroundResource(colorRes);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContainerBgColorFromPrefs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean isUISafe() {
        return isVisible;
    }

    protected void makeToast(@NonNull String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
