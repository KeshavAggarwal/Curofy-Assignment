package com.example.keshavaggarwal.curofyassignment.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keshavaggarwal.curofyassignment.R

/**
 * Created by KeshavAggarwal on 14/07/18.
 */
class FragmentLogin : Fragment() {

    private lateinit var listenerPhNum : PhNumClickListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_login, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listenerPhNum = context as PhNumClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    interface PhNumClickListener{
        fun onPhNumClicked()
    }

    fun setPhNumClickListener(listenerPh : PhNumClickListener){
        this.listenerPhNum = listenerPh
    }

    companion object {
        fun newInstance() : FragmentLogin = FragmentLogin()

    }


}