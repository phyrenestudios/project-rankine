package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CobblePatchFeature extends Feature<RandomPatchConfiguration> {
    public CobblePatchFeature(Codec<RandomPatchConfiguration> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(FeaturePlaceContext<RandomPatchConfiguration> p_160210_) {

        RandomPatchConfiguration randompatchconfiguration = p_160210_.config();
        Random random = p_160210_.random();
        BlockPos blockpos = p_160210_.origin();
        WorldGenLevel worldgenlevel = p_160210_.level();

        List<String> rockList = new ArrayList<String>();
        if (WorldgenUtils.GEN_BIOMES.contains(worldgenlevel.getBiome(blockpos).value().getRegistryName())) {
            rockList = WorldgenUtils.LAYER_LISTS.get(WorldgenUtils.GEN_BIOMES.indexOf(worldgenlevel.getBiome(blockpos).value().getRegistryName()));
        }

        if (rockList.size() < 1) return false;
        Block stoneBlock = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(rockList.get(random.nextInt(rockList.size()))));
        if (!VanillaIntegration.RankineStonesMap.containsKey(stoneBlock)) {
            return false;
        }
        BlockState blockstate = VanillaIntegration.RankineStonesMap.get(stoneBlock).getCobble().defaultBlockState();


        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int j = randompatchconfiguration.xzSpread() + 1;
        int k = randompatchconfiguration.ySpread() + 1;

        for(int l = 0; l < randompatchconfiguration.tries(); ++l) {
            blockpos$mutableblockpos.setWithOffset(blockpos, random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j));
            if (worldgenlevel.getBlockState(blockpos$mutableblockpos).is(Blocks.AIR) && blockstate.canSurvive(worldgenlevel, blockpos$mutableblockpos)) {
                worldgenlevel.setBlock(blockpos$mutableblockpos, blockstate, 3);
                ++i;
            }
        }

        return i > 0;
    }
}

