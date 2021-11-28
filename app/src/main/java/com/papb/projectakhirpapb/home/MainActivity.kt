package com.papb.projectakhirpapb.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.papb.projectakhirpapb.R
import com.papb.projectakhirpapb.addEntry.AddOrUpdateActivity
import com.papb.projectakhirpapb.databinding.ActivityMainBinding
import com.papb.projectakhirpapb.detailGame.DetailGameActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : ActivityMainViewModel by lazy {
        ActivityMainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        val adapter = GameAdapter(GameItemListener {
            val intent = Intent(this, DetailGameActivity::class.java)
            intent.putExtra(DetailGameActivity.GAME_OBJECT, it)
            startActivity(intent)
        })

        binding.gameRv.adapter = adapter

        viewModel.gameList.observe(this, {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
            }
        })

        binding.addButton.setOnClickListener {
            startActivity(Intent(this, AddOrUpdateActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchGameFromDb()
    }
}