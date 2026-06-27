package com.example.seapedia.global.utils

import java.text.NumberFormat
import java.util.Locale

class Formatting {
    companion object{
        val rupiahFormatter by lazy {
            NumberFormat.getNumberInstance(Locale("in","ID"))
        }
    }

    fun formatRupiah(value: String): String {
        if (value.isBlank()) return ""

        return "Rp ${Formatting.rupiahFormatter.format(value.toLong())}"
    }

    fun formatRupiahToInt(value: String) : Int {
        if(value.isBlank()) return 0
        val newValue = value
            .removePrefix("Rp ")
            .replace(".", "")
            .trim().toInt()
        return newValue
    }
}