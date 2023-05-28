package com.kira.composemvvmcoroutineshilt.user

import com.kira.composemvvmcoroutineshilt.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val remoteSource: UserRemoteSource) {
    suspend fun getRandomUsers(): List<User> {
        return remoteSource.getRandomUsers().mapUsersToDomain()
    }
}