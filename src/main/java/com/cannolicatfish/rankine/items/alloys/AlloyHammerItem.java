package com.cannolicatfish.rankine.items.alloys;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;

public class AlloyHammerItem extends HammerItem implements IAlloyTool {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;
    public AlloyHammerItem(Tier tier, int attackDamageIn, float attackSpeedIn, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn,  Properties properties) {
        super(attackDamageIn, attackSpeedIn, tier, properties);
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return !state.is(RankineToolMaterials.incorrectBlocksForLevel(getAlloyHarvestLevel(stack)));
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
        // PORT-NOTE: the per-enchantment helper methods are gone (enchantments are data-driven registry entries
        // now); levels are looked up by ResourceKey through the level registry. Note the 1.18.2 code read the
        // LIGHTNING_ASPECT level (not Daze) for the slowness duration inside the Daze branch â€” preserved as-is.
        int lightningLevel = RankineEnchantmentHelper.getLevel(attacker, RankineEnchantments.LIGHTNING_ASPECT, stack);
        if (target.getCommandSenderWorld().isRainingAt(target.blockPosition()) && lightningLevel > 0) {
            LightningBolt ent = new LightningBolt(EntityType.LIGHTNING_BOLT,attacker.level());
            //ent.moveTo(Vector3d.atBottomCenterOf(new BlockPos(target.getPosX(),target.getPosY(),target.getPosZ())));
            ent.setPos(target.getX(),target.getY(),target.getZ());
            ((ServerLevel)target.getCommandSenderWorld()).addFreshEntity(ent);
        }
        if (RankineEnchantmentHelper.getLevel(attacker, RankineEnchantments.DAZE, stack) > 0) {
            if (attacker instanceof Player) {
                Player player = (Player) attacker;
                if (player.getAttackStrengthScale(0) >= (1f)) {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,lightningLevel*20, 2));
                } else {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,lightningLevel*20, 1));
                }
            } else {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,lightningLevel*20, 1));
            }

        }
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(calcDurabilityLoss(stack,attacker.getCommandSenderWorld(),attacker,true), attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        boolean creativeFlag = false;
        if (entityLiving instanceof Player) {
            creativeFlag = ((Player) entityLiving).isCreative();
        }
        if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots && !worldIn.isEmptyBlock(pos) && this.isCorrectToolForDrops(stack,state)) {
            for (RecipeHolder<CrushingRecipe> recipeHolder : worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.CRUSHING.get())) {
                CrushingRecipe recipe = recipeHolder.value();
                for (ItemStack s : recipe.getIngredientAsStackList()) {
                    if (s.getItem() == worldIn.getBlockState(pos).getBlock().asItem()) {
                        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
                            stack.hurtAndBreak(calcDurabilityLoss(stack,worldIn,entityLiving,true), entityLiving, EquipmentSlot.MAINHAND);
                        }

                        if (!creativeFlag) {
                            List<ItemStack> results;
                            if (getEnchantmentLevel(stack, RankineEnchantments.ATOMIZE) > 0) {
                                Tuple<List<ItemStack>,Integer> atomizeResults = recipe.getAtomizeResults(getAlloyTier(stack), worldIn.getRandom(), PeriodicTableUtils.getInstance().getCrushingAmountFromTier(getAlloyTier(stack))  + 1 + getEnchantmentLevel(stack, Enchantments.FORTUNE));
                                results = atomizeResults.getA();
                                for (int i = 0; i < atomizeResults.getB(); i++) {
                                    worldIn.addFreshEntity(new ExperienceOrb(worldIn, pos.getX(), pos.getY() + 0.5D, pos.getZ(), worldIn.getRandom().nextInt(2) + 1));
                                }
                            } else {
                                results = recipe.getResults(getAlloyTier(stack), worldIn.getRandom(), PeriodicTableUtils.getInstance().getCrushingAmountFromTier(getAlloyTier(stack))  + 1 + getEnchantmentLevel(stack, Enchantments.FORTUNE));
                            }
                            for (ItemStack t : results) {
                                Block.popResource(worldIn, pos, t.copy());
                            }

                        }

                        worldIn.destroyBlock(pos,false);
                        return true;

                    }

                }
            }
            SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
            worldIn.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getHitSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
        }
        return false;
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
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCrouching() && context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof AnvilBlock) {
            Level worldIn = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState anvil = worldIn.getBlockState(pos);
            if (anvil.getBlock() == Blocks.CHIPPED_ANVIL && (getAlloyDurability(context.getItemInHand()) - context.getItemInHand().getDamageValue()) >= 100) {
                worldIn.setBlock(pos,Blocks.ANVIL.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING,anvil.getValue(HorizontalDirectionalBlock.FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.IRON_GOLEM_REPAIR,SoundSource.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItemInHand().hurtAndBreak(100, context.getPlayer(), EquipmentSlot.MAINHAND);
                return InteractionResult.SUCCESS;
            } else if (anvil.getBlock() == Blocks.DAMAGED_ANVIL && (getAlloyDurability(context.getItemInHand()) - context.getItemInHand().getDamageValue()) >= 100) {
                worldIn.setBlock(pos,Blocks.CHIPPED_ANVIL.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING,anvil.getValue(HorizontalDirectionalBlock.FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.IRON_GOLEM_REPAIR,SoundSource.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItemInHand().hurtAndBreak(100, context.getPlayer(), EquipmentSlot.MAINHAND);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }

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