package com.papb.projectakhirpapb.detailDeveloper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.papb.projectakhirpapb.R
import com.papb.projectakhirpapb.databinding.ActivityDetailDeveloperBinding
import com.papb.projectakhirpapb.dto.Developer

class DetailDeveloperActivity : AppCompatActivity() {

    companion object {
        const val DEVELOPER_OBJECT = "developer_object"
    }

    private lateinit var binding: ActivityDetailDeveloperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_developer)

        val developer = intent.getParcelableExtra<Developer>(DEVELOPER_OBJECT)
        developer?.let {
            binding.tvDevn.text = developer.nama
            if (it.deskripsi.equals("Unknown")) {
                binding.tvDevd.text = String.format("%s's has no description", it.nama)
            } else {
                binding.tvDevd.text = it.deskripsi
            }
            if (it.alamat.equals("Unknown")) {
                binding.tvAdrs.text = String.format("%s's has no address", it.nama)
            } else {
                binding.tvAdrs.text = it.alamat
            }
        }
    }
}