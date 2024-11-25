package com.example.retro

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/random")
    fun getImages(): Call<List<RecipeImage>>
}