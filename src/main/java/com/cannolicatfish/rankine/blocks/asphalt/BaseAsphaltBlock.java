package com.cannolicatfish.rankine.blocks.asphalt;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import javax.annotation.Nullable;

public class BaseAsphaltBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<BaseAsphaltBlock> CODEC = simpleCodec(p -> new BaseAsphaltBlock());
    //public static final EnumProperty<AsphaltStates> LINE_TYPE = EnumProperty.create("line_type", AsphaltStates.class);
    //public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    public static final IntegerProperty SIZE = IntegerProperty.create("size",1,4);

    public BaseAsphaltBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(2));
        this.registerDefaultState(this.stateDefinition.any().setValue(SIZE, 4).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseAsphaltBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SIZE,FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(0.0D,0.0D,0.0D,16.0D,4*state.getValue(SIZE),16.0D);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack heldItem = context.getPlayer().getOffhandItem();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.defaultBlockState().setValue(FACING,context.getPlayer().getDirection()).setValue(SIZE, Math.min(4, BuildingToolItem.getBuildingMode(heldItem)+1));
        }
        return this.defaultBlockState().setValue(FACING,context.getPlayer().getDirection());
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        Direction dir = player.getDirection();
        if (stack.is(Tags.Items.DYES)) {
            if (state.getValue(FACING) != dir) {
                worldIn.setBlock(pos, state.setValue(FACING, dir), 3);
            } else {
                worldIn.setBlock(pos, RankineLists.ASPHALT_BLOCKS.get((RankineLists.ASPHALT_BLOCKS.indexOf(state.getBlock())+1)%RankineLists.ASPHALT_BLOCKS.size()).defaultBlockState().setValue(SIZE,state.getValue(SIZE)).setValue(FACING, dir), 3);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, worldIn, pos, player, handIn, hit);
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
