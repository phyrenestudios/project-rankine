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

public class DowsingRodItem extends Item {
    public DowsingRodItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        if (!world.isRemote && player != null) {
            player.sendStatusMessage(new StringTextComponent("Water height is about y=" + WorldgenUtils.waterTableHeight(world, context.getPos())).mergeStyle(TextFormatting.BLUE), true);
        }
        return ActionResultType.SUCCESS;
    }
}
