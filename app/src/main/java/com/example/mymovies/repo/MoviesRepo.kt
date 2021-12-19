package com.example.mymovies.repo

import android.accounts.NetworkErrorException
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovies.api.MovieServiceInterface
import com.example.mymovies.database.MovieDatabase
import com.example.mymovies.models.MovieList
import com.example.mymovies.utils.NetworkStatus
import java.lang.Exception

class MoviesRepo(
    private val moviesService: MovieServiceInterface,
    movieDatabase: MovieDatabase,
    private val context: Context
) {

    private val moviesList = MutableLiveData<MovieList>()
    private val moviesDao = movieDatabase.movieDao()

    val movies: LiveData<MovieList>
        get() = moviesList


    suspend fun getMovies(page: Int, type: String) {
        if (NetworkStatus.networkStatus().checkForInternet(context = context)) {
            val response = moviesService.getMovies(type, page)
            if (response.body() != null) {
                response.body()?.let { moviesDao.addMovies(it.results!!) }
                moviesList.postValue(response.body())
            }
        } else {
            moviesList.postValue(
                MovieList(
                    page = 1,
                    moviesDao.getAllMovies(),
                    total_pages = 1,
                    total_results = 1
                )
            )
        }
    }
}