package com.yujie.letwechat

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.hyphenate.easeui.controller.EaseUI
import com.yujie.kotlinfulicenter.model.bean.User
import java.util.*

/**
 * Created by yujie on 16-10-28.
 */
class App : Application(){
    val TAG : String = App::class.java.simpleName
    companion object {
        var context : Context? = null
        private val instance = App()
        fun initInstance():App{
            return instance
        }
    }

    /**
     * set current user who has logined
     */
    var currentUser : User? = null

    /**
     * it's important to use list to save per activity
     */
    val activitylist : ArrayList<Activity> = ArrayList()
    /**
     * add activity
     */
    fun addActivity(activity : Activity): Unit {
        activitylist.add(activity)
    }

    /**
     * remove activity by activiyt's className
     */
    fun removeActivity(position: Int): Unit {
        activitylist.removeAt(position)
    }
    fun removeActivity(activity: Activity): Unit {
        activitylist.remove(activity)
    }

    /**
     * get last activity
     */
    fun getLastActivity(): Activity {
        return activitylist[activitylist.size-1]
    }
    /**
     * exit application and finish per activity
     */
    fun exit(): Unit {
        try {
            activitylist.forEach { it?.finish() }
        }catch (e : Exception){
            Log.e(TAG,"exit :->$e")
        }finally {
            System.exit(0)
        }
    }




    /**
     * init HXSDK,and added the connect state listener
     */
    override fun onCreate() {
        super.onCreate()
          context = applicationContext
        val options = EMOptions()
        initChatOptions(options)
        EaseUI.getInstance().init(context,options)
        EMClient.getInstance().setDebugMode(true)
    }


    /**
     * set autologin and add friends need pass Invitation
     */
    private fun initChatOptions(options: EMOptions) {
        options.acceptInvitationAlways = false
        options.autoLogin = true
    }

}