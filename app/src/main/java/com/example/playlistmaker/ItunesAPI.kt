package com.example.playlistmaker

import com.example.playlistmaker.track.TrackResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ITunesApi {

    @GET("search")
    fun search(@Query("term", encoded = false) text: String): Call<TrackResponse>
}