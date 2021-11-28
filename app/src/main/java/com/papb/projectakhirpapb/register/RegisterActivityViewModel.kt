package com.papb.projectakhirpapb.register

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
import kotlin.contracts.contract

class RegisterActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = Firebase.auth

    var email = MutableLiveData<String>()
    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var confirmPassword = MutableLiveData<String>()

    private val _onRegisterSuccess = MutableLiveData(false)
    val onRegisterSuccess : LiveData<Boolean>
        get() = _onRegisterSuccess

    fun registerUser() {
        val username = username.value?.trim()
        val email = email.value?.trim()
        val password = password.value?.trim()
        val confirmPassword = confirmPassword.value?.trim()

        if (validateForm(username, email, password, confirmPassword)) {
            auth.createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _onRegisterSuccess.value = true
                    } else {
                        Log.w("Register", task.exception)
                        Toast.makeText(getApplication(), "Register User failed.", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun validateForm(name : String?, email : String?, password : String?, confirmPassword : String?) : Boolean {
        return when {
            TextUtils.isEmpty(name) -> return false
            TextUtils.isEmpty(email) -> return false
            TextUtils.isEmpty(password) -> return false
            !TextUtils.equals(password, confirmPassword) -> return false
            else -> true
        }
    }

    fun onNavigatedSuccess(){
        _onRegisterSuccess.value = false
    }
}