package com.cannolicatfish.rankine.blocks.tap;

import com.cannolicatfish.rankine.blocks.FloodGateBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.recipe.TreetappingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static com.cannolicatfish.rankine.init.RankineBlocks.TREE_TAP_TILE;

public class TreeTapTile extends BlockEntity {
    FluidTank outputTank = new FluidTank(1000);

    public TreeTapTile(BlockPos posIn, BlockState stateIn) {
        super(TREE_TAP_TILE, posIn, stateIn);
    }

    public void tick() {
        if (level.getDayTime() % 24000 >2000 && level.getDayTime() % 24000 <10000 && isTreeAlive(worldPosition,level) && !level.isClientSide()) {
            BlockPos logPos = worldPosition.relative(this.getBlockState().getValue(TreeTapBlock.FACING).getOpposite());
            for (BlockPos s : BlockPos.betweenClosed(logPos.offset(-1,-2,-1),logPos.offset(1,2,1))) {
                BlockPos stupidPos = s.immutable();
                if (!stupidPos.equals(worldPosition) && level.getBlockState(stupidPos).is(RankineBlocks.TREE_TAP.get())) {
                    return;
                }
            }
            Block log = level.getBlockState(logPos).getBlock();
            TreetappingRecipe irecipe = this.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.TREETAPPING, new SimpleContainer(new ItemStack(log)), this.level).orElse(null);
            if (irecipe != null && level.getDayTime()%irecipe.getTapTime() == 0) {
                outputTank.fill(irecipe.getResult(), IFluidHandler.FluidAction.EXECUTE);
            }

            if (outputTank.getFluidAmount() == 1000 && level.getBlockState(worldPosition.below()).getBlock().equals(RankineBlocks.TAP_LINE.get())) {
                BlockPos floodGate = null;
                Set<BlockPos> checkedBlocks = new HashSet<>();
                Stack<BlockPos> toCheck = new Stack<>();
                toCheck.add(worldPosition.below());
                while (!toCheck.isEmpty()) {
                    BlockPos cp = toCheck.pop();
                    if (!checkedBlocks.contains(cp)) {
                        checkedBlocks.add(cp);
                        if (level.hasChunkAt(cp)) {
                            BlockState s = level.getBlockState(cp);
                            if (s.getBlock().equals(RankineBlocks.FLOOD_GATE.get())) {
                                floodGate = cp;
                                break;
                            } else if (s.getBlock().equals(RankineBlocks.TAP_LINE.get())) {
                                toCheck.add(cp.north());
                                toCheck.add(cp.east());
                                toCheck.add(cp.south());
                                toCheck.add(cp.west());
                                toCheck.add(cp.below());
                            }
                        }
                        if (checkedBlocks.size() > 200) {
                            break;
                        }
                    }
                }

                if (floodGate != null && FloodGateBlock.placeFluid(level, floodGate, outputTank.getFluid().getFluid().defaultFluidState().createLegacyBlock())) {
                    outputTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                }
            }

        }
    }




    public FluidTank getOutputTank() {
        return outputTank;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.outputTank = this.outputTank.readFromNBT(nbt.getCompound("OutputTank"));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.put("OutputTank",this.outputTank.writeToNBT(new CompoundTag()));

        return compound;
    }

    private boolean isTreeAlive(BlockPos pos, Level worldIn) {
        Set<BlockPos> checkedBlocks = new HashSet<>();
        Stack<BlockPos> toCheck = new Stack<>();

        toCheck.add(pos.relative(worldIn.getBlockState(pos).getValue(TreeTapBlock.FACING).getOpposite()));
        while (!toCheck.isEmpty()) {
            BlockPos cp = toCheck.pop();
            if (!checkedBlocks.contains(cp)) {
                checkedBlocks.add(cp);
                for (BlockPos b : BlockPos.betweenClosed(cp.offset(-1,-1,-1), cp.offset(1,1,1))) {
                    BlockState target = worldIn.getBlockState(b.immutable());
                    if (target.is(RankineTags.Blocks.TREE_LEAVES)) {
                        return true;
                    } else if (target.is(RankineTags.Blocks.TREE_LOGS)) {
                        toCheck.add(b.immutable());
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
