package com.truemind.selidpic_v20.camera;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.View;
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
import com.truemind.selidpic_v20.ui.TouchtoolActivity;

import com.truemind.selidpic_v20.util.CamManualDialog;
import com.truemind.selidpic_v20.util.CamSettingDialog;
import com.truemind.selidpic_v20.util.CommonDialog;
import com.truemind.selidpic_v20.util.UserSizeDialog;

/**
 * Created by 현석 on 2017-04-24.
 */
public class SelidPicCam extends BaseActivity implements SurfaceHolder.Callback, Camera.PreviewCallback, SensorEventListener {

    private TextView txtSize;
    private TextView txtType;
    private TextView txtCurType;
    private ImageButton btnCam;
    private ImageButton btnBack;
    private ImageButton btnGallery;
    private ImageButton btnSetting;
    private LinearLayout manualGuide;
    private View camGuide;

    private VideoView camView;

    private boolean isTypeManual = true;
    private String type;
    private int width;
    private int height;
    private int guideHeight;
    private int guidewidth;
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
        camView = (VideoView) findViewById(R.id.camView);
        manualGuide = (LinearLayout) findViewById(R.id.manual_guide);
        /**camManualDialog는 설정에서의 guide와는 다른 guide
         * 만약 설정에서의 guide가 각 버튼에 대한 설명이라면
         * 이 다이얼로그에서의 guide는 각 기능에 대한 설명(자세함)*/
        if (Constants.camManualGuide) {
            CamManualDialog dialog = new CamManualDialog(getContext());
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
     *
     * */
    public void settingUpdate(){
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
        guideHeight = (dm.widthPixels * height) / width;
        guidewidth = (dm.heightPixels * width) / height;

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) camGuide.getLayoutParams();
        if (guidewidth > dm.widthPixels) {
            lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
            lp.height = guideHeight;
        } else {
            lp.width = guidewidth;
            lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
        }
        camGuide.setLayoutParams(lp);
    }

    /**listener initiating
     * setting 버튼 클릭 시 setting dialog 출력
     * setting dialog의 버튼에 따라 가이드가 출력되거나 현재 설정 값을 저장하는 것을 선택
     * 가이드는 취소 버튼 혹은 뷰를 터치하면 해제되어 사라짐
     * 설정 값 저장시 settingUpdate 호출
     * */
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

        btnCam.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TouchtoolActivity.class);
                startActivity(intent);
                finish();

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

    public void cameraCancel() {

        if(manualGuide.getVisibility()==View.VISIBLE){
            manualGuide.setVisibility(View.GONE);
        }else{
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


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
