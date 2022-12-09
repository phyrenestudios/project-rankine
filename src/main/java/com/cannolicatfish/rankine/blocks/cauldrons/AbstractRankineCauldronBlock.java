package com.cannolicatfish.rankine.blocks.cauldrons;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public abstract class AbstractRankineCauldronBlock extends Block {
    private static final VoxelShape INSIDE = box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Shapes.or(box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), INSIDE), BooleanOp.ONLY_FIRST);

    public AbstractRankineCauldronBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CAULDRON));
    }
    /*
    protected boolean isEntityInsideContent(BlockState p_151980_, BlockPos p_151981_, Entity p_151982_) {
        return p_151982_.getY() < (double)p_151981_.getY() + this.getContentHeight(p_151980_) && p_151982_.getBoundingBox().maxY > (double)p_151981_.getY() + 0.25D;
    }
    protected double getContentHeight(BlockState p_153500_) {
        return 0.9375D;
    }
     */

    public VoxelShape getShape(BlockState p_151964_, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return SHAPE;
    }

    public VoxelShape getInteractionShape(BlockState p_151955_, BlockGetter p_151956_, BlockPos p_151957_) {
        return INSIDE;
    }

    public boolean hasAnalogOutputSignal(BlockState p_151986_) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState p_153502_, Level p_153503_, BlockPos p_153504_) {
        return 4;
    }

    public boolean isPathfindable(BlockState p_151959_, BlockGetter p_151960_, BlockPos p_151961_, PathComputationType p_151962_) {
        return false;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(Items.CAULDRON);
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return true;
    }

    @Override
    public void randomTick(BlockState p_60551_, ServerLevel levelIn, BlockPos posIn, Random rand) {
        if (isHeated(levelIn, posIn) && this.getOutput() != null) {
            if (this.getOutput() == RankineItems.MAPLE_SYRUP.get()) {
                levelIn.setBlockAndUpdate(posIn, RankineBlocks.MAPLE_SYRUP_CAULDRON.get().defaultBlockState());
            } else {
                popResourceFromFace(levelIn, posIn, Direction.UP, new ItemStack(this.getOutput()));
                levelIn.setBlockAndUpdate(posIn, Blocks.CAULDRON.defaultBlockState());
                return;
            }
        }
        super.randomTick(p_60551_, levelIn, posIn, rand);
    }

    @Override
    public InteractionResult use(BlockState p_151969_, Level levelIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult p_151974_) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);

        if (itemstack.is(Items.BUCKET)) {
            return fillBottle(levelIn, pos, playerIn, handIn, itemstack, new ItemStack(getBottle()), SoundEvents.BUCKET_FILL);
        } else if (itemstack.is(Items.GLASS_BOTTLE)) {
            return fillBottle(levelIn, pos, playerIn, handIn, itemstack, new ItemStack(getBottle()), SoundEvents.BOTTLE_FILL);
        } else {
            return InteractionResult.PASS;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level levelIn, BlockPos posIn, Random rand) {
        if (isHeated(levelIn, posIn)) {
            double d0 = (double) posIn.getX() + 0.5D;
            double d1 = (double) posIn.getY() + 0.95D;
            double d2 = (double) posIn.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                levelIn.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = rand.nextDouble() * 0.6D - 0.3D;
            double d6 = rand.nextDouble() * 6.0D / 16.0D;
            if (rand.nextDouble() < 0.05D) {
                levelIn.addParticle(ParticleTypes.SMOKE, d0 + d4, d1, d2 + d5, 0.0D, 5.0E-4D, 0.0D);
            }
            if (rand.nextDouble() < 0.2D) {
                levelIn.addParticle(ParticleTypes.BUBBLE, d0 + d4, d1, d2 + d5, 0.0D, 5.0E-4D, 0.0D);
            }
            if (rand.nextDouble() < 0.2D) {
                levelIn.addParticle(ParticleTypes.BUBBLE_POP, d0 + d4, d1, d2 + d5, 0.0D, 5.0E-4D, 0.0D);
            }
        }
    }

    public abstract Item getBottle();
    public abstract Item getOutput();

    private static InteractionResult fillBottle(Level levelIn, BlockPos posIn, Player playerIn, InteractionHand handIn, ItemStack stackIn, ItemStack bottleOut, SoundEvent soundIn) {
        if (!levelIn.isClientSide) {
            playerIn.setItemInHand(handIn, ItemUtils.createFilledResult(stackIn, playerIn, bottleOut));
            playerIn.awardStat(Stats.USE_CAULDRON);
            playerIn.awardStat(Stats.ITEM_USED.get(stackIn.getItem()));
            levelIn.setBlockAndUpdate(posIn, Blocks.CAULDRON.defaultBlockState());
            levelIn.playSound((Player)null, playerIn, soundIn, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        return InteractionResult.sidedSuccess(levelIn.isClientSide);
    }

    private static boolean isHeated(Level levelIn, BlockPos posIn) {
        for (Direction dir : Direction.values()) {
            if (levelIn.getBlockState(posIn.relative(dir)).is(RankineTags.Blocks.HEAT_SOURCES)) return true;
        }
        return false;
    }

}
