package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.cannolicatfish.rankine.util.alloys.AlloyEnchantmentUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class AlloyKnifeItem extends KnifeItem implements IAlloyTool{

    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;

    public AlloyKnifeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn, Item.Properties builderIn) {
        super(tier, (int) attackDamageIn, attackSpeedIn, builderIn);
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        applyHitEffects(stack,target,attacker);
        stack.damageItem(calcDurabilityLoss(stack,attacker.getEntityWorld(),attacker,true), attacker, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(calcDurabilityLoss(stack,worldIn,entityLiving,false), entityLiving, (entity) -> {
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getDamage(stack) * 1f / this.getAlloyDurability(stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getAlloyDurability(stack);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return this.getAlloyEnchantability(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (this.isAlloyInit(stack))
        {
            if (!Screen.hasShiftDown())
            {
                tooltip.add((new StringTextComponent("Hold shift for details...")).mergeStyle(TextFormatting.GRAY));
            }
            if (Screen.hasShiftDown())
            {
                if (IAlloyItem.getAlloyComposition(stack).isEmpty()) {
                    tooltip.add((new StringTextComponent("Any Composition").mergeStyle(TextFormatting.GOLD)));
                } else {
                    tooltip.add((new StringTextComponent("Composition: " + IAlloyItem.getAlloyComposition(stack)).mergeStyle(TextFormatting.GOLD)));
                }

                if (!IAlloyItem.needsRefresh(stack)) {
                    float eff = getAlloyEfficiency(stack);
                    float wear = getWearAsPercent(eff,getAlloyWear(getWearModifierMining(eff),getDamage(stack),getMaxDamage(stack)));

                    tooltip.add((new StringTextComponent("Tool Efficiency: " + Math.round(wear) + "%")).mergeStyle(getWearColor(wear)));
                    tooltip.add((new StringTextComponent("Durability: " + (getAlloyDurability(stack) - getDamage(stack)) + "/" + getAlloyDurability(stack))).mergeStyle(TextFormatting.DARK_GREEN));
                    tooltip.add((new StringTextComponent("Harvest Level: " + (getAlloyHarvestLevel(stack)))).mergeStyle(TextFormatting.GRAY));
                    tooltip.add((new StringTextComponent("Mining Speed: " + df.format(eff))).mergeStyle(TextFormatting.GRAY));
                    tooltip.add((new StringTextComponent("Enchantability: " + getAlloyEnchantability(stack))).mergeStyle(TextFormatting.GRAY));
                    if (Config.ALLOYS.ALLOY_CORROSION.get())
                    {
                        tooltip.add((new StringTextComponent("Corrosion Resistance: " + (df.format(getCorrResist(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                    }
                    if (Config.ALLOYS.ALLOY_HEAT.get())
                    {
                        tooltip.add((new StringTextComponent("Heat Resistance: " + (df.format(getHeatResist(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                    }
                    if (Config.ALLOYS.ALLOY_TOUGHNESS.get())
                    {
                        tooltip.add((new StringTextComponent("Toughness: " + (df.format(getToughness(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                    }
                }

                if (flagIn.isAdvanced()) {
                    if (IAlloyItem.getAlloyRecipe(stack) != null) {
                        tooltip.add((new StringTextComponent("Recipe: " + (IAlloyItem.getAlloyRecipe(stack))).mergeStyle(TextFormatting.LIGHT_PURPLE)));
                    } else {
                        tooltip.add((new StringTextComponent("No Recipe Defined").mergeStyle(TextFormatting.LIGHT_PURPLE)));
                    }

                }
            }
        }
    }

    /*
        @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (getComposition(stack).size() > 0 && alloy.getDefComposition().equals("80Hg-20Au") && nbt != null && !nbt.getString("nameAdd").isEmpty() && !nbt.getString("nameAdd").equals("false")) {
            String name = new TranslationTextComponent(this.getTranslationKey(stack)).getString();
            String[] sp = name.split(" ");
            if (sp.length > 0) {
                name = sp[sp.length - 1];
            }
            return new StringTextComponent(stack.getTag().getString("nameAdd") + " " + name);
        }
        return super.getDisplayName(stack);
    }


     */

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        this.applyAlloyEnchantments(stack,worldIn);
        super.onCreated(stack, worldIn, playerIn);
    }


    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!this.isAlloyInit(stack)) {
            this.createAlloyNBT(stack,worldIn,this.defaultComposition,this.defaultAlloyRecipe,null);
            this.applyAlloyEnchantments(stack,worldIn);
        } else if (IAlloyItem.needsRefresh(stack)) {
            this.createAlloyNBT(stack,worldIn,IAlloyItem.getAlloyComposition(stack),IAlloyItem.getAlloyRecipe(stack),null);
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
/*
    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineTools) {
            ItemStack stack = getAlloyItemStack(new AlloyData(alloy.getDefComposition()),this.getItem());
            for (Enchantment e: getEnchantments(returnCompositionString(stack,this.alloy),stack.getItem(),this.alloy))
            {
                int enchLvl = alloy.getEnchantmentLevel(e,getAlloyEnchantability(stack));
                if (enchLvl > 0) {
                    stack.addEnchantment(e,enchLvl);
                }
            }
            items.add(stack);
        }
    }
*/

}
