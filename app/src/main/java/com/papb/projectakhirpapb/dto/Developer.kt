package com.papb.projectakhirpapb.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Developer(
    var id : String = "",
    val nama : String? = null,
    val deskripsi : String? = null,
    val alamat : String? = null,
) : Parcelable
