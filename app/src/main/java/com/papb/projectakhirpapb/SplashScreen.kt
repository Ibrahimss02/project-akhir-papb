package com.papb.projectakhirpapb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.papb.projectakhirpapb.home.MainActivity
import com.papb.projectakhirpapb.login.LoginActivity
import com.papb.projectakhirpapb.register.RegisterActivity
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)

        lifecycleScope.launch {
            delay(1500)
            val user = auth.currentUser
            if (user == null) {
                startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            }
        }
    }
}