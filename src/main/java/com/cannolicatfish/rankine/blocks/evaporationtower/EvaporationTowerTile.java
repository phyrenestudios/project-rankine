package com.cannolicatfish.rankine.blocks.evaporationtower;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.CrucibleRecipe;
import com.cannolicatfish.rankine.recipe.EvaporationRecipe;
import com.cannolicatfish.rankine.util.WeightedCollection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Inventory;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.cannolicatfish.rankine.init.RankineBlocks.EVAPORATION_TOWER_TILE;


public class EvaporationTowerTile extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    private static final int[] SLOTS_UP = new int[]{};
    private static final int[] SLOTS_DOWN = new int[]{0};
    private static final int[] SLOTS_HORIZONTAL = new int[]{};
    private int cookTime;
    private int cookTimeTotal = 6400;
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
        public int getCount() {
            return 2;
        }
    };

    public EvaporationTowerTile() {
        super(EVAPORATION_TOWER_TILE);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt,this.items);
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        ItemStackHelper.saveAllItems(compound, this.items);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        return compound;
    }

    @Override
    public void tick() {
        World worldIn = this.getLevel();
        if (!worldIn.isClientSide) {
            ItemStack output = this.items.get(0);
            BlockPos p = this.getBlockPos();
            EvaporationRecipe recipe = this.getEvaporationRecipe(p.above());
            if (recipe != null) {
                if (this.cookTimeTotal != recipe.getTime()) {
                    this.cookTimeTotal = recipe.getTime();
                }
                if (recipe.isLarge()) {
                    int h = checkStructure(p, worldIn, worldIn.getBlockState(p.above()).getBlock());
                    if (h > 0 && output.isEmpty()) {
                        ++this.cookTime;
                        if (this.cookTime >= this.cookTimeTotal / h) {
                            this.items.set(0, recipe.getEvaporationResult(worldIn,worldIn.getBiome(p).getRegistryName()));
                            cookTime = 0;
                        }
                    } else if (cookTime > 0) {
                        this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
                    }
                } else {
                    if (boilerStructure(p, worldIn) && output.isEmpty()) {
                        ++this.cookTime;
                        if (this.cookTime >= this.cookTimeTotal) {
                            worldIn.setBlock(p.above(), Blocks.AIR.defaultBlockState(), 3);
                            this.items.set(0, recipe.getEvaporationResult(worldIn,worldIn.getBiome(p).getRegistryName()));
                            cookTime = 0;
                        }
                    } else if (cookTime > 0) {
                        this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
                    }
                }
            }
        }

    }

    private EvaporationRecipe getEvaporationRecipe(BlockPos pos) {
        if (this.level != null) {
            for (EvaporationRecipe recipe : this.level.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.EVAPORATION)) {
                if (!recipe.getEvaporationResult(this.level,level.getBiome(pos).getRegistryName()).isEmpty() && recipe.fluidMatch(this.level.getFluidState(pos).getType())){
                    return recipe;
                }
            }
        }
        return null;
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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
    public void setRemoved() {
        super.setRemoved();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

    private boolean boilerStructure(BlockPos pos, World worldIn) {
        if (!worldIn.isClientSide) {
            for (BlockPos p : Arrays.asList(pos.north(), pos.east(), pos.south(), pos.west(), pos.above().north(), pos.above().east(), pos.above().west(), pos.above().south())) {
                if (!worldIn.getBlockState(p).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private int checkStructure(BlockPos pos, World worldIn, Block fluid) {
        int height = 0;
        if (!worldIn.isClientSide) {
            List<BlockPos> Base = Arrays.asList(
                    pos.offset(2, 0, -1),
                    pos.offset(2, 0, 0),
                    pos.offset(2, 0, 1),
                    pos.offset(-2, 0, -1),
                    pos.offset(-2, 0, 0),
                    pos.offset(-2, 0, 1),
                    pos.offset(-1, 0, 2),
                    pos.offset(0, 0, 2),
                    pos.offset(1, 0, 2),
                    pos.offset(-1, 0, 2),
                    pos.offset(0, 0, 2),
                    pos.offset(1, 0, 2),
                    pos.offset(1, 0, 0),
                    pos.offset(0, 0, 1),
                    pos.offset(-1, 0, 0),
                    pos.offset(0, 0, -1),
                    pos.offset(1, 0, 1),
                    pos.offset(1, 0, -1),
                    pos.offset(-1, 0, 1),
                    pos.offset(-1, 0, -1));
            for (BlockPos b : Base) {
                if (worldIn.getBlockState(b) != Blocks.MAGMA_BLOCK.defaultBlockState()) {
                    return 0;
                }
            }
            for (int i = 1; i <= Config.MACHINES.EVAPORATION_TOWER_RANGE.get(); ++i) {
                if (worldIn.getBlockState(pos.offset(3, i, -1)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(3, i, 0)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(3, i, 1)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(-3, i, -1)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(-3, i, 0)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(-3, i, 1)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(-1, i, 3)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(0, i, 3)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(1, i, 3)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(-1, i, 3)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(0, i, 3)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(2, i, 2)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(-2, i, -2)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(2, i, -2)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(-2, i, 2)).getBlock().getTags().contains(new ResourceLocation("forge:sheetmetal"))
                        && worldIn.getBlockState(pos.offset(2, i, -1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(2, i, 0)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(2, i, 1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-2, i, -1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-2, i, 0)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-2, i, 1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-1, i, 2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(0, i, 2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(1, i, 2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-1, i, -2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(0, i, -2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(1, i, -2)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(1, i, 0)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-1, i, 0)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-1, i, -1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(1, i, 1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(-1, i, 1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(1, i, -1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(0, i, 1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(0, i, -1)) == Blocks.BUBBLE_COLUMN.defaultBlockState()
                        && worldIn.getBlockState(pos.offset(0, i, 0)).getBlock() == fluid) {
                    height = i;
                }
            }
        }
        return height;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Override
    @Nullable
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new EvaporationTowerContainer(i, level, worldPosition, playerInventory, playerEntity, this, this.towerData);
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
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStackIn);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public int getContainerSize() {
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
    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ItemStackHelper.removeItem(this.items, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ItemStackHelper.takeItem(this.items, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (index == 0 && !flag) {
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return false;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }


}
