package com.example.mymovies.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.mymovies.api.MovieServiceInterface
import com.example.mymovies.database.MovieDatabase
import com.example.mymovies.helper.RetrofitHelper
import com.example.mymovies.repo.MoviesRepo
import com.example.mymovies.viewModel.MovieViewModel
import com.example.mymovies.viewModel.MoviesViewModelFactory

class DataImpl {

    private lateinit var moviesRepo: MoviesRepo

    fun dataBuilder(applicationContext: Context): DataImpl {
        val retrofit = RetrofitHelper.getInstance().create(MovieServiceInterface::class.java)
        val database = MovieDatabase.getInstance(applicationContext)
        moviesRepo = MoviesRepo(retrofit,database,applicationContext)
        return this
    }

    fun initViewModel(viewModelStore: ViewModelStore): MovieViewModel {
       return ViewModelProvider(viewModelStore, MoviesViewModelFactory(moviesRepo))[MovieViewModel::class.java]
    }
}
