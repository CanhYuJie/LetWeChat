package com.yujie.kotlinfulicenter.model.bean

import java.io.Serializable
import java.util.*

/**
 * Created by yujie on 16-10-22.
 */

data class Result (val flag : Boolean,val msg : String?) : Serializable

data class User(val name: String,
              val uid: String,
              val sex: String?,
              val b_class: String?,
              val b_department: String?,
              val b_bedroom: String?,
              val user_nick: String?,
              val blackList: String?,
              val password: String?) : Serializable
data class ResultData(val flag: Boolean,val msg: String?,val data:User?): Serializable