package com.hefesto.pokedex_equipevulcano

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
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
            -60.147652,
            "https://assets.pokemon.com/assets/cms2/img/misc/countries/pt/country_detail_pokemon.png"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //pokemons = listOf("Pteste1","Pteste2","PGiovanny")
        rvPokemons.adapter = PokemonAdapter(pokemons) {
            startActivity(Intent(this,PokemonDetailActivity::class.java))
        }
        shouldDisplayEmptyView(pokemons.isEmpty())
        //

    }

    fun shouldDisplayEmptyView(isEmpty: Boolean) {
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    class PokemonAdapter(
        private val pokemons: List<Pokemon>,
        private val onItemClick: (Pokemon) -> Unit
    ):
        RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>(){
        class PokemonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
            val intemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pokemon,parent,false)
            return PokemonViewHolder(intemView)
        }

        override fun getItemCount() = pokemons.size

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
            holder.itemView.tvPokemonName.text = pokemons[position].name
            holder.itemView.tvPokemonNumber.text = "#%03d".format(pokemons[position].number)
            Picasso.get().load(pokemons[position].imageUrl).into(holder.itemView.ivPokemonImage)

            holder.itemView.setOnClickListener {
                onItemClick(pokemons[position])
            }
        }
    }
}