package com.example.playlistmaker.track

data class Track(
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя
    val trackTime: String, // Продолжительность трека
    val artworkUrl100: String, // Ссылка на изображение обложки
    val trackId: Int,
    val albumName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String)
{
    fun getAlbum() = artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")


    fun getYear(): String{
        return if(releaseDate.isNotEmpty()){
            releaseDate.substring(0,4)
        } else {
            ""
        }
    }}

