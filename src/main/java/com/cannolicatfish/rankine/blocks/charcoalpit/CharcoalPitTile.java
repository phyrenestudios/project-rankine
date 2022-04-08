package com.cannolicatfish.rankine.blocks.charcoalpit;

import com.cannolicatfish.rankine.blocks.RankineEightLayerBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static com.cannolicatfish.rankine.init.RankineBlocks.CHARCOAL_PIT_TILE;

public class CharcoalPitTile extends BlockEntity {
    int MAX_HEIGHT = Config.MACHINES.CHARCOAL_PIT_HEIGHT.get();
    double RADIUS = Config.MACHINES.CHARCOAL_PIT_RADIUS.get()+0.5;
    int totalTime;
    int proccessTime = 0;

    public CharcoalPitTile(BlockPos posIn, BlockState stateIn) {
        super(CHARCOAL_PIT_TILE, posIn, stateIn);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.proccessTime = nbt.getInt("ProcessTime");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("ProcessTime", this.proccessTime);
    }

    public void tick() {
        if (!level.isAreaLoaded(worldPosition, 1) || !this.getBlockState().getValue(CharcoalPitBlock.LIT) || level.getBlockState(worldPosition.below()).is(RankineBlocks.CHARCOAL_PIT.get())) return;
        if (proccessTime == 0) {
            totalTime = Mth.nextInt(level.random,(int) Math.round(0.8*Config.MACHINES.CHARCOAL_PIT_SPEED.get()),(int) Math.round(1.2*Config.MACHINES.CHARCOAL_PIT_SPEED.get()));
        }

        if (proccessTime >= totalTime) {
            Set<BlockPos> checkedBlocks = new HashSet<>();
            Stack<BlockPos> toCheck = new Stack<>();
            Set<BlockPos> validLogs = new HashSet<>();
            Set<BlockPos> invalidLogs = new HashSet<>();
            Set<BlockPos> toChar = new HashSet<>();

            toCheck.add(worldPosition);
            validLogs.add(worldPosition);
            while (!toCheck.isEmpty()) {
                BlockPos cp = toCheck.pop();
                if (!checkedBlocks.contains(cp)) {
                    checkedBlocks.add(cp);
                    for (Direction dir : Direction.values()) {
                        if (cp.getY() - worldPosition.getY() <= MAX_HEIGHT && cp.getY() - worldPosition.getY() >= 0 && Math.pow(worldPosition.getX() - cp.getX(), 2) + Math.pow(worldPosition.getZ() - cp.getZ(), 2) <= RADIUS * RADIUS + 0.5) {
                            BlockState target = level.getBlockState(cp.relative(dir));
                            if (target.is(BlockTags.LOGS_THAT_BURN) || target.is(RankineBlocks.CHARCOAL_PIT.get())) {
                                boolean valid = true;
                                for (Direction dir2 : Direction.values()) {
                                    if (dir.getOpposite() == dir2) continue;
                                    if (!level.getBlockState(cp.relative(dir).relative(dir2)).isSolidRender(level, cp.relative(dir).relative(dir2))) {
                                        valid = false;
                                        invalidLogs.add(cp.relative(dir));
                                        break;
                                    }
                                }
                                if (valid) {
                                    toCheck.add(cp.relative(dir));
                                    validLogs.add(cp.relative(dir));
                                }
                            }
                        }
                    }

                }
            }


            int k = 0;
            while (level.getBlockState(worldPosition.above(k)).is(RankineBlocks.CHARCOAL_PIT.get()) && k <= MAX_HEIGHT) {
                k++;
            }

            for (int i = 0; i<=k; ++i) {
                for (BlockPos b : BlockPos.betweenClosed(worldPosition.offset(-RADIUS,i,-RADIUS),worldPosition.offset(RADIUS,i,RADIUS))) {
                    if (validLogs.contains(b.immutable())) {
                        int layerCount = logLayerCount(level,level.getBlockState(b));
                        if (layerCount == 0) continue;
                        int j = 1;
                        while (WorldgenUtils.isGasOrAir(level,b.below(j)) && j <= i) {
                            j++;
                        }
                        if (level.getBlockState(b.below(j)).is(RankineBlocks.CHARCOAL_BLOCK.get())) {
                            int downState = level.getBlockState(b.below(j)).getValue(RankineEightLayerBlock.LAYERS);
                            if (downState + layerCount > 8) {
                                level.setBlock(b.below(j), RankineBlocks.CHARCOAL_BLOCK.get().defaultBlockState().setValue(RankineEightLayerBlock.LAYERS, 8),3);
                                level.setBlock(b.below(j-1), RankineBlocks.CHARCOAL_BLOCK.get().defaultBlockState().setValue(RankineEightLayerBlock.LAYERS, downState+layerCount-8),3);
                                if (j>1) {
                                    level.removeBlock(b,false);
                                }
                            } else {
                                level.setBlock(b.below(j), RankineBlocks.CHARCOAL_BLOCK.get().defaultBlockState().setValue(RankineEightLayerBlock.LAYERS, downState+layerCount),3);
                                level.removeBlock(b,false);
                            }
                        } else {
                            level.setBlock(b, RankineBlocks.CHARCOAL_BLOCK.get().defaultBlockState().setValue(RankineEightLayerBlock.LAYERS, layerCount),3);
                        }
                    } else if (invalidLogs.contains(b.immutable())) {
                        level.setBlock(b, RankineBlocks.CARBON_DIOXIDE_GAS_BLOCK.get().defaultBlockState(),3);
                    }
                }
            }
        } else {
            proccessTime += 1;
        }
    }

    public static int logLayerCount(Level worldIn, BlockState target) {
        Block blockTarget = target.getBlock();
        if (VanillaIntegration.fuelValueMap.containsKey(blockTarget.asItem())) {
            int value = VanillaIntegration.fuelValueMap.get(blockTarget.asItem());
            return (int) Math.floor(Mth.clamp(value/100D -2.0D + (worldIn.random.nextFloat() < (value%100)/100D ? 1 : 0),1,8));
        } else if (target.is(BlockTags.LOGS_THAT_BURN)) {
            return 2;
        } else if (blockTarget.equals(RankineBlocks.CHARCOAL_PIT.get())) {
            return 2;
        }
        return 0;
    }

}
