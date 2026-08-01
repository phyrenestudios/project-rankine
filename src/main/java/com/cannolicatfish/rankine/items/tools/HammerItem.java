package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends DiggerItem {

    public HammerItem(float attackDamageIn, float attackSpeedIn, Tier tier, Properties builder) {
        super(tier, RankineTags.Blocks.HARDENED_GLASS, builder.attributes(DiggerItem.createAttributes(tier, attackDamageIn, attackSpeedIn)));
    }

    protected static int getEnchantmentLevel(ItemStack stack, ResourceKey<Enchantment> key) {
        for (var entry : stack.getTagEnchantments().entrySet()) {
            if (entry.getKey().is(key)) return entry.getIntValue();
        }
        return 0;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return !state.is(this.getTier().getIncorrectBlocksForDrops());
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        boolean creativeFlag = false;
        if (entityLiving instanceof Player)
        {
            creativeFlag = ((Player) entityLiving).isCreative();
        }
        if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots && !worldIn.isEmptyBlock(pos) && this.isCorrectToolForDrops(stack,state)) {
            for (RecipeHolder<CrushingRecipe> recipeHolder : worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.CRUSHING.get())) {
                CrushingRecipe recipe = recipeHolder.value();
                for (ItemStack s : recipe.getIngredientAsStackList().clone()) {
                    if (s.getItem() == worldIn.getBlockState(pos).getBlock().asItem()) {
                        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
                            stack.hurtAndBreak(1, entityLiving, EquipmentSlot.MAINHAND);
                        }
                        double d0 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
                        double d1 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
                        double d2 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;

                        if (!creativeFlag) {
                            List<ItemStack> results = recipe.getResults(getTier(), worldIn.getRandom(), PeriodicTableUtils.getInstance().getCrushingAmountFromTier(getTier()) + 1 + getEnchantmentLevel(stack, Enchantments.FORTUNE));
                            for (ItemStack t : results) {
                                ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, t.copy());
                                itementity.setDefaultPickUpDelay();
                                worldIn.addFreshEntity(itementity);
                            }
                            worldIn.destroyBlock(pos, false);
                            return true;
                        }

                    }

                }
            }
            SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
            worldIn.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getHitSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
        }
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getCommandSenderWorld().isRainingAt(target.blockPosition()) && RankineEnchantmentHelper.getLightningAspectEnchantment(stack) > 0) {
            LightningBolt ent = new LightningBolt(EntityType.LIGHTNING_BOLT,attacker.level());
            ent.setPos(target.getX(),target.getY(),target.getZ());
            target.getCommandSenderWorld().addFreshEntity(ent);
        }
        if (RankineEnchantmentHelper.getDazeEnchantment(stack) > 0) {
            int dazeLevel = RankineEnchantmentHelper.getDazeEnchantment(stack);
            if (attacker instanceof Player) {
                Player player = (Player) attacker;
                if (player.getAttackStrengthScale(0) >= (1f)) {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,dazeLevel*10, dazeLevel*2));
                } else {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,dazeLevel*10, 1));
                }
            } else {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,dazeLevel*10, 1));
            }

        }
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    public void getExcavationResult(BlockPos posIn, Level levelIn, Player player, ItemStack stack) {
        BlockHitResult raytraceresult = getPlayerPOVHitResult(levelIn, player, ClipContext.Fluid.ANY);
        if (RankineEnchantmentHelper.getExcavateEnchantment(stack) <= 0) return;
        for (BlockPos p : evacuationArea(posIn, raytraceresult.getDirection(), RankineEnchantmentHelper.getExcavateEnchantment(stack))) {
            mineBlock(stack, levelIn, levelIn.getBlockState(p), p, player);
        }
    }

    private static List<BlockPos> evacuationArea(BlockPos posIn, Direction dirIn, int radius) {
        List<BlockPos> blockList = new ArrayList<>();
        if (dirIn.getAxis().isHorizontal()) {
            for (BlockPos p : BlockPos.betweenClosed(posIn.relative(dirIn.getClockWise(),radius).relative(Direction.UP,radius), posIn.relative(dirIn.getCounterClockWise(),radius).relative(Direction.DOWN,radius))) {
                if (p.distToCenterSqr(posIn.getX()+0.5D, posIn.getY()+0.5D, posIn.getZ()+0.5D) <= Math.pow(radius-0.5*(radius-1),2)) blockList.add(p.immutable());
            }
        } else {
            for (BlockPos p : BlockPos.betweenClosed(posIn.relative(Direction.NORTH,radius).relative(Direction.WEST,radius), posIn.relative(Direction.SOUTH,radius).relative(Direction.EAST,radius))) {
                if (p.distToCenterSqr(posIn.getX()+0.5D, posIn.getY()+0.5D, posIn.getZ()+0.5D) <= Math.pow(radius-0.5*(radius-1),2)) blockList.add(p.immutable());
            }
        }

        return blockList;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.EFFICIENCY) || enchantment.is(Enchantments.SILK_TOUCH)) {
            return false;
        }
        return super.supportsEnchantment(stack,enchantment);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCrouching() && context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof AnvilBlock) {
            Level worldIn = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState anvil = worldIn.getBlockState(pos);
            if (anvil.getBlock() == Blocks.CHIPPED_ANVIL && (context.getItemInHand().getMaxDamage() - context.getItemInHand().getDamageValue()) >= 100) {
                worldIn.setBlock(pos,Blocks.ANVIL.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING,anvil.getValue(HorizontalDirectionalBlock.FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.IRON_GOLEM_REPAIR,SoundSource.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItemInHand().hurtAndBreak(100, context.getPlayer(), EquipmentSlot.MAINHAND);
                return InteractionResult.SUCCESS;
            } else if (anvil.getBlock() == Blocks.DAMAGED_ANVIL && (context.getItemInHand().getMaxDamage() - context.getItemInHand().getDamageValue()) >= 100) {
                worldIn.setBlock(pos,Blocks.CHIPPED_ANVIL.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING,anvil.getValue(HorizontalDirectionalBlock.FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.IRON_GOLEM_REPAIR,SoundSource.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItemInHand().hurtAndBreak(100, context.getPlayer(), EquipmentSlot.MAINHAND);
                return InteractionResult.SUCCESS;
            }
        } else if (context.getPlayer() != null && context.getLevel().getBlockState(context.getClickedPos()).getBlock().equals(RankineBlocks.GEODE.get())) {
            Level worldIn = context.getLevel();
            if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots) {
                Block randomGeode = RankineLists.GEODES.get(worldIn.getRandom().nextInt(RankineLists.GEODES.size()));
                BlockPos pos = context.getClickedPos();
                double d0 = (double) (worldIn.getRandom().nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.getRandom().nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.getRandom().nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(randomGeode));
                itementity.setDefaultPickUpDelay();
                worldIn.addFreshEntity(itementity);
                worldIn.destroyBlock(context.getClickedPos(), false);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }
}
