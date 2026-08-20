package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTieredItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import net.minecraft.util.RandomSource;
import java.util.stream.Collectors;

public class PackagedArmorItem extends Item {
    public PackagedArmorItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add((Component.translatable("item.rankine.packaged_armor_desc")).withStyle(ChatFormatting.RED));
        super.appendHoverText(stack, context, tooltip, flagIn);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!playerIn.level().isClientSide) {
            playerIn.addItem(genRandomTool(playerIn.getItemInHand(handIn),worldIn));
            playerIn.getItemInHand(handIn).shrink(1);

        }
        return super.use(worldIn, playerIn, handIn);

    }

    public ItemStack genRandomTool(ItemStack stack, Level worldIn){
        ItemStack ret;
        String forced = stack.getOrDefault(RankineDataComponents.FORCE_ARMOR.get(), "");
        if (!forced.isEmpty()) {
            String s = forced;
            switch (s.toUpperCase(Locale.ROOT)) {
                case "HELMET":
                default:
                    ret = new ItemStack(RankineItems.ALLOY_HELMET.get());
                    break;
                case "CHESTPLATE":
                    ret = new ItemStack(RankineItems.ALLOY_CHESTPLATE.get());
                    break;
                case "LEGGINGS":
                    ret = new ItemStack(RankineItems.ALLOY_LEGGINGS.get());
                    break;
                case "BOOTS":
                    ret = new ItemStack(RankineItems.ALLOY_BOOTS.get());
                    break;

            }
        } else {
            RandomSource random = worldIn.getRandom();

            switch(random.nextInt(4))
            {
                case 0:
                default:
                    ret = new ItemStack(RankineItems.ALLOY_HELMET.get());
                    break;
                case 1:
                    ret = new ItemStack(RankineItems.ALLOY_CHESTPLATE.get());
                    break;
                case 2:
                    ret = new ItemStack(RankineItems.ALLOY_LEGGINGS.get());
                    break;
                case 3:
                    ret = new ItemStack(RankineItems.ALLOY_BOOTS.get());
                    break;
            }
        }

        // PORT NOTE: see PackagedToolItem — the RecipeHolder is kept because AlloyingRecipe#getId()
        // stays null until something resolves it lazily, which on a fresh world has never happened.
        List<RecipeHolder<AlloyingRecipe>> recipes = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ALLOYING.get());
        List<RecipeHolder<AlloyingRecipe>> newRecipes = recipes.stream().filter(holder -> !holder.value().getElementList(worldIn).isEmpty()).collect(Collectors.toList());
        if (newRecipes.isEmpty()) {
            return ret;
        }
        // See PackagedToolItem: the draw is bounded by the FILTERED list, because the unfiltered
        // bound ran off the end for the two element-less alloying recipes.
        RecipeHolder<AlloyingRecipe> alloyHolder = newRecipes.get(worldIn.getRandom().nextInt(newRecipes.size()));
        AlloyingRecipe alloy = alloyHolder.value();
        ResourceLocation alloyId = alloyHolder.id();
        alloy.bindId(alloyId);

        String comp = alloy.generateRandomResult(worldIn);
        ((IAlloyItem) ret.getItem()).createAlloyNBT(ret, worldIn, comp, alloyId, !alloy.getLocalName().isEmpty() ? alloy.getLocalName() : null);
        if (alloy.getColor() != 16777215) {
            IAlloyItem.addColorNBT(ret, alloy.getColor());
        }
        if (ret.getItem() instanceof IAlloyTieredItem) {
            IAlloyTieredItem tieredItem = ((IAlloyTieredItem) ret.getItem());
            ((IAlloyTieredItem) ret.getItem()).initStats(ret,tieredItem.getElementMap(comp,worldIn),tieredItem.getAlloyingRecipe(alloyId,worldIn),null);
            ((IAlloyTieredItem) ret.getItem()).applyAlloyEnchantments(ret,worldIn);
        }
        return ret;
    }
}

