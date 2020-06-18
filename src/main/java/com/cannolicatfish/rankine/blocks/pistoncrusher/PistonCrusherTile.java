package com.cannolicatfish.rankine.blocks.pistoncrusher;


import com.cannolicatfish.rankine.recipe.PistonCrusherRecipes;
import javafx.util.Pair;
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

import java.util.Random;

import static com.cannolicatfish.rankine.init.ModBlocks.PISTON_CRUSHER_TILE;

public class PistonCrusherTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private LazyOptional<IItemHandlerModifiable> handler = LazyOptional.of(this::createHandler);
    private IItemHandlerModifiable handler2 = handler.orElseGet(this::createHandler);
    public PistonCrusherTile() {
        super(PISTON_CRUSHER_TILE);
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
                    return PistonCrusherTile.this.burnTime;
                case 1:
                    return PistonCrusherTile.this.currentBurnTime;
                case 2:
                    return PistonCrusherTile.this.cookTime;
                case 3:
                    return PistonCrusherTile.this.cookTimeTotal;
                case 4:
                    return PistonCrusherTile.this.redstonePower;
                default:
                    return 0;
            }
        }

        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    PistonCrusherTile.this.burnTime = value;
                    break;
                case 1:
                    PistonCrusherTile.this.currentBurnTime = value;
                    break;
                case 2:
                    PistonCrusherTile.this.cookTime = value;
                    break;
                case 3:
                    PistonCrusherTile.this.cookTimeTotal = value;
                    break;
                case 4:
                    PistonCrusherTile.this.redstonePower = value;
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
            ItemStack input = this.handler2.getStackInSlot(0);
            ItemStack fuel = this.handler2.getStackInSlot(1);
            if ((this.isBurning() || !fuel.isEmpty() && !this.handler2.getStackInSlot(0).isEmpty()) && redstoneCheck()) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.burnTime = ForgeHooks.getBurnTime(fuel);
                    this.currentBurnTime = this.burnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (fuel.hasContainerItem())
                            this.handler2.setStackInSlot(1, fuel.getContainerItem());
                        else
                        if (!fuel.isEmpty()) {
                            Item item = fuel.getItem();
                            fuel.shrink(1);
                            if (fuel.isEmpty()) {
                                this.handler2.setStackInSlot(1, fuel.getContainerItem());
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt()) {
                    cookTime++;
                    if (cookTime >= cookTimeTotal) {
                        int smeltingamt = PistonCrusherRecipes.getInstance().getPrimaryResult(input).getValue()[0].intValue();
                        float output1chance = PistonCrusherRecipes.getInstance().getPrimaryResult(input).getValue()[1];
                        ItemStack smelting = new ItemStack(PistonCrusherRecipes.getInstance().getPrimaryResult(input).getKey().getItem(), smeltingamt);


                        if (this.handler2.getStackInSlot(2).getCount() > 0) {
                            this.handler2.getStackInSlot(2).grow(smeltingamt);
                        } if (this.handler2.getStackInSlot(2).getCount() <= 0) {
                            this.handler2.insertItem(2, smelting, false);
                        }
                        Random random = new Random();
                        if (random.nextFloat() < output1chance)
                        {
                            if (this.handler2.getStackInSlot(2).getCount() > 0) {
                                this.handler2.getStackInSlot(2).grow(1);
                            } if (this.handler2.getStackInSlot(2).getCount() <= 0) {
                            this.handler2.insertItem(2, smelting, false);
                        }
                        }
                        float output2chance = PistonCrusherRecipes.getInstance().getSecondaryResult(input).getValue();
                        if (random.nextFloat() < output2chance)
                        {
                            ItemStack smelting2 = PistonCrusherRecipes.getInstance().getSecondaryResult(input).getKey();
                            if (this.handler2.getStackInSlot(3).getCount() > 0) {
                                this.handler2.getStackInSlot(3).grow(1);
                            } else {
                                this.handler2.insertItem(3, smelting2, false);
                            }
                        }
                        input.shrink(1);
                        cookTime = 0;
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
    /*
    @Override
    public void tick(){
        if (!this.world.isRemote) {
            BlockState blockState = world.getBlockState(pos);
            if (this.isBurning()) {
                --this.burnTime;
                System.out.println(this.burnTime);
                if (redstoneCheck(blockState))
                {
                    world.setBlockState(pos, blockState.with(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
                } else
                {
                    world.setBlockState(pos, blockState.with(BlockStateProperties.LIT, Boolean.valueOf(false)), 11);
                }
            } else {
                world.setBlockState(pos, blockState.with(BlockStateProperties.LIT, Boolean.valueOf(false)), 11);
            }
            ItemStack input = this.handler2.getStackInSlot(0);
            ItemStack fuel = this.handler2.getStackInSlot(1);
            if (!this.isBurning() || !fuel.isEmpty() && !this.handler2.getStackInSlot(0).isEmpty()) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.burnTime = ForgeHooks.getBurnTime(fuel);
                    this.currentBurnTime = burnTime;
                    //System.out.println("lad");

                    if (this.isBurning() && !fuel.isEmpty()) {
                        Item item = fuel.getItem();
                        fuel.shrink(1);

                        if (fuel.isEmpty()) {
                            ItemStack item1 = item.getContainerItem(fuel);
                            this.handler2.setStackInSlot(1, item1);
                        }
                    }
                }
            }
            if (this.isBurning() && cookTime > 0 && redstoneCheck(blockState)) {
                cookTime++;
                if (cookTime >= cookTimeTotal) {
                    if (this.handler2.getStackInSlot(2).getCount() > 0) {
                        this.handler2.getStackInSlot(2).grow(smeltingamt);
                    } if (this.handler2.getStackInSlot(2).getCount() <= 0) {
                        this.handler2.insertItem(2, smelting, false);
                    }
                    Random random = new Random();
                    if (random.nextFloat() < output1chance)
                    {
                        if (this.handler2.getStackInSlot(2).getCount() > 0) {
                            this.handler2.getStackInSlot(2).grow(1);
                        } if (this.handler2.getStackInSlot(2).getCount() <= 0) {
                        this.handler2.insertItem(2, smelting, false);
                    }
                    }
                    random = new Random();
                    if (random.nextFloat() < output2chance)
                    {
                        smelting2 = PistonCrusherRecipes.getInstance().getSecondaryResult(input).getKey();
                        if (this.handler2.getStackInSlot(3).getCount() > 0) {
                            this.handler2.getStackInSlot(3).grow(smeltingamt);
                        } else {
                        this.handler2.insertItem(3, smelting2, false);
                        }
                    }


                    smelting = ItemStack.EMPTY;
                    smelting2 = ItemStack.EMPTY;
                    hasProduct = false;
                    smeltingamt = 1;
                    output2chance = 1f;
                    cookTime = 0;
                    return;
                }
            } else {
                if (this.canSmelt() && this.isBurning()) {
                    Pair<ItemStack, Float[]> output1 = PistonCrusherRecipes.getInstance().getPrimaryResult(input);
                    Pair<ItemStack, Float> output2 = PistonCrusherRecipes.getInstance().getSecondaryResult(input);
                    smelting2 = output2.getKey();
                    output2chance = output2.getValue();

                    if (!output1.getKey().isEmpty()) {
                        smelting = output1.getKey();
                        smeltingamt = output1.getValue()[0].intValue();
                        output1chance = output1.getValue()[1];
                        cookTime++;

                        //write code that: returns shrink amount for inputs and smeltingamt
                        if (!hasProduct)
                        {
                            input.shrink(1);
                        }
                        hasProduct = true;
                        this.handler2.setStackInSlot(0, input);
                    }
                }
            }

        }
    }

     */

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
        return new ItemStackHandler(4) {

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
                if (slot == 1 && isFuel(stack))
                {
                    return true;
                }
                if (slot == 2)
                {
                    return PistonCrusherRecipes.getInstance().getPrimaryResult(getStackInSlot(0)).getKey().getItem() == stack.getItem();

                }
                if (slot == 3)
                {
                    return PistonCrusherRecipes.getInstance().getSecondaryResult(getStackInSlot(0)).getKey().getItem() == stack.getItem();
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
    public static boolean isBurning(com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canSmelt()
    {
        if(((ItemStack)this.handler2.getStackInSlot(0)).isEmpty())
        {
            return false;
        }
        else
        {
            Pair<ItemStack, Float[]> preresult = PistonCrusherRecipes.getInstance().getPrimaryResult((ItemStack)this.handler2.getStackInSlot(0));
            Pair<ItemStack, Float> preresult2 = PistonCrusherRecipes.getInstance().getSecondaryResult(((ItemStack)this.handler2.getStackInSlot(0)));
            ItemStack result = preresult.getKey();
            ItemStack result2 = preresult2.getKey();
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
                ItemStack output = (ItemStack)this.handler2.getStackInSlot(2);
                ItemStack output2 = (ItemStack)this.handler2.getStackInSlot(3);
                if(output.isEmpty() && output2.isEmpty()) return true;

                if(!output.isItemEqual(result) && !output2.isItemEqual(result2))
                {
                    return false;
                }
                int res = output.getCount() + preresult.getValue()[0].intValue();
                int res2 = output2.getCount() + 1;
                return res <= 64 && res <= output.getMaxStackSize() && res2 <= 64 && res2 <= output2.getMaxStackSize();
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
        return new PistonCrusherContainer(i, world, pos, playerInventory, playerEntity, this.furnaceData);
    }


}
