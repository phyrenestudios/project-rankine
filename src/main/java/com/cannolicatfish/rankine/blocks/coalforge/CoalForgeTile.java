package com.cannolicatfish.rankine.blocks.coalforge;

import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.items.ItemTemplate;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.CoalForgeRecipes;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
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

public class CoalForgeTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    private static final int[] SLOTS_UP = new int[]{0,1,2};
    private static final int[] SLOTS_DOWN = new int[]{4};
    private static final int[] SLOTS_HORIZONTAL = new int[]{3};
    public CoalForgeTile() {
        super(COAL_FORGE_TILE);
    }
    protected NonNullList<ItemStack> items = NonNullList.withSize(13, ItemStack.EMPTY);
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

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt,this.items);
        this.burnTime = nbt.getInt("BurnTime");
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        this.currentBurnTime = ForgeHooks.getBurnTime(this.items.get(1));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        ItemStackHelper.saveAllItems(compound, this.items);

        return compound;
    }

    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }

        if (!this.world.isRemote) {
            ItemStack[] inputs = new ItemStack[]{this.items.get(0), this.items.get(1)};
            ItemStack template = this.items.get(2);
            ItemStack fuel = this.items.get(3);
            if ((this.isBurning() || !fuel.isEmpty() && !this.items.get(0).isEmpty() && !this.items.get(1).isEmpty() && !this.items.get(2).isEmpty())) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.burnTime = ForgeHooks.getBurnTime(fuel);
                    this.currentBurnTime = this.burnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (fuel.hasContainerItem())
                            this.items.set(3, fuel.getContainerItem());
                        else
                        if (!fuel.isEmpty()) {
                            Item item = fuel.getItem();
                            fuel.shrink(1);
                            if (fuel.isEmpty()) {
                                this.items.set(3, fuel.getContainerItem());
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt()) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        ItemStack smelting = CoalForgeRecipes.getInstance().getResult(inputs[0],inputs[1],template);
                        if (this.items.get(4).getCount() > 0) {
                            this.items.get(4).grow(smelting.getCount());
                        } else {
                            this.items.set(4, smelting);
                        }
                        this.cookTime = 0;
                        if (template.getItem() == ModItems.PICKAXE_TEMPLATE || template.getItem() == ModItems.AXE_TEMPLATE)
                        {
                            inputs[0].shrink(2);
                            inputs[1].shrink(3);
                        }
                        else if (template.getItem() == ModItems.SHOVEL_TEMPLATE)
                        {
                            inputs[0].shrink(2);
                            inputs[1].shrink(1);
                        }
                        else if (template.getItem() == ModItems.SWORD_TEMPLATE)
                        {
                            inputs[0].shrink(1);
                            inputs[1].shrink(2);
                        }
                        else if (template.getItem() == ModItems.SPEAR_TEMPLATE)
                        {
                            inputs[0].shrink(2);
                            inputs[1].shrink(3);
                        }
                        else if (template.getItem() == ModItems.HAMMER_TEMPLATE)
                        {
                            inputs[0].shrink(2);
                            inputs[1].shrink(5);
                        }
                        else if (template.getItem() == ModItems.HOE_TEMPLATE)
                        {
                            inputs[0].shrink(2);
                            inputs[1].shrink(2);
                        } else if (template.getItem() == ModItems.PENDANT_TEMPLATE)
                        {
                            inputs[0].shrink(4);
                            inputs[1].shrink(24);
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
        if((this.items.get(0)).isEmpty() || AlloyItem.getComposition(this.items.get(1)).size() == 0)
        {
            return false;
        }
        else
        {
            ItemStack result = CoalForgeRecipes.getInstance().getResult(this.items.get(0),this.items.get(1), this.items.get(2));
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
                ItemStack output = this.items.get(4);
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

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void remove() {
        super.remove();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Override
    @Nullable
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CoalForgeContainer(i, world, pos, playerInventory, playerEntity, this, this.furnaceData);
    }


    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.cookTimeTotal = 200;
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        switch (index)
        {
            case 0:
                return stack.getItem().getTags().contains(new ResourceLocation("forge:rods")) || stack.getItem().getTags().contains(new ResourceLocation("forge:gems"));
            case 1:
                return stack.getItem() instanceof AlloyItem;
            case 2:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return stack.getItem() instanceof ItemTemplate;
            case 3:
                return AbstractFurnaceTileEntity.isFuel(stack);
            case 4:
                return ItemStack.areItemsEqual(CoalForgeRecipes.getInstance().getResult(getStackInSlot(0),getStackInSlot(1),getStackInSlot(2)), stack);
            default:
                return false;
        }
    }

    @Override
    public void clear() {
        this.items.clear();
    }
}
