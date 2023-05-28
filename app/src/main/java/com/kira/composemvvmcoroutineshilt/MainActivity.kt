package com.kira.composemvvmcoroutineshilt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.kira.composemvvmcoroutineshilt.model.User
import com.kira.composemvvmcoroutineshilt.ui.theme.ComposeMVVMCoroutinesHiltTheme
import com.kira.composemvvmcoroutineshilt.user.UserState
import com.kira.composemvvmcoroutineshilt.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow

@AndroidEntryPoint
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
                    ScreenSetup(viewModel)
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

@Composable
fun NameList(
    users: List<User>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(12.dp)
    ) {
        items(users) {user ->
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(Modifier.padding(10.dp)) {
                    Row {
                        SetUserText(text = "USER ID: ")
                        SetUserText(text = user.id.toString())
                    }
                    Row {
                        SetUserText(text = "UID: ")
                        SetUserText(text = user.uid.toString())
                    }
                    Row {
                        SetUserText(text = "NAME: ")
                        SetUserText(text = "${user.firstName} ${user.lastName}")
                    }
                    Row {
                        SetUserText(text = "USERNAME: ")
                        SetUserText(text = user.userName.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun SetUserText(
    text: String
) {
    Text(
        text = text,
        fontSize = 14.sp
    )
}

