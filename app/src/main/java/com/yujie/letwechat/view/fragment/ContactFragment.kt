package com.yujie.letwechat.view.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yujie.kotlinfulicenter.model.bean.User

import com.yujie.letwechat.R
import com.yujie.letwechat.presenter.ContactPre
import com.yujie.letwechat.view.iview.IContactView
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
            //TODO go message activity to add new friend
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
    }
}
