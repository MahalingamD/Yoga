package com.yoga.app.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.model.Gender;

import java.util.ArrayList;

public class GenderAdapter extends ArrayAdapter<Gender> {

    Context mContext;
    ArrayList<Gender> mGenderList;

    public GenderAdapter(FragmentActivity activity, int simple_spinner_item, ArrayList<Gender> mGenderArrayList) {
        super(activity, simple_spinner_item, mGenderArrayList);

        mContext = activity;
        mGenderList = mGenderArrayList;
    }

    @Override
    public int getCount() {
        return mGenderList.size();
    }

    @Override
    public Gender getItem(int position) {
        return mGenderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(ContextCompat.getColor(mContext, R.color.color_62));

        Typeface typeface = ResourcesCompat.getFont(mContext, R.font.gilroy_regular);
        label.setTypeface(typeface);

        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(mGenderList.get(position).gender_name);

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);

        label.setPadding(10, 16, 10, 16);
        label.setTextColor(ContextCompat.getColor(mContext, R.color.color_62));
        Typeface typeface = ResourcesCompat.getFont(mContext, R.font.gilroy_regular);
        label.setTypeface(typeface);
        label.setText(mGenderList.get(position).gender_name);
        return label;
    }

}
