package com.possible_triangle.flightlib.platform.services

import net.minecraft.core.Holder
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.ai.attributes.Attribute

interface IRegistries {
    val swimSpeed: Holder<Attribute>?

    fun registerSound(name: String): () -> SoundEvent
}
