package com.macuguita.set_time.mixin;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TimeCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TimeCommand.class)
public class TimeCommandMixin {

    @Inject(method = "executeSet", at = @At("HEAD"), cancellable = true)
    private static void modifyExecuteSet(ServerCommandSource source, int time, CallbackInfoReturnable<Integer> cir) {
        for (ServerWorld serverWorld : source.getServer().getWorlds()) {
            long currentTime = serverWorld.getTimeOfDay();
            long dayTime = currentTime % 24000;

            if (time == 1000 || time == 6000 || time == 13000 || time == 18000) {
                if (time <= dayTime) {
                    time += 24000;
                }

                long newTime = (currentTime - dayTime) + time;
                serverWorld.setTimeOfDay(newTime);
            } else {
                serverWorld.setTimeOfDay(time);
            }
        }

        int adjustedTime = (int) (source.getWorld().getTimeOfDay() % 24000);
        source.sendFeedback(() -> Text.translatable("commands.time.set", adjustedTime), true);
        cir.setReturnValue(adjustedTime);
    }
}
