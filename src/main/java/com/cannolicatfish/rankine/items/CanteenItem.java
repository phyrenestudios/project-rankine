package com.cannolicatfish.rankine.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class CanteenItem extends Item implements IFluidHandlerItem {
    public CanteenItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_77661_1_) {
        return UseAnim.DRINK;
    }

    @NotNull
    @Override
    public ItemStack getContainer() {
        return new ItemStack(this);
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Nonnull
    public FluidStack getFluid()
    {
        return FluidStack.EMPTY;
    }


    public void setFluid()
    {

    }

    @NotNull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return null;
    }

    @Override
    public int getTankCapacity(int tank) {
        return 800;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return true;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (!resource.isEmpty() || !isFluidValid(0,resource))
        {
            return 0;
        }

        if (action.execute())
        {
            setFluid();
        }

        return 1000;
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return null;
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return null;
    }

}
