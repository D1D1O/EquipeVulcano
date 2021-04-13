package com.hefesto.pokedex_equipevulcano

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon_detail.*
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;


class PokemonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)
        intent.getParcelableExtra<Pokemon>(POKEMON_EXTRA)?.let{pokemon ->
            tvName.text = pokemon.name
            tvNumber.text = "#%03d".format(pokemon.number)
            tvFirstType.text = pokemon.types.getOrNull(0)
            val secondType:String? = pokemon.types.getOrNull(1)
            if(secondType == null){
                tvSecondType.visibility = View.GONE
            }else{
                tvSecondType.visibility = View.VISIBLE
                tvSecondType.text = secondType
            }
            tvWeight.text = "%.1f kg".format(pokemon.weight)
            tvHeight.text = "%.2f cm".format(pokemon.height/10)
            Picasso.get().load(pokemon.imageUrl).into(ivImage)

            val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
            mapFragment.getMapAsync {
                it.uiSettings.isZoomControlsEnabled = true
                val latLng = LatLng(pokemon.latitude, pokemon.longitude)


                val marker = MarkerOptions().position(latLng).icon(bitmapDescriptorFromVector(this, R.drawable.ic_marker))
                it.addMarker(marker)

                it.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                it.moveCamera(CameraUpdateFactory.zoomTo(15f))

            }
        }
    }


    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    private fun bindMapFragmentToPokemonLocation(pokemon: Pokemon) {
        (supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment).apply {
            getMapAsync { googleMap ->
                googleMap.uiSettings.isZoomControlsEnabled = true

                val latLng = LatLng(pokemon.latitude, pokemon.longitude)

                MarkerOptions()
                        .position(latLng)
                        .icon(activity?.let { bitmapDescriptorFromVector(it, R.drawable.ic_marker) })
                        .also { googleMap.addMarker(it) }

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
            }
        }
    }



    companion object{
        const val POKEMON_EXTRA = "Pokemon"
    }
}