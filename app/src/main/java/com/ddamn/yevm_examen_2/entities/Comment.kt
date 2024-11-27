package com.ddamn.yevm_examen_2.entities

data class Comment(
    val id: Long,
    val postId: Long,
    val email: String,
    val name: String,
    val body: String,
)
