package com.papb.projectakhirpapb.addEntry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.papb.projectakhirpapb.R
import com.papb.projectakhirpapb.databinding.ActivityAddOrUpdateBinding
import com.papb.projectakhirpapb.dto.Developer
import com.papb.projectakhirpapb.dto.Game
import com.papb.projectakhirpapb.home.MainActivity
import java.math.RoundingMode

class AddOrUpdateActivity : AppCompatActivity() {

    companion object {
        const val EDIT_ENTRY = "entry_edit"
        const val OBJECT_GAME = "game_object"
        const val OBJECT_DEVELOPER = "developer_object"
    }

    private val viewModel: ActivityAddOrUpdateViewModel by lazy {
        ActivityAddOrUpdateViewModel()
    }
    private lateinit var binding: ActivityAddOrUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_or_update)

        binding.lifecycleOwner = this

        val isEditEntry = intent.getBooleanExtra(EDIT_ENTRY, false)

        if (isEditEntry) {
            val game = intent.getParcelableExtra<Game>(OBJECT_GAME)!!
            val developer = intent.getParcelableExtra<Developer>(OBJECT_DEVELOPER)
            Log.i("UpdateAdd", "running with $game")
            binding.apply {
                inputTitle.editText!!.setText(game.nama)
                inputDescription.editText!!.setText(game.deskripsi)
                inputPublisher.editText!!.setText(game.publisher)
                inputGenre.editText!!.setText(game.genre)
                inputReleaseYear.editText!!.setText(game.tahun_rilis.toString())
                inputRating.editText!!.setText(game.rating.toString())
                inputPrice.editText!!.setText(game.harga.toString())

                if (developer?.nama != null) {
                    this.inputDeveloper.editText!!.setText(developer.nama)
                    viewModel.developer.value = developer
                }

                saveButton.setOnClickListener {
                    val title = this.etGameTitle.text.toString()
                    val description = this.etGameDescription.text.toString()
                    val developerNew = this.etGameDeveloper.text.toString().trim()
                    val publisher = this.etGamePublisher.text.toString()
                    val genre = this.etGameGenre.text.toString()
                    val releaseYear = this.etGameReleaseYear.text.toString().toInt()
                    val rating = this.etGameRating.text.toString().toFloat()
                    val price = this.etGamePrice.text.toString().toInt()

                    if (developer?.nama != developerNew) {
                        Log.i("EditActivity", "different")
                        viewModel.addDeveloperToCloud(developerNew)

                        viewModel.developer.observe(this@AddOrUpdateActivity, {
                            game.apply {
                                nama = title
                                deskripsi = description
                                this.id_developer = it.id
                                this.publisher = publisher
                                this.genre = genre
                                tahun_rilis = releaseYear
                                this.rating = rating
                                harga = price

                                viewModel.onClickUpdateGame(game)
                            }
                        })
                    } else {
                        Log.i("EditActivity", "same")
                        game.apply {
                            nama = title
                            deskripsi = description
                            this.publisher = publisher
                            this.genre = genre
                            tahun_rilis = releaseYear
                            this.rating = rating
                            harga = price

                        }

                        viewModel.onClickUpdateGame(game)
                    }
                }
            }
        } else {
            binding.apply {
                saveButton.setOnClickListener {
                    val title = this.etGameTitle.text.toString()
                    val description = this.etGameDescription.text.toString()
                    val developer = this.etGameDeveloper.text.toString().trim()
                    val publisher = this.etGamePublisher.text.toString()
                    val genre = this.etGameGenre.text.toString()
                    val releaseYear = this.etGameReleaseYear.text.toString().toInt()
                    val rating = this.etGameRating.text.toString().toFloat()
                    val price = this.etGamePrice.text.toString().toInt()

                    if (developer.isNotEmpty()){
                        viewModel.addDeveloperToCloud(developer)

                        viewModel.developer.observe(this@AddOrUpdateActivity, {
                            viewModel.onClickSave(
                                title,
                                description,
                                developer,
                                publisher,
                                genre,
                                releaseYear,
                                rating,
                                price
                            )
                        })
                    } else {
                        viewModel.onClickSave(
                            title,
                            description,
                            developer,
                            publisher,
                            genre,
                            releaseYear,
                            rating,
                            price
                        )
                    }
                }
            }
        }

        viewModel.addedGame.observe(this, {
            if (it) {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
                viewModel.navigatedAway()
            }
        })
    }
}