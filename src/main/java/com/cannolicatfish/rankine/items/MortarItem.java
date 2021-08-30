package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.blocks.RankineVerticalSlabBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class MortarItem extends Item {
    public MortarItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        ResourceLocation rs = block.getRegistryName();
            if (rs != null) {
                ResourceLocation rs2 = new ResourceLocation(rs.getNamespace(), rs.getPath()+"_bricks");
                if (ForgeRegistries.BLOCKS.containsKey(rs2) && !worldIn.isRemote()) {
                    worldIn.setBlockState(pos, Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(rs2)).getDefaultState(), 2);
                    spawnParticles(worldIn, pos, 0);
                    context.getItem().shrink(1);
                    return ActionResultType.SUCCESS;
                } else if (block == RankineBlocks.CAST_IRON_SUPPORT.get() && !worldIn.isRemote()) {
                    int i = 0;
                    while (worldIn.getBlockState(pos.up(i)) == RankineBlocks.CAST_IRON_SUPPORT.get().getDefaultState()) {
                        worldIn.setBlockState(pos.up(i), RankineBlocks.CONCRETE.get().getDefaultState());
                        ++i;
                        if (context.getItem().getCount() >= 1) {
                            context.getItem().shrink(1);
                        } else {
                            break;
                        }
                    }
                    i = 1;
                    while (worldIn.getBlockState(pos.down(i)) == RankineBlocks.CAST_IRON_SUPPORT.get().getDefaultState()) {
                        worldIn.setBlockState(pos.down(i), RankineBlocks.CONCRETE.get().getDefaultState());
                        ++i;
                        if (context.getItem().getCount() >= 1) {
                            context.getItem().shrink(1);
                        } else {
                            break;
                        }
                    }
                    return ActionResultType.SUCCESS;
                } else if (block == RankineBlocks.CAST_IRON_SUPPORT_SLAB.get() && !worldIn.isRemote()) {
                    worldIn.setBlockState(pos, RankineBlocks.CONCRETE_SLAB.get().getDefaultState().with(BlockStateProperties.SLAB_TYPE, state.get(BlockStateProperties.SLAB_TYPE)).with(BlockStateProperties.WATERLOGGED, state.get(BlockStateProperties.WATERLOGGED)));
                    context.getItem().shrink(1);
                    return ActionResultType.SUCCESS;
                } else if (block == RankineBlocks.CAST_IRON_SUPPORT_STAIRS.get() && !worldIn.isRemote()) {
                    worldIn.setBlockState(pos, RankineBlocks.CONCRETE_STAIRS.get().getDefaultState().with(StairsBlock.SHAPE, state.get(StairsBlock.SHAPE)).with(StairsBlock.FACING, state.get(StairsBlock.FACING)).with(StairsBlock.HALF, state.get(StairsBlock.HALF)).with(BlockStateProperties.WATERLOGGED, state.get(BlockStateProperties.WATERLOGGED)));
                    context.getItem().shrink(1);
                    return ActionResultType.SUCCESS;
                } else if (block == RankineBlocks.CONCRETE_VERTICAL_SLAB.get() && !worldIn.isRemote()) {
                    worldIn.setBlockState(pos, RankineBlocks.CONCRETE_VERTICAL_SLAB.get().getDefaultState().with(RankineVerticalSlabBlock.HORIZONTAL_FACING, state.get(RankineVerticalSlabBlock.HORIZONTAL_FACING)).with(RankineVerticalSlabBlock.TYPE, state.get(RankineVerticalSlabBlock.TYPE)));
                    context.getItem().shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }
            return super.onItemUse(context);
        }

    public static void spawnParticles(IWorld worldIn, BlockPos posIn, int data) {
        if (worldIn.isRemote())
        {
            if (data == 0) {
                data = 15;
            }

            BlockState blockstate = worldIn.getBlockState(posIn);
            if (!blockstate.isAir(worldIn, posIn)) {
                double d0 = 0.5D;
                double d1;
                if (blockstate.matchesBlock(Blocks.WATER)) {
                    data *= 3;
                    d1 = 1.0D;
                    d0 = 3.0D;
                } else if (blockstate.isOpaqueCube(worldIn, posIn)) {
                    posIn = posIn.up();
                    data *= 3;
                    d0 = 3.0D;
                    d1 = 1.0D;
                } else {
                    d1 = blockstate.getShape(worldIn, posIn).getEnd(Direction.Axis.Y);
                }

                worldIn.addParticle(ParticleTypes.WHITE_ASH, (double)posIn.getX() + 0.5D, (double)posIn.getY() + 0.5D, (double)posIn.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

                for(int i = 0; i < data; ++i) {
                    double d2 = random.nextGaussian() * 0.02D;
                    double d3 = random.nextGaussian() * 0.02D;
                    double d4 = random.nextGaussian() * 0.02D;
                    double d5 = 0.5D - d0;
                    double d6 = (double)posIn.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                    double d7 = (double)posIn.getY() + random.nextDouble() * d1;
                    double d8 = (double)posIn.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
                    if (!worldIn.getBlockState((new BlockPos(d6, d7, d8)).down()).isAir()) {
                        worldIn.addParticle(ParticleTypes.WHITE_ASH, d6, d7, d8, d2, d3, d4);
                    }
                }

            }
        }

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
            tooltip.add(new StringTextComponent("Used for creating bricks. Made by throwing dry mortar into water").mergeStyle(TextFormatting.GRAY));
    }


}
