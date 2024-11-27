package com.ddamn.yevm_examen_2.repositories

import com.ddamn.yevm_examen_2.entities.Post
import com.ddamn.yevm_examen_2.network.ClienteRetrofit
import com.ddamn.yevm_examen_2.services.PostService


class PostRepository(private val postService: PostService = ClienteRetrofit.getInstanciaRetrofitPosts) {
    suspend fun getAllPosts(id: Long) : List<Post> = postService.getAllPosts(id)
}