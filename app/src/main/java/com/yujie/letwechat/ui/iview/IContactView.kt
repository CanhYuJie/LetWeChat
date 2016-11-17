package com.yujie.letwechat.ui.iview

import com.yujie.kotlinfulicenter.model.bean.User

/**
 * Created by yujie on 16-11-2.
 */
interface IContactView {
    fun goProfileActivity(t: User)
    fun showHint()
}