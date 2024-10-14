package com.example.lab21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Отримуємо доступ до елементів інтерфейсу
        val coalInput = findViewById<EditText>(R.id.coalInput)
        val oilInput = findViewById<EditText>(R.id.oilInput)
        val gasInput = findViewById<EditText>(R.id.gasInput)
        val resultView = findViewById<TextView>(R.id.resultView)
        val calculateButton = findViewById<Button>(R.id.calculateButton)

        // Слухач для кнопки "Розрахувати"
        calculateButton.setOnClickListener {
            try {
                // Перевіряємо введення, щоб уникнути NullPointerException
                val coalAmount = coalInput.text.toString().toDoubleOrNull() ?: 0.0
                val oilAmount = oilInput.text.toString().toDoubleOrNull() ?: 0.0
                val gasAmount = gasInput.text.toString().toDoubleOrNull() ?: 0.0

                // Викликаємо функцію для обчислення викидів
                val (coalEmissions, oilEmissions, gasEmissions) = calculateEmissions(coalAmount, oilAmount, gasAmount)

                // Виводимо результат
                val resultText = """
                    Валовий викид від вугілля: $coalEmissions т
                    Валовий викид від мазуту: $oilEmissions т
                    Валовий викид від природного газу: $gasEmissions т
                    Загальний валовий викид: ${coalEmissions + oilEmissions + gasEmissions} т
                """.trimIndent()

                resultView.text = resultText
            } catch (e: Exception) {
                // Якщо виникла помилка, показуємо повідомлення
                Toast.makeText(this, "Введіть коректні значення", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Функція для обчислення викидів
    private fun calculateEmissions(coal: Double, oil: Double, gas: Double): Triple<Double, Double, Double> {
        val coalEmissionFactor = 150.0  // г/ГДж
        val oilEmissionFactor = 0.57    // г/ГДж
        val gasEmissionFactor = 0.0     // г/ГДж (немає твердих частинок)

        val coalLowerHeatingValue = 20.47 // МДж/кг
        val oilLowerHeatingValue = 40.40  // МДж/кг
        val gasLowerHeatingValue = 33.08  // МДж/м3

        // Обчислення викидів
        val coalEmissions = (coalEmissionFactor * coal * coalLowerHeatingValue) / 1000
        val oilEmissions = (oilEmissionFactor * oil * oilLowerHeatingValue) / 1000
        val gasEmissions = (gasEmissionFactor * gas * gasLowerHeatingValue) / 1000

        // Повертаємо результат як трійку значень
        return Triple(coalEmissions, oilEmissions, gasEmissions)
    }

}
