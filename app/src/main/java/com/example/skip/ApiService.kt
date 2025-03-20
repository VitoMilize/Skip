package com.example.skip
import com.example.skip.dataclasses.LoginRequest
import com.example.skip.dataclasses.LoginResponse
import com.example.skip.dataclasses.RegistrationRequest
import com.example.skip.dataclasses.RegistrationResponse
import com.example.skip.dataclasses.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body request: RegistrationRequest): Call<RegistrationResponse>

    @GET("v1/users/me")
    fun getMe(@Header("Authorization") token: String): Call<User>
}