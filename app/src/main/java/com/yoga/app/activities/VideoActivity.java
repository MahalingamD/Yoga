package com.yoga.app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.wang.avi.AVLoadingIndicatorView;
import com.yoga.app.R;
import com.yoga.app.adapter.CourseVideoAdapter;
import com.yoga.app.model.Course;
import com.yoga.app.model.Course_videos;
import com.yoga.app.utils.KToast;
import com.yoga.app.utils.VideoPlayerConfig;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity implements Player.EventListener,
        CourseVideoAdapter.Callback, View.OnClickListener {

    private static final String TAG = "VideoPlayerActivity";

    private static final String KEY_VIDEO_POSITION = "Video_List";
    private static final String KEY_VIDEO_ID = "Video_ID";
    private static final String KEY_VIDEO_ARRAY = "Video_Array";
    Timeline.Window window;
    DefaultTimeBar exoProgress;
    private PlayerView mVideoFullScreenPlayer;
    private AVLoadingIndicatorView mSpinnerVideoDetails;
    private RecyclerView mRelatedList;
    private CourseVideoAdapter mAdapter;
    private ArrayList<Course_videos> mVideoArrayList = new ArrayList<>();
    //private ArrayList<Course_videos> mRemovedVideoArrayList = new ArrayList<>();
    private AppCompatTextView mTitleTXT, mDescriptionTXT;
    private Uri videoUri;
    private SimpleExoPlayer player;
    private int mPosition = 0;
    private int mVideoID = 1;
    private ImageView changeOrientation;
    Course course;

    public static Intent getStartIntent(Context context, int aPosition, int id, ArrayList<Course_videos> mVideoDetailsList) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(KEY_VIDEO_POSITION, aPosition);
        intent.putExtra(KEY_VIDEO_ID, id);
        intent.putExtra(KEY_VIDEO_ARRAY, mVideoDetailsList);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);

        intentvalue();
        init();
        listeners();
        videoUri = Uri.parse(mVideoArrayList.get(mPosition).getCvideo_video());
        setUp();
        setupRecyclerView();
        loadValues();
    }

    private void intentvalue() {
        course = (Course) getIntent().getSerializableExtra("course");
        mPosition = getIntent().getIntExtra("position", 0);
        if (course != null)
            mVideoArrayList = course.getCourse_videos();
    }

    private void init() {
        mVideoFullScreenPlayer = findViewById(R.id.videoFullScreenPlayer);
        mSpinnerVideoDetails = findViewById(R.id.spinnerVideoDetails);

        mRelatedList = findViewById(R.id.activity_video_detail_related_list);
        exoProgress = findViewById(R.id.exo_progress);
        changeOrientation = findViewById(R.id.change_orientation_IMG);

        exoProgress.setEnabled(false);
        exoProgress.setClickable(false);

        mTitleTXT = findViewById(R.id.activity_video_detail_title);
        mDescriptionTXT = findViewById(R.id.activity_video_detail_description);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private void listeners() {
        changeOrientation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_orientation_IMG:
                changeOrientation();
                break;
        }
    }

    private void changeOrientation() {
        int orientation = VideoActivity.this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //First Hide other objects (listview or recyclerview), better hide them using Gone.
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mVideoFullScreenPlayer.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            mVideoFullScreenPlayer.setLayoutParams(params);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //unhide your objects here.
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mVideoFullScreenPlayer.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = 700;
            mVideoFullScreenPlayer.setLayoutParams(params);
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager aLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRelatedList.setLayoutManager(aLinearLayoutManager);
        mRelatedList.setHasFixedSize(true);

        //remove current position
       /* mRemovedVideoArrayList.addAll(mVideoArrayList);
        for (Course_videos aVideoDetails : mVideoArrayList) {
            if (aVideoDetails.getCvideo_id() == mVideoID)
                mRemovedVideoArrayList.remove(aVideoDetails);
        }*/

        mAdapter = new CourseVideoAdapter(this, mVideoArrayList, this);
        mRelatedList.setAdapter(mAdapter);
    }

    private void loadValues() {
        /*for (Course_videos aVideoDetails : mVideoArrayList) {
            if (aVideoDetails.getCvideo_id() == mVideoID) {
                mTitleTXT.setText(aVideoDetails.getCvideo_desc_title());
                mDescriptionTXT.setText(aVideoDetails.getCvideo_desc_title());
            }
        }*/
        mTitleTXT.setText(mVideoArrayList.get(mPosition).getCvideo_desc_title());
    }

    private void setUp() {
        initializePlayer();
        if (videoUri == null) {
            return;
        }
        buildMediaSource(videoUri);
    }

    private void initializePlayer() {
        if (player == null) {
            // 1. Create a default TrackSelector
            LoadControl loadControl = new DefaultLoadControl(
                    new DefaultAllocator(true, 16),
                    VideoPlayerConfig.MIN_BUFFER_DURATION,
                    VideoPlayerConfig.MAX_BUFFER_DURATION,
                    VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                    VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            // 2. Create the player
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
            mVideoFullScreenPlayer.setPlayer(player);
        }
    }

    private void buildMediaSource(Uri mUri) {
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mUri);

        // Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        player.addListener(this);

        ConcatenatingMediaSource concatenatedSource = new ConcatenatingMediaSource();

        for (Course_videos aVideoDetail : mVideoArrayList) {
            if (aVideoDetail.getCvideo_is_free() == 1) {
                Uri aUri = Uri.parse(aVideoDetail.getCvideo_video());
                MediaSource aVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(aUri);
                concatenatedSource.addMediaSource(aVideoSource);
            }
        }
        player.prepare(concatenatedSource);
        player.seekTo(mPosition, 0);

    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    private void resumePlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resumePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }


    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {

            case Player.STATE_BUFFERING:
                mSpinnerVideoDetails.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_ENDED:
                // Activate the force enable
                //mAppDatabase.aVideoDao().update(mVideoID, "0");
                /*if (mPosition < mVideoArrayList.size() - 1) {
                    ((VideoActivity) this).finish();
                    startActivity(VideoActivity.getStartIntent(this, mPosition + 1, mVideoArrayList.get(mPosition + 1).getCvideo_id(), mVideoArrayList));
                } else
                    finish();*/
                break;
            case Player.STATE_IDLE:

                break;
            case Player.STATE_READY:
                mSpinnerVideoDetails.setVisibility(View.GONE);
                break;

            default:
                // status = PlaybackStatus.IDLE;
                break;
        }
    }


    @Override
    public void onRepeatModeChanged(int repeatMode) {
    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {
        mPosition = player.getCurrentWindowIndex();
        loadValues();
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void click(int aPostion, String s) {
        if (aPostion == mPosition)
            return;
        if (mVideoArrayList.get(aPostion).getCvideo_is_free() == 1) {
            player.seekTo(aPostion, 0);
        } else {
            KToast.errorToast(VideoActivity.this, "This is an premium video, Need to purchase this course for play...");
        }
    }

    @Override
    public void onBackPressed() {
        int orientation = VideoActivity.this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            super.onBackPressed();
        }
    }
}

