package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class RockGeneratorUtils {

    public BlockState modifiedStoneGenerator(RockGeneratorRecipe recipe, List<Block> blocks) {

        return Blocks.AIR.defaultBlockState();
    }

    public enum RockGenType {
        INTRUSIVE_IGNEOUS,
        EXTRUSIVE_IGNEOUS,
        SEDIMENTARY,
        METAMORPHIC,
        VOLCANIC
    }
}
