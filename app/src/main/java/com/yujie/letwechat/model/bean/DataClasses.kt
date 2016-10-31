package com.yujie.kotlinfulicenter.model.bean

import java.io.Serializable
import java.util.*

/**
 * Created by yujie on 16-10-22.
 */

data class Result (val retCode : Int,val retMsg : Boolean,val retData : RetDataBean?) : Serializable

data class RetDataBean(val muserName     : String,
                       val muserNick     : String,
                       val mavatarId     : Int,
                       val mavatarPath   : String,
                       val mavatarSuffix : String,
                       val mavatarType   : Int,
                       val mavatarLastUpdateTime : String) : Serializable