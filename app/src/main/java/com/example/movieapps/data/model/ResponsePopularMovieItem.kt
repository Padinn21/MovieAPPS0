package com.example.movieapps.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponsePopularMovieItem(
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("overview")
    val overview: String,
): Serializable