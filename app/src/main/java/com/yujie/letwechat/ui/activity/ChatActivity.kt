package com.yujie.letwechat.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMImageMessageBody
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMMessageBody
import com.hyphenate.easeui.ui.EaseChatFragment
import com.hyphenate.easeui.ui.EaseShowBigImageActivity
import com.hyphenate.easeui.widget.chatrow.EaseChatRow
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider
import com.yujie.kotlinfulicenter.model.bean.ResultData
import com.yujie.kotlinfulicenter.model.bean.User
import com.yujie.letwechat.R
import com.yujie.letwechat.api.ApiFactory
import com.yujie.letwechat.utils.common_utils.KstartActivity
import com.yujie.letwechat.utils.common_utils.showShortToastRes
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File

class ChatActivity : AppCompatActivity() {
    val TAG : String = ChatActivity::class.java.simpleName
    var context:Activity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        context = this
        val chatLayout = EaseChatFragment()
        setListener(chatLayout)
        chatLayout.arguments = intent.extras
        supportFragmentManager
                .beginTransaction()
                .add(R.id.chat_content_layout,chatLayout)
                .commit()
    }

    private fun setListener(chatLayout: EaseChatFragment) {
        val user = intent.getSerializableExtra("user_tag") as User
        chatLayout.setChatFragmentListener(object : EaseChatFragment.EaseChatFragmentHelper{
            override fun onSetMessageAttributes(message: EMMessage?) {

            }

            override fun onEnterToChatDetails() {

            }

            override fun onAvatarLongClick(username: String) {

            }

            override fun onMessageBubbleClick(message: EMMessage): Boolean {
                return false
            }

            override fun onMessageBubbleLongClick(message: EMMessage?) {

            }

            override fun onExtendMenuItemClick(itemId: Int, view: View?): Boolean {
                return false
            }

            override fun onSetCustomChatRowProvider(): EaseCustomChatRowProvider? {
                return null
            }

            override fun onAvatarClick(username: String) {
                val service = ApiFactory.getNetApiInstance()
                service!!.getUserByNick(username)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({result -> goDetail(result)},{e -> Log.e(TAG,"onAvatarClick "+e)})
            }

        })

    }

    private fun goDetail(result: ResultData) {
        KstartActivity(context!!,UserDetailActivity::class.java,"user_tag", result.data!!)
    }
}
