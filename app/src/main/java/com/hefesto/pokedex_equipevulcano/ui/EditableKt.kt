package com.hefesto.pokedex_equipevulcano.ui

import android.text.Editable

val Editable?.isFilled: Boolean get() = !(isNullOrEmpty() || isNullOrBlank())