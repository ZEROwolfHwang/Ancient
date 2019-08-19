
package com.liuzr.ancient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.liuzr.ancient.R;
import com.liuzr.ancient.global.BaseActivity;

public class SplashActivity extends BaseActivity {

    private final static int JUMP_TO_NEXT = 1;

    //    private MyHandler handler = new MyHandler(SplashActivity.this);

    /**
     * 实现延时跳转到登录界面
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case JUMP_TO_NEXT:
                    Intent intent = new Intent(mActivity, SignupActivity.class);
                    mActivity.startActivity(intent);
                    mActivity.finish();
            }
            return false;
        }
    });
    private SplashActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mActivity = this;
        handler.sendEmptyMessageDelayed(JUMP_TO_NEXT, 500);
    }

}
