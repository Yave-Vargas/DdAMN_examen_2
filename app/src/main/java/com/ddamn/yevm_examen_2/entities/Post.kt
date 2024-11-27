package com.ddamn.yevm_examen_2.entities

data class Post(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String,
)
