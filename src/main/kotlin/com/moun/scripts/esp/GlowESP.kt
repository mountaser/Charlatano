

package com.moun.scripts.esp

import com.moun.game.CSGO.csgoEXE
import com.moun.game.Color
import com.moun.game.entity.*
import com.moun.game.forEntities
import com.moun.game.me
import com.moun.settings.*
import com.moun.utils.every

internal fun glowEsp() = every(4) {
	if (!GLOW_ESP) return@every
	
	val myTeam = me.team()
	
	forEntities {
		val entity = it.entity
		if (entity <= 0 || me == entity) return@forEntities
		
		val glowAddress = it.glowAddress
		if (glowAddress <= 0) return@forEntities
		
		when (it.type) {
			EntityType.CCSPlayer -> {
				if (entity.dead() || (!SHOW_DORMANT && entity.dormant())) return@forEntities
				
				val entityTeam = entity.team()
				val team = !DANGER_ZONE && myTeam == entityTeam
				var health = (entity.health() * 2 + 5).toInt()
				
				if (SHOW_ENEMIES && !team) {
					if (!HEALTH_BASED_GLOW) {
					    glowAddress.glow(ENEMY_COLOR)
  						entity.chams(ENEMY_COLOR)
  					}
  					else {
  						glowAddress.glow(Color(health, 0, 255-health))
  						entity.chams(Color(health, 0, 255-health))
  					}
				} else if (SHOW_TEAM && team) {
					glowAddress.glow(TEAM_COLOR)
					entity.chams(TEAM_COLOR)
				}
			}
			EntityType.CPlantedC4, EntityType.CC4 -> if (SHOW_BOMB) {
				glowAddress.glow(BOMB_COLOR)
				entity.chams(BOMB_COLOR)
			}
			else ->
				if (SHOW_WEAPONS && it.type.weapon) glowAddress.glow(WEAPON_COLOR)
				else if (SHOW_GRENADES && it.type.grenade) glowAddress.glow(GRENADE_COLOR)
		}
	}
}

private fun Entity.glow(color: Color) {
	csgoEXE[this + 0x4] = color.red / 255F
	csgoEXE[this + 0x8] = color.green / 255F
	csgoEXE[this + 0xC] = color.blue / 255F
	csgoEXE[this + 0x10] = color.alpha.toFloat()
	csgoEXE[this + 0x24] = true
}

private fun Entity.chams(color: Color) {
	if (COLOR_MODELS) {
		csgoEXE[this + 0x70] = color.red.toByte()
		csgoEXE[this + 0x71] = color.green.toByte()
		csgoEXE[this + 0x72] = color.blue.toByte()
		csgoEXE[this + 0x73] = color.alpha.toByte()
	}
}