package com.example.pasteleria_mil_sabores.remote

import com.example.pasteleria_mil_sabores.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun  getPosts():List<Post>
}