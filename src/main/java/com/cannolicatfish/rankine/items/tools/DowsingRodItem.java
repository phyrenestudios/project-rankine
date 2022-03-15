package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.text.DecimalFormat;

import net.minecraft.item.Item.Properties;

public class DowsingRodItem extends Item {
    public DowsingRodItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        PlayerEntity player = context.getPlayer();
        if (!world.isClientSide && player != null) {
            player.displayClientMessage(new StringTextComponent("Water height is about y=" + WorldgenUtils.waterTableHeight(world, context.getClickedPos())).withStyle(TextFormatting.WHITE), true);
        }
        return ActionResultType.SUCCESS;
    }
}
