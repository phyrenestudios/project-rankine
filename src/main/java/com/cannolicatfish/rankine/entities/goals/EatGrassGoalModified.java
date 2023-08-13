package com.cannolicatfish.rankine.entities.goals;

import com.cannolicatfish.rankine.blocks.block_enums.SoilBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.function.Predicate;

public class EatGrassGoalModified extends Goal {
    private static final Predicate<BlockState> IS_GRASS = (blockState -> blockState.is(RankineTags.Blocks.GRASS_BLOCKS));
    /** The entity owner of this AITask */
    private final Mob grassEaterEntity;
    /** The world the grass eater entity is eating from */
    private final Level entityWorld;
    /** Number of ticks since the entity started to eat grass */
    private int eatingGrassTimer;

    public EatGrassGoalModified(Mob grassEaterEntityIn) {
        this.grassEaterEntity = grassEaterEntityIn;
        this.entityWorld = grassEaterEntityIn.level;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if (this.grassEaterEntity.getRandom().nextInt(this.grassEaterEntity.isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = this.grassEaterEntity.blockPosition();
            if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos.below()))) {
                return true;
            } else {
                return this.entityWorld.getBlockState(blockpos.below()).is(Blocks.GRASS_BLOCK);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.eatingGrassTimer = 40;
        this.entityWorld.broadcastEntityEvent(this.grassEaterEntity, (byte)10);
        this.grassEaterEntity.getNavigation().stop();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.eatingGrassTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return this.eatingGrassTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat grass
     */
    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
        if (this.eatingGrassTimer == 4) {
            BlockPos blockpos = this.grassEaterEntity.blockPosition();
            BlockPos blockpos1 = blockpos.below();
            if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos1))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                    this.entityWorld.setBlock(blockpos1, SoilBlocks.getSoilFromBlock(this.entityWorld.getBlockState(blockpos1).getBlock()) != null ? SoilBlocks.getSoilFromBlock(this.entityWorld.getBlockState(blockpos1).getBlock()).getSoilBlock().defaultBlockState() : Blocks.DIRT.defaultBlockState(), 2);
                }
                this.grassEaterEntity.ate();
            } else if (this.entityWorld.getBlockState(blockpos1).is(Blocks.GRASS_BLOCK)) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                    this.entityWorld.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                    this.entityWorld.setBlock(blockpos1, Blocks.DIRT.defaultBlockState(), 2);
                }
                this.grassEaterEntity.ate();
            }


        }
    }
}
