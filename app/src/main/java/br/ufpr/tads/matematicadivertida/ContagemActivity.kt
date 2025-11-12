package br.ufpr.tads.matematicadivertida

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

data class DadosImagem(val idRecurso: Int, val quantidade: Int)

class ContagemActivity : AppCompatActivity() {

    private lateinit var ivImagem: ImageView
    private lateinit var btnOpcao1: Button
    private lateinit var btnOpcao2: Button
    private lateinit var btnOpcao3: Button

    private var indiceAtual = 0
    private var pontuacao = 0
    private val totalPerguntas = 5
    private lateinit var perguntasSelecionadas: List<DadosImagem>
    private var respostaCorretaAtual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contagem)

        ivImagem = findViewById(R.id.ivImagem)
        btnOpcao1 = findViewById(R.id.btnOpcao1)
        btnOpcao2 = findViewById(R.id.btnOpcao2)
        btnOpcao3 = findViewById(R.id.btnOpcao3)

        inicializarJogo()

        btnOpcao1.setOnClickListener { verificarResposta(btnOpcao1.text.toString().toInt()) }
        btnOpcao2.setOnClickListener { verificarResposta(btnOpcao2.text.toString().toInt()) }
        btnOpcao3.setOnClickListener { verificarResposta(btnOpcao3.text.toString().toInt()) }
    }

    private fun inicializarJogo() {
        val todasImagens = mutableListOf(
            DadosImagem(R.drawable.img1, 1),
            DadosImagem(R.drawable.img2, 2),
            DadosImagem(R.drawable.img3, 3),
            DadosImagem(R.drawable.img4, 4),
            DadosImagem(R.drawable.img5, 5),
            DadosImagem(R.drawable.img6, 6),
            DadosImagem(R.drawable.img7, 7),
            DadosImagem(R.drawable.img8, 8),
            DadosImagem(R.drawable.img9, 9),
            DadosImagem(R.drawable.img10, 10)
        )
        todasImagens.shuffle()
        perguntasSelecionadas = todasImagens.take(totalPerguntas)
        carregarPergunta()
    }

    private fun carregarPergunta() {
        val pergunta = perguntasSelecionadas[indiceAtual]
        respostaCorretaAtual = pergunta.quantidade
        ivImagem.setImageResource(pergunta.idRecurso)

        val opcoes = mutableListOf<Int>()
        opcoes.add(respostaCorretaAtual)

        while (opcoes.size < 3) {
            val incorreta = Random.nextInt(1, 11)
            if (!opcoes.contains(incorreta)) {
                opcoes.add(incorreta)
            }
        }

        opcoes.shuffle()

        btnOpcao1.text = opcoes[0].toString()
        btnOpcao2.text = opcoes[1].toString()
        btnOpcao3.text = opcoes[2].toString()
    }

    private fun verificarResposta(respostaSelecionada: Int) {
        val acertou = (respostaSelecionada == respostaCorretaAtual)
        if (acertou) {
            pontuacao++
        }
        mostrarDialogoResultado(acertou)
    }

    private fun mostrarDialogoResultado(acertou: Boolean) {
        val mensagem = if (acertou) {
            "Correto!"
        } else {
            "Errado! A resposta certa é $respostaCorretaAtual"
        }

        AlertDialog.Builder(this)
            .setTitle("Resultado")
            .setMessage(mensagem)
            .setPositiveButton("Adiante") { _, _ ->
                if (indiceAtual < totalPerguntas - 1) {
                    indiceAtual++
                    carregarPergunta()
                } else {
                    finalizarJogo()
                }
            }
            .setCancelable(false)
            .show()
    }

    private fun finalizarJogo() {
        val notaFinal = (pontuacao * 100) / totalPerguntas
        AlertDialog.Builder(this)
            .setTitle("Fim de Jogo")
            .setMessage("Sua nota é $notaFinal")
            .setPositiveButton("OK") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
}