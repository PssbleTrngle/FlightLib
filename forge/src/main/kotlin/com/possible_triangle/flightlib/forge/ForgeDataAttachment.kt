package com.possible_triangle.flightlib.forge

import com.possible_triangle.flightlib.api.Constants
import com.possible_triangle.flightlib.logic.ISettingsStorage
import com.possible_triangle.flightlib.logic.JetpackSettings
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries

object ForgeDataAttachment {

    private val ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Constants.MOD_ID)

    @JvmField
    val SETTINGS_ATTACHMENT = ATTACHMENT_TYPES.register("settings") { ->
        AttachmentType.builder<JetpackSettings>(::emptyMap)
            .serialize(ISettingsStorage.KEYS_CODEC)
            .copyOnDeath()
            .build()
    }

    @JvmStatic
    fun register(modBus: IEventBus) {
        ATTACHMENT_TYPES.register(modBus)
    }

}