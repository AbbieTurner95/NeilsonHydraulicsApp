package com.example.abbieturner.neilsonsapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.abbieturner.neilsonsapp.R;
import com.example.abbieturner.neilsonsapp.Utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QrActivity extends AppCompatActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.company)
    EditText company;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.qr_info_added)
    EditText qr_info_added;
    @BindView(R.id.body_text)
    EditText body_text;

    String barcodeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            barcodeDate = getIntent().getExtras().getString(QRScanningActivity.BARCODE_RESULT);
            qr_info_added.setText(barcodeDate);
        }
    }

    @OnClick(R.id.send_button)
    public void sendNow() {
        if (Utils.checkError(name) && Utils.checkError(company) && Utils.checkError(phone)
                && Utils.checkError(email) && Utils.checkError(qr_info_added)
                && Utils.checkError(body_text) && Utils.checkEmail(email)) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"abbiefacebook@hotmail.co.uk"});
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Mobile app request");
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Name : " + name.getText().toString() + "\n" +
                            "Phone : " + phone.getText().toString() + "\n" +
                            "Email : " + email.getText().toString() + "\n" +
                            "Company : " + company.getText().toString() + "\n" +
                            "QR code information : " + qr_info_added.getText().toString() + "\n" +
                            "Message : " + body_text.getText().toString());

            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sendIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getString(R.string.send_msg_by)));
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