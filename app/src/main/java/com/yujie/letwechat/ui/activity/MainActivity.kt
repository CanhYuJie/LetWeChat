package com.yujie.letwechat.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError
import com.hyphenate.chat.EMChatManager
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.yujie.kotlinfulicenter.utils.convertDraw
import com.yujie.letwechat.App
import com.yujie.letwechat.R
import com.yujie.letwechat.utils.common_utils.showLongToastRes
import com.yujie.letwechat.ui.fragment.ChatFragment
import com.yujie.letwechat.ui.fragment.ContactFragment
import com.yujie.letwechat.ui.fragment.DiscoverFragment
import com.yujie.letwechat.ui.fragment.MeFragment
import com.yujie.letwechat.widget.ActionItem
import com.yujie.letwechat.widget.TitlePopup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG : String = MainActivity::class.java.simpleName
    var btnArr : Array<RadioButton> ?= null
    var titlePop : TitlePopup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnArr = arrayOf(main_weixin,main_contacts,main_discover,main_me)
        initDrawable()
        initChose()
        initTitlePop()
        initPopListener()
        initReceiver()
        Log.e(TAG,"onCreate ${App.initInstance().currentUser.toString()}")
    }

    private fun initReceiver() {
        EMClient.getInstance().addConnectionListener(KConnectionListener())
    }

    private fun initPopListener() {
        iv_add_fun.setOnClickListener {
            titlePop?.show(titlebar)
        }
    }

    private fun initTitlePop() {
        titlePop = TitlePopup(this, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        titlePop?.setItemOnClickListener(itemclick)
        titlePop?.addAction(ActionItem(this,R.string.menu_groupchat,
                            R.drawable.icon_menu_group))
        titlePop?.addAction(ActionItem(this,R.string.menu_addfriend,
                            R.drawable.icon_menu_addfriend))
        titlePop?.addAction(ActionItem(this,R.string.menu_qrcode,
                            R.drawable.icon_menu_sao))
        titlePop?.addAction(ActionItem(this,R.string.menu_money,
                            R.drawable.abv))
    }

    private val itemclick = TitlePopup.OnItemOnClickListener { item, position ->
        when(position){
            0       ->  {
                //TODO create group chat
            }
            1       ->  {
                //TODO add new friend
            }
            2       ->  {
                //TODO scan qrcode
            }
            3       ->  {
                //TODO get money
            }
        }
    }

    private fun initChose() {
        main_weixin.isChecked = true
        mainGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.main_weixin    ->  {
                    mainVP.currentItem = 0
                }
                R.id.main_contacts  ->  {
                    mainVP.currentItem = 1
                }
                R.id.main_discover  ->  {
                    mainVP.currentItem = 2
                }
                R.id.main_me        ->  {
                    mainVP.currentItem = 3
                }
            }
        }

        mainVP.adapter = object : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment? {
                when(position){
                    0   ->  {return ChatFragment()}
                    1   ->  {return ContactFragment()}
                    2   ->  {return DiscoverFragment()}
                    3   ->  {return MeFragment()}
                }
                return null
            }

            override fun getCount(): Int {
                return 4
            }
        }

        mainVP.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                btnArr?.get(position)?.isChecked = true
            }

        })
    }

    private fun initDrawable() {
        convertDraw(this,main_weixin,R.drawable.tab_weixin)
        convertDraw(this,main_contacts,R.drawable.tab_contact_list)
        convertDraw(this,main_discover,R.drawable.tab_find)
        convertDraw(this,main_me,R.drawable.tab_profile)
    }


    /**
     * HX server connect state listener
     */
    class KConnectionListener : EMConnectionListener {
        override fun onConnected() {
            // TODO do something here,like sync group info and user info
        }

        override fun onDisconnected(error: Int) {
            when (error) {
                EMError.USER_REMOVED -> {
                    showErrorToast(R.string.account_removed)
                }
                EMError.USER_LOGIN_ANOTHER_DEVICE -> {
                    showErrorToast(R.string.user_login_another_device)
                }
                EMError.SERVER_SERVICE_RESTRICTED -> {
                    showErrorToast(R.string.user_forbidden)
                }
            }
        }

        private fun showErrorToast(stringId: Int) {
            showLongToastRes(App.initInstance().getLastActivity().applicationContext,stringId)
        }

    }

    private class NewMsgReceiver() : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            val from = intent.getStringExtra("from")
			// 消息id
			val msgId = intent.getStringExtra("msgid")
            val message = EMClient.getInstance().chatManager().getMessage(msgId)
            // TODO if message.getTo() same as chatactivity,return
            // TODO refresh message count here ,and if current fragment
            // TODO is chatFragment,refresh fragment
        }
    }

    fun updateUnreadLabel(): Unit {
        var count = 0
        count = EMClient.getInstance().chatManager().unreadMsgsCount
        if (count > 0){
            main_unread_msg.text = count.toString()
            main_unread_msg.visibility = View.VISIBLE
        }else{
            main_unread_msg.visibility = View.GONE
        }
    }

}
