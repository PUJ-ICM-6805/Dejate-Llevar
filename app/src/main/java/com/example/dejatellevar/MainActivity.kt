package com.example.dejatellevar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.biometric.BiometricPrompt
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
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
    lateinit var textView11: TextView
    lateinit var empresa: ImageView
    private lateinit var auth: FirebaseAuth
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        initComponents()
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Maneja el error de autenticación
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d("BiometricAuth", "onAuthenticationSucceeded called")
                    Toast.makeText(this@MainActivity, "Autenticación exitosa", Toast.LENGTH_SHORT).show()

                    // Añadir notificación
                    val channelId = "mi_canal_notificaciones"
                    val channelName = "Notificaciones de inicio de sesión"

                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
                        notificationManager.createNotificationChannel(channel)
                    }

                    val notificationBuilder = NotificationCompat.Builder(this@MainActivity, channelId)
                        .setSmallIcon(R.drawable.notificacion)
                        .setContentTitle("Inicio de sesión exitoso")
                        .setContentText("¡Bienvenido de nuevo!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    notificationManager.notify(1, notificationBuilder.build())

                    val intent = Intent(this@MainActivity, InicioTikTok::class.java)
                    startActivity(intent)
                }



                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // La autenticación falló, maneja el error aquí
                    Toast.makeText(this@MainActivity, "La autenticación ha fallado", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación biométrica")
            .setSubtitle("Confirma tu identidad con tu huella dactilar")
            .setNegativeButtonText("Cancelar")
            .build()
        initListeners()
        auth = FirebaseAuth.getInstance()

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    imageView2.visibility = View.VISIBLE
                } else {
                    imageView2.visibility = View.GONE
                }
            }
        })

        editTextTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No necesitamos hacer nada antes de cambiar el texto
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No necesitamos hacer nada cuando el texto cambia
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    imageView3.visibility = View.VISIBLE
                } else {
                    imageView3.visibility = View.GONE
                }
            }
        })

    }

    private fun initComponents() {
        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)
        editTextTextPassword = findViewById(R.id.editTextTextPassword)
        loginButton = findViewById(R.id.loginButton)
        signupButton = findViewById(R.id.signupButton)
        login = findViewById(R.id.login)
        imageView2 = findViewById(R.id.imageView2)
        imageView3 = findViewById(R.id.imageView3)
        imageView4 = findViewById(R.id.imageView4)
        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        textView11 = findViewById(R.id.textView11)
        empresa = findViewById(R.id.empresa)
    }

    private fun initListeners() {
        loginButton.setOnClickListener {
            Toast.makeText(this, ":) Ya estás en Login", Toast.LENGTH_SHORT).show()
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            val email = editText.text.toString()
            val password = editTextTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                iniciarSesion(email, password)
            } else {
                Toast.makeText(this, "Por favor, ingresa un correo y una contraseña válidos", Toast.LENGTH_SHORT).show()
            }
        }
        imageView4.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

        empresa.setOnClickListener {
            val intent = Intent(this, login_empresa::class.java)
            startActivity(intent)
        }
    }

    private fun iniciarSesion(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Usuario ha iniciado sesión con éxito
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, ":) Bienvenido", Toast.LENGTH_SHORT).show()

                    // Obtener información del usuario
                    user?.let {
                        obtenerInformacionUsuario(it.uid, object : UsuarioJsonCallback {
                            override fun onCallback(json: String?) {
                                if (json != null) {
                                    // si el rol es cliente
                                    if (JSONObject(json).getString("rol") == "cliente") {
                                        val intent = Intent(this@MainActivity, Categorias1Activity::class.java)
                                        // pasar el id del usuario
                                        startActivity(intent)
                                    }
                                    else if (JSONObject(json).getString("rol") == "tiktok") {
                                        val intent = Intent(this@MainActivity, InicioTikTok::class.java)
                                        intent.putExtra("usuarioID", it.uid)
                                        Toast.makeText(this@MainActivity, "usuarioID: ${it.uid}", Toast.LENGTH_SHORT).show()
                                        startActivity(intent)
                                    }
                                    else if (JSONObject(json).getString("rol") == "empresa") {
                                        val intent = Intent(this@MainActivity, login_empresa::class.java)
                                        startActivity(intent)
                                    }

                                    else {
                                        Toast.makeText(this@MainActivity, "No tiene permisos", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(this@MainActivity, "Error al obtener información del usuario", Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                    }


                } else {
                    // Manejar errores de inicio de sesión
                    manejarErroresInicioSesion(task.exception)
                }
            }
    }

    private fun obtenerInformacionUsuario(uid: String, callback: UsuarioJsonCallback) {
        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val json = JSONObject(document.data).toString()
                    callback.onCallback(json)
                } else {
                    Log.d("LoginActivity", "No such document")
                    callback.onCallback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("LoginActivity", "get failed with ", exception)
                callback.onCallback(null)
            }
    }


    interface UsuarioJsonCallback {
        fun onCallback(json: String?)
    }



    private fun manejarErroresInicioSesion(exception: Exception?) {
        val errorMessage = exception?.message ?: "Error de inicio de sesión"
        if (errorMessage.contains("wrong password", ignoreCase = true)) {
            textView11.text = "Contraseña inválida"
        } else if (errorMessage.contains("no user record", ignoreCase = true)) {
            textView11.text = "Usuario inválido"
        } else {
            textView11.text = "Error: Usuario inválido"
        }
        textView11.setTextColor(Color.RED)
    }

}
