package com.example.dejatellevar.perfilUsuario

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.example.dejatellevar.Categorias1Activity
import com.example.dejatellevar.ImageData
import com.example.dejatellevar.R
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.*

class Categorias4Activity : AppCompatActivity(), SensorEventListener {
    private lateinit var likeCountTextView: TextView
    lateinit var casaicono: ImageView
    private lateinit var imageView: ImageView
    private lateinit var likeButton: ImageButton
    private lateinit var dislikeButton: ImageButton
    private var currentImageIndex = 0
    private var imageList = mutableListOf<ImageData>()

    private lateinit var firestore: FirebaseFirestore
    private var documentReference: DocumentReference? = null
    private var firestoreListener: ListenerRegistration? = null

    private lateinit var sensorManager: SensorManager
    private var gyroscope: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias4)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        initComponents()
        imageList = intent.getSerializableExtra("imageList") as? ArrayList<ImageData> ?: arrayListOf()
        cargarimagenes()
        initListeners()

        // Inicializa Firestore y comienza a escuchar cambios en los likes
        firestore = FirebaseFirestore.getInstance()
        if (imageList.isNotEmpty()) {
            documentReference = firestore.collection("servicio").document(imageList[currentImageIndex].documentId)
            firestoreListener = documentReference?.addSnapshotListener(EventListener<DocumentSnapshot> { snapshot, e ->
                if (e != null) {
                    Log.w("Firestore", "Listen failed.", e)
                    return@EventListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val likes = snapshot.getLong("likes")
                    if (likes != null) {
                        // Actualiza la cantidad de likes en tiempo real
                        likeCountTextView.text = likes.toString()
                    }
                }
            })
        }

        // Inicializa el giroscopio
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            Log.e("Giroscopio", "No se ha encontrado el sensor de giroscopio en este dispositivo.")
        }
    }

    fun initComponents() {
        casaicono = findViewById(R.id.casaicono)
        likeCountTextView = findViewById(R.id.likeCountTextView)
        imageView = findViewById(R.id.imageView8)
        likeButton = findViewById(R.id.imageButton5)
        dislikeButton = findViewById(R.id.imageButton3)
    }

    private fun cargarimagenes() {
        if (imageList.isNotEmpty()) {
            val currentImage = imageList[currentImageIndex]
            // Load and display the image from the currentImage object using Glide
            Glide.with(this)
                .load(currentImage.imageUrl)
                .into(imageView)
            likeCountTextView.text = currentImage.likes.toString()
        }
    }

    private fun initListeners() {
        casaicono.setOnClickListener {
            val intent = Intent(this, Categorias1Activity::class.java)
            startActivity(intent)
        }

        likeButton.setOnClickListener {
            if (imageList.isNotEmpty()) {
                val currentImage = imageList[currentImageIndex]
                val newLikes = currentImage.likes + 1 // Incrementa los likes localmente

                // Actualiza los likes en Firestore
                documentReference?.update("likes", newLikes)
                    ?.addOnSuccessListener {
                        // La actualización en Firestore fue exitosa
                        currentImage.likes = newLikes // Actualiza los likes localmente

                        // Carga la siguiente imagen
                        loadNextImage()
                    }
                    ?.addOnFailureListener { e ->
                        // Error al actualizar en Firestore, maneja el error según sea necesario
                        Log.e("Firestore", "Error al actualizar los likes: $e")
                    }
            }
        }


        dislikeButton.setOnClickListener {
        }
    }
    private fun loadNextImage() {
        currentImageIndex++
        if (currentImageIndex < imageList.size) {
            // Cargar la siguiente imagen si hay más
            cargarimagenes()
        } else {
            // Volver al principio si estás en la última imagen
            currentImageIndex = 0
            cargarimagenes()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        // Detén la escucha de Firestore cuando ya no sea necesario
        firestoreListener?.remove()

        // Detén la detección del giroscopio
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            val x = event.values[0] // Valor en el eje X
            if (x > 0.5) {
                // Mover a la derecha (dar like)
                likeButton.performClick()
            } else if (x < -0.5) {
                // Mover a la izquierda (dar dislike)
                dislikeButton.performClick()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No se necesita implementación aquí
    }


}
