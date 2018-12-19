

package com.moun.utils

data class Vector(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {
	
	fun set(x: Double, y: Double, z: Double) = apply {
		this.x = x
		this.y = y
		this.z = z
	}
	
	fun invalid() = x == 0.0 && y == 0.0 && z == 0.0
	
	fun valid() = !invalid()
	
}