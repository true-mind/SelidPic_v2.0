package com.truemind.selidpic_v20.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.truemind.selidpic_v20.R;


/**
 * 이 클래스는 권한 요청 및 관리를 위해 사용됩니다
 */
public final class Permissions {

    private Permissions() {
    }

    public static boolean granted(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    private static boolean requestPermission(final Activity activity, final int requestCode, String permission) {
        if (!granted(activity, permission)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            } else {
                //처음 또는 다시 묻지 않기.
                //String r = Save.requestedPermissions();
                /*if (r == null || !r.contains(permission)) {
                    Save.requestedPermissions(permission);
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);*/
                //} else {
                new AlertDialog.Builder(activity)
                        .setMessage(permissionMessage(permission))
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                        activity.startActivityForResult(intent, requestCode);
                                    }
                                })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                //}
            }
            return true;
        }
        return false;
    }

    public static boolean verifyOnResult(int[] results) {
        if (results.length < 1) return false;
        for (int result : results) {
            if (PackageManager.PERMISSION_GRANTED != result) return false;
        }
        return true;
    }

    public static boolean internet(Activity activity, int requestCode) {
        return requestPermission(activity, requestCode, Manifest.permission.INTERNET);
    }

    public static boolean accessNetworkState(Activity activity, int requestCode) {
        return requestPermission(activity, requestCode, Manifest.permission.ACCESS_NETWORK_STATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean readExternalStorage(Activity activity, int requestCode) {
        return requestPermission(activity, requestCode, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public static boolean writeExternalStorage(Activity activity, int requestCode) {
        return requestPermission(activity, requestCode, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static boolean camera(Activity activity, int requestCode) {
        return requestPermission(activity, requestCode, Manifest.permission.CAMERA);
    }

    private static int permissionMessage(String permission) {
        switch (permission) {
            case Manifest.permission.INTERNET:
                return R.string.permission_deny_internet;
            case Manifest.permission.ACCESS_NETWORK_STATE:
                return R.string.permission_deny_network_state;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return R.string.permission_deny_read_storage;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                return R.string.permission_deny_write_storage;
            case Manifest.permission.CAMERA:
                return R.string.permission_deny_camera;
            default:
                return R.string.permission_deny;
        }
    }
}
