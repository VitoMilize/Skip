package com.example.skip.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.skip.AuthViewModel
import com.example.skip.RetrofitClientV1
import com.example.skip.dataclasses.User
import com.montanainc.simpleloginscreen.components.CreateRequestButton
import com.montanainc.simpleloginscreen.components.HeadingTextComponent
import com.montanainc.simpleloginscreen.components.ViewOwnRequestsButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MainScreenStudent(navController: NavHostController, authViewModel: AuthViewModel = viewModel()) {
    val nameState = remember { mutableStateOf("Загрузка...") }

    RetrofitClientV1.instance.getMe(authViewModel.getToken().toString())
        .enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    nameState.value = user!!.name
                } else {

                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {

            }
        })

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HeadingTextComponent(value = nameState.value)
            Spacer(modifier = Modifier.height(25.dp))
            CreateRequestButton{ navController.navigate("CreateRequest") }
            ViewOwnRequestsButton {}
        }
    }
}