package com.example.abbieturner.neilsonsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.qr_button)
    Button qr_btn;
    @BindView(R.id.send_button)
    Button send_btn;
    @BindView(R.id.twitter)
    ImageView twitter;
    @BindView(R.id.facebook)
    ImageView facebook;
    @BindView(R.id.linkinin)
    ImageView linkedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



    }
}
