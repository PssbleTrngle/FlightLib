package com.possible_triangle.flightlib.neoforge;

import com.possible_triangle.flightlib.api.Constants;
import com.possible_triangle.flightlib.init.CommonClass;
import com.possible_triangle.flightlib.logic.ControlManager;
import com.possible_triangle.flightlib.logic.ControlSender;
import com.possible_triangle.flightlib.logic.JetpackLogic;
import com.possible_triangle.flightlib.neoforge.compat.CuriosCompat;
import com.possible_triangle.flightlib.neoforge.services.ForgeNetwork;
import com.possible_triangle.flightlib.neoforge.services.ForgeRegistries;
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
public class NeoForgeEntrypoint {

    public NeoForgeEntrypoint(IEventBus modBus, Dist dist) {
        CommonClass.init();

        if (dist == Dist.CLIENT) {
            clientInit(modBus);
        }

        ForgeNetwork.register(modBus);
        ForgeRegistries.register(modBus);
        ForgeSources.register();
        CuriosCompat.register();
        ForgeDataAttachment.register(modBus);

        NeoForge.EVENT_BUS.addListener((PlayerTickEvent.Pre event) -> JetpackLogic.INSTANCE.onTick(event.getEntity()));

        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedInEvent event) -> {
            if (event.getEntity() instanceof ServerPlayer player) {
                ControlManager.INSTANCE.load(player);
            }
        });
        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerChangedDimensionEvent event) -> {
            if (event.getEntity() instanceof ServerPlayer player) {
                ControlManager.INSTANCE.load(player);
            }
        });
        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerRespawnEvent event) -> {
            if (event.getEntity() instanceof ServerPlayer player) {
                ControlManager.INSTANCE.load(player);
            }
        });

    }

    private void clientInit(IEventBus modBus) {
        CommonClass.clientInit();

        modBus.addListener((RegisterKeyMappingsEvent event) -> ControlManager.registerKeybinds(event::register));
        NeoForge.EVENT_BUS.addListener((InputEvent.Key event) -> ControlSender.INSTANCE.checkKeys());
        NeoForge.EVENT_BUS.addListener((PlayerTickEvent.Pre event) -> {
            if (event.getEntity() instanceof LocalPlayer player) ControlSender.INSTANCE.onTick(player);
        });

    }

}
