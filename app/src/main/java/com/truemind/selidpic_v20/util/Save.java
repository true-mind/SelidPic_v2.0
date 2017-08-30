package com.truemind.selidpic_v20.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Hyunseok on 2017-08-04.
 */

public final class Save {

    private static SharedPreferences SP;
    private static final String KEY_PHOTO_BYTE_STREAM = "pbs";
    private static final String KEY_ORIGIN_IMAGE = "oi";
    private static final String KEY_COMPOSED_IMAGE = "ci";
    private static final String KEY_CAM_MANUAL = "cm";
    private static final String KEY_USER_SIZE_WIDTH = "usw";
    private static final String KEY_USER_SIZE_HEIGHT = "ush";

    private static SharedPreferences instance(Context context) {
        if (SP == null) {
            synchronized (Save.class) {
                if (SP == null) {
                    SP = context.getSharedPreferences("save", Context.MODE_PRIVATE);
                }
            }
        }
        return SP;
    }

    /**
     * 촬영 직후의 사진 byteArray 반환
     *
     * @param context context
     * @return 촬영 직후의 이미지 byteArray
     */
    public static byte[] photoByteStream(Context context) {
        String stringByteStream = instance(context).getString(KEY_PHOTO_BYTE_STREAM, "");
        return Base64.decode(stringByteStream, Base64.DEFAULT);
    }

    /**
     * 촬영 직후의 사진 byteStream 저장
     *
     * @param context    context
     * @param byteStream 촬영 직후의 사진 byteStream
     */
    public static void photoByteStream(Context context, byte[] byteStream) {
        String saveThis = Base64.encodeToString(byteStream, Base64.DEFAULT);
        instance(context).edit().putString(KEY_PHOTO_BYTE_STREAM, saveThis).apply();
    }

    /**
     * 저장하고 있는 합성된 Bitmap 반환
     *
     * @param context context
     * @return Bitmap
     */
    public static Bitmap composedImage(Context context) {
        String stringByteStream = instance(context).getString(KEY_COMPOSED_IMAGE, "");
        byte[] decodedByte = Base64.decode(stringByteStream, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    /**
     * SP로 합성된 이미지 저장
     *
     * @param context context
     * @param bitmap  composed Image
     */
    public static void composedImage(Context context, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] arr = baos.toByteArray();
        String saveThis = Base64.encodeToString(arr, Base64.DEFAULT);
        instance(context).edit().putString(KEY_COMPOSED_IMAGE, saveThis).apply();
    }

    /**
     * 저장하고 있는 합성되지 않은 Bitmap 반환
     *
     * @param context context
     * @return Bitmap
     */
    public static Bitmap originImage(Context context) {
        String stringByteStream = instance(context).getString(KEY_ORIGIN_IMAGE, "");
        byte[] decodedByte = Base64.decode(stringByteStream, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    /**
     * SP로 합성되지 않은 원본 이미지 저장
     *
     * @param context context
     * @param bitmap  composed Image
     */
    public static void originImage(Context context, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] arr = baos.toByteArray();
        String saveThis = Base64.encodeToString(arr, Base64.DEFAULT);
        instance(context).edit().putString(KEY_ORIGIN_IMAGE, saveThis).apply();
    }

    public static void camManualValidate(Context context, boolean isValidate){
        instance(context).edit().putBoolean(KEY_CAM_MANUAL, isValidate).apply();
    }

    public static boolean camManualValidate(Context context){
        return instance(context).getBoolean(KEY_CAM_MANUAL, true);
    }

    public static void userSizeWidth(Context context, int width){
        instance(context).edit().putInt(KEY_USER_SIZE_WIDTH, width).apply();
    }

    public static int userSizeWidth(Context context){
        return instance(context).getInt(KEY_USER_SIZE_WIDTH, 0);
    }

    public static void userSizeHeight(Context context, int height){
        instance(context).edit().putInt(KEY_USER_SIZE_HEIGHT, height).apply();
    }

    public static int userSizeHeight(Context context){
        return instance(context).getInt(KEY_USER_SIZE_HEIGHT, 0);
    }

    /**
     * 설정값 변경시 callback 수신을 허용할 수 있습니다.
     *
     * @param l 콜백 수신자.
     */
    public static void listen(Context context, SharedPreferences.OnSharedPreferenceChangeListener l) {
        instance(context).registerOnSharedPreferenceChangeListener(l);
    }

    /**
     * 설정값 변경시 callback 수신을 취소할 수 있습니다.
     *
     * @param l 콜백 수신자.
     */
    public static void unlisten(Context context, SharedPreferences.OnSharedPreferenceChangeListener l) {
        instance(context).unregisterOnSharedPreferenceChangeListener(l);
    }


}
