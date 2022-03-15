package com.cannolicatfish.rankine.blocks.asphalt;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class BaseAsphaltBlock extends HorizontalBlock {
    //public static final EnumProperty<AsphaltStates> LINE_TYPE = EnumProperty.create("line_type", AsphaltStates.class);
    //public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    public static final IntegerProperty SIZE = IntegerProperty.create("size",1,4);

    public BaseAsphaltBlock() {
        super(Block.Properties.of(Material.STONE).harvestTool(ToolType.PICKAXE).strength(2).harvestLevel(0));
        this.registerDefaultState(this.stateDefinition.any().setValue(SIZE, 4).setValue(FACING, Direction.NORTH));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE,FACING);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.box(0.0D,0.0D,0.0D,16.0D,4*state.getValue(SIZE),16.0D);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        ItemStack heldItem = context.getPlayer().getOffhandItem();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.defaultBlockState().setValue(FACING,context.getPlayer().getDirection()).setValue(SIZE, Math.min(4, BuildingToolItem.getBuildingMode(heldItem)+1));
        }
        return this.defaultBlockState().setValue(FACING,context.getPlayer().getDirection());
    }


    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        Direction dir = player.getDirection();
        if (Tags.Items.DYES.contains(player.getItemInHand(handIn).getItem())) {
            if (state.getValue(FACING) != dir) {
                worldIn.setBlock(pos, state.setValue(FACING, dir), 3);
            } else {
                worldIn.setBlock(pos, RankineLists.ASPHALT_BLOCKS.get((RankineLists.ASPHALT_BLOCKS.indexOf(state.getBlock())+1)%RankineLists.ASPHALT_BLOCKS.size()).defaultBlockState().setValue(SIZE,state.getValue(SIZE)).setValue(FACING, dir), 3);
            }
            return ActionResultType.SUCCESS;
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
