package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val settingsBack = findViewById<ImageView>(R.id.settings_back)
        settingsBack.setOnClickListener {
            finish()
        }

        val shareButton = findViewById<TextView>(R.id.share_pril)
        shareButton.setOnClickListener {
            val message = getString(R.string.share_message)

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_title)))
        }

        val supportButton = findViewById<TextView>(R.id.support)
        supportButton.setOnClickListener {
            val recipientEmail = getString(R.string.support_email)
            val subject = getString(R.string.support_subject)
            val body = getString(R.string.support_body)

            val uri = Uri.parse(
                "mailto:$recipientEmail?subject=${Uri.encode(subject)}&body=${
                    Uri.encode(body)
                }"
            )
            val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
            startActivity(Intent.createChooser(emailIntent, getString(R.string.support_title)))
        }

        val agreementButton = findViewById<TextView>(R.id.agree)
        agreementButton.setOnClickListener {
            val url = getString(R.string.agree_url)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
        val switchTheme = findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.switch_btn)
        switchTheme.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)

            val sharedPrefs = getSharedPreferences(PLAYLIST_MAKER_PREFERENCES, MODE_PRIVATE)
            sharedPrefs.edit()
                .putBoolean(THEME_SWITCHER_KEY, checked)
                .apply()
        }
    }

    companion object {
        private const val PLAYLIST_MAKER_PREFERENCES = "playlist_maker_preferences"
        private const val THEME_SWITCHER_KEY = "theme_switcher_key"
    }
}


