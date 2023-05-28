package com.kira.composemvvmcoroutineshilt.network

import com.kira.composemvvmcoroutineshilt.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users/random_user?size=100")
    suspend fun getRandomUser(): Response<List<User>>
}