package com.yujie.letwechat.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.hyphenate.chat.EMMessage
import com.hyphenate.easeui.EaseConstant
import com.yujie.kotlinfulicenter.model.bean.User
import com.yujie.letwechat.App
import com.yujie.letwechat.I
import com.yujie.letwechat.R
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_profile.*

class UserDetailActivity : AppCompatActivity() {
    val TAG : String = UserDetailActivity::class.java.simpleName
    var usernick: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setData()
        setListener()
    }

    private fun setListener() {
        titlebar.left_btn.setOnClickListener {
            finish()
        }
        btn_sendmsg.setOnClickListener {
            val intent = Intent(this,ChatActivity::class.java)
            intent.putExtra(EaseConstant.EXTRA_USER_ID,usernick)
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EMMessage.ChatType.Chat)
            startActivity(intent)
        }
    }

    private fun setData() {
        val user = intent.getSerializableExtra("user_tag") as User
        Log.e(TAG,"setData $user"+ App.initInstance().currentUser)
        if (user != null) {
            if (user.b_department == null) {
                tv_department.text = "尚未设置所在系部"
            }else{
               tv_department.text = user.b_department
            }
            if (user.b_class == null){
                tv_class.text = "尚未设置所在班级"
            }else{
                tv_class.text = user.b_class
            }
            tv_name.text = user.name
            tv_accout.text = user.user_nick
            usernick = user.user_nick
            if (user.sex.equals("男")){
                iv_sex.setImageDrawable(resources.getDrawable(R.drawable.ic_sex_male))
            }else{
                iv_sex.setImageDrawable(resources.getDrawable(R.drawable.ic_sex_female))
            }
            Glide.with(this).load(I.AVATAR_SERVER_ROOT+user.user_nick+ I.JPGFORMAT)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .bitmapTransform(CropCircleTransformation(this))
                    .into(iv_avatar)
        }
    }
}
