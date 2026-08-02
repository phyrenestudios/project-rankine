package com.cannolicatfish.rankine.items.alloys;
import net.minecraft.world.item.ItemStack;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class AlloyPickaxeItem extends PickaxeItem implements IAlloyTool {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;

    public AlloyPickaxeItem(Tier tier, int attackDamageIn, float attackSpeedIn, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn, Item.Properties properties) {
        super(tier, properties.attributes(PickaxeItem.createAttributes(tier, (float) attackDamageIn, attackSpeedIn)));
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    private static int getEnchantmentLevel(ItemStack stack, ResourceKey<Enchantment> key) {
        for (var entry : stack.getTagEnchantments().entrySet()) {
            if (entry.getKey().is(key)) return entry.getIntValue();
        }
        return 0;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public TagKey<Block> getAlloyMineableTag() {
        return BlockTags.MINEABLE_WITH_PICKAXE;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE) && !state.is(RankineToolMaterials.incorrectBlocksForLevel(getAlloyHarvestLevel(stack)));
    }

    @Override
    public float getDestroySpeed(ItemStack p_41004_, BlockState p_41005_) {
        return p_41005_.is(BlockTags.MINEABLE_WITH_PICKAXE) ? getAlloyMiningSpeed(p_41004_) : 1.0F;
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
        stack.hurtAndBreak(calcDurabilityLoss(stack,attacker.getCommandSenderWorld(),attacker,false), attacker, EquipmentSlot.MAINHAND);
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(calcDurabilityLoss(stack,worldIn,entityLiving,true), entityLiving, EquipmentSlot.MAINHAND);
            if (Config.TOOLS.ALLOY_PICKAXE_BONUS.get() && state.is(Tags.Blocks.ORES)) {
                if (getEnchantmentLevel(stack, Enchantments.SILK_TOUCH) <= 0) {
                    // NOTE: 1.21 common tags use the "c" namespace instead of "forge"
                    TagKey<Block> oreTag = state.getTags().filter(blockTagKey -> blockTagKey.location().getNamespace().equals("c") && blockTagKey.location().getPath().startsWith("ores/")).findFirst().orElse(null);
                    if (oreTag != null && oreTag.location().getPath().split("ores/").length > 1) {
                        String oreMetal = oreTag.location().getPath().split("ores/")[1];
                        TagKey<Item> nuggetTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","nuggets/"+oreMetal));
                        Optional<HolderSet.Named<Item>> nuggets = BuiltInRegistries.ITEM.getTag(nuggetTag);
                        if (nuggets.isPresent() && nuggets.get().size() > 0) {
                            if (worldIn.getRandom().nextFloat() > 1/entityLiving.getAttributes().getValue(Attributes.ATTACK_SPEED)) {
                                nuggets.get().stream().findFirst().ifPresent(nuggetItem -> Block.popResource(worldIn, pos, new ItemStack(nuggetItem.value(),Math.max(0,worldIn.random.nextInt((int) Math.round(entityLiving.getAttributes().getValue(Attributes.ATTACK_DAMAGE)))))));
                            }

                        }
                    }
                }

                if (getEnchantmentLevel(stack, RankineEnchantments.ENDLESS) > 0) {
                    List<Holder.Reference<MobEffect>> r = BuiltInRegistries.MOB_EFFECT.holders().filter(holder -> holder.value().isBeneficial() &&
                            holder.key().location().getNamespace().equals("minecraft")).toList();
                    Holder<MobEffect> rand = r.get(worldIn.getRandom().nextInt(r.size()));
                    MobEffectInstance e = new MobEffectInstance(rand,400, getEnchantmentLevel(stack, RankineEnchantments.ENDLESS));
                    entityLiving.addEffect(e);
                }

            }
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