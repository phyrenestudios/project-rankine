package com.cannolicatfish.rankine.items.tools;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class SawItem extends ToolItem {

    private static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(Blocks.OAK_LOG);

    public SawItem(Properties properties) {
        super(1.0f, -3.0f, RankineToolMaterials.INVAR, EFFECTIVE_ON, properties);
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        int i = this.getTier().getHarvestLevel();
        if (blockIn.getBlock().getTags().contains(new ResourceLocation("minecraft:logs"))) {
            return i >= blockIn.getHarvestLevel();
        }
        Material material = blockIn.getMaterial();
        return material == Material.WOOD;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return efficiency;
        return canHarvestBlock(state) ? this.efficiency : 0.1F;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && state.getBlock().getTags().contains(new ResourceLocation("minecraft:logs"))) {
            int dmg = 0;
            boolean alive = false;
            List<BlockPos> tree = new ArrayList<>();

            tree.add(pos);
            Optional<BlockPos> newpos = BlockPos.getClosestMatchingPosition(pos,1,1, (p) -> { return worldIn.getBlockState(p).getBlock().getTags().contains(new ResourceLocation("minecraft:logs"));});
            //tree.add(newpos);
            if (alive) {
                int i = 0;
                for (BlockPos b : tree) {
                    i += 1;
                    if (i < dmg) {
                        worldIn.destroyBlock(b, true);
                    }
                }
                if (state.getBlockHardness(worldIn, pos) != 0.0F) {
                    stack.damageItem(i, entityLiving, (entity) -> {
                        entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                    });
                }
            }

        }
        return true;
    }



  //  private BlockPos treeCheck(World worldIn, BlockPos pos) {
        //BlockPos.getClosestMatchingPosition())



  //  }


}
