package com.possible_triangle.flightlib.forge;

import com.possible_triangle.dungeon.forge.platform.ForgeNetwork;
import com.possible_triangle.flightlib.api.Constants;
import com.possible_triangle.flightlib.forge.compat.CuriosCompat;
import com.possible_triangle.flightlib.forge.services.ForgeRegistries;
import com.possible_triangle.flightlib.init.CommonClass;
import com.possible_triangle.flightlib.logic.ControlManager;
import com.possible_triangle.flightlib.logic.ControlSender;
import com.possible_triangle.flightlib.logic.JetpackLogic;
import net.minecraft.client.player.LocalPlayer;
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

        if(dist == Dist.CLIENT) {
            clientInit(modBus);
        }

        ForgeNetwork.Companion.register(modBus);
        ForgeRegistries.Companion.register(modBus);
        ForgeSources.INSTANCE.register();
        CuriosCompat.INSTANCE.register();

        NeoForge.EVENT_BUS.addListener((PlayerTickEvent event) -> JetpackLogic.INSTANCE.onTick(event.getEntity()));

        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerChangedDimensionEvent event) -> ControlManager.INSTANCE.reset(event.getEntity()));
        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedOutEvent event) -> ControlManager.INSTANCE.reset(event.getEntity()));
    }

    private void clientInit(IEventBus modBus) {
        CommonClass.INSTANCE.clientInit();

        modBus.addListener((RegisterKeyMappingsEvent event) -> ControlManager.INSTANCE.registerKeybinds(event::register));
        NeoForge.EVENT_BUS.addListener((InputEvent.Key event) -> ControlSender.INSTANCE.checkKeys());
        NeoForge.EVENT_BUS.addListener((PlayerTickEvent event) -> {
            if(event.getEntity() instanceof LocalPlayer player) ControlSender.INSTANCE.onTick(player);
        });
    }

}
