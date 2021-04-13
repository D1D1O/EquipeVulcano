package com.hefesto.pokedex_equipevulcano

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val name: String,
    val number: Int,
    val types:List<String>,
    val weight: Float,
    val height: Float,
    var latitude: Double,
    var longitude: Double,
    val imageUrl: String
):Parcelable