package com.trezza.superscudetto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.trezza.superscudetto.R
import com.trezza.superscudetto.entity.Risultati
import kotlinx.android.synthetic.main.adapter_risultati_squadre.view.*

class RisultatiListView(context : Context, lista : List<Risultati>) : BaseAdapter() {

    var context = context
    var lista = lista

    inner class Holder {
      lateinit var risultatiGiornata: TextView
      lateinit var risultatiSquadra: TextView
      lateinit var risultatiPunti: TextView
    }

    override fun getView(
      position: Int,
      convertView: View?,
      parent: ViewGroup?
    ): View {
      val newView: View
      val holder: Holder
      if (convertView == null) {
        newView = LayoutInflater.from(context)
          .inflate(R.layout.adapter_risultati_squadre, null)
        holder = Holder().apply {
          risultatiGiornata = newView.risultatiGiornata
          risultatiSquadra = newView.risultatiSquadra
          risultatiPunti = newView.risultatiPunti
        }
        newView.tag = holder
      } else {
        newView = convertView
        holder = newView.tag as Holder
      }
      // Current item
      val item = lista[position]
      // UI logic
      holder.run {
        risultatiGiornata.text = item.descrizione
        risultatiSquadra.text = item.squadra
        risultatiPunti.text = item.punti.toString()
      }

      newView.setOnClickListener {
        Toast.makeText(context,"HAI CLICCATO SU " + item.punti , Toast.LENGTH_SHORT).show()
      }

      // Return the item
      return newView
    }

    override fun getItem(position: Int): Any =
      lista[position]

    override fun getItemId(position: Int): Long =
      lista.indexOf(lista[position]).toLong()

    override fun getCount(): Int =
      lista.size

  }