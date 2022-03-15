package com.cannolicatfish.rankine.blocks.beehiveoven;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.BeehiveOvenRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.BEEHIVE_OVEN_TILE;

public class BeehiveOvenTile extends TileEntity implements ITickableTileEntity {
    private int proccessTime;
    private int nextRecipe;
    public BeehiveOvenTile() {
        super(BEEHIVE_OVEN_TILE);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.proccessTime = nbt.getInt("ProcessTime");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        compound.putInt("ProcessTime", this.proccessTime);
        return compound;
    }

    public void tick() {
        if (!level.isAreaLoaded(worldPosition, 1)) return;
        if (nextRecipe == 0) {
            nextRecipe = level.getRandom().nextInt(structureCheck(level, worldPosition)) + 100;
        }
        if (canSeeSky(level, worldPosition) ) {
            if (this.getBlockState().getValue(BeehiveOvenPitBlock.LIT)) {
                proccessTime += 1;
                if (proccessTime >= nextRecipe) {
                    boolean flag = true;
                    for (BlockPos p: BlockPos.betweenClosed(worldPosition.offset(-1,1,-1),worldPosition.offset(1,2,1))) {
                        BeehiveOvenRecipe recipe = level.getRecipeManager().getRecipeFor(RankineRecipeTypes.BEEHIVE, new Inventory(new ItemStack(level.getBlockState(p).getBlock())), level).orElse(null);
                        if (recipe != null) {
                            ItemStack output = recipe.getResultItem();
                            if (!output.isEmpty()) {
                                if (output.getItem() instanceof BlockItem) {
                                    level.setBlock(p, ((BlockItem) output.getItem()).getBlock().defaultBlockState(), 2);
                                    proccessTime = 0;
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (flag) level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(BlockStateProperties.LIT, Boolean.FALSE), 3);
                    nextRecipe = level.getRandom().nextInt(structureCheck(level, worldPosition)) + 100;
                }
            }
        } else {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(BlockStateProperties.LIT, Boolean.FALSE), 3);
        }
    }

    private static boolean canSeeSky(World worldIn, BlockPos pos) {
        if (Config.MACHINES.BEEHIVE_OVEN_SKYLIGHT.get() != 0) {
            for (int i = 1; i <= Config.MACHINES.BEEHIVE_OVEN_SKYLIGHT.get(); i++) {
                if (!worldIn.isEmptyBlock(pos.above(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    private int structureCheck(World world, BlockPos pos) {
        List<BlockPos> oven = Arrays.asList(
                pos.offset(-1,0,-1),
                pos.offset(-1,0,0),
                pos.offset(-1,0,1),
                pos.offset(1,0,-1),
                pos.offset(1,0,0),
                pos.offset(1,0,1),
                pos.offset(0,0,-1),
                pos.offset(0,0,1),

                pos.offset(-2,0,-1),
                pos.offset(-2,0,0),
                pos.offset(-2,0,1),
                pos.offset(2,0,-1),
                pos.offset(2,0,0),
                pos.offset(2,0,1),
                pos.offset(-1,0,-2),
                pos.offset(0,0,-2),
                pos.offset(1,0,-2),
                pos.offset(-1,0,2),
                pos.offset(0,0,2),
                pos.offset(1,0,2),

                pos.offset(-2,1,-1),
                pos.offset(-2,1,1),
                pos.offset(2,1,-1),
                pos.offset(2,1,1),
                pos.offset(-1,1,-2),
                pos.offset(1,1,-2),
                pos.offset(-1,1,2),
                pos.offset(1,1,2),

                pos.offset(-2,2,-1),
                pos.offset(-2,2,1),
                pos.offset(2,2,-1),
                pos.offset(2,2,1),
                pos.offset(-1,2,-2),
                pos.offset(1,2,-2),
                pos.offset(-1,2,2),
                pos.offset(1,2,2),

                pos.offset(-1,3,-1),
                pos.offset(-2,3,0),
                pos.offset(-1,3,1),
                pos.offset(1,3,-1),
                pos.offset(2,3,0),
                pos.offset(1,3,1),
                pos.offset(0,3,-2),
                pos.offset(0,3,2),
                pos.offset(0,3,-1),
                pos.offset(0,3,1),
                pos.offset(-1,3,0),
                pos.offset(1,3,0)
        );

        int count = 8000;
        for (BlockPos b : oven) {
            if (world.getBlockState(b) == RankineBlocks.REFRACTORY_BRICKS.get().defaultBlockState()) {
                count -= 70;
            } else if (world.getBlockState(b) != RankineBlocks.REFRACTORY_BRICKS.get().defaultBlockState() && world.getBlockState(b) == RankineBlocks.HIGH_REFRACTORY_BRICKS.get().defaultBlockState()) {
                count -= 110;
            } else if (world.getBlockState(b) == RankineBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.get().defaultBlockState() && world.getBlockState(b) == RankineBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.get().defaultBlockState()) {
                count -= 160;
            }
        }
        return count;
    }

}
