package com.montanainc.simpleloginscreen.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import com.example.skip.RetrofitClientAuth
import com.example.skip.RetrofitClientV1
import com.example.skip.dataclasses.RegistrationRequest
import com.example.skip.dataclasses.RegistrationResponse
import com.example.skip.dataclasses.User
import com.example.skip.ui.theme.AccentColor
import com.example.skip.ui.theme.Secondary
import com.montanainc.simpleloginscreen.components.AccountQueryComponent
import com.montanainc.simpleloginscreen.components.HeadingTextComponent
import com.montanainc.simpleloginscreen.components.MyTextFieldComponent
import com.montanainc.simpleloginscreen.components.PasswordTextFieldComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private fun registerUser(name: String, login: String, password: String, navController: NavHostController) {
    val request = RegistrationRequest(login, name, password, "STUDENT")

    RetrofitClientAuth.instance.register(request).enqueue(object : Callback<RegistrationResponse> {
        override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
            if (response.isSuccessful) {
                val registrationResponse = response.body()

                RetrofitClientV1.instance.getMe("Bearer ${registrationResponse!!.token}")
                    .enqueue(object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.isSuccessful) {
                                val user = response.body()
                                if (user!!.role == "STUDENT")
                                    navController.navigate("MainStudent")
                                else if (user.role == "TEACHER")
                                    navController.navigate("MainTeacher")
                            } else {

                            }
                        }
                        override fun onFailure(call: Call<User>, t: Throwable) {

                        }
                    })
            } else {

            }
        }

        override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {

        }
    })
}

@Composable
fun SignupScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            HeadingTextComponent(value = "Добро пожаловать в Skip")
            Spacer(modifier = Modifier.height(25.dp))

            Column {
                MyTextFieldComponent(
                    labelValue = "Имя",
                    icon = Icons.Outlined.Person,
                    textValue = name,
                    onTextChanged = { name = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                MyTextFieldComponent(
                    labelValue = "Логин",
                    icon = Icons.Outlined.Email,
                    textValue = login,
                    onTextChanged = { login = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextFieldComponent(
                    labelValue = "Пароль",
                    icon = Icons.Outlined.Lock,
                    textValue = password,
                    onTextChanged = { password = it }
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { registerUser(name, login, password, navController) },
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
                                Text(text = "Войти", color = Color.White, fontSize = 20.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        AccountQueryComponent("Уже есть аккаунт? ", "Войдите", navController)
                    }
                }
            }
        }
    }
}