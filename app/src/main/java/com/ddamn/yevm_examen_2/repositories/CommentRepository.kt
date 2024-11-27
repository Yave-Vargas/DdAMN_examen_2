package com.ddamn.yevm_examen_2.repositories

import com.ddamn.yevm_examen_2.entities.Comment
import com.ddamn.yevm_examen_2.network.ClienteRetrofit
import com.ddamn.yevm_examen_2.services.CommentService

class CommentRepository(private val commentService: CommentService = ClienteRetrofit.getInstanciaRetrofitComments) {
    suspend fun getAllComments(id: Long) : List<Comment> = commentService.getAllComments(id)
}