package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.blocks.gases.PitchModulating;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.event.PlayLevelSoundEvent;

public class PlaySoundAtEntityHandler {

    // 1.21.1: PlaySoundAtEntityEvent -> PlayLevelSoundEvent.AtEntity;
    // getDefaultPitch/setPitch -> getOriginalPitch/setNewPitch.
    public static void onPlaySound(PlayLevelSoundEvent.AtEntity event) {
        if (event.getEntity() != null) {
            BlockPos headPosition = BlockPos.containing(event.getEntity().getEyePosition());
            if (event.getEntity().level().getBlockState(headPosition).getBlock() instanceof PitchModulating pitchModulating) {
                float pitchMult = pitchModulating.getPitchMultiplier();
                event.setNewPitch(Mth.clamp(event.getOriginalPitch() * pitchMult,0,2));
            }
        }
    }
}
