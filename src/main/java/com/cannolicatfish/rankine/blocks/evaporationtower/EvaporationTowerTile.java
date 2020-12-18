package com.cannolicatfish.rankine.blocks.evaporationtower;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.util.WeightedCollection;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.Random;

import static com.cannolicatfish.rankine.init.ModBlocks.EVAPORATION_TOWER_TILE;


public class EvaporationTowerTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    private static final int[] SLOTS_UP = new int[]{};
    private static final int[] SLOTS_DOWN = new int[]{0};
    private static final int[] SLOTS_HORIZONTAL = new int[]{};
    private int cookTime;
    private int cookTimeTotal = Config.EVAPORATION_TOWER_SPEED.get();
    protected NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);
    private final IIntArray towerData = new IIntArray(){
        public int get(int index)
        {
            switch(index)
            {
                case 0:
                    return EvaporationTowerTile.this.cookTime;
                case 1:
                    return EvaporationTowerTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }
        public void set(int index, int value)
        {
            switch(index)
            {
                case 0:
                    EvaporationTowerTile.this.cookTime = value;
                    break;
                case 1:
                    EvaporationTowerTile.this.cookTimeTotal = value;
                    break;
            }
        }
        public int size() {
            return 2;
        }
    };

    public EvaporationTowerTile() {
        super(EVAPORATION_TOWER_TILE);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt,this.items);
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.items);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        return compound;
    }

    @Override
    public void tick() {
        World worldIn = this.getWorld();
        if (!worldIn.isRemote) {

            boolean ready = checkStructure(this.getPos(),worldIn) && this.items.get(0).isEmpty();
            if (ready)
            {
                ++this.cookTime;
                if (this.cookTime == this.cookTimeTotal) {
                    this.items.set(0,randomOutput(worldIn.getRandom()));
                    cookTime = 0;
                }
            } else if (cookTime > 0)
            {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
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

    private boolean checkStructure(BlockPos pos, World worldIn) {
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 0, 1))) {
            if (worldIn.getBlockState(blockpos) != Blocks.MAGMA_BLOCK.getDefaultState() && worldIn.getBlockState(blockpos) != ModBlocks.EVAPORATION_TOWER.getDefaultState()) {
                return false;
            }
        }
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-2, 0, -1), pos.add(-2, 4, 1))) {
            if (!worldIn.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(2, 0, -1), pos.add(2, 4, 1))) {
            if (!worldIn.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -2), pos.add(1, 4, -2))) {
            if (!worldIn.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, 2), pos.add(-1, 4, 2))) {
            if (!worldIn.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        /*
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 1, -1), pos.add(1, 4, 1))) {
            if (worldIn.getFluidState(blockpos) != Fluids.WATER.getDefaultState()) {
                return false;
            }
        }

         */

        return true;
    }

    private ItemStack randomOutput(Random random)
    {
        World worldIn = this.getWorld();
        if (worldIn != null)
        {
            WeightedCollection<ItemStack> COLLECTION;
            if (worldIn.getBiome(this.getPos()).getCategory() == Biome.Category.OCEAN || worldIn.getBiome(this.getPos()).getCategory() == Biome.Category.BEACH)
            {
                COLLECTION = returnOceanCollection();
            } else if (worldIn.getBiome(this.getPos()).getCategory() == Biome.Category.RIVER || worldIn.getBiome(this.getPos()).getCategory() == Biome.Category.SWAMP) {
                COLLECTION = returnRiverCollection();
            } else {
                COLLECTION = returnGroundwaterCollection();
            }
            return COLLECTION.getRandomElement();
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static WeightedCollection<ItemStack> returnOceanCollection(){
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        col.add(1,new ItemStack(ModItems.BROMINE_NUGGET, 1));
        col.add(2,new ItemStack(ModItems.SULFUR_NUGGET, 1));
        col.add(3,new ItemStack(ModItems.POTASSIUM_NUGGET,1));
        col.add(4,new ItemStack(ModItems.CALCIUM_NUGGET,1));
        col.add(5,new ItemStack(ModItems.MAGNESIUM_NUGGET,1));
        col.add(10,new ItemStack(ModItems.SALT,2));
        return col;
    }

    public static WeightedCollection<ItemStack> returnRiverCollection(){
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        col.add(1,new ItemStack(ModItems.CADMIUM_NUGGET, 1));
        col.add(1,new ItemStack(ModItems.CHROMIUM_NUGGET,1));
        col.add(1,new ItemStack(ModItems.LEAD_NUGGET,1));
        col.add(1,new ItemStack(ModItems.MANGANESE_NUGGET,1));
        col.add(1,new ItemStack(ModItems.MERCURY_NUGGET,1));
        col.add(2,new ItemStack(Items.IRON_NUGGET,1));
        col.add(4,new ItemStack(ModItems.ZINC_NUGGET,1));
        col.add(4,new ItemStack(ModItems.COPPER_NUGGET,1));
        col.add(6,new ItemStack(ModItems.CALCIUM_NUGGET,2));
        return col;
    }

    public static WeightedCollection<ItemStack> returnGroundwaterCollection(){
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        col.add(1,new ItemStack(ModItems.COBALT_NUGGET, 1));
        col.add(1,new ItemStack(ModItems.TITANIUM_NUGGET,1));
        col.add(1,new ItemStack(ModItems.ZIRCONIUM_NUGGET,1));
        col.add(1,new ItemStack(ModItems.BORON_NUGGET,1));
        col.add(1,new ItemStack(ModItems.SILVER_NUGGET,1));
        col.add(2,new ItemStack(ModItems.MANGANESE_NUGGET,1));
        col.add(4,new ItemStack(ModItems.LEAD_NUGGET,1));
        col.add(4,new ItemStack(ModItems.LITHIUM_NUGGET,1));
        col.add(6,new ItemStack(ModItems.SILICON_NUGGET,2));
        return col;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Override
    @Nullable
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new EvaporationTowerContainer(i, world, pos, playerInventory, playerEntity, this, this.towerData);
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
        return true;
    }

    @Override
    public void clear() {
        this.items.clear();
    }


}
