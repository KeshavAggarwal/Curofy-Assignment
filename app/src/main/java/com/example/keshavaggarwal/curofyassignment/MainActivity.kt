package com.example.keshavaggarwal.curofyassignment

import android.os.Bundle
import com.example.keshavaggarwal.curofyassignment.databinding.ActivityMainBinding

/**
 * Created by KeshavAggarwal on 15/07/18.
 */
class MainActivity : BaseActivity<ActivityMainBinding>(){

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}