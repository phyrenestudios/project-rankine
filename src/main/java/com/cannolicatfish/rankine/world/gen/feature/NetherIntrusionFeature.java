package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.WGConfig;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;

public class NetherIntrusionFeature extends Feature<ReplacerFeatureConfig> {
    public NetherIntrusionFeature(Codec<ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    public static WeightedCollection<BlockState> intrusionCollection(){
        WeightedCollection<BlockState> col = new WeightedCollection<>();
        for (int i = 0; i < WGConfig.INTRUSIONS.NETHER_INTRUSION_LIST.get().size(); i+=2) {
            try {
                col.add(Integer.parseInt(WGConfig.INTRUSIONS.NETHER_INTRUSION_LIST.get().get(i+1)), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(WGConfig.INTRUSIONS.NETHER_INTRUSION_LIST.get().get(i))).getDefaultState());
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return col;
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {

        float CHANCE = rand.nextFloat();
        int endY = 50 + rand.nextInt(30);
        int radius = WGConfig.INTRUSIONS.NETHER_INTRUSION_RADIUS.get() - rand.nextInt(4);

        if (CHANCE < WGConfig.INTRUSIONS.NETHER_INTRUSION_CHANCE.get()) {
            BlockState INTRUSION = intrusionCollection().getRandomElement();
            for (int y = 0; y <= endY; ++y) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-radius, y - 1, -radius), pos.add(radius, y - 1, radius))) {
                    if (blockpos.distanceSq(new BlockPos(pos.getX(), y, pos.getZ())) <= Math.pow(radius + 0.5, 2)) {
                        if (reader.getBlockState(blockpos) != Blocks.BEDROCK.getDefaultState()) {
                            if (INTRUSION == RankineBlocks.KOMATIITE.get().getDefaultState()) {
                                if (rand.nextFloat() < WGConfig.INTRUSIONS.INTERSPINIFEX_CHANCE.get().floatValue()) {
                                    reader.setBlockState(blockpos, RankineBlocks.INTERSPINIFEX_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, 29), 4);
                                } else {
                                    reader.setBlockState(blockpos, INTRUSION, 4);
                                }
                            } else {
                                reader.setBlockState(blockpos, INTRUSION, 4);
                            }
                        }
                    }
                }
                if (rand.nextFloat() < 0.1) {
                    radius -= 1;
                    if (radius <= 0) {
                        break;
                    }
                }
            }
            for (int y = endY; y <= 127; ++y) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-radius, y - 1, -radius), pos.add(radius, y - 1, radius))) {
                    if (blockpos.distanceSq(new BlockPos(pos.getX(), y, pos.getZ())) <= Math.pow(radius + 0.5, 2)) {
                        if (reader.getBlockState(blockpos) != Blocks.BEDROCK.getDefaultState()) {
                            if (INTRUSION == RankineBlocks.KOMATIITE.get().getDefaultState()) {
                                if (rand.nextFloat() < WGConfig.INTRUSIONS.INTERSPINIFEX_CHANCE.get().floatValue()) {
                                    reader.setBlockState(blockpos, RankineBlocks.INTERSPINIFEX_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, 29), 4);
                                } else {
                                    reader.setBlockState(blockpos, INTRUSION, 4);
                                }
                            } else {
                                reader.setBlockState(blockpos, INTRUSION, 4);
                            }
                        }
                    }
                }
                if (rand.nextFloat() < 0.08) {
                    radius += 1;
                }
            }
        }
        return true;
    }


}
