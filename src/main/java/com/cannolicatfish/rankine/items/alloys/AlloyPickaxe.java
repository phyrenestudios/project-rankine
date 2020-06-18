package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.items.ModItems;
import com.cannolicatfish.rankine.util.AlloyUtils;
import com.cannolicatfish.rankine.util.BronzeAlloyUtils;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.*;

public class AlloyPickaxe extends PickaxeItem {
    private float wmodifier;
    private final AlloyUtils alloy;
    private final PeriodicTableUtils utils = new PeriodicTableUtils();
    private float heat_resistance;
    private float corr_resistance;
    private float toughness;
    public AlloyPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, float corr_resistance, float heat_resistance, float toughness, AlloyUtils alloy, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.heat_resistance = heat_resistance;
        this.toughness = toughness;
        this.corr_resistance = corr_resistance;
        this.alloy = alloy;
    }

    /*@Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (getComposition(stack).size() != 0) {
            return getDamage(stack) * 1f / alloy.getDurability(getComposition(stack).getCompound(0).get("comp").getString());
        } else {
            return getDamage(stack) * 1f / this.getTier().getMaxUses();
        }
    }


    @Override
    public final int getMaxDamage(ItemStack stack) {

        //System.out.println(alloy.getDurability(getComposition(stack).getCompound(0).get("comp").getString()));
        if (getComposition(stack).size() != 0)
        {
            return alloy.getDurability(getComposition(stack).getCompound(0).get("comp").getString());
        } else {
            return this.getTier().getMaxUses();
        }


    }*/

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
        String comp = getComposition(stack).getCompound(0).get("comp").getString();
        return utils.calcDurability(getElements(comp),getPercents(comp)) + this.alloy.getDurabilityBonus();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        float efficiency = getEfficiency(stack);
        float wear_modifier = getWearModifier(stack);
        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return (efficiency - wear_modifier);
        return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? (1.0f - wear_modifier) : (efficiency - wear_modifier);
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
            return alloy.getEfficiency(getComposition(stack).getCompound(0).get("comp").getString());
        } else {
            return this.efficiency;
        }
    }

    public float getCurrentAttackDamage(ItemStack stack)
    {
        return this.attackDamage - getWearModifier(stack);
    }

    public float getAttackSpeed(ItemStack stack)
    {
        if (getComposition(stack).size() != 0) {

            return -2.8f - 0.6f*Math.abs(1 - alloy.getAttackSpeedMod(getComposition(stack).getCompound(0).get("comp").getString()));
        } else {
            return -2.8f;
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

    public float getCorrResist(ItemStack stack)
    {
        if (getComposition(stack).size() != 0) {
            return alloy.getCorrResistance(getComposition(stack).getCompound(0).get("comp").getString());
        } else {
            return this.corr_resistance;
        }
    }

    public float getHeatResist(ItemStack stack)
    {
        if (getComposition(stack).size() != 0) {
            return alloy.getHeatResistance(getComposition(stack).getCompound(0).get("comp").getString());
        } else {
            return this.heat_resistance;
        }
    }

    public boolean isCorrResistant(ItemStack stack)
    {
        return false;
    }

    public int calcDurabilityLoss(ItemStack stack, World worldIn, LivingEntity entityLiving, boolean isEfficient)
    {
        Random rand = new Random();
        int i = 1;
        i += rand.nextFloat() < toughness ? 1 : 0;
        if (rand.nextFloat() > getHeatResist(stack) && (worldIn.getDimension().getType() == DimensionType.THE_NETHER || entityLiving.isInLava() || entityLiving.getFireTimer() > 0)) {
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
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(calcDurabilityLoss(stack,worldIn,entityLiving,true), entityLiving, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }
        return true;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        if (getComposition(stack).size() != 0) {
            return alloy.getEnchantability(getComposition(stack).getCompound(0).get("comp").getString());
        } else {
            return this.getTier().getEnchantability();
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(calcDurabilityLoss(stack,attacker.getEntityWorld(),attacker,false), attacker, (p_220038_0_) -> {
            p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!Screen.hasShiftDown() && getComposition(stack).size() != 0)
        {
            tooltip.add((new StringTextComponent("Hold shift for details...").applyTextStyle(TextFormatting.GRAY).applyTextStyle(TextFormatting.ITALIC)));
        }
        if (Screen.hasShiftDown() && getComposition(stack).size() != 0)
        {
            DecimalFormat df = new DecimalFormat("#.#");
            tooltip.add((new StringTextComponent("Composition: " +getComposition(stack).getCompound(0).get("comp").getString())).applyTextStyle(TextFormatting.GOLD));
            tooltip.add((new StringTextComponent("Efficiency: " + Math.round(getWearAsPercent(stack)) + "%").applyTextStyle(getWearColor(stack))));
            tooltip.add((new StringTextComponent("Mining Speed: " + getEfficiency(stack)).applyTextStyle(TextFormatting.WHITE)));
            //tooltip.add((new StringTextComponent("Attack Speed: " + Float.parseFloat(df.format((4 + getAttackSpeed(stack))))).applyTextStyle(TextFormatting.WHITE)));
            tooltip.add((new StringTextComponent("Enchantability: " + getItemEnchantability(stack)).applyTextStyle(TextFormatting.WHITE)));
            tooltip.add((new StringTextComponent("Corrosion Resistance: " + (getCorrResist(stack) * 100) + "%").applyTextStyle(TextFormatting.WHITE)));
            tooltip.add((new StringTextComponent("Heat Resistance: " + (getHeatResist(stack) * 100) + "%").applyTextStyle(TextFormatting.WHITE)));
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

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */

    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineTools) {
            items.add(getAlloyItemStack(new AlloyData(alloy.getDefComposition())));
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    private void replaceAttackModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double multiplier)
    {
        final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());
        final Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

        if (modifierOptional.isPresent())
        {
            final AttributeModifier modifier = modifierOptional.get();

            modifiers.remove(modifier);
            modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), this.attackDamage - multiplier, modifier.getOperation()));
            System.out.println("New Attack Damage:");
            System.out.println(this.attackDamage - multiplier);
        }
    }

    private void replaceAttackSpeedModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double attspeed)
    {
        final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());
        final Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

        if (modifierOptional.isPresent())
        {
            final AttributeModifier modifier = modifierOptional.get();

            modifiers.remove(modifier);
            modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), attspeed, modifier.getOperation()));
            System.out.println("New Attack Speed:");
            System.out.println(attspeed);
        }
    }
}
