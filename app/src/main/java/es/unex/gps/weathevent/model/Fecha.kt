package es.unex.gps.weathevent.model

import java.io.Serializable

data class Fecha (
    var dia : Int,
    var mes : Int,
    var ano : Int,
    var hora : Int,
    var mins : Int
) : Serializable {
    fun getFormatDay() : String {
        val months = arrayOf("Enero", "Febrero", "Marzo", "Abril",
            "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
            "Octubre", "Noviembre", "Diciembre")

        return "$dia de ${months[mes-1]} de $ano"
    }

    fun getFormatHour() : String {
        val mins_string = if (mins < 10) "0$mins" else mins
        return "$hora:$mins_string"
    }

    fun isValid() : Boolean {
        return if (hora in 0..23) {
            if (ano in 1980..2035) {
                // Comprobar si es bisiesto
                if (ano % 4 == 0) {
                    // Es bisiesto
                    if (mes in arrayOf(1, 3, 5, 7, 8, 10, 12)){
                        // Meses con 31 días
                        dia in 1..31
                    } else if (mes == 2) {
                        // Mes con 29 días
                        dia in 1..29
                    } else {
                        // Mes con 30 días
                        dia in 1..30
                    }
                } else {
                    // No es bisiesto
                    if (mes in arrayOf(1, 3, 5, 7, 8, 10, 12)){
                        // Meses con 31 días
                        dia in 1..31
                    } else if (mes == 2) {
                        // Mes con 28 días
                        dia in 1..28
                    } else {
                        // Mes con 30 días
                        dia in 1..30
                    }
                }
            } else false
        } else false
    }

    fun getAbsoluteDay() : Int {
        val days = arrayListOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        var cDays = days.subList(0, mes - 1).sum() + dia

        if (ano % 4 == 0) cDays += 1

        var yDays = ano * 365 + ano/4 - ano/100 + ano/400

        return yDays + cDays
    }
}