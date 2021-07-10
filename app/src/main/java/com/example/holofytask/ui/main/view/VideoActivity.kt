package com.example.holofytask.ui.main.view

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.holofytask.data.model.VideoDetail
import com.example.holofytask.data.model.VideoDetailData
import com.example.holofytask.databinding.ActivityVideoBinding
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultAllocator
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.util.*

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding
    private var video: VideoDetailData? = null

    private val TAG = "ExoPlayerRecyclerView"
    private val AppName = "Holofy Task"

    /**
     * PlayerViewHolder UI component
     * Watch PlayerViewHolder class
     */
    private var mediaCoverImage: ImageView? = null
    private lateinit var progressBar: ProgressBar
    private var mediaContainer: FrameLayout? = null
    private lateinit var videoSurfaceView: PlayerView
    private lateinit var videoPlayer: SimpleExoPlayer

    /**
     * variable declaration
     */
    private var videoSurfaceDefaultHeight = 0
    private var screenDefaultHeight = 0

    private var title: String = ""
    private var subTitle: String = ""
    private var desc: String = ""
    private var thumb: String = ""
    private var url: String = ""
    private var duration: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {
            video = intent.getParcelableExtra("VIDEO")
        }

        initData()
    }

    private fun initData() {

        if (video != null) {

            title = video!!.getTitle()
            subTitle = video!!.getSubTitle()
            desc = video!!.getDescription()
            thumb = video!!.getThumb()
            url = video!!.getVideoUrl()
            duration = video!!.getVideoSeekPosition()
        }

        binding.itemTitle.text = title
        binding.itemSubTitle.text = subTitle
        binding.itemDescription.text = desc

        Glide.with(this).setDefaultRequestOptions(RequestOptions()).load(thumb)
            .into(binding.itemImage)
        init()
    }


    private fun init() {

        val display = (Objects.requireNonNull(
            getSystemService(WINDOW_SERVICE)
        ) as WindowManager).defaultDisplay
        val point = Point()
        display.getSize(point)

        videoSurfaceDefaultHeight = point.x
        screenDefaultHeight = point.y
        videoSurfaceView = PlayerView(this)
        videoSurfaceView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

        val loadControl: LoadControl = DefaultLoadControl.Builder()
            .setAllocator(DefaultAllocator(true, 16))
            .setBufferDurationsMs(
                100,
                2000,
                100,
                100
            )
            .setTargetBufferBytes(-1)
            .setPrioritizeTimeOverSizeThresholds(true).build()

        //Create the player using ExoPlayerFactory
        videoPlayer = SimpleExoPlayer.Builder(this).setLoadControl(loadControl).build()

        // Disable Player Control
        videoSurfaceView.useController = false
        // Bind the player to the view.
        videoSurfaceView.player = videoPlayer

        playVideo()

        videoPlayer.addListener(object : Player.Listener {
            override fun onPlayerError(error: ExoPlaybackException) {
                Log.e(TAG, "onPlayerStateChanged: Error: $error")
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                Log.e(TAG, "onIsLoadingChanged: $isLoading")
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        Log.e(TAG, "onPlayerStateChanged: Buffering video.")
                        progressBar.visibility = View.VISIBLE
                    }
                    Player.STATE_ENDED -> {
                        Log.d(TAG, "onPlayerStateChanged: Video ended.")
                        videoPlayer.seekTo(0)
                    }
                    Player.STATE_IDLE -> {
                    }
                    Player.STATE_READY -> {
                        Log.e(TAG, "onPlayerStateChanged: Ready to play.")
                        progressBar.visibility = View.GONE
                        addVideoView()
                    }
                }
            }
        })
    }

    private fun playVideo() {

        // remove any old surface views from previously playing videos
        videoSurfaceView.visibility = View.INVISIBLE

        mediaCoverImage = binding.itemImage
        progressBar = binding.progressBar
        mediaContainer = binding.mediaContainer
        videoSurfaceView.player = videoPlayer

        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            this, Util.getUserAgent(this, AppName)
        )

        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(url))
        videoPlayer.setMediaSource(videoSource)
        videoPlayer.prepare()
        videoPlayer.playWhenReady = true
        videoPlayer.seekTo(duration)
    }

    private fun addVideoView() {
        mediaContainer!!.addView(videoSurfaceView)
        videoSurfaceView.requestFocus()
        videoSurfaceView.visibility = View.VISIBLE
        videoSurfaceView.alpha = 1f
        mediaCoverImage!!.visibility = View.GONE
    }

    private fun releasePlayer() {
        videoPlayer.release()
    }

    private fun onPausePlayer() {
        videoPlayer.pause()
    }

    override fun onPause() {
        super.onPause()
        onPausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaContainer?.removeAllViews()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        mediaContainer?.removeAllViews()
        releasePlayer()
    }
}