package com.truemind.selidpic_v20.util;

/**
 * Created by 현석 on 2017-05-24.
 */
import android.os.SystemClock;
import android.view.View;


public abstract class OnSingleClickListener implements View.OnClickListener {

    private static final long MIN_CLICK_INTERVAL = 450;
    private long mLastClickTime;

    public abstract void onSingleClick(View v);

    @Override
    public final void onClick(View v) {
        long currentClickTime=SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;
        mLastClickTime=currentClickTime;

        if(elapsedTime<=MIN_CLICK_INTERVAL){
            return;
        }

        onSingleClick(v);
    }

}