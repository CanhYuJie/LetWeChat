package com.yujie.letwechat

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.yujie.letwechat.utils.common_utils.showLongToastRes
import java.util.*

/**
 * Created by yujie on 16-10-28.
 */
class App : Application(){
    val TAG : String = App::class.java.simpleName
    private var context : Context? = null
    companion object {
        private val instance = App()
        fun initInstance():App{
            return instance
        }
    }

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
        EMClient.getInstance().init(applicationContext,options)
        EMClient.getInstance().setDebugMode(true)
        addConnectionListener()
    }

    /**
     * add connection state listener
     */
    private fun addConnectionListener() {
        EMClient.getInstance().addConnectionListener(KConnectionListener())
    }

    /**
     * set autologin and add friends need pass Invitation
     */
    private fun initChatOptions(options: EMOptions) {
        options.acceptInvitationAlways = true
        options.autoLogin = true
        Log.e(TAG,"onCreate +++++++++")
    }

    class KConnectionListener : EMConnectionListener{
        override fun onConnected() {
            // TODO do something here,like sync group info and user info
        }

        override fun onDisconnected(error: Int) {
            when{
                error == EMError.USER_REMOVED   ->  {
                    showErrorToast(R.string.account_removed)
                }
                error == EMError.USER_LOGIN_ANOTHER_DEVICE  ->  {
                    showErrorToast(R.string.user_login_another_device)
                }
                error == EMError.SERVER_SERVICE_RESTRICTED  ->  {
                    showErrorToast(R.string.user_forbidden)
                }
            }
        }

        private fun showErrorToast(stringId: Int) {
            showLongToastRes(initInstance().getLastActivity().applicationContext,stringId)
        }

    }
}