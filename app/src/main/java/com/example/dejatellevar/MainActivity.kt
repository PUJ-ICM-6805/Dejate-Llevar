package com.example.dejatellevar

import android.content.Intent
import android.graphics.Color
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
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

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
    }

    private fun iniciarSesion(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, ":) Bienvenido", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Categorias1Activity::class.java)
                    startActivity(intent)
                } else {
                    val errorMessage = task.exception?.message ?: "Error de inicio de sesión"
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
    }

}
