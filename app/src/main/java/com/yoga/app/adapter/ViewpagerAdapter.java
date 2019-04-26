package com.yoga.app.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoga.app.R;

public class ViewpagerAdapter extends PagerAdapter {

    private Activity activity;
    private Integer[] imagesArray;
    private String[] namesArray;

    public ViewpagerAdapter(Activity activity, Integer[] imagesArray, String[] namesArray) {

        this.activity = activity;
        this.imagesArray = imagesArray;
        this.namesArray = namesArray;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = ((Activity) activity).getLayoutInflater();
        View viewItem = inflater.inflate(R.layout.pager_item, container, false);


        ImageView imageView = (ImageView) viewItem.findViewById(R.id.pager_image);
        imageView.setImageResource(imagesArray[position]);
        // TextView textView1 = (TextView) viewItem.findViewById(R.id.textview);
        //textView1.setText(namesArray[position]);
        ((ViewPager) container).addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imagesArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}
