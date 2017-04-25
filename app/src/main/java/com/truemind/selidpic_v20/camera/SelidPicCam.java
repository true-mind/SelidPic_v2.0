package com.truemind.selidpic_v20.camera;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;
import com.truemind.selidpic_v20.ui.CautionActivity;
import com.truemind.selidpic_v20.ui.GalleryActivity;
import com.truemind.selidpic_v20.ui.MainActivity;

import util.CommonDialog;

/**
 * Created by 현석 on 2017-04-24.
 */
public class SelidPicCam extends BaseActivity {

    private TextView txtSize;
    private TextView txtType;
    private TextView txtCurType;/*
    private TextView txtManual;
    private TextView txtAuto;
    private RadioButton checkManual;
    private RadioButton checkAuto;
    private RadioGroup rdoBase;
    private LinearLayout menu;*/
    private ImageButton btnCam;
    private ImageButton btnBack;
    private ImageButton btnGallery;

    //private boolean isTypeMenu = true;
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
        height = new Constants().getCurrentPhotoHeigth();

        initView();
        initListener();

    }

    public void initView() {

        txtType = (TextView) findViewById(R.id.txtType);
        txtSize = (TextView) findViewById(R.id.txtSize);
        txtCurType = (TextView) findViewById(R.id.txtCurType);/*
        txtManual = (TextView) findViewById(R.id.txtManual);
        txtAuto = (TextView) findViewById(R.id.txtAuto);
        checkManual = (RadioButton) findViewById(R.id.checkManual);
        checkAuto = (RadioButton) findViewById(R.id.checkAuto);
        rdoBase = (RadioGroup) findViewById(R.id.rdoBase);
        menu = (LinearLayout) findViewById(R.id.menu);*/
        btnCam = (ImageButton) findViewById(R.id.btnCam);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnGallery = (ImageButton) findViewById(R.id.btnGallery);

        setFontToViewBold(txtType, txtSize, txtCurType);//, txtManual, txtAuto);

        String size = width + getContext().getResources().getString(R.string.multiply) + height +
                getContext().getResources().getString(R.string.mm);
        txtType.setText(type);
        txtSize.setText(size);

    }

    public void initListener() {

        txtCurType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //isTypeMenu = modifiyTypeMenu(isTypeMenu);
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
/*
        rdoBase.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.checkAuto:
                        checkManual.setChecked(false);
                        break;
                    case R.id.checkManual:
                        checkAuto.setChecked(false);
                        break;
                }
            }
        });*/

    }

/*
    public boolean modifiyTypeMenu(boolean isMenu) {

        if (isMenu)
            menu.setVisibility(View.GONE);
        else
            menu.setVisibility(View.VISIBLE);

        return !isMenu;
    }*/

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
        dialog.showDialog(getContext(), "촬영을 취소하겠습니까?", true, "확인", "취소");
    }

    public void onBackPressed() {
        cameraCancel();
    }


}
