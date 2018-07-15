package com.example.keshavaggarwal.curofyassignment.utils

import android.databinding.BindingAdapter
import android.util.Log
import android.view.View

/**
 * Created by KeshavAggarwal on 15/07/18.
 */
class BindingUtils
// can't instantiate this class publicly
private constructor(){

    companion object {

        @BindingAdapter(value = "visibleGone")
        @JvmStatic
        fun showHide(view: View, show: Boolean) {
            if (show) {
                Log.i("VISIBLE", "show is true")
                view.visibility = View.VISIBLE
            } else {
                Log.i("GONE", "show is false")
                view.visibility = View.GONE
            }
        }
    }
}