package com.yujie.letwechat.presenter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import com.bumptech.glide.Glide
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.yujie.kotlinfulicenter.model.bean.FriendMsg
import com.yujie.letwechat.App
import com.yujie.letwechat.I
import com.yujie.letwechat.R
import com.yujie.letwechat.api.ApiFactory
import com.yujie.letwechat.db.DBHelper
import com.yujie.letwechat.ui.iview.IFriendMsg
import com.yujie.letwechat.utils.common_utils.showLongToast
import com.yujie.letwechat.utils.common_utils.showLongToastRes
import com.yujie.letwechat.utils.common_utils.showShortToast
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import kotlin.concurrent.thread

/**
 * Created by yujie on 16-11-17.
 */
class FriendPre(val context: Context,
                val view: IFriendMsg,
                val rec: RecyclerView,
                val list:ArrayList<FriendMsg>) {
    val TAG : String = FriendPre::class.java.simpleName
    val api = ApiFactory.getNetApiInstance()
    var adapter : CommonAdapter<FriendMsg>? = null
    var hasSearched = false
    init {
        rec.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter = object : CommonAdapter<FriendMsg>(context, R.layout.friend_msg_layout,list){
            override fun convert(holder: ViewHolder, t: FriendMsg, position: Int) {
                if (hasSearched){
                    holder.setText(R.id.nick,t.name)
                    holder.setText(R.id.msg,"这个人很懒，什么都没留下")
                    Glide.with(context).load(I.AVATAR_SERVER_ROOT+t.user_nick+ I.JPGFORMAT)
                            .placeholder(R.drawable.ease_default_avatar)
                            .error(R.drawable.ease_default_avatar)
                            .into(holder.getView(R.id.avatar))
                    holder.getView<Button>(R.id.refuse_btn).visibility = View.GONE
                    holder.getView<Button>(R.id.add_btn).setOnClickListener {
                        thread {
                            try {
                                Log.e(TAG,"convert "+t.user_nick)
                                EMClient.getInstance().contactManager().addContact(t.user_nick,"加个好友呗")
                            }catch (e:HyphenateException){
                                Log.e(TAG,"convert "+e.errorCode)
                            }
                        }
                    }
                }else{
                    holder.setText(R.id.nick,t.name)
                    holder.setText(R.id.msg,t.msg)
                    Glide.with(context).load(I.AVATAR_SERVER_ROOT+t.user_nick+ I.JPGFORMAT)
                            .placeholder(R.drawable.ease_default_avatar)
                            .error(R.drawable.ease_default_avatar)
                            .into(holder.getView(R.id.avatar))
                    holder.setText(R.id.add_btn,"同意")
                    holder.getView<Button>(R.id.refuse_btn).visibility = View.VISIBLE
                    holder.getView<Button>(R.id.refuse_btn).setOnClickListener {
                        refuse(t,position)
                        thread {
                            try {
                                EMClient.getInstance().contactManager().declineInvitation(t.user_nick)
                            }catch (e:HyphenateException){
                                Log.e(TAG,"convert "+e)
                            }
                        }
                    }
                    holder.getView<Button>(R.id.add_btn).setOnClickListener {
                        addContact(t,position)
                        thread {
                            try {
                                EMClient.getInstance().contactManager().acceptInvitation(t.user_nick)
                            }catch (e:HyphenateException){
                                Log.e(TAG,"convert "+e)
                            }
                        }

                    }

                }
            }
        }
        rec.adapter = adapter
    }

    private fun addContact(t: FriendMsg, position: Int) {
        api!!.addContact(App.initInstance().currentUser!!.uid,
                t.uid!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null && it.flag){
                        if (DBHelper(context).delFriendMsg(t.user_nick)){
                            view.addSuccess()
                            list.remove(t)
                            adapter?.notifyItemRemoved(position)
                            if (list.size == 0){
                                view.showHint()
                            }
                        }else{
                            showShortToast(context,"无法正确清除验证信息")
                        }
                    }else{
                        view.addFailed("添加好友失败")
                    }
                },{ showLongToast(context,it.toString())})
    }

    fun search(nick: String) {
        api!!.getUsersByNick(nick)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.hideHint()
                    list.clear()
                    it.forEach {
                        if (!it.user_nick.equals(App.initInstance().currentUser!!.user_nick)){
                            val msg = FriendMsg(it.user_nick!!,it.name,null,it.uid,false)
                            list.add(msg)
                        }
                    }
                    hasSearched = true
                    adapter?.notifyDataSetChanged()
                },{ showShortToast(context,it.toString())})
    }

    fun refuse(t:FriendMsg,position: Int) {
        DBHelper(context).delFriendMsg(t.user_nick)
        list.remove(t)
        adapter?.notifyItemRemoved(position)
    }
}