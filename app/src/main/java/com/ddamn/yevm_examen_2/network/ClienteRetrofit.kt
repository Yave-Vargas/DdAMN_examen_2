package com.ddamn.yevm_examen_2.network

import com.ddamn.yevm_examen_2.services.CommentService
import com.ddamn.yevm_examen_2.services.PostService
import com.ddamn.yevm_examen_2.services.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClienteRetrofit {
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    val getInstanciaRetrofitUsers: UserService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    val getInstanciaRetrofitPosts: PostService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostService::class.java)
    }

    val getInstanciaRetrofitComments: CommentService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentService::class.java)
    }

}