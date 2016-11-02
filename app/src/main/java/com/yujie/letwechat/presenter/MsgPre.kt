package com.yujie.letwechat.presenter

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.yujie.kotlinfulicenter.model.bean.Result
import com.yujie.letwechat.I
import com.yujie.letwechat.ifs.IMsgView
import com.yujie.letwechat.utils.net_utils.OkHttpUtils
import java.util.*

/**
 * Created by yujie on 16-11-2.
 */
class MsgPre(val context: Context,
             val recyclerView: RecyclerView,
             val view: IMsgView) {
    val TAG : String = MsgPre::class.java.simpleName
    var defPageId = 1
    val defPageSize = 10
    var datalist : ArrayList<Any>? = null

    fun searchUserByNick(nick: String): Unit {
        val util = OkHttpUtils<Result>(context)
        util.setRequestUrl(I.REQUEST_FIND_USERS_FOR_SEARCH)
            .addParams(I.User.NICK,nick)
            .addParams(I.PAGE_ID,defPageId.toString())
            .addParams(I.PAGE_SIZE,defPageSize.toString())
            .targetClass(Result::class.java)
            .execute(object : OkHttpUtils.OnCompleteListener<Result>{
                override fun onSuccess(result: Result) {

                }

                override fun onError(msg: String) {

                }
            })
    }
}