package com.cannolicatfish.rankine.items.tools;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;

public class KnifeItem extends SwordItem {
    private final float attackDamage;
    private final float attackSpeed;

    public KnifeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.attackSpeed = attackSpeedIn;
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.matchesBlock(Blocks.COBWEB)) {
            return 15.0F;
        } else if (state.isIn(BlockTags.LEAVES)) {
            return 0.5F;
        } else {
            Material material = state.getMaterial();
            return material != Material.CORAL && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }


}



