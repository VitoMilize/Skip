package com.montanainc.simpleloginscreen

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.skip.RetrofitClient
import com.example.skip.dataclasses.LoginRequest
import com.example.skip.dataclasses.LoginResponse
import com.example.skip.ui.theme.SkipTheme
import com.montanainc.simpleloginscreen.app.SkipApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkipTheme {
                SkipApp()

                loginUser(this, "test1", "test1")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SkipTheme {
        SkipApp()
    }
}

private fun loginUser(context: Context, email: String, password: String) {
    val request = LoginRequest(email, password)

    RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                val loginResponse = response.body()
                Toast.makeText(context, "Login Successful! Token: ${loginResponse?.token}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_LONG).show()
        }
    })
}