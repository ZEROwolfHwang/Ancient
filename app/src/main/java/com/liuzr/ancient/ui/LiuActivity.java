package com.liuzr.ancient.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.liuzr.ancient.R;

import androidx.appcompat.app.AppCompatActivity;

public class LiuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liu);

        final TextView textView = findViewById(R.id.text1);
        Button changeText = findViewById(R.id.button1);

        changeText.setOnClickListener(v -> {
            textView.setText("liuzhourong");
            textView.setTextColor(Color.BLUE);

        });
    }
}
