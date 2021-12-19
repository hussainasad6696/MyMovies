package com.example.mymovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.interfaces.OnBackPressed
import com.example.mymovies.ui.MainFragment
import com.example.mymovies.viewModel.MovieViewModel
import com.example.mymovies.viewModel.MoviesViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var onBackPressed: OnBackPressed

    fun onBackPressed(onBackPressed: OnBackPressed){
        this.onBackPressed = onBackPressed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeFragment()
    }

    override fun onBackPressed() {
        onBackPressed.onBackPressed()
    }

    private fun changeFragment() {
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, MainFragment())
            .commit()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}