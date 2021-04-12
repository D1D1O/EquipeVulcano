package com.hefesto.pokedex_equipevulcano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class MainActivity : AppCompatActivity() {
    private  var pokemons: List<Pokemon> = listOf(
        Pokemon(
            "Pikachu234",
            254,
            listOf("Eletric"),
            6f,
            4f,
            -3.1828263,
            -60.147652
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //pokemons = listOf("Pteste1","Pteste2","PGiovanny")
        rvPokemons.adapter = PokemonAdapter(pokemons)
        shouldDisplayEmptyView(pokemons.isEmpty())

    }

    fun shouldDisplayEmptyView(isEmpty: Boolean) {
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    class PokemonAdapter(private val pokemons: List<Pokemon>): RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>(){
        class PokemonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
            val intemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pokemon,parent,false)
            return PokemonViewHolder(intemView)
        }

        override fun getItemCount() = pokemons.size

        override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
            holder.itemView.tvPokemonName.text = pokemons[position].name
            holder.itemView.tvPokemonNumber.text = pokemons[position].number.toString()
        }
    }
}