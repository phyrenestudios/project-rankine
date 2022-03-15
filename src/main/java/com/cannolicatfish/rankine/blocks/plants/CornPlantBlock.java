package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class CornPlantBlock extends TripleCropsBlock {

    public CornPlantBlock(Properties properties) {
        super(properties);
    }

    protected IItemProvider getBaseSeedId() {
        return RankineItems.CORN_SEEDS.get();
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && state.getValue(AGE) > 2) {
            entityIn.makeStuckInBlock(state, new Vector3d((double)0.95F, 1.0D, (double)0.95F));
        }
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.getValue(AGE) == 7) {
            if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                double d0 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(RankineItems.CORN_EAR.get(), 1 + worldIn.getRandom().nextInt(3)));
                itementity.setDefaultPickUpDelay();
                worldIn.addFreshEntity(itementity);
            }
            switch (state.getValue(SECTION)) {
                case BOTTOM:
                    worldIn.setBlock(pos, RankineBlocks.CORN_STALK.get().defaultBlockState().setValue(SECTION, TripleBlockSection.BOTTOM), 19);
                    worldIn.setBlock(pos.above(1), RankineBlocks.CORN_STALK.get().defaultBlockState().setValue(SECTION, TripleBlockSection.MIDDLE), 19);
                    worldIn.setBlock(pos.above(2), RankineBlocks.CORN_STALK.get().defaultBlockState().setValue(SECTION, TripleBlockSection.TOP), 19);
                    break;
                case MIDDLE:
                    worldIn.setBlock(pos.below(), RankineBlocks.CORN_STALK.get().defaultBlockState().setValue(SECTION, TripleBlockSection.BOTTOM), 19);
                    worldIn.setBlock(pos, RankineBlocks.CORN_STALK.get().defaultBlockState().setValue(SECTION, TripleBlockSection.MIDDLE), 19);
                    worldIn.setBlock(pos.above(), RankineBlocks.CORN_STALK.get().defaultBlockState().setValue(SECTION, TripleBlockSection.TOP), 19);
                    break;
                case TOP:
                    worldIn.setBlock(pos.below(2), RankineBlocks.CORN_STALK.get().defaultBlockState().setValue(SECTION, TripleBlockSection.BOTTOM), 19);
                    worldIn.setBlock(pos.below(1), RankineBlocks.CORN_STALK.get().defaultBlockState().setValue(SECTION, TripleBlockSection.MIDDLE), 19);
                    worldIn.setBlock(pos, RankineBlocks.CORN_STALK.get().defaultBlockState().setValue(SECTION, TripleBlockSection.TOP), 19);
                    break;
            }
        }

        return super.use(state, worldIn, pos, player, handIn, hit);
    }
}
