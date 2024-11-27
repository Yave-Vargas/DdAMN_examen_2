package com.ddamn.yevm_examen_2.services

import com.ddamn.yevm_examen_2.entities.UserEntity
import retrofit2.http.GET

interface UserService {
    @GET("users")
    suspend fun getAllUsers(): List<UserEntity>
}