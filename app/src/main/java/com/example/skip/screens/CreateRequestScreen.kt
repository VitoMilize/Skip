package com.example.skip.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.skip.AuthViewModel
import com.example.skip.RetrofitClientV1
import com.example.skip.dataclasses.CreateRequest
import com.example.skip.dataclasses.User
import com.example.skip.ui.theme.AccentColor
import com.example.skip.ui.theme.Secondary
import com.montanainc.simpleloginscreen.components.HeadingTextComponent
import com.montanainc.simpleloginscreen.components.MyTextFieldComponentNoIcon
import com.montanainc.simpleloginscreen.components.MyTextFieldComponentNoIconClickable
import com.montanainc.simpleloginscreen.components.MyTextFieldComponentSelector
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


private fun createRequest(
    startDate: String,
    endDate: String,
    comment: String,
    reason: String,
    docLink: String,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    RetrofitClientV1.instance.getMe(authViewModel.getToken().toString())
        .enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    val id = user!!.id
                    val role = user.role

                    val engReason = when (reason) {
                        "Семейные обстоятельства" -> "FAMILY"
                        "По болезни" -> "ILLNESS"
                        "Учебная активность" -> "STUDY_ACTIVITY"
                        else -> ""
                    }

                    val fileInDean = docLink.isEmpty()

                    val request = CreateRequest(
                        id,
                        "${startDate}T01:35:19.520Z",
                        "${endDate}T01:35:19.520Z",
                        comment,
                        "PENDING",
                        engReason,
                        fileInDean,
                        listOf(docLink)
                    )

                    Log.e("CreateRequest", "Ошибка: ${request}")

                    RetrofitClientV1.instance.createRequest(request, authViewModel.getToken().toString()).enqueue(object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.isSuccessful) {
                                if (role == "STUDENT")
                                    navController.navigate("MainStudent")
                                else if (role == "TEACHER")
                                    navController.navigate("MainTeacher")
                            } else {
                                Log.e("API Error", "Ошибка при создании запроса")
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.e("API Error", "Ошибка при создании запроса: ${t.message}")
                        }
                    })
                } else {
                    Log.e("API Error", "Ошибка при получении данных о пользователе")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("API Error", "Ошибка при получении данных о пользователе: ${t.message}")
            }
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRequestScreen(navController: NavHostController, authViewModel: AuthViewModel = viewModel()) {
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    var docLink by remember { mutableStateOf("") }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    if (showStartDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis
                    if (selectedMillis != null) {
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        startDate = sdf.format(Date(selectedMillis))
                    }
                    showStartDatePicker = false
                }) {
                    Text("Выбрать")
                }
            },
            dismissButton = {
                Button(onClick = { showStartDatePicker = false }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showEndDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis
                    if (selectedMillis != null) {
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        endDate = sdf.format(Date(selectedMillis))
                    }
                    showEndDatePicker = false
                }) {
                    Text("Выбрать")
                }
            },
            dismissButton = {
                Button(onClick = { showEndDatePicker = false }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
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
            HeadingTextComponent(value = "Создание заявки")
            Spacer(modifier = Modifier.height(25.dp))
            Column {
                MyTextFieldComponentNoIconClickable(
                    labelValue = "Дата начала отсутствия",
                    textValue = startDate,
                    onClick = { showStartDatePicker = true }
                )
                Spacer(modifier = Modifier.height(10.dp))
                MyTextFieldComponentNoIconClickable(
                    labelValue = "Дата конца отсутствия",
                    textValue = endDate,
                    onClick = { showEndDatePicker = true }
                )
                Spacer(modifier = Modifier.height(10.dp))
                MyTextFieldComponentNoIcon(
                    labelValue = "Комментарий",
                    textValue = comment,
                    onTextChanged = { comment = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                MyTextFieldComponentSelector(
                    labelValue = "Выбор варианта",
                    selectedOption = reason,
                    options = listOf("Семейные обстоятельства", "По болезни", "Учебная активность")
                ) { reason = it }
                Spacer(modifier = Modifier.height(10.dp))
                MyTextFieldComponentNoIcon(
                    labelValue = "Ссылка на документ(если документ не в деканате)",
                    textValue = docLink,
                    onTextChanged = { docLink = it }
                )
                Spacer(modifier = Modifier.height(10.dp))

            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { createRequest(startDate, endDate, comment, reason, docLink, navController, authViewModel) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.horizontalGradient(listOf(Secondary, AccentColor)),
                                    shape = RoundedCornerShape(50.dp)
                                )
                                .fillMaxWidth()
                                .heightIn(48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Создать", color = Color.White, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}