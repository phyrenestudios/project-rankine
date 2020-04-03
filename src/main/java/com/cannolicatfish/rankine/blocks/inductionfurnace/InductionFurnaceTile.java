package com.cannolicatfish.rankine.blocks.inductionfurnace;

import com.cannolicatfish.rankine.items.ModItems;
import com.cannolicatfish.rankine.recipe.InductionFurnaceRecipes;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
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

import static com.cannolicatfish.rankine.blocks.ModBlocks.INDUCTION_FURNACE_TILE;

public class InductionFurnaceTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private LazyOptional<IItemHandlerModifiable> handler = LazyOptional.of(this::createHandler);
    private IItemHandlerModifiable handler2 = handler.orElseGet(this::createHandler);
    private ItemStack smelting = ItemStack.EMPTY;
    private int smeltingamt = 1;
    public InductionFurnaceTile() {
        super(INDUCTION_FURNACE_TILE);
    }
    private boolean hasProduct = false;
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int cookTimeTotal = 800;
    private final IIntArray furnaceData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return InductionFurnaceTile.this.burnTime;
                case 1:
                    return InductionFurnaceTile.this.currentBurnTime;
                case 2:
                    return InductionFurnaceTile.this.cookTime;
                case 3:
                    return InductionFurnaceTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    InductionFurnaceTile.this.burnTime = value;
                    break;
                case 1:
                    InductionFurnaceTile.this.currentBurnTime = value;
                    break;
                case 2:
                    InductionFurnaceTile.this.cookTime = value;
                    break;
                case 3:
                    InductionFurnaceTile.this.cookTimeTotal = value;
            }
        }
        public int size() {
            return 4;
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
            if (this.isBurning() || !fuel.isEmpty() && !this.handler2.getStackInSlot(0).isEmpty() && !this.handler2.getStackInSlot(1).isEmpty()) {
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
                        ItemStack output = InductionFurnaceRecipes.getInstance().getAlloyResult(inputs[0], inputs[1], inputs[2]).getKey();

                        if (!output.isEmpty()) {
                            smelting = output;
                            this.cookTime++;
                            int[] x = InductionFurnaceRecipes.getInstance().getAlloyResult(inputs[0], inputs[1], inputs[2]).getValue();
                            smeltingamt = x[2];
                            this.handler2.setStackInSlot(0, inputs[0]);
                            this.handler2.setStackInSlot(1, inputs[1]);
                            this.handler2.setStackInSlot(2, inputs[2]);
                            this.hasProduct = true;
                        }
                        if (this.handler2.getStackInSlot(4).getCount() > 0) {
                            this.handler2.getStackInSlot(4).grow(smeltingamt);
                        } else {
                            this.handler2.insertItem(4, smelting, false);
                        }
                        int[] x = InductionFurnaceRecipes.getInstance().getAlloyResult(inputs[0], inputs[1], inputs[2]).getValue();
                        inputs[0].shrink(x[0]);
                        inputs[1].shrink(x[1]);
                        inputs[2].shrink(x[2]);
                        smelting = ItemStack.EMPTY;
                        hasProduct = false;
                        smeltingamt = 1;
                        this.cookTime = 0;
                        flag1 = true;
                        return;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, Boolean.valueOf(this.isBurning())), 3);
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
                world.setBlockState(pos, blockState.with(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
            } else {
                world.setBlockState(pos, blockState.with(BlockStateProperties.LIT, Boolean.valueOf(false)), 11);
            }
            ItemStack[] inputs = new ItemStack[]{this.handler2.getStackInSlot(0), this.handler2.getStackInSlot(1), this.handler2.getStackInSlot(2)};
            ItemStack fuel = this.handler2.getStackInSlot(3);
            if (this.isBurning() || !fuel.isEmpty() && !this.handler2.getStackInSlot(0).isEmpty() && !this.handler2.getStackInSlot(1).isEmpty()) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.burnTime = ForgeHooks.getBurnTime(fuel);
                    this.currentBurnTime = this.burnTime;
                    //System.out.println("lad");

                    if (this.isBurning() && !fuel.isEmpty()) {
                        Item item = fuel.getItem();
                        fuel.shrink(1);

                        if (fuel.isEmpty()) {
                            ItemStack item1 = item.getContainerItem(fuel);
                            this.handler2.setStackInSlot(2, item1);
                        }
                    }
                }
            }
            if (this.isBurning() && this.canSmelt() && this.cookTime > 0) {
                this.cookTime++;
                if (this.cookTime == this.cookTimeTotal) {
                    if (this.handler2.getStackInSlot(4).getCount() > 0) {
                        this.handler2.getStackInSlot(4).grow(smeltingamt);
                    } else {
                        this.handler2.insertItem(4, smelting, false);
                    }
                    int[] x = InductionFurnaceRecipes.getInstance().getAlloyResult(inputs[0], inputs[1], inputs[2]).getValue();
                    inputs[0].shrink(x[0]);
                    inputs[1].shrink(x[1]);
                    inputs[2].shrink(x[2]);
                    smelting = ItemStack.EMPTY;
                    hasProduct = false;
                    smeltingamt = 1;
                    this.cookTime = 0;
                    return;
                }
            } else {
                if (this.canSmelt() && this.isBurning()) {
                    ItemStack output = InductionFurnaceRecipes.getInstance().getAlloyResult(inputs[0], inputs[1], inputs[2]).getKey();

                    if (!output.isEmpty()) {
                        smelting = output;
                        this.cookTime++;
                        int[] x = InductionFurnaceRecipes.getInstance().getAlloyResult(inputs[0], inputs[1], inputs[2]).getValue();
                        smeltingamt = x[2];
                        this.handler2.setStackInSlot(0, inputs[0]);
                        this.handler2.setStackInSlot(1, inputs[1]);
                        this.handler2.setStackInSlot(2, inputs[2]);
                        this.hasProduct = true;
                    }
                }
                if (!this.isBurning() && this.cookTime > 0) {
                    this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
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
                    System.out.println("InductionFurnaceTile.isFuel");
                    return true;
                }
                if (slot == 4 && stack.getItem() == ModItems.BRONZE_ALLOY || stack.getItem() == ModItems.BRASS_ALLOY || stack.getItem() == ModItems.CAST_IRON_INGOT || stack.getItem() == Items.GLASS )
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
    public static boolean isBurning(InductionFurnaceTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    private boolean canSmelt()
    {
        if(((ItemStack)this.handler2.getStackInSlot(0)).isEmpty() || ((ItemStack)this.handler2.getStackInSlot(1)).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack result = InductionFurnaceRecipes.getInstance().getAlloyResult((ItemStack)this.handler2.getStackInSlot(0), (ItemStack)this.handler2.getStackInSlot(1), (ItemStack)this.handler2.getStackInSlot(2)).getKey();
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
        return new InductionFurnaceContainer(i, world, pos, playerInventory, playerEntity, this.furnaceData);
    }


}

