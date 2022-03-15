package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTieredItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

import net.minecraft.item.Item.Properties;

public class PackagedArmorItem extends Item {
    public PackagedArmorItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add((new TranslationTextComponent("item.rankine.packaged_tool_armor_desc")).withStyle(TextFormatting.RED));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!playerIn.level.isClientSide) {
            playerIn.addItem(genRandomTool(playerIn.getItemInHand(handIn),worldIn));
            playerIn.getItemInHand(handIn).shrink(1);

        }
        return super.use(worldIn, playerIn, handIn);

    }

    public ItemStack genRandomTool(ItemStack stack, World worldIn){
        ItemStack ret;
        if (stack.hasTag() && !stack.getTag().getString("forceArmor").isEmpty()) {
            String s = stack.getTag().getString("forceArmor");
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
            Random random = worldIn.getRandom();

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

        List<AlloyingRecipe> recipes = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ALLOYING);
        List<AlloyingRecipe> newRecipes = recipes.stream().filter(alloyingRecipe -> !alloyingRecipe.getElementList(worldIn).isEmpty()).collect(Collectors.toList());
        AlloyingRecipe alloy = newRecipes.get(worldIn.getRandom().nextInt(recipes.size()));
        //System.out.println(alloy.getId());
        //System.out.println(alloy.generateRandomResult(worldIn));


        String comp = alloy.generateRandomResult(worldIn);
        ((IAlloyItem) ret.getItem()).createAlloyNBT(ret, worldIn, comp, alloy.getId(), !alloy.getLocalName().isEmpty() ? alloy.getLocalName() : null);
        if (alloy.getColor() != 16777215) {
            ret.getOrCreateTag().putInt("color",alloy.getColor());
        }
        if (ret.getItem() instanceof IAlloyTieredItem) {
            IAlloyTieredItem tieredItem = ((IAlloyTieredItem) ret.getItem());
            ((IAlloyTieredItem) ret.getItem()).initStats(ret,tieredItem.getElementMap(comp,worldIn),tieredItem.getAlloyingRecipe(alloy.getId(),worldIn),null);
            ((IAlloyTieredItem) ret.getItem()).applyAlloyEnchantments(ret,worldIn);
        }
        return ret;
    }
}

