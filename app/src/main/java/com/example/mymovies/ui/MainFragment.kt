package com.example.mymovies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.mymovies.R
import com.example.mymovies.adapter.ViewPagerAdapter
import com.example.mymovies.databinding.FragmentMainBinding
import com.example.mymovies.utils.DataImpl
import com.example.mymovies.viewModel.MovieViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment() : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var movieViewModel: MovieViewModel
    private var tabsName = arrayOf("Popular","Now Playing")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false)

        initViews()

        return binding.root
    }

    private fun initViews() {
        movieViewModel = DataImpl().dataBuilder(requireContext()).initViewModel(viewModelStore)
        viewPagerAdapter = ViewPagerAdapter(requireActivity(),movieViewModel)
        binding.viewPager2.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager2) { tab, position ->
            tab.text = tabsName[position]
        }.attach()
    }

}