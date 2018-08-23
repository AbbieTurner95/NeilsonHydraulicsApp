package com.example.abbieturner.neilsonsapp.UI;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.abbieturner.neilsonsapp.R;
import com.example.abbieturner.neilsonsapp.Utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageActivity extends AppCompatActivity {
    @BindView(R.id.image_taken_preview)
    ImageView image_taken_preview;
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

    Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            imagePath = Uri.parse("file://" + getIntent().getExtras().getString(MainActivity.IMAGE_URL));
            image_taken_preview.setImageURI(imagePath);
        }
    }

    @OnClick(R.id.send_button)
    public void sendNow() {
        if (Utils.checkError(name) && Utils.checkError(company) && Utils.checkError(phone)
                && Utils.checkError(email) && Utils.checkError(body_text) && Utils.checkEmail(email)) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"abbiefacebook@hotmail.co.uk"});
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Mobile app request");

            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Name : " + name.getText().toString() + "\n" +
                            "Phone : " + phone.getText().toString() + "\n" +
                            "Email : " + email.getText().toString() + "\n" +
                            "Company : " + company.getText().toString() + "\n" +
                            "Message : " + body_text.getText().toString());

            shareIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, getString(R.string.send_msg_by)));
        }
    }

    @OnClick(R.id.facebook)
    public void startFacebook() {
        startActivity(Utils.getOpenFacebookIntent(this));
    }

    @OnClick(R.id.linkinin)
    public void startLinkedIn() {
        Utils.getOpenLinkedInIntent(this);
    }

    @OnClick(R.id.twitter)
    public void startTwitter() {
        startActivity(Utils.getOpenTwitterIntent(this));
    }
}