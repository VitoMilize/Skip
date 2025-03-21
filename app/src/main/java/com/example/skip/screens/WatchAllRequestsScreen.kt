package com.example.skip.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skip.AuthViewModel
import com.example.skip.RetrofitClientV1
import com.example.skip.dataclasses.SkipRequest
import com.montanainc.simpleloginscreen.components.HeadingTextComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun WatchAllRequestsScreen(authViewModel: AuthViewModel = viewModel()) {
    val requests = remember { mutableStateOf<List<SkipRequest>>(emptyList()) }
    val context = LocalContext.current
    val token = authViewModel.getToken().toString()

    LaunchedEffect(Unit) {
        val apiService = RetrofitClientV1.instance
        val call = apiService.getAllRequests(token)

        call.enqueue(object : Callback<List<SkipRequest>> {
            override fun onResponse(
                call: Call<List<SkipRequest>>,
                response: Response<List<SkipRequest>>
            ) {
                if (response.isSuccessful) {
                    requests.value = response.body() ?: emptyList()
                } else {
                    Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<SkipRequest>>, t: Throwable) {
                Toast.makeText(context, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


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
            HeadingTextComponent(value = "Мои заявки")
            Spacer(modifier = Modifier.height(25.dp))
            CustomListView(requests.value)
        }
    }
}
