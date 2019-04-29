package com.yoga.app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.C;
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
import com.yoga.app.model.CourseVideos;
import com.yoga.app.utils.VideoPlayerConfig;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity implements Player.EventListener,
        CourseVideoAdapter.Callback, View.OnClickListener {

    private static final String TAG = "VideoPlayerActivity";

    private static final String KEY_VIDEO_POSITION = "Video_List";
    private static final String KEY_VIDEO_ID = "Video_ID";
    private static final String KEY_VIDEO_ARRAY = "Video_Array";
    //AppDatabase mAppDatabase;
    View nextButton;
    Timeline.Window window;
    DefaultTimeBar exoProgress;
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
    private PlayerView mVideoFullScreenPlayer;
    private AVLoadingIndicatorView mSpinnerVideoDetails;
    private AppCompatButton mPlayBTN;
    private RecyclerView mRelatedList;
    private CourseVideoAdapter mAdapter;
    private ArrayList<CourseVideos> mVideoArrayList = new ArrayList<>();
    private ArrayList<CourseVideos> mRemovedVideoArrayList = new ArrayList<>();
    private AppCompatTextView mTitleTXT, mDescriptionTXT;
    private Uri videoUri;
    private SimpleExoPlayer player;
    private Handler mHandler;
    private Runnable mRunnable;
    private int mPosition;
    private int mVideoID = 1;
    private ImageView changeOrientation;

    public static Intent getStartIntent(Context context, int aPosition, int id, ArrayList<CourseVideos> mVideoDetailsList) {
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

        //mAppDatabase = AppDatabase.getDatabase(this);
        init();
        loadDummyData();
        listeners();

        /*if (getIntent().hasExtra(KEY_VIDEO_POSITION) && getIntent().hasExtra(KEY_VIDEO_ARRAY)) {
            mPosition = getIntent().getIntExtra(KEY_VIDEO_POSITION, 0);
            mVideoID = getIntent().getStringExtra(KEY_VIDEO_ID);
            mVideoArrayList = (ArrayList<CourseVideos>) getIntent().getSerializableExtra(KEY_VIDEO_ARRAY);
            for (CourseVideos aVideoDetails : mVideoArrayList) {
                if (aVideoDetails.id.equals(mVideoID))
                    videoUri = Uri.parse(aVideoDetails.url);
            }
            //videoUri = Uri.parse(mVideoArrayList.get(mPosition).url);
        }*/

        /*if (mAppDatabase.aVideoDao().getVideoDurationEmpty().size() == 0) {
            for (int i = 0; i < mVideoArrayList.size(); i++) {
                CourseVideos aVideoDetailList = mVideoArrayList.get(i);

                VideoDetail aVideoDetail = new VideoDetail();

                aVideoDetail.id = aVideoDetailList.id;
                aVideoDetail.duration = "0";

                mVideoDetailList.add(aVideoDetail);
            }
            mAppDatabase.aVideoDao().insertVideoDuration(mVideoDetailList);
        }*/

        videoUri = Uri.parse(mVideoArrayList.get(0).video_url);

        setUp();
        setupRecyclerView();
        loadValues();
    }

    private void init() {
        mVideoFullScreenPlayer = findViewById(R.id.videoFullScreenPlayer);
        mSpinnerVideoDetails = findViewById(R.id.spinnerVideoDetails);

        mPlayBTN = findViewById(R.id.activity_video_detail_play_BTN);
        mRelatedList = findViewById(R.id.activity_video_detail_related_list);
        exoProgress = findViewById(R.id.exo_progress);
        changeOrientation = findViewById(R.id.change_orientation_IMG);

        exoProgress.setEnabled(false);
        exoProgress.setClickable(false);
        //nextButton = findViewById(R.id.exo_next);

        mTitleTXT = findViewById(R.id.activity_video_detail_title);
        mDescriptionTXT = findViewById(R.id.activity_video_detail_description);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //getSupportActionBar().hide();
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
            mVideoArrayList.add(courseVideos);
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
        mRemovedVideoArrayList.addAll(mVideoArrayList);
        for (CourseVideos aVideoDetails : mVideoArrayList) {
            if (aVideoDetails.getId() == mVideoID)
                mRemovedVideoArrayList.remove(aVideoDetails);

        }

        mAdapter = new CourseVideoAdapter(this, mVideoArrayList, this);
        mRelatedList.setAdapter(mAdapter);
    }

    private void loadValues() {
        for (CourseVideos aVideoDetails : mVideoArrayList) {
            if (aVideoDetails.getId() == mVideoID) {
                mTitleTXT.setText(aVideoDetails.title);
                mDescriptionTXT.setText(aVideoDetails.description);
            }
        }
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

        for (CourseVideos aVideoDetail : mVideoArrayList) {

            Uri aUri = Uri.parse(aVideoDetail.getVideo_url());

            MediaSource aVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(aUri);

            concatenatedSource.addMediaSource(aVideoSource);

        }

        player.prepare(concatenatedSource);

        //automatically seek to previous position
        /*VideoDetail sss = (mAppDatabase.aVideoDao().getVideoDuration(mVideoArrayList.get(mPosition).id));
        long along = Long.parseLong(sss.duration);
        player.seekTo(along);*/

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
            /*if (player.getCurrentPosition() > 0) {
                VideoDetail aVideoDetail = new VideoDetail();
                aVideoDetail.id = mVideoArrayList.get(mPosition).id;
                aVideoDetail.duration = String.valueOf(player.getCurrentPosition());

                //mAppDatabase.aVideoDao().update(mVideoArrayList.get(mPosition).id, String.valueOf(player.getCurrentPosition()));
            }*/
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
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
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
                if (mPosition < mVideoArrayList.size() - 1) {
                    ((VideoActivity) this).finish();
                    startActivity(VideoActivity.getStartIntent(this, mPosition + 1, mVideoArrayList.get(mPosition + 1).id, mVideoArrayList));
                } else
                    finish();
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

    private void next() {
        Timeline timeline = player.getCurrentTimeline();
        if (timeline.isEmpty()) {
            return;
        }
        int windowIndex = player.getCurrentWindowIndex();
        if (windowIndex < timeline.getWindowCount() - 1) {
            player.seekTo(windowIndex + 1, C.TIME_UNSET);
        } else if (timeline.getWindow(windowIndex, window, false).isDynamic) {
            player.seekTo(windowIndex, C.TIME_UNSET);
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

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }


    @Override
    public void click(int aPostion, String s) {

    }
}

