package com.yujie.letwechat.api

import android.content.Context
import android.util.Log
import com.yujie.letwechat.api.ApiService

/**
 * Created by yujie on 16-11-9.
 */
class ApiFactory {
    companion object{
        private val monitor = Object()
        /**
         * product the ApiService
         */
        private var apiService: ApiService? = null
        fun getNetApiInstance(): ApiService?{
            synchronized(monitor){
                if (apiService == null){
                    apiService = ApiRetrofit().getApiServiceInstance()
                }
                return apiService
            }
        }
    }
}