package com.yujie.letwechat.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.yujie.letwechat.R
import com.yujie.letwechat.ifs.ILoginView
import com.yujie.letwechat.presenter.LoginPre
import com.yujie.letwechat.utils.common_utils.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),ILoginView{
    val TAG : String = LoginActivity::class.java.simpleName
    val pre : LoginPre = LoginPre(this,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login()
    }

    private fun login() {
        btn_login.setOnClickListener(){
            val userName = et_usertel.text.toString()
            val password = et_password.text.toString()
            if (invalidEmpty(et_usertel))
                return@setOnClickListener
            if (invalidEmpty(et_password))
                return@setOnClickListener
            if (isNumber(userName)){
                Log.e(TAG,"login ")
                pre.login(userName,password)
            }
        }
    }

    override fun loginFailed(msg: String) {
        showShortToast(this,msg)
    }

    override fun loginSuccess() {
        KstartActivity(this,MainActivity::class.java)
        finish()
    }
}
