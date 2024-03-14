package com.example.kotlinflowsample.data.repository

import com.example.kotlinflowsample.data.network.ApiService
import com.example.kotlinflowsample.data.network.CommentApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CommentsRepository(private val apiService: ApiService) {
    suspend fun getComment(id: Int) = flow {
        // get the Comment Data from the api
        val comment = apiService.getComments(id).mapToDomain()

        // Emit this data wrapped in
        // the helper class [CommentApiState]
        emit(CommentApiState.success(comment))
    }.flowOn(Dispatchers.IO)
}

