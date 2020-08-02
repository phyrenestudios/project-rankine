package com.cannolicatfish.rankine.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ThermometerItem extends Item {
    public ThermometerItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand p_77659_3_) {
        if (!world.isRemote) {
            playerEntity.sendMessage(new StringTextComponent("Biome Air Temperature: " + world.getBiome(playerEntity.func_233580_cy_()).getDefaultTemperature()),playerEntity.getUniqueID());
        }
        return ActionResult.resultSuccess(new ItemStack(this));
    }
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (!world.isRemote)
        {
            context.getPlayer().sendMessage(new StringTextComponent("Block Temperature: " + world.getBiome(context.getPos()).getTemperature(context.getPos())),context.getPlayer().getUniqueID());
        }


        return ActionResultType.SUCCESS;
    }
}
