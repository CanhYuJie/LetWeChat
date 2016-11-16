package com.yujie.letwechat.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.yujie.letwechat.R

/**
 * A simple [Fragment] subclass.
 */
open class BaseFragment() : Fragment() {
    var isViewPrepare = false
    var hasFetchData  = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val textView = TextView(activity)
        textView.setText(R.string.hello_blank_fragment)
        return textView
    }

    open protected fun lazyFetchData(){
        Log.e("lazy","------>lazyFetchData")
    }

    private fun lazyFetchDataPrepare(){
    if (userVisibleHint && !hasFetchData && !isViewPrepare){
            hasFetchData = true
            lazyFetchData()
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isVisible){
            lazyFetchDataPrepare()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            lazyFetchDataPrepare()
        }
    }
}
