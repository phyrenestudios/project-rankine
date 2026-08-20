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

public class PackagedToolItem extends Item {
    public PackagedToolItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add((Component.translatable("item.rankine.packaged_tool_desc")).withStyle(ChatFormatting.RED));
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
        String forced = stack.getOrDefault(RankineDataComponents.FORCE_TOOL.get(), "");
        if (!forced.isEmpty()) {
            String s = forced;
            switch (s.toUpperCase(Locale.ROOT)) {
                case "AXE":
                    ret = new ItemStack(RankineItems.ALLOY_AXE.get());
                    break;
                case "SHOVEL":
                    ret = new ItemStack(RankineItems.ALLOY_SHOVEL.get());
                    break;
                case "HAMMER":
                    ret = new ItemStack(RankineItems.ALLOY_HAMMER.get());
                    break;
                case "SWORD":
                    ret = new ItemStack(RankineItems.ALLOY_SWORD.get());
                    break;
                case "SPEAR":
                    ret = new ItemStack(RankineItems.ALLOY_SPEAR.get());
                    break;
                case "PICKAXE":
                default:
                    ret = new ItemStack(RankineItems.ALLOY_PICKAXE.get());
                    break;
                case "HOE":
                    ret = new ItemStack(RankineItems.ALLOY_HOE.get());
                    break;
                case "KNIFE":
                    ret = new ItemStack(RankineItems.ALLOY_KNIFE.get());
                    break;
                case "CROWBAR":
                    ret = new ItemStack(RankineItems.ALLOY_CROWBAR.get());
                    break;
                case "BLUNDERBUSS":
                    ret = new ItemStack(RankineItems.ALLOY_BLUNDERBUSS.get());
                    break;
            }
        } else {
            RandomSource random = worldIn.getRandom();

            switch(random.nextInt(10))
            {
                case 0:
                    ret = new ItemStack(RankineItems.ALLOY_AXE.get());
                    break;
                case 1:
                    ret = new ItemStack(RankineItems.ALLOY_SHOVEL.get());
                    break;
                case 2:
                    ret = new ItemStack(RankineItems.ALLOY_HAMMER.get());
                    break;
                case 3:
                    ret = new ItemStack(RankineItems.ALLOY_SWORD.get());
                    break;
                case 4:
                    ret = new ItemStack(RankineItems.ALLOY_SPEAR.get());
                    break;
                case 5:
                default:
                    ret = new ItemStack(RankineItems.ALLOY_PICKAXE.get());
                    break;
                case 6:
                    ret = new ItemStack(RankineItems.ALLOY_HOE.get());
                    break;
                case 7:
                    ret = new ItemStack(RankineItems.ALLOY_KNIFE.get());
                    break;
                case 8:
                    ret = new ItemStack(RankineItems.ALLOY_CROWBAR.get());
                    break;
                case 9:
                    ret = new ItemStack(RankineItems.ALLOY_BLUNDERBUSS.get());
                    break;
            }
        }

        // PORT NOTE: keep the RecipeHolder rather than mapping straight to the recipe value. An
        // AlloyingRecipe only learns its own id lazily (AlloyingRecipe#resolveId, driven from
        // matches()/generateResult()), so in a world where nobody has smelted yet getId() is still
        // null and the minted gear comes out with no recipe, a blank name and no recipe stat
        // bonuses. The holder carries the authoritative id, which the RecipeManager has held since
        // datapack load — before any level can be used, on client and dedicated server alike, and
        // with no dependence on registry order or level-load timing.
        List<RecipeHolder<AlloyingRecipe>> recipes = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ALLOYING.get());
        List<RecipeHolder<AlloyingRecipe>> newRecipes = recipes.stream().filter(holder -> !holder.value().getElementList(worldIn).isEmpty()).collect(Collectors.toList());
        if (newRecipes.isEmpty()) {
            return ret;
        }
        // The draw is bounded by the FILTERED list. It used to be bounded by the unfiltered one
        // (1.18.2 did the same), which runs off the end whenever an alloying recipe declares no
        // elements — crucible_steel_alloying and damascus_steel_alloying both do, so 2 draws in 49
        // threw IndexOutOfBoundsException on the server thread instead of yielding any gear.
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
