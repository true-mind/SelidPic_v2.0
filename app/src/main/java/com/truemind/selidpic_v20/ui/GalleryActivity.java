package com.truemind.selidpic_v20.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import uk.co.senab.photoview.PhotoViewAttacher;
import com.truemind.selidpic_v20.util.CommonDialog;

/**
 * Created by 현석 on 2017-04-24.
 *
 * @notice After android 6.0 (Marshmallow), Permission issue occurs
 * Read/Write from storage, or Camera or else need permission during RUNTIME
 * Users have to grant their permission due to accessibility,
 * and provider (application) must check it's permission matter, periodically.
 *
 * Using PhotoView, Apache licensed on ChrisBanes
 *
 */
public class GalleryActivity extends BaseActivity {

    private static final String TAG = "MyTag";

    private String selectedImagePath;
    private static final int SELECT_PICTURE = 1;
    private static final int MY_PERMISSION_REQUEST_STORAGE = 1;

    private ImageView photoView;
    private Bitmap image_bitmap;
    private InputStream imageStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initView();
        initFooter();
        initFloating();
        floatingListener(getContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

                //resized = resizeBitmap(selectedImagePath);
                Bitmap resized = resizeBitmapWithURL(selectedImageUri);

                photoView.setImageBitmap(resized);
                PhotoViewAttacher mAttacher = new PhotoViewAttacher(photoView);
                mAttacher.update();
            }
        }
    }

    /**
     * 갤러리에서 선택한 이미지의 Uri를 받아서
     * 해당 이미지의 path를 가져옴
     *
     * @param uri 갤러리에서 이미지 선택시 생성되는 Uri
     * @return String string 형식의 path를 반환
     * */
    @SuppressWarnings("deprecation")
    public String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        return uri.getPath();
    }

    /**Android 6.0 버전 이후에서 돌아가지 않는다.
     *
     * @param imagePath Uri를 이용해서 얻은 path
     * @return Bitmap 1/4 규격으로 축소된 ImageView
     * */
    public Bitmap resizeBitmap(String imagePath) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        image_bitmap = BitmapFactory.decodeFile(imagePath, options);
        int dstWidth = image_bitmap.getWidth();
        int dstHeight = image_bitmap.getHeight();
        return Bitmap.createScaledBitmap(image_bitmap, dstWidth, dstHeight, true);

    }

    /**Android 6.0 버전 이후에서 돌아감.
     * 메모리 문제를 고려해야할 필요가 있음
     *
     * @param ImageUri 갤러리에서 ImageSelect 시 획득한 Uri
     * @return Bitmap 1/4 규격으로 축소된 ImageView
     * */
    public Bitmap resizeBitmapWithURL(Uri ImageUri) {

        try {
            imageStream = getContentResolver().openInputStream(ImageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        image_bitmap = BitmapFactory.decodeStream(imageStream);
        int dstWidth = image_bitmap.getWidth()/2;
        int dstHeight = image_bitmap.getHeight()/2;

        return Bitmap.createScaledBitmap(image_bitmap, dstWidth, dstHeight, true);
    }

    public void initView() {
        photoView = (ImageView) findViewById(R.id.photoView);
    }

    /**
     * Android 6.0이상에서만 구동되는 권한 체크 함수
     * 해당 Permission 확인 시 다이얼로그 자동 생성
     * */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            /** Access denied (Permission denied)*/

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //퍼미션을 재요청 하는 경우 - 왜 이 퍼미션이 필요한지등을 대화창에 넣어서 사용자를 설득할 수 있다.
                //대화상자에 '다시 묻지 않기' 체크박스가 자동으로 추가된다.
                Log.d(TAG, "퍼미션을 재요청 합니다.");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_STORAGE);

            } else {
                Log.d(TAG, "첫 퍼미션 요청입니다.");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_STORAGE);
            }
        } else {
            /** Access already granted (Permission granted)*/

            Log.d(TAG, "Permission is granted");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE: //
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    /** Access granted (Permission granted)*/

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,
                            "Select Picture"), SELECT_PICTURE);

                } else {

                    /** Access denied (user denied permission)*/

                    CommonDialog dialog = new CommonDialog();
                    dialog.showDialog(this, "권한이 없습니다.");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);

                }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
