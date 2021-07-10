package com.example.holofytask.data.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONArray
import org.json.JSONObject

class VideoDetailData() : Parcelable {

    private var videoUrl: String = ""
    private var title: String = ""
    private var subTitle: String = ""
    private var description: String = ""
    private var seekPosition = 0L
    private var thumb: String = ""

    constructor(parcel: Parcel) : this() {
        videoUrl = parcel.readString().toString()
        title = parcel.readString().toString()
        subTitle = parcel.readString().toString()
        description = parcel.readString().toString()
        seekPosition = parcel.readLong()
        thumb = parcel.readString().toString()
    }

    fun getVideoUrl(): String {
        return videoUrl
    }

    fun setVideoUrl(videoUrl: String) {
        this.videoUrl = videoUrl
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getSubTitle(): String {
        return subTitle
    }

    fun setSubTitle(subTitle: String) {
        this.subTitle = subTitle
    }

    fun getDescription(): String {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getVideoSeekPosition(): Long {
        return seekPosition
    }

    fun setVideoSeekPosition(continueVideoFromDuration: Long) {
        this.seekPosition = continueVideoFromDuration
    }

    fun getThumb(): String {
        return thumb
    }

    fun setThumb(thumb: String) {
        this.thumb = thumb
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(videoUrl)
        parcel.writeString(title)
        parcel.writeString(subTitle)
        parcel.writeString(description)
        parcel.writeLong(seekPosition)
        parcel.writeString(thumb)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoDetailData> {
        override fun createFromParcel(parcel: Parcel): VideoDetailData {
            return VideoDetailData(parcel)
        }

        override fun newArray(size: Int): Array<VideoDetailData?> {
            return arrayOfNulls(size)
        }
    }

    fun parseData(json: JSONObject) {
        setTitle(json.optString("title"))
        setSubTitle(json.optString("subtitle"))
        setDescription(json.optString("description"))
        setThumb(json.optString("thumb"))
        setVideoUrl(json.getJSONArray("sources").getString(0))
    }
}