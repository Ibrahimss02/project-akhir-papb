package com.papb.projectakhirpapb.addEntry

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.papb.projectakhirpapb.dto.Developer
import com.papb.projectakhirpapb.dto.Game
import com.papb.projectakhirpapb.home.ActivityMainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityAddOrUpdateViewModel() : ViewModel() {

    companion object {
        const val DEVELOPER_COLLECTION = "developer"
        const val TAG = "AddOrUpdateViewModel"
    }

    private val _addedGame = MutableLiveData(false)
    val addedGame: LiveData<Boolean>
        get() = _addedGame

    val developer = MutableLiveData<Developer>()


    private val firestore = Firebase.firestore

    fun onClickSave(
        title: String, description: String, developer: String, publisher: String, genre: String,
        releaseYear: Int, rating: Float, price: Int, id: String = ""
    ) {

        Log.i(TAG, this.developer.value.toString())


        if (id.isEmpty()) {
            Log.i(TAG, "isEmpty")
            val newGame = hashMapOf(
                "id" to "",
                "nama" to title,
                "deskripsi" to description,
                "id_developer" to this.developer.value?.id,
                "publisher" to publisher,
                "genre" to genre,
                "tahun_rilis" to releaseYear,
                "rating" to rating,
                "harga" to price
            )

            viewModelScope.launch(Dispatchers.IO) {
                firestore.collection(ActivityMainViewModel.GAME_COLLECTION).add(newGame)
                    .addOnSuccessListener {
                        updateGameWithId(it.id, title)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "error adding game", e)
                    }
            }
        }
    }

    private fun updateGameWithId(id: String, title: String) {
        firestore.collection(ActivityMainViewModel.GAME_COLLECTION).whereEqualTo("nama", title)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, error.message, error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    val document = value.documents
                    var game: Game?
                    document.forEach {
                        game = it.toObject(Game::class.java)
                        if (game != null) {
                            game!!.id = id

                            firestore.collection(ActivityMainViewModel.GAME_COLLECTION).document(game!!.id)
                                .set(game!!, SetOptions.merge())
                                .addOnSuccessListener {
                                    Log.i(TAG, "new game added : ${game.toString()}")
                                    _addedGame.value = true
                                }
                                .addOnFailureListener{
                                    Log.w(TAG, "error adding game", it)
                                }
                        }
                    }
                }
            }
    }

    fun onClickUpdateGame(game: Game) {
            Log.i(TAG, "isNotEmpty")

            viewModelScope.launch(Dispatchers.IO) {
                firestore.collection(ActivityMainViewModel.GAME_COLLECTION).document(game.id)
                    .set(game, SetOptions.merge())
                    .addOnSuccessListener {
                        viewModelScope.launch(Dispatchers.Main) {
                            _addedGame.value = true
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "error adding game", e)
                    }
            }
    }

    fun addDeveloperToCloud(developer: String) {
        val newDeveloper = hashMapOf(
            "nama" to developer,
            "deskripsi" to "Unknown",
            "alamat" to "Unknown"
        )

        firestore.collection(DEVELOPER_COLLECTION).add(newDeveloper)
            .addOnSuccessListener {
                Log.d(TAG, "new developer added with id: ${it.id}")
                val developer = Developer(it.id, newDeveloper["nama"], newDeveloper["deskripsi"], newDeveloper["alamat"])

                firestore.collection(DEVELOPER_COLLECTION).document(developer.id).set(developer, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.i(TAG, "new developer added: $developer")
                        this.developer.value = developer
                    }
                    .addOnFailureListener {
                        Log.w(TAG, "error adding new developer item", it)
                    }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding developer", e)
            }
    }

    fun navigatedAway() {
        _addedGame.value = false
    }

}