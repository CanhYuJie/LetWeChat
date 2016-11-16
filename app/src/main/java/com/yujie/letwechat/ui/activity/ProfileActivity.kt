package com.yujie.letwechat.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.yujie.kotlinfulicenter.model.bean.User
import com.yujie.letwechat.App
import com.yujie.letwechat.I

import com.yujie.letwechat.R
import com.yujie.letwechat.presenter.ProfilePre
import com.yujie.letwechat.utils.common_utils.invalidEmpty
import com.yujie.letwechat.utils.common_utils.showLongToast
import com.yujie.letwechat.ui.iview.IProfileView
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_profiles.*

class ProfileActivity : AppCompatActivity(),IProfileView, View.OnClickListener {
    var pre:ProfilePre? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles)
        pre = ProfilePre(this,this)
        setData()
        setListener()
    }

    private fun setListener() {
        tv_department.setOnClickListener(this)
        tv_bedroom.setOnClickListener(this)
        tv_b_class.setOnClickListener(this)
        tv_sex.setOnClickListener(this)
        iv_avatar.setOnClickListener(this)
        btn_sure.setOnClickListener(this)
    }

    private fun setData() {
        val user = App.initInstance().currentUser as User
        if (user != null) {
            if (!invalid(user.b_department)) {
                tv_department.text = "尚未设置所在系部"
            }else{
                tv_department.text = user.b_department
            }
            if (!invalid(user.b_class)){
                tv_b_class.text = "尚未设置所在班级"
            }else{
                tv_b_class.text = user.b_class
            }
            if (!invalid(user.b_bedroom)){
                tv_bedroom.text = "尚未设置所在寝室"
            }else{
                tv_bedroom.text = user.b_bedroom
            }
            if (!invalid(user.sex)){
                tv_sex.text = "尚未设置性别"
            }else{
                tv_sex.text = user.sex
            }
            tv_name.setText(user.name)
            tv_user_nick.setText(user.user_nick)
            tv_uid.setText(user.uid)
            Glide.with(this).load(I.AVATAR_SERVER_ROOT+user.user_nick+ I.JPGFORMAT)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .bitmapTransform(CropCircleTransformation(this))
                    .into(iv_avatar)
        }
    }

    private fun invalid(str: String?): Boolean{
        if (str == null){
            return false
        }else{
            try {
                if (str.equals("null")){
                    return false
                }
                if (str.toInt() == 0){
                    return false
                }
            }catch (e:Exception){ }
        }
        return true
    }

    override fun showClass(className: String) {
        tv_b_class.text = className
    }

    override fun showDepartment(department: String) {
        tv_department.text = department
    }

    override fun showBedRoom(bedroom: String) {
        tv_bedroom.text = bedroom
    }

    override fun showSex(sex: String) {
        tv_sex.text = sex
    }

    override fun showSelectData(flagId: Int, data: String) {
        when(flagId){
            R.id.tv_sex                 ->{tv_sex.text = data}
            R.id.tv_department          ->{tv_department.text = data}
            R.id.tv_bedroom             ->{tv_bedroom.text = data}
            R.id.tv_b_class             ->{tv_b_class.text = data}
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.iv_avatar){
            // TODO change avatar
        }
        if (v.id == R.id.btn_sure){
            modUserInfo()
        }else{
            pre?.getDataList(v.id,v)
        }
    }

    private fun modUserInfo() {
        if (invalidEmpty(tv_name))
            return
        if (invalidEmpty(tv_uid))
            return
        if (invalidEmpty(tv_user_nick))
            return
        val name = tv_name.text.toString()
        val uid = tv_uid.text.toString()
        val user_nick = tv_user_nick.text.toString()
        val department = tv_department.text.toString()
        val b_class = tv_b_class.text.toString()
        val bedroom = tv_bedroom.text.toString()
        val sex = tv_sex.text.toString()
        pre?.updateUser(name,uid,user_nick,department,b_class,bedroom,sex)
    }

    override fun updateFailed(msg: String) {
        showLongToast(this,msg)
    }

    override fun updateSuccess() {
        showLongToast(this,"数据更新成功")
        setData()
    }
}
