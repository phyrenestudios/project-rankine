package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.compatibility.TerraForged;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
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
        for (int i = 0; i < Config.OVERWORLD_INTRUSION_LIST.get().size(); i+=2) {
            try {
                col.add(Integer.parseInt(Config.OVERWORLD_INTRUSION_LIST.get().get(i+1)), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Config.OVERWORLD_INTRUSION_LIST.get().get(i))).getDefaultState());
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
        int radius = Config.OVERWORLD_INTRUSION_RADIUS.get() - rand.nextInt(3);
        int startY = 0;
        int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());


        if (CHANCE < Config.OVERWORLD_INTRUSION_CHANCE.get()) {
            if (TerraForged.isInstalled()) {
                INTRUSION = RankineBlocks.KIMBERLITE.get().getDefaultState();
            } else {
                INTRUSION = intrusionCollection().getRandomElement();
            }
            for (int y = startY; y <= endY; ++y) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-radius, y - 1, -radius), pos.add(radius, y - 1, radius))) {
                    if (blockpos.distanceSq(new BlockPos(pos.getX(), y, pos.getZ())) <= Math.pow(radius + 0.5, 2)) {
                        if (reader.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("rankine:intrusion_passable"))) {
                            if (INTRUSION == RankineBlocks.KIMBERLITE.get().getDefaultState()) {
                                float chance = rand.nextFloat();
                                if (chance < Config.ILMENITE_CHANCE.get().floatValue() && y <= 50) {
                                    reader.setBlockState(blockpos, RankineBlocks.ILMENITE_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, 28), 4);
                                } else if (chance < Config.DIAMON_CHANCE.get().floatValue() && y <= 50) {
                                    reader.setBlockState(blockpos, RankineBlocks.DIAMOND_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, 28), 4);
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
                        return true;
                    }
                }
            }
        }
        return true;
    }


}
