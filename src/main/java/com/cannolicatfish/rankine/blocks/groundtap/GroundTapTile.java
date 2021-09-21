package com.cannolicatfish.rankine.blocks.groundtap;

import com.cannolicatfish.rankine.blocks.states.TapBarrelFluids;
import com.cannolicatfish.rankine.blocks.tap.TapBarrelBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import static com.cannolicatfish.rankine.init.RankineBlocks.GROUND_TAP_TILE;

public class GroundTapTile extends TileEntity implements ITickableTileEntity {

    public GroundTapTile() {
        super(GROUND_TAP_TILE);
    }

    public void tick() {
        World worldIn = this.getWorld();
        BlockState state = worldIn.getBlockState(pos.up());
        if (worldIn.getDayTime() % 200 == 0 && state.matchesBlock(Blocks.AIR) && worldIn.getBlockState(new BlockPos(pos.getX(), WorldgenUtils.waterTableHeight(worldIn, pos), pos.getZ())).matchesBlock(RankineBlocks.METAL_PIPE.get())) {
            worldIn.setBlockState(pos.up(),Blocks.WATER.getDefaultState(),3);
        }
    }

}
