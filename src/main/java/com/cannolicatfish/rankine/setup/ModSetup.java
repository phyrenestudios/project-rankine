package com.cannolicatfish.rankine.setup;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModSetup {

    public ItemGroup itemGroup = new ItemGroup("rankine") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.NIOBIUM_INGOT);
        }
    };

    public void init() {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
    }

}
