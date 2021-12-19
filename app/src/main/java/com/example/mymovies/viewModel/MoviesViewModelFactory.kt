package com.example.mymovies.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.repo.MoviesRepo

class MoviesViewModelFactory(private val repo: MoviesRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(repo) as T
    }
}