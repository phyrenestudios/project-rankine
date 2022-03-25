package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class MudBlock extends Block {
    public MudBlock() {
        super(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).sound(SoundType.GRAVEL).strength(0.5F));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        if (!WorldgenUtils.isWet((WorldGenLevel) worldIn, pos)) {
            worldIn.setBlock(pos, RankineLists.SOIL_BLOCKS.get(RankineLists.MUD_BLOCKS.indexOf(state.getBlock())).defaultBlockState(), 2);
        }
    }

}
