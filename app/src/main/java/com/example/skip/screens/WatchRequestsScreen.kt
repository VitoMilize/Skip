package com.example.skip.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skip.AuthViewModel
import com.example.skip.RetrofitClientV1
import com.example.skip.dataclasses.SkipRequest
import com.example.skip.dataclasses.User
import com.montanainc.simpleloginscreen.components.HeadingTextComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun WatchRequestsScreen(authViewModel: AuthViewModel = viewModel()) {
    val idState = remember { mutableStateOf("Загрузка...") }

    RetrofitClientV1.instance.getMe(authViewModel.getToken().toString())
        .enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    idState.value = user!!.id
                } else {

                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {

            }
        })

    val requests = remember { mutableStateOf<List<SkipRequest>>(emptyList()) }
    val context = LocalContext.current
    val token = authViewModel.getToken().toString()

    LaunchedEffect(Unit) {
        val apiService = RetrofitClientV1.instance
        val call = apiService.getUserRequests(idState.value ,token)

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

@Composable
fun CustomListView(requests: List<SkipRequest>) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        items(requests) { request ->
            CreateRequestCard(request = request, context = context)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CreateRequestCard(request: SkipRequest, context: Context) {
    val backgroundColor = when (request.status) {
        "APPROVED" -> Color(0xFFB2DFDB)
        "PENDING" -> Color(0xFFFFF176)
        "DECLINED" -> Color(0xFFE57373)
        else -> Color.LightGray
    }

    Card(
        onClick = {
            Toast.makeText(context, "${request.status} selected..", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .padding(start = 16.dp, top = 24.dp, end = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Status: ${request.status}",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Start Date: ${request.dateStart}", color = Color.Black)
            Text(text = "End Date: ${request.dateEnd}", color = Color.Black)
        }
    }
}
