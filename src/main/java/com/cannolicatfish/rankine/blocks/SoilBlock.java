package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SoilBlock extends Block {

    public SoilBlock() {
        super(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.5F).harvestLevel(0));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World worldIn = context.getWorld();
        //if (WorldgenUtils.isWet((ISeedReader) worldIn, context.getPos())) {
        //    worldIn.setBlockState(context.getPos(), VanillaIntegration.soil_mud_map.get(((BlockItem) context.getItem().getItem()).getBlock()).getDefaultState(), 2);
       // }
        return super.getStateForPlacement(context);
    }
}
