package com.truemind.selidpic_v20.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;

/**
 * Created by 현석 on 2017-05-01.
 */

public class CamSettingDialog extends Dialog {

    public final static int CANCEL = -1;
    public final static int BUTTON1 = 1;
    public final static int BUTTON2 = 2;

    private LinearLayout btn1;
    private LinearLayout btn2;

    private EditText edtTime;

    private CheckBox checkBox1;
    private CheckBox checkBox2;

    public CamSettingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_dialog);

        initView();

        /** dialog on cancel (dismiss)*/
        setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                if (mListener != null)
                    mListener.onClose(CANCEL, null);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox1.isChecked())
                    Constants.camTitleInvalidate = true;
                else
                    Constants.camTitleInvalidate = false;

                if (checkBox2.isChecked())
                    Constants.camGuideValidate = true;
                else
                    Constants.camGuideValidate = false;

                if (edtTime.getText().toString().length() > 0) {
                    Constants.camTimerTime = Integer.parseInt(edtTime.getText().toString());
                }
                if (mListener != null)
                    mListener.onClose(BUTTON1, null);
                dismiss();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onClose(BUTTON2, null);
                dismiss();
            }
        });

    }

    public void initView() {
        TextView title = (TextView) findViewById(R.id.title);
        TextView item1 = (TextView) findViewById(R.id.item1);
        TextView item2 = (TextView) findViewById(R.id.item2);
        TextView item3 = (TextView) findViewById(R.id.item3);
        TextView sec = (TextView) findViewById(R.id.sec);
        TextView txtBtn1 = (TextView) findViewById(R.id.txtBtn1);
        TextView txtBtn2 = (TextView) findViewById(R.id.txtBtn2);

        btn1 = (LinearLayout) findViewById(R.id.btn1);
        btn2 = (LinearLayout) findViewById(R.id.btn2);
        edtTime = (EditText) findViewById(R.id.edtTime);

        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);

        if (Constants.camTitleInvalidate) {
            checkBox1.setChecked(true);
        }else{
            checkBox1.setChecked(false);
        }
        if (Constants.camGuideValidate) {
            checkBox2.setChecked(true);
        }else{
            checkBox2.setChecked(false);
        }
        edtTime.setText(Integer.toString(Constants.camTimerTime));

        setFontToViewBold(getContext(), title, item1, item2, item3, sec, txtBtn1, txtBtn2, edtTime);

    }

    public void setFontToViewBold(Context context, TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(context.getAssets(), "BMJUA_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

    /**
     * onCloseListener
     * if dialog dismissed, event occurs
     */
    public interface OnCloseListener {
        void onClose(int which, Object data);
    }

    public void setOnCloseListener(CamSettingDialog.OnCloseListener listener) {
        mListener = listener;
    }

    protected CamSettingDialog.OnCloseListener mListener = null;


}
