package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AsparagusPlantBlock extends DoubleCropsBlock {

    public AsparagusPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (worldIn.getBlockState(pos.down()).getBlock().getTags().contains(new ResourceLocation("forge:dirt"))) {
            worldIn.setBlockState(pos.down(), RankineBlocks.ASPARAGUS_ROOT.get().getDefaultState());
            worldIn.removeBlock(pos, false);
            stack.shrink(1);
            worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_STEM_PLACE, SoundCategory.BLOCKS, 0.4f, 0.4f, false);
        }
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == RankineBlocks.ASPARAGUS_ROOT.get();
    }

    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }


}
