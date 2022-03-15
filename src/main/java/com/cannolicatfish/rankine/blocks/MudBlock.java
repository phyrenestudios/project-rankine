package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class MudBlock extends Block {
    public MudBlock() {
        super(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.DIRT).sound(SoundType.GRAVEL).harvestTool(ToolType.SHOVEL).strength(0.5F).harvestLevel(0));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!WorldgenUtils.isWet((ISeedReader) worldIn, pos)) {
            worldIn.setBlock(pos, RankineLists.SOIL_BLOCKS.get(RankineLists.MUD_BLOCKS.indexOf(state.getBlock())).defaultBlockState(), 2);
        }
    }

}
