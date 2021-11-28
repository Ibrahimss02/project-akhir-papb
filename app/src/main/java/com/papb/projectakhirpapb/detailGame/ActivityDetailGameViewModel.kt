package com.papb.projectakhirpapb.detailGame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.papb.projectakhirpapb.addEntry.ActivityAddOrUpdateViewModel
import com.papb.projectakhirpapb.dto.Developer
import com.papb.projectakhirpapb.dto.Game

class ActivityDetailGameViewModel(game: Game) : ViewModel() {

    private val firestore = Firebase.firestore

    private val _developer = MutableLiveData<Developer>()
    val developer : LiveData<Developer>
        get() = _developer

    init {
        if (!game.id_developer.isNullOrEmpty()){
            getDeveloperData(game.id_developer!!)
        }
    }

    private fun getDeveloperData(developerId : String) {
            Log.d("DetailGameViewModel", "called with $developerId")
            firestore.collection(ActivityAddOrUpdateViewModel.DEVELOPER_COLLECTION).document(developerId).get()
                .addOnSuccessListener {
                    val developer = it.toObject(Developer::class.java)
                    if (developer != null) {
                        _developer.value = developer!!
                    }
                    Log.i("DetailGameViewModel", developer.toString())
                }
                .addOnFailureListener {
                    Log.w("DetailGameViewModel", it.message, it)
                }
    }
}