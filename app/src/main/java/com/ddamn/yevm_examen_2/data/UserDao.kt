package com.ddamn.yevm_examen_2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ddamn.yevm_examen_2.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * from user")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * from user WHERE id = :id")
    fun getById(id: Int): Flow<UserEntity>

    @Insert
    suspend fun add(user: UserEntity): Long
}