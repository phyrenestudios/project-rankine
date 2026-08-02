package com.cannolicatfish.rankine.items.alloys;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;

public class AlloySwordItem extends SwordItem implements IAlloyTool {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;

    public AlloySwordItem(Tier tier, float attackDamageIn, float attackSpeedIn, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn, Item.Properties builderIn) {
        super(tier, builderIn.attributes(SwordItem.createAttributes(tier, (float)(int) attackDamageIn, attackSpeedIn)));
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public Component getName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return Component.translatable(this.getDescriptionId(stack),Component.translatable(IAlloyItem.getNameOverride(stack)));
        }
        return Component.translatable(this.getDescriptionId(stack),Component.translatable(generateLangFromRecipe(this.defaultAlloyRecipe)));
    }


    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(calcDurabilityLoss(stack,attacker.getCommandSenderWorld(),attacker,true), attacker, EquipmentSlot.MAINHAND);
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(calcDurabilityLoss(stack,worldIn,entityLiving,false), entityLiving, EquipmentSlot.MAINHAND);
        }

        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getAlloyDurability(stack);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return this.getAlloyEnchantability(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        addAlloyInformation(stack,context.level(),tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,context.level(),tooltip,flagIn);
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        this.initStats(stack,getElementMap(IAlloyItem.getAlloyComposition(stack),worldIn),getAlloyingRecipe(IAlloyItem.getAlloyRecipe(stack),worldIn),null);
        this.applyAlloyEnchantments(stack,worldIn);
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!this.isAlloyInit(stack)) {
            this.createAlloyNBT(stack,worldIn,this.defaultComposition,this.defaultAlloyRecipe,null);
            this.initStats(stack,getElementMap(this.defaultComposition,worldIn),getAlloyingRecipe(this.defaultAlloyRecipe,worldIn),null);
            this.applyAlloyEnchantments(stack,worldIn);
        } else if (this.needsRefresh(stack)) {
            this.createAlloyNBT(stack,worldIn,IAlloyItem.getAlloyComposition(stack),IAlloyItem.getAlloyRecipe(stack),null);
            this.initStats(stack,getElementMap(IAlloyItem.getAlloyComposition(stack),worldIn),getAlloyingRecipe(IAlloyItem.getAlloyRecipe(stack),worldIn),null);
        }

        if (!worldIn.isClientSide && isSelected && RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.ENDURE, stack) > 0 && entityIn instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entityIn;
            List<EndCrystal> list = worldIn.getEntitiesOfClass(EndCrystal.class, new AABB(living.blockPosition()).inflate(5, 5, 5));
            for (EndCrystal enderCrystalEntity : list) {
                if (living.tickCount % 100 == 0 && living.getHealth() < living.getMaxHealth()) {
                    enderCrystalEntity.setBeamTarget(living.blockPosition());
                    living.setHealth(living.getHealth() + 1.0F);
                } else {
                    enderCrystalEntity.setBeamTarget(null);
                }
            }
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    // PORT-TODO(init): fillItemCategory is gone â€” dynamic creative-tab variants
    // (AlloyCustomHelper.getItemsFromAlloying/getItemsFromAlloyCrafting when defaultAlloyRecipe == null)
    // must be added in RankineCreativeTabs' displayItems.

    @Override
    public String getDefaultComposition() {
        return this.defaultComposition;
    }

    @Override
    public ResourceLocation getDefaultRecipe() {
        return this.defaultAlloyRecipe;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        if (isAlloyInit(repair) && isAlloyInit(toRepair) && (repair.is(Tags.Items.INGOTS) || repair.getItem() == this)) {
            String s = IAlloyItem.getAlloyComposition(repair);
            String r = IAlloyItem.getAlloyComposition(toRepair);

            String s2 = String.valueOf(IAlloyItem.getAlloyRecipe(repair));
            String r2 = String.valueOf(IAlloyItem.getAlloyRecipe(toRepair));
            return !s.isEmpty() && s.equals(r) && s2.equals(r2);
        }
        return false;
    }

    // 1.18.2 restricted these two enchantments to a single alloy via canEnchant /
    // canApplyAtEnchantingTable. See IAlloyTool#alloyGateAllows for why that moved here.
    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return IAlloyTool.alloyGateAllows(stack, enchantment, false, super.supportsEnchantment(stack, enchantment));
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return IAlloyTool.alloyGateAllows(stack, enchantment, true, super.isPrimaryItemFor(stack, enchantment));
    }
}