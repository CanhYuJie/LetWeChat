package com.yujie.letwechat.presenter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMClient.TAG
import com.yujie.kotlinfulicenter.model.bean.FriendMsg
import com.yujie.kotlinfulicenter.model.bean.User
import com.yujie.letwechat.App
import com.yujie.letwechat.I
import com.yujie.letwechat.R
import com.yujie.letwechat.api.ApiFactory
import com.yujie.letwechat.db.DBHelper
import com.yujie.letwechat.utils.common_utils.showLongToastRes
import com.yujie.letwechat.ui.iview.IContactView
import com.yujie.letwechat.utils.common_utils.showLongToast
import com.yujie.letwechat.utils.common_utils.showShortToast
import com.yujie.letwechat.widget.ActionItem
import com.yujie.letwechat.widget.TitlePopup
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import kotlin.concurrent.thread

/**
 * Created by yujie on 16-11-12.
 */
class ContactPre(val context: Context,
                 val rec: RecyclerView,
                 val view:IContactView
                 ) {
    var data = ArrayList<User>()
    val api = ApiFactory.getNetApiInstance()
    var adapter: CommonAdapter<User>? = null
    var titlePop : TitlePopup? = null
    var deletePosition = -1
    fun getContacts(): Unit {
        val currentUser = App.initInstance().currentUser
        if (currentUser != null) {
            api!!.getContacts(currentUser!!.uid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({result -> loadData(result)},{e -> showLongToastRes(context, R.string.def_empty_data_text)})
        }
    }

    private fun loadData(result: ArrayList<User>) {
        data = result
        initAdapter()
    }

    fun getUserInfo(user_nick: String) {
        api!!.getUserByNick(user_nick)
                .map { it.data }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({addContact(it!!)},{ showShortToast(context,it.toString())})
    }

    private fun addContact(user: User) {
        if (!data.contains(user)){
            showLongToast(context,user.name+"已经成为了你的好友")
            data.add(user)
            adapter?.notifyItemInserted(adapter?.itemCount!!)
        }else{
            showLongToast(context,"已是好友,不能重复添加")
        }
    }

    private fun initAdapter() {
        rec.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter =object: CommonAdapter<User>(context,R.layout.contact_layout,data){
            override fun convert(holder: ViewHolder, t: User, position: Int) {
                if (t.name!=null){
                    holder.setText(R.id.contact_name,t.name)
                }else{
                    holder.setText(R.id.contact_name,t.user_nick)
                }
                Glide.with(context).load(I.AVATAR_SERVER_ROOT+t.user_nick+I.JPGFORMAT)
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_image)
                        .into(holder.getView(R.id.contact_img))
                holder.getView<LinearLayout>(R.id.contact_item_root).setOnClickListener {
                    view.goProfileActivity(t)
                }
                holder.getView<LinearLayout>(R.id.contact_item_root).setOnLongClickListener({
                    titlePop = TitlePopup(context, ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
                    titlePop?.setItemOnClickListener(itemclick)
                    titlePop?.addAction(ActionItem(context,"删除该好友"))
                    titlePop?.addAction(ActionItem(context,"取消"))
                    titlePop?.show(holder.getView<LinearLayout>(R.id.contact_item_root))
                    deletePosition = position
                    true
                })
            }

        }
        rec.adapter = adapter
    }

    private val itemclick = TitlePopup.OnItemOnClickListener { item, position ->
        when(position){
            0       ->  {
                deleteContact(deletePosition)
            }
            1       ->  {
                titlePop?.dismiss()
            }
        }
    }

    private fun deleteContact(deletePosition: Int) {
        Log.e(TAG,"deleteContact "+App.initInstance().currentUser!!.uid
                +"\n"+data[deletePosition].uid)
        api!!.delContact(App.initInstance().currentUser!!.uid,
                data[deletePosition].uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e(TAG,"deleteContact $it")
                    if (it!=null && it.flag){
                        delNotify(data[deletePosition])
                    }else{
                        view.delFailed("删除失败")
                    }
                },{showShortToast(context,it.toString())})
    }

    private fun delNotify(user: User) {
        thread {
            try{
                EMClient.getInstance().contactManager().deleteContact(user.user_nick)
                data.remove(user)
                adapter?.notifyItemRemoved(deletePosition)
                deletePosition = -1
            }catch(e: Exception){
                Log.e(TAG,"delNotify 删除失败了"+e.message)
            }
        }
    }

    fun contactDeleted(user_nick: String){
        for (i in data){
            if (i.user_nick.equals(user_nick)){
                data.remove(i)
                break
            }
        }
        adapter?.notifyDataSetChanged()
    }

    fun hasDat(): Boolean {
        return data.size == 0
    }

    fun addFriendMsg(user_name: String,msg:String?) {
        api!!.getUserByNick(user_name)
                .map { it.data }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e(TAG,"addFriendMsg $it")
                    val msg = FriendMsg(user_name,it?.name,msg,it?.uid,false)
                    DBHelper(context).addFriendMsg(msg)
                    view.showHint()
                },{ showShortToast(context,it.toString())})
    }

}