package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityModifierBlock extends Block {
    public EntityModifierBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn.isLiving())
        {
            ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(Effects.SPEED, 1, 0));
        }
    }
}
