package com.cannolicatfish.rankine.entities.goals;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.EnumSet;
import java.util.function.Predicate;

public class EatGrassGoalModified extends Goal {
    private static final Predicate<BlockState> IS_TALL_GRASS = (blockState -> blockState.is(Blocks.GRASS) || blockState.is(RankineBlocks.SHORT_GRASS.get()));
    private static final Predicate<BlockState> IS_GRASS_BLOCK = (blockState -> blockState.is(RankineTags.Blocks.GRASS_BLOCKS));
    private static final int EAT_ANIMATION_TICKS = 40;
    private final Mob mob;
    private final Level level;
    private int eatAnimationTick;


    public EatGrassGoalModified(Mob grassEaterEntityIn) {
        this.mob = grassEaterEntityIn;
        this.level = grassEaterEntityIn.level;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    public boolean canUse() {
        if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = this.mob.blockPosition();
            if (IS_TALL_GRASS.test(this.level.getBlockState(blockpos))) {
                return true;
            } else {
                return IS_GRASS_BLOCK.test(this.level.getBlockState(blockpos.below()));
            }
        }
    }

    public void start() {
        this.eatAnimationTick = this.adjustedTickDelay(40);
        this.level.broadcastEntityEvent(this.mob, (byte)10);
        this.mob.getNavigation().stop();
    }
    public void stop() {
        this.eatAnimationTick = 0;
    }

    public boolean canContinueToUse() {
        return this.eatAnimationTick > 0;
    }

    public int getEatAnimationTick() {
        return this.eatAnimationTick;
    }

    public void tick() {
        this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        if (this.eatAnimationTick == this.adjustedTickDelay(4)) {
            BlockPos blockpos = this.mob.blockPosition();
            if (IS_TALL_GRASS.test(this.level.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.mob)) {
                    this.level.destroyBlock(blockpos, false);
                }

                this.mob.ate();
                this.mob.gameEvent(GameEvent.EAT, this.mob.eyeBlockPosition());
            } else {
                BlockPos blockpos1 = blockpos.below();
                if (IS_GRASS_BLOCK.test(this.level.getBlockState(blockpos1))) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.mob)) {
                        this.level.levelEvent(2001, blockpos1, Block.getId(this.level.getBlockState(blockpos1).getBlock().defaultBlockState()));
                        if (RankineLists.GRASS_BLOCKS.contains(this.level.getBlockState(blockpos1).getBlock())) {
                            this.level.setBlock(blockpos1, RankineLists.SOIL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(this.level.getBlockState(blockpos1).getBlock())).defaultBlockState(), 2);
                        } else {
                            this.level.setBlock(blockpos1, Blocks.DIRT.defaultBlockState(), 2);
                        }

                    }

                    this.mob.ate();
                    this.mob.gameEvent(GameEvent.EAT, this.mob.eyeBlockPosition());
                }
            }

        }
    }
}