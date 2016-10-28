package com.yujie.kotlinfulicenter.utils

import android.content.Context
import android.widget.EditText
import java.util.*

/**
 * Created by yujie on 16-10-21.
 */
    /*
        case array to list
     */
    fun <T>array2List(array: Array<T>): ArrayList<T>?{
        var list : ArrayList<T> = ArrayList()
        if (array.size != 0) {
            for (i in 0..array.size-1){
                list.add(array[i])
            }
            return list
        }
        return null
    }
    /*
    case px to dp
     */
    fun px2dp(context: Context, px: Int): Int? {
        val density = context.resources.displayMetrics.density.toInt()
        return px/density
    }
    /*
    if the widget's text is empty,return false else true
     */
    fun ifEmpty(text: String, widget: EditText): Boolean {
        if (text.isEmpty()){
            widget.error = "不能为空"
            widget.requestFocus()
            return false
        }
        return true
    }

