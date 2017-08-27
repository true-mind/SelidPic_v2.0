package com.truemind.selidpic_v20.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

/**
 * Created by jeongjisu on 2017. 5. 7..
 */

public class OriginImage {

    private static final String TAG = "MyTag";

    private double ppi;
    private double height;
    private double width;

    private Bitmap image;
    private byte[] arr;


    public Bitmap getOriginImage(final byte[] arr, final double width, final double height, final double ppi) {
        this.ppi = ppi;
        this.height = height;
        this.width = width;
        this.arr = arr;

        thread.start();
        try {

            thread.join();
            return image;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            //이미지 해상도 조절
            image = set_resolution();

            int screenWidth = image.getWidth();
            int screenHeight = image.getHeight();

            int widthMid = screenWidth / 2;
            int heightMid = screenHeight / 2;

            double picHeight = (screenWidth / height * width);
            double picWidth = (screenHeight / width * height);

            if (picHeight > screenHeight) {
                picHeight = screenHeight;
                picWidth = (picHeight / width * height);
            } else {
                picWidth = screenWidth;
                picHeight = (picWidth / height * width);
            }
            double cropStartX = widthMid - (picWidth / 2);
            double cropStartY = heightMid - (picHeight / 2);

            Bitmap imageCropped = Bitmap.createBitmap(image, (int) cropStartX, (int) cropStartY, (int) picWidth, (int) picHeight);
            image = rotateImage(imageCropped, 90);
            imageCropped.recycle();
        }
    });


    private Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.setScale(1, -1);  // 상하반전
        matrix.setScale(-1, 1);  // 좌우반전

        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }


    private Bitmap set_resolution() { //Get the dimensions of the View

        int targetW, targetH;
        //onCreate 안에서 view가 아직 안 띄워짐 고로 임의 값 설정하겠음 나중에 수정해도 됨

        double tempW = height;
        double tempH = width;
        double multy_value = ppi / 30;
        tempW *= multy_value;
        tempH *= multy_value;

        targetW = (int) tempW;
        targetH = (int) tempH;

        //Get the dimensions of the bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if (arr == null) {
            Log.d(TAG, "null");
        }
        //BitmapFactory.decodeByteArray(arr, 0, arr.length, options);
        int photoW = options.outHeight;
        int photoH = options.outWidth;

        //Determine how much to alpha down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        //Decode the image file into a Bitmap sized to fill the View
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        return BitmapFactory.decodeByteArray(arr, 0, arr.length, options);
    }

}
