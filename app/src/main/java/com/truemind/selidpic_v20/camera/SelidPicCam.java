package com.truemind.selidpic_v20.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;
import com.truemind.selidpic_v20.ui.CautionActivity;
import com.truemind.selidpic_v20.ui.GalleryActivity;
import com.truemind.selidpic_v20.ui.MainActivity;
import com.truemind.selidpic_v20.ui.TouchtoolActivity;

import com.truemind.selidpic_v20.util.CamManualDialog;
import com.truemind.selidpic_v20.util.CamSettingDialog;
import com.truemind.selidpic_v20.util.CommonDialog;
import com.truemind.selidpic_v20.util.UserSizeDialog;

import java.io.ByteArrayOutputStream;

/**
 * Created by 현석 on 2017-04-24.
 */
public class SelidPicCam extends BaseActivity implements SensorEventListener {
    private static final String TAG = "MyTag";
    private final static int CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private final static int MY_PERMISSION_REQUEST_CAMERA = 1;

    Camera camera;
    Preview preview;

    private Handler handler;
    private boolean b[];

    private TextView txtSize;
    private TextView txtType;
    private TextView txtCurType;
    private ImageButton btnCam;
    private ImageButton btnBack;
    private ImageButton btnGallery;
    private ImageButton btnSetting;
    private LinearLayout manualGuide;
    private View camGuide;

    private SensorManager sensorManager;
    private Sensor sensor;
    private float sensorValue = 0;
    ByteArrayOutputStream outstr;

