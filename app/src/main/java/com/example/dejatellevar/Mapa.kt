package com.example.dejatellevar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import android.app.UiModeManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.osmdroid.api.IMapController
import org.osmdroid.bonuspack.location.GeocoderNominatim
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.TilesOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.IMyLocationConsumer
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.concurrent.Executors
import android.os.Handler
import android.widget.ImageButton

class Mapa : AppCompatActivity(), SensorEventListener, IMyLocationConsumer,
    MapEventsReceiver  {

    lateinit var casaicono: ImageView
    private lateinit var mapView: MapView
    private lateinit var mapController: IMapController
    private lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val userPath: ArrayList<GeoPoint> = ArrayList()
    private val routePoints = ArrayList<GeoPoint>()
    private val locationHandler = Handler()
    private val locationUpdateInterval = 10000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        initComponents()
        initListeners()

        Configuration.getInstance().load(applicationContext, getPreferences(Context.MODE_PRIVATE))

        mapView = findViewById(R.id.mapView)
        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(applicationContext), mapView)
        myLocationOverlay.enableMyLocation()

        val editTextAddress = findViewById<EditText>(R.id.editTextAddress)
        val buttonSearch = findViewById<ImageButton>(R.id.buttonSearch)


        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.zoomController.setZoomInEnabled(true)
        mapView.zoomController.setZoomOutEnabled(true)
        mapView.setMultiTouchControls(true)
        mapController = mapView.controller
        mapController.setZoom(19.0)
        // Verificar permiso de ubicación
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            setupMapWithLocation()
        } else {
            // Solicitar permiso de ubicación
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }



        // Inicializa el sensor de luminosidad
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sharedPreferences = getSharedPreferences("MapPreferences", Context.MODE_PRIVATE)

        //configuracion para marker local
        setupMapWithLocation()

        //buscar ubicacion
        editTextAddress.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                createMarkerFromAddress(editTextAddress.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        buttonSearch.setOnClickListener {
            val address = editTextAddress.text.toString()
            createMarkerFromAddress(address)
        }

        //Longclick
        mapView.isLongClickable = true
        val longClickDetector = GestureDetector(applicationContext, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                val longClickLocation = mapView.projection.fromPixels(e.x.toInt(), e.y.toInt())
                Thread {
                    try {
                        val geocoder = GeocoderNominatim("Taller2")
                        val locations = geocoder.getFromLocation(
                            longClickLocation.latitude,
                            longClickLocation.longitude,
                            1
                        )
                        if (locations.isNotEmpty()) {
                            val location = locations[0]
                            val address = location.thoroughfare
                            val marker = Marker(mapView)
                            marker.position = longClickLocation as GeoPoint?
                            marker.title = address
                            mapView.overlays.add(marker)
                            mapView.invalidate()
                            runOnUiThread {
                                showToast("Marcador creado en $address")
                            }
                        } else {
                            runOnUiThread {
                                showToast("No se pudo obtener la dirección")
                            }
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            showToast("Error al obtener la dirección: ${e.message}")
                        }
                    }
                }.start()
            }
        })
        mapView.setOnTouchListener { _, event ->
            longClickDetector.onTouchEvent(event)
            false
        }

        //polyline de seguimiento
        startLocationUpdatesAtInterval()
    }

    fun initComponents(){
        casaicono= findViewById(R.id.casaicono)

    }

    fun initListeners(){
    casaicono.setOnClickListener {
        // Crear un Intent para abrir la nueva actividad
        val intent = Intent(this, Categorias1Activity::class.java)
        // Iniciar la nueva actividad
        startActivity(intent)
    }
    }
    private fun setupMapWithLocation() {
        // Configura el proveedor de ubicación
        val locationProvider = GpsMyLocationProvider(this)
        locationProvider.addLocationSource(LocationManager.NETWORK_PROVIDER)
        myLocationOverlay = MyLocationNewOverlay(locationProvider, mapView)
        myLocationOverlay.enableMyLocation()

        // Cambia el icono del marcador a amarillo
        val yellowMarkerBitmap =
            (ContextCompat.getDrawable(this, R.drawable.marker) as BitmapDrawable).bitmap

        // Ajusta el anclaje del marcador al centro inferior (si es necesario)
        val marker = Marker(mapView)
        marker.icon = BitmapDrawable(resources, yellowMarkerBitmap)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM) // Ajusta el anclaje

        mapView.overlays.add(myLocationOverlay)

        // Llama a runOnFirstFix para centrar el mapa en tu ubicación actual cuando esté disponible
        myLocationOverlay.runOnFirstFix(Runnable {
            val location = myLocationOverlay.myLocation
            if (location != null) {
                // Eliminar cualquier marcador existente en la ubicación actual
                mapView.overlays.removeAll { it is Marker }

                // Mostrar la ubicación actual en un nuevo marcador
                marker.position = org.osmdroid.util.GeoPoint(location)
                mapView.overlays.add(marker)

                // Obtener el nombre del lugar más cercano en un hilo separado
                Executors.newSingleThreadExecutor().execute {
                    val geocoder = GeocoderNominatim("Taller2")
                    val closestPlace = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )
                    if (closestPlace.isNotEmpty()) {
                        val placeName =
                            closestPlace[0].locality
                        runOnUiThread {
                            marker.title = placeName
                            mapController.animateTo(marker.position)
                            mapView.invalidate()
                        }
                    }
                }
            }
        })
        //PRUEBA
        // Añadir marcas en puntos específicos
        val puntos = ArrayList<OverlayItem>()
        val madrid = org.osmdroid.util.GeoPoint(40.4167047, -3.7035825) // Ejemplo: Madrid
        puntos.add(OverlayItem("Madrid", "Ciudad de Madrid", madrid))

        val capa = ItemizedIconOverlay<OverlayItem>(this, puntos, null)
        mapView.overlays.add(capa)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupMapWithLocation()
            } else {
                // Permiso de ubicación denegado
                Toast.makeText(
                    this,
                    "El permiso de ubicación es necesario para mostrar tu ubicación actual en el mapa.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    //UBICACION CON EL BUTON
    private fun createMarkerFromAddress(address: String) {
        runBlocking {
            launch(Dispatchers.IO) {
                try {
                    val geocoder = GeocoderNominatim("Taller2")
                    val locations = geocoder.getFromLocationName(address, 1)
                    if (locations.isNotEmpty()) {
                        val location = locations[0]
                        val geoPoint = GeoPoint(location.latitude, location.longitude)

                        // Crea un marcador en el mapa
                        val marker = Marker(mapView)
                        marker.position = geoPoint
                        marker.title = address

                        mapView.overlays.add(marker)

                        // Centra el mapa en la ubicación del marcador en el hilo principal
                        runOnUiThread {
                            mapController.animateTo(geoPoint)
                            mapView.invalidate()
                        }

                        buildRouteToMarker(geoPoint)
                    } else {
                        Log.d(TAG, "createMarkerFromAddress: Dirección '$address' no encontrada")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error al buscar la dirección: ${e.message}", e)
                }
            }
        }
    }
    private fun buildRouteToMarker(destinationPoint: GeoPoint) {
        // Obtén la ubicación actual del usuario
        val startPoint = myLocationOverlay.myLocation

        if (startPoint != null) {
            val roadManager = OSRMRoadManager(this,"Taller2")
            val road = roadManager.getRoad(arrayListOf(startPoint, destinationPoint))

            if (road.mStatus == Road.STATUS_OK) {
                val roadOverlay = RoadManager.buildRoadOverlay(road)
                mapView.overlays.add(roadOverlay)
                mapView.postInvalidate()
            } else {
                showToast("No se pudo encontrar una ruta")
            }
        } else {
            showToast("No se pudo obtener la ubicación actual del usuario")
        }
    }
    override fun onLocationChanged(location: Location?, source: IMyLocationProvider?) {
        // La ubicación del usuario ha cambiado, actualiza la ruta
        val geoPoint = location?.let { GeoPoint(it.latitude, location.longitude) }
        if (geoPoint != null) {
            buildRouteToMarker(geoPoint)
            userPath.add(geoPoint)
            updatePolyline()
        }
        geoPoint?.let { userPath.add(it) }
        updatePolyline()
        val newPoint = GeoPoint(geoPoint)
        routePoints.add(newPoint)
        mapView.invalidate()
    }
    private fun updatePolyline() {
        //  mapView.overlayManager.removeAll { it is Polyline }

        if (routePoints.size >= 2) {
            val polyline = Polyline()
            polyline.setPoints(routePoints)

            val paint = polyline.paint
            paint.color = Color.RED
            paint.strokeWidth = 10f
            mapView.overlayManager.add(polyline)
        }

        mapView.invalidate()
    }
    //ACTUALIZACION INTERVALOS
    private fun startLocationUpdates() {
        myLocationOverlay.runOnFirstFix {
            val startPoint = myLocationOverlay.myLocation
            startPoint?.let {
                routePoints.add(it)
                updatePolyline()
            }
        }
    }
    private val locationUpdateRunnable = object : Runnable {
        override fun run() {
            startLocationUpdates()
            locationHandler.postDelayed(this, locationUpdateInterval.toLong())
        }
    }

    private fun startLocationUpdatesAtInterval() {
        startLocationUpdates()
        locationHandler.postDelayed(locationUpdateRunnable, locationUpdateInterval.toLong())
    }

    // Función para mostrar un Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //funcion sensor
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_LIGHT) {
                val lightValue = it.values[0]
                Log.d("LightSensor", "Light value: $lightValue")
                if (lightValue < 5) {
                    Log.d("LightSensor", "Low light condition")
                    mapView.overlayManager.tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS)
                } else {
                    Log.d("LightSensor", "High light condition")
                    mapView.overlayManager.tilesOverlay.setColorFilter(null)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No necesitamos implementar esto
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        val uiManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
        if (uiManager.nightMode !== UiModeManager.MODE_NIGHT_YES) {
            mapView.overlayManager.tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS)
        }
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
        TODO("Not yet implemented")
    }

    override fun longPressHelper(p: GeoPoint?): Boolean {
        TODO("Not yet implemented")
    }

}