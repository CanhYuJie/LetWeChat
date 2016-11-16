package com.yujie.letwechat.ui.activity

import android.Manifest
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.hyphenate.chat.EMClient
import com.yujie.letwechat.App
import com.yujie.letwechat.R
import com.yujie.letwechat.api.ApiFactory
import com.yujie.letwechat.db.DBHelper
import com.yujie.letwechat.utils.common_utils.KstartActivity
import com.yujie.letwechat.utils.common_utils.PermissionHelper
import com.yujie.letwechat.utils.common_utils.showLongToastRes
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.concurrent.thread

class SplashActivty : AppCompatActivity() {
    val TAG : String = SplashActivty::class.java.simpleName
    val netStateApi = ApiFactory.getNetApiInstance()
    private val sleepTime = 1500
    private var context : Context? = null
    private val GO_MAIN = 101
    private val GO_LOGIN = 102
    val permission = PermissionHelper(this@SplashActivty)
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
        context = this
    }

    override fun onStart() {
        super.onStart()
        netStateApi!!.getNetWorkState()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t ->
                        Log.e(TAG,"onStart "+t.toString())
                        if (t != null && t.flag) {
                            permission.requestPermissions("请授予[读写SD卡]权限！",
                                    object :PermissionHelper.PermissionListener{
                                        override fun doAfterGrand(vararg permission: String?) {
                                            Log.e(TAG,"doAfterDenied 没有权限")
                                            finish()
                                            System.exit(0)
                                        }

                                        override fun doAfterDenied(vararg permission: String?) {
                                            val user = DBHelper(this@SplashActivty).findLoginUser()
                                            if (user != null) {
                                                App.initInstance().currentUser = user
                                                hxIsLogin()
                                            }else{
                                                handler.sendEmptyMessageDelayed(GO_LOGIN,sleepTime.toLong())
                                            }
                                        }

                                    }, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                        }
                    }, { showLongToastRes(this@SplashActivty,R.string.no_connection) })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permission.handleRequestPermissionsResult(requestCode,permissions,grantResults)
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
