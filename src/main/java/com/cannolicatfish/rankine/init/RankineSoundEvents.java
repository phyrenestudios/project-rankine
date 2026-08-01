package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RankineSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, ProjectRankine.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> TOTEM_OF_ENDURING_USE = SOUNDS.register("totem_of_enduring_use",() -> SoundEvent.createVariableRangeEvent(ResourceLocation.parse("rankine:totem_of_enduring_use")));
    public static final DeferredHolder<SoundEvent, SoundEvent> PENNING_TRAP_ABSORB = SOUNDS.register("penning_trap_absorb",() -> SoundEvent.createVariableRangeEvent(ResourceLocation.parse("rankine:penning_trap_absorb")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SHULKER_GAS_VACUUM_ABSORB = SOUNDS.register("shulker_gas_vacuum_absorb",() -> SoundEvent.createVariableRangeEvent(ResourceLocation.parse("rankine:shulker_gas_vacuum_absorb")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SHULKER_GAS_VACUUM_RELEASE = SOUNDS.register("shulker_gas_vacuum_release",() -> SoundEvent.createVariableRangeEvent(ResourceLocation.parse("rankine:shulker_gas_vacuum_release")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SEDIMENT_FAN_GEN = SOUNDS.register("sediment_fan_gen",() -> SoundEvent.createVariableRangeEvent(ResourceLocation.parse("rankine:sediment_fan_gen")));
}
