package com.trezza.superscudetto.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.trezza.superscudetto.R
import com.trezza.superscudetto.entity.Risultati
import kotlinx.android.synthetic.main.adapter_risultati_main.view.*

class RisultatiMainListView(context : Context, lista1 : List<Risultati>, lista2 : List<Risultati>, lista3 : List<Risultati> ) : BaseAdapter() {

    var context = context
    var lista1 = lista1
    var lista2 = lista2
    var lista3 = lista3

    inner class Holder {
      lateinit var risultatiGiornata1: TextView
      lateinit var risultatiSquadra1: TextView
      lateinit var risultatiPunti1: TextView
      lateinit var risultatiGiornata2: TextView
      lateinit var risultatiSquadra2: TextView
      lateinit var risultatiPunti2: TextView
      lateinit var risultatiGiornata3: TextView
      lateinit var risultatiSquadra3: TextView
      lateinit var risultatiPunti3: TextView
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
          .inflate(R.layout.adapter_risultati_main, null)
        holder = Holder().apply {
            risultatiGiornata1 = newView.risultatiGiornata1
          risultatiSquadra1 = newView.risultatiSquadra1
          risultatiPunti1 = newView.risultatiPunti1
          risultatiGiornata2 = newView.risultatiGiornata2
          risultatiSquadra2 = newView.risultatiSquadra2
          risultatiPunti2 = newView.risultatiPunti2
          risultatiGiornata3 = newView.risultatiGiornata3
          risultatiSquadra3 = newView.risultatiSquadra3
          risultatiPunti3 = newView.risultatiPunti3
        }
        newView.tag = holder
      } else {
        newView = convertView
        holder = newView.tag as Holder
      }
      // Current item
      val item1 = lista1[position]
      val item2 = lista2[position]
      val item3 = lista3[position]

      // UI logic
      holder.run {
        risultatiGiornata1.text = item1.descrizione
        risultatiSquadra1.text = item1.squadra
        risultatiPunti1.text = item1.punti.toString()
        risultatiGiornata2.text = item2.descrizione
        risultatiSquadra2.text = item2.squadra
        risultatiPunti2.text = item2.punti.toString()
        risultatiGiornata3.text = item3.descrizione
        risultatiSquadra3.text = item3.squadra
        risultatiPunti3.text = item3.punti.toString()

        var max = item1.punti
        if(item2.punti > max)
          max = item2.punti
        if(item3.punti > max)
          max = item3.punti

        if(max == item1.punti){
          risultatiSquadra1.setTextColor(Color.RED)
          risultatiPunti1.setTextColor(Color.RED)
        }
        else{
          risultatiSquadra1.setTextColor(Color.BLACK)
          risultatiPunti1.setTextColor(Color.BLACK)
        }
        if(max == item2.punti){
          risultatiSquadra2.setTextColor(Color.RED)
          risultatiPunti2.setTextColor(Color.RED)
        }
        else{
          risultatiSquadra2.setTextColor(Color.BLACK)
          risultatiPunti2.setTextColor(Color.BLACK)
        }
        if(max == item3.punti){
          risultatiSquadra3.setTextColor(Color.RED)
          risultatiPunti3.setTextColor(Color.RED)
        }
        else{
          risultatiSquadra3.setTextColor(Color.BLACK)
          risultatiPunti3.setTextColor(Color.BLACK)
        }
      }

      // Return the item
      return newView
    }

    override fun getItem(position: Int): Any =
      lista1[position]

    override fun getItemId(position: Int): Long =
      lista1.indexOf(lista1[position]).toLong()

    override fun getCount(): Int =
      lista1.size

  }