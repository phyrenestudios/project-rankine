package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.item.Item.Properties;

public class PumiceSoapItem extends Item {
    public PumiceSoapItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (Config.GENERAL.PUMICE_SOAP.get()) return ActionResultType.FAIL;

        World worldIn = context.getLevel();
        Block block = worldIn.getBlockState(context.getClickedPos()).getBlock();
        ResourceLocation rs = block.getRegistryName();
        if (rs != null) {
            if(rs.getPath().contains("mossy_")) {
                ResourceLocation rs2 = new ResourceLocation(rs.getNamespace(),rs.getPath().split("mossy_")[1]);
                Block bl = ForgeRegistries.BLOCKS.getValue(rs2);
                if (bl != null && bl != Blocks.AIR) {
                    worldIn.setBlock(context.getClickedPos(),bl.defaultBlockState(),2);
                    spawnSoapParticles(worldIn,context.getClickedPos(),0);
                    context.getItemInHand().shrink(1);
                    return ActionResultType.SUCCESS;
                }
            } else if(rs.getPath().contains("cracked_")) {
                ResourceLocation rs2 = new ResourceLocation(rs.getNamespace(),rs.getPath().split("cracked_")[1]);
                Block bl = ForgeRegistries.BLOCKS.getValue(rs2);
                if (bl != null && bl != Blocks.AIR)
                {
                    worldIn.setBlock(context.getClickedPos(),bl.defaultBlockState(),2);
                    spawnSoapParticles(worldIn,context.getClickedPos(),0);
                    context.getItemInHand().shrink(1);
                    return ActionResultType.SUCCESS;
                }
            } else if (block.getTags().contains(new ResourceLocation("forge:stone")) && !rs.getPath().contains("polished_")) {
                ResourceLocation rs2 = new ResourceLocation(rs.getNamespace(),"polished_" + rs.getPath());
                Block bl = ForgeRegistries.BLOCKS.getValue(rs2);
                if (bl != null && bl != Blocks.AIR) {
                    worldIn.setBlock(context.getClickedPos(),bl.defaultBlockState(),2);
                    spawnSoapParticles(worldIn,context.getClickedPos(),0);
                    context.getItemInHand().shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.useOn(context);
    }

    public static void spawnSoapParticles(IWorld worldIn, BlockPos posIn, int data) {
        if (worldIn.isClientSide())
        {
            if (data == 0) {
                data = 15;
            }

            BlockState blockstate = worldIn.getBlockState(posIn);
            if (!blockstate.isAir(worldIn, posIn)) {
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

                worldIn.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)posIn.getX() + 0.5D, (double)posIn.getY() + 0.5D, (double)posIn.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

                for(int i = 0; i < data; ++i) {
                    double d2 = random.nextGaussian() * 0.02D;
                    double d3 = random.nextGaussian() * 0.02D;
                    double d4 = random.nextGaussian() * 0.02D;
                    double d5 = 0.5D - d0;
                    double d6 = (double)posIn.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                    double d7 = (double)posIn.getY() + random.nextDouble() * d1;
                    double d8 = (double)posIn.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
                    if (!worldIn.getBlockState((new BlockPos(d6, d7, d8)).below()).isAir()) {
                        worldIn.addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
                    }
                }

            }
        }

    }
}
