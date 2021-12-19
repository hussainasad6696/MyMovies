package com.example.mymovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mymovies.MainActivity
import com.example.mymovies.R
import com.example.mymovies.adapter.MovieAdapter
import com.example.mymovies.adapter.ViewPagerAdapter
import com.example.mymovies.databinding.FragmentDisplayMoviesListBinding
import com.example.mymovies.interfaces.CoroutineExecutionInterface
import com.example.mymovies.interfaces.OnBackPressed
import com.example.mymovies.interfaces.OnItemClickInterface
import com.example.mymovies.models.Result
import com.example.mymovies.viewModel.MovieViewModel
import com.google.android.material.snackbar.Snackbar

class DisplayMoviesListFragment(
    private val viewTypes: ViewPagerAdapter.ViewTypes,
    private val movieViewModel: MovieViewModel
) : Fragment(), CoroutineExecutionInterface, OnItemClickInterface, OnBackPressed {

    private lateinit var binding: FragmentDisplayMoviesListBinding
    private lateinit var adapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_display_movies_list,
            container,
            false
        )

        initViews()

        return binding.root
    }

    private fun initViews() {
        (activity as MainActivity).onBackPressed(this)
        movieViewModel.withCoroutineExecutionListener(this)
        adapter = MovieAdapter(requireContext(), this)
        binding.movieViewRecyclerview.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.movieViewRecyclerview.adapter = adapter

        loadData(1)
        movieViewModel.movies.observe(viewLifecycleOwner) {
            adapter.setMovieList(it)
        }

        binding.movieViewRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!binding.movieViewRecyclerview.canScrollVertically(1)) {
                    loadData(adapter.currentPage() + 1)
                    binding.movieViewRecyclerview.smoothScrollToPosition(0)
                    Snackbar.make(
                        requireContext(),
                        binding.mainView,
                        "Loading more...",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun loadData(pageNo: Int) {
        when (viewTypes) {
            ViewPagerAdapter.ViewTypes.NOW_PLAYING -> {
                movieViewModel.executeNetworkCallInThread(pageNo, "now_playing")
            }
            ViewPagerAdapter.ViewTypes.POPULAR -> {
                movieViewModel.executeNetworkCallInThread(pageNo, "popular")
            }
        }

    }


    override fun onPreExecutionListener() {
        binding.progressCircular.visibility = View.VISIBLE
    }

    override fun onPostExecutionListener() {
        binding.progressCircular.visibility = View.GONE
    }

    override fun onItemClickListener(result: Result) {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, MovieDetailFragment.withArguments(result)).commit()
    }

    override fun onBackPressed() {
        requireActivity().finish()
    }

}