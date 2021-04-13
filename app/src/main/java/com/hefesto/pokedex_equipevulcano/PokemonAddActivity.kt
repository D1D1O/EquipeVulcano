package com.hefesto.pokedex_equipevulcano

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.hefesto.pokedex_equipevulcano.ui.isFilled
import com.hefesto.pokedex_equipevulcano.ui.shortToast
import kotlinx.android.synthetic.main.activity_pokemon_add.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/*import com.hefesto.pokedex.R
import com.hefesto.pokedex.data.AppDatabase
import com.hefesto.pokedex.data.PokeApi
import com.hefesto.pokedex.data.Pokemon
import com.hefesto.pokedex.isFilled
import com.hefesto.pokedex.shortToast
import kotlinx.android.synthetic.main.activity_add_pokemon.**/


class PokemonAddActivity : AppCompatActivity() {
    private lateinit var place: Place

 /*   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_add)
        setUpLocationInputClick()
        setUpDoneButtonClick()
    }

    private fun setUpLocationInputClick() {
        edtLocationInput.setOnClickListener { startAutocompleteActivityForPlace() }
    }

    private fun startAutocompleteActivityForPlace() {
        val fields = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG)
        Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this).also {
            startActivityForResult(it, AUTOCOMPLETE_REQUEST_CODE)
        }
    }

    private fun setUpDoneButtonClick() {
        btnDone.setOnClickListener {
            if (edtNameInput.text.isFilled && edtLocationInput.text.isFilled) {
                val name = edtNameInput.text.toString().toLowerCase(Locale.getDefault())
                fetchPokemonByName(
                    name,
                    onSuccess = {
                        val pokemonWithLocation = it.apply {
                            latitude = place.latLng?.latitude ?: 0.0
                            longitude = place.latLng?.longitude ?: 0.0
                        }
                        //AppDatabase.getInstance(this).pokemonDao.insert(pokemonWithLocation)
                        finish()
                    },
                    onError = { shortToast(R.string.error_message) }
                )
            } else {
                shortToast(R.string.invalid_fields_message)
            }
        }
    }

    private fun fetchPokemonByName(
        name: String,
        onSuccess: (Pokemon) -> Unit,
        onError: () -> Unit
    ) {
        PokeApi.INSTANCE.getPokemonByName(name).enqueue(object : Callback<Pokemon> {
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                onError()
            }

            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                val pokemon = response.body()
                if (response.isSuccessful && pokemon != null) {
                    onSuccess(pokemon)
                } else {
                    onError()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                place = Autocomplete.getPlaceFromIntent(it)
                edtLocationInput.setText(place.address)
            }
        }
    }
*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_add)
        btnDone.setOnClickListener {

            if((!edtNameInput.text.isNullOrBlank() && !edtNameInput.text.isNullOrEmpty() &&
                    !edtLocationInput.text.isNullOrBlank() && !edtLocationInput.text.isNullOrEmpty())){
                    // name = edtNameInput.text.toString().toLowerCase()
                    val name = edtNameInput.text.toString().toLowerCase(Locale.getDefault())
                    PokeApi.INSTANCE.getPokemonByName(name).enqueue(object : Callback<Pokemon>{
                        override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                            Toast.makeText(this@PokemonAddActivity,"Erro inesperado",Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                            if(response.isSuccessful){
                                //Toast.makeText(this@PokemonAddActivity,"SUCESSO !",Toast.LENGTH_SHORT).show()
                                //edtLocationInput.setText("status200")
                                val pokemon = response.body()?.apply {
                                    latitude = place.latLng?.latitude ?: 0.0
                                    longitude = place.latLng?.longitude ?: 0.0
                                }
                                val intent = Intent().apply {
                                    putExtra(ADD_POKEMON_EXTRA,pokemon)
                                }
                                setResult(Activity.RESULT_OK,intent)
                                finish()
                            }else{
                                Toast.makeText(this@PokemonAddActivity,"Erro !",Toast.LENGTH_SHORT).show()
                            }

                        }

                    })
                    //edtLocationInput.setText("sucesso")
            }else{
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
                    //edtLocationInput.setText("ERROR")
            }
        }
        setUpLocationInputClick()
    }

    private fun setUpLocationInputClick() {
        edtLocationInput.setOnClickListener {
            val fields = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG)
            val intent =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == AUTOCOMPLETE_REQUEST_CODE && data != null){
            if (resultCode == Activity.RESULT_OK){
                place = Autocomplete.getPlaceFromIntent(data)
                edtLocationInput.setText(place.address)
            }
        }
    }

    companion object{
        const val AUTOCOMPLETE_REQUEST_CODE =1
        const val ADD_POKEMON_EXTRA = "AddedPokemon"
    }
}