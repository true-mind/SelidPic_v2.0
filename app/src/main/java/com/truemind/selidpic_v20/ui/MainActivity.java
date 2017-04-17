package com.truemind.selidpic_v20.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.selidpic_v20.R;

public class MainActivity extends Activity {

    private LinearLayout btn1;
    private LinearLayout btn2;
    private LinearLayout btn3;
    private LinearLayout btn4;
    private LinearLayout btn5;
    private LinearLayout btn6;

    private ImageButton fab_btn;
    private ImageButton fab_home;
    private ImageButton fab_gallery;
    private ImageButton fab_about;
    private LinearLayout fab_home_base;
    private LinearLayout fab_gallery_base;
    private LinearLayout fab_about_base;
    private Drawable fab_btn_selected;
    private Drawable fab_btn_unselected;

    private TextView mTxtBtn1Size;
    private TextView mTxtBtn1Size2;
    private TextView mTxtBtn1Name;

    private TextView mTxtBtn3Size;
    private TextView mTxtBtn3Size2;
    private TextView mTxtBtn3Name;

    private TextView mTxtBtn4Size;
    private TextView mTxtBtn4Size2;
    private TextView mTxtBtn4Name;

    private TextView mTxtBtn5Size;
    private TextView mTxtBtn5Size2;
    private TextView mTxtBtn5Name;

    private TextView mTxtBtn2;
    private TextView mTxtBtn6;


    private boolean isMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        closeMenu();
        initListener();

    }
    public void setFontToViewBold(TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(this.getAssets(), "BMJUA_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

    public void initView(){

        btn1 = (LinearLayout) findViewById(R.id.main_btn1);
        btn2 = (LinearLayout) findViewById(R.id.main_btn2);
        btn3 = (LinearLayout) findViewById(R.id.main_btn3);
        btn4 = (LinearLayout) findViewById(R.id.main_btn4);
        btn5 = (LinearLayout) findViewById(R.id.main_btn5);
        btn6 = (LinearLayout) findViewById(R.id.main_btn6);

        fab_btn = (ImageButton) findViewById(R.id.fab_btn);
        fab_home = (ImageButton) findViewById(R.id.fab_home);
        fab_gallery = (ImageButton) findViewById(R.id.fab_gallery);
        fab_about = (ImageButton) findViewById(R.id.fab_about);

        fab_home_base = (LinearLayout) findViewById(R.id.fab_home_base);
        fab_gallery_base = (LinearLayout) findViewById(R.id.fab_gallery_base);
        fab_about_base = (LinearLayout) findViewById(R.id.fab_about_base);
        fab_btn_selected = getResources().getDrawable(R.drawable.main_fbtn_s);
        fab_btn_unselected = getResources().getDrawable(R.drawable.main_fbtn);

        mTxtBtn6 = (TextView) findViewById(R.id.txtBtn6);
        mTxtBtn2 = (TextView) findViewById(R.id.txtBtn2);

        mTxtBtn1Size = (TextView) findViewById(R.id.txtBtn1Size);
        mTxtBtn1Size2 = (TextView) findViewById(R.id.txtBtn1Size2);
        mTxtBtn1Name = (TextView) findViewById(R.id.txtBtn1Name);

        mTxtBtn3Size = (TextView) findViewById(R.id.txtBtn3Size);
        mTxtBtn3Size2 = (TextView) findViewById(R.id.txtBtn3Size2);
        mTxtBtn3Name = (TextView) findViewById(R.id.txtBtn3Name);

        mTxtBtn4Size = (TextView) findViewById(R.id.txtBtn4Size);
        mTxtBtn4Size2 = (TextView) findViewById(R.id.txtBtn4Size2);
        mTxtBtn4Name = (TextView) findViewById(R.id.txtBtn4Name);

        mTxtBtn5Size = (TextView) findViewById(R.id.txtBtn5Size);
        mTxtBtn5Size2 = (TextView) findViewById(R.id.txtBtn5Size2);
        mTxtBtn5Name = (TextView) findViewById(R.id.txtBtn5Name);

        setFontToViewBold(mTxtBtn1Size, mTxtBtn1Size2, mTxtBtn1Name, mTxtBtn3Size, mTxtBtn3Size2, mTxtBtn3Name,
                mTxtBtn4Size, mTxtBtn4Size2, mTxtBtn4Name, mTxtBtn5Size, mTxtBtn5Size2, mTxtBtn5Name,
                mTxtBtn2,mTxtBtn6);


    }

    public void closeMenu(){
        fab_home_base.setVisibility(View.GONE);
        fab_gallery_base.setVisibility(View.GONE);
        fab_about_base.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            fab_btn.setBackground(fab_btn_unselected);
        }
        isMenu = false;
    }

    public void openMenu(){
        fab_home_base.setVisibility(View.VISIBLE);
        fab_gallery_base.setVisibility(View.VISIBLE);
        fab_about_base.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            fab_btn.setBackground(fab_btn_selected);
        }
        isMenu = true;
    }

    public void initListener(){

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "4", Toast.LENGTH_SHORT).show();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "5", Toast.LENGTH_SHORT).show();
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "6", Toast.LENGTH_SHORT).show();
            }
        });

        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMenu){
                    closeMenu();
                }else{
                    openMenu();
                }
            }
        });

        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fab_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "gallery", Toast.LENGTH_SHORT).show();
            }
        });

        fab_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "about", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
