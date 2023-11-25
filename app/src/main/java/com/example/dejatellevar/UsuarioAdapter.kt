// UsuarioAdapter.kt
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.dejatellevar.Chat
import com.example.dejatellevar.ChatMensaje
import com.example.dejatellevar.R

class UsuarioAdapter(context: Context, usuarios: List<Chat.Usuario>) : ArrayAdapter<Chat.Usuario>(context, 0, usuarios) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(context)
        val view = convertView ?: layoutInflater.inflate(R.layout.item_usuario, parent, false)

        val usuario = getItem(position)

        val textViewNombre = view.findViewById<TextView>(R.id.textViewNombreUsuario)
        textViewNombre.text = usuario?.nombre

        // Manejador de clics para el elemento de la lista
        view.setOnClickListener {
            val intent = Intent(context, ChatMensaje::class.java)
            intent.putExtra("usuarioID", usuario?.id) // Asegúrate de que tu clase Usuario tenga un campo 'id'
            context.startActivity(intent)
        }

        return view
    }
}
