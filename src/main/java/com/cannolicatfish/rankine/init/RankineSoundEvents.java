package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RankineSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ProjectRankine.MODID);

    public static final RegistryObject<SoundEvent> TOTEM_OF_ENDURING_USE = SOUNDS.register("totem_of_enduring_use",() -> new SoundEvent(new ResourceLocation("rankine:totem_of_enduring_use")));
    public static final RegistryObject<SoundEvent> PENNING_TRAP_ABSORB = SOUNDS.register("penning_trap_absorb",() -> new SoundEvent(new ResourceLocation("rankine:penning_trap_absorb")));
    public static final RegistryObject<SoundEvent> SHULKER_GAS_VACUUM_ABSORB = SOUNDS.register("shulker_gas_vacuum_absorb",() -> new SoundEvent(new ResourceLocation("rankine:shulker_gas_vacuum_absorb")));
    public static final RegistryObject<SoundEvent> SHULKER_GAS_VACUUM_RELEASE = SOUNDS.register("shulker_gas_vacuum_release",() -> new SoundEvent(new ResourceLocation("rankine:shulker_gas_vacuum_release")));
    public static final RegistryObject<SoundEvent> SEDIMENT_FAN_GEN = SOUNDS.register("sediment_fan_gen",() -> new SoundEvent(new ResourceLocation("rankine:sediment_fan_gen")));
}
