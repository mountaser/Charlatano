

package com.moun.game.entity

import com.moun.game.CSGO.csgoEXE
import com.moun.game.Weapons
import com.moun.game.me
import com.moun.game.netvars.NetVarOffsets
import com.moun.game.netvars.NetVarOffsets.flNextPrimaryAttack
import com.moun.game.netvars.NetVarOffsets.iClip1
import com.moun.utils.extensions.uint

typealias Weapon = Long

internal fun Weapon.bullets() = csgoEXE.uint(this + iClip1)

internal fun Weapon.nextPrimaryAttack() = csgoEXE.float(this + flNextPrimaryAttack).toDouble()

internal fun Weapon.canFire(): Boolean = if (bullets() > 0) {
	val nextAttack = nextPrimaryAttack()
	nextAttack <= 0 || nextAttack < me.time()
} else false

internal fun Weapon.type(): Weapons {
	var id = 42
	if (this > 0)
		id = csgoEXE.short(this + NetVarOffsets.iItemDefinitionIndex).toInt()

	return Weapons[id]
}