package com.tk.calc

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.view.View
import java.math.BigDecimal
import java.text.DecimalFormat
import com.tk.calc.utils.msg
import com.tk.calc.utils.percent

public class MainActivity : Activity() {

    private var c = 0
    private lateinit var btnOpr: Button
    private lateinit var btnRes: Button
    private lateinit var txOpr1: TextView
    private lateinit var txOpr2: TextView
    private lateinit var txNum: TextView
    private lateinit var txOprS: TextView
    private lateinit var txSin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialization()
		
		if (savedInstanceState != null) {
            txOpr1.text = savedInstanceState.getString("txOpr1_key")
            txOpr2.text = savedInstanceState.getString("txOpr2_key")
            txNum.text = savedInstanceState.getString("txNum_key")
            txOprS.text = savedInstanceState.getString("txOprS_key")
            txSin.visibility = if (savedInstanceState.getBoolean("txSinVisible_key")) View.VISIBLE else View.GONE
        }
    }
	
	// Manter dados na UI ao girar tela ou mudar estados da UI
	override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("txOpr1_key", txOpr1.text.toString())
		outState.putString("txOpr2_key", txOpr2.text.toString())
		outState.putString("txNum_key", txNum.text.toString())
		outState.putString("txOprS_key", txOprS.text.toString())
        outState
	}

    fun initialization() {
        txOpr1 = findViewById(R.id.txopr1)
        txOpr2 = findViewById(R.id.txopr2)
        txOprS = findViewById(R.id.txoprs)
        txNum  = findViewById(R.id.txnum)
        txSin  = findViewById(R.id.txsignal)

        val btnIds = listOf(
            R.id.txclear, R.id.txdel, R.id.txpercent, R.id.txdivision,
            R.id.txone, R.id.txtwo, R.id.txthree, R.id.txmultiply, R.id.txfour,
            R.id.txfive, R.id.txsix, R.id.txsubtraction, R.id.txseven, R.id.txeight,
            R.id.txnine, R.id.txaddition, R.id.txdot, R.id.txzero, R.id.txcomma,
            R.id.txequal
        )

        // Buscar lista de ids dos botões
        btnIds.forEach { id ->
			// Selecionar id do btn clicado
            val btnVal: Button = findViewById(id)
			
            btnVal.setOnClickListener {
                val txclick = btnVal.text.toString()

                when (id) {
                    R.id.txclear -> clearAll()
                    R.id.txdel -> txNum.text = txNum.text.dropLast(1)
                    R.id.txaddition, R.id.txdivision,
                    R.id.txmultiply, R.id.txsubtraction -> {
                        txOpr1.text = txNum.text.toString()
                        txSin.visibility = View.VISIBLE
                        txSin.text = txclick
                        txOprS.text = txclick
                        txNum.text = ""
                    }
                    R.id.txpercent -> {
						calculatePercentage()
						txSin.text = txclick
					}
                    R.id.txequal -> calculateResult()
                    else -> txNum.append(txclick)
                }
            }
        }
    }

    private fun clearAll() {
        txOpr1.text = ""
        txOpr2.text = ""
        txOprS.text = ""
        txNum.text = ""
        txSin.visibility = View.GONE
        msg(this, "Limpo")
    }

    private fun calculateResult() {
        if (txNum.text.isNotEmpty() && txOpr1.text.isNotEmpty()) {
            txOpr2.text = txNum.text.toString()
            val result = calc(txOprS.text.first())
            txNum.text = result
            txSin.visibility = View.GONE
        } else {
            msg(this, "Digite o valor!")
        }
    }

    fun calc(op: Char): String {
		val num1 = txOpr1.text.toString().toDouble()
		val num2 = txOpr2.text.toString().toDouble()
		val result = when (op) {
			'+' -> num1 + num2
			'-' -> num1 - num2
			'*' -> num1 * num2
			'/' -> num1 / num2
			else -> null
		}
		return result?.toString() ?: "Erro"
	}

	private fun calculatePercentage() {
		if (txNum.text.isNotEmpty() && txOpr1.text.isNotEmpty()) {
			txOpr2.text = txNum.text.toString()
			val v1 = txOpr1.text.toString().toDouble()
			val v2 = txNum.text.toString().toDouble()
			
			val result = percent(v1, txOprS.text.first(), v2)
			txNum.text = result.toString()
		} else {
			msg(this, "Digite os valores para calcular o percentual!")
		}
    }

    private fun formatNumber(number: BigDecimal): String {
        val decimalFormat = DecimalFormat("#,##0.##########")
        return decimalFormat.format(number)
    }
}