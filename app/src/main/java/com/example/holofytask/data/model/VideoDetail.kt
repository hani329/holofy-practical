package com.example.holofytask.data.model

import org.json.JSONArray

class VideoDetail {

    private var videoDetailList: ArrayList<VideoDetailData> = arrayListOf()

    fun getVideoList(): ArrayList<VideoDetailData> {
        return videoDetailList
    }

    fun parseData(json: JSONArray) {

        val videoData = VideoDetailData()

        for (i in 0..json.length()) {
            videoData.parseData(json.getJSONObject(i))
            videoDetailList.add(videoData)
        }
    }
}