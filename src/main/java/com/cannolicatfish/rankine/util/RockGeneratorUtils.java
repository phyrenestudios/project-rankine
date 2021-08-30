package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.List;

public class RockGeneratorUtils {

    public BlockState modifiedStoneGenerator(RockGeneratorRecipe recipe, List<Block> blocks) {

        return Blocks.AIR.getDefaultState();
    }

    public enum RockGenType {
        INTRUSIVE_IGNEOUS,
        EXTRUSIVE_IGNEOUS,
        SEDIMENTARY,
        METAMORPHIC,
        VOLCANIC
    }
}
