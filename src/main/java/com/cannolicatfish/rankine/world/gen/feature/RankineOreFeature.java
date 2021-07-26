package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class RankineOreFeature extends Feature<RankineOreFeatureConfig> {
    public RankineOreFeature(Codec<RankineOreFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }


    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, RankineOreFeatureConfig config) {
        float f = rand.nextFloat() * (float)Math.PI;
        float f1 = (float)config.size / 8.0F;
        int i = MathHelper.ceil(((float)config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d0 = (double)((float)pos.getX() + MathHelper.sin(f) * f1);
        double d1 = (double)((float)pos.getX() - MathHelper.sin(f) * f1);
        double d2 = (double)((float)pos.getZ() + MathHelper.cos(f) * f1);
        double d3 = (double)((float)pos.getZ() - MathHelper.cos(f) * f1);
        int j = 2;
        double d4 = (double)(pos.getY() + rand.nextInt(3) - 2);
        double d5 = (double)(pos.getY() + rand.nextInt(3) - 2);
        int k = pos.getX() - MathHelper.ceil(f1) - i;
        int l = pos.getY() - 2 - i;
        int i1 = pos.getZ() - MathHelper.ceil(f1) - i;
        int j1 = 2 * (MathHelper.ceil(f1) + i);
        int k1 = 2 * (2 + i);

        for(int l1 = k; l1 <= k + j1; ++l1) {
            for(int i2 = i1; i2 <= i1 + j1; ++i2) {
                if (l <= reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, l1, i2)) {
                    return this.func_207803_a(reader, rand, config, d0, d1, d2, d3, d4, d5, k, l, i1, j1, k1);
                }
            }
        }

        return false;
    }

    public boolean func_207803_a(IWorld worldIn, Random random, RankineOreFeatureConfig config, double p_207803_4_, double p_207803_6_, double p_207803_8_, double p_207803_10_, double p_207803_12_, double p_207803_14_, int p_207803_16_, int p_207803_17_, int p_207803_18_, int p_207803_19_, int p_207803_20_) {
        int i = 0;
        BitSet bitset = new BitSet(p_207803_19_ * p_207803_20_ * p_207803_19_);
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
        double[] adouble = new double[config.size * 4];

        for(int j = 0; j < config.size; ++j) {
            float f = (float)j / (float)config.size;
            double d0 = MathHelper.lerp((double)f, p_207803_4_, p_207803_6_);
            double d2 = MathHelper.lerp((double)f, p_207803_12_, p_207803_14_);
            double d4 = MathHelper.lerp((double)f, p_207803_8_, p_207803_10_);
            double d6 = random.nextDouble() * (double)config.size / 16.0D;
            double d7 = ((double)(MathHelper.sin((float)Math.PI * f) + 1.0F) * d6 + 1.0D) / 2.0D;
            adouble[j * 4 + 0] = d0;
            adouble[j * 4 + 1] = d2;
            adouble[j * 4 + 2] = d4;
            adouble[j * 4 + 3] = d7;
        }

        for(int l2 = 0; l2 < config.size - 1; ++l2) {
            if (!(adouble[l2 * 4 + 3] <= 0.0D)) {
                for(int j3 = l2 + 1; j3 < config.size; ++j3) {
                    if (!(adouble[j3 * 4 + 3] <= 0.0D)) {
                        double d12 = adouble[l2 * 4 + 0] - adouble[j3 * 4 + 0];
                        double d13 = adouble[l2 * 4 + 1] - adouble[j3 * 4 + 1];
                        double d14 = adouble[l2 * 4 + 2] - adouble[j3 * 4 + 2];
                        double d15 = adouble[l2 * 4 + 3] - adouble[j3 * 4 + 3];
                        if (d15 * d15 > d12 * d12 + d13 * d13 + d14 * d14) {
                            if (d15 > 0.0D) {
                                adouble[j3 * 4 + 3] = -1.0D;
                            } else {
                                adouble[l2 * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        for(int i3 = 0; i3 < config.size; ++i3) {
            double d11 = adouble[i3 * 4 + 3];
            if (!(d11 < 0.0D)) {
                double d1 = adouble[i3 * 4 + 0];
                double d3 = adouble[i3 * 4 + 1];
                double d5 = adouble[i3 * 4 + 2];
                int k = Math.max(MathHelper.floor(d1 - d11), p_207803_16_);
                int k3 = Math.max(MathHelper.floor(d3 - d11), p_207803_17_);
                int l = Math.max(MathHelper.floor(d5 - d11), p_207803_18_);
                int i1 = Math.max(MathHelper.floor(d1 + d11), k);
                int j1 = Math.max(MathHelper.floor(d3 + d11), k3);
                int k1 = Math.max(MathHelper.floor(d5 + d11), l);

                for(int l1 = k; l1 <= i1; ++l1) {
                    double d8 = ((double)l1 + 0.5D - d1) / d11;
                    if (d8 * d8 < 1.0D) {
                        for(int i2 = k3; i2 <= j1; ++i2) {
                            double d9 = ((double)i2 + 0.5D - d3) / d11;
                            if (d8 * d8 + d9 * d9 < 1.0D) {
                                for(int j2 = l; j2 <= k1; ++j2) {
                                    double d10 = ((double)j2 + 0.5D - d5) / d11;
                                    if (d8 * d8 + d9 * d9 + d10 * d10 < 1.0D) {
                                        int k2 = l1 - p_207803_16_ + (i2 - p_207803_17_) * p_207803_19_ + (j2 - p_207803_18_) * p_207803_19_ * p_207803_20_;
                                        if (!bitset.get(k2)) {
                                            bitset.set(k2);
                                            blockpos$mutableblockpos.setPos(l1, i2, j2);
                                            Block b = worldIn.getBlockState(blockpos$mutableblockpos).getBlock();
                                            ResourceLocation rs = b.getRegistryName();
                                            if (config.target.getPredicate().test(worldIn.getBlockState(blockpos$mutableblockpos))) {
                                                List<Block> blockList = Arrays.asList(Blocks.GRANITE,Blocks.DIORITE,Blocks.ANDESITE,Blocks.SANDSTONE,Blocks.RED_SANDSTONE,Blocks.NETHERRACK, Blocks.BLACKSTONE,Blocks.BASALT,Blocks.END_STONE,Blocks.OBSIDIAN,
                                                        RankineBlocks.GRAY_GRANITE.get(), RankineBlocks.GRANODIORITE.get(), RankineBlocks.HORNBLENDE_ANDESITE.get(), RankineBlocks.THOLEIITIC_BASALT.get(), RankineBlocks.GABBRO.get(), RankineBlocks.ANORTHOSITE.get(), RankineBlocks.RHYOLITE.get(), RankineBlocks.COMENDITE.get(), RankineBlocks.BLACK_DACITE.get(), RankineBlocks.RED_DACITE.get(), RankineBlocks.RED_PORPHYRY.get(), RankineBlocks.PURPLE_PORPHYRY.get(), RankineBlocks.PEGMATITE.get(), RankineBlocks.PERIDOTITE.get(), RankineBlocks.TROCTOLITE.get(), RankineBlocks.KIMBERLITE.get(), RankineBlocks.KOMATIITE.get(), RankineBlocks.PUMICE.get(), RankineBlocks.SCORIA.get(),
                                                        RankineBlocks.WHITE_MARBLE.get(), RankineBlocks.BLACK_MARBLE.get(), RankineBlocks.GNEISS.get(), RankineBlocks.MICA_SCHIST.get(), RankineBlocks.PHYLLITE.get(), RankineBlocks.SLATE.get(), RankineBlocks.QUARTZITE.get(), RankineBlocks.SKARN.get(),
                                                        RankineBlocks.LIMESTONE.get(), RankineBlocks.DOLOSTONE.get(), RankineBlocks.CHALK.get(), RankineBlocks.SHALE.get(), RankineBlocks.SILTSTONE.get(), RankineBlocks.ITACOLUMITE.get(), RankineBlocks.ARKOSE.get(), RankineBlocks.MUDSTONE.get(), RankineBlocks.BRECCIA.get(),
                                                        RankineBlocks.METEORITE.get(), RankineBlocks.ENSTATITE.get());
                                                if (config.state.getBlock() instanceof RankineOreBlock) {
                                                    /*if (blockList.contains(b)) {
                                                        worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, blockList.indexOf(b)+1), 2);
                                                        ++i;
                                                    } else  */
                                                    /*
                                                    if (rs.getNamespace().equals("rankine")) {
                                                        switch (rs.getPath()) {
                                                            case "gray_granite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 11), 2);
                                                                break;
                                                            case "granodiorite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 12), 2);
                                                                break;
                                                            case "hornblende_andesite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 13), 2);
                                                                break;
                                                            case "tholeiitic_basalt":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 14), 2);
                                                                break;
                                                            case "pyroxene_gabbro":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 15), 2);
                                                                break;
                                                            case "anorthosite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 16), 2);
                                                                break;
                                                            case "rhyolite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 17), 2);
                                                                break;
                                                            case "comendite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 18), 2);
                                                                break;
                                                            case "black_dacite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 19), 2);
                                                                break;
                                                            case "red_dacite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 20), 2);
                                                                break;
                                                            case "red_porphyry":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 21), 2);
                                                                break;
                                                            case "purple_porphyry":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 22), 2);
                                                                break;
                                                            case "pegmatite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 23), 2);
                                                                break;
                                                            case "peridotite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 24), 2);
                                                                break;
                                                            case "troctolite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 25), 2);
                                                                break;
                                                            case "kimberlite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 26), 2);
                                                                break;
                                                            case "komatiite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 27), 2);
                                                                break;
                                                            case "pumice":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 28), 2);
                                                                break;
                                                            case "scoria":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 29), 2);
                                                                break;
                                                            case "white_marble":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 30), 2);
                                                                break;
                                                            case "black_marble":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 31), 2);
                                                                break;
                                                            case "gneiss":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 32), 2);
                                                                break;
                                                            case "mica_schist":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 33), 2);
                                                                break;
                                                            case "phyllite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 34), 2);
                                                                break;
                                                            case "slate":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 35), 2);
                                                                break;
                                                            case "quartzite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 36), 2);
                                                                break;
                                                            case "mariposite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 37), 2);
                                                                break;
                                                            case "skarn":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 38), 2);
                                                                break;
                                                            case "ringwoodite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 39), 2);
                                                                break;
                                                            case "wadsleyite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 40), 2);
                                                                break;
                                                            case "bridgmanite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 41), 2);
                                                                break;
                                                            case "ferropericlase":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 42), 2);
                                                                break;
                                                            case "perovskite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 43), 2);
                                                                break;
                                                            case "tufa_limestone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 44), 2);
                                                                break;
                                                            case "dolostone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 45), 2);
                                                                break;
                                                            case "chalk":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 46), 2);
                                                                break;
                                                            case "carbonaceous_shale":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 47), 2);
                                                                break;
                                                            case "siltstone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 48), 2);
                                                                break;
                                                            case "quartz_sandstone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 49), 2);
                                                                break;
                                                            case "arkose_sandstone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 50), 2);
                                                                break;
                                                            case "mudstone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 51), 2);
                                                                break;
                                                            case "breccia":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 52), 2);
                                                                break;
                                                            case "meteorite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 53), 2);
                                                                break;
                                                            case "entstatite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 54), 2);
                                                                break;
                                                            default:
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 0), 2);
                                                        }
                                                        ++i;
                                                    } else if (rs.getNamespace().equals("minecraft")) {
                                                        switch (rs.getPath()) {
                                                            case "granite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 1), 2);
                                                                break;
                                                            case "diorite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 2), 2);
                                                                break;
                                                            case "andesite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 3), 2);
                                                                break;
                                                            case "sandstone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 4), 2);
                                                                break;
                                                            case "red_sandstone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 5), 2);
                                                                break;
                                                            case "netherrack":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 6), 2);
                                                                break;
                                                            case "blackstone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 7), 2);
                                                                break;
                                                            case "basalt":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 8), 2);
                                                                break;
                                                            case "end_stone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 9), 2);
                                                                break;
                                                            case "obsidian":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 10), 2);
                                                                break;
                                                        }
                                                        ++i;
                                                    } else if (rs.getNamespace().equals("create")) {
                                                        switch (rs.getPath()) {
                                                            case "weathered_limestone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 55), 2);
                                                                break;
                                                            case "limestone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 56), 2);
                                                                break;
                                                            case "dolomite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 57), 2);
                                                                break;
                                                            case "gabbro":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 58), 2);
                                                                break;
                                                            case "natural_scoria":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 59), 2);
                                                                break;
                                                            default:
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 0), 2);
                                                        }
                                                        ++i;
                                                    } else if (rs.getNamespace().equals("quark")) {
                                                        switch (rs.getPath()) {
                                                            case "limestone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 60), 2);
                                                                break;
                                                            case "marble":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 61), 2);
                                                                break;
                                                            case "jasper":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 62), 2);
                                                                break;
                                                            case "slate":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 63), 2);
                                                                break;
                                                            case "basalt":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 64), 2);
                                                                break;
                                                            case "myalite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 65), 2);
                                                                break;
                                                            default:
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 0), 2);
                                                        }
                                                        ++i;
                                                    } else if (b.getRegistryName().getNamespace().equals("unearthed")) {
                                                        switch (rs.getPath()) {
                                                            case "weathered_rhyolite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 66), 2);
                                                                break;
                                                            case "rhyolite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 67), 2);
                                                                break;
                                                            case "gabbro":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 68), 2);
                                                                break;
                                                            case "granodiorite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 69), 2);
                                                                break;
                                                            case "white_granite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 70), 2);
                                                                break;
                                                            case "kimberlite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 71), 2);
                                                                break;
                                                            case "pumice":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 72), 2);
                                                                break;
                                                            case "dacite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 73), 2);
                                                                break;
                                                            case "dolerite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 74), 2);
                                                                break;
                                                            case "phyllite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 75), 2);
                                                                break;
                                                            case "slate":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 76), 2);
                                                                break;
                                                            case "quartzite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 77), 2);
                                                                break;
                                                            case "dolomite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 78), 2);
                                                                break;
                                                            case "marble":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 79), 2);
                                                                break;
                                                            case "schist":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 80), 2);
                                                                break;
                                                            case "beige_limestone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 81), 2);
                                                                break;
                                                            case "grey_limestone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 82), 2);
                                                                break;
                                                            case "limestone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 83), 2);
                                                                break;
                                                            case "siltstone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 84), 2);
                                                                break;
                                                            case "mudstone":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 85), 2);
                                                                break;
                                                            case "conglomerate":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 86), 2);
                                                                break;
                                                            case "pillow_basalt":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 87), 2);
                                                                break;
                                                            case "lignite":
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 88), 2);
                                                                break;
                                                            default:
                                                                worldIn.setBlockState(blockpos$mutableblockpos, config.state.with(RankineOreBlock.TYPE, 0), 2);
                                                        }
                                                        ++i;
                                                    } else {
                                                        DimensionType dimensionType = worldIn.getDimensionType();
                                                        if (DimensionType.THE_NETHER.equals(dimensionType)) {
                                                            worldIn.setBlockState(blockpos$mutableblockpos, config.state.getBlock().getDefaultState().with(RankineOreBlock.TYPE, 6), 2);
                                                        } else if (DimensionType.THE_END.equals(dimensionType)) {
                                                            worldIn.setBlockState(blockpos$mutableblockpos, config.state.getBlock().getDefaultState().with(RankineOreBlock.TYPE, 9), 2);
                                                        } else {
                                                            worldIn.setBlockState(blockpos$mutableblockpos, config.state.getBlock().getDefaultState().with(RankineOreBlock.TYPE, 0), 2);
                                                        }
                                                        ++i;
                                                    }
                                                    */
                                                } else {
                                                    worldIn.setBlockState(blockpos$mutableblockpos, config.state.getBlock().getDefaultState(), 2);
                                                    ++i;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return i > 0;
    }
}
