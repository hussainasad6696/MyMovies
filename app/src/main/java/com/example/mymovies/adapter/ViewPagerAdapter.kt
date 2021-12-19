package com.example.mymovies.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mymovies.ui.DisplayMoviesListFragment
import com.example.mymovies.viewModel.MovieViewModel

class ViewPagerAdapter(private var fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private lateinit var movieViewModel: MovieViewModel
    constructor(fragmentActivity: FragmentActivity,movieViewModel: MovieViewModel): this(fragmentActivity){
        this.fragmentActivity = fragmentActivity
        this.movieViewModel = movieViewModel
    }
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DisplayMoviesListFragment(ViewTypes.POPULAR,movieViewModel)
            1 -> DisplayMoviesListFragment(ViewTypes.NOW_PLAYING, movieViewModel)
            else -> DisplayMoviesListFragment(ViewTypes.POPULAR, movieViewModel)
        }
    }

    enum class ViewTypes{
        POPULAR,
        NOW_PLAYING
    }
}