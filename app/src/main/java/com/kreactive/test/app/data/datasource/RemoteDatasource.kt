package com.kreactive.test.app.data.datasource

import com.google.gson.GsonBuilder
import com.kreactive.test.app.data.model.remote.GetMovieResult
import com.kreactive.test.app.data.model.remote.SearchDeserializer
import com.kreactive.test.app.data.model.remote.SearchMoviesResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import java.io.IOException


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

        val searchDeserializer =
            GsonBuilder().registerTypeAdapter(SearchMoviesResult::class.java, SearchDeserializer()).create()


        omdbapi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(searchDeserializer))
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(OMDBApiService::class.java)
    }

    suspend fun searchMovie(
        search: String,
        page: Int
    ): ResultWrapper<SearchMoviesResult> = withContext(Dispatchers.IO) {
        safeApiCall {
            omdbapi.searchMovie(search, page)!!

        }
    }

    suspend fun getMovie(
        movieId: String
    ): ResultWrapper<GetMovieResult> = withContext(Dispatchers.IO) {
        safeApiCall {
            omdbapi.getMovie(movieId)!!
        }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                ResultWrapper.NetworkError(throwable)
            }
        }
    }
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class NetworkError(val error: Throwable) : ResultWrapper<Nothing>()
}

interface OMDBApiService {

    @GET(".")
    suspend fun searchMovie(
        @Query("s") title: String,
        @Query("page") page: Int,
        @Query("apikey") apikey: String = "9cef1082"
    ): SearchMoviesResult?

    @GET(".")
    suspend fun getMovie(
        @Query("i") movieId: String,
        @Query("apikey") apikey: String = "9cef1082"
    ): GetMovieResult?
}

