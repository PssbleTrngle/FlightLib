package com.possible_triangle.flightlib.platform.services

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer

fun interface ServerMessageBus<TMessage> {
    fun send(message: TMessage)
}

interface INetwork {

    fun <TMessage : CustomPacketPayload> clientToServer(
        type: CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, TMessage>,
        handler: (TMessage, ServerPlayer) -> Unit,
    ): ServerMessageBus<TMessage>

}