package com.truemind.selidpic_v20.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;

import util.CommonDialog;
import util.UserSizeDialog;

public class MainActivity extends BaseActivity {

    private LinearLayout btn1;
    private LinearLayout btn2;
    private LinearLayout btn3;
    private LinearLayout btn4;
    private LinearLayout btn5;
    private LinearLayout btn6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

        initFooter();
        initFloating();
        floatingListener(getContext());
        CommonDialog dialog = new CommonDialog();
    }

    /**View initiating, get all textviews for typekit*/
    public void initView(){

        btn1 = (LinearLayout) findViewById(R.id.main_btn1);
        btn2 = (LinearLayout) findViewById(R.id.main_btn2);
        btn3 = (LinearLayout) findViewById(R.id.main_btn3);
        btn4 = (LinearLayout) findViewById(R.id.main_btn4);
        btn5 = (LinearLayout) findViewById(R.id.main_btn5);
        btn6 = (LinearLayout) findViewById(R.id.main_btn6);

        TextView mTxtBtn6 = (TextView) findViewById(R.id.txtBtn6);
        TextView mTxtBtn2 = (TextView) findViewById(R.id.txtBtn2);

        TextView mTxtBtn1Size = (TextView) findViewById(R.id.txtBtn1Size);
        TextView mTxtBtn1Size2 = (TextView) findViewById(R.id.txtBtn1Size2);
        TextView mTxtBtn1Name = (TextView) findViewById(R.id.txtBtn1Name);

        TextView mTxtBtn3Size = (TextView) findViewById(R.id.txtBtn3Size);
        TextView mTxtBtn3Size2 = (TextView) findViewById(R.id.txtBtn3Size2);
        TextView mTxtBtn3Name = (TextView) findViewById(R.id.txtBtn3Name);

        TextView mTxtBtn4Size = (TextView) findViewById(R.id.txtBtn4Size);
        TextView mTxtBtn4Size2 = (TextView) findViewById(R.id.txtBtn4Size2);
        TextView mTxtBtn4Name = (TextView) findViewById(R.id.txtBtn4Name);

        TextView mTxtBtn5Size = (TextView) findViewById(R.id.txtBtn5Size);
        TextView mTxtBtn5Size2 = (TextView) findViewById(R.id.txtBtn5Size2);
        TextView mTxtBtn5Name = (TextView) findViewById(R.id.txtBtn5Name);

        setFontToViewBold(mTxtBtn1Size, mTxtBtn1Size2, mTxtBtn1Name, mTxtBtn3Size, mTxtBtn3Size2, mTxtBtn3Name,
                mTxtBtn4Size, mTxtBtn4Size2, mTxtBtn4Name, mTxtBtn5Size, mTxtBtn5Size2, mTxtBtn5Name,
                mTxtBtn2, mTxtBtn6);

    }

    /**initiating listener*/
    public void initListener(){

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CautionActivity.class);
                intent.putExtra("type", Constants.PHOTO_TYPE1);
                startActivity(intent);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserSizeDialog dialog = new UserSizeDialog(getContext());
                dialog.setOnCloseListener(new UserSizeDialog.OnCloseListener() {
                    @Override
                    public void onClose(int which, Object data) {
                        /** 1 for confirm, -1, 2 for cancel*/
                        if(which==1){
                            Intent intent = new Intent(getContext(), CautionActivity.class);
                            intent.putExtra("type", Constants.PHOTO_TYPE2);
                            startActivity(intent);
                            finish();
                        }else{
                         Log.d("MyTag", getString(R.string.userCancel));
                        }
                    }
                });
                dialog.show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CautionActivity.class);
                intent.putExtra("type", Constants.PHOTO_TYPE3);
                startActivity(intent);
                finish();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CautionActivity.class);
                intent.putExtra("type", Constants.PHOTO_TYPE4);
                startActivity(intent);
                finish();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CautionActivity.class);
                intent.putExtra("type", Constants.PHOTO_TYPE5);
                startActivity(intent);
                finish();
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "6", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if(0<=intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getContext(), R.string.exitMessage,Toast.LENGTH_SHORT).show();
        }
    }

}
