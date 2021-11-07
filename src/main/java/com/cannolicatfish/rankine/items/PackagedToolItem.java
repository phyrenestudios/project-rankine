package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PackagedToolItem extends Item {
    public PackagedToolItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add((new StringTextComponent("Contains a completely random tool. Results may vary.")).mergeStyle(TextFormatting.RED));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!playerIn.world.isRemote) {
            playerIn.getHeldItem(handIn).shrink(1);
            playerIn.addItemStackToInventory(genRandomTool(worldIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);

    }

    public ItemStack genRandomTool(World worldIn){
        Random random = worldIn.getRandom();
        ItemStack ret;
        switch(random.nextInt(7))
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
        }
        List<AlloyingRecipe> recipes = worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.ALLOYING);
        AlloyingRecipe alloy = recipes.stream().filter(alloyingRecipe -> !alloyingRecipe.getElementList(worldIn).isEmpty()).collect(Collectors.toList()).get(worldIn.getRandom().nextInt(recipes.size()));
        //System.out.println(alloy.getId());
        //System.out.println(alloy.generateRandomResult(worldIn));

        ((IAlloyItem) ret.getItem()).createAlloyNBT(ret, worldIn, alloy.generateRandomResult(worldIn), alloy.getId(), !alloy.getLocalName().isEmpty() ? alloy.getLocalName() : null);
        if (alloy.getColor() != 16777215) {
            ret.getOrCreateTag().putInt("color",alloy.getColor());
        }
        if (ret.getItem() instanceof IAlloyTool) {
            ((IAlloyTool) ret.getItem()).applyAlloyEnchantments(ret,worldIn);
        }
        return ret;

    }
}
