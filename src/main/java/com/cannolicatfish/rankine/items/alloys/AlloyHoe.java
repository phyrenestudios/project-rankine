package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
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
import java.text.DecimalFormatSymbols;
import java.util.*;

public class AlloyHoe extends HoeItem {
    private static final Set<Block> effectiveBlocks = ImmutableSet.of(Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK, Blocks.HAY_BLOCK, Blocks.DRIED_KELP_BLOCK, Blocks.TARGET, Blocks.SHROOMLIGHT, Blocks.SPONGE, Blocks.WET_SPONGE, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES);
    private float wmodifier;
    private final AlloyUtils alloy;
    private final PeriodicTableUtils utils = new PeriodicTableUtils();
    private final float attackDamage;
    private final float attackSpeedIn;
    private ImmutableMultimap<Attribute, AttributeModifier> attributeModifiers;
    public AlloyHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn, AlloyUtils alloy, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
        this.alloy = alloy;
        this.attackSpeedIn = attackSpeedIn;
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (getComposition(stack).size() != 0) {
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

    public float getWearModifierMining(ItemStack stack)
    {
        float eff = getEfficiency(stack);
        float current_dur = this.getDamage(stack);
        float max_dur = getMaxDamage(stack);
        this.wmodifier = eff * Config.ALLOY_WEAR_MINING_AMT.get().floatValue();
        return wmodifier - wmodifier*((max_dur - current_dur)/max_dur);
    }

    public float getWearModifierDmg(ItemStack stack)
    {
        float dmg = getAttackDamage(stack);
        float current_dur = this.getDamage(stack);
        float max_dur = getMaxDamage(stack);
        float wmodifier = dmg * Config.ALLOY_WEAR_DAMAGE_AMT.get().floatValue();
        return wmodifier - wmodifier*((max_dur - current_dur)/max_dur);
    }

    public float getWearAsPercent(ItemStack stack)
    {
        float eff = getEfficiency(stack);
        float wear_mod = getWearModifierMining(stack);
        return (eff - wear_mod)/eff * 100;
    }

    public float getMaxWearPercent(ItemStack stack)
    {
        float eff = getEfficiency(stack);
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
        if (!Config.ALLOY_CORROSION.get())
        {
            return 100;
        }
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return Math.min(utils.calcCorrResist(getElements(comp),getPercents(comp)) + alloy.getCorrResistBonus(), 1);
        } else
        {
            return alloy.getCorrResistBonus();
        }

    }


    public float getHeatResist(ItemStack stack)
    {
        if (!Config.ALLOY_HEAT.get())
        {
            return 100;
        }
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return Math.min(utils.calcHeatResist(getElements(comp),getPercents(comp)) + alloy.getHeatResistBonus(),1);
        } else
        {
            return alloy.getHeatResistBonus();
        }
    }

    public float getToughness(ItemStack stack)
    {
        if (!Config.ALLOY_TOUGHNESS.get())
        {
            return 0;
        }
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return utils.calcToughness(getElements(comp),getPercents(comp)) + alloy.getToughnessBonus();
        } else
        {
            return alloy.getToughnessBonus();
        }
    }

    public int calcDurabilityLoss(ItemStack stack, World worldIn, LivingEntity entityLiving, boolean isEfficient)
    {
        Random rand = new Random();
        int i = 1;
        i += rand.nextFloat() < getToughness(stack) ? 1 : 0;
        if (rand.nextFloat() > getHeatResist(stack) && (entityLiving.isInLava() || entityLiving.getFireTimer() > 0)) {
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
        float maxWear = getMaxWearPercent(stack);
        if (maxWear >= 80f)
        {
            return TextFormatting.AQUA;
        } else if (maxWear >= 60f)
        {
            return TextFormatting.GREEN;
        } else if (maxWear >= 40f)
        {
            return TextFormatting.YELLOW;
        } else if (maxWear >= 20f)
        {
            return TextFormatting.RED;
        } else{
            return TextFormatting.GRAY;
        }


    }

    public float getAttackDamage(ItemStack stack) {
        if (getComposition(stack).size() != 0) {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return this.attackDamage + utils.calcDamage(getElements(comp), getPercents(comp)) + this.alloy.getAttackDamageBonus();
        } else {
            return this.attackDamage;
        }
    }

    public float getAttackSpeed(ItemStack stack) {

        if (getComposition(stack).size() != 0) {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return Math.min(this.attackSpeedIn + utils.calcAttackSpeed(getElements(comp), getPercents(comp)) + this.alloy.getAttackSpeedBonus(), 0);
        } else {
            return this.attackSpeedIn;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (!Screen.hasShiftDown() && getComposition(stack).size() != 0)
        {
            tooltip.add((new StringTextComponent("Hold shift for details...")).mergeStyle(TextFormatting.GRAY));
        }
        if (Screen.hasShiftDown() && getComposition(stack).size() != 0)
        {

            tooltip.add((new StringTextComponent("Composition: " +getComposition(stack).getCompound(0).get("comp").getString())).mergeStyle(alloy.getAlloyGroupColor()));
            tooltip.add((new StringTextComponent("Tool Efficiency: " + Math.round(getWearAsPercent(stack)) + "%")).mergeStyle(getWearColor(stack)));
            if (!flagIn.isAdvanced())
            {
                tooltip.add((new StringTextComponent("Durability: " + (getMaxDamage(stack) - getDamage(stack)) + "/" + getMaxDamage(stack))).mergeStyle(TextFormatting.DARK_GREEN));
            }
            tooltip.add((new StringTextComponent("Harvest Level: " + getMiningLevel(stack))).mergeStyle(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent("Mining Speed: " + df.format(getEfficiency(stack)))).mergeStyle(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent("Enchantability: " + getItemEnchantability(stack))).mergeStyle(TextFormatting.GRAY));
            if (Config.ALLOY_CORROSION.get())
            {
                tooltip.add((new StringTextComponent("Corrosion Resistance: " + (df.format(getCorrResist(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
            }
            if (Config.ALLOY_HEAT.get())
            {
                tooltip.add((new StringTextComponent("Heat Resistance: " + (df.format(getHeatResist(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
            }
            if (Config.ALLOY_TOUGHNESS.get())
            {
                tooltip.add((new StringTextComponent("Toughness: -" + (df.format(getToughness(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
            }
        }
        tooltip.add((new StringTextComponent("" )));
        tooltip.add((new StringTextComponent("When in main hand: " ).mergeStyle(TextFormatting.GRAY)));
        tooltip.add((new StringTextComponent(" " + df.format((1 + getAttackDamage(stack))) + " Attack Damage") .mergeStyle(TextFormatting.DARK_GREEN)));
        tooltip.add((new StringTextComponent(" " + df.format((4 + getAttackSpeed(stack))) + " Attack Speed").mergeStyle(TextFormatting.DARK_GREEN)));
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
        p_92115_0_.getOrCreateTag().putInt("HideFlags",2);
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


    private void replaceModifier(double multiplier)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", Math.max((double)this.attackDamage - multiplier,1), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float efficiency = getEfficiency(stack);
        float wear_modifier = getWearModifierMining(stack);
        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return (efficiency - wear_modifier);
        return !effectiveBlocks.contains(state.getBlock()) ? (1.0f - wear_modifier) : (efficiency - wear_modifier);
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

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(calcDurabilityLoss(stack,worldIn,entityLiving,true), entityLiving, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
            replaceModifier(getWearModifierDmg(stack));
        }

        return true;
    }

    public AlloyUtils getAlloy() {
        return alloy;
    }
}
