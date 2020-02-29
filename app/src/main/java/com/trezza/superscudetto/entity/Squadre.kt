package com.trezza.superscudetto.entity

//creo le squadre
var lele = Squadre( 1,"squadra di Lele","Barcanò",106.0);
var fra = Squadre(2,"squadra di Francesco","Morbidi Amici",92.5);
var babbo = Squadre(3,"squadra del Babbo","Vincerà",99.0);

class Squadre(
    var id: Int,
    var descrizione: String,
    var nome: String,
    var massimoPunti: Double){

    fun settaMassimoPunti(punti: Double){
        this.massimoPunti = punti
    }

    fun getDescription() : String{
        return this.descrizione
    }

    fun getMassimoPunti() : String{
        return this.massimoPunti.toString()
    }
}