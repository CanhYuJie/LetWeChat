package com.yujie.letwechat.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.ViewGroup
import android.widget.RadioButton
import com.yujie.kotlinfulicenter.utils.convertDraw
import com.yujie.letwechat.R
import com.yujie.letwechat.view.fragment.ChatFragment
import com.yujie.letwechat.view.fragment.ContactFragment
import com.yujie.letwechat.view.fragment.DiscoverFragment
import com.yujie.letwechat.view.fragment.MeFragment
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

    private val itemclick = object : TitlePopup.OnItemOnClickListener{
        override fun onItemClick(item: ActionItem?, position: Int) {
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

}
