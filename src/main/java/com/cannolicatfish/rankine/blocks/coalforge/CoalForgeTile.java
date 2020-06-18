package com.cannolicatfish.rankine.blocks.coalforge;

import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.recipe.CoalForgeRecipes;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cannolicatfish.rankine.init.ModBlocks.COAL_FORGE_TILE;

public class CoalForgeTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private LazyOptional<IItemHandlerModifiable> handler = LazyOptional.of(this::createHandler);
    private IItemHandlerModifiable handler2 = handler.orElseGet(this::createHandler);
    public CoalForgeTile() {
        super(COAL_FORGE_TILE);
    }
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int cookTimeTotal = 200;
    private int redstonePower = 0;
    private final IIntArray furnaceData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return CoalForgeTile.this.burnTime;
                case 1:
                    return CoalForgeTile.this.currentBurnTime;
                case 2:
                    return CoalForgeTile.this.cookTime;
                case 3:
                    return CoalForgeTile.this.cookTimeTotal;
                case 4:
                    return CoalForgeTile.this.redstonePower;
                default:
                    return 0;
            }
        }

        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    CoalForgeTile.this.burnTime = value;
                    break;
                case 1:
                    CoalForgeTile.this.currentBurnTime = value;
                    break;
                case 2:
                    CoalForgeTile.this.cookTime = value;
                    break;
                case 3:
                    CoalForgeTile.this.cookTimeTotal = value;
                    break;
                case 4:
                    CoalForgeTile.this.redstonePower = value;
            }
        }

        public int size() {
            return 5;
        }
    };

    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }

        if (!this.world.isRemote) {
            ItemStack[] inputs = new ItemStack[]{this.handler2.getStackInSlot(0), this.handler2.getStackInSlot(1)};
            ItemStack template = this.handler2.getStackInSlot(2);
            ItemStack fuel = this.handler2.getStackInSlot(3);
            if ((this.isBurning() || !fuel.isEmpty() && !this.handler2.getStackInSlot(0).isEmpty() && !this.handler2.getStackInSlot(1).isEmpty() && !this.handler2.getStackInSlot(2).isEmpty())) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.burnTime = ForgeHooks.getBurnTime(fuel);
                    this.currentBurnTime = this.burnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (fuel.hasContainerItem())
                            this.handler2.setStackInSlot(3, fuel.getContainerItem());
                        else
                        if (!fuel.isEmpty()) {
                            Item item = fuel.getItem();
                            fuel.shrink(1);
                            if (fuel.isEmpty()) {
                                this.handler2.setStackInSlot(3, fuel.getContainerItem());
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt()) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        ItemStack smelting = CoalForgeRecipes.getInstance().getResult(inputs[0],inputs[1],template);
                        if (this.handler2.getStackInSlot(4).getCount() > 0) {
                            this.handler2.getStackInSlot(4).grow(smelting.getCount());
                        } else {
                            this.handler2.insertItem(4, smelting, false);
                        }
                        this.cookTime = 0;
                        if (template.getItem() == ModItems.PICKAXE_TEMPLATE || template.getItem() == ModItems.AXE_TEMPLATE)
                        {
                            inputs[0].shrink(2);
                            inputs[1].shrink(3);
                        }
                        if (template.getItem() == ModItems.SHOVEL_TEMPLATE)
                        {
                            inputs[0].shrink(2);
                            inputs[1].shrink(1);
                        }
                        if (template.getItem() == ModItems.SWORD_TEMPLATE)
                        {
                            inputs[0].shrink(1);
                            inputs[1].shrink(2);
                        }
                        if (template.getItem() == ModItems.SPEAR_TEMPLATE)
                        {
                            inputs[0].shrink(2);
                            inputs[1].shrink(3);
                        }
                        if (template.getItem() == ModItems.HAMMER_TEMPLATE)
                        {
                            inputs[0].shrink(2);
                            inputs[1].shrink(5);
                        }

                        return;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if ((!this.isBurning()) && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, this.isBurning()), 3);
            }
        }

        if (flag1) {
            this.markDirty();
        }

    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        super.read(tag);
        this.burnTime = tag.getInt("BurnTime");
        this.cookTime = tag.getInt("CookTime");
        this.cookTimeTotal = tag.getInt("CookTimeTotal");
        this.currentBurnTime = ForgeHooks.getBurnTime(this.handler2.getStackInSlot(1));
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.putInt("BurnTime", (short)this.burnTime);
            tag.putInt("CookTime", (short)this.cookTime);
            tag.putInt("CookTimeTotal", (short)this.cookTimeTotal);
            tag.put("inv", compound);
        });

        return super.write(tag);
    }

    public boolean redstoneCheck()
    {
        int x = world.getRedstonePowerFromNeighbors(pos);
        if (x > 0)
        {
            redstonePower = 1;
            return true;
        } else
        {
            redstonePower = 0;
            return false;
        }
    }


    private IItemHandlerModifiable createHandler() {
        return new ItemStackHandler(5) {

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 0)
                {
                    return true;
                }
                if (slot == 1)
                {
                    return true;
                }
                if (slot == 2)
                {
                    return true;
                }
                if (slot == 4)
                {
                    return ItemStack.areItemsEqual(CoalForgeRecipes.getInstance().getResult(getStackInSlot(0),getStackInSlot(1),getStackInSlot(2)), stack);

                }
                if (slot == 3)
                {
                    return isFuel(stack);
                }
                return false;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot,stack))
                {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            @Nonnull
            public ItemStack getStackInSlot(int slot)
            {
                validateSlotIndex(slot);
                return this.stacks.get(slot);
            }
        };
    }

    protected static boolean isFuel(ItemStack p_217058_1_) {
        return AbstractFurnaceTileEntity.isFuel(p_217058_1_);
    }

    public boolean isBurning()
    {
        return this.burnTime > 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isBurning(CoalForgeTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canSmelt()
    {
        if((this.handler2.getStackInSlot(0)).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack result = CoalForgeRecipes.getInstance().getResult(this.handler2.getStackInSlot(0),this.handler2.getStackInSlot(1), this.handler2.getStackInSlot(2));
            if(result.isEmpty())
            {
                /*
                System.out.println("Result Itemstack");
                System.out.println(result);
                System.out.println("Result is empty False");*/
                return false;
            }
            else
            {
                ItemStack output = (ItemStack)this.handler2.getStackInSlot(4);
                if(output.isEmpty()) return true;

                if(!output.isItemEqual(result))
                {
                    return false;
                }
                int res = output.getCount() + result.getCount();
                return res <= 64 && res <= output.getMaxStackSize();
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Override
    @Nullable
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CoalForgeContainer(i, world, pos, playerInventory, playerEntity, this.furnaceData);
    }


}
