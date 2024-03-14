package com.example.kotlinflowsample.data.network

import com.example.kotlinflowsample.data.model.CommentModelResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // Get method to call the api ,passing id as a path
    @GET("/comments/{id}")
    suspend fun getComments(@Path("id") id: Int): CommentModelResponse

    @GET("/comments")
    suspend fun getAllComments(): List<CommentModelResponse>

}