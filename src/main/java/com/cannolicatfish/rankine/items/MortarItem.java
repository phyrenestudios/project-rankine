package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class MortarItem extends Item {
    public MortarItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        ResourceLocation rs = ForgeRegistries.BLOCKS.getKey(block);
            if (rs != null) {
                ResourceLocation rs2 = new ResourceLocation(rs.getNamespace(), rs.getPath()+"_bricks");
                if (ForgeRegistries.BLOCKS.containsKey(rs2) && !worldIn.isClientSide()) {
                    worldIn.setBlock(pos, Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(rs2)).defaultBlockState(), 2);
                    spawnParticles(worldIn, pos, 0);
                    context.getItemInHand().shrink(1);
                    return InteractionResult.SUCCESS;
                } else if (block == RankineBlocks.CAST_IRON_SUPPORT.get() && !worldIn.isClientSide()) {
                    int i = 0;
                    while (worldIn.getBlockState(pos.above(i)) == RankineBlocks.CAST_IRON_SUPPORT.get().defaultBlockState()) {
                        worldIn.setBlockAndUpdate(pos.above(i), RankineBlocks.CONCRETE.getCementBlock().defaultBlockState());
                        ++i;
                        if (context.getItemInHand().getCount() >= 1) {
                            context.getItemInHand().shrink(1);
                        } else {
                            break;
                        }
                    }
                    i = 1;
                    while (worldIn.getBlockState(pos.below(i)) == RankineBlocks.CAST_IRON_SUPPORT.get().defaultBlockState()) {
                        worldIn.setBlockAndUpdate(pos.below(i), RankineBlocks.CONCRETE.getCementBlock().defaultBlockState());
                        ++i;
                        if (context.getItemInHand().getCount() >= 1) {
                            context.getItemInHand().shrink(1);
                        } else {
                            break;
                        }
                    }
                    return InteractionResult.SUCCESS;
                } else if (block == RankineBlocks.CAST_IRON_SUPPORT_SLAB.get() && !worldIn.isClientSide()) {
                    worldIn.setBlockAndUpdate(pos, RankineBlocks.CONCRETE.getCementSlab().defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, state.getValue(BlockStateProperties.SLAB_TYPE)).setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED)));
                    context.getItemInHand().shrink(1);
                    return InteractionResult.SUCCESS;
                } else if (block == RankineBlocks.CAST_IRON_SUPPORT_STAIRS.get() && !worldIn.isClientSide()) {
                    worldIn.setBlockAndUpdate(pos, RankineBlocks.CONCRETE.getCementStairs().defaultBlockState().setValue(StairBlock.SHAPE, state.getValue(StairBlock.SHAPE)).setValue(StairBlock.FACING, state.getValue(StairBlock.FACING)).setValue(StairBlock.HALF, state.getValue(StairBlock.HALF)).setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED)));
                    context.getItemInHand().shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
            return super.useOn(context);
        }

    public static void spawnParticles(LevelAccessor worldIn, BlockPos posIn, int data) {
        if (worldIn.isClientSide())
        {
            if (data == 0) {
                data = 15;
            }

            BlockState blockstate = worldIn.getBlockState(posIn);
            if (!blockstate.isAir()) {
                double d0 = 0.5D;
                double d1;
                if (blockstate.is(Blocks.WATER)) {
                    data *= 3;
                    d1 = 1.0D;
                    d0 = 3.0D;
                } else if (blockstate.isSolidRender(worldIn, posIn)) {
                    posIn = posIn.above();
                    data *= 3;
                    d0 = 3.0D;
                    d1 = 1.0D;
                } else {
                    d1 = blockstate.getShape(worldIn, posIn).max(Direction.Axis.Y);
                }

                worldIn.addParticle(ParticleTypes.WHITE_ASH, (double)posIn.getX() + 0.5D, (double)posIn.getY() + 0.5D, (double)posIn.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                RandomSource random = worldIn.getRandom();
                for(int i = 0; i < data; ++i) {
                    double d2 = random.nextGaussian() * 0.02D;
                    double d3 = random.nextGaussian() * 0.02D;
                    double d4 = random.nextGaussian() * 0.02D;
                    double d5 = 0.5D - d0;
                    double d6 = (double)posIn.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                    double d7 = (double)posIn.getY() + random.nextDouble() * d1;
                    double d8 = (double)posIn.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
                    if (!worldIn.getBlockState((new BlockPos((int) d6, (int) d7, (int) d8)).below()).isAir()) {
                        worldIn.addParticle(ParticleTypes.WHITE_ASH, d6, d7, d8, d2, d3, d4);
                    }
                }

            }
        }

    }

    /*
    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
            tooltip.add(new StringTextComponent("Used for creating bricks.").mergeStyle(TextFormatting.GRAY));
    }

     */


}
