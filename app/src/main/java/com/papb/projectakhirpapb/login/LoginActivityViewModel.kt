package com.papb.projectakhirpapb.login

import android.app.Application
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.papb.projectakhirpapb.roomdb.User
import com.papb.projectakhirpapb.roomdb.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = Firebase.auth
    private lateinit var roomDb : UserDao

    fun signInUser() {
        val email = ""
        val password = ""

        if(validateForm(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewModelScope.launch(Dispatchers.IO) {
                            fetchUserIntoRoomDB()
                        }
                    } else {
                        Log.w("Login", task.exception)
                        Toast.makeText(getApplication(), "Sign In Failed.", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private suspend fun fetchUserIntoRoomDB() {
        // TODO(Get user first)
        val user = User()

        viewModelScope.launch(Dispatchers.IO) {
            roomDb.insert(user)
        }
    }

    private fun validateForm(email : String, password : String) : Boolean {
        return when {
            TextUtils.isEmpty(email) -> return false
            TextUtils.isEmpty(password) -> return false
            else -> true
        }
    }
}