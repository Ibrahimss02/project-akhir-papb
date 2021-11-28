package com.papb.projectakhirpapb.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.papb.projectakhirpapb.R
import com.papb.projectakhirpapb.addEntry.AddOrUpdateActivity
import com.papb.projectakhirpapb.databinding.ActivityMainBinding
import com.papb.projectakhirpapb.detailGame.DetailGameActivity
import com.papb.projectakhirpapb.login.LoginActivity
import kotlin.system.exitProcess

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

        viewModel.onSignedOut.observe(this, {
            if (it) {
                Toast.makeText(this, "Signed out", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signOut -> viewModel.signOut()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigateUp(): Boolean {
        finishAndRemoveTask()
        return super.onNavigateUp()
    }
}