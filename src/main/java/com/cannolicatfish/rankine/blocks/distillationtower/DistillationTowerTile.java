package com.cannolicatfish.rankine.blocks.distillationtower;

import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.recipe.AirDistillationRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.cannolicatfish.rankine.init.RankineBlocks.AIR_DISTILLATION_PACKING;
import static com.cannolicatfish.rankine.init.RankineBlocks.GAS_VENT;

public class DistillationTowerTile extends TileEntity implements ITickableTileEntity {
    private int proccessTime;
    public DistillationTowerTile() {
        super(RankineTileEntities.DISTILLATION_TOWER.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.proccessTime = nbt.getInt("ProcessTime");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("ProcessTime", this.proccessTime);
        return compound;
    }

    public void tick() {
        if (!world.isAreaLoaded(pos, 1)) return;
        int LAYERS = structureCheck(world,pos);
        if (!world.isRemote && LAYERS > 0) {
            ++proccessTime;
            if (proccessTime >= Config.MACHINES.AIR_DISTILLATION_SPEED.get()) {
                System.out.println(world.getDimensionKey().getLocation());
                AirDistillationRecipe recipe =  getRecipe(world,world.getBiome(pos).getRegistryName(),world.getDimensionKey().getLocation());
                if (recipe != null) {
                    for (int i = 4; i < LAYERS * 3 + 2; i+=3) {
                        if (world.getBlockState(pos.up(i)).matchesBlock(Blocks.AIR)) {
                            ItemStack result = recipe.getDistillationWithChances(world, Math.floorDiv(i,3), world.getBiome(pos).getRegistryName(), world.getDimensionKey().getLocation());
                            if (result.getItem() instanceof BlockItem) {
                                world.setBlockState(pos.up(i), ((BlockItem) result.getItem()).getBlock().getDefaultState());
                            }
                        }
                    }
                    System.out.println("CCCC");
                }
                proccessTime = 0;
            }
        }
    }

    private AirDistillationRecipe getRecipe(World worldIn, ResourceLocation biome, ResourceLocation dim) {
        for (AirDistillationRecipe recipe : worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.AIR_DISTILLATION)) {
            if (recipe.matchesDistillationRecipe(biome, dim)){
                return recipe;
            }
        }
        return null;
    }

