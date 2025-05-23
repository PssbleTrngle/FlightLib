package com.possible_triangle.flightlib.logic

import com.possible_triangle.flightlib.FlightLibNetwork
import com.possible_triangle.flightlib.api.FlightKey
import com.possible_triangle.flightlib.api.IFlightApi
import com.possible_triangle.flightlib.logic.network.KeyPressedEvent
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer

object ControlSender {

    private var LAST_PRESS = mutableMapOf<FlightKey, Long>()

    private fun FlightKey.canPressAgain(): Boolean {
        return LAST_PRESS[this]?.let {
            (System.currentTimeMillis() - it) > 150
        } ?: true
    }

    private fun send(event: KeyPressedEvent) {
        val player = Minecraft.getInstance().player ?: return
        FlightLibNetwork.KEY_PRESSED.send(event)
        ControlManager.setKey(player, event.key, event.pressed)
    }

    fun checkKeys() {
        val player = Minecraft.getInstance().player ?: return
        IFlightApi.INSTANCE.findJetpack(player) ?: return

        FlightKey.entries
            .filter { it.toggle }
            .filter { it.binding.get().isDown }
            .filter { it.canPressAgain() }
            .forEach { key ->
                LAST_PRESS[key] = System.currentTimeMillis()
                send(KeyPressedEvent(key, !key.isPressed(player), true))
            }
    }

    fun onTick(player: LocalPlayer) {
        FlightKey.values().filter { !it.toggle && it.binding.isPresent }.forEach {
            send(KeyPressedEvent(it, it.binding.get().isDown))
        }

        send(KeyPressedEvent(FlightKey.UP, player.input.jumping))
        send(KeyPressedEvent(FlightKey.LEFT, player.input.left))
        send(KeyPressedEvent(FlightKey.RIGHT, player.input.right))
        send(KeyPressedEvent(FlightKey.FORWARD, player.input.forwardImpulse > 0))
        send(KeyPressedEvent(FlightKey.BACKWARD, player.input.forwardImpulse < 0))
    }

}