package com.cannolicatfish.rankine.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class HardnessTesterItem extends Item {
    public HardnessTesterItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (!world.isRemote)
        {
            Block b = world.getBlockState(context.getPos()).getBlock();
            int harvest = b.getHarvestLevel(b.getDefaultState()) + 1;
            String desc;
            if (b.getHarvestTool(b.getDefaultState()) == ToolType.PICKAXE)
            {
                switch (harvest)
                {
                    case 0:
                    case -1:
                    default:
                        desc = " (None)";
                        break;
                    case 1:
                        desc = " (Flint)";
                        break;
                    case 2:
                        desc = " (Bronze)";
                        break;
                    case 3:
                        desc = " (Iron)";
                        break;
                    case 4:
                        desc = " (Steel)";
                        break;
                    case 5:
                        desc = " (Advanced)";
                        break;

                }
            } else {
                harvest = 0;
                desc = " (None)";
            }

            context.getPlayer().sendMessage(new StringTextComponent("Hardness: " + harvest + desc),context.getPlayer().getUniqueID());
        }


        return ActionResultType.SUCCESS;
    }
}
