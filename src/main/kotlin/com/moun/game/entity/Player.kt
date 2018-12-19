/*
 * Moun: Free and open-source (FOSS) cheat for CS:GO/CS:CO
 * Copyright (C) 2017 - Thomas G. P. Nappo, Jonathan Beaudoin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.moun.game.entity

import com.moun.game.CSGO.ENTITY_SIZE
import com.moun.game.CSGO.clientDLL
import com.moun.game.CSGO.csgoEXE
import com.moun.game.Weapons
import com.moun.game.netvars.NetVarOffsets
import com.moun.game.netvars.NetVarOffsets.bHasDefuser
import com.moun.game.netvars.NetVarOffsets.bIsScoped
import com.moun.game.netvars.NetVarOffsets.dwBoneMatrix
import com.moun.game.netvars.NetVarOffsets.fFlags
import com.moun.game.netvars.NetVarOffsets.hActiveWeapon
import com.moun.game.netvars.NetVarOffsets.iHealth
import com.moun.game.netvars.NetVarOffsets.lifeState
import com.moun.game.netvars.NetVarOffsets.nTickBase
import com.moun.game.netvars.NetVarOffsets.vecPunch
import com.moun.game.netvars.NetVarOffsets.vecVelocity
import com.moun.game.netvars.NetVarOffsets.vecViewOffset
import com.moun.game.offsets.ClientOffsets.dwEntityList
import com.moun.settings.HEAD_BONE
import com.moun.settings.SERVER_TICK_RATE
import com.moun.utils.Angle
import com.moun.utils.Vector
import com.moun.utils.extensions.uint
import com.moun.utils.readCached
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap

typealias Player = Long

fun Player.weaponIndex(): Int {
	return ((csgoEXE.uint(this + hActiveWeapon) and 0xFFF) - 1).toInt()
}

fun Player.weaponEntity(): Weapon {
	return clientDLL.uint(dwEntityList + weaponIndex() * ENTITY_SIZE)
}

fun Player.weapon(weaponEntity: Weapon = weaponEntity()): Weapons {
	return weaponEntity.type()
}

internal fun Player.flags(): Int = csgoEXE.int(this + fFlags)

internal fun Player.onGround() = flags() and 1 == 1

internal fun Player.health(): Int = csgoEXE.int(this + iHealth)

internal fun Player.lifeState(): Int = csgoEXE.byte(this + lifeState).toInt()

internal fun Player.dead() = try {
	lifeState() != 0 || health() <= 0
} catch (t: Throwable) {
	false
}

private val player2Punch: Long2ObjectMap<Angle> = Long2ObjectOpenHashMap(255)

internal fun Player.punch(): Angle = readCached(player2Punch) {
	x = csgoEXE.float(it + vecPunch).toDouble()
	y = csgoEXE.float(it + vecPunch + 4).toDouble()
	z = 0.0
}

internal fun Player.shotsFired(): Int = csgoEXE.int(this + NetVarOffsets.iShotsFired)

internal fun Player.viewOffset(): Angle = Vector(csgoEXE.float(this + vecViewOffset).toDouble(),
		csgoEXE.float(this + vecViewOffset + 4).toDouble(),
		csgoEXE.float(this + vecViewOffset + 8).toDouble())

internal fun Player.velocity(): Angle = Vector(csgoEXE.float(this + vecVelocity).toDouble(),
		csgoEXE.float(this + vecVelocity + 4).toDouble(),
		csgoEXE.float(this + vecVelocity + 8).toDouble())

internal fun Player.boneMatrix() = csgoEXE.uint(this + dwBoneMatrix)

internal fun Player.bone(offset: Int, boneID: Int = HEAD_BONE, boneMatrix: Long = boneMatrix()) = csgoEXE.float(boneMatrix + ((0x30 * boneID) + offset)).toDouble()

internal fun Player.isScoped(): Boolean = csgoEXE.boolean(this + bIsScoped)

internal fun Player.hasDefuser(): Boolean = csgoEXE.boolean(this + bHasDefuser)

internal fun Player.time(): Double = csgoEXE.int(this + nTickBase) * (1.0 / SERVER_TICK_RATE)

internal fun Player.location(): String = csgoEXE.read(this + NetVarOffsets.szLastPlaceName, 32, true)?.getString(0)
		?: ""