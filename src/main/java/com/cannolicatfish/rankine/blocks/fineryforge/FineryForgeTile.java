package com.cannolicatfish.rankine.blocks.fineryforge;


import com.cannolicatfish.rankine.items.ModItems;
import com.cannolicatfish.rankine.recipe.FineryForgeRecipes;
import javafx.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
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

import static com.cannolicatfish.rankine.blocks.ModBlocks.FINERY_FORGE_TILE;

public class FineryForgeTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private LazyOptional<IItemHandlerModifiable> handler = LazyOptional.of(this::createHandler);
    private IItemHandlerModifiable handler2 = handler.orElseGet(this::createHandler);
    private ItemStack smelting = ItemStack.EMPTY;
    private ItemStack crushing = ItemStack.EMPTY;
    private ItemStack crushing2 = ItemStack.EMPTY;
    private boolean hasProduct = false;
    private boolean hasCrushingProduct = false;
    private int smeltingamt = 1;
    private int crushingamt = 1;
    private float output1chance = 0f;
    private float output2chance = 1f;
    public FineryForgeTile() {
        super(FINERY_FORGE_TILE);
    }
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int cookTimeTotal = 200;
    private int crushTime;
    private int crushTimeTotal = 200;
    private int redstonePower = 0;
    private final IIntArray furnaceData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return FineryForgeTile.this.burnTime;
                case 1:
                    return FineryForgeTile.this.currentBurnTime;
                case 2:
                    return FineryForgeTile.this.cookTime;
                case 3:
                    return FineryForgeTile.this.cookTimeTotal;
                case 4:
                    return FineryForgeTile.this.crushTime;
                case 5:
                    return FineryForgeTile.this.crushTimeTotal;
                case 6:
                    return FineryForgeTile.this.redstonePower;
                default:
                    return 0;
            }
        }

        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    FineryForgeTile.this.burnTime = value;
                    break;
                case 1:
                    FineryForgeTile.this.currentBurnTime = value;
                    break;
                case 2:
                    FineryForgeTile.this.cookTime = value;
                    break;
                case 3:
                    FineryForgeTile.this.cookTimeTotal = value;
                    break;
                case 4:
                    FineryForgeTile.this.crushTime = value;
                    break;
                case 5:
                    FineryForgeTile.this.crushTimeTotal = value;
                    break;
                case 6:
                    FineryForgeTile.this.redstonePower = value;
                    break;
            }
        }

        public int size() {
            return 7;
        }
    };

    @Override
    public void tick() {
        if (!this.world.isRemote) {
            BlockState blockState = world.getBlockState(pos);
            if (this.isBurning()) {
                --this.burnTime;
                world.setBlockState(pos, blockState.with(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
            } else {
                redstoneCheck(blockState);
                world.setBlockState(pos, blockState.with(BlockStateProperties.LIT, Boolean.valueOf(false)), 11);
            }
            ItemStack input = this.handler2.getStackInSlot(4);
            ItemStack fuel = this.handler2.getStackInSlot(1);
            if (!this.isBurning() || !fuel.isEmpty() && !this.handler2.getStackInSlot(4).isEmpty()) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.burnTime = ForgeHooks.getBurnTime(fuel);
                    this.currentBurnTime = burnTime;

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
            if (this.isBurning() && cookTime > 0) {
                cookTime++;
                if (this.cookTime == this.cookTimeTotal) {
                    if (this.handler2.getStackInSlot(0).getCount() > 0) {
                        this.handler2.getStackInSlot(0).grow(smeltingamt);
                    } else {
                        this.handler2.insertItem(0, smelting, false);
                    }

                    smelting = ItemStack.EMPTY;
                    hasProduct = false;
                    smeltingamt = 1;
                    this.cookTime = 0;
                    return;
                }
            } else {
                if (this.canSmelt() && this.isBurning()) {
                    ItemStack output = FineryForgeRecipes.getInstance().getResult(input);
                    if (!output.isEmpty()) {
                        smelting = output;
                        this.cookTime++;
                        if (!hasProduct) {
                            input.shrink(1);
                        }
                        this.handler2.setStackInSlot(4, input);
                        this.hasProduct = true;
                    }
                }
                if (!this.isBurning() && this.cookTime > 0) {
                    this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
                }
            }

            ItemStack crushinput = this.handler2.getStackInSlot(0);

            if (this.canCrush() && !this.isCrushing() && redstoneCheck(blockState)) {
                Pair<ItemStack, Float[]> output1 = FineryForgeRecipes.getInstance().getPrimaryResult(crushinput);
                Pair<ItemStack, Float> output2 = FineryForgeRecipes.getInstance().getSecondaryResult(crushinput);
                crushing2 = output2.getKey();
                output2chance = output2.getValue();

                if (!output1.getKey().isEmpty()) {
                    crushing = output1.getKey();
                    crushingamt = output1.getValue()[0].intValue();
                    output1chance = output1.getValue()[1];
                    crushTime++;
                    //write code that: returns shrink amount for inputs and smeltingamt
                    if (!hasCrushingProduct) {
                        crushinput.shrink(1);
                    }
                    hasCrushingProduct = true;
                    this.handler2.setStackInSlot(0, crushinput);
                }
            }
            if (this.isCrushing() && redstoneCheck(blockState)) {
                crushTime++;
                System.out.println(crushTimeTotal - crushTime);
                if (crushTime == crushTimeTotal) {
                    if (this.handler2.getStackInSlot(2).getCount() > 0) {
                        this.handler2.getStackInSlot(2).grow(crushingamt);
                    }
                    if (this.handler2.getStackInSlot(2).getCount() <= 0) {
                        this.handler2.insertItem(2, crushing, false);
                    }
                    Random random = new Random();
                    if (random.nextFloat() < output1chance) {
                        if (this.handler2.getStackInSlot(2).getCount() > 0) {
                            this.handler2.getStackInSlot(2).grow(1);
                        }
                        if (this.handler2.getStackInSlot(2).getCount() <= 0) {
                            this.handler2.insertItem(2, crushing, false);
                        }
                    }
                    random = new Random();
                    if (random.nextFloat() < output2chance) {
                        crushing2 = FineryForgeRecipes.getInstance().getSecondaryResult(crushinput).getKey();
                        if (this.handler2.getStackInSlot(3).getCount() > 0) {
                            this.handler2.getStackInSlot(3).grow(1);
                        } else {
                            this.handler2.insertItem(3, crushing2, false);
                        }
                    }


                    crushing = ItemStack.EMPTY;
                    crushing2 = ItemStack.EMPTY;
                    hasCrushingProduct = false;
                    crushingamt = 1;
                    output2chance = 1f;
                    crushTime = 0;
                    return;
                }
            }
        }
    }

    public boolean redstoneCheck(BlockState block)
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
        this.cookTime = tag.getInt("CookTime");
        this.cookTimeTotal = tag.getInt("CookTimeTotal");
        this.crushTime = tag.getInt("CrushTime");
        this.crushTimeTotal = tag.getInt("CrushTimeTotal");
        this.currentBurnTime = ForgeHooks.getBurnTime(this.handler2.getStackInSlot(1));
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.putInt("BurnTime", (short)this.burnTime);
            tag.putInt("CookTime", (short)this.cookTime);
            tag.putInt("CookTimeTotal", (short)this.cookTimeTotal);
            tag.putInt("CrushTime", (short)this.crushTime);
            tag.putInt("CrushTimeTotal", (short)this.crushTimeTotal);
            tag.put("inv", compound);
        });

        return super.write(tag);
    }


    private IItemHandlerModifiable createHandler() {
        return new ItemStackHandler(6) {

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
                    System.out.println("FineryForgeTile.isFuel");
                    return true;
                }
                if (slot == 2)
                {
                    // ADD VALIDITY FOR SLOTS 2 AND 3
                    return true;
                }
                if (slot == 3)
                {
                    return true;
                }
                if (slot == 4)
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
        return p_217058_1_.getItem() == Items.CHARCOAL || p_217058_1_.getItem() == ModItems.COKE;
    }

    public boolean isBurning()
    {
        return this.burnTime > 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isBurning(com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeTile te)
    {
        return te.furnaceData.get(0) > 0;
    }

    public boolean isCrushing()
    {
        return this.crushTime > 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isCrushing(com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeTile te)
    {
        return te.furnaceData.get(4) > 0;
    }

    private boolean canSmelt()
    {
        if(((ItemStack)this.handler2.getStackInSlot(4)).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack result = FineryForgeRecipes.getInstance().getResult((ItemStack)this.handler2.getStackInSlot(4));

            if(result.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack output = (ItemStack)this.handler2.getStackInSlot(0);
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

    private boolean canCrush()
    {
        if(((ItemStack)this.handler2.getStackInSlot(0)).isEmpty())
        {
            return false;
        }
        else
        {
            Pair<ItemStack, Float[]> preresult = FineryForgeRecipes.getInstance().getPrimaryResult((ItemStack)this.handler2.getStackInSlot(0));
            Pair<ItemStack, Float> preresult2 = FineryForgeRecipes.getInstance().getSecondaryResult(((ItemStack)this.handler2.getStackInSlot(0)));
            ItemStack result = preresult.getKey();
            ItemStack result2 = preresult2.getKey();
            if(result.isEmpty())
            {
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
        return new FineryForgeContainer(i, world, pos, playerInventory, playerEntity, this.furnaceData);
    }


}
