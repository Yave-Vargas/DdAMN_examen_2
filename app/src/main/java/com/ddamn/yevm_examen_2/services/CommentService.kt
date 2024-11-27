package com.ddamn.yevm_examen_2.services

import com.ddamn.yevm_examen_2.entities.Comment
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentService {
    @GET("posts/{id}/comments")
    suspend fun getAllComments(@Path("id") id: Long): List<Comment>
}