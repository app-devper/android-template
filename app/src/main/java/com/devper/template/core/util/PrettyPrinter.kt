package com.devper.template.core.util

import com.google.gson.GsonBuilder

object PrettyPrinter {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun print(o: Any): String {
        return gson.toJson(o)
    }

}