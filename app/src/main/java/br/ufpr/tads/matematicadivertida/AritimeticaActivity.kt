package br.ufpr.tads.matematicadivertida

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class AritimeticaActivity : AppCompatActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var etAnswer: EditText
    private lateinit var btnCheck: Button

    private var questaoAtual = 1
    private var score = 0
    private var resultado = 0
    private val totalQuestoes = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aritimetica)

        tvQuestion = findViewById(R.id.tvQuestion)
        etAnswer = findViewById(R.id.etAnswer)
        btnCheck = findViewById(R.id.btnCheck)

        montarQuestao()

        btnCheck.setOnClickListener {
            checaResposta()
        }
    }

    private fun montarQuestao() {
        val num1 = Random.nextInt(10)
        val num2 = Random.nextInt(10)
        val isSoma = Random.nextBoolean()

        if (isSoma) {
            resultado = num1 + num2
            tvQuestion.text = "$num1 + $num2"
        } else {
            resultado = num1 - num2
            tvQuestion.text = "$num1 - $num2"
        }
        etAnswer.text.clear()
    }

    private fun checaResposta() {
        val input = etAnswer.text.toString()
        if (input.isEmpty()) {
            Toast.makeText(this, "Insira uma resposta", Toast.LENGTH_SHORT).show()
            return
        }

        val resposta = input.toInt()
        val acertou = resposta == resultado

        if (acertou) {
            score++
        }

        showResultDialog(acertou)
    }

    private fun showResultDialog(acertou: Boolean) {
        val message = if (acertou) {
            "Certo!"
        } else {
            "Errado! O certo era $resultado"
        }

        AlertDialog.Builder(this)
            .setTitle("Resultado")
            .setMessage(message)
            .setPositiveButton("Adiante") { _, _ ->
                if (questaoAtual < totalQuestoes) {
                    questaoAtual++
                    montarQuestao()
                } else {
                    finalizarJogo()
                }
            }
            .setCancelable(false)
            .show()
    }

    private fun finalizarJogo() {
        val scoreFinal = (score * 100) / totalQuestoes

        AlertDialog.Builder(this)
            .setTitle("Fim do jogo")
            .setMessage("Sua nota é $scoreFinal")
            .setPositiveButton("OK") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
}