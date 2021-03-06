

package com.moun.game.entity

import com.moun.game.CSGO
import com.moun.game.CSGO.ENTITY_SIZE
import com.moun.game.CSGO.clientDLL
import com.moun.game.CSGO.csgoEXE
import com.moun.game.CSGO.engineDLL
import com.moun.game.netvars.NetVarOffsets
import com.moun.game.netvars.NetVarOffsets.bBombDefused
import com.moun.game.netvars.NetVarOffsets.flC4Blow
import com.moun.game.netvars.NetVarOffsets.flDefuseCountDown
import com.moun.game.netvars.NetVarOffsets.hOwnerEntity
import com.moun.game.netvars.NetVarOffsets.szLastPlaceName
import com.moun.game.offsets.ClientOffsets
import com.moun.game.offsets.ClientOffsets.dwEntityList
import com.moun.game.offsets.EngineOffsets.dwGlobalVars
import com.moun.utils.extensions.uint

typealias Bomb = Long

internal fun Bomb.defused(): Boolean = csgoEXE.boolean(this + bBombDefused)

internal fun Bomb.blowTime() = csgoEXE.float(this + flC4Blow)

internal fun Bomb.defuseTime() = csgoEXE.float(this + flDefuseCountDown)

internal fun Bomb.defuserPointer(): Long = csgoEXE.uint(this + NetVarOffsets.hBombDefuser)

internal fun Bomb.defuser(): Player {
    val defuserPointer = defuserPointer()
    return if (defuserPointer > 0) clientDLL.uint(dwEntityList + ((defuserPointer and 0xFFF) - 1L) * ENTITY_SIZE) else 0
}

internal fun Bomb.owner() = csgoEXE.uint(this + NetVarOffsets.hOwnerEntity)

internal fun Bomb.carrier(): Player {
    val owner = owner()
    return if (owner > 0)
        CSGO.clientDLL.uint(ClientOffsets.dwEntityList + ((owner and 0xFFF) - 1L) * ENTITY_SIZE)
    else 0
}

internal fun Bomb.plantLocation(): String = carrier().location()