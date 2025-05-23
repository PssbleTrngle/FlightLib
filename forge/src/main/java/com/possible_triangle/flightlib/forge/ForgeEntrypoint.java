package com.possible_triangle.flightlib.forge;

import com.possible_triangle.flightlib.api.Constants;
import com.possible_triangle.flightlib.forge.compat.CuriosCompat;
import com.possible_triangle.flightlib.forge.services.ForgeNetwork;
import com.possible_triangle.flightlib.forge.services.ForgeRegistries;
import com.possible_triangle.flightlib.init.CommonClass;
import com.possible_triangle.flightlib.logic.ControlManager;
import com.possible_triangle.flightlib.logic.ControlSender;
import com.possible_triangle.flightlib.logic.JetpackLogic;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@Mod(Constants.MOD_ID)
public class ForgeEntrypoint {

    public ForgeEntrypoint(IEventBus modBus, Dist dist) {
        CommonClass.INSTANCE.init();

        if (dist == Dist.CLIENT) {
            clientInit(modBus);
        }

        ForgeNetwork.Companion.register(modBus);
        ForgeRegistries.Companion.register(modBus);
        ForgeSources.INSTANCE.register();
        CuriosCompat.INSTANCE.register();

        NeoForge.EVENT_BUS.addListener((PlayerTickEvent.Pre event) -> JetpackLogic.INSTANCE.onTick(event.getEntity()));
    }

    private void clientInit(IEventBus modBus) {
        CommonClass.INSTANCE.clientInit();

        modBus.addListener((RegisterKeyMappingsEvent event) -> ControlManager.INSTANCE.registerKeybinds(event::register));
        NeoForge.EVENT_BUS.addListener((InputEvent.Key event) -> ControlSender.INSTANCE.checkKeys());
        NeoForge.EVENT_BUS.addListener((PlayerTickEvent.Pre event) -> {
            if (event.getEntity() instanceof LocalPlayer player) ControlSender.INSTANCE.onTick(player);
        });

        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedInEvent event) -> {
            if (event.getEntity() instanceof ServerPlayer player) {
                ControlManager.INSTANCE.load(player);
            }
        });
    }

}
