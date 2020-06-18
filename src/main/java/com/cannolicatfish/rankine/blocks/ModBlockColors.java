package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.state.IProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.ILightReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class ModBlockColors {
    private final java.util.Map<net.minecraftforge.registries.IRegistryDelegate<Block>, IBlockColor> colors = new java.util.HashMap<>();
    private final Map<Block, Set<IProperty<?>>> colorStates = Maps.newHashMap();

    public static BlockColors init() {
        BlockColors blockcolors = new BlockColors();

        blockcolors.register((p_228064_0_, p_228064_1_, p_228064_2_, p_228064_3_) -> {
            return p_228064_1_ != null && p_228064_2_ != null ? BiomeColors.getGrassColor(p_228064_1_, p_228064_2_) : GrassColors.get(0.5D, 1.0D);
        }, ModBlocks.SHORT_GRASS);
        net.minecraftforge.client.ForgeHooksClient.onBlockColorsInit(blockcolors);
        return blockcolors;
    }

    public int getColorOrMaterialColor(BlockState state, World worldIn, BlockPos blockPosIn) {
        IBlockColor iblockcolor = this.colors.get(state.getBlock().delegate);
        if (iblockcolor != null) {
            return iblockcolor.getColor(state, (ILightReader)null, (BlockPos)null, 0);
        } else {
            MaterialColor materialcolor = state.getMaterialColor(worldIn, blockPosIn);
            return materialcolor != null ? materialcolor.colorValue : -1;
        }
    }

    public int getColor(BlockState blockStateIn, @Nullable ILightReader lightReaderIn, @Nullable BlockPos blockPosIn, int tintIndexIn) {
        IBlockColor iblockcolor = this.colors.get(blockStateIn.getBlock().delegate);
        return iblockcolor == null ? -1 : iblockcolor.getColor(blockStateIn, lightReaderIn, blockPosIn, tintIndexIn);
    }

    public void register(IBlockColor blockColor, Block... blocksIn) {
        for(Block block : blocksIn) {
            this.colors.put(block.delegate, blockColor);
        }

    }

    private void addColorStates(Set<IProperty<?>> propertiesIn, Block... blocksIn) {
        for(Block block : blocksIn) {
            this.colorStates.put(block, propertiesIn);
        }

    }

    private void addColorState(IProperty<?> propertyIn, Block... blocksIn) {
        this.addColorStates(ImmutableSet.of(propertyIn), blocksIn);
    }

    public Set<IProperty<?>> getColorProperties(Block blockIn) {
        return this.colorStates.getOrDefault(blockIn, ImmutableSet.of());
    }

}
