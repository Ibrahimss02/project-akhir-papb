package com.papb.projectakhirpapb.register

import android.app.Application
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = Firebase.auth

    fun registerUser() {
        val username = ""
        val email = ""
        val password = ""
        val confirmPassword = ""

        if (validateForm(username, email, password, confirmPassword)) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Register Success
                    } else {
                        Log.w("Register", task.exception)
                        Toast.makeText(getApplication(), "Register User failed.", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun validateForm(name : String, email : String, password : String, confirmPassword : String) : Boolean {
        return when {
            TextUtils.isEmpty(name) -> return false
            TextUtils.isEmpty(email) -> return false
            TextUtils.isEmpty(password) -> return false
            !TextUtils.equals(password, confirmPassword) -> return false
            else -> true
        }
    }
}