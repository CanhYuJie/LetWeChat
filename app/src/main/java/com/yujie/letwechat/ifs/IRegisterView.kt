package com.yujie.letwechat.ifs

/**
 * Created by yujie on 16-11-1.
 */
interface IRegisterView {
    fun registerSuccess(id : String)
    fun registerFailed(msg : String)
}