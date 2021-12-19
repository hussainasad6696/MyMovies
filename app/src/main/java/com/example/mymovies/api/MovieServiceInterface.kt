package com.example.mymovies.api

import com.example.mymovies.helper.RetrofitHelper
import com.example.mymovies.models.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServiceInterface {

    @GET("movie/{type}?api_key=${RetrofitHelper.API_KEY}")
    suspend fun getMovies(
        @Path("type") type: String,
        @Query("page") page: Int
    ): Response<MovieList>
}