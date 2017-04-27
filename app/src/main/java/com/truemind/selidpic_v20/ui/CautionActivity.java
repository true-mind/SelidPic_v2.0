package com.truemind.selidpic_v20.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;
import com.truemind.selidpic_v20.camera.SelidPicCam;

/**
 * Created by 현석 on 2017-04-21.
 */
public class CautionActivity extends BaseActivity {

    private LinearLayout btnStart;
    private LinearLayout btnBack;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caution);

        Intent intent = getIntent();

        if (intent.hasExtra("type")) {
            type = intent.getStringExtra("type");
            new Constants().setCurrentPhotoAll(type, getTypeMatchedSize(type)[0], getTypeMatchedSize(type)[1]);
        }

        Log.d("MyTag", new Constants().getCurrentPhotoType());
        Log.d("MyTag", Integer.toString(new Constants().getCurrentPhotoWidth()));
        Log.d("MyTag", Integer.toString(new Constants().getCurrentPhotoHeight()));

        initView();
        initListener();

        initFooter();
        initFloating();
        floatingListener(getContext());
    }

    public void initView() {

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

        btnBack = (LinearLayout) findViewById(R.id.btnBack);
        btnStart = (LinearLayout) findViewById(R.id.btnStart);

        setFontToViewBold(rule1, rule2, rule3, rule4, rule5, rule6, rule7, txtBtnBack, txtBtnStart, txtCheck1, txtCheck2);
        setFontToViewBold2(rule);
    }

    /**
     * 현재 전달된 type에 따라 Constants에 있는 width와 height값을 받아옴
     * 전달된 type에 따라 받아온 width와 height는 setter로 현재 값을 받아오기 때문에,(현재 activity에서)
     * type 재전달로 인해 값이 변경되는 경우를 제외하고는 getter로 받아오면된다.
     *
     * @param type - 현재 전달받은 type의 값. 만약 type을 전달받지 못한 상태라면(main에서 이동된 경우가 아니라면)
     *             아래의 method를 거치지 않는다.
     * @return typeMatchedSize - int[]로, 해당 값의 [0]에는 width가, [1]에는 height가 저장된다.
     */
    private int[] getTypeMatchedSize(String type) {

        int[] typeMatchedSize = new int[2];

        switch (type) {

            case Constants.PHOTO_TYPE1:
                typeMatchedSize[0] = Constants.PHOTO_TYPE1_WIDTH;
                typeMatchedSize[1] = Constants.PHOTO_TYPE1_HEIGHT;
                break;

            case Constants.PHOTO_TYPE2:
                typeMatchedSize[0] = Constants.PHOTO_TYPE2_WIDTH;
                typeMatchedSize[1] = Constants.PHOTO_TYPE2_HEIGHT;
                break;

            case Constants.PHOTO_TYPE3:
                typeMatchedSize[0] = Constants.PHOTO_TYPE3_WIDTH;
                typeMatchedSize[1] = Constants.PHOTO_TYPE3_HEIGHT;
                break;

            case Constants.PHOTO_TYPE4:
                typeMatchedSize[0] = Constants.PHOTO_TYPE4_WIDTH;
                typeMatchedSize[1] = Constants.PHOTO_TYPE4_HEIGHT;
                break;

            case Constants.PHOTO_TYPE5:
                typeMatchedSize[0] = Constants.PHOTO_TYPE5_WIDTH;
                typeMatchedSize[1] = Constants.PHOTO_TYPE5_HEIGHT;
                break;

        }

        return typeMatchedSize;

    }


    public void initListener() {

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
                Intent intent = new Intent(getContext(), SelidPicCam.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void onBackPressed() {
        Intent Intent = new Intent(getContext(), MainActivity.class);
        startActivity(Intent);
        finish();
    }
}
