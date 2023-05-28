package com.kira.composemvvmcoroutineshilt.user

import com.kira.composemvvmcoroutineshilt.network.ApiService
import javax.inject.Inject

class UserRemoteSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getRandomUsers() = apiService.getRandomUser()
}