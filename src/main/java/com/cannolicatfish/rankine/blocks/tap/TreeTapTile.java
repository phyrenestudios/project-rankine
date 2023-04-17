package com.cannolicatfish.rankine.blocks.tap;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.recipe.TreetappingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static com.cannolicatfish.rankine.init.RankineBlocks.TREE_TAP_TILE;

public class TreeTapTile extends BlockEntity {
    FluidTank outputTank = new FluidTank(1000);
    private int proccessTime;

    public TreeTapTile(BlockPos posIn, BlockState stateIn) {
        super(TREE_TAP_TILE, posIn, stateIn);
    }

    public static void tick(Level level, BlockPos pos, BlockState bs, TreeTapTile tile) {
        if (level.getDayTime() % 24000 >2000 && level.getDayTime() % 24000 <10000 && tile.isTreeAlive(tile.worldPosition,level) && !level.isClientSide()) {
            BlockPos logPos = tile.worldPosition.relative(tile.getBlockState().getValue(TreeTapBlock.FACING).getOpposite());
            for (BlockPos s : BlockPos.betweenClosed(logPos.offset(-1,-2,-1),logPos.offset(1,2,1))) {
                BlockPos stupidPos = s.immutable();
                if (!stupidPos.equals(tile.worldPosition) && level.getBlockState(stupidPos).is(RankineBlocks.TREE_TAP.get())) {
                    return;
                }
            }
            tile.proccessTime += 1;
            Block log = level.getBlockState(logPos).getBlock();
            TreetappingRecipe irecipe = tile.level.getRecipeManager().getRecipeFor(RankineRecipeTypes.TREETAPPING, new SimpleContainer(new ItemStack(log)), level).orElse(null);
            if (irecipe != null && tile.proccessTime > irecipe.getTapTime()) {
                tile.outputTank.fill(irecipe.getResult(), IFluidHandler.FluidAction.EXECUTE);
                tile.proccessTime = 0;
            }

            if (tile.outputTank.getFluidAmount() == 1000) {
                BlockPos cauldron = null;
                if (level.getBlockState(tile.worldPosition.below()).is(Blocks.CAULDRON)) {
                    cauldron = tile.worldPosition.below();
                } else if (level.getBlockState(tile.worldPosition.below()).is(RankineBlocks.TAP_LINE.get())) {
                    Set<BlockPos> checkedBlocks = new HashSet<>();
                    Stack<BlockPos> toCheck = new Stack<>();
                    toCheck.add(tile.worldPosition.below());
                    while (!toCheck.isEmpty()) {
                        BlockPos cp = toCheck.pop();
                        if (!checkedBlocks.contains(cp)) {
                            checkedBlocks.add(cp);
                            if (level.hasChunkAt(cp)) {
                                BlockState s = level.getBlockState(cp);
                                if (s.is(Blocks.CAULDRON)) {
                                    cauldron = cp;
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

                }
                if (cauldron != null && ForgeRegistries.FLUIDS.getHolder(tile.outputTank.getFluid().getFluid()).flatMap(Holder::unwrapKey).isPresent()) {
                    level.setBlockAndUpdate(cauldron, getCauldron(level, cauldron, ForgeRegistries.FLUIDS.getHolder(tile.outputTank.getFluid().getFluid()).flatMap(Holder::unwrapKey).get().location().getPath()));
                    tile.outputTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                }
            }

        }
    }

    private static BlockState getCauldron(Level levelIn, BlockPos posIn, String fluidIn) {
        return switch (fluidIn) {
            case "lava" -> Blocks.LAVA_CAULDRON.defaultBlockState();
            case "sap" -> RankineBlocks.SAP_CAULDRON.get().defaultBlockState();
            case "maple_sap" -> RankineBlocks.MAPLE_SAP_CAULDRON.get().defaultBlockState();
            case "latex" -> RankineBlocks.LATEX_CAULDRON.get().defaultBlockState();
            case "resin" -> RankineBlocks.RESIN_CAULDRON.get().defaultBlockState();
            case "juglone" -> RankineBlocks.JUGLONE_CAULDRON.get().defaultBlockState();
            case "water" -> Blocks.WATER_CAULDRON.defaultBlockState();
            default -> Blocks.CAULDRON.defaultBlockState();
        };
    }



    public FluidTank getOutputTank() {
        return outputTank;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.outputTank = this.outputTank.readFromNBT(nbt.getCompound("OutputTank"));
        this.proccessTime = nbt.getInt("ProcessTime");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("OutputTank",this.outputTank.writeToNBT(new CompoundTag()));
        compound.putInt("ProcessTime", this.proccessTime);
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
