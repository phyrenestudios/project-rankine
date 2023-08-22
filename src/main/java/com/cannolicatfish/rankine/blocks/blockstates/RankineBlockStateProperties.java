package com.cannolicatfish.rankine.blocks.blockstates;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class RankineBlockStateProperties {

    public static final IntegerProperty STYLE_2 = IntegerProperty.create("style", 1, 2);
    public static final IntegerProperty STYLE_3 = IntegerProperty.create("style", 1, 3);
    public static final IntegerProperty STYLE_4 = IntegerProperty.create("style", 1, 4);
    public static final IntegerProperty STYLE_5 = IntegerProperty.create("style", 1, 5);
    public static final IntegerProperty STYLE_6 = IntegerProperty.create("style", 1, 6);
    public static final IntegerProperty STYLE_7 = IntegerProperty.create("style", 1, 7);
    public static final IntegerProperty STYLE_8 = IntegerProperty.create("style", 1, 8);
    public static final IntegerProperty STYLE_9 = IntegerProperty.create("style", 1, 9);
    public static final IntegerProperty STYLE_10 = IntegerProperty.create("style", 1, 10);
    public static final IntegerProperty STYLE_11 = IntegerProperty.create("style", 1, 11);

    private static final IntegerProperty[] STYLES = {STYLE_2, STYLE_3, STYLE_4, STYLE_5, STYLE_6, STYLE_7, STYLE_8, STYLE_9, STYLE_10, STYLE_11};

    public static IntegerProperty getStyleProperty(int maxStage) {
        if (maxStage > 0 && maxStage <= STYLES.length) {
            return STYLES[maxStage - 1];
        }
        throw new IllegalArgumentException("No stage property for stages [1, " + maxStage + "]");
    }

}
