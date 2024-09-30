package com.gabrielmarques.ecodesconto

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabrielmarques.ecodesconto.databinding.ActivitySegundaTelaBinding

class segunda_tela : AppCompatActivity(){
   private lateinit var binding: ActivitySegundaTelaBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySegundaTelaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkbox_teste()

        binding.btCalcular.setOnClickListener {
            handleEnviar()
        }

        binding.btNovamente.setOnClickListener {
            limparCampos()
        }
    }

    private fun checkbox_teste(){

        val checar_materias = listOf(binding.typePapel,binding.typePlast,binding.typeMetal, binding.typeGlass)
        val checar_endereco = listOf(binding.tipoEnd1, binding.tipoEnd2)
        val checar_conta = listOf(binding.energy, binding.water)

        checar_materias.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    // Desmarcar os outros CheckBoxes de materiais
                    checar_materias.forEach { other ->
                        if (other != buttonView) {
                            other.isChecked = false
                        }
                    }

                    // Verificar se mais de um está selecionado
                    if (checar_materias.count { it.isChecked } > 1) {
                        Toast.makeText(this, "Você só pode selecionar um material por vez.", Toast.LENGTH_SHORT).show()
                        // buttonView.isChecked = false // Desmarcar o CheckBox que foi marcado
                    }
                }
            }
        }

        checar_endereco.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked){
                    checar_endereco.forEach { other ->
                        if (other != buttonView){
                            other.isChecked = false
                        }

                        if (checar_endereco.count{it.isChecked} > 1){
                            Toast.makeText(this,"Você So Pode Escolher Um Tipo de Conta Por Vez", Toast.LENGTH_SHORT).show()
                            // buttonView.isChecked = false
                        }
                    }
                }
            }
        }

        checar_conta.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked){
                    checar_conta.forEach { other ->
                        if (other != buttonView){
                            other.isChecked = false
                        }

                        if (checar_conta.count{it.isChecked} > 1){
                            Toast.makeText(this,"Você So Pode Escolher Um Tipo de Conta Por Vez", Toast.LENGTH_SHORT).show()
                            // buttonView.isChecked = false
                        }
                    }
                }
            }
        }


    }

    private fun handleEnviar() {
        val nome = binding.nomeInfo.text.toString()
        val endereco = binding.infoEndereco.text.toString()
        val quantidade = binding.infoQuant.text.toString().toDoubleOrNull()

        // Obter o tipo de material selecionado
        val tipoMaterial = when {
            binding.typePapel.isChecked -> "papel"
            binding.typePlast.isChecked -> "plástico"
            binding.typeMetal.isChecked -> "metal"
            binding.typeGlass.isChecked -> "vidro"
            else -> null
        }

        // Validação dos campos
        if (nome.isEmpty() || endereco.isEmpty() || tipoMaterial == null || quantidade == null || !binding.tipoEnd1.isChecked && !binding.tipoEnd2.isChecked) {
            Toast.makeText(this, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
            return
        }



        val valorConta = when {
            binding.energy.isChecked -> 5.0 // Valor fixo para conta de energia
            binding.water.isChecked -> 10.0 // Valor fixo para conta de água
            else -> 0.0
        }





        // Cálculo do desconto
        val valorDesconto = when (tipoMaterial) {
            "papel" -> quantidade * 0.30f // Exemplo: 0.1 por kg
            "plástico" -> quantidade * 1.10f // Exemplo: 0.15 por kg
            "metal" -> quantidade * 5.50f // Exemplo: 0.2 por kg
            "vidro" -> quantidade * 0.50 // Exemplo: 0.25 por kg
            else -> 0.0


        }

        val final_resultado = valorConta + valorDesconto

        binding.resFinal.text = "R$ ${"%.2f".format(final_resultado)}"
    }

    private fun limparCampos() {
        // Verificar se há algum campo preenchido ou CheckBox marcado
        val isAnyFieldFilled = binding.nomeInfo.text.isNotEmpty() ||
                binding.infoEndereco.text.isNotEmpty() ||
                binding.infoQuant.text.isNotEmpty() ||
                binding.typePapel.isChecked ||
                binding.typePlast.isChecked ||
                binding.typeMetal.isChecked ||
                binding.typeGlass.isChecked ||
                binding.tipoEnd1.isChecked ||
                binding.tipoEnd2.isChecked ||
                binding.energy.isChecked ||
                binding.water.isChecked

        if (isAnyFieldFilled) {
            // Limpar os campos se houver algo preenchido
            binding.nomeInfo.text.clear()
            binding.infoEndereco.text.clear()
            binding.endnumer.text.clear()
            binding.endercomp.text.clear()
            binding.infoCep.text.clear()
            binding.infoQuant.text.clear()

            // Limpar os CheckBoxes
            binding.typePapel.isChecked = false
            binding.typePlast.isChecked = false
            binding.typeMetal.isChecked = false
            binding.typeGlass.isChecked = false
            binding.tipoEnd1.isChecked = false
            binding.tipoEnd2.isChecked = false
            binding.energy.isChecked = false
            binding.water.isChecked = false

            // Resetar o valor final
            binding.resFinal.text = "R$ 0"

            Toast.makeText(this, "Campos limpos com sucesso.", Toast.LENGTH_SHORT).show()
        } else {
            // Exibir mensagem se não houver dados preenchidos
            Toast.makeText(this, "Nenhum dado preenchido para limpar.", Toast.LENGTH_SHORT).show()
        }
    }

}
