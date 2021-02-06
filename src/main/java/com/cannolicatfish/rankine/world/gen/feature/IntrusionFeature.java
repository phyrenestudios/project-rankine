package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.compatibility.TerraForged;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.WGConfig;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class IntrusionFeature extends Feature<ReplacerFeatureConfig> {
    public IntrusionFeature(Codec<ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    public static WeightedCollection<BlockState> intrusionCollection(){
        WeightedCollection<BlockState> col = new WeightedCollection<>();
        for (int i = 0; i < WGConfig.INTRUSIONS.OVERWORLD_INTRUSION_LIST.get().size(); i+=2) {
            try {
                col.add(Integer.parseInt(WGConfig.INTRUSIONS.OVERWORLD_INTRUSION_LIST.get().get(i+1)), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(WGConfig.INTRUSIONS.OVERWORLD_INTRUSION_LIST.get().get(i))).getDefaultState());
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return col;
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {

        BlockState INTRUSION;
        float CHANCE = rand.nextFloat();
        int radius = WGConfig.INTRUSIONS.OVERWORLD_INTRUSION_RADIUS.get() - rand.nextInt(3);
        int startY = 0;
        int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());

        if (CHANCE < WGConfig.INTRUSIONS.OVERWORLD_INTRUSION_CHANCE.get()) {
            INTRUSION = intrusionCollection().getRandomElement();
            int x1 = rand.nextInt(radius)-radius/2;
            int x2 = rand.nextInt(radius)-radius/2;
            int x3 = rand.nextInt(radius)-radius/2;
            int x4 = rand.nextInt(radius)-radius/2;
            int z1 = rand.nextInt(radius)-radius/2;
            int z2 = rand.nextInt(radius)-radius/2;
            int z3 = rand.nextInt(radius)-radius/2;
            int z4 = rand.nextInt(radius)-radius/2;


            for (int y = startY; y <= endY; ++y) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-3*radius, y, -3*radius), pos.add(3*radius, y, 3*radius))) {
                    if (blockpos.distanceSq(new BlockPos(pos.getX()+x1, y, pos.getZ()+z1)) <= Math.pow(radius + 0.5, 2) || blockpos.distanceSq(new BlockPos(pos.getX()+x2, y, pos.getZ()+z2)) <= Math.pow(radius + 0.5, 2) || blockpos.distanceSq(new BlockPos(pos.getX()+x3, y, pos.getZ()+z3)) <= Math.pow(radius + 0.5, 2) || blockpos.distanceSq(new BlockPos(pos.getX()+x4, y, pos.getZ()+z4)) <= Math.pow(radius + 0.5, 2)) {
                        if (reader.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("rankine:intrusion_passable"))) {
                            float chance = rand.nextFloat();
                            if (INTRUSION == RankineBlocks.KIMBERLITE.get().getDefaultState()) {
                                if (chance < WGConfig.INTRUSIONS.ILMENITE_CHANCE.get().floatValue() && y <= 50) {
                                    reader.setBlockState(blockpos, RankineBlocks.ILMENITE_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, 9), 4);
                                } else if (chance < WGConfig.INTRUSIONS.DIAMON_CHANCE.get().floatValue() && y <= 50) {
                                    reader.setBlockState(blockpos, RankineBlocks.DIAMOND_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, 9), 4);
                                } else {
                                    reader.setBlockState(blockpos, INTRUSION, 4);
                                }
                            } else if (INTRUSION == RankineBlocks.PEGMATITE.get().getDefaultState()) {
                                if (chance < WGConfig.INTRUSIONS.PETALITE_CHANCE.get().floatValue() && y <= 50) {
                                    reader.setBlockState(blockpos, RankineBlocks.PETALITE_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, 9), 4);
                                }
                            } else {
                                reader.setBlockState(blockpos, INTRUSION, 4);
                            }
                        }
                    }
                }
                if (rand.nextFloat() < WGConfig.INTRUSIONS.OVERWORLD_INTRUSION_SHRINK.get()) {
                    x1 += rand.nextInt(3)-1;
                    x2 += rand.nextInt(3)-1;
                    x3 += rand.nextInt(3)-1;
                    x4 += rand.nextInt(3)-1;
                    z1 += rand.nextInt(3)-1;
                    z2 += rand.nextInt(3)-1;
                    z3 += rand.nextInt(3)-1;
                    z4 += rand.nextInt(3)-1;
                }
                if (rand.nextFloat() < 0.08) {
                    radius -= 1;
                    if (radius <= 0) {
                        return true;
                    }
                }
            }
        }
        return true;
    }


}
