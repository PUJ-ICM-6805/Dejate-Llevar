package com.example.dejatellevar

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.initialize
import java.text.SimpleDateFormat
import java.util.Locale


class RegistrarActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var editText: EditText
    lateinit var editTextTextPassword: EditText
    lateinit var loginButton: Button
    lateinit var signupButton: Button
    lateinit var login: Button
    lateinit var imageView2: ImageView
    lateinit var imageView3: ImageView
    lateinit var imageView4: ImageView
    lateinit var textView: TextView
    lateinit var textView2: TextView
    lateinit var TextNombre: EditText
    lateinit var editApellido: EditText
    lateinit var editCalendario: EditText
    lateinit var spinnerGenero: Spinner
    lateinit var editUsername: EditText
    lateinit var editTPassword: EditText
    lateinit var registrarse: Button
    lateinit var terminosYcondiciones: TextView
    lateinit var imageCalendario: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        initComponents()
        initListeners()

    }

    private fun initComponents() {
        loginButton = findViewById(R.id.loginButton)
        signupButton = findViewById(R.id.signupButton)
        registrarse = findViewById(R.id.registrarse)
        imageCalendario = findViewById(R.id.imageCalendario)
        editCalendario = findViewById(R.id.editCalendario)
        editUsername = findViewById(R.id.editUsername)
        editTPassword = findViewById(R.id.editTPassword)
        TextNombre = findViewById(R.id.TextNombre)
        editApellido = findViewById(R.id.editApellido)
        spinnerGenero = findViewById(R.id.spinnerGenero)

    }

    private fun initListeners() {

        signupButton.setOnClickListener{
            Toast.makeText(this, ":) Ya estás en Registrar", Toast.LENGTH_SHORT).show()
        }
        loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        registrarse.setOnClickListener {
            val nombre = TextNombre.text.toString().trim()
            val apellido = editApellido.text.toString().trim()
            val fechaNacimiento = editCalendario.text.toString().trim()
            val genero = spinnerGenero.selectedItem.toString()
            val email = editUsername.text.toString()
            val password = editTPassword.text.toString()

            if (camposRegistroValidos(nombre, fechaNacimiento, genero) && camposCredencialesValidos(email, password)) {
                if (esMayorDeEdad(fechaNacimiento)) {
                    registrarUsuario(email, password, nombre, apellido, fechaNacimiento, genero)
                } else {
                    Toast.makeText(this, "Debes ser mayor de edad para registrarte", Toast.LENGTH_SHORT).show()
                }
            }
        }

        imageCalendario.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val anio: Int = cal.get(Calendar.YEAR)
            val mes: Int = cal.get(Calendar.MONTH)
            val dia: Int = cal.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { view, year, month, dayOfMonth ->
                val fecha = "$dayOfMonth/$month/$year"
                val editableFecha = Editable.Factory.getInstance().newEditable(fecha)
                editCalendario.text = editableFecha
            }, anio, mes, dia)
            dpd.show()
        }
    }
    private fun esMayorDeEdad(fechaNacimiento: String): Boolean {
        val fechaNacimientoFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaNacimientoDate = fechaNacimientoFormat.parse(fechaNacimiento)
        val calendarNacimiento = Calendar.getInstance()
        calendarNacimiento.time = fechaNacimientoDate

        val calendarHoy = Calendar.getInstance()
        var edad = calendarHoy.get(Calendar.YEAR) - calendarNacimiento.get(Calendar.YEAR)

        if (calendarHoy.get(Calendar.DAY_OF_YEAR) < calendarNacimiento.get(Calendar.DAY_OF_YEAR)) {
            edad--
        }

        val edadRequerida = 18
        return edad >= edadRequerida
    }
    private fun camposRegistroValidos(nombre: String,fechaNacimiento: String, genero: String): Boolean {
        if (nombre.isEmpty() || fechaNacimiento.isEmpty() || genero.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun camposCredencialesValidos(email: String, password: String): Boolean {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            return true
        } else {
            Toast.makeText(this, "Por favor, ingresa un correo y una contraseña válidos", Toast.LENGTH_SHORT).show()
            return false
        }
    }
    private fun registrarUsuario(email: String, password: String, nombre: String, apellido: String, fechaNacimiento: String, genero: String) {
        val auth = FirebaseAuth.getInstance()
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result != null && result.signInMethods?.isEmpty() == true) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { registrationTask ->
                                if (registrationTask.isSuccessful) {
                                    // Registro exitoso, guardar datos en Firestore
                                    val user = Firebase.auth.currentUser
                                    val uid = user?.uid

                                    val db = Firebase.firestore
                                    val usuario = hashMapOf(
                                        "idUsuario" to uid, // Se inicializa automáticamente con el UID del usuario
                                        "nombre" to nombre,
                                        "apellido" to apellido,
                                        "fechaNacimiento" to fechaNacimiento,
                                        "genero" to genero,
                                        "email" to email,
                                        "foto" to "", // Inicializar con una cadena vacía
                                        "ubicacion" to hashMapOf("latitud" to "", "longitud" to ""), // Inicializar con latitud y longitud vacías
                                        "preferencia" to "",
                                        "historialReservas" to emptyList<String>(), // Inicializar como una lista vacía de cadenas
                                        "favoritos" to emptyList<String>(), // Inicializar como una lista vacía de cadenas
                                        "comentarios" to emptyList<String>(),
                                        "estadoReservas" to false
                                    )

                                    if (uid != null) {
                                        db.collection("usuarios")
                                            .document(uid)
                                            .set(usuario)
                                            .addOnSuccessListener {
                                                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                                // Crear un Intent para abrir la nueva actividad
                                                val intent = Intent(this, MainActivity::class.java)
                                                startActivity(intent)
                                            }
                                            .addOnFailureListener { e ->
                                                Toast.makeText(this, "Error al guardar los datos en Firestore", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                } else {
                                    val errorMessage = registrationTask.exception?.message
                                        ?: "Error al registrar el usuario"
                                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show()

                    }
                } else {
                    val errorMessage = task.exception?.message ?: "Error al verificar el correo electrónico"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun calcularEdad(fechaNacimiento: String): Int {
        val fechaNacimientoFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaNacimientoDate = fechaNacimientoFormat.parse(fechaNacimiento)
        val calendarNacimiento = Calendar.getInstance()
        calendarNacimiento.time = fechaNacimientoDate

        val calendarHoy = Calendar.getInstance()
        var edad = calendarHoy.get(Calendar.YEAR) - calendarNacimiento.get(Calendar.YEAR)

        if (calendarHoy.get(Calendar.DAY_OF_YEAR) < calendarNacimiento.get(Calendar.DAY_OF_YEAR)) {
            edad--
        }

        return edad
    }



}