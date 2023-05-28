package com.kira.composemvvmcoroutineshilt.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {

    private val mutableUserState: MutableSharedFlow<UserState> = MutableSharedFlow()
    val userState = mutableUserState.asSharedFlow()

    init {
        viewModelScope.launch(
            CoroutineExceptionHandler {_, error -> runBlocking { mutableUserState.emit(UserState.ShowError(error = error)) } }
        ) {
            mutableUserState.emit(UserState.ShowLoading)
            val users = userUseCase.getRandomUsers()
            users.let { data ->
                mutableUserState.emit(UserState.SetResults(data))
            }
        }
    }
}