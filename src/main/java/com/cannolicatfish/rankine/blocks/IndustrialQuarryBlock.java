package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import com.google.gson.internal.$Gson$Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class IndustrialQuarryBlock extends Block {
    public IndustrialQuarryBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        World world = worldIn;

        if(player.getHeldItemMainhand().getItem() == Items.NETHER_STAR) {
            if (isValidStructure(world, pos)) {
                for (int x = -7; x <= 7; ++x) {
                    for (int z = -7; z <= 7; ++z) {
                        for (int y = -3; y >= -pos.getY(); --y) {
                            Block TARGET = world.getBlockState(pos.add(x,y,z)).getBlock();
                            if(TARGET == ModBlocks.QUARRY_BARRIER) {
                                break;
                            } else if (!world.getBlockState(pos.add(x,y,z)).getBlock().getTags().contains(new ResourceLocation("rankine:nonquarryable"))) {
                                world.setBlockState(pos.add(x,y,z), Blocks.AIR.getDefaultState());
                            }
                        }
                    }
                }

             /*
                for (BlockPos target : BlockPos.getAllInBoxMutable(pos.add(-7, -3, -7), pos.add(7, -pos.getY(), 7))) {
                    if (!world.getBlockState(target).getBlock().getTags().contains(new ResourceLocation("rankine:nonquarryable"))) {
                        world.setBlockState(target, Blocks.AIR.getDefaultState());
                    }
                }

              */
                ItemStack itemstack = player.getHeldItem(handIn);
                itemstack.shrink(1);
                world.playSound(player, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 0.6F, 1.0F);
                return ActionResultType.SUCCESS;

            } else {
                player.sendStatusMessage(new TranslationTextComponent("Invalid Structure"), true);
            }

        }

        ItemStack itemstack = player.getHeldItem(handIn);
        return itemstack.getItem() instanceof BlockItem && (new BlockItemUseContext(player, handIn, itemstack, hit)).canPlace() ? ActionResultType.PASS : ActionResultType.FAIL;
    }




    private static boolean isValidStructure(World world, BlockPos pos) {
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, 1), pos.add(1, 0, 7))) {
            if (!world.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 0, -7))) {
            if (!world.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(1, 0, -1), pos.add(7, 0, 1))) {
            if (!world.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(-7, 0, 1))) {
            if (!world.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, -6, -8), pos.add(1, 0, -8))) {
            if (!world.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-8, -6, -1), pos.add(-8, 0, 1))) {
            if (!world.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, -6, 8), pos.add(1, 0, 8))) {
            if (!world.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(8, -6, -1), pos.add(8, 0, 1))) {
            if (!world.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                return false;
            }
        }
        return true;
    }


}
