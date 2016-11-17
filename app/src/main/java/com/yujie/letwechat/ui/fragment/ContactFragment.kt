package com.yujie.letwechat.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyphenate.EMContactListener
import com.hyphenate.chat.EMClient
import com.hyphenate.easeui.ui.EaseContactListFragment
import com.yujie.kotlinfulicenter.model.bean.User

import com.yujie.letwechat.R
import com.yujie.letwechat.presenter.ContactPre
import com.yujie.letwechat.ui.activity.FriendMsgActivity
import com.yujie.letwechat.utils.common_utils.KstartActivity
import com.yujie.letwechat.ui.activity.UserDetailActivity
import com.yujie.letwechat.ui.iview.IContactView
import com.yujie.letwechat.utils.common_utils.showLongToast
import kotlinx.android.synthetic.main.fragment_contact.*

class ContactFragment :BaseFragment(),IContactView {
    var pre:ContactPre? = null
    val TAG : String = ContactFragment::class.java.simpleName
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_contact, container, false)
        return view
    }


    override fun lazyFetchData() {
        super.lazyFetchData()
        pre?.getContacts()
    }


    private fun setListener() {
        new_friend_root.setOnClickListener {
            KstartActivity(activity,FriendMsgActivity::class.java)
        }
        group_chat_root.setOnClickListener {
            //TODO go group activity to chat
        }
        public_group_root.setOnClickListener {
            //TODO go public group activity
        }
        EMClient.getInstance().contactManager()
                .setContactListener(object :EMContactListener{
                    override fun onContactInvited(user_name: String, p1: String) {
                        //收到好友邀请
                        pre?.addFriendMsg(user_name,p1)
                    }

                    override fun onContactRefused(user_name: String) {
                        showLongToast(context,"$user_name 拒绝了你的好友申请")
                    }

                    override fun onContactDeleted(user_name: String) {

                    }

                    override fun onContactAdded(user_name: String) {

                    }

                    override fun onContactAgreed(user_name: String) {
                        pre?.getUserInfo(user_name)
                    }

                })
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pre = ContactPre(activity,contact_rec,this)
        setListener()
        if (pre!!.hasDat()){
            lazyFetchData()
        }
    }

    override fun goProfileActivity(t: User) {
        KstartActivity(activity,UserDetailActivity::class.java,"user_tag",t)
    }

    override fun showHint() {
        friendMsg_unread.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        if (isVisible){
            friendMsg_unread.visibility = View.GONE
        }
    }
}
