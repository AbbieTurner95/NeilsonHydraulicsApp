package com.example.abbieturner.neilsonsapp.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;
import android.widget.EditText;

import com.example.abbieturner.neilsonsapp.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/530835540266092"))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/neilsonhydraulics/?rf=1590347741283202"))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    public static Intent getOpenTwitterIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=2302911457"))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/neilsonhyd"))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    public static void getOpenLinkedInIntent(Context context) {

        String pageId = "neilson-hydraulics-&-engineering-ltd";
        final String urlFb = "linkedin://" + pageId;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() == 0) {
            final String urlBrowser = "http://www.linkedin.com/company/" + pageId;
            intent.setData(Uri.parse(urlBrowser));
        }
        context.startActivity(intent);
    }

    public static void goToImageSettings(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                .setMessage(R.string.upload_images)
                .setPositiveButton(context.getString(R.string.settings), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.getPackageName(), null));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }).setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }

    public static boolean isValid(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static final boolean checkEmail(EditText email) {
        if (!isEmailValid(email.getText().toString())) {
            email.setError("Check Email Address is correct");
            return false;
        }
        return true;
    }

    public static final boolean checkError(EditText editText) {
        if (!isValid(editText.getText().toString())) {
            editText.setError(editText.getContext().getString(R.string.empty_field));
            return false;
        }
        return true;
    }
}
