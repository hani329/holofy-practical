package com.example.holofytask.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getVideos() = apiService.getVideos()

}