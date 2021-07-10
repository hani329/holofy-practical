package com.example.holofytask.data.repository

import com.example.holofytask.data.api.ApiHelper
import com.example.holofytask.data.model.VideoDetail
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUsers(): Single<List<VideoDetail>> {
        return apiHelper.getVideos()
    }

}