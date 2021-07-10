package com.example.holofytask.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.holofytask.data.model.VideoDetailData
import com.example.holofytask.databinding.VideoItemViewBinding
import com.example.holofytask.ui.main.view.VideoHolder

class VideoListAdapter(
    private val videos: ArrayList<VideoDetailData>,
    private val requestManager: RequestManager
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: VideoItemViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        binding = VideoItemViewBinding.inflate(LayoutInflater.from(parent.context))
        val rootView = binding.root

        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        rootView.layoutParams = lp

        return VideoHolder(rootView, binding)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoHolder).onBind(videos[position], requestManager)
    }
}