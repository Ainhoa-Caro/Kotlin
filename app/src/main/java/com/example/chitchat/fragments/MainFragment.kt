package com.example.chitchat

import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    //Inicializacion de las animaciones del fab button
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(
        context,
        R.anim.rotate_open_animation
    ) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(
        context,
        R.anim.rotate_close_animation
    ) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(
        context,
        R.anim.from_bottom_animation
    ) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(
        context,
        R.anim.to_bottom_animation
    ) }

    //Variable clicked determina si el fab de nuevo chat esta clickado o no
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Tell the fragment it has an option menu
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)


        val fab_nuevaAccion = root.findViewById<View>(R.id.fab_nuevaAccion)
        val fab_nuevoChat = root.findViewById<View>(R.id.fab_nuevochat)
        val fab_nuevoGrupo = root.findViewById<View>(R.id.fab_nuevogrupo)
        val fab_nuevaSala = root.findViewById<View>(R.id.fab_nuevasala)

        fab_nuevaAccion.setOnClickListener{
            onNewButtonClicked()
        }

        fab_nuevoChat.setOnClickListener{
            Toast.makeText(context, "Crear nuevo chat 1 a 1", Toast.LENGTH_SHORT).show()
            //Navegar a la ventana de contactos para crear el chat
        }

        fab_nuevoGrupo.setOnClickListener{
            Toast.makeText(context, "Crear nuevo grupo", Toast.LENGTH_SHORT).show()
            //Navega a la ventana de creacion de grupo y a continuacion a la de contactos para seleccionar los integrantes del grupo
        }

        fab_nuevaSala.setOnClickListener{
            //Navega a la ventana de creacion de sala para crear una nueva sala.
            Toast.makeText(context, "Crear nueva sala", Toast.LENGTH_SHORT).show()
        }

        //Conectas la actividad con el drawer layout para poder usarlo
        val drawerLayout: DrawerLayout = root.findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(requireActivity(), drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(toggle)

        //Onclick de las opciones del menu desplegable
        val navIzq: NavigationView = root.findViewById(R.id.drawer_izq)
        navIzq.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.abrirPerfilConfig -> //Abrir config del perfil
                    Toast.makeText(context, "Abrir perfil", Toast.LENGTH_SHORT).show()

                R.id.abrirAppConfig -> //Abrir configuracion de la app o settings (Ainhoa)
                    Toast.makeText(context, "Abrir configuracion", Toast.LENGTH_SHORT).show()

                R.id.abrirAyuda -> //Abrir la ayuda de la app
                    Toast.makeText(context, "Abrir ayuda", Toast.LENGTH_SHORT).show()

                R.id.cerrarSesion -> //Cerrar sesion
                    Toast.makeText(context, "Has cerrado sesion", Toast.LENGTH_SHORT).show()

            }
            true
        }

        val abrirOpcionesButton = root.findViewById<View>(R.id.imageButtonOpenOptionDrawer)
        abrirOpcionesButton.setOnClickListener { //Abrir el drawer layout
            drawerLayout.openDrawer(root.findViewById<View>(R.id.drawer_izq))
        }
        
        return root
    }

    fun onNewButtonClicked(){
        setVisibility(clicked)
        setAnimation(clicked)

        //Cambia el estado de la variable clicked (Si no estaba clickado se pone a true y si lo estaba a false)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            fab_nuevaAccion.startAnimation(rotateOpen)
            fab_nuevogrupo.startAnimation(fromBottom)
            fab_nuevochat.startAnimation(fromBottom)
            fab_nuevasala.startAnimation(fromBottom)
        } else {
            fab_nuevaAccion.startAnimation(rotateClose)
            fab_nuevogrupo.startAnimation(toBottom)
            fab_nuevochat.startAnimation(toBottom)
            fab_nuevasala.startAnimation(toBottom)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            fab_nuevasala.visibility = View.VISIBLE
            fab_nuevochat.visibility = View.VISIBLE
            fab_nuevogrupo.visibility = View.VISIBLE
        } else {
            fab_nuevasala.visibility = View.INVISIBLE
            fab_nuevochat.visibility = View.INVISIBLE
            fab_nuevogrupo.visibility = View.INVISIBLE
        }
    }

}