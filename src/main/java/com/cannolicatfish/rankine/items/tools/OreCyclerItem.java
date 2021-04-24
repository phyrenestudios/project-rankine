package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.ElementEquation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class OreCyclerItem extends Item {
    public OreCyclerItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        List<ElementRecipe> recipes = context.getWorld().getRecipeManager().getRecipesForType(RankineRecipeTypes.ELEMENT);
        if (recipes.size() > 0 && !context.getWorld().isRemote) {
            DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
                p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
            });
            ElementRecipe element = recipes.get(context.getWorld().getRandom().nextInt(recipes.size()));
            int num = context.getWorld().getRandom().nextInt(100) + 1;
            System.out.println(element.getName() + ", " + element.getSymbol() + ", " + element.getAtomicNumber());
            System.out.println("Percent composition: " + num);
            System.out.println("Durability: " + element.getDurability(num));
            System.out.println("Mining Speed: " + df.format(element.getMiningSpeed(num)));
            System.out.println("Mining Level: " + element.getMiningLevel(num));
            System.out.println("Enchantability: " + element.getEnchantability(num));
            System.out.println("Damage: " + df.format(element.getDamage(num)));
            System.out.println("Attack Speed: " + df.format(element.getAttackSpeed(num)));
            System.out.println("Corrosion Resistance: " + df.format(element.getCorrosionResistance(num) * 100)+ "%");
            System.out.println("Heat Resistance: " + df.format(element.getHeatResistance(num) * 100) + "%");
            System.out.println("Knockback Resistance: " + df.format(element.getKnockbackResistance(num) * 100) + "%");
            System.out.println("Toughness: " + df.format(element.getToughness(num)* 100) + "%");
            System.out.println("Tool Enchant: " + element.getToolEnchantment(num));
            System.out.println("Armor Enchant: " + element.getToolEnchantment(num));
            System.out.println("Arrow Type: " + element.getArrowType(num));
            System.out.println("Netherite Ingot Test: " + element.getMaterialCount(Items.NETHERITE_INGOT));

        }

        if (worldIn.getBlockState(context.getPos()).getBlock() instanceof RankineOreBlock)
        {
            RankineOreBlock blockIn = (RankineOreBlock) worldIn.getBlockState(context.getPos()).getBlock();
            int x = worldIn.getBlockState(context.getPos()).get(RankineOreBlock.TYPE) + 1;
            if (x >= 88)
            {
                x = 0;
            }
            worldIn.setBlockState(context.getPos(),blockIn.getDefaultState().with(RankineOreBlock.TYPE, x));
            return ActionResultType.SUCCESS;
        }


        return ActionResultType.FAIL;
    }

}
