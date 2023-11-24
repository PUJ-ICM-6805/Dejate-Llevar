package com.example.dejatellevar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import java.util.Calendar
import java.util.Date

class ContratoEmpActivity : AppCompatActivity() {

    private lateinit var atras: ImageView
    private lateinit var fecha: Date

    //ediciones de texto
    private lateinit var editTituloContratoEmp : EditText
    private lateinit var editDescrpContratoEmp : EditText //descripcion del contrato con tiktoker
    private lateinit var editFechaEntregaContratoEmp : DatePicker
    private lateinit var editCiudadContratoEmp : EditText
    private lateinit var editDireccionContratoEmp : EditText
    private lateinit var editEstiloContratoEmp : EditText
    private lateinit var editAlcanceContratoEmp : EditText
    private lateinit var editValorContratoEmp : EditText

    //boton de envio
    private lateinit var btnEnvioContratoEmp : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contratoemp)

        init_elementos()

        init_listeners()


    }

    fun init_elementos(){
        editTituloContratoEmp = findViewById(R.id.editTituloContratoEmp)
        editDescrpContratoEmp = findViewById(R.id.editDescContratoEmp)
        editFechaEntregaContratoEmp = findViewById(R.id.editFechaContratoEmp)
        editCiudadContratoEmp = findViewById(R.id.editCiudadContratoEmp)
        editDireccionContratoEmp = findViewById(R.id.editDireccionContratoEmp)
        editEstiloContratoEmp = findViewById(R.id.editEstiloContratoEmp)
        editAlcanceContratoEmp = findViewById(R.id.editAlcanceContratoEmp)
        editValorContratoEmp = findViewById(R.id.editValorContratoEmp)
        atras = findViewById(R.id.imageViewBack)
        btnEnvioContratoEmp = findViewById(R.id.solicitudBtn)
    }

    private fun init_listeners(){

        atras.setOnClickListener {
            val intent = Intent(this, InicioTikTok::class.java)
            startActivity(intent)
        }

        btnEnvioContratoEmp.setOnClickListener{

            processSelectedDate(editFechaEntregaContratoEmp.year,
                                editFechaEntregaContratoEmp.month,
                                editFechaEntregaContratoEmp.dayOfMonth)

            try{

                val solicitud = Solicitud(  editTituloContratoEmp.text.toString(),
                    editDescrpContratoEmp.text.toString(),
                    fecha,
                    editValorContratoEmp.text.toString(),
                    editEstiloContratoEmp.text.toString()
                )

                if(SolicitudHolder.solicitudList == null){
                    SolicitudHolder.solicitudList = ArrayList()
                }

                SolicitudHolder.solicitudList?.add(solicitud)
                Toast.makeText(this, "Solicitud creada correctamente", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, InicioTikTok::class.java)
                startActivity(intent)

            }catch(e: Exception){
                e.printStackTrace()
                Toast.makeText(this, "Error al crear la solicitud", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun processSelectedDate(year: Int, month: Int, day: Int) {
        val selectedDate = createDateObject(year, month, day)
        fecha = selectedDate
    }

    private fun createDateObject(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }
}