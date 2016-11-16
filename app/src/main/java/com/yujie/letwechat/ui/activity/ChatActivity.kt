package com.yujie.letwechat.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hyphenate.easeui.ui.EaseChatFragment
import com.yujie.letwechat.R

class ChatActivity : AppCompatActivity() {
    val TAG : String = ChatActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val chatLayout = EaseChatFragment()
        chatLayout.arguments = intent.extras
        supportFragmentManager
                .beginTransaction()
                .add(R.id.chat_content_layout,chatLayout)
                .commit()
    }
}
