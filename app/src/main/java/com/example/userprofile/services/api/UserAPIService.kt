package com.example.userprofile.services.api

import com.example.userprofile.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val API_URL = "https://jsonplaceholder.typicode.com"

interface UserAPIService {

    @GET("users/{userId}")
    fun getUser(@Path("userId") id: String): Call<User>

    companion object {
        fun create(): UserAPIService {
            val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(UserAPIService::class.java)
        }
    }
}