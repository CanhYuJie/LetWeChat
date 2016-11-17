package com.yujie.letwechat.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.yujie.kotlinfulicenter.model.bean.FriendMsg
import com.yujie.letwechat.R
import com.yujie.letwechat.db.DBHelper
import com.yujie.letwechat.presenter.FriendPre
import com.yujie.letwechat.ui.iview.IFriendMsg
import com.yujie.letwechat.utils.common_utils.invalidEmpty
import com.yujie.letwechat.utils.common_utils.showLongToast
import com.yujie.letwechat.utils.common_utils.showLongToastRes
import kotlinx.android.synthetic.main.activity_friend_msg.*
import java.util.*

class FriendMsgActivity : AppCompatActivity(),IFriendMsg {
    val TAG : String = FriendMsgActivity::class.java.simpleName
    var list = ArrayList<FriendMsg>()
    var pre: FriendPre? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_msg)
        setData()
        setListener()
    }

    private fun setListener() {
        search_btn.setOnClickListener {
            if (invalidEmpty(et_search))
                return@setOnClickListener
            val nick = et_search.text.toString()
            pre?.search(nick)
        }
    }

    private fun setData() {
        list = DBHelper(this).getMsg()
        if (list.size == 0){
            txt_nomsg.visibility = View.VISIBLE
        }
        pre = FriendPre(this,this,msg_rec,list)
    }

    override fun addSuccess() {
        showLongToastRes(this,R.string.addcontact_be_friented)
    }

    override fun addFailed(msg: String) {
        showLongToast(this,msg)
    }

    override fun hideHint() {
        txt_nomsg.visibility = View.GONE
    }

    override fun showHint() {
        txt_nomsg.visibility = View.VISIBLE
    }
}
