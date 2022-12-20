package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class RankineBucketItem extends BucketItem {

    public RankineBucketItem(java.util.function.Supplier<? extends Fluid> supplier, Item.Properties builder) {
        super(supplier, builder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(levelIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
        if (blockhitresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = blockhitresult.getBlockPos();
            if (levelIn.getBlockState(blockpos).is(Blocks.CAULDRON) && VanillaIntegration.cauldron_map.containsKey(itemstack.getItem())) {
                if (!levelIn.isClientSide) {
                    levelIn.setBlockAndUpdate(blockpos, VanillaIntegration.cauldron_map.get(itemstack.getItem()).defaultBlockState());
                    playerIn.awardStat(Stats.FILL_CAULDRON);
                    playerIn.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
                    levelIn.playSound(null, playerIn, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                playerIn.swing(handIn);
                return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(itemstack, playerIn), levelIn.isClientSide());
            }
        }
        return super.use(levelIn, playerIn, handIn);
    }

}
