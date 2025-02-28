package com.macuguita.set_time.forge;

import com.macuguita.set_time.Set_time;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod(value = Set_time.MOD_ID)
public final class Set_timeForge {
    public Set_timeForge() {
        // Run our common setup.
        Set_time.init();
    }
}
