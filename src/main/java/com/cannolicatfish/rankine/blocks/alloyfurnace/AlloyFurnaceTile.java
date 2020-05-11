package com.cannolicatfish.rankine.blocks.alloyfurnace;

import com.cannolicatfish.rankine.items.ModItems;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipesComplex;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.cannolicatfish.rankine.blocks.ModBlocks.ALLOY_FURNACE_TILE;

public class AlloyFurnaceTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private LazyOptional<IItemHandlerModifiable> handler = LazyOptional.of(this::createHandler);
    private IItemHandlerModifiable handler2 = handler.orElseGet(this::createHandler);
    private ItemStack smelting = ItemStack.EMPTY;
    private int smeltingamt = 1;
    public AlloyFurnaceTile() {
        super(ALLOY_FURNACE_TILE);
    }
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int cookTimeTotal = 800;
    private int percentSlot1 = 0;
    private int percentSlot2 = 0;
    private int percentSlot3 = 0;
    private int recipeLocked = 0;
    private int redstonePower = 0;
    private List<ItemStack> setRecipe = Collections.emptyList();
    private final IIntArray furnaceData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return AlloyFurnaceTile.this.burnTime;
                case 1:
                    return AlloyFurnaceTile.this.currentBurnTime;
                case 2:
                    return AlloyFurnaceTile.this.cookTime;
                case 3:
                    return AlloyFurnaceTile.this.cookTimeTotal;
                case 4:
                    return AlloyFurnaceTile.this.percentSlot1;
                case 5:
                    return AlloyFurnaceTile.this.percentSlot2;
                case 6:
                    return AlloyFurnaceTile.this.percentSlot3;
                case 7:
                    return AlloyFurnaceTile.this.recipeLocked;
                case 8:
                    return AlloyFurnaceTile.this.redstonePower;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    AlloyFurnaceTile.this.burnTime = value;
                    break;
                case 1:
                    AlloyFurnaceTile.this.currentBurnTime = value;
                    break;
                case 2:
                    AlloyFurnaceTile.this.cookTime = value;
                    break;
                case 3:
                    AlloyFurnaceTile.this.cookTimeTotal = value;
                    break;
                case 4:
                    AlloyFurnaceTile.this.percentSlot1 = value;
                    break;
                case 5:
                    AlloyFurnaceTile.this.percentSlot2 = value;
                    break;
                case 6:
                    AlloyFurnaceTile.this.percentSlot3 = value;
                    break;
                case 7:
                    AlloyFurnaceTile.this.recipeLocked = value;
                    getRecipe();
                    break;
                case 8:
                    AlloyFurnaceTile.this.redstonePower = value;
                    break;
            }
        }
        public int size() {
            return 9;
        }
    };

    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }

        if (!this.world.isRemote) {
            ItemStack[] inputs = new ItemStack[]{this.handler2.getStackInSlot(0), this.handler2.getStackInSlot(1), this.handler2.getStackInSlot(2)};
            ItemStack fuel = this.handler2.getStackInSlot(3);
            if ((this.isBurning() || !fuel.isEmpty() && !this.handler2.getStackInSlot(0).isEmpty() && !this.handler2.getStackInSlot(1).isEmpty()) && redstoneCheck()) {
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
                        ItemStack output = AlloyingRecipesComplex.getInstance().getAlloyResult(inputs[0], inputs[1], inputs[2]).getKey();
                        if (output.getItem() instanceof AlloyItem)
                        {
                            int[] v = AlloyingRecipesComplex.getInstance().getAlloyResult(inputs[0], inputs[1], inputs[2]).getValue();
                            AlloyItem.addAlloy(output,new AlloyData(AlloyingRecipesComplex.getInstance().getComposition(inputs[0], inputs[1], inputs[2])));
                        }
                        if (!output.isEmpty()) {
                            smelting = output;
                            this.cookTime++;
                            int[] x = AlloyingRecipesComplex.getInstance().getAlloyResult(inputs[0], inputs[1], inputs[2]).getValue();
                            smeltingamt = x[2];
                            this.handler2.setStackInSlot(0, inputs[0]);
                            this.handler2.setStackInSlot(1, inputs[1]);
                            this.handler2.setStackInSlot(2, inputs[2]);
                        }
                        if (this.handler2.getStackInSlot(4).getCount() > 0) {
                            this.handler2.getStackInSlot(4).grow(smelting.getCount());
                        } else {
                            this.handler2.insertItem(4, smelting, false);
                        }
                        int[] x = AlloyingRecipesComplex.getInstance().getAlloyResult(inputs[0], inputs[1], inputs[2]).getValue();
                        inputs[0].shrink(x[0]);
                        inputs[1].shrink(x[1]);
                        inputs[2].shrink(x[2]);
                        smelting = ItemStack.EMPTY;
                        smeltingamt = 1;
                        this.cookTime = 0;
                        flag1 = true;
                        return;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if ((!this.isBurning() || !redstoneCheck()) && this.cookTime > 0) {
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

    private boolean redstoneCheck()
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

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        super.read(tag);
        this.burnTime = tag.getInt("BurnTime");
        this.currentBurnTime = tag.getInt("CurrentBurnTime");
        this.cookTime = tag.getInt("CookTime");
        this.cookTimeTotal = tag.getInt("CookTimeTotal");
        this.currentBurnTime = ForgeHooks.getBurnTime(this.handler2.getStackInSlot(2));
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.putInt("BurnTime", this.burnTime);
            tag.putInt("CurrentBurnTime",this.currentBurnTime);
            tag.putInt("CookTime", this.cookTime);
            tag.putInt("CookTimeTotal", this.cookTimeTotal);
            tag.put("inv", compound);
        });

        return super.write(tag);
    }



    private IItemHandlerModifiable createHandler() {
        return new ItemStackHandler(5) {

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                canSmelt();
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 0 || slot == 1 || slot == 2)
                {
                    //return stack.getItem() == ModItems.COPPER_INGOT || stack.getItem() == ModItems.TIN_INGOT;
                    return true;
                }
                if (slot == 3 && isFuel(stack))
                {
                    //System.out.println("AlloyFurnaceTile.isFuel");
                    return true;
                }
                if (slot == 4 && stack.getItem() instanceof AlloyItem)
                {
                    return true;
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
    public static boolean isBurning(AlloyFurnaceTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canSmelt()
    {
        /*
        System.out.println(recipeLocked);
        if (recipeLocked == 1 && (this.handler2.getStackInSlot(0) != setRecipe.get(0) || this.handler2.getStackInSlot(1) != setRecipe.get(1) || this.handler2.getStackInSlot(2) != setRecipe.get(2)))
        {
            System.out.println(setRecipe);
            return false;
        }*/
        if(((ItemStack)this.handler2.getStackInSlot(0)).isEmpty() || ((ItemStack)this.handler2.getStackInSlot(1)).isEmpty())
        {
            this.percentSlot1 = 0;
            this.percentSlot2 = 0;
            this.percentSlot3 = 0;
            return false;
        }
        else
        {
            ItemStack result = AlloyingRecipesComplex.getInstance().getAlloyResult((ItemStack)this.handler2.getStackInSlot(0), (ItemStack)this.handler2.getStackInSlot(1), (ItemStack)this.handler2.getStackInSlot(2)).getKey();
            this.percentSlot1 = AlloyingRecipesComplex.getInstance().getPercent((ItemStack)this.handler2.getStackInSlot(0), (ItemStack)this.handler2.getStackInSlot(1), (ItemStack)this.handler2.getStackInSlot(2), 0);
            this.percentSlot2 = AlloyingRecipesComplex.getInstance().getPercent((ItemStack)this.handler2.getStackInSlot(0), (ItemStack)this.handler2.getStackInSlot(1), (ItemStack)this.handler2.getStackInSlot(2), 1);
            this.percentSlot3 = AlloyingRecipesComplex.getInstance().getPercent((ItemStack)this.handler2.getStackInSlot(0), (ItemStack)this.handler2.getStackInSlot(1), (ItemStack)this.handler2.getStackInSlot(2), 2);
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

    private void getRecipe()
    {
        setRecipe = Arrays.asList((ItemStack)this.handler2.getStackInSlot(0), (ItemStack)this.handler2.getStackInSlot(1), (ItemStack)this.handler2.getStackInSlot(2));
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
        return new AlloyFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.furnaceData);
    }


}

