package com.truemind.selidpic_v20.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;

/**
 * Created by 현석 on 2017-05-02.
 */

public class AboutActivity extends BaseActivity{

    private TextView appId;
    private TextView version;
    private TextView license;
    private TextView intro;
    private TextView back;
    private LinearLayout licenseBase;

    private boolean showLicense = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initFooter();
        initView();
        initListener();
    }

    public void initView(){
        appId = (TextView)findViewById(R.id.appId);
        version = (TextView)findViewById(R.id.version);
        license = (TextView)findViewById(R.id.license);
        intro = (TextView)findViewById(R.id.intro);
        back = (TextView)findViewById(R.id.back);
        licenseBase = (LinearLayout)findViewById(R.id.licenseDetailBase);
        TextView licenseDetail1 = (TextView)findViewById(R.id.licenseDetail);
        TextView licenseDetail2 = (TextView)findViewById(R.id.licenseDetail2);
        TextView licenseDetail3 = (TextView)findViewById(R.id.licenseDetail3);

        setFontToViewBold(appId, version, license, intro, back);


        SpannableString licenseContent = new SpannableString(getContext().getResources().getString(R.string.license));
        licenseContent.setSpan(new UnderlineSpan(), 0, licenseContent.length(), 0);
        license.setText(licenseContent);

        SpannableString introContent = new SpannableString(getContext().getResources().getString(R.string.intro_to_ur_fos));
        introContent.setSpan(new UnderlineSpan(), 0, introContent.length(), 0);
        intro.setText(introContent);

        SpannableString backContent = new SpannableString(getContext().getResources().getString(R.string.back));
        backContent.setSpan(new UnderlineSpan(), 0, backContent.length(), 0);
        back.setText(backContent);

    }

    public void initListener(){
        license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showLicense){
                    licenseBase.setVisibility(View.GONE);
                    showLicense = !showLicense;
                }else{
                    licenseBase.setVisibility(View.VISIBLE);
                    showLicense = !showLicense;
                }
            }
        });
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "intro", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onBackPressed() {
        finish();
    }

}
