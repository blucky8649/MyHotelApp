package com.example.myhotelapp.di

import androidx.paging.ExperimentalPagingApi
import com.example.myhotelapp.data.remote.HotelApi
import com.example.myhotelapp.data.repository.HotelRepository
import com.example.myhotelapp.data.repository.HotelRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalPagingApi
object AppModule {

    @Provides
    @Singleton
    fun provideRemoteApi() : HotelApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://www.gccompany.co.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(HotelApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: HotelApi): HotelRepository = HotelRepositoryImpl(api)
}