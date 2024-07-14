package com.example.yauri21100406ef202401

// Importaciones necesarias
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterEnfrentamientosActivity : AppCompatActivity() {

    private lateinit var spinnerLocalTeam: Spinner
    private lateinit var spinnerVisitorTeam: Spinner
    private lateinit var editTextLocalWinOdds: EditText
    private lateinit var editTextDrawOdds: EditText
    private lateinit var editTextVisitorWinOdds: EditText
    private lateinit var buttonRegisterMatchup: Button
    private lateinit var buttonRegisterTeams: Button
    private lateinit var buttonListMatchups: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_enfrentamientos) // Actualiza a nuevo nombre XML

        // Inicializar Firestore
        db = Firebase.firestore

        // Obtener referencias a las vistas
        spinnerLocalTeam = findViewById(R.id.spinnerLocalTeam)
        spinnerVisitorTeam = findViewById(R.id.spinnerVisitorTeam)
        editTextLocalWinOdds = findViewById(R.id.editTextLocalWinOdds)
        editTextDrawOdds = findViewById(R.id.editTextDrawOdds)
        editTextVisitorWinOdds = findViewById(R.id.editTextVisitorWinOdds)
        buttonRegisterMatchup = findViewById(R.id.buttonRegisterMatchup)
        buttonRegisterTeams = findViewById(R.id.buttonRegisterTeams)
        buttonListMatchups = findViewById(R.id.buttonListMatchups)

        // Configurar el botón Registrar
        buttonRegisterMatchup.setOnClickListener {
            registerMatchup()
        }

        // Configurar los botones de navegación
        buttonRegisterTeams.setOnClickListener {
            // Redireccionar a la pantalla del Caso 01
            val intent = Intent(this, RegisterTeamsActivity::class.java)
            startActivity(intent)
        }

        buttonListMatchups.setOnClickListener {
            // Redireccionar a la pantalla del Caso 03
            val intent = Intent(this, ListaEnfrentamientosActivity::class.java)
            startActivity(intent)
        }

        // Llenar los Spinners con datos
        loadTeams()
    }

    private fun loadTeams() {
        // Consulta Firestore para obtener la lista de equipos
        db.collection("teams").get().addOnSuccessListener { result ->
            val teams = result.map { it.getString("name") ?: "" }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, teams)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerLocalTeam.adapter = adapter
            spinnerVisitorTeam.adapter = adapter
        }
    }

    private fun registerMatchup() {
        val localTeam = spinnerLocalTeam.selectedItem.toString()
        val visitorTeam = spinnerVisitorTeam.selectedItem.toString()
        val localWinOdds = editTextLocalWinOdds.text.toString().trim()
        val drawOdds = editTextDrawOdds.text.toString().trim()
        val visitorWinOdds = editTextVisitorWinOdds.text.toString().trim()

        if (localTeam.isEmpty() || visitorTeam.isEmpty() || localWinOdds.isEmpty() || drawOdds.isEmpty() || visitorWinOdds.isEmpty()) {
            // Validar los campos
            Toast.makeText(this, "Todos los campos deben ser llenados", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear un mapa con los datos del enfrentamiento
        val matchup = hashMapOf(
            "localTeam" to localTeam,
            "visitorTeam" to visitorTeam,
            "localWinOdds" to localWinOdds.toDouble(),
            "drawOdds" to drawOdds.toDouble(),
            "visitorWinOdds" to visitorWinOdds.toDouble()
        )

        // Guardar en Firestore
        db.collection("matchups").add(matchup)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Éxito
                    // Limpiar los campos o mostrar un mensaje
                    editTextLocalWinOdds.text.clear()
                    editTextDrawOdds.text.clear()
                    editTextVisitorWinOdds.text.clear()
                    Toast.makeText(this, "Enfrentamiento registrado exitosamente", Toast.LENGTH_SHORT).show()
                } else {
                    // Error
                    Toast.makeText(this, "Error al registrar el enfrentamiento", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
