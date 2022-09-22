package com.kismi.unscramble

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kismi.unscramble.R



/**
 * pada bagian ini untuk membuat activiti yang menghosting fragmen Game di aplikasi
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}