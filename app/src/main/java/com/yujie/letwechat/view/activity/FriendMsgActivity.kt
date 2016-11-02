package com.yujie.letwechat.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yujie.letwechat.R
import com.yujie.letwechat.ifs.IMsgView

class FriendMsgActivity : AppCompatActivity(),IMsgView {
    val TAG : String = FriendMsgActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_msg)
    }
}
