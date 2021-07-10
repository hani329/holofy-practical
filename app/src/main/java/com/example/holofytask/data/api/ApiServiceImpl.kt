package com.example.holofytask.data.api

import com.example.holofytask.data.model.VideoDetail
import io.reactivex.Single

class ApiServiceImpl : ApiService {

    override fun getVideos(): Single<List<VideoDetail>> {
        TODO("Not yet implemented")
    }
}