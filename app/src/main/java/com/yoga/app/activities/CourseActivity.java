package com.yoga.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.adapter.CourseVideoAdapter;
import com.yoga.app.model.CourseVideos;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity implements View.OnClickListener, CourseVideoAdapter.Callback {

    FragmentActivity myContext;
    TextView aboutTXT, addtofavTXT, titleTXT, categoryTXT;
    ImageView favIMG, trailIMG;
    RecyclerView courseVideoRCLE;
    RelativeLayout trailLAY;
    LinearLayout favLAY;

    CourseVideoAdapter mAdapter;
    ArrayList<CourseVideos> courseVideosArrayList = new ArrayList<>();
    String[] thump = new String[]{"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerFun.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerJoyrides.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerMeltdowns.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/SubaruOutbackOnStreetAndDirt.jpg",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg"};

    String[] video = new String[]{"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        init();
        listeners();
        setRecyclerView();
    }

    private void init() {
        myContext = this;
        aboutTXT = findViewById(R.id.course_about_course_txt);
        addtofavTXT = findViewById(R.id.course_fav_txt);
        titleTXT = findViewById(R.id.course_title);
        categoryTXT = findViewById(R.id.course_category);
        favIMG = findViewById(R.id.course_fav_icn);
        trailIMG = findViewById(R.id.course_banner_img);
        courseVideoRCLE = findViewById(R.id.course_video_list);
        trailLAY = findViewById(R.id.course_trail_video_lay);
        favLAY = findViewById(R.id.course_add_to_fav_lay);
    }

    private void listeners() {
        trailLAY.setOnClickListener(this);
        favLAY.setOnClickListener(this);
    }

    private void setRecyclerView() {

        loadDummyData();

        courseVideoRCLE.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CourseVideoAdapter(this, courseVideosArrayList, this);
        courseVideoRCLE.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void loadDummyData() {
        for (int i = 0; i < 10; i++) {
            CourseVideos courseVideos = new CourseVideos();
            courseVideos.setId(1);
            courseVideos.setTitle("Title " + (i + 1));
            courseVideos.setDescription("Description " + (i + 1));
            courseVideos.setMin((i + 1) + " min");
            courseVideos.setPaid(true);
            courseVideos.setThumnail_url(thump[i]);
            courseVideos.setVideo_url(video[i]);
            courseVideosArrayList.add(courseVideos);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.course_trail_video_lay:
                startActivity(new Intent(this, VideoActivity.class));
                break;

            case R.id.course_add_to_fav_lay:
                break;
        }
    }

    @Override
    public void click(int aPostion, String s) {

    }
}
