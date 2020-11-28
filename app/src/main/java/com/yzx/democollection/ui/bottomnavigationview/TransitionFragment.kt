package com.yzx.democollection.ui.bottomnavigationview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TransitionFragment(private val layout: Int) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.i("TAG", "onCreateView: $layout")
        return inflater.inflate(layout, container, false)
    }

}