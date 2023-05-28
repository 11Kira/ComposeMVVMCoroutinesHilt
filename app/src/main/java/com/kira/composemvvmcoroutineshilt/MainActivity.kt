package com.kira.composemvvmcoroutineshilt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.kira.composemvvmcoroutineshilt.model.User
import com.kira.composemvvmcoroutineshilt.ui.theme.ComposeMVVMCoroutinesHiltTheme
import com.kira.composemvvmcoroutineshilt.user.UserState
import com.kira.composemvvmcoroutineshilt.user.UserViewModel
import kotlinx.coroutines.flow.SharedFlow

class MainActivity : ComponentActivity() {

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMVVMCoroutinesHiltTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenSetup(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(viewModel: UserViewModel) {
    MainScreen(viewModel.userState)
}
@Composable
fun MainScreen(sharedFlow: SharedFlow<UserState>) {
    val messages = remember { mutableStateListOf<User>() }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect { state ->
                when (state) {
                    is UserState.SetResults -> {
                        state.userList.forEach{
                            messages.add(it)
                        }
                    }

                    is UserState.ShowError -> {
                        Log.e("Error: ", state.error.toString())
                    }

                    else -> {
                        Log.e("Error: ", state.toString())
                    }
                }
            }
        }
    }
}
