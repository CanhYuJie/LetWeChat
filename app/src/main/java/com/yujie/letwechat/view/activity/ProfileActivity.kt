package com.yujie.letwechat.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yujie.kotlinfulicenter.model.bean.RetDataBean
import com.yujie.letwechat.I
import com.yujie.letwechat.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    val TAG : String = ProfileActivity::class.java.simpleName
    var user: RetDataBean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        user = intent.getSerializableExtra("frindeProfile") as RetDataBean?
        if (user == null) {
            return
        }
        initUserInfo()
        sendMsgListener()
    }

    private fun sendMsgListener() {
        btn_sendmsg.setOnClickListener {
            //TODO go chatActivity here
            Log.e(TAG,"sendMsgListener here go chatActivity")
        }
    }

    private fun initUserInfo() {
        val imgPath = I.SERVER_ROOT+ I.REQUEST_DOWNLOAD_AVATAR+"?"+
                I.NAME_OR_HXID+"="+user?.muserName+"&"+
                I.AVATAR_TYPE+"="+ I.AVATAR_TYPE_USER_PATH+"&"+
                I.Avatar.AVATAR_SUFFIX+"="+ I.AVATAR_SUFFIX_PNG+"&"+
                I.AVATAR_WIDTH+"="+ I.AVATAR_WIDTH_DEFAULT+"&"+
                I.AVATAR_HEIGHT+"="+ I.AVATAR_HEIGHT_DEFAULT
        Glide.with(this).load(imgPath)
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_avatar)
        tv_name.text = user?.muserNick
    }
}
