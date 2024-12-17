package com.example.playlistmaker.track

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import java.text.SimpleDateFormat
import java.util.Locale


class TrackAdapter(private val trackList: List<Track>) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val trackName: TextView = itemView.findViewById(R.id.track_title)
        private val artistName: TextView = itemView.findViewById(R.id.track_artist)
        private val trackTime: TextView = itemView.findViewById(R.id.track_duration)
        private val artworkImage: ImageView = itemView.findViewById(R.id.track_image)

        fun bind(track: Track) {
            trackName.text = track.trackName
            artistName.text = track.artistName
            if (track.trackTime === null){
                trackTime.text = "00:00"
            } else {
                trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTime.toInt())
            }

            Glide.with(itemView.context)
                .load(track.artworkUrl100)
                .transform(RoundedCorners(10))
                .into(artworkImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view)
    }


    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount(): Int = trackList.size

}
