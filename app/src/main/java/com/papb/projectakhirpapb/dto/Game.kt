package com.papb.projectakhirpapb.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    var id : String = "",
    var nama : String = "",
    var deskripsi : String? = null,
    var id_developer : String? = null,
    var publisher : String? = null,
    var genre : String? = null,
    var tahun_rilis : Int = 0,
    var rating : Float = 0f,
    var harga : Int = 0
) : Parcelable
