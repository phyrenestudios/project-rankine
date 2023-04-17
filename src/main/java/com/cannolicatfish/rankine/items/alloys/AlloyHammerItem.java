package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.tools.HammerItem;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;

import javax.annotation.Nullable;
import java.util.Arrays;
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
        List<Tiers> tiers = Arrays.asList(Tiers.WOOD,Tiers.STONE,Tiers.IRON,Tiers.DIAMOND,Tiers.NETHERITE);
        return TierSortingRegistry.isCorrectTierForDrops(tiers.get(getAlloyHarvestLevel(stack)),state);
    }

    @Override
    public Component getName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return Component.translatable(this.getDescriptionId(stack),Component.translatable(IAlloyItem.getNameOverride(stack)));
        }
        return Component.translatable(this.getDescriptionId(stack),Component.translatable(generateLangFromRecipe(this.defaultAlloyRecipe)));
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getCommandSenderWorld().isRainingAt(target.blockPosition()) && RankineEnchantmentHelper.getLightningAspectEnchantment(stack) > 0) {
            LightningBolt ent = new LightningBolt(EntityType.LIGHTNING_BOLT,attacker.level);
            //ent.moveTo(Vector3d.atBottomCenterOf(new BlockPos(target.getPosX(),target.getPosY(),target.getPosZ())));
            ent.setPos(target.getX(),target.getY(),target.getZ());
            ((ServerLevel)target.getCommandSenderWorld()).addFreshEntity(ent);
        }
        if (RankineEnchantmentHelper.getDazeEnchantment(stack) > 0) {
            if (attacker instanceof Player) {
                Player player = (Player) attacker;
                if (player.getAttackStrengthScale(0) >= (1f)) {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,RankineEnchantmentHelper.getLightningAspectEnchantment(stack)*20, 2));
                } else {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,RankineEnchantmentHelper.getLightningAspectEnchantment(stack)*20, 1));
                }
            } else {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,RankineEnchantmentHelper.getLightningAspectEnchantment(stack)*20, 1));
            }

        }
        stack.hurtAndBreak(calcDurabilityLoss(stack,attacker.getCommandSenderWorld(),attacker,true), attacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        boolean creativeFlag = false;
        if (entityLiving instanceof Player) {
            creativeFlag = ((Player) entityLiving).isCreative();
        }
        if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots && !worldIn.isEmptyBlock(pos) && this.isCorrectToolForDrops(stack,state)) {
            for (CrushingRecipe recipe : worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.CRUSHING)) {
                for (ItemStack s : recipe.getIngredientAsStackList()) {
                    if (s.getItem() == worldIn.getBlockState(pos).getBlock().asItem()) {
                        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
                            stack.hurtAndBreak(calcDurabilityLoss(stack,worldIn,entityLiving,true), entityLiving, (p_220038_0_) -> {
                                p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                            });
                        }

                        if (!creativeFlag) {
                            List<ItemStack> results;
                            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ATOMIZE.get(),stack) > 0) {
                                Tuple<List<ItemStack>,Integer> atomizeResults = recipe.getAtomizeResults(getAlloyTier(stack), worldIn.getRandom(), PeriodicTableUtils.getInstance().getCrushingAmountFromTier(getAlloyTier(stack))  + 1 + EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE,stack));
                                results = atomizeResults.getA();
                                for (int i = 0; i < atomizeResults.getB(); i++) {
                                    worldIn.addFreshEntity(new ExperienceOrb(worldIn, pos.getX(), pos.getY() + 0.5D, pos.getZ(), worldIn.getRandom().nextInt(2) + 1));
                                }
                            } else {
                                results = recipe.getResults(getAlloyTier(stack), worldIn.getRandom(), PeriodicTableUtils.getInstance().getCrushingAmountFromTier(getAlloyTier(stack))  + 1 + EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE,stack));
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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        addAlloyInformation(stack,worldIn,tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,worldIn,tooltip,flagIn);
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

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCrouching() && context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof AnvilBlock) {
            Level worldIn = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState anvil = worldIn.getBlockState(pos);
            if (anvil.getBlock() == Blocks.CHIPPED_ANVIL && (getAlloyDurability(context.getItemInHand()) - context.getItemInHand().getDamageValue()) >= 100) {
                worldIn.setBlock(pos,Blocks.ANVIL.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING,anvil.getValue(HorizontalDirectionalBlock.FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.IRON_GOLEM_REPAIR,SoundSource.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItemInHand().hurtAndBreak(100, context.getPlayer(), (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                return InteractionResult.SUCCESS;
            } else if (anvil.getBlock() == Blocks.DAMAGED_ANVIL && (getAlloyDurability(context.getItemInHand()) - context.getItemInHand().getDamageValue()) >= 100) {
                worldIn.setBlock(pos,Blocks.CHIPPED_ANVIL.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING,anvil.getValue(HorizontalDirectionalBlock.FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.IRON_GOLEM_REPAIR,SoundSource.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItemInHand().hurtAndBreak(100, context.getPlayer(), (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
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

            String s2 = IAlloyItem.getAlloyRecipe(repair).toString();
            String r2 = IAlloyItem.getAlloyRecipe(toRepair).toString();
            return !s.isEmpty() && s.equals(r) && s2.equals(r2);
        }
        return false;
    }
}
