package com.truemind.selidpic_v20.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;

/**
 * Created by 현석 on 2017-04-21.
 */
public class CautionActivity extends BaseActivity {

    private LinearLayout btnStart;
    private LinearLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caution);

        getIntent().getStringExtra("type");
        Toast.makeText(getContext(), getIntent().getStringExtra("type"), Toast.LENGTH_SHORT).show();

        initView();
        initListener();

        initFooter();
        initFloating();
        floatingListener(getContext());
    }

    public void initView(){

        TextView rule = (TextView) findViewById(R.id.rule);
        TextView rule1 = (TextView) findViewById(R.id.rule1);
        TextView rule2 = (TextView) findViewById(R.id.rule2);
        TextView rule3 = (TextView) findViewById(R.id.rule3);
        TextView rule4 = (TextView) findViewById(R.id.rule4);
        TextView rule5 = (TextView) findViewById(R.id.rule5);
        TextView rule6 = (TextView) findViewById(R.id.rule6);
        TextView rule7 = (TextView) findViewById(R.id.rule7);

        TextView txtBtnStart = (TextView) findViewById(R.id.txtBtnStart);
        TextView txtBtnBack = (TextView) findViewById(R.id.txtBtnBack);

        TextView txtCheck1 = (TextView) findViewById(R.id.txtCheck1);
        TextView txtCheck2 = (TextView) findViewById(R.id.txtCheck2);

        btnBack = (LinearLayout)findViewById(R.id.btnBack);
        btnStart = (LinearLayout)findViewById(R.id.btnStart);

        setFontToViewBold(rule1, rule2, rule3, rule4, rule5, rule6, rule7, txtBtnBack, txtBtnStart, txtCheck1, txtCheck2);
        setFontToViewBold2(rule);
    }

    public void initListener(){

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Start", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onBackPressed() {
        Intent Intent = new Intent(getContext(), MainActivity.class);
        startActivity(Intent);
        finish();
    }
}
