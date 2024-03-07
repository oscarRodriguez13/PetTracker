package com.example.pettracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class HistorialActivity : AppCompatActivity() {

    private var mlista: ListView? = null
    private var mHistorialAdapter: HistorialAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        // Inicialización de la ListView
        mlista = findViewById(R.id.historial)

        // Cargar JSON desde los assets
        val jsonString = loadJSONFromAsset()

        // Parsear el JSON a una lista de objetos HistorialItem
        val historialList = parseJSON(jsonString) ?: listOf()

        // Inicialización del adaptador con la lista parseada
        mHistorialAdapter = HistorialAdapter(this, historialList)
        mlista?.adapter = mHistorialAdapter
    }

    private fun loadJSONFromAsset(): String? {
        return try {
            val inputStream = assets.open("historial.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

    private fun parseJSON(jsonString: String?): List<HistorialItem>? {
        return jsonString?.let {
            val gson = Gson()
            val type = object : TypeToken<List<HistorialItem>>() {}.type
            gson.fromJson<List<HistorialItem>>(it, type)
        }
    }
}