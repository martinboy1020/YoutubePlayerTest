//package com.example.youtubeplayertest.di
//
//import android.content.Context
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
//import com.nt.universalscore.BuildConfig
//import com.nt.universalscore.datacenter.Repository
//import com.nt.universalscore.datacenter.api.Contract
//import com.nt.universalscore.datacenter.slack.SlackRepository
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import org.jetbrains.annotations.Contract
//import org.koin.android.ext.koin.androidContext
//import org.koin.core.qualifier.named
//import org.koin.dsl.module
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
///**
// * 用於設置API的Module, 初始化在BaseApplication的getKoinModules()
// */
//val apiClientScope = module {
//
//    fun provideOkHttpClient(): OkHttpClient{
//        val okHttpClient = OkHttpClient.Builder()
//        okHttpClient.connectTimeout(Contract().connectTimeout, TimeUnit.SECONDS)
//        okHttpClient.writeTimeout(Contract().connectTimeout, TimeUnit.SECONDS)
//        okHttpClient.readTimeout(Contract().connectTimeout, TimeUnit.SECONDS)
//        // 攔截器 用於查看API回傳的Log時使用 Log關鍵字: OkHttp, 只有Debug版本時開啟
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        if(BuildConfig.DEBUG_VERSION) okHttpClient.addInterceptor(httpLoggingInterceptor)
//        return okHttpClient.build()
//    }
//
//    fun provideConverter(): Gson =
//        GsonBuilder()
//            .setLenient()
//            .create()
//
//    fun provideUsApiRetrofit(client: OkHttpClient, gson: Gson): Retrofit{
//        val baseUrl = BuildConfig.USCORE_HOST
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create(gson))
////            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .client(client)
//            .build()
//    }
//
//    fun provideChatApiRetrofit(client: OkHttpClient, gson: Gson): Retrofit{
//        val baseUrl = BuildConfig.US_CHAT_HOST
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create(gson))
////            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .client(client)
//            .build()
//    }
//
//    fun provideSlackApiRetrofit(client: OkHttpClient, gson: Gson): Retrofit{
//        val baseUrl = "https://hooks.slack.com/services/"
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create(gson))
////            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .client(client)
//            .build()
//    }
//
//    fun provideRepository(context: Context, client: Retrofit, chatClient: Retrofit) =
//        Repository(context, client, chatClient)
//
//    fun provideSlackRepository(context: Context, slackClient: Retrofit) =
//        SlackRepository(context, slackClient)
//
//    single { provideOkHttpClient() }
//    single { provideConverter() }
//    single(named("match_api")) { provideUsApiRetrofit(get(), get()) }
//    single(named("chat_api")) { provideChatApiRetrofit(get(), get()) }
//    single(named("slack_api")) { provideSlackApiRetrofit(get(), get()) }
//    single { provideRepository(androidContext(), get(named("match_api")), get(named("chat_api"))) }
//    single { provideSlackRepository(androidContext(), get(named("slack_api"))) }
//}