package com.ddamn.yevm_examen_2.services

import com.ddamn.yevm_examen_2.entities.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {
    @GET("users/{id}/posts")
    suspend fun getAllPosts(@Path("id") id: Long): List<Post>
}