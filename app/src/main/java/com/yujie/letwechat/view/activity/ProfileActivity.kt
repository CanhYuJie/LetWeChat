package com.yujie.letwechat.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.yujie.kotlinfulicenter.model.bean.User
import com.yujie.letwechat.App
import com.yujie.letwechat.I

import com.yujie.letwechat.R
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_profiles.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles)
        setData()
        setListener()
    }

    private fun setListener() {
        tv_department.setOnClickListener(MyListener())
        tv_bedroom.setOnClickListener(MyListener())
        tv_b_class.setOnClickListener(MyListener())
        tv_sex.setOnClickListener(MyListener())
        iv_avatar.setOnClickListener(MyListener())
    }

    private fun setData() {
        val user = App.initInstance().currentUser
        Log.e("Data","setData $user")
        if (user != null) {
//            if ((user.b_department == null) or (user.b_department!!.toInt() <=0)) {
//                tv_department.text = "尚未设置所在系部"
//            }else{
//                tv_department.text = user.b_department
//            }
//            if ((user.b_class == null) or (user.b_class!!.toInt() <= 0) ){
//                tv_b_class.text = "尚未设置所在班级"
//            }else{
//                tv_b_class.text = user.b_class
//            }
//            if ((user.b_bedroom == null) or (user.b_bedroom!!.toInt() <=0)){
//                tv_bedroom.text = user.b_bedroom
//            }
//
//            if (user.sex == null){
//                tv_sex.text = "尚未设置性别"
//            }else{
//                tv_sex.text = user.sex
//            }
            tv_name.text = user.name
            tv_user_nick.text = user.user_nick
            tv_uid.text = user.uid
            Glide.with(this).load(I.AVATAR_SERVER_ROOT+user.user_nick+ I.JPGFORMAT)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .bitmapTransform(CropCircleTransformation(this))
                    .into(iv_avatar)
        }
    }

    class MyListener() : View.OnClickListener{
        override fun onClick(v: View) {
            when(v.id){
                R.id.iv_avatar          ->{}
                R.id.tv_b_class         ->{}
                R.id.tv_bedroom         ->{}
                R.id.tv_department      ->{}
                R.id.tv_sex             ->{}
            }
        }
    }
}
