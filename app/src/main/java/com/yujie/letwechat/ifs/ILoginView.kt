package com.yujie.letwechat.ifs

/**
 * Created by yujie on 16-11-1.
 */
interface ILoginView {
    fun loginSuccess()
    fun loginFailed(msg: String)
}