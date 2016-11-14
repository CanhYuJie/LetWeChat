package com.yujie.letwechat.presenter

import android.content.Context
import android.util.Log
import com.yujie.letwechat.R
import com.yujie.letwechat.api.ApiFactory
import com.yujie.letwechat.utils.common_utils.showLongToastRes
import com.yujie.letwechat.view.iview.IProfile
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by yujie on 16-11-14.
 */
class ProfilePre(val view:IProfile,val context: Context) {
    val api = ApiFactory.getNetApiInstance()

    fun getClassData(id: String) {
        val map = HashMap<String,String>()
        map.put("request","getClassById")
        map.put("id",id)
        api!!.getProfile(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    Log.e("getDepartmentData","getDepartmentData $result ")
                    view.setClass(result.msg?:"")
                },{ showLongToastRes(context, R.string.def_empty_data_text)})
    }

    fun getDepartmentData(id: String) {
        val map = HashMap<String,String>()
        map.put("request","getDepartmentById")
        map.put("id",id)
        api!!.getProfile(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    Log.e("getDepartmentData","getDepartmentData $result ")
                    view.setDepartment(result.msg?:"")
                },{ showLongToastRes(context, R.string.def_empty_data_text)})
    }

    fun getBedroomData(id: String) {
//        val map = HashMap<String,String>()
//        map.put("request","getBedroomById")
//        map.put("id",id)
//        api!!.getProfile(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({result -> view.setClass(result?:"")},{ showLongToastRes(context, R.string.def_empty_data_text)})
    }
}