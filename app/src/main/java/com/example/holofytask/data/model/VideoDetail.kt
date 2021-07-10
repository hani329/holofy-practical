package com.example.holofytask.data.model

import org.json.JSONArray

class VideoDetail {

    private var videoDetailList: ArrayList<VideoDetailData> = arrayListOf()

    fun getVideoList(): ArrayList<VideoDetailData> {
        return videoDetailList
    }

    fun parseData(json: JSONArray) {

        for (i in 0 until json.length()) {
            val videoData = VideoDetailData()
            videoData.parseData(json.getJSONObject(i))
            videoDetailList.add(videoData)
        }
    }
}