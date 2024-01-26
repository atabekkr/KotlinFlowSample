package com.example.kotlinflowsample.repository

import com.example.kotlinflowsample.model.CommentModel
import com.example.kotlinflowsample.network.ApiService
import com.example.kotlinflowsample.network.CommentApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CommentsRepository(private val apiService: ApiService) {

    suspend fun getComment(id: Int): Flow<CommentApiState<CommentModel>> {
        return flow {
            // get the Comment Data from the api
            val comment = apiService.getComments(id)

            // Emit this data wrapped in
            // the helper class [CommentApiState]
            emit(CommentApiState.success(comment))
        }.flowOn(Dispatchers.IO)
    }

}