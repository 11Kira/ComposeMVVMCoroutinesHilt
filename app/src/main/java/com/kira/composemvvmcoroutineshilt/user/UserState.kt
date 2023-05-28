package com.kira.composemvvmcoroutineshilt.user

import com.kira.composemvvmcoroutineshilt.model.User

sealed class UserState {
    object ShowLoading: UserState()
    data class ShowError(val error: Any): UserState()
    data class SetResults(val userList: List<User>): UserState()
}