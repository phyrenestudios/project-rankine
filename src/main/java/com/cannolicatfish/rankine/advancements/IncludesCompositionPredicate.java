package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;

public class IncludesCompositionPredicate extends ItemPredicate {

    String str;
    ResourceLocation tag;
    public IncludesCompositionPredicate(String str, ResourceLocation tagIn) {
        this.str = str;
        this.tag = tagIn;
    }

    public IncludesCompositionPredicate(JsonObject jsonObject) {
        this(GsonHelper.getAsString(jsonObject, "comp"),new ResourceLocation(GsonHelper.getAsString(jsonObject,"tag")));
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (stack.getItem() instanceof IAlloyItem && stack.getItem().getTags().contains(tag)) {
            return IAlloyItem.getAlloyComposition(stack).contains(str);
        }
        return false;
    }
}
