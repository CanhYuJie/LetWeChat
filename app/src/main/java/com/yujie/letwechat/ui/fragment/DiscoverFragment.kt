package com.yujie.letwechat.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yujie.letwechat.R

/**
 * A simple [Fragment] subclass.
 */
class DiscoverFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_discover, container, false)
    }

    override fun lazyFetchData() {
        super.lazyFetchData()
    }

}
