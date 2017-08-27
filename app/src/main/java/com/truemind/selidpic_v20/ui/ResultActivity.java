package com.truemind.selidpic_v20.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;
import com.truemind.selidpic_v20.camera.Compose;
import com.truemind.selidpic_v20.camera.OriginImage;
import com.truemind.selidpic_v20.camera.SelidPicCam;
import com.truemind.selidpic_v20.util.CommonDialog;
import com.truemind.selidpic_v20.util.ProgressDialog;
import com.truemind.selidpic_v20.util.Save;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hyunseok on 2017-08-27.
 */

public class ResultActivity extends BaseActivity {

    private static final String TAG = "MyTag";

    private String selectedImagePath;
    private static final int MY_PERMISSION_REQUEST_STORAGE = 1;
    private static final String FOLDER_NAME = "/SelicPic";
    private static final String FINAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + FOLDER_NAME + "/";
    private static final int MODE_DRAW = 300;
    private static final int MODE_ERASE = 301;

    private TextView titleName;
    private TextView titleSize;
    private ImageView finalImage;

    private LinearLayout btnHelp;

    private ImageView back1;
    private ImageView back2;
    private ImageView back3;
    private ImageView back4;
    private ImageButton btnGetBack;

    private LinearLayout btnSos1;
    private LinearLayout btnSos2;
    private LinearLayout btnSos3;
    private LinearLayout btnSos4;

    private CheckBox checkBox;
    private boolean composed = true;

    ProgressDialog progressDialog;
    Compose compose = new Compose();
    OriginImage originImage = new OriginImage();

    Bitmap background;
    Bitmap composedImage;
    Bitmap imageOrigin;

    private String FILE_NAME = "default";
    private boolean isOriginImageValidate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initView();
        initListener();

        initFooter();
        initFloating();
        floatingListener(getContext());

