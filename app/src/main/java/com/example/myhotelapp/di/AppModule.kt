package com.example.myhotelapp.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.example.myhotelapp.data.remote.HotelApi
import com.example.myhotelapp.data.repository.HotelRepository
import com.example.myhotelapp.data.repository.HotelRepositoryImpl
import com.example.myhotelapp.db.HotelDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
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
    fun provideRoomDatabase(app: Application): HotelDatabase =
        Room.databaseBuilder(
            app,
            HotelDatabase::class.java,
            "favorite_room2_db.db"
        ).build()

    @Provides
    @Singleton
    fun provideRepository(api: HotelApi, db: HotelDatabase): HotelRepository = HotelRepositoryImpl(api, db)
}