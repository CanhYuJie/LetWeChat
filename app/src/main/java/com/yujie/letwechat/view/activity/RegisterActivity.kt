package com.yujie.letwechat.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.yujie.letwechat.R
import com.yujie.letwechat.ifs.IRegisterView
import com.yujie.letwechat.presenter.RegisterPre
import com.yujie.letwechat.utils.common_utils.KstartActivity
import com.yujie.letwechat.utils.common_utils.invalidEmpty
import com.yujie.letwechat.utils.common_utils.isNumber
import com.yujie.letwechat.utils.common_utils.showShortToast
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_title.*

class RegisterActivity : AppCompatActivity(),IRegisterView {
    val TAG : String = RegisterActivity::class.java.simpleName
    val pre = RegisterPre(this,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        img_back.visibility = View.VISIBLE
        register()
    }

    private fun register() {
        btn_register.setOnClickListener{
            val userName = et_usertel.text.toString()
            val password = et_password.text.toString()
            val userNick = et_nick.text.toString()
            if (invalidEmpty(et_usertel))
                return@setOnClickListener
            if (invalidEmpty(et_password))
                return@setOnClickListener
            if (invalidEmpty(et_nick))
                return@setOnClickListener
            pre.registerLocal(userName,userNick,password)
        }
    }

    override fun registerSuccess(id : String) {
        KstartActivity(this,LoginActivity::class.java,"userName",id)
        finish()
    }

    override fun registerFailed(msg: String) {
        showShortToast(this,msg)
    }
}
