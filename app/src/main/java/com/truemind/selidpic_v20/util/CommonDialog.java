package com.truemind.selidpic_v20.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truemind.selidpic_v20.R;

/**
 * Created by 현석 on 2017-04-23.
 *
 * 공통 dialog
 * btns로 버튼 개수 선택 가능 (최대 2개)
 * btns로 string을 받았을 때, 해당 개수에 맞는 버튼에 string을 넣음
 *
 * ex)
 *
 * new CommonDialog dialog = new CommonDialog();
 * dialog.setOnCloseListener(new UserSizeDialog.OnCloseListener() {
 *
 *      public void onClose(int which, Object data) {
 *          if(which==1){
 *              //TODO : btn1번 눌렸을 때
 *          }else if(which == 2){
 *              //TODO : btn1번 눌렸을 때
 *          }
 *      }
 * });
 * showDialog(getContext(), title, message, cancelable, btn1, btn2);
 */
public class CommonDialog {

    public final static int CANCEL = -1;
    public final static int BUTTON1 = 1;
    public final static int BUTTON2 = 2;

    /**
     * 확인 버튼만 가지고 있음(버튼 1개), title 없음
     */
    public Dialog showDialog(Context context, String message) {
        return showDialog(context, "", message, true, "확인");
    }

    /**
     * 확인 버튼만 가지고 있음(버튼 1개), title 있음
     */
    public Dialog showDialog(Context context, String title, String message) {
        return showDialog(context, title, message, true, "확인");
    }

    /**
     * title없는 dialog의 경우
     */
    public Dialog showDialog(Context context, String message, boolean cancelable, String... btns) {
        return showDialog(context, "", message, cancelable, btns);
    }

    /**
     * 공통 dialog
     * btns로 버튼 개수 선택 가능 (최대 2개)
     * btns로 string을 받았을 때, 해당 개수에 맞는 버튼에 string을 넣음
     *
     * @param context    - BaseActivity를 상속받는 activity에서 getContext()로 받아와서 사용할 것
     * @param title      - dialog의 title의 입력, 만약 title이 입력되지 않은 constructor 사용 시 titleBase - GONE
     * @param message    - dialog에 입력되는 text
     * @param cancelable - dialog cancelable
     * @param btns       - 활성화 되는 btn의 btnText
     */

    public Dialog showDialog(Context context, String title, String message, boolean cancelable, String... btns) {

        final Dialog dialog = new Dialog(context, R.style.AppThemeTranslucent);
        dialog.setContentView(R.layout.common_dialog);

        LinearLayout titleBase = (LinearLayout) dialog.findViewById(R.id.titleBase);
        TextView mTitle = (TextView) dialog.findViewById(R.id.title);
        TextView mMessage = (TextView) dialog.findViewById(R.id.message);
        LinearLayout mBtn1 = (LinearLayout) dialog.findViewById(R.id.btn1);
        LinearLayout mBtn2 = (LinearLayout) dialog.findViewById(R.id.btn2);
        TextView txtBtn1 = (TextView) dialog.findViewById(R.id.txtBtn1);
        TextView txtBtn2 = (TextView) dialog.findViewById(R.id.txtBtn2);

        setFontToViewBold(context, mTitle, txtBtn1, txtBtn2, mMessage);

        if (title.equals("")) {
            titleBase.setVisibility(View.GONE);
        } else {
            titleBase.setVisibility(View.VISIBLE);
            mTitle.setText(title);
        }

        if (message == null)
            message = "";

        mMessage.setText(message);

        dialog.setCancelable(cancelable);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                if (mListener != null)
                    mListener.onClose(dialog, CANCEL, null);
            }
        });

        mBtn1.setVisibility(View.GONE);
        mBtn2.setVisibility(View.GONE);

        switch (btns.length) {
            case 2:
                mBtn2.setVisibility(View.VISIBLE);
                mBtn2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (mListener != null)
                            mListener.onClose(dialog, BUTTON2, null);
                    }
                });
                txtBtn2.setText(btns[1]);
            case 1:
                mBtn1.setVisibility(View.VISIBLE);
                mBtn1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (mListener != null)
                            mListener.onClose(dialog, BUTTON1, null);
                    }
                });
                txtBtn1.setText(btns[0]);
        }

        dialog.show();

        return dialog;
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

    public OnCloseListener getOnCloseListener() {
        return mListener;
    }

    public interface OnCloseListener {
        public void onClose(DialogInterface dialog, int which, Object data);
    }

    public void setOnCloseListener(OnCloseListener listener) {
        mListener = listener;
    }

    protected OnCloseListener mListener = null;

}
