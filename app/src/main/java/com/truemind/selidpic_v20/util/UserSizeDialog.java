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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.selidpic_v20.Constants;
import com.truemind.selidpic_v20.R;


/**
 * Created by 현석 on 2017-04-22.
 */
public class UserSizeDialog extends Dialog {

    public final static int CANCEL = -1;
    public final static int BUTTON1 = 1;
    public final static int BUTTON2 = 2;

    private LinearLayout cancel;
    private LinearLayout confirm;

    private EditText edtWidth;
    private EditText edtHeight;

    private TextView txtPreviousValue;

    public UserSizeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.size_dialog);

        initView();

        /** dialog on cancel (dismiss)*/
        setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                if (mListener != null)
                    mListener.onClose(CANCEL, null);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtWidth.getText().toString().length() > 0
                        && edtHeight.getText().toString().length() > 0) {
                    if (Integer.parseInt(edtWidth.getText().toString()) <
                            Integer.parseInt(edtHeight.getText().toString())) {
                        new Constants().setUserTypeSize(Integer.parseInt(edtWidth.getText().toString()),
                                Integer.parseInt(edtHeight.getText().toString()));
                        if (mListener != null)
                            mListener.onClose(BUTTON1, null);
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.userSizeRule), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
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
        TextView txtWidth = (TextView) findViewById(R.id.txtWidth);
        TextView txtHeight = (TextView) findViewById(R.id.txtHeight);
        TextView txtPrevious = (TextView) findViewById(R.id.txtPrevious);
        TextView rule = (TextView) findViewById(R.id.rule);
        TextView txtCancel = (TextView) findViewById(R.id.txtCancel);
        TextView txtConfirm = (TextView) findViewById(R.id.txtConfirm);

        txtPreviousValue = (TextView) findViewById(R.id.txtPreviousValue);
        getPreviousValue();

        cancel = (LinearLayout) findViewById(R.id.cancel);
        confirm = (LinearLayout) findViewById(R.id.confirm);
        edtWidth = (EditText) findViewById(R.id.edtWidth);
        edtHeight = (EditText) findViewById(R.id.edtHeight);

        setFontToViewBold(getContext(), txtWidth, txtHeight, txtPrevious, txtPreviousValue, rule, txtCancel, txtConfirm, edtWidth, edtHeight);
        setFontToViewBold2(getContext(), title);

    }

    public void setFontToViewBold(Context context, TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(context.getAssets(), "BMJUA_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

    public void setFontToViewBold2(Context context, TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(context.getAssets(), "BMDOHYEON_ttf.ttf");

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

    public void setOnCloseListener(OnCloseListener listener) {
        mListener = listener;
    }

    protected OnCloseListener mListener = null;

    /**
     * get Previously set values
     * if values not exist, set txtPreviousValue - none,
     * else(if exist), set txtPreviousValue - Constants values.
     * <p/>
     * spannableString for underlines text resource
     * if text onClick event occurs, set width, height - Constants value.
     */
    public void getPreviousValue() {
        if (Constants.PHOTO_TYPE2_WIDTH > 0 && Constants.PHOTO_TYPE2_HEIGHT > 0) {
            SpannableString content = new SpannableString(Constants.PHOTO_TYPE2_WIDTH +
                    getContext().getResources().getString(R.string.multiply) + Constants.PHOTO_TYPE2_HEIGHT +
                    getContext().getResources().getString(R.string.mm));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            txtPreviousValue.setText(content);
            txtPreviousValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtWidth.setText(Integer.toString(Constants.PHOTO_TYPE2_WIDTH));
                    edtHeight.setText(Integer.toString(Constants.PHOTO_TYPE2_HEIGHT));
                }
            });
        } else {
            txtPreviousValue.setText(getContext().getResources().getString(R.string.none));
        }
    }

}
