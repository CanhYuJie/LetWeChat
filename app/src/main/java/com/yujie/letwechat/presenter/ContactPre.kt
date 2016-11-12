package com.yujie.letwechat.presenter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.hyphenate.chat.EMClient.TAG
import com.yujie.kotlinfulicenter.model.bean.User
import com.yujie.letwechat.App
import com.yujie.letwechat.I
import com.yujie.letwechat.R
import com.yujie.letwechat.api.ApiFactory
import com.yujie.letwechat.utils.common_utils.showLongToastRes
import com.yujie.letwechat.view.iview.IContactView
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by yujie on 16-11-12.
 */
class ContactPre(val context: Context,
                 val rec: RecyclerView,
                 val view:IContactView
                 ) {
    var data = ArrayList<User>()
    val getContacts = ApiFactory.getNetApiInstance()
    var adapter: CommonAdapter<User>? = null
    fun getContacts(): Unit {
        val currentUser = App.initInstance().currentUser
        if (currentUser != null) {
            getContacts!!.getContacts(currentUser!!.uid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({result -> loadData(result)},{e -> showLongToastRes(context, R.string.def_empty_data_text)})
        }
    }

    private fun loadData(result: ArrayList<User>) {
        data = result
        initAdapter()
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
                Log.e(TAG,"convert "+I.AVATAR_SERVER_ROOT+t.user_nick+I.JPGFORMAT)
                Glide.with(context).load(I.AVATAR_SERVER_ROOT+t.user_nick+I.JPGFORMAT)
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_image)
                        .into(holder.getView(R.id.contact_img))
                holder.getView<LinearLayout>(R.id.contact_item_root).setOnClickListener {
                    view.goProfileActivity(t)
                }
            }

        }
        rec.adapter = adapter
    }

    fun hasDat(): Boolean {
        return data.size == 0
    }
}