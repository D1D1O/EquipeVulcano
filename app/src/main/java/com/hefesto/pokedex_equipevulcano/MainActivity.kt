package com.hefesto.pokedex_equipevulcano

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.Places
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PokemonAdapter
    private  var pokemons: MutableList<Pokemon> = mutableListOf(
        Pokemon(
            "Pikachu",
            254,
            listOf("Eletric"),
            6f,
            4f,
            -3.1828263,
            -60.147652,
            "https://assets.pokemon.com/assets/cms2/img/misc/countries/pt/country_detail_pokemon.png"
        ),
        Pokemon(
            "Red",
            254,
            listOf("Eletric"),
            12f,
            42f,
            -3.1828263,
            -60.147652,
            "https://super.abril.com.br/wp-content/uploads/2018/07/57113eb00e2163161501025cpokemon21.jpeg"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Places.initialize(applicationContext,BuildConfig.GOOGLE_API_KEY)

        setUpRecycleView()
        fabAddPokemon.setOnClickListener {
            val intent = Intent(this,PokemonAddActivity::class.java)
            startActivityForResult(intent,ADD_POKEMON_REQUEST_CODE)
        }
        shouldDisplayEmptyView(pokemons.isEmpty())


    }
    private fun setUpRecycleView(){
        adapter = PokemonAdapter(pokemons) {
            val intent = Intent(this,PokemonDetailActivity::class.java).apply {
                putExtra(PokemonDetailActivity.POKEMON_EXTRA,it)
            }
            startActivity(intent)
        }

        rvPokemons.adapter = adapter
    }


    fun shouldDisplayEmptyView(isEmpty: Boolean) {
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_POKEMON_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            data?.getParcelableExtra<Pokemon>(PokemonAddActivity.ADD_POKEMON_EXTRA)?.let{
                pokemons.add(it)
                adapter.notifyDataSetChanged()
            }

        }

    }

    companion object{
        const val ADD_POKEMON_REQUEST_CODE = 1
    }


}