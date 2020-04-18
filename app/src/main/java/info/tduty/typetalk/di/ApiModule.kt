package info.tduty.typetalk.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import info.tduty.typetalk.api.*
import info.tduty.typetalk.data.pref.UrlStorage
import info.tduty.typetalk.socket.SslOkHttpClientBuilderBase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val builder: OkHttpClient.Builder = SslOkHttpClientBuilderBase()
            .setup(context)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Provides
    @Singleton
    fun provideClassApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): ClassApi {
        return retrofitBuilder
            .baseUrl(UrlStorage.getUrl() + ClassApi.ENDPOINT_PREFIX)
            .client(okHttpClient)
            .build()
            .create(ClassApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLessonApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): LessonApi {
        return retrofitBuilder
            .baseUrl(UrlStorage.getUrl())
            .client(okHttpClient)
            .build()
            .create(LessonApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHistoryApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): HistoryApi {
        return retrofitBuilder
            .baseUrl(UrlStorage.getUrl())
            .client(okHttpClient)
            .build()
            .create(HistoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChatApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): ChatApi {
        return retrofitBuilder
            .baseUrl(UrlStorage.getUrl() + ChatApi.ENDPOINT_PREFIX)
            .client(okHttpClient)
            .build()
            .create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): LoginApi {
        return retrofitBuilder
            .baseUrl(UrlStorage.getUrl() + LoginApi.ENDPOINT_PREFIX)
            .client(okHttpClient)
            .build()
            .create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): DictionaryApi {
        return retrofitBuilder
            .baseUrl(UrlStorage.getUrl())
            .client(okHttpClient)
            .build()
            .create(DictionaryApi::class.java)
    }
}