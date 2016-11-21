package com.yujie.letwechat.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yujie.kotlinfulicenter.model.bean.User

import com.yujie.letwechat.R
import com.yujie.letwechat.event.ContactAgreed
import com.yujie.letwechat.event.ContactDeleted
import com.yujie.letwechat.event.FriendInvite
import com.yujie.letwechat.presenter.ContactPre
import com.yujie.letwechat.ui.activity.FriendMsgActivity
import com.yujie.letwechat.utils.common_utils.KstartActivity
import com.yujie.letwechat.ui.activity.UserDetailActivity
import com.yujie.letwechat.ui.iview.IContactView
import com.yujie.letwechat.utils.common_utils.showLongToast
import kotlinx.android.synthetic.main.fragment_contact.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ContactFragment :BaseFragment(),IContactView {
    var pre:ContactPre? = null
    val TAG : String = ContactFragment::class.java.simpleName
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_contact, container, false)
        EventBus.getDefault().register(this)
        return view
    }


    override fun lazyFetchData() {
        super.lazyFetchData()
        pre?.getContacts()
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun friendInvite(event: FriendInvite){
        pre?.addFriendMsg(event.name,event.reason)
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun contactAgreed(event: ContactAgreed){
        pre?.getUserInfo(event.name)
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun contactDeleted(event:ContactDeleted){
        showLongToast(activity,event.nick+"已和你解除好友关系")
        pre?.contactDeleted(event.nick)
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

    override fun delSuccess() {
        showLongToast(activity,"该好友已删除")
    }

    override fun delFailed(s: String) {
        showLongToast(activity,s)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
