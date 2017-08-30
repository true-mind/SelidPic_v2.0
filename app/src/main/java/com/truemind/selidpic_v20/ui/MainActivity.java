package com.truemind.selidpic_v20.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;
import com.truemind.selidpic_v20.ad.QuitAdDialogFactory;
import com.truemind.selidpic_v20.util.UserSizeDialog;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MyTag";

    private LinearLayout btn1;
    private LinearLayout btn2;
    private LinearLayout btn3;
    private LinearLayout btn4;
    private LinearLayout btn5;
    private LinearLayout btn6;

    // Quit Ad Dialog
    private AdRequest mQuitAdRequest;
    private AdView mQuitPortraitAdView;
    private AdView mQuitLandscapeAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

        initFooter();
        initFloating();
        floatingListener(getContext());

        MobileAds.initialize(getContext(), Constants.APP_UNIT_ID);
        initQuitAdView();
    }


    private void initQuitAdView() {
        mQuitAdRequest = new AdRequest.Builder()
                .addTestDevice("CC2A80F3D7ED02504314B961F17B72E8")
                .addTestDevice("E1D20670773A41C2F5FB8D2122C22BB4")
                .build();
        mQuitPortraitAdView = QuitAdDialogFactory.initPortraitAdView(this, Constants.AD_UNIT_ID,
                mQuitAdRequest);
        mQuitLandscapeAdView = QuitAdDialogFactory.initLandscapeAdView(this, Constants.AD_UNIT_ID,
                mQuitAdRequest);
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
                         Log.d(TAG, getString(R.string.userCancel));
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
                Intent intent = new Intent(getContext(), AboutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mQuitPortraitAdView.resume();
        mQuitLandscapeAdView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mQuitPortraitAdView.pause();
        mQuitLandscapeAdView.pause();
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

/*

    @Override
    public void onBackPressed() {
        if (InternetConnectionManager.isNetworkAvailable(this)) {

            QuitAdDialogFactory.Options options =
                    new QuitAdDialogFactory.Options(this, mQuitPortraitAdView);
            options.isAppCompat = false;
            options.isRotatable = true;
            options.landscapeAdView = mQuitLandscapeAdView;

            Dialog adDialog = QuitAdDialogFactory.makeDialog(options);
            if (adDialog != null) {
                adDialog.show();
                // make AdView again for next quit dialog
                // prevent child reference
                // 가로 모드는 7.5% 가량 사용하고 있기에 속도를 위해서 광고를 계속 불러오지 않음
                mQuitPortraitAdView = QuitAdDialogFactory.initPortraitAdView(this,
                        Constants.AD_UNIT_ID, mQuitAdRequest);
            } else {
                // just finish activity when dialog is null
                super.onBackPressed();
            }
        } else {
            // just finish activity when no ad item is bought
            super.onBackPressed();
        }
    }
*/

}
