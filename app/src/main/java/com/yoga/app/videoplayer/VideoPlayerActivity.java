package com.yoga.app.videoplayer;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;
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
import com.yoga.app.R;


import java.util.ArrayList;

/**
 * Created by Gowtham on 28-02-2019.
 */

public class VideoPlayerActivity extends AppCompatActivity implements Player.EventListener {

    private static final String TAG = "VideoPlayerActivity";

    private static final String KEY_VIDEO_POSITION = "Video_List";
    private static final String KEY_VIDEO_ID = "Video_ID";
    private static final String KEY_VIDEO_ARRAY = "Video_Array";

    private PlayerView mVideoFullScreenPlayer;
    private ProgressBar mSpinnerVideoDetails;
    private AppCompatButton mPlayBTN;
    private RecyclerView mRelatedList;
    //private RelatedVideoListAdapter mAdapter;
    // private ArrayList<ReturnValues.VideoDetails> mVideoArrayList = new ArrayList<>();
    // private ArrayList<VideoDetail> mVideoDetailList = new ArrayList<>();
    // private ArrayList<ReturnValues.VideoDetails> mRemovedVideoArrayList = new ArrayList<>();
    private AppCompatTextView mTitleTXT, mDescriptionTXT;
    private Uri videoUri;
    private SimpleExoPlayer player;
    private Handler mHandler;
    private Runnable mRunnable;
    private int mPosition;
    private String mVideoID;
    //  AppDatabase mAppDatabase;
    View nextButton;
    Timeline.Window window;
    DefaultTimeBar exoProgress;

   /* public static Intent getStartIntent(Context context, int aPosition, String id, ArrayList<ReturnValues.VideoDetails> mVideoDetailsList) {
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra(KEY_VIDEO_POSITION, aPosition);
        intent.putExtra(KEY_VIDEO_ID, id);
        intent.putExtra(KEY_VIDEO_ARRAY, mVideoDetailsList);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_detail);


        mVideoFullScreenPlayer = findViewById(R.id.videoFullScreenPlayer);
        mSpinnerVideoDetails = findViewById(R.id.spinnerVideoDetails);
        mPlayBTN = findViewById(R.id.activity_video_detail_play_BTN);
        mRelatedList = findViewById(R.id.activity_video_detail_related_list);
        exoProgress = findViewById(R.id.exo_progress);

        exoProgress.setEnabled(false);
        //nextButton = findViewById(R.id.exo_next);

        mTitleTXT = findViewById(R.id.activity_video_detail_title);
        mDescriptionTXT = findViewById(R.id.activity_video_detail_description);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        getSupportActionBar().hide();


        videoUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");


        setUp();
//        setupRecyclerView();
        loadValues();

        /*nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });*/
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
            params.height = 600;
            mVideoFullScreenPlayer.setLayoutParams(params);
        }
    }


    private void loadValues() {

  //      mTitleTXT.setText(aVideoDetails.title);
    //    mDescriptionTXT.setText(aVideoDetails.description);
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


}

