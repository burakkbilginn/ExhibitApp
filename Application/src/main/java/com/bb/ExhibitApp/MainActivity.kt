package com.bb.ExhibitApp

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Welcome"

//        launchScreen.alpha = 1f

        val w = window
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        w.setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        w.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

//        hideNavigation()

//        Handler().postDelayed({
//            launchScreen.alpha = 0f
//                    //fullscreen icin:
//            w.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
//            w.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//
//        }, 5000.toLong())



        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentbox, FirstFragment()).addToBackStack(null).commit()
    }

    fun hideNavigation() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }}

}




fun alphaDown(obje: View){
    val animation = AlphaAnimation(1f, 0.4f)
    animation.duration = 150
    animation.repeatMode = 1
    obje.startAnimation(animation)
    obje.apply { isEnabled = false; postDelayed({ isEnabled = true }, 70) }
    //vibration:
    obje.setHapticFeedbackEnabled(true);
    obje.performHapticFeedback(
        HapticFeedbackConstants.VIRTUAL_KEY,
        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
    )  //ikinci flag'ı sil eğer çalışmazsa
}

fun alphaUp(obje: View){
//        val animation = AlphaAnimation(0f, 0.7f)
//        animation.duration = 500
//        animation.repeatMode = 1
//        obje.startAnimation(animation)
    obje.alpha = 1f
//        obje.apply { isEnabled = false; postDelayed({ isEnabled = true }, 70) }

//        val handler = Handler()
//        val delay = 3000.toLong() //milliseconds
//        handler.postDelayed(object : Runnable {
//            override fun run() {
//                handler.postDelayed(this, delay)
//                obje.alpha = 0f
//            }
//        }, delay.toLong())

    Handler().postDelayed({
        obje.alpha = 0f
    }, 3000.toLong())
}

object Utils {
    fun showToast(mContext: Context?, message: String?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post { Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show() }
    }
}

