package com.yujie.letwechat

import android.app.Application
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions

/**
 * Created by yujie on 16-10-28.
 */
class App : Application(){
    val TAG : String = App::class.java.simpleName
    companion object {
        private val instance = App()
        fun initInstance():App{
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        val options = EMOptions()
        options.acceptInvitationAlways = true
        EMClient.getInstance().init(applicationContext,options)
        EMClient.getInstance().setDebugMode(true)
    }
}