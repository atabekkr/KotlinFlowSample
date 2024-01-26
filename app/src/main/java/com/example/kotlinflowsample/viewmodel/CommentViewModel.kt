package com.example.kotlinflowsample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflowsample.model.CommentModel
import com.example.kotlinflowsample.network.CommentApiState
import com.example.kotlinflowsample.network.Status
import com.example.kotlinflowsample.repository.CommentsRepository
import com.example.kotlinflowsample.utils.AppConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CommentViewModel : ViewModel() {

    // Create a Repository and pass the api
    // service we created in AppConfig file
    private val repository = CommentsRepository(
        AppConfig.ApiService()
    )

    val commentState = MutableStateFlow(
        CommentApiState(
            Status.LOADING,
            CommentModel(),
            ""
        )
    )

    init {
        // Initiate a starting
        // search with comment Id 1
        getNewComment(1)
    }

    fun getNewComment(id: Int) {

        // Since Network Calls takes time,Set the
        // initial value to loading state
        commentState.value = CommentApiState.loading()

        // ApiCalls takes some time, So it has to be
        // run and background thread. Using viewModelScope
        // to call the api

        viewModelScope.launch {

            // Collecting the data emitted
            // by the function in repository
            repository.getComment(id)
                // If any errors occurs like 404 not found
                // or invalid query, set the state to error
                // State to show some info
                // on screen
                .catch {
                    commentState.value =
                        CommentApiState.error(it.message.toString())
                }
                // If Api call is succeeded, set the State to Success
                // and set the response data to data received from api
                .collect {
                    commentState.value = CommentApiState.success(it.data)
                }
        }
    }

}