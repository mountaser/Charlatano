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

package com.moun.scripts

import com.moun.game.CSGO.clientDLL
import com.moun.game.CSGO.csgoEXE
import com.moun.game.entity.Player
import com.moun.game.entity.dead
import com.moun.game.hooks.onFlash
import com.moun.game.netvars.NetVarOffsets.flFlashMaxAlpha
import com.moun.game.offsets.ClientOffsets.dwLocalPlayer
import com.moun.settings.ENABLE_REDUCED_FLASH
import com.moun.settings.FLASH_MAX_ALPHA
import com.moun.utils.extensions.uint

fun reducedFlash() = onFlash {
	if (!ENABLE_REDUCED_FLASH) return@onFlash
	
	val me: Player = clientDLL.uint(dwLocalPlayer)
	if (me > 0 && !me.dead()) csgoEXE[me + flFlashMaxAlpha] = FLASH_MAX_ALPHA
}