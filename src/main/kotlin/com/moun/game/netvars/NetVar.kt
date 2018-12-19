

package com.moun.game.netvars

import com.moun.game.netvars.NetVars.hashClassAndVar
import com.moun.game.netvars.NetVars.map
import kotlin.reflect.KProperty

class NetVar(val className: String, var varName: String?, val offset: Int, val index: Int) {
	
	private var value = -1L
	
	operator fun getValue(thisRef: Any?, property: KProperty<*>): Long {
		if (varName == null) varName = "m_${property.name}" + if (index < 0) "" else "[$index]"
		if (value == -1L) value = map[hashClassAndVar(className, varName!!)]!!.offset + offset
		return value
	}
	
	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
		this.value = value
	}
	
}

fun netVar(className: String, varName: String? = null, offset: Int = 0, index: Int = -1)
		= NetVar(className, if (varName != null && index >= 0) "$varName[$index]" else varName, offset, index)