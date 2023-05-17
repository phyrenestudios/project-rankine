package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTieredItem;
import com.cannolicatfish.rankine.recipe.OldAlloyingRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PackagedArmorItem extends Item {
    public PackagedArmorItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add((Component.translatable("item.rankine.packaged_tool_armor_desc")).withStyle(ChatFormatting.RED));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!playerIn.level.isClientSide) {
            playerIn.addItem(genRandomTool(playerIn.getItemInHand(handIn),worldIn));
            playerIn.getItemInHand(handIn).shrink(1);

        }
        return super.use(worldIn, playerIn, handIn);

    }

    public ItemStack genRandomTool(ItemStack stack, Level worldIn){
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

        List<OldAlloyingRecipe> recipes = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ALLOYING.get());
        List<OldAlloyingRecipe> newRecipes = recipes.stream().filter(alloyingRecipe -> !alloyingRecipe.getElementList(worldIn).isEmpty()).collect(Collectors.toList());
        OldAlloyingRecipe alloy = newRecipes.get(worldIn.getRandom().nextInt(recipes.size()));
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

