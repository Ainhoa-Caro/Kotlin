package com.example.chitchat.adaptadores

class MensajeAdapter(private val user: String): RecyclerView.Adapter<MensajeAdapter.MessageViewHolder>() {

    private var mensajes: List<Mensaje> = emptyList()

    fun setData(list: List<Mensaje>){
        mensajes = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_mensaje,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = mensajes[position]

        if(user == message.from){
            holder.itemView.miMensajeLayout.visibility = View.VISIBLE
            holder.itemView.extMensajeLayout.visibility = View.GONE

            holder.itemView.miMensajeTextView.text = message.message
        } else {
            holder.itemView.miMensajeLayout.visibility = View.GONE
            holder.itemView.extMensajeLayout.visibility = View.VISIBLE

            holder.itemView.extMensajeTextView.text = message.message
        }

    }

    override fun getItemCount(): Int {
        return mensajes.size
    }

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}