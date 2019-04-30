package com.yoga.app.adapter;

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
import com.yoga.app.model.CourseVideos;

import java.util.ArrayList;

public class CourseVideoAdapter extends RecyclerView.Adapter<CourseVideoAdapter.ViewHolder> {

    FragmentActivity mContext;
    ArrayList<CourseVideos> mCourseVideosArrayList;
    Callback mCallback;

    public CourseVideoAdapter(FragmentActivity aContext, ArrayList<CourseVideos> courseVideosArrayList, Callback callback) {
        mContext = aContext;
        mCourseVideosArrayList = courseVideosArrayList;
        mCallback = callback;
    }

    @NonNull
    @Override
    public CourseVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inflate_course_video_list, parent, false);
        return new CourseVideoAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseVideoAdapter.ViewHolder viewHolder, final int i) {

        final CourseVideos courseVideos = mCourseVideosArrayList.get(i);

        viewHolder.titleTXT.setText(courseVideos.getTitle());
        viewHolder.durationTXT.setText(courseVideos.getMin());
        Picasso.with(mContext).load(courseVideos.getThumnail_url()).
                placeholder(R.drawable.yoga_benefit).into(viewHolder.thumpnail);

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                mCallback.click(i, courseVideos.getTitle());
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
        TextView titleTXT, durationTXT;
        ImageView thumpnail;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.main_course_lay);
            titleTXT = itemView.findViewById(R.id.course_list_title_txt);
            durationTXT = itemView.findViewById(R.id.course_list_duration_txt);
            thumpnail = itemView.findViewById(R.id.course_list_image);
        }
    }
}
