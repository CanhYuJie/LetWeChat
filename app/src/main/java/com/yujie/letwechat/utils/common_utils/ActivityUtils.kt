package com.yujie.kotlinfulicenter.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.io.Serializable

/**
 * Created by yujie on 16-10-21.
 */
    fun <T>goNextActivity(context : Context, nextActivity : Class<T>,dataClass : Serializable? ,goKey : String?){
        val intent = Intent(context,nextActivity)
        if (dataClass != null) {
            val bundle = Bundle()
            bundle.putSerializable(goKey,dataClass)
            intent.putExtras(bundle)
        }
        context.startActivity(intent)
    }

    fun ActFinish(context : AppCompatActivity){
        context.finish()
    }