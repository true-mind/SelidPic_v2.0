package com.truemind.selidpic_v20.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;
import com.truemind.selidpic_v20.easter.MiniGame;

/**
 * Created by 현석 on 2017-05-02.
 */

public class AboutActivity extends BaseActivity{

    private TextView appId;
    private TextView version;
    private TextView license;
    private TextView update;
    private TextView back;
    private ImageView iconShow;
    private LinearLayout licenseBase;

    private boolean showLicense = false;
    private int i = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initFooter();
        initView();
        initListener();
    }

    public void initView(){
        iconShow = (ImageView)findViewById(R.id.iconShow);
        appId = (TextView)findViewById(R.id.appId);
        version = (TextView)findViewById(R.id.version);
        license = (TextView)findViewById(R.id.license);
        update = (TextView)findViewById(R.id.update);
        back = (TextView)findViewById(R.id.back);
        licenseBase = (LinearLayout)findViewById(R.id.licenseDetailBase);

        setFontToViewBold(appId, version, license, update, back);


        SpannableString licenseContent = new SpannableString(getContext().getResources().getString(R.string.license));
        licenseContent.setSpan(new UnderlineSpan(), 0, licenseContent.length(), 0);
        license.setText(licenseContent);

        SpannableString introContent = new SpannableString(getContext().getResources().getString(R.string.figure_update));
        introContent.setSpan(new UnderlineSpan(), 0, introContent.length(), 0);
        update.setText(introContent);

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
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.APP_LINK)));
            }
        });
        iconShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if(i>4){
                    /** Easter Egg!*/
                    Toast.makeText(getContext(), "Hi Sally!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MiniGame.class);
                    startActivity(intent);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        i = 0;
                    }
                }, 500);
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
