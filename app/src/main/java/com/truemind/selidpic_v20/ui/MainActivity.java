package com.truemind.selidpic_v20.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.R;

public class MainActivity extends BaseActivity {

    private LinearLayout btn1;
    private LinearLayout btn2;
    private LinearLayout btn3;
    private LinearLayout btn4;
    private LinearLayout btn5;
    private LinearLayout btn6;

    private TextView mTxtBtn1Size;
    private TextView mTxtBtn1Size2;
    private TextView mTxtBtn1Name;

    private TextView mTxtBtn3Size;
    private TextView mTxtBtn3Size2;
    private TextView mTxtBtn3Name;

    private TextView mTxtBtn4Size;
    private TextView mTxtBtn4Size2;
    private TextView mTxtBtn4Name;

    private TextView mTxtBtn5Size;
    private TextView mTxtBtn5Size2;
    private TextView mTxtBtn5Name;

    private TextView mTxtBtn2;
    private TextView mTxtBtn6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

        initFooter();
        initFloating();
        floatingListener(getContext());
    }

    public void initView(){

        btn1 = (LinearLayout) findViewById(R.id.main_btn1);
        btn2 = (LinearLayout) findViewById(R.id.main_btn2);
        btn3 = (LinearLayout) findViewById(R.id.main_btn3);
        btn4 = (LinearLayout) findViewById(R.id.main_btn4);
        btn5 = (LinearLayout) findViewById(R.id.main_btn5);
        btn6 = (LinearLayout) findViewById(R.id.main_btn6);

        mTxtBtn6 = (TextView) findViewById(R.id.txtBtn6);
        mTxtBtn2 = (TextView) findViewById(R.id.txtBtn2);

        mTxtBtn1Size = (TextView) findViewById(R.id.txtBtn1Size);
        mTxtBtn1Size2 = (TextView) findViewById(R.id.txtBtn1Size2);
        mTxtBtn1Name = (TextView) findViewById(R.id.txtBtn1Name);

        mTxtBtn3Size = (TextView) findViewById(R.id.txtBtn3Size);
        mTxtBtn3Size2 = (TextView) findViewById(R.id.txtBtn3Size2);
        mTxtBtn3Name = (TextView) findViewById(R.id.txtBtn3Name);

        mTxtBtn4Size = (TextView) findViewById(R.id.txtBtn4Size);
        mTxtBtn4Size2 = (TextView) findViewById(R.id.txtBtn4Size2);
        mTxtBtn4Name = (TextView) findViewById(R.id.txtBtn4Name);

        mTxtBtn5Size = (TextView) findViewById(R.id.txtBtn5Size);
        mTxtBtn5Size2 = (TextView) findViewById(R.id.txtBtn5Size2);
        mTxtBtn5Name = (TextView) findViewById(R.id.txtBtn5Name);

        setFontToViewBold(mTxtBtn1Size, mTxtBtn1Size2, mTxtBtn1Name, mTxtBtn3Size, mTxtBtn3Size2, mTxtBtn3Name,
                mTxtBtn4Size, mTxtBtn4Size2, mTxtBtn4Name, mTxtBtn5Size, mTxtBtn5Size2, mTxtBtn5Name,
                mTxtBtn2,mTxtBtn6);


    }



    public void initListener(){

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "4", Toast.LENGTH_SHORT).show();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "5", Toast.LENGTH_SHORT).show();
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "6", Toast.LENGTH_SHORT).show();
            }
        });



    }

}
