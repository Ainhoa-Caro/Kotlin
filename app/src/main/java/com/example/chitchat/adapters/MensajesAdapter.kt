package com.example.chitchat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchat.R
import com.example.chitchat.pojos.Mensaje
import kotlinx.android.synthetic.main.item_mensaje.view.*

//El adaptador se encargará de crear y asociar datos a cada ViewHolder(mensaje) dentro del RecyclerView.
//Recibe por parámetro el Id del user Logueado.
class MensajesAdapter(private val userLogueadoId: String): RecyclerView.Adapter<MensajesAdapter.MensajeViewHolder>() {

    //Creamos lista donde posteriormente se guardarán todos objetos Mensaje.
    private var listaMensajes: List<Mensaje> = emptyList()

    //
    fun anadirDatos(list: List<Mensaje>){
        listaMensajes = list
        //IMPORTANTE !!
        // Notifica a los observadores adjuntos que los datos se han modificado
        // y que cualquier Vista que refleje el conjunto de datos debe actualizarse.
        notifyDataSetChanged()
    }

    //Este método creará una nueva vista(un nuevo objeto ViewHolder) llamando a su constructor "MensajeViewHolder".
    //Será invocado por el LayoutManager cada vez que se necesite añadir un nuevo mensaje a la conversación.
    //Devolvemos el nuevo objeto ViewHolder, es decir, un nuevo objeto representado visualmente con item_Mensaje.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        return MensajeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_mensaje,
                parent,
                false
            )
        )
    }

    //Método encargado de asignarle a cada ViewHolder(contenedor de un mensaje) los datos que tiene que mostrar.
    //Será invocado por el LayoutManager cada vez que se necesite añadir un nuevo mensaje a la conversación
    // y en cada actualización será llamado tantas veces como ViewHolders(mensajes) existan dentro de una conversación.
    //Le llega cómo parámetros:
    //  -El ViewHolder, es decir, el contenedor del mensaje.
    //  -La posición de cada objeto Mensaje dentro de la lista de mensajes.
    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        //Obtenemos el nuevo mensaje.
        val objMensaje = listaMensajes[position]

        //Ahora...cómo cada ViewHolder(item_Mensaje) contiene dos TextView uno para "mis propios mensajes (derecha)"
        // y otro para "mensajes externos(izquierda)", debemos elegir cual mostraremos en cada caso.

        //Si el userLogueado es el emisor del mensaje...
        if(userLogueadoId == objMensaje.emisorId){
            //Mostraremos solo el TextView de la derecha (miMensajeLayout).
            holder.itemView.miMensajeLayout.visibility = View.VISIBLE
            holder.itemView.extMensajeLayout.visibility = View.GONE
            //Añadiremos el texto al TextView(miMensajeLayout).
            holder.itemView.miMensajeTextView.text = objMensaje.contenidoMensaje

        } else { //Si el userLogueado NO es el emisor del mensaje...
            //Mostraremos solo el TextView de la izquierda (extMensajeLayout).
            holder.itemView.miMensajeLayout.visibility = View.GONE
            holder.itemView.extMensajeLayout.visibility = View.VISIBLE
            //Añadiremos el texto al TextView(extMensajeLayout).
            holder.itemView.extMensajeTextView.text = objMensaje.contenidoMensaje
        }
    }

    //Este método nos devuelve el tamaño del conjunto de elementos(mensajes) a mostrar.
    //RecyclerView lo usa para determinar cuándo no hay más elementos que se puedan mostrar
    // y así decidir el número de veces que debe invocar al método "onBindViewHolder".
    override fun getItemCount(): Int {
        return listaMensajes.size
    }

    //Constructor del ViewHolder (contenedor de cada mensaje).
    //Es llamado desde el método "onCreateViewHolder".
    //Recibe por parámetro un View(enlace Kotlin-XML).
    //Devuelve un ViewHolder asociado a su Layout (item_Mensaje.xml)
    class MensajeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}