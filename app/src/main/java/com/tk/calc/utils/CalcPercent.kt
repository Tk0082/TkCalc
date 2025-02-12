package com.tk.calc.utils

fun percent(v1:Double, opr: Char, v2: Double): Double {
	val perc = (v2 / 100) * v1
	val resultado = when (opr) {
		'+' -> v1 + perc
		'-' -> v1 - perc
		'*' -> v1 * perc
		'/' -> if (v2 != 0.0) v1 / perc else 0.0
		else -> throw IllegalArgumentException("Operação inválida")
	}
	return resultado
}