package com.app.akademikapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Alamat IP khusus agar Emulator Android bisa membaca localhost laptop kamu
    private const val BASE_URL = "http://10.0.2.2:3000/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Mengubah JSON dari server jadi objek Kotlin
            .build()

        retrofit.create(ApiService::class.java)
    }
}