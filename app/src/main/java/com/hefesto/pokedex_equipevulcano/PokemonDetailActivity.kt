package com.hefesto.pokedex_equipevulcano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon_detail.*

class PokemonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)
        intent.getParcelableExtra<Pokemon>(POKEMON_EXTRA)?.let{
            tvName.text = it.name
            tvNumber.text = "#%03d".format(it.number)
            tvFirstType.text = it.types.getOrNull(0)
            val secondType:String? = it.types.getOrNull(1)
            if(secondType == null){
                tvSecondType.visibility = View.GONE
            }else{
                tvSecondType.visibility = View.VISIBLE
                tvSecondType.text = secondType
            }
            tvWeight.text = "%.1f kg".format(it.weight)
            tvHeight.text = "%.2f cm".format(it.height/10)
            Picasso.get().load(it.imageUrl).into(ivImage)
        }
    }

    companion object{
        const val POKEMON_EXTRA = "Pokemon"
    }
}