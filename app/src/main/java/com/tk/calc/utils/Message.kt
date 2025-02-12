package com.tk.calc.utils

import android.content.Context
import android.widget.Toast

fun msg(context: Context, m: String){
	Toast.makeText(context, m, Toast.LENGTH_SHORT).show()
}