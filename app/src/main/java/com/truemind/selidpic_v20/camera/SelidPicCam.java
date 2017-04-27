package com.truemind.selidpic_v20.camera;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;
import com.truemind.selidpic_v20.ui.CautionActivity;
import com.truemind.selidpic_v20.ui.GalleryActivity;
import com.truemind.selidpic_v20.ui.TouchtoolActivity;

import util.CommonDialog;

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

    private VideoView camView;

    private boolean isTypeManual = true;
    private String type;
    private int width;
    private int height;

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

    public void initView() {

        txtType = (TextView) findViewById(R.id.txtType);
        txtSize = (TextView) findViewById(R.id.txtSize);
        txtCurType = (TextView) findViewById(R.id.txtCurType);
        btnCam = (ImageButton) findViewById(R.id.btnCam);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnGallery = (ImageButton) findViewById(R.id.btnGallery);
        camView = (VideoView)findViewById(R.id.camView);

        setFontToViewBold(txtType, txtSize, txtCurType);

        String size = width + getContext().getResources().getString(R.string.multiply) + height +
                getContext().getResources().getString(R.string.mm);
        txtType.setText(type);
        txtSize.setText(size);

    }

    public void initListener() {

        txtCurType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTypeManual){
                    txtCurType.setText("A");
                    txtCurType.setTextColor(getResources().getColor(R.color.colorAuto));
                    isTypeManual= !isTypeManual;
                }else{
                    txtCurType.setText("M");
                    txtCurType.setTextColor(getResources().getColor(R.color.colorManual));
                    isTypeManual= !isTypeManual;
                }
            }
        });

        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TouchtoolActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GalleryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraCancel();
            }
        });

    }

    public void cameraCancel(){

        CommonDialog dialog = new CommonDialog();
        dialog.setOnCloseListener(new CommonDialog.OnCloseListener() {
            @Override
            public void onClose(DialogInterface dialog, int which, Object data) {
                if(which==1){
                    Intent intent = new Intent(getContext(), CautionActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        dialog.showDialog(getContext(), "촬영을 취소하시겠습니까?", true, "확인", "취소");
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
