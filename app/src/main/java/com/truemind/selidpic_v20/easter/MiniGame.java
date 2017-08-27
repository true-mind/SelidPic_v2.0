package com.truemind.selidpic_v20.easter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.R;
import com.truemind.selidpic_v20.util.CommonDialog;

import java.util.Random;

/**
 * Created by 현석 on 2017-05-04.
 */

public class MiniGame extends BaseActivity {

    private TextView score;
    private TextView timer;
    private ImageView sally;
    private int scoreNum = 0;
    Random mRand;
    private int deviceHeight;
    private int deviceWidth;
    private int time = 30;

    Thread threadTime;
    Thread threadMove;
    private boolean gamePlay = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_base);

        mRand = new Random();
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        deviceHeight = dm.heightPixels;
        deviceWidth = dm.widthPixels;

        initFooterWhite();
        initView();
        initListener();
        moveSally();

    }

    public void initView() {
        sally = (ImageView) findViewById(R.id.sally);
        score = (TextView) findViewById(R.id.score);
        timer = (TextView) findViewById(R.id.timer);
        setFontToViewBold(score, timer);

    }

    public void moveSally() {

        threadMove = new Thread() {
            public void run() {
                while (gamePlay) {
                    try {
                        sleep(700);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        };
        threadMove.start();

        threadTime = new Thread() {
            public void run() {
                while (gamePlay) {
                    try {
                        sleep(1000);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(1);
                }
            }
        };
        threadTime.start();

    }

    private Handler handler = new Handler() {
        @SuppressLint("DefaultLocale")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    sally.setX(mRand.nextInt(deviceWidth - 200));
                    sally.setY(mRand.nextInt(deviceHeight - 200));
                    break;
                case 1:
                    time--;
                    timer.setText(String.format("00:%02d", time));
                    if (time < 0) {
                        gameOver();
                        timer.setText("00:00");
                    }
                    break;
            }

        }

    };

    public void initListener() {
        sally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNum++;
                score.setText(Integer.toString(scoreNum));
            }
        });
    }

    public void gameOver() {
        gamePlay = false;
        CommonDialog dialog = new CommonDialog();
        dialog.setOnCloseListener(new CommonDialog.OnCloseListener() {
            @Override
            public void onClose(DialogInterface dialog, int which, Object data) {
                if (which == 1) {
                    dialog.dismiss();
                    finish();
                }
            }
        });
        dialog.showDialog(getContext(), "Game Over!", false,  "End");
    }
}
