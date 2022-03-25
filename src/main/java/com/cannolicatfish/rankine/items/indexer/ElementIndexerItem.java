package com.cannolicatfish.rankine.items.indexer;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


import net.minecraft.world.item.Item.Properties;

public class ElementIndexerItem extends Item {
    private static final ElementIndexerContainerProvider INSTANCE = new ElementIndexerContainerProvider();
    private static final PeriodicTableUtils utils = PeriodicTableUtils.getInstance();


    public ElementIndexerItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!worldIn.isClientSide)
        {
            NetworkHooks.openGui((ServerPlayer) playerIn,INSTANCE);
        }

        return super.use(worldIn, playerIn, handIn);
    }

    public static class ElementIndexerIItemHandler extends ItemStackHandler
    {
    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }

}
    public static class ElementIndexerContainerProvider implements MenuProvider {

        @Override
        public Component getDisplayName() {
            return new TranslatableComponent("container.rankine.element_indexer");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
            return new ElementIndexerContainer(p_createMenu_1_,p_createMenu_2_,p_createMenu_3_);
        }
    }
}
