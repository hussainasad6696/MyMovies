package com.example.mymovies.helper

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "3dcbb3f057d2bb290b467c2b870884f0"
    const val IMAGE_LINK = "https://image.tmdb.org/t/p/w500"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //val movieService = retrofit.create(MovieService.class)
}