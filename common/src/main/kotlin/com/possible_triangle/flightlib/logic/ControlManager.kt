package com.possible_triangle.flightlib.logic

import com.mojang.blaze3d.platform.InputConstants
import com.possible_triangle.flightlib.FlightLibNetwork
import com.possible_triangle.flightlib.api.FlightKey
import com.possible_triangle.flightlib.logic.network.KeysSyncEvent
import net.minecraft.client.KeyMapping
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import java.util.*
import java.util.function.Consumer

object ControlManager {
    internal fun isPressed(
        key: FlightKey,
        entity: LivingEntity,
    ): Boolean {
        if (entity !is ISettingsStorage) return false
        return entity.isPressed(key)
    }

    internal fun setKey(
        entity: LivingEntity,
        key: FlightKey,
        pressed: Boolean,
    ) {
        if (entity !is ISettingsStorage) return
        entity.setKey(key, pressed)
    }

    @JvmStatic
    fun registerKeybinds(registry: Consumer<KeyMapping>) {
        FlightKey.entries.forEach { key ->
            key.binding =
                Optional.ofNullable(key.defaultKey).map {
                    KeyMapping(
                        "key.jetpack.${key.name.lowercase()}.description",
                        InputConstants.Type.KEYSYM,
                        it,
                        "key.categories.movement.jetpack",
                    )
                }
            key.binding.ifPresent {
                registry.accept(it)
            }
        }
    }

    fun load(player: ServerPlayer) {
        val keys = player.`flightlib$get`()
        val event = KeysSyncEvent(keys)
        FlightLibNetwork.KEYS_SYNC.send(player, event)
    }
}
