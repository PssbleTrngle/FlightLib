package com.possible_triangle.flightlib.fabric.services

import com.google.common.base.Suppliers
import com.possible_triangle.flightlib.api.Constants
import com.possible_triangle.flightlib.platform.services.IRegistries
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import kotlin.jvm.optionals.getOrNull

class FabricRegistries : IRegistries {
    private val memoizedSwimSpeed =
        Suppliers.memoize {
            BuiltInRegistries.ATTRIBUTE.getHolder(ResourceLocation.fromNamespaceAndPath("porting_lib", "swim_speed")).getOrNull()
        }

    override val swimSpeed get() = memoizedSwimSpeed.get()

    override fun registerSound(name: String): () -> SoundEvent {
        val id = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
        val registered = Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id))
        return { registered }
    }
}
