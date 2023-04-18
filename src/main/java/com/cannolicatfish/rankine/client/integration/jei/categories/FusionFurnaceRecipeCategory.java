package com.cannolicatfish.rankine.client.integration.jei.categories;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.recipes.RankineJEIRecipeTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.recipe.FusionFurnaceRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FusionFurnaceRecipeCategory implements IRecipeCategory<FusionFurnaceRecipe> {

    public static ResourceLocation UID = new ResourceLocation(ProjectRankine.MODID, "fusion_furnace");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IGuiHelper guiHelper;

    private final LoadingCache<Integer, IDrawableAnimated> cachedFlames;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
    public FusionFurnaceRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        background = guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/fusion_furnace_jei.png"), 0, 0, 172,86)
                .addPadding(0, 0, 0, 0)
                .build();
        localizedName = I18n.get("rankine.jei.fusion_furnace");
        overlay = guiHelper.createDrawable(new ResourceLocation(ProjectRankine.MODID, "textures/gui/fusion_furnace_jei.png"),
                0, 15, 175, 80);
        this.cachedFlames = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer burnTime) {
                        return guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/cauldron_drying_jei.png"), 232, 0, 14, 14)
                                .buildAnimated(burnTime, IDrawableAnimated.StartDirection.TOP, true);
                    }
                });
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return guiHelper.drawableBuilder(new ResourceLocation(ProjectRankine.MODID, "textures/gui/cauldron_drying_jei.png"), 232, 14, 24, 17)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }

    @Override
    public RecipeType<FusionFurnaceRecipe> getRecipeType() {
        return RankineJEIRecipeTypes.FUSION_FURNACE_RECIPE_TYPE;
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(FusionFurnaceRecipe recipe) {
        return UID;
    }

    @Override
    public Component getTitle() {
        return Component.literal(localizedName);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RankineItems.FUSION_FURNACE.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FusionFurnaceRecipe recipe, IFocusGroup focuses) {
        if (!recipe.getFluidIn().isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT,2,2).addIngredient(ForgeTypes.FLUID_STACK, recipe.getFluidIn()).setFluidRenderer(64000,true,16,64);
            ItemStack bucket = FluidUtil.getFilledBucket(recipe.getFluidIn());
            if (bucket != ItemStack.EMPTY) {
                builder.addSlot(RecipeIngredientRole.INPUT,2,68).addItemStack(bucket);
            }
        }
        if (!recipe.getFluidOut().isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT,154,2).addIngredient(ForgeTypes.FLUID_STACK, recipe.getFluidOut()).setFluidRenderer(64000,true,16,64);
            ItemStack bucket = FluidUtil.getFilledBucket(recipe.getFluidOut());
            if (bucket != ItemStack.EMPTY) {
                builder.addSlot(RecipeIngredientRole.OUTPUT,154,68).addItemStack(bucket);
            }
        }
        if (!recipe.getGasIn().isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT,20,25).addItemStack(recipe.getGasIn());
        }
        if (!recipe.getGasOut().isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT,136,25).addItemStack(recipe.getGasOut());
        }
        builder.addSlot(RecipeIngredientRole.INPUT,44,7).addIngredients(recipe.getIngredient1());
        builder.addSlot(RecipeIngredientRole.INPUT,62,7).addIngredients(recipe.getIngredient2());

        builder.addSlot(RecipeIngredientRole.OUTPUT,108,14).addItemStack(recipe.getResult1());
        builder.addSlot(RecipeIngredientRole.OUTPUT,108,40).addItemStack(recipe.getResult2());

        builder.addSlot(RecipeIngredientRole.CATALYST,53,43).addIngredients(Ingredient.merge(List.of(Ingredient.of(RankineTags.Items.BATTERIES),Ingredient.of(RankineTags.Items.RTG))));



    }
}
