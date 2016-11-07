package com.yujie.letwechat.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yujie.kotlinfulicenter.model.bean.RetDataBean
import com.yujie.letwechat.I

import com.yujie.letwechat.R
import com.yujie.letwechat.ifs.IContactView
import com.yujie.letwechat.presenter.ContactPre
import com.yujie.letwechat.utils.common_utils.KstartActivity
import com.yujie.letwechat.view.activity.FriendMsgActivity
import com.yujie.letwechat.view.activity.ProfileActivity
import kotlinx.android.synthetic.main.fragment_contact.*

class ContactFragment : IContactView,BaseFragment() {
    val TAG : String = ContactFragment::class.java.simpleName
    var pre : ContactPre? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_contact, container, false)
        return view
    }

    override fun lazyFetchData() {
        super.lazyFetchData()
        Log.e(TAG,"lazyFetchData 1111")
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pre = ContactPre(activity,this,contact_rec)
        if (isVisible){
            Log.e(TAG,"onViewCreated "+pre?.hasContent())
            if (pre?.hasContent()!! && !pre?.isFirstIn()!!){
                pre?.initAdapter()
            }else{
                init()
            }
        }
    }

    fun init(): Unit {
        pre?.initData2()
    }
    override fun goProfileActivity(t: RetDataBean) {
        KstartActivity(activity,ProfileActivity::class.java,"frindeProfile",t)
    }

    override fun goFriendMsgActivity() {
        KstartActivity(activity,FriendMsgActivity::class.java)
    }


}
