package com.cannolicatfish.rankine.blocks.distillationtower;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTags;
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

import static com.cannolicatfish.rankine.init.RankineBlocks.*;

public class DistillationTowerTile extends TileEntity implements ITickableTileEntity {
    private int proccessTime;
    public DistillationTowerTile() {
        super(DISTILLATION_TOWER_TILE);
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
        int LAYERS = structureCheck(level,worldPosition);
        if (!level.isClientSide && LAYERS > 0) {
            ++proccessTime;
            if (proccessTime >= Config.MACHINES.AIR_DISTILLATION_SPEED.get()) {
                System.out.println(level.dimension().location());
                AirDistillationRecipe recipe =  getRecipe(level,level.getBiome(worldPosition).getRegistryName(),level.dimension().location());
                if (recipe != null) {
                    for (int i = 4; i < LAYERS * 3 + 2; i+=3) {
                        if (level.getBlockState(worldPosition.above(i)).is(Blocks.AIR)) {
                            ItemStack result = recipe.getDistillationWithChances(level, Math.floorDiv(i,3), level.getBiome(worldPosition).getRegistryName(), level.dimension().location());
                            if (result.getItem() instanceof BlockItem) {
                                level.setBlockAndUpdate(worldPosition.above(i), ((BlockItem) result.getItem()).getBlock().defaultBlockState());
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
        for (AirDistillationRecipe recipe : worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.AIR_DISTILLATION)) {
            if (recipe.matchesDistillationRecipe(biome, dim)){
                return recipe;
            }
        }
        return null;
    }

    private int structureCheck(World worldIn, BlockPos pos) {
        if (getRing(worldIn,pos, BlockTags.ICE) &&
            worldIn.getBlockState(pos.offset(2,0,-1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(2,0,0)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(2,0,1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-2,0,-1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-2,0,0)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-2,0,1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-1,0,2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(0,0,2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(1,0,2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-1,0,-2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(0,0,-2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(1,0,-2)).is(RankineTags.Blocks.SHEETMETAL) &&

            getRing(worldIn,pos.above(),BlockTags.ICE) &&
            worldIn.getBlockState(pos.offset(2,1,-1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(2,1,0)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(2,1,1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-2,1,-1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-2,1,0)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-2,1,1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-1,1,2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(0,1,2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(1,1,2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-1,1,-2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(0,1,-2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(1,1,-2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(0,1,0)).is(RankineBlocks.AIR_DISTILLATION_PACKING.get()) &&

            getRing(worldIn,pos.above(2),BlockTags.ICE) &&
            worldIn.getBlockState(pos.offset(2,2,-1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(2,2,0)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(2,2,1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-2,2,-1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-2,2,0)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-2,2,1)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-1,2,2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(0,2,2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(1,2,2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(-1,2,-2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(0,2,-2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(1,2,-2)).is(RankineTags.Blocks.SHEETMETAL) &&
            worldIn.getBlockState(pos.offset(0,2,0)).is(RankineBlocks.AIR_DISTILLATION_PACKING.get())
        ) {
            int top = 0;
            for (int i = 1; i<256; i++) {
                if (worldIn.getBlockState(pos.above(i)).is(RankineTags.Blocks.SHEETMETAL)) {
                    top = i;
                    break;
                }
            }
            if (top >= 5) {
                int layers = 0;
                for (int i = 1; i<=top; i++) {
                    if (worldIn.getBlockState(pos.offset(0,i*3,0)).is(AIR_DISTILLATION_PACKING.get()) &&
                        getRing(worldIn,pos.above(i*3),RankineTags.Blocks.SHEETMETAL) &&
                        worldIn.getBlockState(pos.offset(0,i*3+1,0)).is(Blocks.AIR) &&
                        worldIn.getBlockState(pos.offset(1,i*3+1,1)).is(RankineTags.Blocks.SHEETMETAL) &&
                        worldIn.getBlockState(pos.offset(-1,i*3+1,1)).is(RankineTags.Blocks.SHEETMETAL) &&
                        worldIn.getBlockState(pos.offset(1,i*3+1,-1)).is(RankineTags.Blocks.SHEETMETAL) &&
                        worldIn.getBlockState(pos.offset(-1,i*3+1,-1)).is(RankineTags.Blocks.SHEETMETAL) &&
                        (worldIn.getBlockState(pos.offset(0,i*3+1,-1)).is(RankineTags.Blocks.SHEETMETAL) || worldIn.getBlockState(pos.offset(0,i*3+1,-1)).is(GAS_VENT.get())) &&
                        (worldIn.getBlockState(pos.offset(0,i*3+1,1)).is(RankineTags.Blocks.SHEETMETAL) || worldIn.getBlockState(pos.offset(0,i*3+1,1)).is(GAS_VENT.get())) &&
                        (worldIn.getBlockState(pos.offset(1,i*3+1,0)).is(RankineTags.Blocks.SHEETMETAL) || worldIn.getBlockState(pos.offset(1,i*3+1,0)).is(GAS_VENT.get())) &&
                        (worldIn.getBlockState(pos.offset(-1,i*3+1,0)).is(RankineTags.Blocks.SHEETMETAL) || worldIn.getBlockState(pos.offset(-1,i*3+1,0)).is(GAS_VENT.get())) &&
                        (worldIn.getBlockState(pos.offset(0, i*3 + 2, 0)).is(AIR_DISTILLATION_PACKING.get()) || worldIn.getBlockState(pos.offset(0, i*3 + 2, 0)).is(RankineTags.Blocks.SHEETMETAL)) &&
                        getRing(worldIn, pos.above(i*3 + 2), RankineTags.Blocks.SHEETMETAL)) {
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
        return worldIn.getBlockState(pos.offset(-1, 0, -1)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(0, 0, -1)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(1, 0, -1)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(-1, 0, 0)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(1, 0, 0)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(-1, 0, 1)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(0, 0, 1)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(1, 0, 1)).is(matchBlock);
    }

    private boolean getRing(World worldIn, BlockPos pos, ITag<Block> matchBlock) {
        return worldIn.getBlockState(pos.offset(-1, 0, -1)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(0, 0, -1)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(1, 0, -1)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(-1, 0, 0)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(1, 0, 0)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(-1, 0, 1)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(0, 0, 1)).is(matchBlock) &&
                worldIn.getBlockState(pos.offset(1, 0, 1)).is(matchBlock);
    }

}


