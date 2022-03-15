package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CobblePatchFeature extends Feature<BlockClusterFeatureConfig> {
    public CobblePatchFeature(Codec<BlockClusterFeatureConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
        List<String> rockList = new ArrayList<String>();
        if (WorldgenUtils.GEN_BIOMES.contains(reader.getBiome(pos).getRegistryName())) {
            rockList = WorldgenUtils.LAYER_LISTS.get(WorldgenUtils.GEN_BIOMES.indexOf(reader.getBiome(pos).getRegistryName()));
        }

        if (rockList.size() < 1) return false;
        Block stoneBlock = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(rockList.get(rand.nextInt(rockList.size()))));
        if (!RankineLists.STONES.contains(stoneBlock)) {
            return false;
        }
        BlockState blockstate = RankineLists.STONE_COBBLES.get(RankineLists.STONES.indexOf(stoneBlock)).defaultBlockState();


        BlockPos blockpos;
        if (config.project) {
            blockpos = reader.getHeightmapPos(Heightmap.Type.WORLD_SURFACE_WG, pos);
        } else {
            blockpos = pos;
        }

        int i = 0;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(int j = 0; j < config.tries; ++j) {
            blockpos$mutable.setWithOffset(blockpos, rand.nextInt(config.xspread + 1) - rand.nextInt(config.xspread + 1), rand.nextInt(config.yspread + 1) - rand.nextInt(config.yspread + 1), rand.nextInt(config.zspread + 1) - rand.nextInt(config.zspread + 1));
            BlockPos blockpos1 = blockpos$mutable.below();
            BlockState blockstate1 = reader.getBlockState(blockpos1);
            if ((reader.isEmptyBlock(blockpos$mutable) || config.canReplace && reader.getBlockState(blockpos$mutable).getMaterial().isReplaceable()) && blockstate.canSurvive(reader, blockpos$mutable) && (config.whitelist.isEmpty() || config.whitelist.contains(blockstate1.getBlock())) && !config.blacklist.contains(blockstate1) && (!config.needWater || reader.getFluidState(blockpos1.west()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.east()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.north()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.south()).is(FluidTags.WATER))) {
                config.blockPlacer.place(reader, blockpos$mutable, blockstate, rand);
                ++i;
            }
        }

        return i > 0;
    }
}

