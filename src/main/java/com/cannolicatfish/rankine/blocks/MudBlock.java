package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

// PORT-NOTE: Material.DIRT + MaterialColor.DIRT -> .mapColor(MapColor.DIRT) (DIRT implied no extra behavior flags).
public class MudBlock extends Block {
    public static final MapCodec<MudBlock> CODEC = MapCodec.unit(MudBlock::new);
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    @Override
    public MapCodec<? extends MudBlock> codec() {
        return CODEC;
    }

    public MudBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.SOUL_SOIL).strength(0.7F));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState p_56702_, BlockGetter p_56703_, BlockPos p_56704_, CollisionContext p_56705_) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState p_56707_, BlockGetter p_56708_, BlockPos p_56709_) {
        return Shapes.block();
    }

    @Override
    protected VoxelShape getVisualShape(BlockState p_56684_, BlockGetter p_56685_, BlockPos p_56686_, CollisionContext p_56687_) {
        return Shapes.block();
    }


}
