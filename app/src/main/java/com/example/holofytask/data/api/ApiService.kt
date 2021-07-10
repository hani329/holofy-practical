package com.example.holofytask.data.api

import com.example.holofytask.data.model.VideoDetail
import io.reactivex.Single

interface ApiService {

    fun getVideos(): Single<List<VideoDetail>>

}