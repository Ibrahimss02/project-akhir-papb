package com.papb.projectakhirpapb.detailGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.papb.projectakhirpapb.R
import com.papb.projectakhirpapb.addEntry.AddOrUpdateActivity
import com.papb.projectakhirpapb.databinding.ActivityDetailGameBinding
import com.papb.projectakhirpapb.detailDeveloper.DetailDeveloperActivity
import com.papb.projectakhirpapb.dto.Game

class DetailGameActivity : AppCompatActivity() {

    companion object {
        const val GAME_OBJECT = "game_object"
    }

    private lateinit var binding: ActivityDetailGameBinding
    private lateinit var viewModel: ActivityDetailGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_game)

        binding.lifecycleOwner = this

        val game = intent.getParcelableExtra<Game>(GAME_OBJECT)
        viewModel = ActivityDetailGameViewModel(game!!)

        binding.apply {
            tvGameTitle.text = game.nama
            tvGameDescription.text = game.deskripsi
            tvGameGenre.text = String.format("Genre: %s", game.genre)
            tvPublisher.text = String.format("Publisher: %s", game.publisher)
            tvReleaseYear.text = String.format("Release year: %d", game.tahun_rilis)
            tvPrice.text = String.format("Price: Rp %,d", game.harga)
            tvGameRating.text = game.rating.toString()

            viewModel.developer.observe(this@DetailGameActivity, { dev ->
                Log.i("DetailGame", dev.toString())
                if (!game.id_developer.isNullOrEmpty()) {
                    binding.tvGameDeveloper.text = String.format("Developer: %s", dev.nama)
                    binding.btnCheckDev.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            val intent = Intent(this@DetailGameActivity, DetailDeveloperActivity::class.java)
                            intent.putExtra(DetailDeveloperActivity.DEVELOPER_OBJECT, dev)
                            startActivity(intent)
                        }
                    }
                }
            })
        }

        binding.btnEditEntry.setOnClickListener {
            val intent = Intent(this, AddOrUpdateActivity::class.java)
            intent.putExtra(AddOrUpdateActivity.EDIT_ENTRY, true)
            intent.putExtra(AddOrUpdateActivity.OBJECT_GAME, game)
            if (viewModel.developer.value != null) {
                intent.putExtra(AddOrUpdateActivity.OBJECT_DEVELOPER, viewModel.developer.value)
            }
            startActivity(intent)
        }

    }
}