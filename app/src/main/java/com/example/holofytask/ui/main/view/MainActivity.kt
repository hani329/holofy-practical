package com.example.holofytask.ui.main.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.holofytask.data.model.VideoDetail
import com.example.holofytask.data.model.VideoDetailData
import com.example.holofytask.databinding.ActivityMainBinding
import com.example.holofytask.ui.main.adapter.VideoListAdapter
import com.example.holofytask.utils.Utility

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: VideoListAdapter

    private var videoDetailList: ArrayList<VideoDetailData> = arrayListOf()
    private var firstTime = true

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initData()
    }

    private fun initView() {
        linearLayoutManager = LinearLayoutManager(this)
        binding.videoList.layoutManager = linearLayoutManager
    }

    private fun initData() {

        /*val url =
            "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API+Retrofit+MVVM+Course+Summary.mp4"
        val title = "Seriously?"
        val subTitle = "Walk this way... If you can"
        val description = "Style out a silly walk with these games."
        val thumb =
            "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API%2C+Retrofit2%2C+MVVM+Course+SUMMARY.png"

        for (i in 1..5) {
            val videoDetail = VideoDetail()
            videoDetail.setVideoUrl(url)
            videoDetail.setTitle(title)
            videoDetail.setSubTitle(subTitle)
            videoDetail.setDescription(description)
            videoDetail.setThumb(thumb)
            videoDetailList.add(videoDetail)
        }*/

        val videoDetail = VideoDetail()
        videoDetail.parseData(Utility.getJSONData())
        videoDetailList = videoDetail.getVideoList()

        binding.videoList.setMediaObjects(videoDetailList)
        adapter = VideoListAdapter(videoDetailList, initGlide())
        binding.videoList.adapter = adapter

        if (firstTime) {
            Handler(Looper.getMainLooper()).post { binding.videoList.playVideo(false) }
            firstTime = false
        }
    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
        return Glide.with(this).setDefaultRequestOptions(options)
    }

    override fun onDestroy() {
        binding.videoList.releasePlayer()
        super.onDestroy()
    }
}