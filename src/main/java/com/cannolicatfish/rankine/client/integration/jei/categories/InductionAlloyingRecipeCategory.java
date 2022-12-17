package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class InductionAlloyingRecipeCategory extends AlloyingRecipeCategory {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "induction_alloying");
    private final IGuiHelper guiHelper;
    public InductionAlloyingRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.guiHelper = guiHelper;
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Component getTitle() {
        return new TextComponent(I18n.get("rankine.jei.induction_alloying"));
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(RankineItems.INDUCTION_FURNACE.get()));
    }
}