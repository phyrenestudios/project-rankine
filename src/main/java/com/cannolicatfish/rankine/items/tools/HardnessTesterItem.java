package com.cannolicatfish.rankine.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import net.minecraft.item.Item.Properties;

public class HardnessTesterItem extends Item {
    public HardnessTesterItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        PlayerEntity player = context.getPlayer();
        if (!world.isClientSide && player != null)
        {
            Block b = world.getBlockState(context.getClickedPos()).getBlock();
            int harvest = b.getHarvestLevel(b.defaultBlockState());
            String desc;
            if (b.getHarvestTool(b.defaultBlockState()) == ToolType.PICKAXE)
            {
                switch (harvest)
                {
                    case -1:
                    default:
                        harvest = 0;
                        desc = " (None)";
                        break;
                    case 0:
                        desc = " (Wood)";
                        break;
                    case 1:
                        desc = " (Stone/Flint/Pewter)";
                        break;
                    case 2:
                        desc = " (Iron/Pewter/Bronze/Invar/Crucible Steel)";
                        break;
                    case 3:
                        desc = " (Diamond/Advanced Alloys)";
                        break;
                    case 4:
                        desc = " (Netherite/Advanced Alloys)";
                        break;
                    case 5:
                        desc = " (Advanced Alloys)";
                        break;
                }

            } else {
                harvest = 0;
                if (b.defaultBlockState().getDestroySpeed(context.getLevel(),context.getClickedPos()) < 0) {
                    desc = " (Unbreakable)";
                } else {
                    desc = " (None)";
                }
            }

            player.sendMessage(new StringTextComponent("Harvest Level: " + harvest + desc),player.getUUID());
        }


        return ActionResultType.SUCCESS;
    }
}
