package com.example.abbieturner.neilsonsapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.abbieturner.neilsonsapp.R;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class QRScanningActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    private BarcodeReader barcodeReader;

    public static final String TAG = QRScanningActivity.class.getSimpleName();
    public static final String BARCODE_RESULT = "barcode_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanning);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);
    }

    @Override
    public void onScanned(final Barcode barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue);
        barcodeReader.playBeep();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getApplicationContext(), "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString(BARCODE_RESULT, barcode.displayValue);
                startActivity(new Intent(getApplicationContext(), QrActivity.class)
                        .putExtras(bundle));
            }
        });

    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        Log.e(TAG, "onScannedMultiple: " + barcodes.size());

        String codes = "";
        for (Barcode barcode : barcodes) {
            codes += barcode.displayValue + ", ";
        }
        barcodeReader.playBeep();
        final String finalCodes = codes;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString(BARCODE_RESULT, finalCodes);
                startActivity(new Intent(getApplicationContext(), QrActivity.class)
                        .putExtras(bundle));

            }
        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
        finish();
    }
}