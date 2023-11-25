package com.example.dejatellevar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.dejatellevar.R
import org.json.JSONArray
import org.json.JSONObject

class InicioTikTok : AppCompatActivity() {

    lateinit var contratoact: ImageView
    lateinit var historialTikTok: ImageView
    lateinit var creeps: ImageView
    lateinit var notifica: ImageView
    private lateinit var viewPager2: ViewPager2
    private lateinit var tiktokerDetailsLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_tik_tok)

        initComponents()
        initListeners()

        // Assuming you have a URL for fetching TikTokers data
        val apiUrl = "https://retoolapi.dev/dcHvMb/data"

        // Call this function to fetch and display data
        fetchDataAndDisplay(apiUrl)
    }

    private fun fetchDataAndDisplay(apiUrl: String) {
        val queue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, apiUrl, null,
            Response.Listener<JSONArray> { response ->
                // Process the JSON response and display it in ViewPager2 and details layout
                processJsonResponse(response)
            },
            Response.ErrorListener { error ->
                println("Error en la solicitud: ${error.message}")
            }
        )

        queue.add(jsonArrayRequest)
    }

    private fun processJsonResponse(jsonArray: JSONArray) {
        val listaTiktokers = ArrayList<Tiktoker>()

        for (i in 0 until jsonArray.length()) {
            val tiktokerJson: JSONObject = jsonArray.getJSONObject(i)

            val id = tiktokerJson.getInt("id")
            val nombre = tiktokerJson.getString("nombre")
            val imagenUrl = tiktokerJson.getString("imagen")

            val tiktoker = Tiktoker(id, nombre, imagenUrl)
            listaTiktokers.add(tiktoker)
        }

        val adapter = TiktokerAdapter(this, listaTiktokers)
        viewPager2.adapter = adapter

        // Assuming you want to display details of the first TikToker initially
        displayTiktokerDetails(listaTiktokers[10])
    }

    private fun displayTiktokerDetails(tiktoker: Tiktoker) {

    }


    fun initComponents(){
        // identificar id de una imagen botton
        contratoact = findViewById(R.id.contratos)
        historialTikTok = findViewById(R.id.historial)
        creeps = findViewById(R.id.creeps)
        notifica = findViewById(R.id.imageView19)

        viewPager2 = findViewById(R.id.viewPager2)
        tiktokerDetailsLayout = findViewById(R.id.tiktokerDetailsLayout)

    }

    private fun initListeners() {

        contratoact.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, Contratoact2::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        historialTikTok.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, HistorialAyuda::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)

        }

        creeps.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, agregarProduct::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        notifica.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, notificacion::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }



    }

}