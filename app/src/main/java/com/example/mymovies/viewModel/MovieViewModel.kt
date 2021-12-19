package com.example.mymovies.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymovies.interfaces.CoroutineExecutionInterface
import com.example.mymovies.models.MovieList
import com.example.mymovies.repo.MoviesRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieViewModel(private val repo: MoviesRepo) : ViewModel(), CoroutineScope {

    private val job = Job()
    private lateinit var coroutineExecutionInterface: CoroutineExecutionInterface

    val movies: LiveData<MovieList>
        get() = repo.movies


    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


    fun withCoroutineExecutionListener(coroutineExecutionInterface: CoroutineExecutionInterface): MovieViewModel {
        this.coroutineExecutionInterface = coroutineExecutionInterface
        return this
    }

    private fun onPreExecute() {
        if (::coroutineExecutionInterface.isInitialized)
        coroutineExecutionInterface.onPreExecutionListener()
    }

    private fun onPostExecute() {
        if (::coroutineExecutionInterface.isInitialized)
        coroutineExecutionInterface.onPostExecutionListener()
    }

    fun executeNetworkCallInThread(pageNo: Int, type: String) = launch(Dispatchers.Main) {
        onPreExecute()
        withContext(Dispatchers.IO) {
            repo.getMovies(pageNo, type)
        }
        onPostExecute()
    }
}