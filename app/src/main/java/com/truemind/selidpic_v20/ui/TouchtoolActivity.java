package com.truemind.selidpic_v20.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;
import com.truemind.selidpic_v20.camera.Compose;
import com.truemind.selidpic_v20.camera.SelidPicCam;

import com.truemind.selidpic_v20.util.CommonDialog;
import com.truemind.selidpic_v20.util.ProgressDialog;

/**
 * Created by 현석 on 2017-04-27.
 */

public class TouchtoolActivity extends BaseActivity {

    private TextView titleName;
    private TextView titleSize;
    private ImageView finalImage;

    private LinearLayout btnHelp;
    private LinearLayout btnDraw;
    private ImageView sizeView1;

    private LinearLayout btnErase;
    private ImageView sizeView2;

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

    ProgressDialog progressDialog;
    Compose compose = new Compose();
    Bitmap background;
    Bitmap composedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchtool);

        initView();
        initListener();

        initFooter();
        initFloating();
        floatingListener(getContext());
        //getBackgroundToBitmap();
        background = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo_back);
        updateBack(background);
    }

    private Handler threadhandler = new Handler() {
        public void handleMessage(Message msg) {
            finalImage.setImageBitmap(composedImage);
            progressDialog.dismiss();
        }
    };

    public void initView() {
        titleSize = (TextView) findViewById(R.id.titleSize);
        titleName = (TextView) findViewById(R.id.titleName);
        finalImage = (ImageView) findViewById(R.id.finalImage);
        TextView subTitle1 = (TextView) findViewById(R.id.subTitle1);
        TextView txtHelp = (TextView) findViewById(R.id.txtHelp);
        btnHelp = (LinearLayout) findViewById(R.id.btnHelp);
        btnDraw = (LinearLayout) findViewById(R.id.btnDraw);
        TextView txtDraw = (TextView) findViewById(R.id.txtDraw);
        TextView toolSize1 = (TextView) findViewById(R.id.toolSize1);
        sizeView1 = (ImageView) findViewById(R.id.sizeView1);
        btnErase = (LinearLayout) findViewById(R.id.btnErase);
        TextView txtErase = (TextView) findViewById(R.id.txtErase);
        TextView toolSize2 = (TextView) findViewById(R.id.toolSize2);
        sizeView2 = (ImageView) findViewById(R.id.sizeView2);
        TextView seekbarNum1 = (TextView) findViewById(R.id.seekbarNum1);
        TextView seekbarNum2 = (TextView) findViewById(R.id.seekbarNum2);
        TextView seekbarNum3 = (TextView) findViewById(R.id.seekbarNum3);
        TextView seekbar2Num1 = (TextView) findViewById(R.id.seekbar2Num1);
        TextView seekbar2Num2 = (TextView) findViewById(R.id.seekbar2Num2);
        TextView seekbar2Num3 = (TextView) findViewById(R.id.seekbar2Num3);
        TextView subTitle2 = (TextView) findViewById(R.id.subTitle2);
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

        setFontToViewBold(titleSize, titleName, subTitle1, txtHelp, txtDraw, toolSize1, txtErase, toolSize2,
                seekbarNum1, seekbarNum2, seekbarNum3, seekbar2Num1, seekbar2Num2, seekbar2Num3,
                subTitle2, txtGetBack, subTitle3, imageSave, imageShare, imageReplay, imageCompare,
                txtCheck1, txtCheck2);
    }
/*
    public void getBackgroundToBitmap() {
        background1 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo_back);
        background2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo_back2);
        background3 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo_back3);
        background4 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo_back4);
    }*/

    public void initListener() {
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog();
                dialog.showDialog(getContext(), getResources().getString(R.string.help), getResources().getString(R.string.helper));
            }
        });
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });
        btnSos1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSos3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSos4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void updateBack(final Bitmap back) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                compose = new Compose();
                composedImage = compose.compose(getContext(), Constants.photoByteStream, Constants.photoWidth, Constants.photoHeight, 440, back);
                threadhandler.sendEmptyMessage(0);
            }
        });
        thread.start();
    }

    public void onBackPressed() {

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
}
