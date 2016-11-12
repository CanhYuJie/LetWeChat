package com.yujie.letwechat.presenter

import android.content.Context
import android.os.Looper
import android.util.Log
import com.hyphenate.EMError
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.yujie.kotlinfulicenter.model.bean.Result
import com.yujie.letwechat.I
import com.yujie.letwechat.R
import com.yujie.letwechat.api.ApiFactory
import com.yujie.letwechat.view.iview.IRegisterView
import com.yujie.letwechat.utils.common_utils.MD5
import com.yujie.letwechat.utils.common_utils.SHA1
import com.yujie.letwechat.utils.common_utils.showLongToast
import com.yujie.letwechat.utils.common_utils.showShortToastRes
import com.yujie.letwechat.utils.net_utils.OkHttpUtils
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.concurrent.thread

/**
 * Created by yujie on 16-11-1.
 */
class RegisterPre(val context: Context,val view: IRegisterView) {
    val TAG : String = RegisterPre::class.java.simpleName
    val register_service = ApiFactory.getNetApiInstance()
    fun registerLocal(userName : String,userUid: String, userNick : String,password : String) : Unit {
        register_service!!.register(userName,userUid,userNick,MD5.getData(userName+password))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({result ->
                            if (result != null && result.flag) {
                                registerHXServer(userNick,password)
                            }else{
                                view.registerFailed(context.getString(R.string.user_exist))
                            }
                        },{msg ->
                            view.registerFailed(msg.message.toString())
                        })
    }

    private fun registerHXServer(usernick: String, password: String) {
        thread {
            try {
                EMClient.getInstance().createAccount(usernick,MD5.getData(usernick+password))
                view.registerSuccess(usernick)
            }catch (e : HyphenateException){
                val errorCode = e.errorCode
                when(errorCode){
                    EMError.NETWORK_ERROR       ->  {
                        val msg = context.getString(R.string.no_connection)
                        unRegister(usernick,msg)
                    }
                    EMError.USER_ALREADY_EXIST  ->  {
                        val msg = context.getString(R.string.user_exist)
                        unRegister(usernick,msg)
                    }
                    EMError.USER_AUTHENTICATION_FAILED  ->  {
                        val msg = context.getString(R.string.no_permission)
                        unRegister(usernick, msg)
                    }
                    EMError.USER_ILLEGAL_ARGUMENT   ->  {
                        val msg = context.getString(R.string.username_invalid_failed)
                        unRegister(usernick, msg)
                    }
                    else        ->  {
                        val msg = context.getString(R.string.register_failed)
                        unRegister(usernick, msg)
                    }
                }
            }
        }
    }

    fun unRegister(usernick: String, msg: String): Unit {
        register_service!!.unregister(usernick)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({result ->
                            if (result != null) { view.registerFailed(msg) }
                            else{ view.registerFailed(context.getString(R.string.unregister_failed)) }
                        }, {msg -> view.registerFailed(context.getString(R.string.unregister_failed)) })
    }
}