

package com.liuzr.ancient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.liuzr.ancient.R;
import com.liuzr.ancient.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private final static int JUMP_TO_NEXT = 1;

    private MyHandler handler = new MyHandler(SplashActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(JUMP_TO_NEXT, 500);
    }

    private static class MyHandler extends Handler {
        private final SplashActivity mActivity;

        public MyHandler(SplashActivity activity) {
            mActivity = activity;
        }


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case JUMP_TO_NEXT:

                    mActivity.startActivity(new Intent(mActivity, SignupActivity.class));
                    mActivity.finish();
            }
            super.handleMessage(msg);
        }
    }
}
