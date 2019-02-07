package com.example.lenovo.edittext_part;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class Activity_B extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("3333","桥梁");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        imageView=findViewById(R.id.b_img);
    }
}
