package com.kira.composemvvmcoroutineshilt.user

import com.kira.composemvvmcoroutineshilt.model.User
import javax.inject.Inject

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun getRandomUsers(): List<User> {
        return userRepository.getRandomUsers()
    }
}