        background = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo_back);
        updateBack(background);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
    }

    /**
     * 쓰레드 이후 핸들러 발생
     * 이 핸들러 내부에서 ImageEditter의 호출이 필요하다.
     * (아니면 compose가 완료되지 않아 bitmap에서 nullPointerException 발생)
     */
    private Handler threadhandler = new Handler() {
        public void handleMessage(Message msg) {
            finalImage.setImageBitmap(composedImage);
            progressDialog.dismiss();
        }
    };

    /**
     * View initiating
     * Seekbar에 의한 size 조절부도 initiating
     */
    public void initView() {
        titleSize = (TextView) findViewById(R.id.titleSize);
        titleName = (TextView) findViewById(R.id.titleName);
        finalImage = (ImageView) findViewById(R.id.finalImage);
        TextView subTitle1 = (TextView) findViewById(R.id.subTitle1);
        TextView txtHelp = (TextView) findViewById(R.id.txtHelp);
        btnHelp = (LinearLayout) findViewById(R.id.btnHelp);
        back1 = (ImageView) findViewById(R.id.back1);
        back2 = (ImageView) findViewById(R.id.back2);
        back3 = (ImageView) findViewById(R.id.back3);
        back4 = (ImageView) findViewById(R.id.back4);
        btnGetBack = (ImageButton) findViewById(R.id.btnGetBack);
        TextView txtGetBack = (TextView) findViewById(R.id.txtGetBack);
        TextView subTitle3 = (TextView) findViewById(R.id.subTitle3);
        btnSos1 = (LinearLayout) findViewById(R.id.btnSos1);
        btnSos2 = (LinearLayout) findViewById(R.id.btnSos2);
        btnSos3 = (LinearLayout) findViewById(R.id.btnSos3);
        btnSos4 = (LinearLayout) findViewById(R.id.btnSos4);
        TextView imageSave = (TextView) findViewById(R.id.txtSave);
        TextView imageShare = (TextView) findViewById(R.id.txtShare);
        TextView imageCompare = (TextView) findViewById(R.id.txtCompare);
        TextView imageReplay = (TextView) findViewById(R.id.txtReplay);
        TextView txtCheck1 = (TextView) findViewById(R.id.txtCheck1);
        TextView txtCheck2 = (TextView) findViewById(R.id.txtCheck2);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        String size = new Constants().getCurrentPhotoWidth() + getContext().getResources().getString(R.string.multiply)
                + new Constants().getCurrentPhotoHeight() + getContext().getResources().getString(R.string.mm);
        String name = new Constants().getCurrentPhotoType();

        titleSize.setText(size);
        titleName.setText(name);

        setFontToViewBold(titleSize, titleName, subTitle1, txtHelp,
                txtGetBack, subTitle3, imageSave, imageShare, imageReplay, imageCompare,
                txtCheck1, txtCheck2);
    }

    /**
     * 모든 listener 등록
     * 단 이미지 터치로 합성하는 부분은 imageEditter로 따로 등록
     */
    public void initListener() {
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog();
                dialog.showDialog(getContext(), getResources().getString(R.string.help), getResources().getString(R.string.helper_simplified));
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo_back);
                updateBack(background);
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo_back2);
                updateBack(background);
            }
        });
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo_back3);
                updateBack(background);
            }
        });
        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo_back4);
                updateBack(background);
            }
        });
        btnGetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "서비스 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btnSos1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    imageSave(imageOrigin, true);
                    imageSave(composedImage, false);
                    Toast.makeText(getContext(), FINAL_PATH + "에 원본과 함께 저장됨", Toast.LENGTH_SHORT).show();
                    galleryRefresh();
                } else {
                    imageSave(composedImage, false);
                    Toast.makeText(getContext(), FINAL_PATH + "에 저장됨", Toast.LENGTH_SHORT).show();
                    galleryRefresh();
                }
            }
        });
        btnSos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (composed) {
                    finalImage.setImageBitmap(imageOrigin);
                    composed = false;
                } else {
                    finalImage.setImageBitmap(composedImage);
                    composed = true;
                }

            }
        });
        btnSos3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File files = new File(FINAL_PATH, FILE_NAME);

                if (files.exists()) {
                    Intent intentSend = new Intent(Intent.ACTION_SEND);
                    intentSend.setType("image/*");
                    intentSend.putExtra(Intent.EXTRA_STREAM, Uri.parse(FINAL_PATH + FILE_NAME));
                    startActivity(Intent.createChooser(intentSend, "Share"));
                    Toast.makeText(getApplicationContext(), "가장 최근에 저장된 파일이 공유됩니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Save first", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnSos4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelEdit();
            }
        });
    }

    public void imageSave(Bitmap image, boolean isOrigin) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        try {
            File mediaStorageDir = new File(FINAL_PATH);

            if (!mediaStorageDir.isDirectory()) {
                mediaStorageDir.mkdirs();
            }

            if (isOrigin) {
                FILE_NAME = "Origin_" + timeStamp + ".jpeg";
            } else {
                FILE_NAME = "SelidPic_" + timeStamp + ".jpeg";
            }
            FileOutputStream out = new FileOutputStream(FINAL_PATH + FILE_NAME);
            image.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        } catch (FileNotFoundException e) {
            Log.d("FileNotFoundException:", e.getMessage());
        } catch (IOException exception) {
            Log.e("IOException", exception.getMessage());
        }
    }

    private void galleryRefresh() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File outputFile = new File(FINAL_PATH + FILE_NAME);
            final Uri contentUri = Uri.fromFile(outputFile);
            scanIntent.setData(contentUri);
            sendBroadcast(scanIntent);
            Log.d(TAG, "Gallery Refreshed");
        } else {
            final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(FINAL_PATH));
            sendBroadcast(intent);
            Log.d(TAG, "Gallery Refreshed");
        }
    }

    /**
     * background를 사용자가 원하는 것으로 바꾸어 다시 compose가능
     * 합수에서 쓰레드 진행됨, 핸들러는 공유
     */
    public void updateBack(final Bitmap back) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                compose = new Compose();
                composedImage = compose.compose(getContext(), Constants.photoByteStream, Constants.photoWidth, Constants.photoHeight, 440, back);
                Save.composedImage(getContext(), composedImage);
                if (imageOrigin == null) {
                    originImage = new OriginImage();
                    imageOrigin = originImage.getOriginImage(Constants.photoByteStream, Constants.photoWidth, Constants.photoHeight, 440);
                    Save.originImage(getContext(), composedImage);
                }
                threadhandler.sendEmptyMessage(0);
            }
        });
        thread.start();
    }

    /**
     * Android 6.0이상에서만 구동되는 권한 체크 함수
     * 해당 Permission 확인 시 다이얼로그 자동 생성
     */
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


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE: //
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    /** Access granted (Permission granted)*/

                } else {

                    /** Access denied (user denied permission)*/

                    CommonDialog dialog = new CommonDialog();
                    dialog.showDialog(this, "권한이 없습니다.");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);

                }
        }

    }

    public void cancelEdit() {

        CommonDialog dialog = new CommonDialog();
        dialog.setOnCloseListener(new CommonDialog.OnCloseListener() {
            @Override
            public void onClose(DialogInterface dialog, int which, Object data) {
                if (which == 1) {
                    Intent intent = new Intent(getContext(), SelidPicCam.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        dialog.showDialog(getContext(), "작업을 끝내시겠습니까?\n현재까지의 작업 내역은 저장되지 않습니다.", true, "확인", "취소");

    }

    public void onBackPressed() {
        cancelEdit();
    }
}
