package com.example.mymovies.models

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movieTable", indices = [Index(value = arrayOf("id"), unique = true)])
data class Result(
    val adult: Boolean = false, //done
    val backdrop_path: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val original_language: String? = null, //done
    val original_title: String? = null, //done
    val overview: String? = null, //done
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null, //done
    val video: Boolean = false,
    val vote_average: Double? = null, //done
    val vote_count: Int? = null //done
): Parcelable