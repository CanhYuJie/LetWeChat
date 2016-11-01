package com.yujie.letwechat.view.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.animation.AlphaAnimation
import com.hyphenate.chat.EMClient
import com.yujie.kotlinfulicenter.model.bean.Result
import com.yujie.letwechat.I
import com.yujie.letwechat.R
import com.yujie.letwechat.db.DBHelper
import com.yujie.letwechat.utils.common_utils.KstartActivity
import com.yujie.letwechat.utils.common_utils.showLongToastRes
import com.yujie.letwechat.utils.net_utils.OkHttpUtils
import kotlinx.android.synthetic.main.activity_splash.*
import kotlin.concurrent.thread

class SplashActivty : AppCompatActivity() {
    val TAG : String = SplashActivty::class.java.simpleName
    private val sleepTime = 3000
    private var context : Context? = null
    private val GO_MAIN = 101
    private val GO_LOGIN = 102
    private val handler =object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                GO_MAIN     ->  {
                    goMain()
                }

                GO_LOGIN    ->{
                    goLogin()
                }
            }
        }
    }

    private fun goMain() {
        KstartActivity(this,MainActivity::class.java)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        context = this
        val animation = AlphaAnimation(0.3f,1.0f)
        animation.duration = 3000
        activity_splash.startAnimation(animation)
    }

    override fun onStart() {
        super.onStart()
        val util = OkHttpUtils<Result>(this)
        util.setRequestUrl(I.REQUEST_SERVERSTATUS)
            .targetClass(Result::class.java)
            .execute(object : OkHttpUtils.OnCompleteListener<Result>{
                override fun onSuccess(result: Result) {
                    if (result != null && result.retMsg) {
                        if (DBHelper(this@SplashActivty).findLoginUser() != null) {
                            hxIsLogin()
                        }else{
                            handler.sendEmptyMessageDelayed(GO_LOGIN,sleepTime.toLong())
                        }
                    }
                }

                override fun onError(msg: String) {
                    showLongToastRes(this@SplashActivty,R.string.no_connection)
                }
            })
    }

    private fun goLogin() {
        KstartActivity(this,LoginActivity::class.java)
        finish()
    }

    private fun hxIsLogin() {
            thread {
                Log.e(TAG,"hxIsLogin "+EMClient.getInstance().isLoggedInBefore)
                if (EMClient.getInstance().isLoggedInBefore){
                    val start = System.currentTimeMillis()
                    EMClient.getInstance().groupManager().loadAllGroups()
                    EMClient.getInstance().chatManager().loadAllConversations()
                    val costTime = System.currentTimeMillis() - start
                    if (sleepTime - costTime > 0) {
                        handler.sendEmptyMessageDelayed(GO_MAIN,sleepTime-costTime)
                    }
                }else{
                    handler.sendEmptyMessageDelayed(GO_LOGIN, sleepTime.toLong())
                }
            }
    }

}
