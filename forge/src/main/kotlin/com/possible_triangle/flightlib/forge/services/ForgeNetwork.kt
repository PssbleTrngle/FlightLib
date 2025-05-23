package com.possible_triangle.flightlib.forge.services

import com.possible_triangle.flightlib.platform.services.ClientMessageBus
import com.possible_triangle.flightlib.platform.services.INetwork
import com.possible_triangle.flightlib.platform.services.ServerMessageBus
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.network.PacketDistributor
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import net.neoforged.neoforge.network.registration.PayloadRegistrar

class ForgeNetwork : INetwork {

    companion object {
        private val entries = arrayListOf<PayloadRegistrar.() -> Unit>()

        @JvmStatic
        fun register(modBus: IEventBus) {
            modBus.addListener { event: RegisterPayloadHandlersEvent ->
                val registrar = event.registrar("1")
                entries.forEach { it(registrar) }
            }
        }
    }

    override fun <TMessage : CustomPacketPayload> clientToServer(
        type: CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, TMessage>,
        handler: (TMessage, ServerPlayer) -> Unit
    ): ServerMessageBus<TMessage> {
        entries.add {
            playToServer(type.type(), type.codec()) { message, context ->
                handler(message, context.player() as ServerPlayer)
            }
        }

        return ServerMessageBus {
            PacketDistributor.sendToServer(it)
        }
    }

    override fun <TMessage : CustomPacketPayload> serverToClient(
        type: CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, TMessage>,
        handler: (TMessage, Player) -> Unit
    ): ClientMessageBus<TMessage> {
        entries.add {
            playToClient(type.type(), type.codec()) { message, context ->
                handler(message, context.player())
            }
        }

        return ClientMessageBus { player, it ->
            PacketDistributor.sendToPlayer(player, it)
        }
    }

}