package com.truemind.selidpic_v20.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truemind.selidpic_v20.R;

import org.w3c.dom.Text;

/**
 * Created by 현석 on 2017-05-02.
 *
 * 주의사항 화면에서의 체크박스 선택에 따라
 * 카메라 시작 시 설명 다이얼로그의 출력 여부를 결정할 수 있다
 * CamManualDialog는 이와 같은 경우의 다이얼로그 출력을 담당하며,
 * 총 4장의 Image로 구성되어 있다.
 *
 */

public class CamManualDialog extends Dialog {

    private static final int SEQUENCE1 = 1;
    private static final int SEQUENCE2 = 2;
    private static final int SEQUENCE3 = 3;
    private static final int SEQUENCE4 = 4;

    private int sequence = 1;

    private LinearLayout btn;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private TextView description;
    private TextView number;
    private TextView txtBtn;

    public CamManualDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.manual_dialog);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initView();

        setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dismiss();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sequence++;
                nextSwitch(sequence);
                if(sequence>SEQUENCE4)
                    dismiss();
            }
        });

    }

    public void initView(){
        btn = (LinearLayout)findViewById(R.id.btnNext);
        image1 = (ImageView)findViewById(R.id.image1);
        image2 = (ImageView)findViewById(R.id.image2);
        image3 = (ImageView)findViewById(R.id.image3);
        image4 = (ImageView)findViewById(R.id.image4);
        description = (TextView)findViewById(R.id.description);
        number = (TextView)findViewById(R.id.number);
        txtBtn = (TextView)findViewById(R.id.txtBtn);

        setFontToViewBold(getContext(), description, number, txtBtn);
    }


    public void setFontToViewBold(Context context, TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(context.getAssets(), "BMJUA_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

    private void nextSwitch(int sequence){
        switch (sequence){
            case SEQUENCE1:
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.GONE);
                image4.setVisibility(View.GONE);
                number.setText(getContext().getResources().getString(R.string.quater));
                description.setText(getContext().getResources().getString(R.string.manual1));
                txtBtn.setText(getContext().getResources().getString(R.string.next));
                break;
            case SEQUENCE2:
                image1.setVisibility(View.GONE);
                image2.setVisibility(View.VISIBLE);
                image3.setVisibility(View.GONE);
                image4.setVisibility(View.GONE);
                number.setText(getContext().getResources().getString(R.string.quater2));
                description.setText(getContext().getResources().getString(R.string.manual2));
                txtBtn.setText(getContext().getResources().getString(R.string.next));
                break;
            case SEQUENCE3:
                image1.setVisibility(View.GONE);
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.VISIBLE);
                image4.setVisibility(View.GONE);
                number.setText(getContext().getResources().getString(R.string.quater3));
                description.setText(getContext().getResources().getString(R.string.manual3));
                txtBtn.setText(getContext().getResources().getString(R.string.next));
                break;
            case SEQUENCE4:
                image1.setVisibility(View.GONE);
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.GONE);
                image4.setVisibility(View.VISIBLE);
                number.setText(getContext().getResources().getString(R.string.quater4));
                description.setText(getContext().getResources().getString(R.string.manual4));
                txtBtn.setText(getContext().getResources().getString(R.string.camStart));
                break;
        }

    }

}
