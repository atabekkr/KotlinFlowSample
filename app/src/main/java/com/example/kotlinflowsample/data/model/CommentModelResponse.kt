package com.example.kotlinflowsample.data.model

import com.example.kotlinflowsample.presentation.model.CommentUI
import com.google.gson.annotations.SerializedName

data class CommentModelResponse(
    val postId: Int? = null,
    val id: Int? = null,
    val email: String? = null,
    val name: String? = null,
    @SerializedName("body")
    val comment: String? = null
) {
    fun mapToDomain() = CommentUI(
        id = id,
        email = email,
        name = name,
        comment = comment
    )
}
