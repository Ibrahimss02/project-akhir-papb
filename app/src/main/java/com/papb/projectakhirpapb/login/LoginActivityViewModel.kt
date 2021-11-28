package com.papb.projectakhirpapb.login

import android.app.Application
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = Firebase.auth

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _onLoginSuccess = MutableLiveData(false)
    val onLoginSuccess : LiveData<Boolean>
         get() = _onLoginSuccess

    fun signInUser() {
        val email = email.value?.trim()
        val password = password.value?.trim()

        viewModelScope.launch {
            if(validateForm(email, password)) {
                auth.signInWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            viewModelScope.launch(Dispatchers.Main) {
                                _onLoginSuccess.value = true
                            }
                        } else {
                            Log.w("Login", task.exception)
                            Toast.makeText(getApplication(), "Sign In Failed. ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }

    private fun validateForm(email : String?, password : String?) : Boolean {
        return when {
            TextUtils.isEmpty(email) -> return false
            TextUtils.isEmpty(password) -> return false
            else -> true
        }
    }
}