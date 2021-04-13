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
    //private lateinit var adapter: PokemonAdapter
    private  var pokemons: MutableList<Pokemon> = mutableListOf()

    private var adapter: PokemonAdapter = PokemonAdapter(pokemons) {
        Intent(this, PokemonDetailActivity::class.java).apply {
            putExtra(PokemonDetailActivity.POKEMON_EXTRA, it)
        }.also { startActivity(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Places.initialize(applicationContext,BuildConfig.GOOGLE_API_KEY)
        setUpPokemonListWithRecyclerView()
        setUpAddPokemonButtonClick()

        shouldDisplayEmptyView()

       /* setUpRecycleView()
        fabAddPokemon.setOnClickListener {
            val intent = Intent(this,PokemonAddActivity::class.java)
            startActivityForResult(intent,ADD_POKEMON_REQUEST_CODE)
        }
        shouldDisplayEmptyView(pokemons.isEmpty())*/
    }
   /* private fun setUpRecycleView(){
        adapter = PokemonAdapter(pokemons) {
            val intent = Intent(this,PokemonDetailActivity::class.java).apply {
                putExtra(PokemonDetailActivity.POKEMON_EXTRA,it)
            }
            startActivity(intent)
        }

        rvPokemons.adapter = adapter
    }*/

    private fun setUpPokemonListWithRecyclerView() {
        rvPokemons.adapter = adapter
    }

    private fun setUpAddPokemonButtonClick() {
        fabAddPokemon.setOnClickListener { startAddPokemonActivityForNewPokemon() }
    }

    private fun startAddPokemonActivityForNewPokemon() {
        Intent(this, PokemonAddActivity::class.java).also {
            startActivityForResult(it, ADD_POKEMON_REQUEST_CODE)
        }
    }

    /*fun shouldDisplayEmptyView(isEmpty: Boolean) {
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }*/
    private fun shouldDisplayEmptyView() {
        emptyView.visibility = if (pokemons.isEmpty()) View.VISIBLE else View.GONE
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_POKEMON_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            data?.getParcelableExtra<Pokemon>(PokemonAddActivity.ADD_POKEMON_EXTRA)?.let{
                //pokemons.add(it)
                //adapter.notifyDataSetChanged()
                appendNewPokemonToRecyclerView(it)
            }

        }

    }
    private fun appendNewPokemonToRecyclerView(pokemon: Pokemon){
        pokemons.add(pokemon)
        adapter.notifyItemInserted(pokemons.size -1)
        shouldDisplayEmptyView()
    }

    companion object{
        const val ADD_POKEMON_REQUEST_CODE = 1
    }


}