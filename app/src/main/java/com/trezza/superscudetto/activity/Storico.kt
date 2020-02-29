package com.trezza.superscudetto

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.trezza.superscudetto.MainActivity.Companion.TAG_ANNO
import com.trezza.superscudetto.MainActivity.Companion.TAG_STORICO
import com.trezza.superscudetto.MainActivity.Companion.superscudettoDb
import com.trezza.superscudetto.db.SuperscudettoDbLifecycle
import com.trezza.superscudetto.adapter.RisultatiListView
import com.trezza.superscudetto.adapter.RisultatiMainListView
import kotlinx.android.synthetic.main.activity_storico.*

class Storico : AppCompatActivity() {

    private var TAG = "ACTIVITY STORICO'"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storico)
        Log.d(TAG, "ON_CREATE")
        superscudettoDb = SuperscudettoDbLifecycle(this)
        superscudettoDb.onAttach(this)

        intent?.apply{
            when(getStringExtra(TAG_STORICO)){
                "MAIN" ->       listView.apply {
                                    adapter = RisultatiMainListView(this.context, superscudettoDb.select("Barcanò",getStringExtra(TAG_ANNO)),superscudettoDb.select("Morbidi Amici",getStringExtra(TAG_ANNO)),superscudettoDb.select("Vincerà", getStringExtra(TAG_ANNO)))
                                }
                "BARCANO" ->    listView.apply {
                                    adapter = RisultatiListView(this.context, superscudettoDb.select("Barcanò", getStringExtra(TAG_ANNO)))
                                }
                "MORBIDI AMICI" -> listView.apply {
                                    adapter = RisultatiListView(this.context, superscudettoDb.select("Morbidi Amici", getStringExtra(TAG_ANNO)))
                                }
                "VINCERA" ->        listView.apply {
                                    adapter = RisultatiListView(this.context, superscudettoDb.select("Vincerà", getStringExtra(TAG_ANNO)))
                                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "ON_START")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ON_RESUME")
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
