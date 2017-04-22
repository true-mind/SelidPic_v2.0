package util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truemind.selidpic_v20.R;


/**
 * Created by 현석 on 2017-04-22.
 */
public class UserSizeDialog extends Dialog {

    private LinearLayout cancel;
    private LinearLayout confirm;

    private EditText edtWidth;
    private EditText edtHeight;

    public UserSizeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.size_dialog);

        initView();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtWidth.getText().toString().length()>0
                        && edtHeight.getText().toString().length()>0){

                }
                dismiss();
            }
        });

    }

    public void initView() {
        TextView title = (TextView) findViewById(R.id.title);
        TextView txtWidth = (TextView) findViewById(R.id.txtWidth);
        TextView txtHeight = (TextView) findViewById(R.id.txtHeight);
        TextView txtPrevious = (TextView) findViewById(R.id.txtPrevious);
        TextView txtPreviousValue = (TextView) findViewById(R.id.txtPreviousValue);
        TextView rule = (TextView) findViewById(R.id.rule);
        TextView txtCancel = (TextView) findViewById(R.id.txtCancel);
        TextView txtConfirm = (TextView) findViewById(R.id.txtConfirm);


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

    public interface OnCloseListener
    {
        public void onClose(DialogInterface dialog, int which, Object data);
    }

    public void setOnCloseListener(OnCloseListener listener)
    {
        mListener = listener;
    }

    protected OnCloseListener	mListener	= null;
}
