package br.ufpr.tads.matematicadivertida

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MaiorActivity : AppCompatActivity() {

    private var round = 0
    private var maxRounds = 5
    private var correct = 0
    private var wrong = 0

    private lateinit var responseButton: Button
    private lateinit var num1: TextView
    private lateinit var num2: TextView
    private lateinit var num3: TextView
    private lateinit var answerNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_maior)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        responseButton = findViewById<Button>(R.id.responseButton)
        num1 = findViewById<TextView>(R.id.num1)
        num2 = findViewById<TextView>(R.id.num2)
        num3 = findViewById<TextView>(R.id.num3)
        answerNumber = findViewById<EditText>(R.id.answerNumber)

        responseButton.setOnClickListener {
            checkAnswer()
        }

        lockInput()
        num1.text = "?"
        num2.text = "?"
        num3.text = "?"

        lifecycleScope.launch(Dispatchers.Main) {
            delay(1000L)
            roundShuffleAnimated()
        }
    }

    fun advanceGame() {
        round += 1

        if ((round + 1) >= maxRounds) {
            endGame()
        } else {
            roundShuffleAnimated()
        }
    }

    fun lockInput() {
        answerNumber.isEnabled = false
        responseButton.isEnabled = false
    }

    fun unlockInput() {
        answerNumber.isEnabled = true
        responseButton.isEnabled = true
    }

    fun checkAnswer() {
        if (answerNumber.text.isEmpty()) {
            Toast.makeText(this, "Por favor insira uma resposta", Toast.LENGTH_SHORT).show()
            return
        }

        var n1 = num1.text.toString().toInt()
        var n2 = num2.text.toString().toInt()
        var n3 = num3.text.toString().toInt()

        val arr = arrayOf(n1, n2, n3)
        arr.sortDescending()

        var correctAnswer = arr[0] * 100 + arr[1] * 10 + arr[2]
        var typedAnswer = answerNumber.text.toString().toInt()

        val adb = android.app.AlertDialog.Builder(this)

        if (correctAnswer == typedAnswer) {
            correct += 1
            adb.setMessage("Correto!")
            adb.setOnDismissListener { advanceGame() }
            adb.setPositiveButton("Ok!", null)
            adb.show()
        } else {
            wrong += 1
            adb.setMessage("ERROU! A resposta correta é ${correctAnswer.toString()}")
            adb.setOnDismissListener { advanceGame() }
            adb.setPositiveButton("Ok!", null)
            adb.show()
        }

        answerNumber.text.clear()
    }

    fun endGame() {
        val adb = android.app.AlertDialog.Builder(this)
        adb.setMessage("Fim de jogo! Voce acertou $correct / $maxRounds")
        adb.setOnDismissListener { finish() }
        adb.show()
    }

    val colorMap = mapOf<Int, Int>(
        0 to Color.BLUE,
        1 to Color.RED,
        2 to Color.CYAN,
        3 to Color.GREEN,
        4 to Color.MAGENTA,
        5 to Color.YELLOW,
        6 to Color.BLUE,
        7 to Color.RED,
        8 to Color.CYAN,
        9 to Color.GREEN,
    )

    fun roundShuffleAnimated() {
        lockInput()

        lifecycleScope.launch(Dispatchers.Main) {
            for (i in 1 .. 90) {
                delay(33L)
                if (i < 30) {
                    var n1 =  getRandomDigit()
                    num1.text = n1.toString()
                    num1.setTextColor(colorMap.getValue(n1))
                    num1.setShadowLayer(50.0f, 0.0f, 0.0f,colorMap.getValue(n1))
                }
                if (i < 60) {
                    var n2 =  getRandomDigit()
                    num2.text = n2.toString()
                    num2.setTextColor(colorMap.getValue(n2))
                    num2.setShadowLayer(50.0f, 0.0f, 0.0f,colorMap.getValue(n2))
                }
                var n3 =  getRandomDigit()
                num3.text = n3.toString()
                num3.setTextColor(colorMap.getValue(n3))
                num3.setShadowLayer(50.0f, 0.0f, 0.0f,colorMap.getValue(n3))
            }

            unlockInput()
        }
    }

    fun roundShuffle() {
        num1.text = getRandomDigit().toString()
        num2.text = getRandomDigit().toString()
        num3.text = getRandomDigit().toString()
    }

    fun getRandomDigit(): Int {
        return Random.nextInt(0, 9)
    }
}