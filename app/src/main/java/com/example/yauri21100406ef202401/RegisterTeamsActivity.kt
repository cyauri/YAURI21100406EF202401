package com.example.yauri21100406ef202401

// Importaciones necesarias
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterTeamsActivity : AppCompatActivity() {

    private lateinit var editTextTeamName: EditText
    private lateinit var editTextTeamLogoUrl: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonRegisterMatchups: Button
    private lateinit var buttonListMatchups: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_teams)

        // Inicializar Firestore
        db = Firebase.firestore

        // Obtener referencias a las vistas
        editTextTeamName = findViewById(R.id.editTextTeamName)
        editTextTeamLogoUrl = findViewById(R.id.editTextTeamLogoUrl)
        buttonSave = findViewById(R.id.buttonSave)
        buttonRegisterMatchups = findViewById(R.id.buttonRegisterMatchups)
        buttonListMatchups = findViewById(R.id.buttonListMatchups)

        // Configurar el botón Guardar
        buttonSave.setOnClickListener {
            saveTeam()
        }

        // Configurar los botones de navegación
        buttonRegisterMatchups.setOnClickListener {
            // Redireccionar a la pantalla del Caso 02
            val intent = Intent(this, RegisterTeamsActivity::class.java)
            startActivity(intent)
        }

        buttonListMatchups.setOnClickListener {
            // Redireccionar a la pantalla del Caso 03
            val intent = Intent(this, ListaEnfrentamientosActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveTeam() {
        val teamName = editTextTeamName.text.toString().trim()
        val teamLogoUrl = editTextTeamLogoUrl.text.toString().trim()

        if (teamName.isEmpty() || teamLogoUrl.isEmpty()) {
            // Validar los campos
            // Podrías mostrar un mensaje de error aquí
            return
        }

        // Crear un mapa con los datos del equipo
        val team = hashMapOf(
            "name" to teamName,
            "logoUrl" to teamLogoUrl
        )

        // Guardar en Firestore
        db.collection("teams").add(team)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Éxito
                    // Limpiar los campos o mostrar un mensaje
                    editTextTeamName.text.clear()
                    editTextTeamLogoUrl.text.clear()
                } else {
                    // Error
                    // Mostrar mensaje de error
                }
            }
    }
}