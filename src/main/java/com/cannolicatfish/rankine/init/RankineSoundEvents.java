package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RankineSoundEvents {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ProjectRankine.MODID);

    public static final RegistryObject<SoundEvent> SHULKER_GAS_VACUUM_ABSORB = REGISTRY.register("shulker_gas_vacuum_absorb",() -> new SoundEvent(new ResourceLocation("rankine:shulker_gas_vacuum_absorb")));
    public static final RegistryObject<SoundEvent> SHULKER_GAS_VACUUM_RELEASE = REGISTRY.register("shulker_gas_vacuum_release",() -> new SoundEvent(new ResourceLocation("rankine:shulker_gas_vacuum_release")));
}
