package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageSearch = findViewById<Button>(R.id.search_button)
        val imageMedia = findViewById<Button>(R.id.media_button)
        val imageSetting = findViewById<Button>(R.id.settings_button)

        imageSetting.setOnClickListener {
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)
        }
        imageMedia.setOnClickListener {
            val displayIntent = Intent(this, MediaActivity::class.java)
            startActivity(displayIntent)
        }
        imageSearch.setOnClickListener {
            val displayIntent = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent)
        }
    }
}
//@SuppressLint("MissingInflatedId", "ResourceType")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val imageButtonSearch = findViewById<Button>(R.id.search_button)
//        val imageMediaSearch = findViewById<Button>(R.id.media_button)
//        val imageSettingSearch = findViewById<Button>(R.id.settings_button)
//        val imageClickListener: View.OnClickListener = object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                Toast.makeText(this@MainActivity, "Поиск", Toast.LENGTH_SHORT).show()
//            }
//        }
//        imageButtonSearch.setOnClickListener(imageClickListener)
//
//
//        imageMediaSearch.setOnClickListener {
//            Toast.makeText(this@MainActivity, "Медиа", Toast.LENGTH_SHORT).show()
//        }
//        imageSettingSearch.setOnClickListener {
//            Toast.makeText(this@MainActivity, "Настройки", Toast.LENGTH_SHORT).show()
//        }
//    }

