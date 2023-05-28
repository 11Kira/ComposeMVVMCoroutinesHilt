package com.kira.composemvvmcoroutineshilt.user

import com.kira.composemvvmcoroutineshilt.model.User
import retrofit2.Response

fun Response<List<User>>.mapUsersToDomain(): List<User> {
    return this.body()?.map { user ->
        User(
            id = user.id,
            uid = user.uid,
            firstName = user.firstName,
            lastName = user.lastName,
            userName = user.userName
        )
    } ?: emptyList()
}