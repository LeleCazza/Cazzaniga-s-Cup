package com.trezza.superscudetto

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.trezza.superscudetto.db.SuperscudettoDbLifecycle
import com.trezza.superscudetto.entity.babbo
import com.trezza.superscudetto.entity.fra
import com.trezza.superscudetto.entity.lele
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {

    companion object {
        const val STRING_KEY = "SQUADRA_CAMPIONE"
        lateinit var superscudettoDb: SuperscudettoDbLifecycle
        lateinit var sharedPreferences: SharedPreferences
        var TAG_STORICO = "STORICO"
        var TAG_ANNO = "ANNO"
        var TAG_SQUADRA = "SQUADRA"
    }

    private var TAG = "ACTIVITY MAIN"
    lateinit var primo : TextView
    lateinit var puntiPrimo : TextView
    lateinit var secondo : TextView
    lateinit var puntiSecondo : TextView
    lateinit var terzo : TextView
    lateinit var puntiTerzo : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "ON_CREATE");
        superscudettoDb = SuperscudettoDbLifecycle(this)
        superscudettoDb.onAttach(this)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        var idSquadra = sharedPreferences.getInt(STRING_KEY, 0)
        NomeCampione.setSelection(idSquadra)
        caricaComponenti()
        creaClassifica()
    }

    private fun caricaComponenti(){
        primo = findViewById<TextView>(R.id.primo)
        puntiPrimo = findViewById<TextView>(R.id.puntiPrimo)
        secondo = findViewById<TextView>(R.id.secondo)
        puntiSecondo = findViewById<TextView>(R.id.puntiSecondo)
        terzo = findViewById<TextView>(R.id.terzo)
        puntiTerzo = findViewById<TextView>(R.id.puntiTerzo)
    }

    @SuppressLint("SetTextI18n")
    private fun creaClassifica(){
        val puntiBarcano = superscudettoDb.puntiSquadra(lele)
        val puntiMorbidiAmici = superscudettoDb.puntiSquadra(fra)
        val puntiBabbo = superscudettoDb.puntiSquadra(babbo)
        val p = maxOf(puntiBabbo, puntiBarcano, puntiMorbidiAmici)
        val u = minOf(puntiBabbo, puntiBarcano, puntiMorbidiAmici)
        calcolaPosizioneSquadra(p,u,puntiBarcano,"BARCANO'")
        calcolaPosizioneSquadra(p,u,puntiBabbo,"VINCERA'")
        calcolaPosizioneSquadra(p,u,puntiMorbidiAmici,"MORBIDI AMICI")
    }

    private fun calcolaPosizioneSquadra(p : Double, u : Double, puntiSquadra : Double, nomeSquadra : String){
        when {
            p == puntiSquadra -> {
                primo.text = nomeSquadra
                puntiPrimo.text = puntiSquadra.toString()
            }
            u == puntiSquadra -> {
                terzo.text = nomeSquadra
                puntiTerzo.text = puntiSquadra.toString()
            }
            else -> {
                secondo.text = nomeSquadra
                puntiSecondo.text = puntiSquadra.toString()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        Log.d(TAG,"ON_CREATE_OPTIONS_MENU")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG,"MENU_CLICK")
        val intent = Intent(this,Squadre::class.java).apply {
            when(item.title){
                "Barcanò" -> putExtra(TAG_SQUADRA,"BARCANO")
                "Morbidi Amici" -> putExtra(TAG_SQUADRA,"MORBIDI AMICI")
                "Vincerà" -> putExtra(TAG_SQUADRA,"VINCERA")
            }
        }
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "ON_START")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ON_RESUME")
        findViewById<Button>(R.id.BottoneStorico2018).setOnClickListener {
            val intent = Intent(this,Storico::class.java).apply {
                putExtra(TAG_STORICO,"MAIN")
                putExtra(TAG_ANNO,"2018")
            }
            startActivity(intent)
        }
        findViewById<Button>(R.id.BottoneStorico2019).setOnClickListener {
            val intent = Intent(this,Storico::class.java).apply {
                putExtra(TAG_STORICO,"MAIN")
                putExtra(TAG_ANNO,"2019")
            }
            startActivity(intent)
        }
        findViewById<Spinner>(R.id.NomeCampione).onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                saveData()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "ON_PAUSE")
        superscudettoDb.onDetach()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "ON_STOP")
        superscudettoDb.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ON_DESTROY")
        superscudettoDb.onDetach()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "ON_RESTART")
        superscudettoDb.onAttach(this)
    }

    fun saveData() {
        sharedPreferences.edit()
            .putInt(STRING_KEY, NomeCampione.selectedItemPosition)
            .apply()
    }
}
