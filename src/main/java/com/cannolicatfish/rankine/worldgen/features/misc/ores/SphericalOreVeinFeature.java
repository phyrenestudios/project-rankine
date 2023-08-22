package com.cannolicatfish.rankine.worldgen.features.misc.ores;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.function.Function;

public class SphericalOreVeinFeature extends Feature<RankineOreFeatureConfig> {

    public SphericalOreVeinFeature(Codec<RankineOreFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }


    @Override
    public boolean place(FeaturePlaceContext<RankineOreFeatureConfig> p_159749_) {
        WorldGenLevel levelIn = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        RandomSource rand = levelIn.getRandom();
        RankineOreFeatureConfig config = p_159749_.config();
        BlockPos posShift = pos.offset(8,0,8);
        BulkSectionAccess bulksectionaccess = new BulkSectionAccess(levelIn);

        if (rand.nextFloat() < 1 - config.chance) {
            return false;
        }

        for (BlockPos BP : BlockPos.betweenClosed(posShift.offset(-config.size, -config.size, -config.size), posShift.offset(config.size, config.size, config.size))) {
            if (rand.nextFloat() < config.density * (rand.nextFloat() / 2 + 0.75)) {
                float Dist = (float) Math.sqrt(BP.offset(1,1,1).distSqr(new Vec3i(posShift.getX(), posShift.getY(), posShift.getZ())));
                float RadiusEffect = (float) (1 - Math.pow(Dist-1,2) / Math.pow(config.size-0.5,2));
                if (RadiusEffect > 0 && rand.nextFloat() < RadiusEffect) {
                    BlockState blockstate = levelIn.getBlockState(BP);
                    for(RankineOreFeatureConfig.TargetBlockState oreconfiguration$targetblockstate : config.targetStates) {
                        if (canPlaceOre(blockstate, bulksectionaccess::getBlockState, rand, config, oreconfiguration$targetblockstate, BP.mutable())) {
                            /*
                            if (oreconfiguration$targetblockstate.state.getBlock() instanceof RankineOreBlock && WorldgenUtils.ORE_STONES.contains(blockstate.getBlock())) {
                                levelIn.setBlock(BP, oreconfiguration$targetblockstate.state.setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(blockstate.getBlock())), 3);
                                break;
                            }

                             */
                            levelIn.setBlock(BP, oreconfiguration$targetblockstate.state, 3);
                            break;
                        }
                    }
                }
            }
        }

        return true;
    }


    public static boolean canPlaceOre(BlockState p_160170_, Function<BlockPos, BlockState> p_160171_, RandomSource p_160172_, RankineOreFeatureConfig p_160173_, RankineOreFeatureConfig.TargetBlockState p_160174_, BlockPos.MutableBlockPos p_160175_) {
        if (!p_160174_.target.test(p_160170_, p_160172_)) {
            return false;
        } else if (shouldSkipAirCheck(p_160172_, p_160173_.discardChanceOnAirExposure)) {
            return true;
        } else {
            return !isAdjacentToAir(p_160171_, p_160175_);
        }
    }

    protected static boolean shouldSkipAirCheck(RandomSource p_160179_, float p_160180_) {
        if (p_160180_ <= 0.0F) {
            return true;
        } else if (p_160180_ >= 1.0F) {
            return false;
        } else {
            return p_160179_.nextFloat() >= p_160180_;
        }
    }
}
