package com.yujie.letwechat.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AndroidException
import android.util.Log
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.yujie.letwechat.App
import com.yujie.letwechat.R
import com.yujie.letwechat.api.ApiFactory
import com.yujie.letwechat.db.DBHelper
import com.yujie.letwechat.utils.common_utils.*
import kotlinx.android.synthetic.main.activity_setting.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SettingActivity : AppCompatActivity() {
    val TAG : String = SettingActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setListener()
    }

    private fun setListener() {
        btn_logout.setOnClickListener {
            EMClient.getInstance().logout(true,object :EMCallBack{
                override fun onSuccess() {
                    logoutFromServer()
                }

                override fun onProgress(p0: Int, p1: String?) {
                }

                override fun onError(p0: Int, p1: String?) {
                    Log.e(TAG,"onError "+"退出登录失败")
                }

            })
        }

//        btn_modPwd.setOnClickListener {
//            if (invalidEmpty(et_oldpassword))
//                return@setOnClickListener
//            if (invalidEmpty(et_newpassword))
//                return@setOnClickListener
//            if (invalidEmpty(et_repeatpassword))
//                return@setOnClickListener
//            val oldpwd = et_oldpassword.text.toString()
//            val newpwd = et_newpassword.text.toString()
//            val reppwd = et_repeatpassword.text.toString()
//            if (!newpwd.equals(reppwd)){
//                et_repeatpassword.error = "密码不一致"
//                et_repeatpassword.requestFocus()
//                return@setOnClickListener
//            }
//            val api = ApiFactory.getNetApiInstance()
//            api!!.login(App.initInstance().currentUser!!.user_nick!!,
//                    MD5.getData(App.initInstance().currentUser!!.user_nick!!+oldpwd))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({
//                        if ((it!=null) and (it.flag)){
//                            modNewPwd(reppwd)
//                        }else{
//                            showLongToast(this@SettingActivity,"原密码验证不正确，无法修改密码")
//                        }
//                    },{e -> Log.e(TAG,"setListener "+e.toString())})
//        }
    }

    private fun logoutFromServer() {
        if (DBHelper(this@SettingActivity).updateStatus(0, App.initInstance().currentUser?.user_nick!!)){
            App.initInstance().currentUser = null
            runOnUiThread {
                showLongToast(this@SettingActivity,"退出登录成功")
                SingleStartActivity(this@SettingActivity,LoginActivity::class.java)
            }
        }else{
            showLongToast(this@SettingActivity,"退出登录失败")
        }
    }

    private fun modNewPwd(reppwd: String) {
        val api = ApiFactory.getNetApiInstance()
        api!!.updatePwd(App.initInstance().currentUser?.user_nick!!,
                MD5.getData(App.initInstance().currentUser?.user_nick+reppwd))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if ((it!=null) and (it.flag)){
                        modHXPWD(reppwd)
                    }else{
                        showLongToast(this@SettingActivity,"无法修改密码")
                    }
                },{e -> Log.e(TAG,"setListener "+e.toString())})
    }

    private fun modHXPWD(reppwd: String) {

    }
}
