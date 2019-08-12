
package com.liuzr.ancient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.liuzr.ancient.R;
import com.liuzr.ancient.global.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SignupActivity extends BaseActivity {

    @BindView(R.id.email)
    EditText mUsername;

    @BindView(R.id.password)
    EditText mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    @OnClick(R.id.login)
    void login() {
//        boolean isOk = checkEmailPwdNonNull();
//        if (isOk) {
        startActivity(new Intent(this, MainActivity.class));
//        }
    }

    private boolean checkEmailPwdNonNull() {
        if (TextUtils.isEmpty(getEditString(mUsername))) {
            mUsername.setError(getString(R.string.email_should_not_be_null));
            return false;
        }
        if (!"admin".equals(getEditString(mUsername))) {
            mUsername.setError(getString(R.string.wrong_email_format));
            return false;
        }
        if (TextUtils.isEmpty(getEditString(mPassword))) {
            mPassword.setError(getString(R.string.password_should_not_be_null));
            return false;
        }
        if (!"123456".equals(getEditString(mPassword))) {
            mPassword.setError(getString(R.string.password_error));
            return false;
        }

        return true;
    }

    private String getEditString(EditText editText) {
        return editText.getText().toString().trim();
    }

}
