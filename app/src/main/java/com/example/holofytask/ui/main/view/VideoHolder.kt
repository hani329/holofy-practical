package com.example.holofytask.ui.main.view

import android.content.Intent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.holofytask.data.model.VideoDetailData
import com.example.holofytask.databinding.VideoItemViewBinding
import com.google.android.exoplayer2.SimpleExoPlayer

class VideoHolder(v: View, itemBinding: VideoItemViewBinding) : RecyclerView.ViewHolder(v),
    View.OnClickListener {

    private var view: View = v
    private var binding: VideoItemViewBinding = itemBinding
    private var video: VideoDetailData? = null

    lateinit var mediaCoverImage: ImageView
    lateinit var mediaContainer: FrameLayout
    lateinit var progressBar: ProgressBar

    init {
        v.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val context = itemView.context

        ExoPlayerRecyclerView.onPausePlayer()
        val player: SimpleExoPlayer = ExoPlayerRecyclerView.getVideoPlayer()
        video?.setVideoSeekPosition(player.currentPosition)

        val videoIntent = Intent(context, VideoActivity::class.java)
        videoIntent.putExtra("VIDEO", video)
        context.startActivity(videoIntent)
    }

    fun onBind(video: VideoDetailData, requestManager: RequestManager) {

        this.video = video
        view.tag = this

        this.mediaCoverImage = binding.itemImage
        this.mediaContainer = binding.mediaContainer
        this.progressBar = binding.progressBar

        binding.itemTitle.text = video.getTitle()
        binding.itemSubTitle.text = video.getSubTitle()
        binding.itemDescription.text = video.getDescription()

        requestManager.load(video.getThumb()).into(mediaCoverImage)
    }
}