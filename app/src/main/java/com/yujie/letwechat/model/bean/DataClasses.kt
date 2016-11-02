package com.yujie.kotlinfulicenter.model.bean

import java.io.Serializable
import java.util.*

/**
 * Created by yujie on 16-10-22.
 */

data class Result (val retCode : Int,val retMsg : Boolean,val retData : Any?) : Serializable{

}
// userinfo / contactinfo(PageData)
data class RetDataBean(val muserName     : String,
                       val muserNick     : String?,
                       val mavatarId     : Int?,
                       val mavatarPath   : String?,
                       val mavatarSuffix : String?,
                       val mavatarType   : Int?,
                       val mavatarLastUpdateTime : String?) : Serializable
data class ContactBean(val currentPage  : Int,
                       val maxRecord    : Int,
                       val pageData     : Any?) : Serializable
