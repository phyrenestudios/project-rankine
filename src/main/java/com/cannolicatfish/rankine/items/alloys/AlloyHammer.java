package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.items.tools.ItemHammer;
import com.cannolicatfish.rankine.recipe.PistonCrusherRecipes;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlloyHammer extends ItemHammer {
    private float wmodifier;
    private final AlloyUtils alloy;
    private final PeriodicTableUtils utils = new PeriodicTableUtils();
    private float heat_resistance;
    private float corr_resistance;
    private float toughness;
    private final float attackDamage;
    private final float attackSpeedIn;
    public AlloyHammer(IItemTier tier, int attackDamageIn, float attackSpeedIn, float corr_resistance, float heat_resistance, float toughness, AlloyUtils alloy, Properties builder) {
        super(attackDamageIn, attackSpeedIn, tier, builder);
        this.heat_resistance = heat_resistance;
        this.toughness = toughness;
        this.corr_resistance = corr_resistance;
        this.alloy = alloy;
        this.attackSpeedIn = attackSpeedIn;
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(calcDurabilityLoss(stack,worldIn,entityLiving,true), entityLiving, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }
        if(PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getValue()[0] > 0f && this.getTier().getHarvestLevel() >= state.getBlock().getHarvestLevel(state))
        {
            if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                float f = 0.5F;
                double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                Item item;
                int val;

                if (getBlastModifier(stack) > 0)
                {
                    worldIn.createExplosion(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), 1.25F + 0.25F*getBlastModifier(stack), Explosion.Mode.DESTROY);
                    if (state.getBlockHardness(worldIn, pos) != 0.0F) {
                        stack.damageItem(calcDurabilityLoss(stack,worldIn,entityLiving,true) + 2*getBlastModifier(stack), entityLiving, (p_220038_0_) -> {
                            p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                        });
                    }
                }
                if (PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getValue()[0] > 0f)
                {
                    if (PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getValue()[0] <= 1f)
                    {
                        item = PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getKey().getItem();
                        val = 1;
                    } else
                    {
                        item = PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getKey().getItem();
                        val = Math.round(PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getValue()[0]) - 2 + Math.round(this.getTier().getHarvestLevel());
                        if (val <= 1)
                        {
                            val = 1;
                        }
                    }
                    ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, new ItemStack(item,val));
                    itementity.setDefaultPickupDelay();
                    worldIn.addEntity(itementity);
                    if (getAtomizeModifier(stack) == 0)
                    {
                        worldIn.removeBlock(pos,false);
                    }
                }
                if (PistonCrusherRecipes.getInstance().getSecondaryResult(new ItemStack(state.getBlock())).getValue() > 0f && getAtomizeModifier(stack) == 1)
                {
                    item = PistonCrusherRecipes.getInstance().getSecondaryResult((new ItemStack(state.getBlock()))).getKey().getItem();
                    if (random.nextFloat() <= PistonCrusherRecipes.getInstance().getSecondaryResult((new ItemStack(state.getBlock()))).getValue()) {
                        ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(item, 1));
                        itementity.setDefaultPickupDelay();
                        worldIn.addEntity(itementity);

                    }
                    worldIn.removeBlock(pos, false);
                }

            }
        }

        return true;
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
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getEntityWorld().isRainingAt(target.func_233580_cy_()) && getLightningModifier(stack) == 1)
        {
            LightningBoltEntity ent = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,attacker.world);
            ent.func_233576_c_(Vector3d.func_237492_c_(new BlockPos(target.getPosX(),target.getPosY(),target.getPosZ())));
            ((ServerWorld)target.getEntityWorld()).addEntity(ent);
        }
        if (getDazeModifier(stack) != 0)
        {
            if (attacker instanceof PlayerEntity)
            {
                PlayerEntity player = (PlayerEntity) attacker;
                if (player.getCooledAttackStrength(0) >= (1f - .15*getSwingModifier(stack)))
                {
                    target.addPotionEffect(new EffectInstance(Effects.SLOWNESS,getDazeModifier(stack)*20, 2));
                } else {
                    target.addPotionEffect(new EffectInstance(Effects.SLOWNESS,getDazeModifier(stack)*20, 1));
                }
            } else {
                target.addPotionEffect(new EffectInstance(Effects.SLOWNESS,getDazeModifier(stack)*20, 1));
            }

        }
        stack.damageItem(calcDurabilityLoss(stack,attacker.getEntityWorld(),attacker,true), attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
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


    public float getHeatResist(ItemStack stack)
    {
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return this.heat_resistance + utils.calcHeatResist(getElements(comp),getPercents(comp)) + alloy.getHeatResistBonus();
        } else
        {
            return this.heat_resistance;
        }
    }

    public float getToughness(ItemStack stack)
    {
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return this.toughness + utils.calcToughness(getElements(comp),getPercents(comp)) + alloy.getToughnessBonus();
        } else
        {
            return this.toughness;
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
        tooltip.add((new StringTextComponent(" " + Float.parseFloat(df.format((1 + getAttackDamage(stack) - getWearModifier(stack)))) + " Attack Damage") .func_240701_a_(TextFormatting.DARK_GREEN)));
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
