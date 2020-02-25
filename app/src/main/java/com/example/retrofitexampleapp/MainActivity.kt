package com.example.retrofitexampleapp

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    lateinit var imagesPuppies: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBreed.setOnQueryTextListener(this)

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query: String) = GlobalScope.launch {
        val call = getRetrofit().create(ApiService::class.java).getCharacterByName("$query/images")
            .execute()

        try{
        val puppies = call.body() as DogsResponse
        launch(Dispatchers.Main) {
            if (puppies.status == "success") {
                initCharacter(puppies.images)
            } else {
                //Toast.makeText(this@MainActivity,"Error de busqueda",Toast.LENGTH_SHORT).show()
                showErrorDialog()
            }
            hideKeyboard()
        }
    }catch (e: Throwable) {
            launch(Dispatchers.Main) {
                showEmptyDialog()
            }
            hideKeyboard()
        }
    }


    private fun initCharacter(images: List<String>) {
        if (images.isNotEmpty()) {
            imagesPuppies = images
        }
        RvDogs.layoutManager = GridLayoutManager(this,2)
        RvDogs.adapter = RecyclerDogAdapter(imagesPuppies)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchByName(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun showErrorDialog() {
        Toast.makeText(this, "Ha ocurrido un error, intentelo mas tarde", Toast.LENGTH_LONG).show()
   }
    private fun showEmptyDialog() {
        Toast.makeText(this,"No hay perros de esa raza", Toast.LENGTH_LONG).show()
    }
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewRoot.windowToken, 0)
    }
}