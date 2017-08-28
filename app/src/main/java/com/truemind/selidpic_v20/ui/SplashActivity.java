package com.truemind.selidpic_v20.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.R;

/**
 * Created by 현석 on 2017-05-02.
 */

public class SplashActivity extends BaseActivity {

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LinearLayout title = (LinearLayout)findViewById(R.id.title);
        TextView splashText = (TextView)findViewById(R.id.splashText);
        View viewLeft = findViewById(R.id.viewLeft);
        View viewRight = findViewById(R.id.viewRight);
        View viewTop = findViewById(R.id.viewTop);
        View viewBottom = findViewById(R.id.viewBottom);
        setFontToViewBold(splashText);

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.alpha);
        Animation trans_from_top = AnimationUtils.loadAnimation(getContext(),R.anim.trans_from_top);
        Animation trans_from_left = AnimationUtils.loadAnimation(getContext(),R.anim.trans_from_left);
        Animation trans_from_bottom = AnimationUtils.loadAnimation(getContext(),R.anim.trans_from_bottom);
        Animation trans_from_right = AnimationUtils.loadAnimation(getContext(),R.anim.trans_from_right);

        viewLeft.startAnimation(trans_from_top);
        viewBottom.startAnimation(trans_from_left);
        viewRight.startAnimation(trans_from_bottom);
        viewTop.startAnimation(trans_from_right);
        title.startAnimation(animation);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 3000);

    }
}