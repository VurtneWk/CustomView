package com.vurtnewk.ui.custom.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vurtnewk.ui.custom.R

/**
 * Created by VurtneWk on 2021/5/11
 */
class TabTest02Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_tab_test_02, container, false)
    }
}