package com.trezza.superscudetto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.trezza.superscudetto.MainActivity.Companion.TAG_SQUADRA
import com.trezza.superscudetto.MainActivity.Companion.superscudettoDb
import com.trezza.superscudetto.db.SuperscudettoDbLifecycle

class Squadre : AppCompatActivity() {

    private var TAG = "ACTIVITY SQUADRE"
    private lateinit var SQUADRA : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squadre)
        Log.d(TAG, "ON_CREATE")
        superscudettoDb = SuperscudettoDbLifecycle(this)
        superscudettoDb.onAttach(this)
        var sq = superscudettoDb.findById(1)
        intent?.apply{
            when(getStringExtra(TAG_SQUADRA)){
                "BARCANO" -> {
                    findViewById<TextView>(R.id.Titolo).text = "BARCANO'"
                    SQUADRA = "BARCANO"
                }
                "MORBIDI AMICI" ->{
                    sq = superscudettoDb.findById(2)
                    findViewById<TextView>(R.id.Titolo).text = "MORBIDI AMICI"
                    SQUADRA = "MORBIDI AMICI"
                }
                "VINCERA" ->{
                    sq = superscudettoDb.findById(3)
                    findViewById<TextView>(R.id.Titolo).text = "VINCERA'"
                    SQUADRA = "VINCERA"
                }
            }
            findViewById<TextView>(R.id.Descrizione).text = sq?.getDescription()
            findViewById<TextView>(R.id.NumeroGiornata).text = "MASSIMO PUNTI = " + sq?.getPunti()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "ON_START")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ON_RESUME")

        findViewById<Button>(R.id.Storico_2018).setOnClickListener {
            val intent = Intent(this,Storico::class.java).apply {
                putExtra(MainActivity.TAG_STORICO,SQUADRA)
                putExtra(MainActivity.TAG_ANNO,"2018")
            }
            startActivity(intent)
        }
        findViewById<Button>(R.id.Storico_2019).setOnClickListener {
            val intent = Intent(this,Storico::class.java).apply {
                putExtra(MainActivity.TAG_STORICO,SQUADRA)
                putExtra(MainActivity.TAG_ANNO,"2019")
            }
            startActivity(intent)
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
}
