package com.yujie.letwechat.presenter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yujie.kotlinfulicenter.model.bean.User
import com.yujie.letwechat.App
import com.yujie.letwechat.I
import com.yujie.letwechat.R
import com.yujie.letwechat.api.ApiFactory
import com.yujie.letwechat.db.DBHelper
import com.yujie.letwechat.utils.common_utils.showLongToastRes
import com.yujie.letwechat.utils.common_utils.showShortToast
import com.yujie.letwechat.utils.common_utils.showShortToastRes
import com.yujie.letwechat.ui.iview.IProfileView
import com.yujie.letwechat.widget.ActionItem
import com.yujie.letwechat.widget.TitlePopup
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by yujie on 16-11-14.
 */
class ProfilePre(val view:IProfileView,val context: Context) {
    val api = ApiFactory.getNetApiInstance()
    var titlePop : TitlePopup? = null
    fun getClassData(id: String) {
        val map = HashMap<String,String>()
        map.put("request","getClassById")
        map.put("id",id)
        api!!.getProfile(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    Log.e("getDepartmentData","getDepartmentData $result ")
                    view.showClass(result.msg?:"")
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
                    view.showDepartment(result.msg?:"")
                },{ showLongToastRes(context, R.string.def_empty_data_text)})
    }

    fun getBedroomData(id: String) {
        val map = HashMap<String,String>()
        map.put("request","getBedroomById")
        map.put("id",id)
        api!!.getProfile(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result -> view.showBedRoom(result.toString())},{ showLongToastRes(context, R.string.def_empty_data_text)})
    }

    fun getDataList(viewId: Int,widget: View){
        titlePop = TitlePopup(context, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        val click = TitlePopup.OnItemOnClickListener{item,position  ->
            view.showSelectData(viewId,item.mTitle.toString())
        }
        titlePop?.setItemOnClickListener(click)
        when(viewId){
            R.id.tv_b_class             ->{getAllClassData(widget)}
            R.id.tv_sex                 ->{getAllSexDta(widget)}
            R.id.tv_bedroom             ->{getAllBedroomData(widget)}
            R.id.tv_department          ->{getAllDepartmentData(widget)}
        }
    }



    private fun getAllDepartmentData(widget: View) {
        api!!.getAllDepartment(I.REQUEST_GET_ALL_DEPARTMENT,I.CLIENT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    result.forEach { titlePop?.addAction(ActionItem(context,it.department)) }
                    showPop(widget)
                }, {e -> showShortToastRes(context,R.string.def_empty_data_text) })
    }

    private fun showPop(widget: View) {
        titlePop?.show(widget)
    }

    private fun getAllBedroomData(widget: View) {
        api!!.getAllBedroom(I.REQUEST_GET_ALL_BEDROOM,I.CLIENT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    result.forEach { titlePop?.addAction(ActionItem(context,it.intro)) }
                    showPop(widget)
                }, {e -> showShortToastRes(context,R.string.def_empty_data_text) })
    }

    private fun getAllSexDta(widget: View) {
        titlePop?.addAction(ActionItem(context,"男"))
        titlePop?.addAction(ActionItem(context,"女"))
        showPop(widget)
    }

    private fun getAllClassData(widget: View) {
        api!!.getAllClass(I.REQUEST_GET_ALL_CLASS,I.CLIENT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    result.forEach { titlePop?.addAction(ActionItem(context,it.className)) }
                    showPop(widget)
                }, {e -> showShortToastRes(context,R.string.def_empty_data_text)
                        Log.e("Exception","getAllClassData $e\n"+e.message)})
    }

    fun updateUser(name: String, uid: String, user_nick: String, department: String, b_class: String, bedroom: String, sex: String) {
        val map = HashMap<String,String>()
        map.put(I.REQUEST_KEY,I.REQUEST_UPDATE_USER)
        map.put(I.Student.NAME,name)
        map.put(I.Student.UID,uid)
        map.put(I.Student.USER_NICK,user_nick)
        map.put(I.Student.BDEPARTMENT,department)
        map.put(I.Student.BCLASS,b_class)
        map.put(I.Student.BBEDROOM,bedroom)
        map.put(I.Student.SEX,sex)
        map.put(I.Student.MARK, App.initInstance().currentUser?.uid!!)
        map.put(I.OPT_USER,I.CLIENT)
        api!!.updateUser(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    if (result!=null && result.flag){
                        val user = User(name, uid, sex, b_class, department, bedroom, user_nick, "", "")
                        if (DBHelper(context).updateUser(user,1)){
                            App.initInstance().currentUser = user
                        }
                        view.updateSuccess()
                    }else{
                        view.updateFailed("数据修改失败")
                    }
                },{e -> view.updateFailed(e.message as String)})
    }
}