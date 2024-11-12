package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private var listQuakes = mutableListOf<Features>()
    private lateinit var tvTitle: TextView
    private lateinit var tvCount: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerQuakes)


        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(listQuakes)
        recyclerView.adapter = adapter

        tvTitle = findViewById(R.id.textViewMainTitle)
        tvCount = findViewById(R.id.textViewMainCount)

        getQuakes()


    }

    private fun getQuakes() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(serviceApi::class.java).getQuakes("significant_month.geojson")
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val quakes = response?.features
                    quakes?.forEach {
                        listQuakes.add(it)
                    }
                    val metadata = response?.metadata?.title
                    tvTitle.text = metadata
                    val count = response?.metadata?.count
                    tvCount.text = "mostrando $count terremotos"

                    adapter.notifyDataSetChanged()

                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}