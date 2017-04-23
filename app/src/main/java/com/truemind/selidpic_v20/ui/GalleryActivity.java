package com.truemind.selidpic_v20.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.truemind.selidpic_v20.BaseActivity;
import com.truemind.selidpic_v20.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by 현석 on 2017-04-24.
 */
public class GalleryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initView();
        initFloating();
        floatingListener(getContext());

    }

    public void initView(){
        ImageView photoView = (ImageView) findViewById(R.id.photoView);
        Drawable bitmap = getResources().getDrawable(R.drawable.main_cam);
        photoView.setImageDrawable(bitmap);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(photoView);
        mAttacher.update();
    }
}
