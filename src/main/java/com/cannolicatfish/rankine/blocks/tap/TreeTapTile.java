package com.cannolicatfish.rankine.blocks.tap;

import com.cannolicatfish.rankine.blocks.FloodGateBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.RankineTileEntities;
import com.cannolicatfish.rankine.recipe.TreetappingRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class TreeTapTile extends TileEntity implements ITickableTileEntity {
    FluidTank outputTank = new FluidTank(1000);

    public TreeTapTile() {
        super(RankineTileEntities.TREE_TAP.get());
    }

    public void tick() {
        if (world.getDayTime() % 24000 >2000 && world.getDayTime() % 24000 <10000 && isTreeAlive(pos,world) && !world.isRemote()) {
            BlockPos logPos = pos.offset(this.getBlockState().get(TreeTapBlock.FACING).getOpposite());
            for (BlockPos s : BlockPos.getAllInBoxMutable(logPos.add(-1,-2,-1),logPos.add(1,2,1))) {
                BlockPos stupidPos = s.toImmutable();
                if (!stupidPos.equals(pos) && world.getBlockState(stupidPos).matchesBlock(RankineBlocks.TREE_TAP.get())) {
                    return;
                }
            }
            Block log = world.getBlockState(logPos).getBlock();
            TreetappingRecipe irecipe = this.world.getRecipeManager().getRecipe(RankineRecipeTypes.TREETAPPING, new Inventory(new ItemStack(log)), this.world).orElse(null);
            if (irecipe != null && world.getDayTime()%irecipe.getTapTime() == 0) {
                outputTank.fill(irecipe.getResult(), IFluidHandler.FluidAction.EXECUTE);
            }

            if (outputTank.getFluidAmount() == 1000 && world.getBlockState(pos.down()).getBlock().matchesBlock(RankineBlocks.TAP_LINE.get())) {
                BlockPos floodGate = null;
                Set<BlockPos> checkedBlocks = new HashSet<>();
                Stack<BlockPos> toCheck = new Stack<>();
                toCheck.add(pos.down());
                while (!toCheck.isEmpty()) {
                    BlockPos cp = toCheck.pop();
                    if (!checkedBlocks.contains(cp)) {
                        checkedBlocks.add(cp);
                        if (world.isBlockLoaded(cp)) {
                            BlockState s = world.getBlockState(cp);
                            if (s.getBlock().matchesBlock(RankineBlocks.FLOOD_GATE.get())) {
                                floodGate = cp;
                                break;
                            } else if (s.getBlock().matchesBlock(RankineBlocks.TAP_LINE.get())) {
                                toCheck.add(cp.north());
                                toCheck.add(cp.east());
                                toCheck.add(cp.south());
                                toCheck.add(cp.west());
                                toCheck.add(cp.down());
                            }
                        }
                        if (checkedBlocks.size() > 200) {
                            break;
                        }
                    }
                }

                if (floodGate != null && FloodGateBlock.placeFluid(world, floodGate, outputTank.getFluid().getFluid().getDefaultState().getBlockState())) {
                    outputTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                }
            }

        }
    }




    public FluidTank getOutputTank() {
        return outputTank;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.outputTank = this.outputTank.readFromNBT(nbt.getCompound("OutputTank"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("OutputTank",this.outputTank.writeToNBT(new CompoundNBT()));

        return compound;
    }

    private boolean isTreeAlive(BlockPos pos, World worldIn) {
        Set<BlockPos> checkedBlocks = new HashSet<>();
        Stack<BlockPos> toCheck = new Stack<>();

        toCheck.add(pos.offset(worldIn.getBlockState(pos).get(TreeTapBlock.FACING).getOpposite()));
        while (!toCheck.isEmpty()) {
            BlockPos cp = toCheck.pop();
            if (!checkedBlocks.contains(cp)) {
                checkedBlocks.add(cp);
                for (BlockPos b : BlockPos.getAllInBoxMutable(cp.add(-1,-1,-1), cp.add(1,1,1))) {
                    BlockState target = worldIn.getBlockState(b.toImmutable());
                    if (target.isIn(RankineTags.Blocks.TREE_LEAVES)) {
                        return true;
                    } else if (target.isIn(RankineTags.Blocks.TREE_LOGS)) {
                        toCheck.add(b.toImmutable());
                    }
                }
                if (toCheck.size() > 300) {
                    break;
                }
            }
        }
        return false;
    }
}
