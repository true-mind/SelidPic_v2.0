package com.truemind.selidpic_v20;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.selidpic_v20.ui.MainActivity;

/**
 * Created by 현석 on 2017-04-20.
 */
public abstract class BaseActivity extends Activity {

    private TextView txtFooter;

    private ImageButton fab_btn;
    private ImageButton fab_home;
    private ImageButton fab_gallery;
    private ImageButton fab_about;
    private LinearLayout fab_home_base;
    private LinearLayout fab_gallery_base;
    private LinearLayout fab_about_base;
    private Drawable fab_btn_selected;
    private Drawable fab_btn_unselected;

    public boolean isMenu = false;

    /**for onBackPress*/
    public final long FINISH_INTERVAL_TIME = 2000;
    public long backPressedTime = 0;
/*

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initView();
        initListener();

    }

    protected abstract void initView();
    protected abstract void initListener();
*/

    /**
     * Typeface로 폰트 적용
     * (배민 주아체로 적용)
     *
     * @param views 적용을 원하는 모든 TextView
     *              ,로 구분하여 무제한 개수의 동시 적용 가능
     * */
    public void setFontToViewBold(TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(this.getAssets(), "BMJUA_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

    /**
     * Typeface로 폰트 적용
     * (배민 도현체로 적용)
     *
     * @param views 적용을 원하는 모든 TextView
     *              ,로 구분하여 무제한 개수의 동시 적용 가능
     * */
    public void setFontToViewBold2(TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(this.getAssets(), "BMDOHYEON_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

    /**
     * footer initiating
     * 공통 footer TextView의 typekit 적용.
     *
     * */
    public void initFooter(){

        txtFooter = (TextView)findViewById(R.id.txtFooter);
        setFontToViewBold(txtFooter);

    }

    /**
     * fab initiating
     * 공통 fab의 resource및 객체 연결
     *
     * */
    public void initFloating(){

        fab_btn = (ImageButton) findViewById(R.id.fab_btn);
        fab_home = (ImageButton) findViewById(R.id.fab_home);
        fab_gallery = (ImageButton) findViewById(R.id.fab_gallery);
        fab_about = (ImageButton) findViewById(R.id.fab_about);

        fab_home_base = (LinearLayout) findViewById(R.id.fab_home_base);
        fab_gallery_base = (LinearLayout) findViewById(R.id.fab_gallery_base);
        fab_about_base = (LinearLayout) findViewById(R.id.fab_about_base);
        fab_btn_selected = getResources().getDrawable(R.drawable.main_fbtn_s);
        fab_btn_unselected = getResources().getDrawable(R.drawable.main_fbtn);
    }

    /**
     * fab 메뉴 open - close의 상태 변환
     * */
    public boolean closeMenu(){
        fab_home_base.setVisibility(View.GONE);
        fab_gallery_base.setVisibility(View.GONE);
        fab_about_base.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            fab_btn.setBackground(fab_btn_unselected);
        }
        return false;
    }

    /**
     * fab 메뉴 close - open의 상태 변환
     * */
    public boolean openMenu(){
        fab_home_base.setVisibility(View.VISIBLE);
        fab_gallery_base.setVisibility(View.VISIBLE);
        fab_about_base.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            fab_btn.setBackground(fab_btn_selected);
        }
        return true;

    }

    /**
     * fab item의 listener 등록
     *
     * 현재는 toast 연결되어있음
     * @param context 해당 context는 listener를 BaseActivity를 상속받는 Activity에서
     *                floatingListener를 호출할 때, getContext로 context를 받아넣어 호출
     *
     * */
    public void floatingListener(final Context context){
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMenu){
                    isMenu = closeMenu();
                }else{
                    isMenu = openMenu();
                }
            }
        });

        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fab_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "gallery", Toast.LENGTH_SHORT).show();
                
            }
        });

        fab_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "about", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 현재 context를 불러오기
     * @return activity
     * */
    public Activity getContext()
    {
        return this;
    }


}
