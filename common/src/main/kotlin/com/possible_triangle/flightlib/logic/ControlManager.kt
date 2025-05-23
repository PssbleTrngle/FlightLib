package com.possible_triangle.flightlib.logic

import com.mojang.blaze3d.platform.InputConstants
import com.possible_triangle.flightlib.FlightLibNetwork
import com.possible_triangle.flightlib.api.FlightKey
import com.possible_triangle.flightlib.logic.network.KeysSyncEvent
import net.minecraft.client.KeyMapping
import net.minecraft.network.syncher.EntityDataSerializer
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import java.util.*
import java.util.function.Consumer

object ControlManager {

    private val DATA_SERIALIZER = EntityDataSerializer.forValueType(FlightLibNetwork.KEYS_STREAM_CODEC)
    private val DATA_ACCESSOR = SynchedEntityData.defineId(Player::class.java, DATA_SERIALIZER)

    private val CACHE = mutableMapOf<UUID, MutableMap<FlightKey, Boolean>>()

    internal fun isPressed(key: FlightKey, entity: LivingEntity): Boolean {
        val keys = CACHE[entity.uuid] ?: entity.entityData.get(DATA_ACCESSOR)
        return keys?.get(key) ?: key.default
    }

    internal fun setKey(player: Player, key: FlightKey, pressed: Boolean) {
        val keys = CACHE.getOrPut(player.uuid) { mutableMapOf() }
        keys[key] = pressed
        player.entityData.set(DATA_ACCESSOR, keys)
    }

    fun registerKeybinds(registry: Consumer<KeyMapping>) {
        FlightKey.entries.forEach { key ->
            key.binding = Optional.ofNullable(key.defaultKey).map {
                KeyMapping(
                    "key.jetpack.${key.name.lowercase()}.description",
                    InputConstants.Type.KEYSYM,
                    it,
                    "key.categories.movement.jetpack"
                )
            }
            key.binding.ifPresent {
                registry.accept(it)
            }
        }
    }

    fun load(player: ServerPlayer) {
        val keys = player.entityData.get(DATA_ACCESSOR)
        CACHE[player.uuid] = keys.toMutableMap()
        val event = KeysSyncEvent(keys ?: return)
        FlightLibNetwork.KEYS_SYNC.send(player, event)
    }

}