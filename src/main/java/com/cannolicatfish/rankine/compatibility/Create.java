package com.cannolicatfish.rankine.compatibility;

import net.minecraftforge.fml.ModList;

public class Create {
    public static boolean isInstalled() {
        return ModList.get() != null && ModList.get().getModContainerById("create").isPresent();
    }
}
