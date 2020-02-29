package com.trezza.superscudetto

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
import androidx.appcompat.app.AppCompatActivity
import com.trezza.superscudetto.db.SuperscudettoDbLifecycle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "ON_CREATE");
        superscudettoDb = SuperscudettoDbLifecycle(this)
        superscudettoDb.onAttach(this)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        var idSquadra = sharedPreferences.getInt(STRING_KEY, 0)
        NomeCampione.setSelection(idSquadra)
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
