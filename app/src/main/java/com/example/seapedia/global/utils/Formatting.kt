package com.example.seapedia.global.utils

import java.text.NumberFormat
import java.util.Locale

class Formatting {
    companion object{
        val rupiahFormatter by lazy {
            NumberFormat.getNumberInstance(Locale("in","ID"))
        }
    }
}