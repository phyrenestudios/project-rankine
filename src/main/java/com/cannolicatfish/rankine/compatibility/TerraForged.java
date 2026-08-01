package com.cannolicatfish.rankine.compatibility;

import net.neoforged.fml.ModList;

public class TerraForged {
    public static boolean isInstalled() {
        return ModList.get() != null && ModList.get().getModContainerById("terraforged").isPresent();
    }
}
