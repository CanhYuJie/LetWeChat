package com.yujie.letwechat.presenter

import android.content.Context
import android.util.Log
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.yujie.kotlinfulicenter.model.bean.Result
import com.yujie.letwechat.App
import com.yujie.letwechat.I
import com.yujie.letwechat.db.DBHelper
import com.yujie.letwechat.ifs.ILoginView
import com.yujie.letwechat.utils.net_utils.OkHttpUtils

/**
 * Created by yujie on 16-11-1.
 */
class LoginPre(val context: Context, val view:ILoginView) {
    val TAG : String = LoginPre::class.java.simpleName
    fun login(userName: String, password: String): Unit {
        val utils = OkHttpUtils<Result>(context)
        utils.setRequestUrl(I.REQUEST_LOGIN)
             .addParams(I.User.USER_NAME,userName)
             .addParams(I.User.PASSWORD,password)
             .targetClass(Result::class.java)
             .execute(object : OkHttpUtils.OnCompleteListener<Result>{
                 override fun onError(msg: String) {
                     view.loginFailed(msg)
                 }

                 override fun onSuccess(result: Result) {
                     if (result != null && result.retMsg) {
                         Log.e(TAG,"login local server success")
                         loginHXServer(userName,password,result)
                     }
                 }
             })
    }

    fun loginHXServer(userName: String, password: String, result: Result): Unit {
        EMClient.getInstance().login(userName,password,object :EMCallBack{
            override fun onSuccess() {
                Log.e(TAG,"login HX server success")
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                App.initInstance().currentUser = result.retData
                DBHelper(context).addUser(result.retData!!,1)
                view.loginSuccess()
            }

            override fun onProgress(p0: Int, p1: String?) {

            }

            override fun onError(p0: Int, p1: String?) {
                view.loginFailed(p1!!)
            }

        })
    }
}