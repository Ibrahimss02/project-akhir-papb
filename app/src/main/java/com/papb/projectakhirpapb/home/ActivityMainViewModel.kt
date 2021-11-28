package com.papb.projectakhirpapb.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.papb.projectakhirpapb.dto.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityMainViewModel : ViewModel() {

    companion object {
        const val TAG = "ActivityMain"
        const val GAME_COLLECTION = "games"
    }

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    private val _onSignedout = MutableLiveData(false)
    val onSignedOut : LiveData<Boolean>
         get() = _onSignedout

    private val _gameList = MutableLiveData<List<Game>>()
    val gameList : LiveData<List<Game>>
        get() = _gameList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchGameFromDb()
        }
    }

    fun fetchGameFromDb() {
        val listGame = ArrayList<Game>()
        firestore.collection(GAME_COLLECTION).addSnapshotListener { value, error ->
            if (error != null) {
                Log.w(TAG, "Error fetching game from cloud", error)
                return@addSnapshotListener
            }
            if (value != null) {
                val documents = value.documents
                var game : Game?
                documents.forEach {
                    game = it.toObject(Game::class.java)
                    game?.let { it1 ->
                        listGame.add(it1)
                        Log.d(TAG, game.toString())
                    }
                }
            }
            viewModelScope.launch(Dispatchers.Main) {
                _gameList.value = listGame
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _onSignedout.value = true
    }
}