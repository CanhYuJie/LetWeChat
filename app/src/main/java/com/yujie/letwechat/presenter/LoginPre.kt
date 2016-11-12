package com.yujie.letwechat.presenter

import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.yujie.kotlinfulicenter.model.bean.Result
import com.yujie.kotlinfulicenter.model.bean.ResultData
import com.yujie.letwechat.App
import com.yujie.letwechat.R
import com.yujie.letwechat.api.ApiFactory
import com.yujie.letwechat.db.DBHelper
import com.yujie.letwechat.view.iview.ILoginView
import com.yujie.letwechat.utils.common_utils.MD5
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by yujie on 16-11-1.
 */
class LoginPre(val context: Context, val view: ILoginView) {
    val TAG : String = LoginPre::class.java.simpleName
    val ApiService = ApiFactory.getNetApiInstance()
    fun login(userName: String, password: String): Unit {
        ApiService!!.login(userName,MD5.getData(userName+password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    Log.e(TAG,"login "+result)
                    Log.e(TAG,"login "+MD5.getData(userName+password))
                    if (result != null && result.flag) {
                         Log.e(TAG,"login local server success")
                         loginHXServer(userName,password,result)
                     }else{
                        view.loginFailed(context.getString(R.string.no_user))
                    }
                },{msg ->
                    view.loginFailed(msg.message.toString())
                })
    }

    fun loginHXServer(userName: String, password: String, result: ResultData): Unit {
        EMClient.getInstance().login(userName, MD5.getData(userName+password),object :EMCallBack{
            override fun onSuccess() {
                Log.e(TAG,"login HX server success")
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                App.initInstance().currentUser = result.data
                if (DBHelper(context).findUserById(result.data!!.uid)!=null){
                    DBHelper(context).updateStatus(1,userName)
                    view.loginSuccess()
                }else{
                    DBHelper(context).addUser(result.data!!,1)
                    view.loginSuccess()
                }
            }

            override fun onProgress(p0: Int, p1: String?) {

            }

            override fun onError(p0: Int, p1: String?) {
                Looper.prepare()
                view.loginFailed(p1!!)
                Looper.loop()
            }
        })
    }
}