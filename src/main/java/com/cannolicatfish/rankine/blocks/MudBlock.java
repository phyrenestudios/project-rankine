package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class MudBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    public MudBlock() {
        super(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).sound(SoundType.GRAVEL).strength(0.5F));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_56702_, BlockGetter p_56703_, BlockPos p_56704_, CollisionContext p_56705_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState p_56707_, BlockGetter p_56708_, BlockPos p_56709_) {
        return Shapes.block();
    }

    @Override
    public VoxelShape getVisualShape(BlockState p_56684_, BlockGetter p_56685_, BlockPos p_56686_, CollisionContext p_56687_) {
        return Shapes.block();
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return true;
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel levelIn, BlockPos pos, Random p_60554_) {
        if (!WorldgenUtils.isWet(levelIn.getChunk(pos), pos)) {
            levelIn.setBlock(pos, RankineLists.SOIL_BLOCKS.get(RankineLists.MUD_BLOCKS.indexOf(blockState.getBlock())).defaultBlockState(), 3);
        }
        super.randomTick(blockState, levelIn, pos, p_60554_);
    }


}
