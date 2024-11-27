package com.ddamn.yevm_examen_2.repositories

import com.ddamn.yevm_examen_2.entities.UserEntity
import com.ddamn.yevm_examen_2.network.ClienteRetrofit
import com.ddamn.yevm_examen_2.services.UserService


class UserRepository(private val userService: UserService = ClienteRetrofit.getInstanciaRetrofitUsers) {
    suspend fun getAllUsers() : List<UserEntity> = userService.getAllUsers()
}