    private String type;
    private int width;
    private int height;
    /**
     * Auto 촬영인지 결정
     * */
    private boolean isTypeManual = true;
    /**
     * timer를 사용할 시에는 해당 클래스에서 private으로 선언된 아래의 timerTime을 사용할 것.
     */
    private int timerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selidpic_cam);

        type = new Constants().getCurrentPhotoType();
        width = new Constants().getCurrentPhotoWidth();
        height = new Constants().getCurrentPhotoHeight();

        initView();
        initListener();
        initSensor();
        initHandler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            startCamera();
        }
    }

    private void initHandler() {
        b = new boolean[3];
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int value = msg.what;
                b[value] = true;

                boolean all_task_done = true;
                for(int i=0;i<3;i++){
                    if(!b[i]){
                        all_task_done = false;
                    }
                }
                if(all_task_done){
                    Intent intent = new Intent(getContext(), TouchtoolActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    /**
     * view initiating
     * camGuideUpdate 호출
     * 기존에 입력된 Constants의 Cam 관련 상수들을 불러와 조건에 따라 view 변동.
     * Autoshoot에서 사용되는 timer의 값도 기존 설정에 의한 constants를 불러와 사용
     */
    public void initView() {
        txtType = (TextView) findViewById(R.id.txtType);
        txtSize = (TextView) findViewById(R.id.txtSize);
        txtCurType = (TextView) findViewById(R.id.txtCurType);
        btnCam = (ImageButton) findViewById(R.id.btnCam);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnGallery = (ImageButton) findViewById(R.id.btnGallery);
        btnSetting = (ImageButton) findViewById(R.id.btnSetting);
        manualGuide = (LinearLayout) findViewById(R.id.manual_guide);
        /**camManualDialog는 설정에서의 guide와는 다른 guide
         * 만약 설정에서의 guide가 각 버튼에 대한 설명이라면
         * 이 다이얼로그에서의 guide는 각 기능에 대한 설명(자세함)*/
        if (Constants.camManualGuide) {
            CamManualDialog dialog = new CamManualDialog(getContext());
            dialog.show();
        }

        camGuide = findViewById(R.id.cam_guide);
        camGuideUpdate();
        settingUpdate();

        setFontToViewBold(txtType, txtSize, txtCurType);

        String size = width + getContext().getResources().getString(R.string.multiply) + height +
                getContext().getResources().getString(R.string.mm);
        txtType.setText(type);
        txtSize.setText(size);
    }

    /**
     * 현재 constants에 저장되어 있는 상수 혹은 조건에 맞춰
     * 변경 사항 혹은 초기 설정으로 업데이트.
     * view initiating 혹은 setting에서 설정 저장 시 호출
     */
    public void settingUpdate() {
        if (Constants.camTitleInvalidate) {
            txtType.setVisibility(View.GONE);
            txtSize.setVisibility(View.GONE);
        } else {
            txtType.setVisibility(View.VISIBLE);
            txtSize.setVisibility(View.VISIBLE);
        }

        if (Constants.camGuideValidate) {
            camGuide.setVisibility(View.VISIBLE);
        } else {
            camGuide.setVisibility(View.GONE);
        }

        timerTime = Constants.camTimerTime;
    }

    /**
     * Camguide의 크기를 화면 크기에 맞춰 출력해줌
     * 만약 가로길이가 화면을 넘어간다면 가로길이를 match_parent속성으로,
     * 세로길이가 화면을 넘어간다면 그 반대로.
     * match_parent 속성이 부여되지 않은 쪽은 사진 크기의 비율에 맞춰서 출력
     */
    public void camGuideUpdate() {
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int guideHeight = (dm.widthPixels * height) / width;
        int guidewidth = (dm.heightPixels * width) / height;

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) camGuide.getLayoutParams();
        if (guidewidth > dm.widthPixels) {
            lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
            lp.height = guideHeight;
            new Constants().setFinalPhotoSize(dm.widthPixels, guideHeight);
        } else {
            lp.width = guidewidth;
            lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
            new Constants().setFinalPhotoSize(guidewidth, dm.heightPixels);

        }
        camGuide.setLayoutParams(lp);
    }

    /**
     * listener initiating
     * setting 버튼 클릭 시 setting dialog 출력
     * setting dialog의 버튼에 따라 가이드가 출력되거나 현재 설정 값을 저장하는 것을 선택
     * 가이드는 취소 버튼 혹은 뷰를 터치하면 해제되어 사라짐
     * 설정 값 저장시 settingUpdate 호출
     */
    public void initListener() {
        txtCurType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTypeManual) {
                    txtCurType.setText("A");
                    txtCurType.setTextColor(getResources().getColor(R.color.colorAuto));
                    isTypeManual = !isTypeManual;
                    Toast.makeText(getContext(), "Auto", Toast.LENGTH_SHORT).show();
                } else {
                    txtCurType.setText("M");
                    txtCurType.setTextColor(getResources().getColor(R.color.colorManual));
                    isTypeManual = !isTypeManual;
                    Toast.makeText(getContext(), "Manual", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CamSettingDialog dialog = new CamSettingDialog(getContext());
                dialog.setOnCloseListener(new CamSettingDialog.OnCloseListener() {
                    @Override
                    public void onClose(int which, Object data) {
                        if (which == 1) {
                            settingUpdate();
                        } else if (which == 2) {
                            manualGuide.setVisibility(View.VISIBLE);
                        }
                    }
                });
                dialog.show();
            }
        });

        /**촬영 버튼*/
        btnCam.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                camera.takePicture(shutterCallback, rawCallback, jpegCallback);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GalleryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                cameraCancel();
            }
        });

        manualGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manualGuide.setVisibility(View.GONE);
            }
        });
    }


    /**
     * onBackPressed 등에서 사용됨
     * 카메라 촬영을 중단하고 해당 액티비티를 강제로 벗어나는 경우에 해당됨
     * camera관련된 모든 센서 및 기능을 함수로 정의하여 호출하려고 하였지만,
     * 코드 상의 변화로 인해 해당 기능은 카메라를 벗어날 때 자동으로 실행됨
     *
     * */
    public void cameraCancel() {
        if (manualGuide.getVisibility() == View.VISIBLE) {
            manualGuide.setVisibility(View.GONE);
        } else {
            CommonDialog dialog = new CommonDialog();
            dialog.setOnCloseListener(new CommonDialog.OnCloseListener() {
                @Override
                public void onClose(DialogInterface dialog, int which, Object data) {
                    if (which == 1) {
                        //TODO Camera관련된 모든 센서 / 기능 전부 중지
                        Intent intent = new Intent(getContext(), CautionActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            dialog.showDialog(getContext(), "촬영을 취소하시겠습니까?", true, "확인", "취소");
        }
    }

    public void onBackPressed() {
        cameraCancel();
    }

    /**
     * init sensor
     * sensor intiating
     * 센서를 이용한 동작은 아직 정의되지 않음
     *
     */
    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }


    /**
     * start camera
     * 카메라 시작 시점은 권한 체크 이후, 권한 승인 시 카메라 시작 됨
     * 아래 startCamera에서는 프리뷰 생성 및 카메라 생성을 수행
     *
     * */
    public void startCamera() {
        if (preview == null) {
            preview = new Preview(this, (SurfaceView) findViewById(R.id.camView));
            preview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            ((FrameLayout) findViewById(R.id.layoutBase)).addView(preview);
            preview.setKeepScreenOn(true);
        }

        preview.setCamera(null);
        if (camera != null) {
            camera.release();
            camera = null;
        }

        int numCams = Camera.getNumberOfCameras();
        if (numCams > 0) {
            try {

                camera = Camera.open(CAMERA_FACING);
                // camera orientation
                camera.setDisplayOrientation(90);
                // get Camera parameters
                Camera.Parameters params = camera.getParameters();
                camera.startPreview();

            } catch (RuntimeException ex) {
                Log.d(TAG, "camera_not_found " + ex.getMessage().toString());
            }
        }

        preview.setCamera(camera);
    }

    /**
     * 카메라의 셔터 체크 시 콜백 함수
     * outputStream을 Log로 찍어본 결과 해당 스트림은 출력 잘 됨
     * */
    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            //byte array를 bitmap으로 변환
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            //bitmap = rotateImage(bitmap, 90);

            //bitmap을 byte array로 변환
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Constants.photoByteStream = stream.toByteArray();
            handler.sendEmptyMessage(0);

        }
    };

    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            Log.d(TAG, "onShutter'd");
            handler.sendEmptyMessage(1);
        }
    };

    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "onPictureTaken - raw");
            handler.sendEmptyMessage(2);
        }
    };


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            sensorValue = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        //timer.cancel();
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        // Surface will be destroyed when we return, so stop the preview.
        if (camera != null) {
            // Call stopPreview() to stop updating the preview surface
            camera.stopPreview();
            preview.setCamera(null);
            camera.release();
            camera = null;
        }

        ((FrameLayout) findViewById(R.id.layoutBase)).removeView(preview);
        preview = null;

    }
    /**
     * 카메라 관련 권한 승인 및 체크
     * 이 권한이 승인되지 않으면 카메라를 실행할 수 없음
     * 마쉬멜로 이상의 안드로이드 버전에서 실행되게 하였음
     * */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            /** Access denied (Permission denied)*/

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                //퍼미션을 재요청 하는 경우 - 왜 이 퍼미션이 필요한지등을 대화창에 넣어서 사용자를 설득할 수 있다.
                //대화상자에 '다시 묻지 않기' 체크박스가 자동으로 추가된다.
                Log.d(TAG, "퍼미션을 재요청 합니다.");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);

            } else {
                Log.d(TAG, "첫 퍼미션 요청입니다.");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
            }
        } else {
            /** Access already granted (Permission granted)*/

            Log.d(TAG, "Permission is granted");
            startCamera();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CAMERA: //
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    /** Access granted (Permission granted)*/
                    startCamera();

                } else {

                    /** Access denied (user denied permission)*/

                    CommonDialog dialog = new CommonDialog();
                    dialog.showDialog(this, "권한이 없습니다.");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getContext(), CautionActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);

                }
                return;
        }

    }


}
