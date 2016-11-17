package com.yujie.letwechat.ui.iview

/**
 * Created by yujie on 16-11-17.
 */
interface IFriendMsg {
    fun addSuccess()
    fun addFailed(msg:String)
    fun hideHint()
    fun showHint()
}