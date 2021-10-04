package com.cannolicatfish.rankine.blocks.beehiveoven;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.BeehiveOvenRecipe;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.BEEHIVE_OVEN_TILE;

public class BeehiveOvenTile extends TileEntity implements ITickableTileEntity {
    private int ticks;
    private int nextRecipe = world.getRandom().nextInt(structureCheck(world, pos)) + 100;
    public BeehiveOvenTile() {
        super(BEEHIVE_OVEN_TILE);
    }

    public void tick() {
        if (!world.isAreaLoaded(pos, 1)) return;
        if (canSeeSky(world, pos) ) {
            if (this.getBlockState().get(BeehiveOvenPitBlock.LIT)) {
                ticks += 1;
                if (ticks >= nextRecipe) {
                    for (BlockPos p: BlockPos.getAllInBoxMutable(pos.add(-1,1,-1),pos.add(1,2,1))) {
                        BeehiveOvenRecipe recipe = world.getRecipeManager().getRecipe(RankineRecipeTypes.BEEHIVE, new Inventory(new ItemStack(world.getBlockState(p).getBlock())), world).orElse(null);
                        if (recipe != null) {
                            ItemStack output = recipe.getRecipeOutput();
                            if (!output.isEmpty()) {
                                if (output.getItem() instanceof BlockItem) {
                                    world.setBlockState(p, ((BlockItem) output.getItem()).getBlock().getDefaultState(), 2);
                                    ticks = 0;
                                    break;
                                }
                            }
                        }
                    }
                    nextRecipe = world.getRandom().nextInt(structureCheck(world, pos)) + 100;
                }
            }
        } else {
            world.setBlockState(pos, world.getBlockState(pos).with(BlockStateProperties.LIT, Boolean.FALSE), 3);
        }
    }

    private static boolean canSeeSky(World worldIn, BlockPos pos) {
        if (Config.MACHINES.BEEHIVE_OVEN_SKYLIGHT.get() != 0) {
            for (int i = 1; i <= Config.MACHINES.BEEHIVE_OVEN_SKYLIGHT.get(); i++) {
                if (!worldIn.isAirBlock(pos.up(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    private int structureCheck(World world, BlockPos pos) {
        List<BlockPos> oven = Arrays.asList(
                pos.add(-1,0,-1),
                pos.add(-1,0,0),
                pos.add(-1,0,1),
                pos.add(1,0,-1),
                pos.add(1,0,0),
                pos.add(1,0,1),
                pos.add(0,0,-1),
                pos.add(0,0,1),

                pos.add(-2,0,-1),
                pos.add(-2,0,0),
                pos.add(-2,0,1),
                pos.add(2,0,-1),
                pos.add(2,0,0),
                pos.add(2,0,1),
                pos.add(-1,0,-2),
                pos.add(0,0,-2),
                pos.add(1,0,-2),
                pos.add(-1,0,2),
                pos.add(0,0,2),
                pos.add(1,0,2),

                pos.add(-2,1,-1),
                pos.add(-2,1,1),
                pos.add(2,1,-1),
                pos.add(2,1,1),
                pos.add(-1,1,-2),
                pos.add(1,1,-2),
                pos.add(-1,1,2),
                pos.add(1,1,2),

                pos.add(-2,2,-1),
                pos.add(-2,2,1),
                pos.add(2,2,-1),
                pos.add(2,2,1),
                pos.add(-1,2,-2),
                pos.add(1,2,-2),
                pos.add(-1,2,2),
                pos.add(1,2,2),

                pos.add(-1,3,-1),
                pos.add(-2,3,0),
                pos.add(-1,3,1),
                pos.add(1,3,-1),
                pos.add(2,3,0),
                pos.add(1,3,1),
                pos.add(0,3,-2),
                pos.add(0,3,2),
                pos.add(0,3,-1),
                pos.add(0,3,1),
                pos.add(-1,3,0),
                pos.add(1,3,0)
        );

        int count = 8000;
        for (BlockPos b : oven) {
            if (world.getBlockState(b) == RankineBlocks.REFRACTORY_BRICKS.get().getDefaultState()) {
                count -= 70;
            } else if (world.getBlockState(b) != RankineBlocks.REFRACTORY_BRICKS.get().getDefaultState() && world.getBlockState(b) == RankineBlocks.HIGH_REFRACTORY_BRICKS.get().getDefaultState()) {
                count -= 110;
            } else if (world.getBlockState(b) == RankineBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.get().getDefaultState() && world.getBlockState(b) == RankineBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.get().getDefaultState()) {
                count -= 160;
            }
        }
        return count;
    }

}
