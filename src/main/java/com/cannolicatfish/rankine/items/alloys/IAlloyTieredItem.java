package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineDataComponents.AlloyStats;
import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.alloys.AlloyEnchantmentUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public interface IAlloyTieredItem extends IAlloySpecialItem {

    List<AlloyModifier.ModifierType> STATS = Arrays.asList(AlloyModifier.ModifierType.DURABILITY, AlloyModifier.ModifierType.CORROSION_RESISTANCE, AlloyModifier.ModifierType.HEAT_RESISTANCE, AlloyModifier.ModifierType.TOUGHNESS);

    @Override
    default List<AlloyModifier.ModifierType> getDefaultStats() {
        return STATS;
    }

    @Override
    default void initStats(ItemStack stack, Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloyRecipe, @Nullable AlloyModifierRecipe alloyModifier) {
        // was NBT "StoredAlloyStats" {durability, enchantability, corrResist, heatResist, toughness}
        AlloyStats stats = new AlloyStats(
                createValueForDurability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.DURABILITY)),
                0f,
                0,
                createValueForEnchantability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.ENCHANTABILITY)),
                0f,
                0f,
                createValueForCorrosionResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.CORROSION_RESISTANCE)),
                createValueForHeatResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.HEAT_RESISTANCE)),
                createValueForToughness(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.TOUGHNESS)),
                0, 0, 0f, 0f);
        applyAlloyStats(stack, stats);
    }

    @Override
    default void addAlloyInformation(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (this.isAlloyInit(stack)) {
            if (!Screen.hasShiftDown()) {
                tooltip.add((Component.literal("Hold shift for details...")).withStyle(ChatFormatting.GRAY));
            }
            if (Screen.hasShiftDown()) {
                if (IAlloyItem.getAlloyComposition(stack).isEmpty()) {
                    tooltip.add((Component.literal("Any Composition").withStyle(ChatFormatting.GOLD)));
                } else {
                    tooltip.add((Component.literal("Composition: " + IAlloyItem.getAlloyComposition(stack)).withStyle(ChatFormatting.GOLD)));
                }

                if (!IAlloyItem.getAlloyModifiers(stack).isEmpty()) {
                    tooltip.add((Component.literal("Modifier: " + (IAlloyItem.getAlloyModifiers(stack).get(0).name())).withStyle(ChatFormatting.AQUA)));
                }

                if (!this.needsRefresh(stack)) {

                    tooltip.add((Component.literal("Durability: " + (getAlloyDurability(stack) - stack.getDamageValue()) + "/" + getAlloyDurability(stack))).withStyle(ChatFormatting.DARK_GREEN));
                    tooltip.add((Component.literal("Enchantability: " + getAlloyEnchantability(stack))).withStyle(ChatFormatting.GRAY));
                    if (Config.ALLOYS.ALLOY_CORROSION.get()) {
                        tooltip.add((Component.literal("Corrosion Resistance: " + (df.format(getCorrResist(stack) * 100)) + "%")).withStyle(ChatFormatting.GRAY));
                    }
                    if (Config.ALLOYS.ALLOY_HEAT.get()) {
                        tooltip.add((Component.literal("Heat Resistance: " + (df.format(getHeatResist(stack) * 100)) + "%")).withStyle(ChatFormatting.GRAY));
                    }
                    if (Config.ALLOYS.ALLOY_TOUGHNESS.get()) {
                        tooltip.add((Component.literal("Toughness: " + (df.format(getToughness(stack) * 100)) + "%")).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
        }
    }


    default int createValueForDurability(Map<ElementRecipe,Integer> elementMap, @Nullable AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        int dur = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            dur += set.getKey().getDurability(set.getValue());
        }
        if (alloy != null) {
            dur += alloy.getBonusDurability();
        }

        if (modifier != null) {
            dur =  (int) Math.max(0,modifier.returnModification(dur));
        }
        return Math.max(1,dur);
    }

    default float createValueForMiningSpeed(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float ms = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            ms += set.getKey().getMiningSpeed(set.getValue());
        }
        if (alloy != null) {
            ms += alloy.getBonusMiningSpeed();
        }

        if (modifier != null) {
            ms =  Math.max(0,modifier.returnModification(ms));
        }
        return Math.round(ms*100)/100f;
    }


    default int createValueForEnchantability(Map<ElementRecipe, Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        int ench = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            ench += set.getKey().getEnchantability(set.getValue());
        }
        if (alloy != null) {
            ench += alloy.getBonusEnchantability();
        }

        if (modifier != null) {
            ench =  (int) Math.max(0,modifier.returnModification(ench));
        }
        return Math.max(1,ench);
    }

    default int createValueForHarvestLevel(Map<ElementRecipe, Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        int hlmin = 0;
        int hlmax = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            int hl = set.getKey().getMiningLevel(set.getValue());
            hlmin = Math.min(hl,hlmin);
            hlmax = Math.max(hl,hlmax);
        }
        if (alloy != null) {
            int hl = alloy.getBonusMiningLevel();
            hlmin = Math.min(hlmin,hlmin+hl);
            hlmax = Math.max(hlmax,hlmax+hl);
        }
        int hl = Math.max(hlmax + hlmin,0);
        if (modifier != null) {
            hl =  (int) Math.max(0,modifier.returnModification(hl));
        }
        return hl;
    }

    default float createValueForAttackSpeed(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float asmin = 0;
        float asmax = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            float as = set.getKey().getAttackSpeed(set.getValue());
            asmin = Math.min(as,asmin);
            asmax = Math.max(as,asmax);
        }
        if (alloy != null) {
            float as = alloy.getBonusAttackSpeed();
            asmin = Math.min(asmin,asmin+as);
            asmax = Math.max(asmax,asmax+as);
        }
        float as = asmax + asmin;
        if (modifier != null) {
            as =  Math.max(0,modifier.returnModification(as));
        }
        return Math.round(as*100)/100f;
    }

    default float createValueForAttackDamage(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float dmgmin = 0;
        float dmgmax = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            float dmg = set.getKey().getDamage(set.getValue());
            dmgmin = Math.min(dmg,dmgmin);
            dmgmax = Math.max(dmg,dmgmax);
        }
        if (alloy != null) {
            float dmg = alloy.getBonusDamage();
            dmgmin = Math.min(dmgmin,dmgmin+dmg);
            dmgmax = Math.max(dmgmax,dmgmax+dmg);
        }
        float dmg = dmgmax + dmgmin;
        if (modifier != null) {
            dmg = Math.max(0,modifier.returnModification(dmg));
        }
        return Math.round(dmg*100)/100f;
    }

    default float createValueForCorrosionResistance(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float cr = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            cr += set.getKey().getCorrosionResistance(set.getValue());
        }
        if (alloy != null) {
            cr += alloy.getBonusCorrosionResistance();
        }

        if (modifier != null) {
            cr =  Math.max(0,modifier.returnModification(cr));
        }
        return Math.round(Math.min(Math.max(0,cr),1)*100)/100f;
    }

    default float createValueForHeatResistance(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float hr = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            hr += set.getKey().getHeatResistance(set.getValue());
        }
        if (alloy != null) {
            hr += alloy.getBonusHeatResistance();
        }

        if (modifier != null) {
            hr =  Math.max(0,modifier.returnModification(hr));
        }
        return Math.round(Math.min(Math.max(0,hr),1)*100)/100f;
    }

    default float createValueForToughness(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float tough = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            tough += set.getKey().getToughness(set.getValue());
        }
        if (alloy != null) {
            tough += alloy.getBonusToughness();
        }

        if (modifier != null) {
            tough =  Math.max(0,modifier.returnModification(tough));
        }
        return Math.round(Math.min(Math.max(-1,tough),1)*100)/100f;
    }

    default float createValueForKnockbackResistance(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        float kr = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            kr += set.getKey().getKnockbackResistance(set.getValue());
        }
        if (alloy != null) {
            kr += alloy.getBonusKnockbackResistance();
        }

        if (modifier != null) {
            kr =  Math.max(0,modifier.returnModification(kr));
        }
        return Math.round(Math.min(Math.max(0,kr),1)*100)/100f;
    }

    default int getAlloyDurability(ItemStack stack)
    {
        var stats = getAlloyStats(stack);
        return stats != null ? stats.durability() : 1;
    }

    default float getAlloyMiningSpeed(ItemStack stack)
    {
        var stats = getAlloyStats(stack);
        return stats != null ? stats.miningSpeed() : 0.1f;
    }

    default int getAlloyEnchantability(ItemStack stack) {
        var stats = getAlloyStats(stack);
        return stats != null ? stats.enchantability() : 1;
    }

    default Tier getAlloyTier(ItemStack stack) {
        return switch (getAlloyHarvestLevel(stack)) {
            case 1 -> Tiers.STONE;
            case 2 -> Tiers.IRON;
            case 3 -> Tiers.DIAMOND;
            case 4 -> Tiers.NETHERITE;
            default -> Tiers.WOOD;
        };
    }

    default int getAlloyHarvestLevel(ItemStack stack) {
        var stats = getAlloyStats(stack);
        return stats != null ? stats.harvestLevel() : 0;
    }

    default float getAlloyAttackSpeed(ItemStack stack)
    {
        var stats = getAlloyStats(stack);
        return stats != null ? stats.attackSpeed() : 0.1f;
    }

    default float getAlloyAttackDamage(ItemStack stack)
    {
        var stats = getAlloyStats(stack);
        return stats != null ? stats.attackDamage() : 0.1f;
    }

    default float getCorrResist(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_CORROSION.get())
        {
            return 100;
        }
        var stats = getAlloyStats(stack);
        return stats != null ? stats.corrResist() : 0;
    }


    default float getHeatResist(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_HEAT.get())
        {
            return 100;
        }
        var stats = getAlloyStats(stack);
        return stats != null ? stats.heatResist() : 0;
    }

    default float getToughness(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_TOUGHNESS.get())
        {
            return 0;
        }
        var stats = getAlloyStats(stack);
        return stats != null ? stats.toughness() : 0;
    }

    /**
     * DELIBERATE BALANCE CHANGE (release decision 1B, 2026-08-22) — not a parity fix.
     *
     * <p>Where the number comes from: {@link #createValueForKnockbackResistance} sums the element
     * curves and clamps the per-piece result into [0,1], and a [0,1] value is enormous on
     * {@code Attributes.KNOCKBACK_RESISTANCE}, where a full vanilla NETHERITE set totals 0.4
     * (0.1 per piece) and 1.0 is outright immunity. The shipped curves reach the top of that range
     * trivially: zinc is linear a=0.01 capped at 0.25, so any brass at 25% zinc or more — an
     * early-game alloy, brass_alloying permits 15-60% — scored 0.25 per piece, i.e. exactly 1.0
     * across four pieces. Krypton (a=0.1, cap 0.5) and uncapped netherite (a=0.01, so 90% netherite
     * white gold alone is 0.9) get there faster still.
     *
     * <p>1.18.2 did not have this problem for an accidental reason: {@code AlloyArmorItem#initStats}
     * wrote {@code knockbackResist} with {@code putFloat} while this getter read it back with
     * {@code CompoundTag#getInt}, which floors a FloatTag — so every value below 1.0 came back as
     * 0 and alloy armor granted no knockback resistance at all. Restoring that truncation was
     * option 1A and was rejected; so was shipping the raw value. Decision: keep the stat, scaled, so
     * the strongest set in the game lands on vanilla's ceiling instead of past it.
     *
     * <p>{@link #KNOCKBACK_RESISTANCE_SCALE} = 0.1 maps the whole [0,1] curve onto vanilla's
     * per-piece [0,0.1]. Consequences, per four-piece set:
     * <table>
     *   <caption>total KNOCKBACK_RESISTANCE for a full set</caption>
     *   <tr><th>set</th><th>raw/piece</th><th>1.18.2</th><th>port before</th><th>after</th></tr>
     *   <tr><td>brass, 25% zinc</td><td>0.25</td><td>0.00</td><td>1.00</td><td>0.10</td></tr>
     *   <tr><td>brass, 60% zinc (zinc cap binds)</td><td>0.25</td><td>0.00</td><td>1.00</td><td>0.10</td></tr>
     *   <tr><td>ender amalgam, 5% Kr + 50% Zn + Ca + Sr</td><td>0.83</td><td>0.00</td><td>1.00</td><td>0.33</td></tr>
     *   <tr><td>white gold, 90% netherite + 10% zinc</td><td>1.00</td><td>0.00</td><td>1.00</td><td>0.40</td></tr>
     * </table>
     * The strongest alloy in the game therefore ties full netherite (0.4) and can never reach 1.0,
     * a typical zinc alloy sits at a quarter of that, and nothing anywhere gains immunity.
     *
     * <p>Applied here rather than in {@code createValueForKnockbackResistance} on purpose: the
     * stored stat stays the raw curve value (so tooltips, the Element Indexer and any datapack
     * comparison keep reading what the JSON describes), and the scale applies once, at the single
     * point that feeds the live attribute — {@code ItemAttributeModifierHandler}, this getter's only
     * caller.
     */
    default float getKnockbackResistance(ItemStack stack)
    {
        var stats = getAlloyStats(stack);
        return stats != null ? scaleKnockbackResistance(stats.knockbackResist()) : 0;
    }

    /**
     * Vanilla's per-piece knockback-resistance ceiling: a netherite armor piece grants 0.1, so a
     * full set totals 0.4. The alloy curve's [0,1] per-piece range is scaled onto [0,0.1] by this.
     */
    float KNOCKBACK_RESISTANCE_SCALE = 0.1f;

    /**
     * Raw alloy knockback-resistance curve value -> the value handed to
     * {@code Attributes.KNOCKBACK_RESISTANCE}. See {@link #getKnockbackResistance}.
     *
     * <p>The clamp is defensive rather than load-bearing:
     * {@code createValueForKnockbackResistance} already bounds its output to [0,1], but a stack
     * whose ALLOY_STATS component was written by hand or by a future datapack must still not be
     * able to push a four-piece set to 1.0.
     */
    static float scaleKnockbackResistance(float raw) {
        return Math.max(0.0f, Math.min(raw, 1.0f)) * KNOCKBACK_RESISTANCE_SCALE;
    }

    default int calcDurabilityLoss(ItemStack stack, Level worldIn, LivingEntity entityLiving, boolean isEfficient)
    {
        RandomSource rand = worldIn.getRandom();
        int i = 1;
        i += calcToughnessProc(stack,rand);
        i += calcCorrosionResistanceProc(stack,entityLiving,rand);
        int hr = calcHeatResistanceProc(stack,entityLiving,rand);
        i += hr;

        if (!isEfficient)
        {
            i *= 2;
        }

        if (hr > 0 && RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.SHAPE_MEMORY, stack) >= 1) {
            stack.setDamageValue(Math.max(stack.getDamageValue() - i,0));
            i = 0;
        }
        return i;
    }

    default int calcCorrosionResistanceProc(ItemStack stack, LivingEntity entity, RandomSource random) {
        float corrResist = getCorrResist(stack);
        if ((random.nextFloat() > corrResist && entity.isInWaterOrRain())) {
            return Config.ALLOYS.ALLOY_CORROSION_AMT.get();
        }
        return 0;
    }

    default int calcHeatResistanceProc(ItemStack stack, LivingEntity entity, RandomSource random) {
        float heatResist = getHeatResist(stack);
        if ((random.nextFloat() > heatResist &&  (entity.isInLava() || entity.getRemainingFireTicks() > 0 || entity.getCommandSenderWorld().dimension() == Level.NETHER))) {
            return Config.ALLOYS.ALLOY_HEAT_AMT.get();
        }
        return 0;
    }

    default int calcToughnessProc(ItemStack stack, RandomSource random) {
        float toughness = getToughness(stack);
        if (toughness > 0 && random.nextFloat() < toughness) {
            return -1;
        } else if (toughness < 0 && random.nextFloat() < Math.abs(toughness)) {
            return 1;
        }
        return 0;
    }

    default void applyAlloyEnchantments(ItemStack stack, Level worldIn) {
        int start = 10;
        int interval = 5;
        int maxLvl = 5;
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        Optional<RecipeHolder<?>> holder = rs != null ? worldIn.getRecipeManager().byKey(rs) : Optional.empty();
        if (holder.isPresent() && holder.get().value() instanceof AlloyingRecipe recipe) {
            start = recipe.getMinEnchantability();
            interval = recipe.getEnchantInterval();
            maxLvl = recipe.getMaxEnchantLevelIn();
            for (Holder<Enchantment> e: AlloyEnchantmentUtils.getAlloyEnchantments(recipe,stack,worldIn)) {
                int enchLvl = Math.min(Math.floorDiv(Math.max(getAlloyEnchantability(stack) - start + interval,0),interval),maxLvl);
                if (enchLvl > 0 && EnchantmentHelper.getItemEnchantmentLevel(e,stack) == 0) {
                    stack.enchant(e,Math.min(e.value().getMaxLevel(),enchLvl));
                }
            }
        }
        for (Holder<Enchantment> e: AlloyEnchantmentUtils.getElementEnchantments(getElementRecipes(IAlloyItem.getAlloyComposition(stack),worldIn),getPercents(IAlloyItem.getAlloyComposition(stack)),stack,worldIn)) {
            int enchLvl = Math.min(Math.floorDiv(Math.max(getAlloyEnchantability(stack) - start + interval,0),interval),maxLvl);
            if (enchLvl > 0 && EnchantmentHelper.getItemEnchantmentLevel(e,stack) == 0) {
                stack.enchant(e,Math.min(e.value().getMaxLevel(),enchLvl));
            }
        }

    }


}
