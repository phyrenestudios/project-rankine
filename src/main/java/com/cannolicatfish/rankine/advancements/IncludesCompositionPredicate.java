package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class IncludesCompositionPredicate extends ItemPredicate {

    String str;
    ResourceLocation tag;
    public IncludesCompositionPredicate(String str, ResourceLocation tagIn) {
        this.str = str;
        this.tag = tagIn;
    }

    public IncludesCompositionPredicate(JsonObject jsonObject) {
        this(JSONUtils.getAsString(jsonObject, "comp"),new ResourceLocation(JSONUtils.getAsString(jsonObject,"tag")));
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (stack.getItem() instanceof IAlloyItem && stack.getItem().getTags().contains(tag)) {
            return IAlloyItem.getAlloyComposition(stack).contains(str);
        }
        return false;
    }
}
