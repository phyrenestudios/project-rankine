package com.cannolicatfish.rankine.items.tools;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Set;

public class ScraperItem extends ToolItem {

    private static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(Blocks.STONE);

    public ScraperItem(Properties properties) {
        super(1.0f, -3.0f, RankineToolMaterials.INVAR, EFFECTIVE_ON, properties);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity playerentity = context.getPlayer();
        World worldIn = context.getWorld();
        Block block = worldIn.getBlockState(context.getPos()).getBlock();
        ResourceLocation rs = block.getRegistryName();
        if (rs != null) {
            if(rs.getPath().contains("_bricks")) {
                ResourceLocation rs2 = new ResourceLocation(rs.getNamespace(),rs.getPath().split("_bricks")[0]);
                Block bl = ForgeRegistries.BLOCKS.getValue(rs2);
                if (bl != null && bl != Blocks.AIR) {
                    worldIn.setBlockState(context.getPos(),bl.getDefaultState(),2);
                    spawnParticles(worldIn,context.getPos(),0);
                    if (!worldIn.isRemote) {
                        assert playerentity != null;
                        if (!playerentity.isCreative()) {
                            context.getItem().damageItem(1, Objects.requireNonNull(playerentity), (p_220038_0_) -> {
                                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                            });
                        }
                    }
                    return ActionResultType.SUCCESS;
                }
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

                worldIn.addParticle(ParticleTypes.ASH, (double)posIn.getX() + 0.5D, (double)posIn.getY() + 0.5D, (double)posIn.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

                for(int i = 0; i < data; ++i) {
                    double d2 = random.nextGaussian() * 0.02D;
                    double d3 = random.nextGaussian() * 0.02D;
                    double d4 = random.nextGaussian() * 0.02D;
                    double d5 = 0.5D - d0;
                    double d6 = (double)posIn.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                    double d7 = (double)posIn.getY() + random.nextDouble() * d1;
                    double d8 = (double)posIn.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
                    if (!worldIn.getBlockState((new BlockPos(d6, d7, d8)).down()).isAir()) {
                        worldIn.addParticle(ParticleTypes.ASH, d6, d7, d8, d2, d3, d4);
                    }
                }

            }
        }

    }


}
