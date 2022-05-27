package com.cannolicatfish.rankine.blocks.beehiveoven;

import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.BeehiveOvenRecipe;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.BEEHIVE_OVEN_TILE;

public class BeehiveOvenTile extends BlockEntity {
    private int proccessTime;
    private int nextRecipe;
    public BeehiveOvenTile(BlockPos posIn, BlockState stateIn) {
        super(BEEHIVE_OVEN_TILE, posIn, stateIn);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.proccessTime = nbt.getInt("ProcessTime");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("ProcessTime", this.proccessTime);
    }

    public static void tick(Level level, BlockPos pos, BlockState bs, BeehiveOvenTile tile) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (tile.nextRecipe == 0) {
            tile.nextRecipe = level.getRandom().nextInt(tile.structureCheck(level, pos)) + 100;
        }
        if (canSeeSky(level, pos) ) {
            if (bs.getValue(BeehiveOvenPitBlock.LIT)) {
                tile.proccessTime += 1;
                if (tile.proccessTime >= tile.nextRecipe) {
                    boolean flag = true;
                    for (BlockPos p: BlockPos.betweenClosed(pos.offset(-1,1,-1),pos.offset(1,2,1))) {
                        BeehiveOvenRecipe recipe = level.getRecipeManager().getRecipeFor(RankineRecipeTypes.BEEHIVE, new SimpleContainer(new ItemStack(level.getBlockState(p).getBlock())), level).orElse(null);
                        if (recipe != null) {
                            ItemStack output = recipe.getResultItem();
                            if (!output.isEmpty()) {
                                if (output.getItem() instanceof BlockItem) {
                                    level.setBlock(p, ((BlockItem) output.getItem()).getBlock().defaultBlockState(), 2);
                                    tile.proccessTime = 0;
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (flag) level.setBlock(pos, bs.setValue(BlockStateProperties.LIT, Boolean.FALSE), 3);
                    tile.nextRecipe = level.getRandom().nextInt(tile.structureCheck(level, pos)) + 100;
                }
            }
        } else {
            level.setBlock(pos, bs.setValue(BlockStateProperties.LIT, Boolean.FALSE), 3);
        }
    }

    private static boolean canSeeSky(Level worldIn, BlockPos pos) {
        if (Config.MACHINES.BEEHIVE_OVEN_SKYLIGHT.get() != 0) {
            for (int i = 1; i <= Config.MACHINES.BEEHIVE_OVEN_SKYLIGHT.get(); i++) {
                if (!worldIn.isEmptyBlock(pos.above(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    private int structureCheck(Level world, BlockPos pos) {
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
