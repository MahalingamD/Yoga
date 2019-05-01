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
import com.yoga.app.model.Course_videos;

import java.util.ArrayList;

public class CourseVideoAdapter extends RecyclerView.Adapter<CourseVideoAdapter.ViewHolder> {

    FragmentActivity mContext;
    ArrayList<Course_videos> mCourseVideosArrayList;
    Callback mCallback;

    public CourseVideoAdapter(FragmentActivity aContext, ArrayList<Course_videos> courseVideosArrayList, Callback callback) {
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

    public void update(ArrayList<Course_videos> aCourseVideosArrayList) {
        mCourseVideosArrayList = aCourseVideosArrayList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CourseVideoAdapter.ViewHolder viewHolder, final int i) {

        final Course_videos courseVideos = mCourseVideosArrayList.get(i);

        viewHolder.titleTXT.setText(courseVideos.getCvideo_desc_title());
        //viewHolder.durationTXT.setText(courseVideos.getMin());
        Picasso.with(mContext).load(courseVideos.getCvideo_thumbnail()).
                placeholder(R.drawable.yoga_benefit).into(viewHolder.thumpnail);
        if(courseVideos.getCvideo_is_free() == 1){
            viewHolder.lock.setVisibility(View.GONE);
        }else{
            viewHolder.lock.setVisibility(View.VISIBLE);
        }

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                mCallback.click(i, courseVideos.getCvideo_desc_title());
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
        ImageView thumpnail, lock;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.course_list_parent_lay);
            titleTXT = itemView.findViewById(R.id.course_list_title_txt);
            durationTXT = itemView.findViewById(R.id.course_list_duration_txt);
            thumpnail = itemView.findViewById(R.id.course_list_image);
            lock = itemView.findViewById(R.id.lock_img);
        }
    }
}
