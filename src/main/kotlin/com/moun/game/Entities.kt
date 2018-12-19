

package com.moun.game

import com.moun.settings.MAX_ENTITIES
import com.moun.game.entity.Entity
import com.moun.game.entity.EntityType
import com.moun.game.entity.Player
import com.moun.game.entity.bone
import com.moun.utils.Angle
import com.moun.utils.collections.CacheableList
import com.moun.utils.collections.ListContainer
import com.moun.utils.readCached
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import java.util.*

@Volatile
var me: Player = 0
@Volatile
var clientState: ClientState = 0

typealias EntityContainer = ListContainer<EntityContext>
typealias EntityList = Object2ObjectArrayMap<EntityType, CacheableList<EntityContext>>

private val cachedResults = Int2ObjectArrayMap<EntityContainer>(EntityType.size)

val entitiesValues = arrayOfNulls<CacheableList<EntityContext>>(MAX_ENTITIES)
var entitiesValuesCounter = 0

val entities: Object2ObjectMap<EntityType, CacheableList<EntityContext>>
		= EntityList(EntityType.size).apply {
	for (type in EntityType.cachedValues) {
		val cacheableList = CacheableList<EntityContext>(MAX_ENTITIES)
		put(type, cacheableList)
		entitiesValues[entitiesValuesCounter++] = cacheableList
	}
}

fun entityByType(type: EntityType): EntityContext? = entities[type]?.firstOrNull()

internal inline fun forEntities(types: Array<EntityType> = EntityType.cachedValues,
                                crossinline body: (EntityContext) -> Unit) {
	val hash = Arrays.hashCode(types)
	val container = cachedResults.get(hash) ?: EntityContainer(EntityType.size)
	
	if (container.empty()) {
		for (type in types) if (type != EntityType.NULL) {
			val cacheableList = entities[type]!!
			container.addList(cacheableList)
			cachedResults.put(hash, container)
		}
	}
	
	container.forEach(body)
}

val entityToBones: Long2ObjectMap<Angle> = Long2ObjectOpenHashMap()

fun Entity.bones(boneID: Int) = readCached(entityToBones) {
	x = bone(0xC, boneID)
	y = bone(0x1C, boneID)
	z = bone(0x2C, boneID)
}