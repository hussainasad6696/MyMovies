package com.example.mymovies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mymovies.MainActivity
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentMovieDetailBinding
import com.example.mymovies.helper.RetrofitHelper
import com.example.mymovies.interfaces.OnBackPressed
import com.example.mymovies.models.Result


class MovieDetailFragment : Fragment(), OnBackPressed,View.OnClickListener {


    companion object{
        fun withArguments(result: Result): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            fragment.arguments = bundleOf("movieDetail" to result)
            return fragment
        }
    }

    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var resultArgument: Result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie_detail, container, false)

        initArguments()

        return binding.root
    }

    private fun initArguments() {
        (activity as MainActivity).onBackPressed(this)
        resultArgument = arguments?.getParcelable("movieDetail")!!
        Glide.with(requireContext()).load("${RetrofitHelper.IMAGE_LINK}${resultArgument.poster_path}")
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(15)))
            .error(ContextCompat.getDrawable(requireContext(), R.drawable.imdb))
            .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.imdb))
            .into(binding.booking.movieImage)

        binding.booking.title.text = resultArgument.title.toString()
        binding.booking.numberOfVotes.text = resultArgument.vote_count.toString()
        binding.booking.rating.text = resultArgument.vote_average.toString()
        binding.booking.overview.text = resultArgument.overview.toString()
        binding.booking.adult.text = if (resultArgument.adult) "Yes" else "No"
        binding.booking.originalLanguage.text = resultArgument.original_language
        binding.booking.originalTitle.text = resultArgument.original_title
        binding.booking.popularity.text = resultArgument.popularity.toString()
        binding.booking.release.text = resultArgument.release_date


        binding.back.setOnClickListener(this)
        binding.booking.buyTicket.setOnClickListener(this)
    }

    private val removeFragment = {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }

    override fun onBackPressed() {
        removeFragment()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back -> removeFragment()
            R.id.buyTicket -> Toast.makeText(requireContext(),"Ticket booked",Toast.LENGTH_SHORT).show()
        }
    }

}