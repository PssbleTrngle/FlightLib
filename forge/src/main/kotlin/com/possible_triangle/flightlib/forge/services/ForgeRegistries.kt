package com.possible_triangle.flightlib.forge.services

import com.possible_triangle.flightlib.api.Constants.MOD_ID
import com.possible_triangle.flightlib.platform.services.IRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.sounds.SoundEvent
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.common.NeoForgeMod
import net.neoforged.neoforge.registries.DeferredRegister

class ForgeRegistries : IRegistries {

    companion object {
        private val SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, MOD_ID)

        fun register(bus: IEventBus) {
            SOUNDS.register(bus)
        }
    }

    override val swimSpeed get() = NeoForgeMod.SWIM_SPEED

    override fun registerSound(name: String): () -> SoundEvent {
        val registered = SOUNDS.register(name, SoundEvent::createVariableRangeEvent)
        return registered::get
    }
}