package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import java.text.DecimalFormat;

import net.minecraft.world.item.Item.Properties;

public class DowsingRodItem extends Item {
    public DowsingRodItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        if (!world.isClientSide && player != null) {
            player.displayClientMessage(new TextComponent("Water height is about y=" + WorldgenUtils.waterTableHeight(world, context.getClickedPos())).withStyle(ChatFormatting.WHITE), true);
        }
        return InteractionResult.SUCCESS;
    }
}
