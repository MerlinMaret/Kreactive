package com.kreactive.test.app.data.datasource

import com.kreactive.test.app.data.model.remote.GetMovieResult
import com.kreactive.test.app.data.model.remote.SearchMoviesResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Response


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

    fun searchMovie(
        search: String,
        page: Int,
        onFailure: () -> Unit,
        onSuccess: (Response<SearchMoviesResult>?) -> Unit
    ) {
        omdbapi.searchMovie(search, page).enqueue(
            object : Callback<SearchMoviesResult> {
                override fun onFailure(call: Call<SearchMoviesResult>?, t: Throwable?) {
                    onFailure.invoke()
                }

                override fun onResponse(
                    call: Call<SearchMoviesResult>?,
                    response: Response<SearchMoviesResult>?
                ) {
                    onSuccess.invoke(response)
                }
            }
        )
    }

    fun getMovie(
        movieId: String,
        onFailure: () -> Unit,
        onSuccess: (Response<GetMovieResult>?) -> Unit
    ) {
        omdbapi.getMovie(movieId).enqueue(
            object : Callback<GetMovieResult> {
                override fun onFailure(call: Call<GetMovieResult>?, t: Throwable?) {
                    onFailure.invoke()
                }

                override fun onResponse(
                    call: Call<GetMovieResult>?,
                    response: Response<GetMovieResult>?
                ) {
                    onSuccess.invoke(response)
                }
            }
        )
    }
}

interface OMDBApiService {


    @GET(".")
    fun searchMovie(
        @Query("s") title: String,
        @Query("page") page: Int,
        @Query("apikey") apikey: String = "9cef1082"
    ): Call<SearchMoviesResult>

    @GET(".")
    fun getMovie(
        @Query("i") movieId: String,
        @Query("apikey") apikey: String = "9cef1082"
    ): Call<GetMovieResult>
}

