package com.possible_triangle.flightlib.fabric.services

import com.possible_triangle.flightlib.platform.services.INetwork
import com.possible_triangle.flightlib.platform.services.ServerMessageBus
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer

class FabricNetwork : INetwork {

    override fun <TMessage : CustomPacketPayload> clientToServer(
        type: CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, TMessage>,
        handler: (TMessage, ServerPlayer) -> Unit
    ): ServerMessageBus<TMessage> {
        PayloadTypeRegistry.playC2S().register(type.type(), type.codec())

        ServerPlayNetworking.registerGlobalReceiver(type.type()) {message, context ->
            handler(message, context.player())
        }

        return ServerMessageBus {
            ClientPlayNetworking.send(it)
        }
    }
}