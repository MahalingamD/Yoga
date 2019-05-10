package com.yoga.app.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.yoga.app.R;
import com.yoga.app.activities.CourseActivity;
import com.yoga.app.model.Category;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Category.Courses> mCourseList;
    private FragmentActivity mContext;
    private int mPosition;
    private Category mCategory;

    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;


    HorizontalAdapter(FragmentActivity aContext, Category aCategory, ArrayList<Category.Courses> aCourseList, int aPosition) {
        mContext = aContext;
        mCourseList = aCourseList;
        mPosition = aPosition;
        mCategory = aCategory;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_horizontal_layout, parent, false);

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) (width * 0.9);
        view.setLayoutParams(params);

        viewHolder = new ViewHolder(view);


        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final Category.Courses aCourses = mCourseList.get(position);
        if (viewHolder.getItemViewType() == LAYOUT_ONE) {
        } else {
            ViewHolder vaultItemHolder = (ViewHolder) viewHolder;
            Picasso.with(mContext).load(aCourses.course_image).
                    placeholder(R.drawable.yoga_benefit).fit().into(vaultItemHolder.mImageView);

            vaultItemHolder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CourseActivity.class);
                    intent.putExtra("id", Integer.parseInt(aCourses.course_id));
                    intent.putExtra("name", aCourses.course_desc_name);
                    intent.putExtra("desc", aCourses.course_desc_description);
                    mContext.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // if( mPosition == 0 )
        //  return LAYOUT_ONE;
        //  else
        return LAYOUT_TWO;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        // LinearLayout mCategoryLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.horizontal_image);
            //  mCategoryLayout = itemView.findViewById( R.id.category_layout );
        }
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {

        LinearLayout mDailyYogaLayout;

        ViewHolderOne(View itemView) {
            super(itemView);
            mDailyYogaLayout = itemView.findViewById(R.id.daily_yoga_layout);
        }
    }

}
