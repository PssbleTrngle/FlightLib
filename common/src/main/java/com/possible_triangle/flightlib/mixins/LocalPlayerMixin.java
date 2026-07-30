package com.possible_triangle.flightlib.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.possible_triangle.flightlib.api.FlightKey;

import java.util.Optional;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Unique
    private boolean flightlib$alreadyActivated = false;

    @Unique
    private Optional<KeyMapping> flightlib$binding() {
        return FlightKey.ACTIVATE_ELYTRA.binding.filter(it -> !it.isUnbound());
    }

    @Inject(
            method = "aiStep()V",
            at = @At("HEAD")
    )
    public void calculateActivated(CallbackInfo ci, @Share("elytraActivated") LocalBooleanRef activated) {
        boolean pressed = flightlib$binding().map(KeyMapping::isDown).orElse(false);

        if(pressed != flightlib$alreadyActivated) {
            activated.set(pressed);
            flightlib$alreadyActivated = pressed;
        } else {
            activated.set(false);
        }
    }

    @Inject(
            method = "aiStep()V",
            at = @At("HEAD")
    )
    public void deactivateElytra(CallbackInfo ci, @Share("elytraActivated") LocalBooleanRef activated) {
        var self = (LocalPlayer) (Object) this;
        if (activated.get() && self.isFallFlying()) {
            self.stopFallFlying();
            activated.set(false);
        }
    }

    @ModifyExpressionValue(
            method = "aiStep()V",
            at = @At(value = "FIELD", ordinal = 2, target = "Lnet/minecraft/client/player/Input;jumping:Z", opcode = Opcodes.GETFIELD)
    )
    public boolean overwriteElytraActivation(boolean original, @Share("elytraActivated") LocalBooleanRef activated) {
        if (flightlib$binding().isPresent()) return activated.get();
        else return original;
    }

}
