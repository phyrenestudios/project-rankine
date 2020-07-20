package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AlloyHoe extends HoeItem {
    private static final Set<Block> effectiveBlocks = ImmutableSet.of(Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK, Blocks.HAY_BLOCK, Blocks.DRIED_KELP_BLOCK, Blocks.TARGET, Blocks.SHROOMLIGHT, Blocks.SPONGE, Blocks.WET_SPONGE, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES);
    private float wmodifier;
    private final AlloyUtils alloy;
    private final PeriodicTableUtils utils = new PeriodicTableUtils();
    private float heat_resistance;
    private float corr_resistance;
    private float toughness;
    public AlloyHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn, float corr_resistance, float heat_resistance, float toughness, AlloyUtils alloy, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.heat_resistance = heat_resistance;
        this.toughness = toughness;
        this.corr_resistance = corr_resistance;
        this.alloy = alloy;
    }
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (getComposition(stack).size() != 0) {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return getDamage(stack) * 1f / this.getMaxDamage(stack);
        } else {
            return getDamage(stack) * 1f / this.getTier().getMaxUses();
        }
    }

    @Override
    public final int getMaxDamage(ItemStack stack)
    {
        if (getComposition(stack).size() != 0) {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return utils.calcDurability(getElements(comp),getPercents(comp)) + this.alloy.getDurabilityBonus();
        } else
        {
            return this.getTier().getMaxUses();
        }
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float efficiency = getEfficiency(stack);
        float wear_modifier = getWearModifier(stack);
        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return (efficiency - wear_modifier);
        return !this.effectiveBlocks.contains(state.getBlock()) ? (1.0f - wear_modifier) : (efficiency - wear_modifier);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
        if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
        if (context.getFace() != Direction.DOWN && world.isAirBlock(blockpos.up())) {
            BlockState blockstate = HOE_LOOKUP.get(world.getBlockState(blockpos).getBlock());
            if (blockstate != null) {
                PlayerEntity playerentity = context.getPlayer();
                world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isRemote) {
                    world.setBlockState(blockpos, blockstate, 11);
                    if (playerentity != null) {
                        context.getItem().damageItem(calcDurabilityLoss(context.getItem(),context.getWorld(),context.getPlayer(),true), playerentity, (p_220043_1_) -> {
                            p_220043_1_.sendBreakAnimation(context.getHand());
                        });
                    }
                }

                return ActionResultType.func_233537_a_(world.isRemote);
            }
        }

        return ActionResultType.PASS;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(calcDurabilityLoss(stack,attacker.getEntityWorld(),attacker,false), attacker, (p_220039_0_) -> {
            p_220039_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    public float getWearModifier(ItemStack stack)
    {
        float eff = getEfficiency(stack);
        float current_dur = this.getDamage(stack);
        float max_dur = getMaxDamage(stack);
        this.wmodifier = eff * .25f;
        return wmodifier - wmodifier*((max_dur - current_dur)/max_dur);
    }

    public float getWearAsPercent(ItemStack stack)
    {
        float eff = getEfficiency(stack);
        float wear_mod = getWearModifier(stack);
        return (eff - wear_mod)/eff * 100;
    }

    public float getMaxWearPercent(ItemStack stack)
    {
        float eff = getEfficiency(stack);
        float wear_mod = getWearModifier(stack);
        return (eff - wmodifier)/eff * 100;
    }

    public float getEfficiency(ItemStack stack)
    {
        if (getComposition(stack).size() != 0) {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return utils.calcMiningSpeed(getElements(comp), getPercents(comp)) + this.alloy.getMiningSpeedBonus();
        } else {
            return this.efficiency;
        }
    }

    public float getCorrResist(ItemStack stack)
    {
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return utils.calcCorrResist(getElements(comp),getPercents(comp)) + alloy.getCorrResistBonus();
        } else
        {
            return this.corr_resistance;
        }

    }

    public float getHeatResist()
    {
        return this.heat_resistance;
    }

    public float getToughness()
    {
        return this.toughness;
    }

    public int calcDurabilityLoss(ItemStack stack, World worldIn, LivingEntity entityLiving, boolean isEfficient)
    {
        Random rand = new Random();
        int i = 1;
        i += rand.nextFloat() < toughness ? 1 : 0;
        if (rand.nextFloat() > getHeatResist() && (entityLiving.isInLava() || entityLiving.getFireTimer() > 0)) {
            i += 1;
        }
        if ((rand.nextFloat() > getCorrResist(stack) && entityLiving.isWet()))
        {
            i += 1;
        }
        if (!isEfficient)
        {
            i *= 2;
        }
        return i;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        if (getComposition(stack).size() != 0) {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return utils.calcEnchantability(getElements(comp), getPercents(comp)) + this.alloy.getEnchantabilityBonus();
        } else {
            return this.getTier().getEnchantability();
        }
    }

    public int getMiningLevel(ItemStack stack)
    {
        if (getComposition(stack).size() != 0) {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return utils.calcMiningLevel(getElements(comp), getPercents(comp)) + this.alloy.getMiningLevelBonus();
        } else {
            return this.getTier().getHarvestLevel();
        }
    }

    public TextFormatting getWearColor(ItemStack stack)
    {
        float maxw = getMaxWearPercent(stack);
        if (getWearAsPercent(stack) >= 80f)
        {
            return TextFormatting.AQUA;
        } else if (getWearAsPercent(stack) >= 60f)
        {
            return TextFormatting.GREEN;
        } else if (getWearAsPercent(stack) >= 40f)
        {
            return TextFormatting.YELLOW;
        } else if (getWearAsPercent(stack) >= 20f)
        {
            return TextFormatting.RED;
        } else{
            return TextFormatting.GRAY;
        }


    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!Screen.hasShiftDown() && getComposition(stack).size() != 0)
        {
            tooltip.add((new StringTextComponent("Hold shift for details...")).func_240701_a_(TextFormatting.GRAY));
        }
        if (Screen.hasShiftDown() && getComposition(stack).size() != 0)
        {
            DecimalFormat df = new DecimalFormat("#.#");
            tooltip.add((new StringTextComponent("Composition: " +getComposition(stack).getCompound(0).get("comp").getString())).func_240701_a_(alloy.getAlloyGroupColor()));
            tooltip.add((new StringTextComponent("Tool Efficiency: " + Math.round(getWearAsPercent(stack)) + "%")).func_240701_a_(getWearColor(stack)));
            if (!flagIn.isAdvanced())
            {
                tooltip.add((new StringTextComponent("Durability: " + (getMaxDamage(stack) - getDamage(stack)) + "/" + getMaxDamage(stack))).func_240701_a_(TextFormatting.DARK_GREEN));
            }
            tooltip.add((new StringTextComponent("Harvest Level: " + getMiningLevel(stack))).func_240701_a_(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent("Mining Speed: " + Float.parseFloat(df.format(getEfficiency(stack))))).func_240701_a_(TextFormatting.GRAY));
            //tooltip.add((new StringTextComponent("Attack Speed: " + Float.parseFloat(df.format((4 + getAttackSpeed(stack))))).applyTextStyle(TextFormatting.WHITE)));
            tooltip.add((new StringTextComponent("Enchantability: " + getItemEnchantability(stack))).func_240701_a_(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent("Corrosion Resistance: " + (Float.parseFloat(df.format(getCorrResist(stack))) * 100) + "%")).func_240701_a_(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent("Heat Resistance: " + (Float.parseFloat(df.format(getHeatResist())) * 100) + "%")).func_240701_a_(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent("Toughness: -" + (Float.parseFloat(df.format(getToughness())) * 100) + "%")).func_240701_a_(TextFormatting.GRAY));
        }

    }

    public static ListNBT getComposition(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt.getList("StoredComposition", 10) : new ListNBT();
    }

    public static void addAlloy(ItemStack p_92115_0_, AlloyData stack) {
        ListNBT listnbt = getComposition(p_92115_0_);
        boolean flag = true;


        if (flag) {
            CompoundNBT compoundnbt1 = new CompoundNBT();
            compoundnbt1.putString("comp", stack.alloyComposition);
            listnbt.add(compoundnbt1);
        }

        p_92115_0_.getOrCreateTag().put("StoredComposition", listnbt);
    }

    /**
     * Returns the ItemStack of an enchanted version of this item.
     */
    public ItemStack getAlloyItemStack(AlloyData p_92111_0_) {
        ItemStack itemstack = new ItemStack(this.getItem());
        addAlloy(itemstack, p_92111_0_);
        return itemstack;
    }

    public List<PeriodicTableUtils.Element> getElements(String c)
    {
        //String c = getComposition(stack).getCompound(0).get("comp").getString();
        PeriodicTableUtils utils = new PeriodicTableUtils();
        String[] comp = c.split("-");
        List<PeriodicTableUtils.Element> list = new ArrayList<>();
        for (String e: comp)
        {
            String str = e.replaceAll("[^A-Za-z]+", "");
            list.add(utils.getElementBySymbol(str));
        }
        return list;
    }

    public List<Integer> getPercents(String c)
    {
        String[] comp = c.split("-");
        List<Integer> list = new ArrayList<>();
        for (String e: comp)
        {
            String str = e.replaceAll("\\D+", "");
            list.add(Integer.parseInt(str));
        }
        return list;
    }

    public List<Enchantment> getEnchantments(String c)
    {
        List<Enchantment> enchantments = new ArrayList<>();
        List<Enchantment> elementEn = utils.getEnchantments(getElements(c),getPercents(c));
        for (Enchantment e: elementEn)
        {
            if (e != null)
            {
                enchantments.add(e);
            }
        }
        Enchantment en = alloy.getEnchantmentBonus(this.getItem());
        if (en != null)
        {
            enchantments.add(en);
        }
        return enchantments;
    }

    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineTools) {
            ItemStack stack = getAlloyItemStack(new AlloyData(alloy.getDefComposition()));
            if (getComposition(stack).size() != 0)
            {
                for (Enchantment e: getEnchantments(getComposition(stack).getCompound(0).get("comp").getString()))
                {
                    stack.addEnchantment(e,alloy.getEnchantmentLevel(e,getItemEnchantability(stack)));
                }
            }

            items.add(stack);
        }
    }
}
