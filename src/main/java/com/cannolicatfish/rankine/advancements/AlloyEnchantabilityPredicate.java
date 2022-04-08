package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class AlloyEnchantabilityPredicate extends ItemPredicate {

    int ench;
    ResourceLocation tag;
    public AlloyEnchantabilityPredicate(int ench, ResourceLocation tagIn) {
        this.ench = ench;
        this.tag = tagIn;
    }

    public AlloyEnchantabilityPredicate(JsonObject jsonObject) {
        this(GsonHelper.getAsInt(jsonObject, "ench"),new ResourceLocation(GsonHelper.getAsString(jsonObject,"tag")));
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (stack.getItem() instanceof IAlloyTool && ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(tag)).contains(stack.getItem())) {
            return ((IAlloyTool) stack.getItem()).getAlloyEnchantability(stack) >= ench;
        }


        return false;
    }
}
