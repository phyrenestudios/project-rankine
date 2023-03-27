package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.loot.SurfRodModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS.get(), ProjectRankine.MODID);

    public static final RegistryObject<GlobalLootModifierSerializer<?>> SURF_ROD = LOOT_MODIFIERS.register("surf_rod_modifier", () -> SurfRodModifier.SERIALIZER);

}
