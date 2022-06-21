package com.example.reposearch.api

import com.example.reposearch.data.Repo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val BASE_URL = "https://api.github.com/"

val interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


interface RepoService{

    @GET("/orgs/{org}/repos")
    suspend fun searchRepo(@Path("org") org : String, @Query("per_page") per_page : Int) : Response<List<Repo>>

}

object WebService {
    val repos: RepoService by lazy {  retrofit.create(RepoService::class.java) }
}