package com.yoga.app.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoga.app.R;
import com.yoga.app.activities.CourseActivity;
import com.yoga.app.model.Course;

import java.util.ArrayList;

public class CoursesListAdapter extends RecyclerView.Adapter<CoursesListAdapter.ViewHolder> {

    FragmentActivity mContext;
    ArrayList<Course> mCourseVideosArrayList;
    Callback mCallback;

    public CoursesListAdapter(FragmentActivity aContext, ArrayList<Course> courseVideosArrayList, Callback callback) {
        mContext = aContext;
        mCourseVideosArrayList = courseVideosArrayList;
        mCallback = callback;
    }

    @NonNull
    @Override
    public CoursesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inflate_course_list_item, parent, false);
        return new CoursesListAdapter.ViewHolder(itemView);
    }

    public void update(ArrayList<Course> courseVideosArrayList) {
        mCourseVideosArrayList = courseVideosArrayList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesListAdapter.ViewHolder viewHolder, final int i) {

        final Course courseVideos = mCourseVideosArrayList.get(i);

        viewHolder.titleTXT.setText(courseVideos.getCourseDescName());
        viewHolder.descTXT.setText(courseVideos.getCourseDescDescription());
        viewHolder.TypeTXT.setText(courseVideos.getCourseLevel());
        if (courseVideos.getCourseIsFree() == 1) {
            viewHolder.paymentTXT.setText("Free");
        } else {
            viewHolder.paymentTXT.setText("Paid");
        }
        //viewHolder.videoCountTXT.setText(courseVideos.());
        Picasso.with(mContext).load(courseVideos.getCourseImage()).
                placeholder(R.drawable.yoga_benefit).into(viewHolder.thumpnail);

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
        viewHolder.startNowTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CourseActivity.class);
                intent.putExtra("id", courseVideos.getCourseId());
                intent.putExtra("name", courseVideos.getCourseDescName());
                intent.putExtra("desc", courseVideos.getCourseDescAbout());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mCourseVideosArrayList.size();
    }

    public interface Callback {
        void click(int aPostion, String s);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView parent;
        TextView titleTXT, videoCountTXT, descTXT, TypeTXT, startNowTXT, paymentTXT;
        ImageView thumpnail;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.main_course_lay);
            titleTXT = itemView.findViewById(R.id.main_course_list_title_txt);
            videoCountTXT = itemView.findViewById(R.id.main_course_list_video_count_txt);
            thumpnail = itemView.findViewById(R.id.main_course_list_image);
            descTXT = itemView.findViewById(R.id.main_course_list_desc_txt);
            TypeTXT = itemView.findViewById(R.id.course_list_type_txt);
            startNowTXT = itemView.findViewById(R.id.course_startnow_txt);
            paymentTXT = itemView.findViewById(R.id.payment_type);
        }
    }
}
