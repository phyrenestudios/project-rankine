package com.cannolicatfish.rankine.items.tools;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class HardnessTesterItem extends Item {
    public HardnessTesterItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        if (!world.isClientSide && player != null)
        {
            Block b = world.getBlockState(context.getClickedPos()).getBlock();
            String desc;
            int harvest = -1;
            if (true)
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

            player.sendMessage(new TextComponent("Harvest Level: " + harvest + desc),player.getUUID());
        }


        return InteractionResult.SUCCESS;
    }
}
