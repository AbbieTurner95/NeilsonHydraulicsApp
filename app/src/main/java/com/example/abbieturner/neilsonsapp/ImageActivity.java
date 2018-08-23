package com.example.abbieturner.neilsonsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity {

    @BindView(R.id.twitter)
    ImageView twitter;
    @BindView(R.id.facebook)
    ImageView facebook;
    @BindView(R.id.linkinin)
    ImageView linkedin;

    @BindView(R.id.image_taken_preview) ImageView preview;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.company)
    EditText company;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.body_text)
    EditText body_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);


    }
}
