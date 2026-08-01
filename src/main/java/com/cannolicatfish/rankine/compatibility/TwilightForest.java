package com.cannolicatfish.rankine.compatibility;

import net.neoforged.fml.ModList;

public class TwilightForest {
    public static boolean isInstalled() {
        return ModList.get() != null && ModList.get().getModContainerById("twilightforest").isPresent();
    }
}
