

package com.moun.scripts.esp

import com.badlogic.gdx.graphics.Color
import com.moun.game.CSGO.csgoEXE
import com.moun.game.CSGO.engineDLL
import com.moun.game.entity.*
import com.moun.game.entity.EntityType.Companion.ccsPlayer
import com.moun.game.forEntities
import com.moun.game.me
import com.moun.game.offsets.EngineOffsets.pStudioModel
import com.moun.game.worldToScreen
import com.moun.overlay.MounOverlay
import com.moun.settings.SKELETON_ESP
import com.moun.utils.Vector
import com.moun.utils.collections.CacheableList
import com.moun.utils.extensions.uint
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap

private val bones = Array(2048) { Line() }
private val entityBones = Long2ObjectArrayMap<CacheableList<Pair<Int, Int>>>()
private var currentIdx = 0

internal fun skeletonEsp() {
	MounOverlay {
		if (!SKELETON_ESP) return@MounOverlay
		
		forEntities(ccsPlayer) {
			val entity = it.entity
			if (entity > 0 && entity != me && !entity.dead() && !entity.dormant()) {
				(entityBones.get(entity) ?: CacheableList<Pair<Int, Int>>(20)).apply {
					if (isEmpty()) {
						//val entityModel = entity.model() //no longer needed since we bypass the findStudioModel call for updated pointer method
						val studioModel = csgoEXE.uint(entity.studioHdr())
						val numbones = csgoEXE.uint(studioModel + 0x9C).toInt()
						val boneIndex = csgoEXE.uint(studioModel + 0xA0)
						
						var offset = 0
						for (idx in 0..numbones - 1) {
							val parent = csgoEXE.int(studioModel + boneIndex + 0x4 + offset)
							if (parent != -1) {
								val flags = csgoEXE.uint(studioModel + boneIndex + 0xA0 + offset) and 0x100
								if (flags != 0L) add(parent to idx)
							}
							
							offset += 216
						}
						
						entityBones.put(entity, this)
					}
					
					forEach { drawBone(entity, it.first, it.second) }
				}
			}
		}
		
		shapeRenderer.apply {
			begin()
			for (i in 0..currentIdx - 1) {
				val bone = bones[i]
				color = bone.color
				line(bone.sX.toFloat(), bone.sY.toFloat(), bone.eX.toFloat(), bone.eY.toFloat())
			}
			end()
		}
		
		currentIdx = 0
	}
}

private fun findStudioModel(pModel: Long): Long {
	val type = csgoEXE.uint(pModel + 0x0110)
	if (type != 3L) return 0 // Type is not Studiomodel
	
	var handle = csgoEXE.uint(pModel + 0x0138) and 0xFFFF
	if (handle == 0xFFFFL) return 0 // Handle is not valid
	handle = handle shl 4
	
	var studioModel = engineDLL.uint(pStudioModel)
	studioModel = csgoEXE.uint(studioModel + 0x28)
	studioModel = csgoEXE.uint(studioModel + handle + 0xC)
	
	return csgoEXE.uint(studioModel + 0x74)
}

private val colors: Array<Color> = Array(101) {
	val red = 1 - (it / 100f)
	val green = (it / 100f)
	
	Color(red, green, 0f, 1f)
}

private val startBone = Vector()
private val endBone = Vector()

private val startDraw = Vector()
private val endDraw = Vector()

private fun drawBone(target: Player, start: Int, end: Int) {
	val boneMatrix = target.boneMatrix()
	startBone.set(
			target.bone(0xC, start, boneMatrix),
			target.bone(0x1C, start, boneMatrix),
			target.bone(0x2C, start, boneMatrix))
	endBone.set(
			target.bone(0xC, end, boneMatrix),
			target.bone(0x1C, end, boneMatrix),
			target.bone(0x2C, end, boneMatrix))
	
	if (worldToScreen(startBone, startDraw) && worldToScreen(endBone, endDraw)) {
		bones[currentIdx].apply {
			sX = startDraw.x.toInt()
			sY = startDraw.y.toInt()
			eX = endDraw.x.toInt()
			eY = endDraw.y.toInt()
			val health = target.health()
			if (health >= 0 && health < colors.size) {
				color = colors[health]
			}
		}
		currentIdx++
	}
}

private data class Line(var sX: Int = -1, var sY: Int = -1,
                        var eX: Int = -1, var eY: Int = -1,
                        var color: Color = Color.WHITE)