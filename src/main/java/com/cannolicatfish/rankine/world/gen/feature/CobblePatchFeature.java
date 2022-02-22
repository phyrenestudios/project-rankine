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
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
        List<String> rockList = new ArrayList<String>();
        if (WorldgenUtils.GEN_BIOMES.contains(reader.getBiome(pos).getRegistryName())) {
            rockList = WorldgenUtils.LAYER_LISTS.get(WorldgenUtils.GEN_BIOMES.indexOf(reader.getBiome(pos).getRegistryName()));
        }

        if (rockList.size() < 1) return false;
        Block stoneBlock = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(rockList.get(rand.nextInt(rockList.size()))));
        if (!RankineLists.STONES.contains(stoneBlock)) {
            return false;
        }
        BlockState blockstate = RankineLists.STONE_COBBLES.get(RankineLists.STONES.indexOf(stoneBlock)).getDefaultState();


        BlockPos blockpos;
        if (config.project) {
            blockpos = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos);
        } else {
            blockpos = pos;
        }

        int i = 0;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(int j = 0; j < config.tryCount; ++j) {
            blockpos$mutable.setAndOffset(blockpos, rand.nextInt(config.xSpread + 1) - rand.nextInt(config.xSpread + 1), rand.nextInt(config.ySpread + 1) - rand.nextInt(config.ySpread + 1), rand.nextInt(config.zSpread + 1) - rand.nextInt(config.zSpread + 1));
            BlockPos blockpos1 = blockpos$mutable.down();
            BlockState blockstate1 = reader.getBlockState(blockpos1);
            if ((reader.isAirBlock(blockpos$mutable) || config.isReplaceable && reader.getBlockState(blockpos$mutable).getMaterial().isReplaceable()) && blockstate.isValidPosition(reader, blockpos$mutable) && (config.whitelist.isEmpty() || config.whitelist.contains(blockstate1.getBlock())) && !config.blacklist.contains(blockstate1) && (!config.requiresWater || reader.getFluidState(blockpos1.west()).isTagged(FluidTags.WATER) || reader.getFluidState(blockpos1.east()).isTagged(FluidTags.WATER) || reader.getFluidState(blockpos1.north()).isTagged(FluidTags.WATER) || reader.getFluidState(blockpos1.south()).isTagged(FluidTags.WATER))) {
                config.blockPlacer.place(reader, blockpos$mutable, blockstate, rand);
                ++i;
            }
        }

        return i > 0;
    }
}

