package com.papb.projectakhirpapb.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.papb.projectakhirpapb.R
import com.papb.projectakhirpapb.databinding.ActivityLoginBinding
import com.papb.projectakhirpapb.home.MainActivity
import com.papb.projectakhirpapb.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val viewModel = LoginActivityViewModel(application)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.tvReg2.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        viewModel.onLoginSuccess.observe(this, {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }
}