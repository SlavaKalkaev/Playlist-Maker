package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.track.Track
import com.example.playlistmaker.track.TrackAdapter
import com.example.playlistmaker.track.TrackResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {
    private var searchQuery: String? = null
    private val itunesBaseUrl = "https://itunes.apple.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itunesService = retrofit.create(ITunesApi::class.java)
    private lateinit var layoutPlaceholder: LinearLayout
    private lateinit var imagePlaceholder: ImageView
    private lateinit var textPlaceholder: TextView
    private lateinit var btnReload: Button
    private lateinit var searchEditText: EditText

    private val trackList = arrayListOf<Track>()
    private val trackAdapter = TrackAdapter(trackList)

    @SuppressLint("WrongViewCast", "MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val searchBack = findViewById<ImageView>(R.id.search_back)
        searchBack.setOnClickListener {
            val displayIntent = Intent(this, MainActivity::class.java)
            startActivity(displayIntent)
        }


        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        layoutPlaceholder = findViewById(R.id.layoutPlaceholder)
        imagePlaceholder = findViewById(R.id.imagePlaceholder)
        textPlaceholder = findViewById(R.id.textPlaceholder)
        btnReload = findViewById(R.id.btnReload)
        searchEditText=findViewById(R.id.search)


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = trackAdapter
        btnReload.setOnClickListener {
            searchTracks()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_search)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            insets
        }


        val clearButton: ImageButton = findViewById(R.id.clear_button)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.isVisible = !s.isNullOrEmpty()
                searchQuery = s?.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        clearButton.setOnClickListener {
            searchEditText.text.clear()
            hideKeyboard()
            searchEditText.clearFocus()
            clearButton.isVisible = false
            trackList.clear()
            trackAdapter.notifyDataSetChanged()
        }
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (searchEditText.text.isNotEmpty()) {
                    searchTracks()
                }
                true
            }
            false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setViewAfterSearch(state: State) {
        when (state) {
            State.EMPTY -> {
                layoutPlaceholder.visibility = View.VISIBLE
                textPlaceholder.text = getString(R.string.no_results)
                imagePlaceholder.setImageResource(R.drawable.no_res)
                btnReload.visibility = View.GONE
            }

            State.ERROR -> {
                layoutPlaceholder.visibility = View.VISIBLE
                textPlaceholder.text =
                    getString(R.string.conn_problem) + "\n" + getString(R.string.loading_fail)
                imagePlaceholder.setImageResource(R.drawable.conn_problem)
                btnReload.visibility = View.VISIBLE
            }

            State.SUCCESS -> {
                layoutPlaceholder.visibility = View.GONE
            }
        }
    }

    private fun searchTracks() {
        itunesService.search(searchEditText.text.toString()).enqueue(object : Callback<TrackResponse> {
            override fun onResponse(
                call: Call<TrackResponse>,
                response: Response<TrackResponse>
            ) {
                trackList.clear()
                if (response.isSuccessful && response.body()?.results?.isNotEmpty() == true) {
                    trackList.addAll(response.body()?.results!!)
                    setViewAfterSearch(State.SUCCESS)
                } else if (response.isSuccessful) {
                    setViewAfterSearch(State.EMPTY)
                } else {
                    setViewAfterSearch(State.ERROR)
                }
                trackAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                trackList.clear()
                setViewAfterSearch(State.ERROR)
            }
        })
    }







    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("SEARCH_QUERY", searchQuery)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchQuery = savedInstanceState.getString("SEARCH_QUERY")
        val searchEditText: EditText = findViewById(R.id.search)
        searchEditText.setText(searchQuery)
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}
