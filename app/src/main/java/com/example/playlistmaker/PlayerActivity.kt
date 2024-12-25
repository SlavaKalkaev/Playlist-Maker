package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.track.Track
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {

    private lateinit var album: ImageView
    private lateinit var trackName: TextView
    private lateinit var artistName: TextView
    private lateinit var durationValue: TextView
    private lateinit var albumValue: TextView
    private lateinit var yearValue: TextView
    private lateinit var genreValue: TextView
    private lateinit var countryValue: TextView
    private lateinit var toolbar: Toolbar

    private lateinit var sharedPref: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        album = findViewById(R.id.album)
        trackName = findViewById(R.id.trackName)
        artistName = findViewById(R.id.artistName)
        durationValue = findViewById(R.id.durationValue)
        albumValue = findViewById(R.id.albumValue)
        yearValue = findViewById(R.id.yearValue)
        genreValue = findViewById(R.id.genreValue)
        countryValue = findViewById(R.id.countryValue)
        toolbar = findViewById(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }
        sharedPref = getSharedPreferences(SearchActivity.PLAYLIST_MAKER_PREFERENCES, MODE_PRIVATE)
        showTrack()
    }

    private fun showTrack(){
        val json = sharedPref.getString(SearchActivity.PLAYER_TRACK, null)
        if (json !== null){
            val trackFromJson = Gson().fromJson(json, Track::class.java)

            trackName.text = trackFromJson.trackName
            artistName.text = trackFromJson.artistName
            val trackTime = trackFromJson.trackTime?.toIntOrNull()
            if (trackTime != null) {
                durationValue.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTime)
            } else {
                durationValue.text = "00:00"
            }
            albumValue.text = trackFromJson.albumName
            yearValue.text = trackFromJson.getYear()
            genreValue.text = trackFromJson.primaryGenreName
            countryValue.text = trackFromJson.country

            val roundCorner = this.resources.getDimensionPixelSize(R.dimen.artwork_corner)

            Glide.with(this)
                .load(trackFromJson.getAlbum())
                .placeholder(R.drawable.album)
                .centerCrop()
                .transform(RoundedCorners(roundCorner))
                .into(album)

        }
    }
}