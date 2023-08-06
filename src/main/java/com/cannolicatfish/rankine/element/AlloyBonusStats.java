package com.cannolicatfish.rankine.element;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.TierSortingRegistry;

public record AlloyBonusStats(int durability, float miningSpeed, float density, int attackDamage, int enchantability, Tier tier) {

    public ResourceLocation getTierResourceLocation() {
        if (TierSortingRegistry.getName(this.tier()) != null) {
            return TierSortingRegistry.getName(this.tier());
        }
        return new ResourceLocation("minecraft:wood");
    }
}

