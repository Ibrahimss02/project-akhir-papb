package com.papb.projectakhirpapb.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.papb.projectakhirpapb.R
import com.papb.projectakhirpapb.databinding.ActivityRegisterBinding
import com.papb.projectakhirpapb.home.MainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.lifecycleOwner = this
        viewModel = RegisterActivityViewModel(application)
        binding.viewModel = viewModel

        viewModel.onRegisterSuccess.observe(this, {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }
}