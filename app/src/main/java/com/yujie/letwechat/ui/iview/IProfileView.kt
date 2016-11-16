package com.yujie.letwechat.ui.iview

/**
 * Created by yujie on 16-11-15.
 */
interface IProfileView {
    fun showClass(className: String)
    fun showDepartment(department: String)
    fun showBedRoom(bedroom: String)
    fun showSex(sex: String)
    fun showSelectData(flagId:Int,data:String)
    fun updateSuccess()
    fun updateFailed(msg:String)
}