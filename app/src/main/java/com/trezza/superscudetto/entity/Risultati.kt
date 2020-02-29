package com.trezza.superscudetto.entity

class Risultati(
    var descrizione: String,
    var squadra: String,
    var giornata: Int,
    var punti: Double,
    var anno: Int) {

    companion object {

        fun getStorico(
            risultatiBarcano: List<Double>,
            risultatiMorbidiAmici: List<Double>,
            risultatiVincera: List<Double>,
            anno: Int
        ): MutableList<Risultati> {

            var storico: MutableList<Risultati> = mutableListOf()
            var nomiSquadre: List<String> = listOf("Barcanò", "Morbidi Amici", "Vincerà")
            for (i in 0..2) {
                for (j in risultatiBarcano.indices) {
                    storico.add(
                        j, Risultati(
                            "giornata " + (j + 1),
                            nomiSquadre.elementAt(i),
                            j + 1,
                            when (i) {
                                0 -> risultatiBarcano.elementAt(j)
                                1 -> risultatiMorbidiAmici.elementAt(j)
                                2 -> risultatiVincera.elementAt(j)
                                else -> 0.0
                            },
                            anno
                        )
                    )
                }
            }
            return storico
        }
    }
}