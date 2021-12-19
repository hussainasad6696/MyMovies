package com.example.mymovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mymovies.R
import com.example.mymovies.models.MovieList
import com.example.mymovies.models.Result
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.mymovies.helper.RetrofitHelper.IMAGE_LINK
import com.example.mymovies.interfaces.OnItemClickInterface


class MovieAdapter(
    private val context: Context,
    private val onItemClickInterface: OnItemClickInterface
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var movieList: MovieList

    fun setMovieList(movieList: MovieList) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    fun currentPage(): Int {
        return if (::movieList.isInitialized)
            movieList.page
        else 0
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val imageHolder: ImageView = view.findViewById(R.id.imageHolder)
        private val movieName: TextView = view.findViewById(R.id.movieName)
        private val ratingBar: RatingBar = view.findViewById(R.id.rating)
        private val ratingNo: TextView = view.findViewById(R.id.ratingNo)
        fun bind(result: Result, onItemClickInterface: OnItemClickInterface) {
            Glide.with(view.context).load("$IMAGE_LINK${result.poster_path}")
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(15)))
                .error(ContextCompat.getDrawable(view.context, R.drawable.imdb))
                .placeholder(ContextCompat.getDrawable(view.context, R.drawable.imdb))
                .into(imageHolder)
            movieName.text = result.title
            ratingBar.rating = result.vote_average!!.toFloat()
            ratingNo.text = result.vote_average.toString()

            itemView.setOnClickListener {
                onItemClickInterface.onItemClickListener(result)
            }
//            setAnimation(itemView,view.context)
        }

        private fun setAnimation(viewToAnimate: View, context: Context) {
            val animation: Animation =
                AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList.results?.get(position)!!, onItemClickInterface)
    }

    override fun getItemCount(): Int =
        if (::movieList.isInitialized) movieList.results!!.size else 0
}