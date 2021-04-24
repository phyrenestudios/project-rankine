package com.cannolicatfish.rankine.items.indexer;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class ElementIndexerItem extends Item {
    private static final ElementIndexerContainerProvider INSTANCE = new ElementIndexerContainerProvider();
    private static final PeriodicTableUtils utils = PeriodicTableUtils.getInstance();

    @CapabilityInject(IItemHandler.class)
    public static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

    public ElementIndexerItem(Properties properties) {
        super(properties);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote)
        {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn,INSTANCE);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public static class ElementIndexerIItemHandler extends ItemStackHandler
    {
    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }

}
    public static class ElementIndexerContainerProvider implements INamedContainerProvider {

        @Override
        public ITextComponent getDisplayName() {
            return new TranslationTextComponent("container.rankine.element_indexer");
        }

        @Nullable
        @Override
        public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
            return new ElementIndexerContainer(p_createMenu_1_,p_createMenu_2_,p_createMenu_3_);
        }
    }
}
