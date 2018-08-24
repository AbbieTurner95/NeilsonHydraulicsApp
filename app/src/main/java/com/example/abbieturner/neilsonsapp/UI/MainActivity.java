package com.example.abbieturner.neilsonsapp.UI;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.abbieturner.neilsonsapp.R;
import com.example.abbieturner.neilsonsapp.Utils.Utils;
import com.fxn.pix.Pix;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.qr_button)
    Button qr_button;

    public static final int IMAGE_CODE = 100;
    public static final String IMAGE_URL = "image_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call_neilsons:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:07527433503"));
                startActivity(callIntent);
                break;

            case R.id.googlemaps:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:53.3880982,-1.6430196?q=53.3880982,-1.6430196(Neilson Hydraulics)"));
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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

    @OnClick(R.id.take_pic_button)
    public void fetchImage() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Pix.start(MainActivity.this, IMAGE_CODE);
                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            Utils.goToImageSettings(MainActivity.this);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }


    @OnClick(R.id.qr_button)
    public void startQrScanning() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startActivity(new Intent(getApplicationContext(), QRScanningActivity.class));
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Utils.goToImageSettings(MainActivity.this);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CODE) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            Toast.makeText(this, "" + returnValue.get(0), Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString(IMAGE_URL, returnValue.get(0));
            startActivity(new Intent(this, ImageActivity.class)
                    .putExtras(bundle));
        }
    }
}