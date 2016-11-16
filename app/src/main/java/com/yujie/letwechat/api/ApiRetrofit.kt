package com.yujie.letwechat.api

import android.util.Log
import com.yujie.letwechat.App
import com.yujie.letwechat.I
import com.yujie.letwechat.utils.common_utils.getNetwokState
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by yujie on 16-11-9.
 */
class ApiRetrofit() {
    /**
     * retrofit obj
     */
    var ret : Retrofit?= null

    var apiService: ApiService? = null
    fun getApiServiceInstance():ApiService? {
        return apiService
    }
    /**
     * init Interceptor,use to rewrite cache
     */
    val REWRITE_CACHE_CONTROL_INTERCEPTOR = Interceptor { chain ->
        val cacheBuilder = CacheControl.Builder()
        cacheBuilder.maxAge(0, TimeUnit.SECONDS)
        cacheBuilder.maxStale(365, TimeUnit.DAYS)
        val cacheControl = cacheBuilder.build()

        var request = chain.request()
        if (!getNetwokState(App.context!!)){
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
        }
        val response = chain.proceed(request)
        if (!getNetwokState(App.context!!)){
            val maxAge = 0
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build()
        }else{
            val maxStale = 60 * 60 * 24 * 28
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build()
        }
    }
    /**
     * init retrofit client
     * and there is a ReWrite cache control Interceptor
     */
    init {
        val cacheDir = File(App.context!!.cacheDir,"responses")
        val cacheSize = 10 * 1024 * 1024
        val cache = Cache(cacheDir, cacheSize.toLong())
        val client = OkHttpClient.Builder()
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache).build()
        ret = Retrofit.Builder()
                .baseUrl(I.SERVER_ROOT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        apiService = ret?.create(ApiService::class.java)
    }
}