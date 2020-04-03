package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AlloyItem extends Item {
    String registryName;
    public AlloyItem(String registryName) {
        super(new Item.Properties().group(ProjectRankine.setup.itemGroup).maxStackSize(64));
        setRegistryName(ProjectRankine.MODID, registryName);
        this.registryName = registryName;
    }



}
