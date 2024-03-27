package com.example.pettracker

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val etHora = findViewById<EditText>(R.id.etHora)
        val etDuracionPaseo = findViewById<EditText>(R.id.etDuracionPaseo)
        val btn_solicitud_paseo = findViewById<Button>(R.id.btn_solicitud_paseo)


        // Obtener la hora actual
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)

        // Crear un diálogo de selección de hora
        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                // Manejar la hora seleccionada aquí
                val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                etHora.setText(selectedTime)
            },
            hour,
            minute,
            true // Si deseas usar formato de 24 horas
        )

        // Mostrar el diálogo de selección de hora cuando se haga clic en el EditText
        etHora.setOnClickListener {
            timePickerDialog.show()
        }

        btn_solicitud_paseo.setOnClickListener {
            if (verificarCamposLlenos(etDuracionPaseo)) {
                // Cambia a la siguiente pantalla
                val intent = Intent(this, SolicitarPaseoActivity::class.java)
                val bundle = Bundle().apply {
                    putString("duracion", etDuracionPaseo.text.toString().trim())
                }

                // Añadir el Bundle al Intent
                intent.putExtras(bundle)

                // Iniciar la actividad con el Intent que tiene el Bundle
                startActivity(intent)
            } else {
                // Muestra un mensaje de error o indicación
                Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        val historialButton = findViewById<Button>(R.id.buttonOption2)
        historialButton.setOnClickListener {
            val intent = Intent(
                applicationContext,
                HistorialActivity::class.java
            )
            startActivity(intent)
        }
    }

    private fun verificarCamposLlenos(vararg campos: EditText): Boolean =
        campos.all { it.text.toString().trim().isNotEmpty() }

}
