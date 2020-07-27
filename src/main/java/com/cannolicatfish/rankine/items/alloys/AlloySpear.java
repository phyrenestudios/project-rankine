package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.ModEntityTypes;
import com.cannolicatfish.rankine.entities.SpearEntity;
import com.cannolicatfish.rankine.items.tools.ItemSpear;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
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

public class AlloySpear extends ItemSpear {
    private final float attackDamage;
    private final float attackSpeedIn;
    private float wmodifier;
    public ResourceLocation type;
    public EntityType<SpearEntity> entity;
    private final AlloyUtils alloy;
    private final PeriodicTableUtils utils = new PeriodicTableUtils();
    private final IItemTier tier;
    private float efficiency;
    private ImmutableMultimap<Attribute, AttributeModifier> attributeModifiers;
    public AlloySpear(IItemTier tier, float attackDamageIn, float attackSpeedIn, AlloyUtils alloy, EntityType<SpearEntity> entity, ResourceLocation type, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn,entity, type, properties);
        this.tier = tier;
        this.entity = entity;
        this.efficiency = tier.getEfficiency();
        this.attackSpeedIn = attackSpeedIn;
        this.attackDamage = attackDamageIn + tier.getAttackDamage();
        this.alloy = alloy;
        this.type = type;
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
            return getDamage(stack) * 1f / this.tier.getMaxUses();
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
            return this.tier.getMaxUses();
        }
    }

    public float getWearModifierMining(ItemStack stack)
    {
        float eff = getEfficiency(stack);
        float current_dur = this.getDamage(stack);
        float max_dur = getMaxDamage(stack);
        this.wmodifier = eff * .25f;
        return wmodifier - wmodifier*((max_dur - current_dur)/max_dur);
    }

    public float getWearModifierDmg(ItemStack stack)
    {
        float dmg = getAttackDamage(stack);
        float current_dur = this.getDamage(stack);
        float max_dur = getMaxDamage(stack);
        this.wmodifier = dmg * .25f;
        return wmodifier - wmodifier*((max_dur - current_dur)/max_dur);
    }

    public float getWearAsPercent(ItemStack stack)
    {
        float eff = getEfficiency(stack);
        float wear_mod = getWearModifierDmg(stack);
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
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return utils.calcCorrResist(getElements(comp),getPercents(comp)) + alloy.getCorrResistBonus();
        } else
        {
            return alloy.getCorrResistBonus();
        }

    }


    public float getHeatResist(ItemStack stack)
    {
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return utils.calcHeatResist(getElements(comp),getPercents(comp)) + alloy.getHeatResistBonus();
        } else
        {
            return alloy.getHeatResistBonus();
        }
    }

    public float getToughness(ItemStack stack)
    {
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
            return this.tier.getEnchantability();
        }
    }

    public int getMiningLevel(ItemStack stack)
    {
        if (getComposition(stack).size() != 0) {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return utils.calcMiningLevel(getElements(comp), getPercents(comp)) + this.alloy.getMiningLevelBonus();
        } else {
            return this.tier.getHarvestLevel();
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
        DecimalFormat df = new DecimalFormat("##.#");
        if (!Screen.hasShiftDown() && getComposition(stack).size() != 0)
        {
            tooltip.add((new StringTextComponent("Hold shift for details...")).func_240701_a_(TextFormatting.GRAY));
        }
        if (Screen.hasShiftDown() && getComposition(stack).size() != 0)
        {

            tooltip.add((new StringTextComponent("Composition: " +getComposition(stack).getCompound(0).get("comp").getString())).func_240701_a_(alloy.getAlloyGroupColor()));
            tooltip.add((new StringTextComponent("Tool Efficiency: " + Math.round(getWearAsPercent(stack)) + "%")).func_240701_a_(getWearColor(stack)));
            if (!flagIn.isAdvanced())
            {
                tooltip.add((new StringTextComponent("Durability: " + (getMaxDamage(stack) - getDamage(stack)) + "/" + getMaxDamage(stack))).func_240701_a_(TextFormatting.DARK_GREEN));
            }
            tooltip.add((new StringTextComponent("Harvest Level: " + getMiningLevel(stack))).func_240701_a_(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent("Mining Speed: " + Float.parseFloat(df.format(getEfficiency(stack))))).func_240701_a_(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent("Enchantability: " + getItemEnchantability(stack))).func_240701_a_(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent("Corrosion Resistance: " + (Float.parseFloat(df.format(getCorrResist(stack) * 100))) + "%")).func_240701_a_(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent("Heat Resistance: " + (Float.parseFloat(df.format(getHeatResist(stack) * 100))) + "%")).func_240701_a_(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent("Toughness: -" + (Float.parseFloat(df.format(getToughness(stack))) * 100) + "%")).func_240701_a_(TextFormatting.GRAY));
        }
        tooltip.add((new StringTextComponent("" )));
        tooltip.add((new StringTextComponent("When in main hand: " ).func_240701_a_(TextFormatting.GRAY)));
        tooltip.add((new StringTextComponent(" " + Float.parseFloat(df.format((1 + getAttackDamage(stack) - getWearModifierDmg(stack)))) + " Attack Damage") .func_240701_a_(TextFormatting.DARK_GREEN)));
        tooltip.add((new StringTextComponent(" " + Float.parseFloat(df.format((4 + getAttackSpeed(stack)))) + " Attack Speed").func_240701_a_(TextFormatting.DARK_GREEN)));

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
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            int i = this.getUseDuration(stack) - timeLeft;
            if (i >= 10) {
                int j = EnchantmentHelper.getRiptideModifier(stack);
                if (j <= 0 || playerentity.isWet()) {
                    if (!worldIn.isRemote) {
                        stack.damageItem(calcDurabilityLoss(stack, worldIn,entityLiving,true), playerentity, (p_220047_1_) -> {
                            p_220047_1_.sendBreakAnimation(entityLiving.getActiveHand());
                        });
                        if (j == 0) {
                            SpearEntity spearentity;
                            spearentity = new SpearEntity(worldIn, playerentity, stack, entity, type, this.attackDamage);
                            spearentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, 2.5F + (float)j * 0.5F, 1.0F);
                            if (playerentity.abilities.isCreativeMode) {
                                spearentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                            }

                            worldIn.addEntity(spearentity);
                            worldIn.playMovingSound((PlayerEntity)null, spearentity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            if (!playerentity.abilities.isCreativeMode) {
                                playerentity.inventory.deleteStack(stack);
                            }
                        }
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                    if (j > 0) {
                        float f7 = playerentity.rotationYaw;
                        float f = playerentity.rotationPitch;
                        float f1 = -MathHelper.sin(f7 * ((float)Math.PI / 180F)) * MathHelper.cos(f * ((float)Math.PI / 180F));
                        float f2 = -MathHelper.sin(f * ((float)Math.PI / 180F));
                        float f3 = MathHelper.cos(f7 * ((float)Math.PI / 180F)) * MathHelper.cos(f * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                        float f5 = 3.0F * ((1.0F + (float)j) / 4.0F);
                        f1 = f1 * (f5 / f4);
                        f2 = f2 * (f5 / f4);
                        f3 = f3 * (f5 / f4);
                        playerentity.addVelocity((double)f1, (double)f2, (double)f3);
                        playerentity.startSpinAttack(20);
                        if (playerentity.func_233570_aj_()) {
                            float f6 = 1.1999999F;
                            playerentity.move(MoverType.SELF, new Vector3d(0.0D, (double)1.1999999F, 0.0D));
                        }

                        SoundEvent soundevent;
                        if (j >= 3) {
                            soundevent = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
                        } else if (j == 2) {
                            soundevent = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                        } else {
                            soundevent = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                        }

                        worldIn.playMovingSound((PlayerEntity)null, playerentity, soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }

                }
            }
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(calcDurabilityLoss(stack,attacker.getEntityWorld(),attacker,true), attacker, (p_220039_0_) -> {
            p_220039_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        replaceModifier(getWearModifierDmg(stack));
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        stack.damageItem(calcDurabilityLoss(stack,worldIn,entityLiving,false), entityLiving, (p_220039_0_) -> {
            p_220039_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        replaceModifier(getWearModifierDmg(stack));
        return true;
    }

}
