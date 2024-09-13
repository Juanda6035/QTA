package com.example.saludoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Asegúrate de que este layout existe

        // Referencias a los elementos del layout
        val valorPropiedadInput = findViewById<EditText>(R.id.et_valor_propiedad)
        val cuantoNecesitasInput = findViewById<EditText>(R.id.et_cuanto_necesitas)
        val plazoCreditoInput = findViewById<EditText>(R.id.et_plazo_credito)
        val tasaInteresInput = findViewById<EditText>(R.id.et_tasa_interes)
        val resultadoText = findViewById<TextView>(R.id.tv_resultado)
        val simularBtn = findViewById<Button>(R.id.btn_simular)

        // Configurar el botón de simulación
        simularBtn.setOnClickListener {
            val valorPropiedad = valorPropiedadInput.text.toString().toDoubleOrNull()
            val cuantoNecesitas = cuantoNecesitasInput.text.toString().toDoubleOrNull()
            val plazoCredito = plazoCreditoInput.text.toString().toIntOrNull()
            val tasaInteres = tasaInteresInput.text.toString().toDoubleOrNull()

            // Validaciones de entrada
            if (valorPropiedad == null || valorPropiedad < 70000000) {
                Toast.makeText(this, "El valor de la propiedad debe ser mayor o igual a $70.000.000", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (cuantoNecesitas == null || cuantoNecesitas < 50000000 || cuantoNecesitas > valorPropiedad * 0.7) {
                Toast.makeText(this, "El préstamo no puede exceder el 70% del valor de la propiedad y debe ser mayor a $50.000.000", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (plazoCredito == null || plazoCredito !in 5..20) {
                Toast.makeText(this, "El plazo debe estar entre 5 y 20 años", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (tasaInteres == null || tasaInteres < 12.0 || tasaInteres > 24.9) {
                Toast.makeText(this, "La tasa de interés debe estar entre 12.0% y 24.9%", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Cálculo de la cuota mensual
            val tasaMensual = (tasaInteres / 12) / 100
            val meses = plazoCredito * 12
            val cuota = cuantoNecesitas * (tasaMensual * (1 + tasaMensual).pow(meses)) / ((1 + tasaMensual).pow(meses) - 1)

            // Mostrar resultado
            resultadoText.text = "Cuota mensual aproximada: $${"%.2f".format(cuota)}"
            resultadoText.visibility = TextView.VISIBLE
        }
    }
}