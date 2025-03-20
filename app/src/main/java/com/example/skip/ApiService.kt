package com.example.skip
import com.example.skip.dataclasses.LoginRequest
import com.example.skip.dataclasses.LoginResponse
import com.example.skip.dataclasses.RegistrationRequest
import com.example.skip.dataclasses.RegistrationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body request: RegistrationRequest): Call<RegistrationResponse>
}