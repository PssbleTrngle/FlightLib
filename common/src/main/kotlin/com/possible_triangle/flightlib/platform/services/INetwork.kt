package com.possible_triangle.flightlib.platform.services

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player

fun interface ServerMessageBus<TMessage> {
    fun send(message: TMessage)
}

fun interface ClientMessageBus<TMessage> {
    fun send(
        player: ServerPlayer,
        message: TMessage,
    )
}

interface INetwork {
    fun <TMessage : CustomPacketPayload> clientToServer(
        type: CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, TMessage>,
        handler: (TMessage, ServerPlayer) -> Unit,
    ): ServerMessageBus<TMessage>

    fun <TMessage : CustomPacketPayload> serverToClient(
        type: CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, TMessage>,
        handler: (TMessage, Player) -> Unit,
    ): ClientMessageBus<TMessage>
}
