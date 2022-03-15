package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.*;

import net.minecraft.item.Item.Properties;

public class HammerItem extends ToolItem {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.STONE, Blocks.COBBLESTONE, Blocks.SMOOTH_STONE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, RankineBlocks.GRAY_GRANITE.get(), RankineBlocks.HORNBLENDE_ANDESITE.get(), RankineBlocks.GRANODIORITE.get(), RankineBlocks.LIMESTONE.get(), RankineBlocks.THOLEIITIC_BASALT.get(), RankineBlocks.RHYOLITE.get(),
            RankineBlocks.GNEISS.get(), RankineBlocks.WHITE_MARBLE.get(), RankineBlocks.SHALE.get(), RankineBlocks.IRONSTONE.get(), RankineBlocks.ANORTHOSITE.get(), RankineBlocks.MAGNETITE_ORE.get(), RankineBlocks.MALACHITE_ORE.get(), RankineBlocks.BAUXITE_ORE.get(), RankineBlocks.CASSITERITE_ORE.get(), RankineBlocks.SPHALERITE_ORE.get(), RankineBlocks.CINNABAR_ORE.get(), RankineBlocks.PENTLANDITE_ORE.get(),
            RankineBlocks.LIGNITE_ORE.get(), RankineBlocks.SUBBITUMINOUS_ORE.get(), RankineBlocks.BITUMINOUS_ORE.get(), RankineBlocks.METEORITE.get());


    public HammerItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Properties builder) {
        super(attackDamageIn, attackSpeedIn, tier, EFFECTIVE_ON, builder);
    }


    @Override
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        boolean creativeFlag = false;
        if (entityLiving instanceof PlayerEntity)
        {
            creativeFlag = ((PlayerEntity) entityLiving).isCreative();
        }
        if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots && !worldIn.isEmptyBlock(pos)) {
            for (CrushingRecipe recipe : worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.CRUSHING)) {
                for (ItemStack s : recipe.getIngredientAsStackList().clone()) {
                    if (s.getItem() == worldIn.getBlockState(pos).getBlock().asItem() && this.getTier().getLevel() >= state.getBlock().getHarvestLevel(state)) {
                        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
                            stack.hurtAndBreak(1, entityLiving, (p_220038_0_) -> {
                                p_220038_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
                            });
                        }
                        double d0 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
                        double d1 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
                        double d2 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;

                        if (!creativeFlag) {
                            List<ItemStack> results = recipe.getResults(getTier().getLevel(), worldIn);
                            if (getAtomizeModifier(stack) >= 1) {
                                for (int i = 0; i < results.size(); i++) {
                                    if (results.get(i).isEmpty()) {
                                        ItemStack resu = recipe.getSpecificResult(getTier().getLevel(),i,worldIn);
                                        results.set(i,resu);
                                    }
                                }
                            }
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
            worldIn.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getHitSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
        }
        return false;
    }

    public boolean isCorrectToolForDrops(BlockState blockIn) {
        int i = this.getTier().getLevel();
        return i >= blockIn.getHarvestLevel();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getCommandSenderWorld().isRainingAt(target.blockPosition()) && getLightningModifier(stack) == 1)
        {
            LightningBoltEntity ent = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,attacker.level);
            //ent.moveTo(Vector3d.atBottomCenterOf(new BlockPos(target.getPosX(),target.getPosY(),target.getPosZ())));
            ent.setPos(target.getX(),target.getY(),target.getZ());
            target.getCommandSenderWorld().addFreshEntity(ent);
        }
        if (getDazeModifier(stack) != 0)
        {
            if (attacker instanceof PlayerEntity)
            {
                PlayerEntity player = (PlayerEntity) attacker;
                if (player.getAttackStrengthScale(0) >= (1f))
                {
                    target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN,getDazeModifier(stack)*10, getDazeModifier(stack)*2));
                } else {
                    target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN,getDazeModifier(stack)*10, 1));
                }
            } else {
                target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN,getDazeModifier(stack)*10, 1));
            }

        }
        stack.hurtAndBreak(1, attacker, (p_220045_0_) -> {
            p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    public void getExcavationResult(BlockPos pos, World worldIn, PlayerEntity player, ItemStack stack) {
        BlockRayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, player, RayTraceContext.FluidMode.ANY);
        List<BlockPos> positions = new ArrayList<>();
        if (getExcavateModifier(stack) == 1)
        {
            switch (raytraceresult.getDirection())
            {
                case EAST:
                case WEST:
                    positions.addAll(Arrays.asList(pos,pos.north(),pos.south(),pos.above(),pos.below()));
                    break;
                case DOWN:
                case UP:
                    positions.addAll(Arrays.asList(pos,pos.north(),pos.south(),pos.west(),pos.east()));
                    break;
                case NORTH:
                case SOUTH:
                    positions.addAll(Arrays.asList(pos,pos.east(),pos.west(),pos.above(),pos.below()));
                    break;
                default:
                    positions.add(pos);
            }

        } else {
            switch (raytraceresult.getDirection())
            {
                case EAST:
                case WEST:
                    positions.addAll(Arrays.asList(pos,pos.north(),pos.south(),pos.above(),pos.below(),pos.north().above(),pos.south().above(),pos.north().below(),pos.south().below()));
                    break;
                case DOWN:
                case UP:
                    positions.addAll(Arrays.asList(pos,pos.north(),pos.south(),pos.west(),pos.east(),pos.north().east(),pos.south().east(),pos.north().west(),pos.south().west()));
                    break;
                case NORTH:
                case SOUTH:
                    positions.addAll(Arrays.asList(pos,pos.east(),pos.west(),pos.above(),pos.below(),pos.above().east(),pos.below().east(),pos.above().west(),pos.below().west()));
                    break;
                default:
                    positions.add(pos);
            }
        }
        for (BlockPos p: positions)
        {
            mineBlock(stack,worldIn,worldIn.getBlockState(p),p, player);
        }
    }


    public static int getLightningModifier(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.LIGHTNING_ASPECT, stack);
    }

    public static int getDazeModifier(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.DAZE, stack);
    }

    public static int getExcavateModifier(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.EXCAVATE, stack);
    }

    public static int getAtomizeModifier(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ATOMIZE, stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.BLOCK_EFFICIENCY || enchantment == Enchantments.SILK_TOUCH || enchantment == Enchantments.BLOCK_FORTUNE ) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack,enchantment);
    }


    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCrouching() && context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof AnvilBlock) {
            World worldIn = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState anvil = worldIn.getBlockState(pos);
            if (anvil.getBlock() == Blocks.CHIPPED_ANVIL && (context.getItemInHand().getMaxDamage() - context.getItemInHand().getDamageValue()) >= 100) {
                worldIn.setBlock(pos,Blocks.ANVIL.defaultBlockState().setValue(HorizontalBlock.FACING,anvil.getValue(HorizontalBlock.FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.IRON_GOLEM_REPAIR,SoundCategory.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItemInHand().hurtAndBreak(100, context.getPlayer(), (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
                return ActionResultType.SUCCESS;
            } else if (anvil.getBlock() == Blocks.DAMAGED_ANVIL && (context.getItemInHand().getMaxDamage() - context.getItemInHand().getDamageValue()) >= 100) {
                worldIn.setBlock(pos,Blocks.CHIPPED_ANVIL.defaultBlockState().setValue(HorizontalBlock.FACING,anvil.getValue(HorizontalBlock.FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.IRON_GOLEM_REPAIR,SoundCategory.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItemInHand().hurtAndBreak(100, context.getPlayer(), (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
                return ActionResultType.SUCCESS;
            }
        }
        return super.useOn(context);
    }
}
