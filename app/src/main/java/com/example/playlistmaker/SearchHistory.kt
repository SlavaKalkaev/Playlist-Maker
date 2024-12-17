package com.example.playlistmaker

import android.content.SharedPreferences
import com.example.playlistmaker.track.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


    val SEARCH_HISTORY_KEY = "key_for_history"

    class SearchHistory(private val sharedPreferences: SharedPreferences) {

        fun read(tracks: ArrayList<Track>) {
            val json = sharedPreferences.getString(SEARCH_HISTORY_KEY, null)
            if (!json.isNullOrEmpty()) {
                val tracksFromJson: List<Track> = Gson().fromJson(json, Array<Track>::class.java).toList()
                tracks.addAll(tracksFromJson)
            }
        }


        fun add(track: Track, tracks: ArrayList<Track>) {

            if (tracks.find { it.trackId === track.trackId } !== null) {
                tracks.remove(track)
                tracks.add(0, track)
            } else {
                if (tracks.size === 10) {
                    tracks.removeLast()
                    tracks.add(0, track)
                } else {
                    tracks.add(0, track)
                }
            }
            val json = Gson().toJson(tracks)
            sharedPreferences.edit()
                .putString(SEARCH_HISTORY_KEY, json)
                .apply()
        }

        fun clear(tracks: ArrayList<Track>) {
            tracks.clear()
            sharedPreferences.edit()
                .remove(SEARCH_HISTORY_KEY)
                .apply()
        }
    }
