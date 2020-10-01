package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class CompositionBlock extends Block {
    int type;
    public CompositionBlock(int type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        super.onPlayerDestroy(worldIn, pos, state);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        ItemStack output;
        switch (type)
        {
            default:
                output = new ItemStack(ModItems.METEORIC_IRON);
                AlloyItem.addAlloy(output,new AlloyData("90Fe-10Ni")); //kamacite
                break;
            case 1:
                output = new ItemStack(ModItems.METEORIC_IRON);
                AlloyItem.addAlloy(output,new AlloyData("80Fe-20Ni")); //antitaenite
                break;
            case 2:
                output = new ItemStack(ModItems.METEORIC_IRON);
                AlloyItem.addAlloy(output,new AlloyData("60Fe-40Ni")); //taenite
                break;
            case 3:
                output = new ItemStack(ModItems.METEORIC_IRON);
                AlloyItem.addAlloy(output,new AlloyData("50Fe-50Ni")); //tetrataenite
                break;
        }
        if (canHarvestBlock(state,worldIn,pos,player) && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots && !player.isCreative()) { // do not drop items while restoring blockstates, prevents item dupe
            float f = 0.5F;
            double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, output);
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);
            worldIn.removeBlock(pos,false);
        }
    }
}
