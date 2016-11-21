package com.yujie.letwechat.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.yujie.letwechat.App
import com.yujie.letwechat.I

import com.yujie.letwechat.R
import com.yujie.letwechat.utils.common_utils.KstartActivity
import com.yujie.letwechat.ui.activity.ProfileActivity
import com.yujie.letwechat.ui.activity.SettingActivity
import com.yujie.letwechat.ui.activity.UserDetailActivity
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_me.*

/**
 * A simple [Fragment] subclass.
 */
class MeFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_me, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setListener()
    }

    private fun setListener() {
        view_user.setOnClickListener {
            KstartActivity(activity, ProfileActivity::class.java)
        }
        txt_setting.setOnClickListener {
            KstartActivity(activity,SettingActivity::class.java)
        }
    }

    private fun setData() {
        val currentUser = App.initInstance().currentUser
        Glide.with(this).load(I.AVATAR_SERVER_ROOT+currentUser?.user_nick+ I.JPGFORMAT)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .bitmapTransform(CropCircleTransformation(activity))
                .into(head)
        tvname.text = currentUser?.name
        if (currentUser?.sex.equals("男")){
            iv_sex.setImageDrawable(resources.getDrawable(R.drawable.ic_sex_male))
        }else{
            iv_sex.setImageDrawable(resources.getDrawable(R.drawable.ic_sex_female))
        }
        tvmsg.text = currentUser?.user_nick
    }

    override fun lazyFetchData() {

    }
}
