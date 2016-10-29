package com.yujie.letwechat.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AlphaAnimation
import com.yujie.letwechat.R
import com.yujie.letwechat.utils.net_utils.OkHttpUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivty : AppCompatActivity() {
    val TAG : String = SplashActivty::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val animation = AlphaAnimation(0.3f,1.0f)
        activity_splash.startAnimation(animation)
    }

    override fun onStart() {
        super.onStart()
        val util = OkHttpUtils<String>(this)
        util.url("http://www.baidu.com")
        .targetClass(String::class.java)
        .execute(object : OkHttpUtils.OnCompleteListener<String> {
            override fun onError(msg: String?) {
                Log.e(TAG,"onError :-> $msg")
            }

            override fun onSuccess(result: String) {
                for (c in result) {
                    Log.e(TAG,"onSuccess fun: -> $c ")
                }
            }
        })
    }
}
