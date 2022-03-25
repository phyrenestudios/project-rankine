package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.ItemLike;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class CornPlantBlock extends TripleCropsBlock {

    public CornPlantBlock(Properties properties) {
        super(properties);
    }

    protected ItemLike getBaseSeedId() {
        return RankineItems.CORN_SEEDS.get();
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && state.getValue(AGE) > 2) {
            entityIn.makeStuckInBlock(state, new Vec3((double)0.95F, 1.0D, (double)0.95F));
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
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
