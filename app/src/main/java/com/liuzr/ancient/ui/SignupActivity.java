
package com.liuzr.ancient.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.liuzr.ancient.R;
import com.liuzr.ancient.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SignupActivity extends BaseActivity {

    @BindView(R.id.email)
    EditText userEmail;

    @BindView(R.id.password)
    EditText userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//        JianShiApplication.getAppComponent().inject(this);
    }

    @OnClick(R.id.login)
    void login() {
//        if (!checkEmailPwdNonNull()) {
//            return;
//        }
        startActivity(MainActivity.createIntent(SignupActivity.this));
    }

    private boolean checkEmailPwdNonNull() {
        if (TextUtils.isEmpty(getEmailText())) {
            userEmail.setError(getString(R.string.email_should_not_be_null));
            return false;
        }
        if (!"admin".equals(getEmailText())) {
            userEmail.setError(getString(R.string.wrong_email_format));
            return false;
        }
        if (TextUtils.isEmpty(getPassword())) {
            userPassword.setError(getString(R.string.password_should_not_be_null));
            return false;
        }
        if (!"123456".equals(getPassword())) {
            userPassword.setError(getString(R.string.password_error));
            return false;
        }

        return true;
    }

    private String getEmailText() {
        return userEmail.getText().toString().trim();
    }

    private String getPassword() {
        return userPassword.getText().toString();
    }


    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, SignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_NO_ANIMATION);
        return intent;
    }
}
