package com.mahmoud.mahmoudapp.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.mahmoud.mahmoudapp.Adapter.ImagePagerAdapter;
import com.mahmoud.mahmoudapp.Entity.Photo;
import com.mahmoud.mahmoudapp.R;

import java.util.ArrayList;

public class ImageDialog extends Dialog implements ViewPager.OnPageChangeListener {
    private Context context;
    private ArrayList<Photo> photos;
    private ViewPager viewPager;
    int num;

    public ImageDialog(Context context, ArrayList<Photo> photos, int num) {
        super (context);
        this.context = context;
        this.num = num;
        this.photos = photos;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_image);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new ImagePagerAdapter(context, photos));
        viewPager.setCurrentItem(num);
        viewPager.addOnPageChangeListener(this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}