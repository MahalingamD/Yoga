package com.yoga.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoga.app.R;
import com.yoga.app.adapter.CourseVideoAdapter;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.model.Course;
import com.yoga.app.model.CourseDetail;
import com.yoga.app.model.Course_videos;
import com.yoga.app.service.RetrofitInstance;
import com.yoga.app.utils.KToast;
import com.yoga.app.utils.Prefs;
import com.yoga.app.utils.ProgressDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.yoga.app.constant.PrefConstants.ACCESS_TOKEN;

public class CourseActivity extends AppCompatActivity implements View.OnClickListener, CourseVideoAdapter.Callback {

    FragmentActivity myContext;
    TextView aboutTXT, addtofavTXT, titleTXT, categoryTXT;
    ImageView favIMG, trailIMG, backIMG;
    RecyclerView courseVideoRCLE;
    RelativeLayout trailLAY;
    LinearLayout favLAY;
    CourseVideoAdapter mAdapter;
    ArrayList<Course_videos> courseVideosArrayList = new ArrayList<>();
    Course course;
    ProgressDialog aProgressDialog;
    private RetrofitInstance myRetrofitInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        init();
        listeners();
        setRecyclerView();
        getCourseDetail();
    }

    private void init() {
        myContext = this;
        aboutTXT = findViewById(R.id.course_about_course_txt);
        addtofavTXT = findViewById(R.id.course_fav_txt);
        titleTXT = findViewById(R.id.course_title);
        categoryTXT = findViewById(R.id.course_category);
        favIMG = findViewById(R.id.course_fav_icn);
        trailIMG = findViewById(R.id.course_first_video_IMG);
        courseVideoRCLE = findViewById(R.id.course_video_list);
        trailLAY = findViewById(R.id.course_trail_video_lay);
        favLAY = findViewById(R.id.course_add_to_fav_lay);
        backIMG = findViewById(R.id.back_arrow);

        if (this.myRetrofitInstance == null) {
            myRetrofitInstance = new RetrofitInstance();
        }
    }

    private void listeners() {
        trailLAY.setOnClickListener(this);
        favLAY.setOnClickListener(this);
        backIMG.setOnClickListener(this);
    }

    private void setRecyclerView() {
        courseVideoRCLE.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CourseVideoAdapter(this, courseVideosArrayList, this);
        courseVideoRCLE.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.course_trail_video_lay:
                Intent intent = new Intent(this, VideoActivity.class);
                intent.putExtra("course", getCourse());
                intent.putExtra("position", 0);
                startActivity(intent);
                break;

            case R.id.course_add_to_fav_lay:
                break;

            case R.id.back_arrow:
                finish();
                break;
        }
    }

    @Override
    public void click(int aPostion, String s) {
        if (getCourse().getCourse_videos().get(aPostion).getCvideo_is_free() == 1) {
            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra("course", getCourse());
            intent.putExtra("position", aPostion);
            startActivity(intent);
        } else {
            KToast.errorToast(CourseActivity.this, "This is an premium video, Need to purchase this course for play...");
        }
    }

    private void getCourseDetail() {
        aProgressDialog = new ProgressDialog(this);
        aProgressDialog.show();

        Log.e("Id", YogaHelper.aDeviceId(this));

        Map<String, String> aHeaderMap = new HashMap<>();

        aHeaderMap.put("X-Device", YogaHelper.aDeviceId(this));
        //aHeaderMap.put("X-Localization", "en");
        aHeaderMap.put("Authorization", "Bearer " + Prefs.getString(ACCESS_TOKEN, ""));

        myRetrofitInstance.getAPI().getCourseDetail("" + getIntent().getIntExtra("id", 0), aHeaderMap).enqueue(new Callback<CourseDetail>() {
            @Override
            public void onResponse(@NotNull Call<CourseDetail> call, @NotNull retrofit2.Response<CourseDetail> response) {
                aProgressDialog.dismiss();
                if (response != null) {
                    CourseDetail data = response.body();
                    if (data != null) {
                        if (data.getSuccess() == 1) {
                            course = data.getData();
                            loadData(data.getData());
                            mAdapter.update(data.getData().getCourse_videos());
                        }
                    } else {
                        YogaHelper.showAlertDialog(CourseActivity.this, "Something went wrong");
                    }
                }
            }

            @Override
            public void onFailure(Call<CourseDetail> call, Throwable t) {
                aProgressDialog.dismiss();
                //  Toast.makeText( getActivity(), "Something went wrong", Toast.LENGTH_SHORT ).show();
                YogaHelper.showAlertDialog(CourseActivity.this, "Something went wrong");

            }
        });
    }

    private void loadData(Course response) {
        aboutTXT.setText(response.getCourseDescAbout());
        titleTXT.setText(response.getCourseDescName());
        categoryTXT.setText(response.getCourseLevel());
        if (response.getCourse_videos().get(0).getCvideo_is_free() == 1) {
            Picasso.with(this).load(response.getCourse_videos().get(0).getCvideo_thumbnail()).
                    placeholder(R.drawable.yoga_benefit).into(trailIMG);
        } else {
            trailLAY.setVisibility(View.GONE);
        }
        //trailIMG = findViewById(R.id.course_banner_img);
        //trailLAY = findViewById(R.id.course_trail_video_lay);
    }
}
