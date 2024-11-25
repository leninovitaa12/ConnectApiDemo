package com.example.retro

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var refreshButton: Button
    private var imageList: List<RecipeImage> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.gambar)
        refreshButton = findViewById(R.id.btnRecipes)

        fetchImagesFromApi()

        refreshButton.setOnClickListener {
            displayRandomImage()
        }
    }

    private fun fetchImagesFromApi() {
        RetrofitInstance.api.getImages().enqueue(object : Callback<List<RecipeImage>> {
            override fun onResponse(
                call: Call<List<RecipeImage>>,
                response: Response<List<RecipeImage>>
            ) {
                if (response.isSuccessful) {
                    imageList = response.body() ?: emptyList()
                    Toast.makeText(this@MainActivity, "Images loaded!", Toast.LENGTH_SHORT).show()
                    displayRandomImage()
                } else {
                    Toast.makeText(this@MainActivity, "Failed to load images", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<RecipeImage>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error fetching images: ${t.message}")
            }
        })
    }

    private fun displayRandomImage() {
        if (imageList.isNotEmpty()) {
            val randomImage = imageList.random()
            Glide.with(this)
                .load(randomImage.image)
                .into(imageView)
        } else {
            Toast.makeText(this, "No images available", Toast.LENGTH_SHORT).show()
        }
    }
}