    private int structureCheck(World worldIn, BlockPos pos) {
        if (getRing(worldIn,pos, BlockTags.ICE) &&
            worldIn.getBlockState(pos.add(2,0,-1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(2,0,0)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(2,0,1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-2,0,-1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-2,0,0)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-2,0,1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-1,0,2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(0,0,2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(1,0,2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-1,0,-2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(0,0,-2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(1,0,-2)).isIn(RankineTags.Blocks.SHEETMETAL) &&

            getRing(worldIn,pos.up(),BlockTags.ICE) &&
            worldIn.getBlockState(pos.add(2,1,-1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(2,1,0)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(2,1,1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-2,1,-1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-2,1,0)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-2,1,1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-1,1,2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(0,1,2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(1,1,2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-1,1,-2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(0,1,-2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(1,1,-2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(0,1,0)).matchesBlock(RankineBlocks.AIR_DISTILLATION_PACKING.get()) &&

            getRing(worldIn,pos.up(2),BlockTags.ICE) &&
            worldIn.getBlockState(pos.add(2,2,-1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(2,2,0)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(2,2,1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-2,2,-1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-2,2,0)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-2,2,1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-1,2,2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(0,2,2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(1,2,2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(-1,2,-2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(0,2,-2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(1,2,-2)).isIn(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.add(0,2,0)).matchesBlock(RankineBlocks.AIR_DISTILLATION_PACKING.get())
        ) {
            int top = 0;
            for (int i = 1; i<256; i++) {
                if (worldIn.getBlockState(pos.up(i)).isIn(RankineTags.Blocks.SHEETMETAL)) {
                    top = i;
                    break;
                }
            }
            if (top >= 5) {
                int layers = 0;
                for (int i = 1; i<=top; i++) {
                    if (worldIn.getBlockState(pos.add(0,i*3,0)).matchesBlock(AIR_DISTILLATION_PACKING.get()) &&
                        getRing(worldIn,pos.up(i*3),RankineTags.Blocks.SHEETMETAL) &&
                        worldIn.getBlockState(pos.add(0,i*3+1,0)).matchesBlock(Blocks.AIR) &&
                        worldIn.getBlockState(pos.add(1,i*3+1,1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
                        worldIn.getBlockState(pos.add(-1,i*3+1,1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
                        worldIn.getBlockState(pos.add(1,i*3+1,-1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
                        worldIn.getBlockState(pos.add(-1,i*3+1,-1)).isIn(RankineTags.Blocks.SHEETMETAL) &&
                        (worldIn.getBlockState(pos.add(0,i*3+1,-1)).isIn(RankineTags.Blocks.SHEETMETAL) || worldIn.getBlockState(pos.add(0,i*3+1,-1)).matchesBlock(GAS_VENT.get())) &&
                        (worldIn.getBlockState(pos.add(0,i*3+1,1)).isIn(RankineTags.Blocks.SHEETMETAL) || worldIn.getBlockState(pos.add(0,i*3+1,1)).matchesBlock(GAS_VENT.get())) &&
                        (worldIn.getBlockState(pos.add(1,i*3+1,0)).isIn(RankineTags.Blocks.SHEETMETAL) || worldIn.getBlockState(pos.add(1,i*3+1,0)).matchesBlock(GAS_VENT.get())) &&
                        (worldIn.getBlockState(pos.add(-1,i*3+1,0)).isIn(RankineTags.Blocks.SHEETMETAL) || worldIn.getBlockState(pos.add(-1,i*3+1,0)).matchesBlock(GAS_VENT.get())) &&
                        (worldIn.getBlockState(pos.add(0, i*3 + 2, 0)).matchesBlock(AIR_DISTILLATION_PACKING.get()) || worldIn.getBlockState(pos.add(0, i*3 + 2, 0)).isIn(RankineTags.Blocks.SHEETMETAL)) &&
                        getRing(worldIn, pos.up(i*3 + 2), RankineTags.Blocks.SHEETMETAL)) {
                            layers = i;
                    } else {
                        break;
                    }
                }
                return layers;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private boolean getRing(World worldIn, BlockPos pos, Block matchBlock) {
        return worldIn.getBlockState(pos.add(-1, 0, -1)).matchesBlock(matchBlock) &&
                worldIn.getBlockState(pos.add(0, 0, -1)).matchesBlock(matchBlock) &&
                worldIn.getBlockState(pos.add(1, 0, -1)).matchesBlock(matchBlock) &&
                worldIn.getBlockState(pos.add(-1, 0, 0)).matchesBlock(matchBlock) &&
                worldIn.getBlockState(pos.add(1, 0, 0)).matchesBlock(matchBlock) &&
                worldIn.getBlockState(pos.add(-1, 0, 1)).matchesBlock(matchBlock) &&
                worldIn.getBlockState(pos.add(0, 0, 1)).matchesBlock(matchBlock) &&
                worldIn.getBlockState(pos.add(1, 0, 1)).matchesBlock(matchBlock);
    }

    private boolean getRing(World worldIn, BlockPos pos, ITag<Block> matchBlock) {
        return worldIn.getBlockState(pos.add(-1, 0, -1)).isIn(matchBlock) &&
                worldIn.getBlockState(pos.add(0, 0, -1)).isIn(matchBlock) &&
                worldIn.getBlockState(pos.add(1, 0, -1)).isIn(matchBlock) &&
                worldIn.getBlockState(pos.add(-1, 0, 0)).isIn(matchBlock) &&
                worldIn.getBlockState(pos.add(1, 0, 0)).isIn(matchBlock) &&
                worldIn.getBlockState(pos.add(-1, 0, 1)).isIn(matchBlock) &&
                worldIn.getBlockState(pos.add(0, 0, 1)).isIn(matchBlock) &&
                worldIn.getBlockState(pos.add(1, 0, 1)).isIn(matchBlock);
    }

}


