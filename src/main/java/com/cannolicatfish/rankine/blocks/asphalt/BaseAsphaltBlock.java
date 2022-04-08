package com.cannolicatfish.rankine.blocks.asphalt;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import javax.annotation.Nullable;

public class BaseAsphaltBlock extends HorizontalDirectionalBlock {
    //public static final EnumProperty<AsphaltStates> LINE_TYPE = EnumProperty.create("line_type", AsphaltStates.class);
    //public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    public static final IntegerProperty SIZE = IntegerProperty.create("size",1,4);

    public BaseAsphaltBlock() {
        super(Block.Properties.of(Material.STONE).strength(2));
        this.registerDefaultState(this.stateDefinition.any().setValue(SIZE, 4).setValue(FACING, Direction.NORTH));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SIZE,FACING);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(0.0D,0.0D,0.0D,16.0D,4*state.getValue(SIZE),16.0D);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack heldItem = context.getPlayer().getOffhandItem();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.defaultBlockState().setValue(FACING,context.getPlayer().getDirection()).setValue(SIZE, Math.min(4, BuildingToolItem.getBuildingMode(heldItem)+1));
        }
        return this.defaultBlockState().setValue(FACING,context.getPlayer().getDirection());
    }


    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        Direction dir = player.getDirection();
        if (player.getItemInHand(handIn).is(Tags.Items.DYES)) {
            if (state.getValue(FACING) != dir) {
                worldIn.setBlock(pos, state.setValue(FACING, dir), 3);
            } else {
                worldIn.setBlock(pos, RankineLists.ASPHALT_BLOCKS.get((RankineLists.ASPHALT_BLOCKS.indexOf(state.getBlock())+1)%RankineLists.ASPHALT_BLOCKS.size()).defaultBlockState().setValue(SIZE,state.getValue(SIZE)).setValue(FACING, dir), 3);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }


    /*
    @Override
    public boolean ticksRandomly(BlockState state) {
        return state.get(AGE) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.01) {
            worldIn.setBlockState(pos, state.with(AGE, Math.min(state.get(AGE)+1,3)));
        }
    }




     */




}
