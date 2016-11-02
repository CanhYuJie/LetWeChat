package com.yujie.letwechat.presenter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.yujie.kotlinfulicenter.model.bean.ContactBean
import com.yujie.kotlinfulicenter.model.bean.Result
import com.yujie.kotlinfulicenter.model.bean.RetDataBean
import com.yujie.letwechat.App
import com.yujie.letwechat.I
import com.yujie.letwechat.R
import com.yujie.letwechat.ifs.IContactView
import com.yujie.letwechat.utils.common_utils.KstartActivity
import com.yujie.letwechat.utils.net_utils.OkHttpUtils
import com.yujie.letwechat.view.activity.ProfileActivity
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import java.util.*

/**
 * Created by yujie on 16-11-2.
 */
class ContactPre(val context: Context,
                 val view:IContactView,
                 val rec:RecyclerView) {
    val TAG : String = ContactPre::class.java.simpleName
    var contacts = ArrayList<RetDataBean>()
    var currentPageId = 1
    val defaultPageSize = 10
    var adapter : CommonAdapter<RetDataBean>? = null
    init {
        initDefData()
    }

    fun initDefData(): Unit {
        val newFriendLabel = RetDataBean("新的朋友",null, R.drawable.icon_addfriend,null,null,null,null)
        val groupChatLabel = RetDataBean("群聊",null, R.drawable.icon_qunliao,null,null,null,null)
        val publicGroupLabel = RetDataBean("公众号",null, R.drawable.icon_public,null,null,null,null)
        contacts.add(0,newFriendLabel)
        contacts.add(1,groupChatLabel)
        contacts.add(2,publicGroupLabel)
    }
    /**
     * get contact info from local server (page)
     */
    fun initData(): Unit {
        val utils = OkHttpUtils<Result>(context)
            utils.setRequestUrl(I.REQUEST_DOWNLOAD_CONTACT_PAGE_LIST)
                 .addParams(I.Contact.USER_NAME,App.initInstance().currentUser!!.muserName)
                 .addParams(I.PAGE_ID,currentPageId.toString())
                 .addParams(I.PAGE_SIZE,defaultPageSize.toString())
                 .targetClass(Result::class.java)
                 .execute(object : OkHttpUtils.OnCompleteListener<Result>{
                     override fun onSuccess(result: Result) {
                         if (result != null && result.retMsg) {
                             val contactBean = Gson().fromJson(result.retData.toString(), ContactBean::class.java)
                             if (contactBean != null) {
                                 if (contactBean.maxRecord > 0){
                                     val dataBean = Gson().fromJson(contactBean.pageData.toString(), RetDataBean::class.java)
                                     if (dataBean != null) {
                                         // TODO init adapter or refresh data
                                     }
                                 }else{
                                     //TODO no more data
                                 }
                             }
                         }
                     }

                     override fun onError(msg: String) {

                     }
                 })
    }

    /**
     * get contact info from local server (all)
     */
    fun initData2(): Unit {
        val utils = OkHttpUtils<Result>(context)
        utils.setRequestUrl(I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST)
                .addParams(I.Contact.USER_NAME,App.initInstance().currentUser!!.muserName)
                .targetClass(Result::class.java)
                .execute(object : OkHttpUtils.OnCompleteListener<Result>{
                    override fun onSuccess(result: Result) {
                        if (result != null && result.retMsg) {
                            val contactsBean = Gson().fromJson(result.retData.toString(),Array<RetDataBean>::class.java)
                            contacts.addAll(contactsBean)
                            initAdapter()
                        }
                    }

                    override fun onError(msg: String) {

                    }
                })
    }

    fun initAdapter(): Unit {
        rec.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter = object : CommonAdapter<RetDataBean>(context,R.layout.contact_layout,contacts){
            override fun convert(holder: ViewHolder, t: RetDataBean, position: Int) {
                if (position < 3){
                    holder.setText(R.id.contact_name,t.muserName)
                    if (t.mavatarId != null) {
                        holder.setBackgroundRes(R.id.contact_img,t.mavatarId!!)
                    }else{
                        holder.setBackgroundRes(R.id.contact_img,R.drawable.default_image)
                    }
                }else{
                    holder.setText(R.id.contact_name,t.muserName)
                    val imgPath = I.SERVER_ROOT+I.REQUEST_DOWNLOAD_AVATAR+"?"+
                                    I.NAME_OR_HXID+"="+t.muserName+"&"+
                                    I.AVATAR_TYPE+"="+I.AVATAR_TYPE_USER_PATH+"&"+
                                    I.Avatar.AVATAR_SUFFIX+"="+I.AVATAR_SUFFIX_PNG+"&"+
                                    I.AVATAR_WIDTH+"="+I.AVATAR_WIDTH_DEFAULT+"&"+
                                    I.AVATAR_HEIGHT+"="+I.AVATAR_HEIGHT_DEFAULT
                    Log.e(TAG,"convert $imgPath")
                    Glide.with(context).load(imgPath)
                                       .error(R.drawable.default_image)
                                       .placeholder(R.drawable.default_image)
                                       .diskCacheStrategy(DiskCacheStrategy.ALL)
                                       .into(holder.getView(R.id.contact_img))
                }
                when(position){
                    0       ->  {
                        holder.getView<LinearLayout>(R.id.contact_item_root).setOnClickListener {
                            //TODO go addfriend activity
                        }
                    }
                    1       ->  {
                        holder.getView<LinearLayout>(R.id.contact_item_root).setOnClickListener {
                            //TODO go group activity
                        }
                    }
                    2       ->  {
                        holder.getView<LinearLayout>(R.id.contact_item_root).setOnClickListener {
                            //TODO go public group activity
                        }
                    }
                    else    ->  {
                        holder.getView<LinearLayout>(R.id.contact_item_root).setOnClickListener {
                            //TODO go userInfo activity
                            view.goProfileActivity(t)
                        }
                    }
                }

            }

        }
        rec.adapter = adapter
    }
}