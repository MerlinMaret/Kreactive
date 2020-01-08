package com.kreactive.test.app.data.datasource

import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.remote.SearchMoviesResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


object RemoteDatasource {

    lateinit var omdbapi: OMDBApiService

    init {
        initOmdbApi()
    }

    private fun initOmdbApi() {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val baseUrl = "https://www.omdbapi.com"

        omdbapi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(OMDBApiService::class.java)
    }
}

interface OMDBApiService {


    @GET(".")
    fun searchMovie(
        @Query("s") title: String,
        @Query("page") page : Int,
        @Query("apikey") apikey: String = "9cef1082"
    ): Call<SearchMoviesResult>
}

