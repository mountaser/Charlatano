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

package com.moun.game.hooks

import com.moun.game.*
import com.moun.settings.CLEANUP_TIME
import com.moun.settings.MAX_ENTITIES
import com.moun.game.CSGO.GLOW_OBJECT_SIZE
import com.moun.game.CSGO.clientDLL
import com.moun.game.CSGO.csgoEXE
import com.moun.game.CSGO.engineDLL
import com.moun.game.entity.EntityType
import com.moun.game.netvars.NetVarOffsets
import com.moun.game.offsets.ClientOffsets
import com.moun.game.offsets.ClientOffsets.dwGlowObject
import com.moun.game.offsets.ClientOffsets.dwLocalPlayer
import com.moun.game.offsets.EngineOffsets
import com.moun.game.offsets.EngineOffsets.dwClientState
import com.moun.settings.DANGER_ZONE
import com.moun.settings.GARBAGE_COLLECT_ON_MAP_START
import com.moun.utils.every
import com.moun.utils.extensions.uint
import com.moun.utils.notInGame
import java.util.concurrent.atomic.AtomicLong
import kotlin.properties.Delegates

private val lastCleanup = AtomicLong(0L)

private val contexts = Array(MAX_ENTITIES) { EntityContext() }

private fun shouldReset() = System.currentTimeMillis() - lastCleanup.get() >= CLEANUP_TIME

private var state by Delegates.observable(SignOnState.MAIN_MENU) { _, old, new ->
    if (old != new) {
//        println("state change from : $old to $new")
        if (new == SignOnState.IN_GAME) {
            val gameRulesProxy = CSGO.clientDLL.long(ClientOffsets.dwGameRulesProxy)
            val survivalDecisionType = CSGO.csgoEXE.int(gameRulesProxy + NetVarOffsets.SurvivalGameRuleDecisionTypes)
            DANGER_ZONE = survivalDecisionType != 0
            if (GARBAGE_COLLECT_ON_MAP_START) {
                System.gc()
            }
            notInGame = false
        } else {
            notInGame = true
        }
    }
}

private fun reset() {
    for (cacheableList in entitiesValues)
        cacheableList?.clear()
    lastCleanup.set(System.currentTimeMillis())
}

fun constructEntities() = every(512) {
    state = SignOnState[CSGO.csgoEXE.int(clientState + EngineOffsets.dwSignOnState)]
    me = clientDLL.uint(dwLocalPlayer)
    if (me <= 0) return@every

    clientState = engineDLL.uint(dwClientState)

    val glowObject = clientDLL.uint(dwGlowObject)
    val glowObjectCount = clientDLL.int(dwGlowObject + 4)

    //maybe we should do this only at map load too? i'm not sure...
    if (shouldReset()) reset()

    for (glowIndex in 0..glowObjectCount) {
        val glowAddress = glowObject + (glowIndex * GLOW_OBJECT_SIZE)
        val entity = csgoEXE.uint(glowAddress)
        val type = EntityType.byEntityAddress(entity)

        val context = contexts[glowIndex].set(entity, glowAddress, glowIndex, type)

        with(entities[type]!!) {
            if (!contains(context)) add(context)
        }
    }
}