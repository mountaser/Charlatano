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

import com.moun.game.entity.EntityType
import com.moun.game.forEntities
import com.moun.game.me
import com.moun.settings.TRIGGER_DURATION
import com.moun.utils.every

fun trigger() = every(TRIGGER_DURATION) {
	forEntities {
		val entity = it.entity
		if (entity <= 0 || me == entity || it.type != EntityType.CCSPlayer) return@forEntities
		
		
	}
}