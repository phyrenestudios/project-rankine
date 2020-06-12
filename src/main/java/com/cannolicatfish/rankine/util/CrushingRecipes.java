package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.items.ModItems;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum CrushingRecipes {

    SPERRYLITE(Collections.singletonList("rankine:crushing/sperrylite"), Arrays.asList(ModItems.PLATINUM_ARSENIDE,ModItems.OSMIRIDIUM_ALLOY),
            Arrays.asList(1,0), Arrays.asList(0f,.5f)),
    LIMESTONE(Collections.singletonList("rankine:crushing/limestone"),Arrays.asList(ModItems.CALCITE,ModItems.DOLOMITE),
            Arrays.asList(1,0), Arrays.asList(0f,.1f)),
    ;


    private final List<String> tags;
    private final List<Item> outputs;
    private final List<Integer> amounts;
    private final List<Float> addChances;

    private CrushingRecipes(List<String> tags, List<Item> outputs, List<Integer> amounts, List<Float> addChances)
    {
        this.tags = tags;
        this.outputs = outputs;
        this.amounts = amounts;
        this.addChances = addChances;
    }
}
