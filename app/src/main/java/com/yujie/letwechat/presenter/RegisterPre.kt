package com.yujie.letwechat.presenter

import android.content.Context
import android.os.Looper
import com.hyphenate.EMError
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.yujie.kotlinfulicenter.model.bean.Result
import com.yujie.letwechat.I
import com.yujie.letwechat.R
import com.yujie.letwechat.ifs.IRegisterView
import com.yujie.letwechat.utils.common_utils.showLongToast
import com.yujie.letwechat.utils.common_utils.showShortToastRes
import com.yujie.letwechat.utils.net_utils.OkHttpUtils
import kotlin.concurrent.thread

/**
 * Created by yujie on 16-11-1.
 */
class RegisterPre(val context: Context,val view: IRegisterView) {
    fun registerLocal(userName : String, userNick : String,password : String) : Unit {
        val util = OkHttpUtils<Result>(context)
        util.setRequestUrl(I.REQUEST_REGISTER)
            .addParams(I.User.USER_NAME,userName)
            .addParams(I.User.NICK,userNick)
            .addParams(I.User.PASSWORD,password)
            .targetClass(Result::class.java)
            .post()
            .execute(object :OkHttpUtils.OnCompleteListener<Result>{
                override fun onSuccess(result: Result) {
                    if (result != null && result.retMsg) {
                        registerHXServer(userName,password)
                    }else{
                        view.registerFailed(context.getString(R.string.user_exist))
                    }
                }

                override fun onError(msg: String) {
                    view.registerFailed(msg)
                }

            })
    }

    private fun registerHXServer(userName: String, password: String) {
        thread {
            try {
                EMClient.getInstance().createAccount(userName,password)
                view.registerSuccess(userName)
            }catch (e : HyphenateException){
                val errorCode = e.errorCode
                when(errorCode){
                    EMError.NETWORK_ERROR       ->  {
                        val msg = context.getString(R.string.no_connection)
                        unRegister(userName,msg)
                    }
                    EMError.USER_ALREADY_EXIST  ->  {
                        val msg = context.getString(R.string.user_exist)
                        unRegister(userName,msg)
                    }
                    EMError.USER_AUTHENTICATION_FAILED  ->  {
                        val msg = context.getString(R.string.no_permission)
                        unRegister(userName, msg)
                    }
                    EMError.USER_ILLEGAL_ARGUMENT   ->  {
                        val msg = context.getString(R.string.username_invalid_failed)
                        unRegister(userName, msg)
                    }
                    else        ->  {
                        val msg = context.getString(R.string.register_failed)
                        unRegister(userName, msg)
                    }
                }
            }
        }
    }

    fun unRegister(username: String, msg: String): Unit {
        val util = OkHttpUtils<Result>(context)
        util.setRequestUrl(I.REQUEST_UNREGISTER)
                .addParams(I.User.USER_NAME,username)
                .targetClass(Result::class.java)
                .execute(object :OkHttpUtils.OnCompleteListener<Result>{
                    override fun onSuccess(result: Result) {
                        if (result != null) {
                            view.registerFailed(msg)
                        }
                    }

                    override fun onError(msg: String) {
                        view.registerFailed(context.getString(R.string.register_failed))
                        showLongToast(context,"取消注册本地服务器失败，请联系管理员,注册失败")
                    }

                })
    }
}