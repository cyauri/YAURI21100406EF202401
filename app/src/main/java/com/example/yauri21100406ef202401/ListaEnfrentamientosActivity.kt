package com.example.yauri21100406ef202401

// Importaciones necesarias
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// Data class para representar un enfrentamiento
data class Matchup(
    val localTeam: String = "",
    val visitorTeam: String = "",
    val localWinOdds: Double = 0.0,
    val drawOdds: Double = 0.0,
    val visitorWinOdds: Double = 0.0,
    val localTeamLogoUrl: String = "",
    val visitorTeamLogoUrl: String = ""
)

class ListaEnfrentamientosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonRegisterTeams: Button
    private lateinit var buttonRegisterMatchups: Button
    private lateinit var db: FirebaseFirestore
    private lateinit var matchupAdapter: MatchupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_enfrentamientos) // Asegúrate de que el archivo XML sea el correcto

        // Inicializar Firestore
        db = Firebase.firestore

        // Obtener referencias a las vistas
        recyclerView = findViewById(R.id.recyclerViewMatchups)
        buttonRegisterTeams = findViewById(R.id.buttonRegisterTeams)
        buttonRegisterMatchups = findViewById(R.id.buttonRegisterMatchups)

        // Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        matchupAdapter = MatchupAdapter()
        recyclerView.adapter = matchupAdapter

        // Cargar los enfrentamientos desde Firestore
        loadMatchups()

        // Configurar los botones de navegación
        buttonRegisterTeams.setOnClickListener {
            // Redireccionar a la pantalla del Caso 01
            val intent = Intent(this, RegisterTeamsActivity::class.java)
            startActivity(intent)
        }

        buttonRegisterMatchups.setOnClickListener {
            // Redireccionar a la pantalla del Caso 02
            val intent = Intent(this, RegisterEnfrentamientosActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadMatchups() {
        db.collection("matchups").get().addOnSuccessListener { result ->

