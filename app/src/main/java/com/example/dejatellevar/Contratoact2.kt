package com.example.dejatellevar

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPTable
import java.io.OutputStream

import android.provider.ContactsContract

import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Contratoact2 : AppCompatActivity() {
    private lateinit var atras: ImageView
    private lateinit var contactos: ImageView

    // Declaración de variables para los EditText
    private lateinit var editNombreContrato: EditText
    private lateinit var editTiktokContrato: EditText
    private lateinit var editNitContrato: EditText
    private lateinit var editBudgetContrato: EditText
    private lateinit var editCelular: EditText

    // declarar un boton
    private lateinit var btnIngresar: Button

    private lateinit var btnSolicitudes: Button

    private lateinit var createFileLauncher: ActivityResultLauncher<Intent>

    private lateinit var contactPickerLauncher: ActivityResultLauncher<Intent>

    private val REQUEST_READ_CONTACTS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contratoact2)
        initComponents()

        createFileLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val uri: Uri? = result.data?.data
                    uri?.let {
                        extraerInformacion(it)
                    }
                } else {
                    Toast.makeText(this, "Archivo no creado", Toast.LENGTH_SHORT).show()
                }
            }

        contactPickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val contactUri = result.data?.data
                    val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val cursor = contactUri?.let { uri ->
                        contentResolver.query(uri, projection, null, null, null)
                    }
                    cursor?.let {
                        if (it.moveToFirst()) {
                            val numberIndex =
                                it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            val number = it.getString(numberIndex)
                            editCelular.setText(number)
                        } else {
                            Toast.makeText(this, "No se pudo obtener el número", Toast.LENGTH_SHORT)
                                .show()
                        }
                        it.close()
                    }
                }
            }


        initListeners()
    }

    private fun requestContactPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_READ_CONTACTS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_READ_CONTACTS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permiso concedido
                } else {
                    // Permiso denegado
                    Toast.makeText(this, "Permiso denegado para leer contactos", Toast.LENGTH_SHORT)
                        .show()
                }
                return
            }

            else -> {
                // Ignore all other requests.
            }
        }
    }

    fun initComponents() {
        // identificar id de una imagen botton
        atras = findViewById(R.id.imageView15)
        editNombreContrato = findViewById(R.id.editNombreContrato)
        editTiktokContrato = findViewById(R.id.editTiktokContrato)
        editNitContrato = findViewById(R.id.editNitContrato)
        editBudgetContrato = findViewById(R.id.editBudgetContrato)
        editCelular = findViewById(R.id.editCelular)
        btnIngresar = findViewById(R.id.generarContrato)
        contactos = findViewById(R.id.imageView14)
        btnSolicitudes = findViewById(R.id.solicitudBtn)
    }

    private fun initListeners() {

        atras.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, InicioTikTok::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        btnIngresar.setOnClickListener {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/pdf"
                putExtra(Intent.EXTRA_TITLE, "contrato.pdf")
            }
            createFileLauncher.launch(intent)
        }


        contactos.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_READ_CONTACTS)
            } else {
                launchContactPicker()
            }
        }

        btnSolicitudes.setOnClickListener{

        }


    }

    private fun launchContactPicker() {
        val contactPickerIntent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
        contactPickerLauncher.launch(contactPickerIntent)
    }

    private fun extraerInformacion(uri: Uri) {
        val nombreContrato = editNombreContrato.text.toString()
        val tiktokContrato = editTiktokContrato.text.toString()
        val nitContrato = editNitContrato.text.toString()
        val budgetContrato = editBudgetContrato.text.toString()
        val celular = editCelular.text.toString()
        generarPDF(nombreContrato, tiktokContrato, nitContrato, budgetContrato, celular, uri)
    }

    private fun generarPDF(
        nombreContrato: String,
        tiktokContrato: String,
        nitContrato: String,
        budgetContrato: String,
        celular: String,
        uri: Uri
    ) {
        val document = Document()

        try {
            val outputStream: OutputStream = contentResolver.openOutputStream(uri)!!
            val pdfWriter = PdfWriter.getInstance(document, outputStream)

            document.open()

            // Fuentes personalizadas
            val fontTitle = Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD)
            val fontContent = Font(Font.FontFamily.HELVETICA, 12f, Font.NORMAL)

            // Título
            val title = Paragraph("Contrato con Empresa", fontTitle)
            title.alignment = Paragraph.ALIGN_CENTER
            document.add(title)

            // Tabla
            val table = PdfPTable(2)  // 2 columnas
            table.addCell(Paragraph("Nombre", fontContent))
            table.addCell(Paragraph(nombreContrato, fontContent))
            table.addCell(Paragraph("Link TikTok", fontContent))
            table.addCell(Paragraph(tiktokContrato, fontContent))
            table.addCell(Paragraph("NIT", fontContent))
            table.addCell(Paragraph(nitContrato, fontContent))
            table.addCell(Paragraph("Presupuesto", fontContent))
            table.addCell(Paragraph(budgetContrato, fontContent))
            table.addCell(Paragraph("Número Celular", fontContent))
            table.addCell(Paragraph(celular, fontContent))
            document.add(table)

            // Texto legal
            val legalText = """
                Este es un texto legal de ejemplo. Las partes acuerdan lo siguiente:
                1. La empresa proporcionará los servicios descritos en este contrato de acuerdo con los términos y condiciones establecidos.
                2. El contratista cumplirá con todas las leyes y regulaciones aplicables.
                3. Este contrato es confidencial y cualquier divulgación de la información contenida en él está prohibida.
                ...
            """.trimIndent()
            document.add(Paragraph(legalText, fontContent))

            document.close()

            Toast.makeText(this, "PDF creado y guardado", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al crear PDF: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }


}