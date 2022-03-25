package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTieredItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

import net.minecraft.world.item.Item.Properties;

public class PackagedToolItem extends Item {
    public PackagedToolItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add((new TranslatableComponent("item.rankine.packaged_tool_desc")).withStyle(ChatFormatting.RED));
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
        if (stack.hasTag() && !stack.getTag().getString("forceTool").isEmpty()) {
            String s = stack.getTag().getString("forceTool");
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
                case "SURF_ROD":
                    ret = new ItemStack(RankineItems.ALLOY_SURF_ROD.get());
                    break;
            }
        } else {
            Random random = worldIn.getRandom();

            switch(random.nextInt(11))
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
                case 10:
                    ret = new ItemStack(RankineItems.ALLOY_SURF_ROD.get());
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
