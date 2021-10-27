package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class RankineBookshelvesBlock extends Block {
    public static final EnumProperty<BookshelvesBuildingStates> BOOKSHELF_TYPE = EnumProperty.create("bookshelf_type", BookshelvesBuildingStates.class);

    public RankineBookshelvesBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BOOKSHELF_TYPE, BookshelvesBuildingStates.NORMAL));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Item heldItem = context.getPlayer().getHeldItemOffhand().getItem();
        if (heldItem == RankineItems.BUILDING_TOOL.get()) {
            return this.getDefaultState().with(BOOKSHELF_TYPE, BookshelvesBuildingStates.NORMAL);
        } else {
            return this.getDefaultState().with(BOOKSHELF_TYPE, BookshelvesBuildingStates.NORMAL);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BOOKSHELF_TYPE);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 30;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 20;
    }

}
