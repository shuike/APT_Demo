package com.skit.apt;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.skit.annotation.SIGN;
import com.skit.processor.SignMapUtils;

import java.util.Arrays;
import java.util.List;

@SIGN
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.text);

        List<String> list = SignMapUtils.map.get(SIGN.class.getSimpleName());
        String s = Arrays.toString(list.toArray());
        textView.setText(s);
    }
}