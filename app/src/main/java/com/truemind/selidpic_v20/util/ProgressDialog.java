package com.truemind.selidpic_v20.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.truemind.selidpic_v20.R;

/**
 * Created by 현석 on 2017-05-02.
 *
 * CutomProgressDialog
 * 취소 불가능 (setCancelable - false)
 *
 */

public class ProgressDialog extends Dialog{

    public ProgressDialog(Context context){
        super(context);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.progress_dialog);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(false);

        ImageView icon = (ImageView)findViewById(R.id.progressIcon);
        TextView text1 = (TextView)findViewById(R.id.textProgress);
        TextView text2 = (TextView)findViewById(R.id.textProgress2);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.clock);
        icon.startAnimation(animation);
        setFontToViewBold(context, text1, text2);

    }

    /**
     * Typeface로 폰트 적용
     * (배민 주아체로 적용)
     *
     * @param views   적용을 원하는 모든 TextView
     *                ,로 구분하여 무제한 개수의 동시 적용 가능
     * @param context BaseActivity를 상속받는 activity에서 getContext()를 사용하여 context 날려줌
     */
    public void setFontToViewBold(Context context, TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(context.getAssets(), "BMJUA_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }
}
