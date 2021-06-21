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

public class HammerItem extends ToolItem {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.STONE, Blocks.COBBLESTONE, Blocks.SMOOTH_STONE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, RankineBlocks.GRAY_GRANITE.get(), RankineBlocks.HORNBLENDE_ANDESITE.get(), RankineBlocks.GRANODIORITE.get(), RankineBlocks.TUFA_LIMESTONE.get(), RankineBlocks.THOLEIITIC_BASALT.get(), RankineBlocks.RHYOLITE.get(),
            RankineBlocks.GNEISS.get(), RankineBlocks.WHITE_MARBLE.get(), RankineBlocks.CARBONACEOUS_SHALE.get(), RankineBlocks.IRONSTONE.get(), RankineBlocks.ANORTHOSITE.get(), RankineBlocks.MAGNETITE_ORE.get(), RankineBlocks.MALACHITE_ORE.get(), RankineBlocks.BAUXITE_ORE.get(), RankineBlocks.CASSITERITE_ORE.get(), RankineBlocks.SPHALERITE_ORE.get(), RankineBlocks.CINNABAR_ORE.get(), RankineBlocks.PENTLANDITE_ORE.get(),
            RankineBlocks.LIGNITE_ORE.get(), RankineBlocks.SUBBITUMINOUS_ORE.get(), RankineBlocks.BITUMINOUS_ORE.get(), RankineBlocks.METEORITE.get());


    public HammerItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Properties builder) {
        super(attackDamageIn, attackSpeedIn, tier, EFFECTIVE_ON, builder);
    }


    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        boolean creativeFlag = false;
        if (entityLiving instanceof PlayerEntity)
        {
            creativeFlag = ((PlayerEntity) entityLiving).isCreative();
        }
        if (!worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots && !worldIn.isAirBlock(pos)) {
            for (CrushingRecipe recipe : worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.CRUSHING)) {
                for (ItemStack s : recipe.getIngredientAsStackList().clone()) {
                    if (s.getItem() == worldIn.getBlockState(pos).getBlock().asItem() && this.getTier().getHarvestLevel() >= state.getBlock().getHarvestLevel(state)) {
                        if (state.getBlockHardness(worldIn, pos) != 0.0F) {
                            stack.damageItem(1, entityLiving, (p_220038_0_) -> {
                                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                            });
                        }
                        double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                        double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                        double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;

                        if (!creativeFlag) {
                            List<ItemStack> results = recipe.getResults(getTier().getHarvestLevel(), worldIn);
                            if (getAtomizeModifier(stack) >= 1) {
                                for (int i = 0; i < results.size(); i++) {
                                    if (results.get(i).isEmpty()) {
                                        ItemStack resu = recipe.getSpecificResult(getTier().getHarvestLevel(),i,worldIn);
                                        results.set(i,resu);
                                    }
                                }
                            }
                            for (ItemStack t : results) {
                                ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, t.copy());
                                itementity.setDefaultPickupDelay();
                                worldIn.addEntity(itementity);
                            }
                            worldIn.destroyBlock(pos, false);
                            return true;
                        }

                    }

                }
            }
            SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
            worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getHitSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
        }
        return false;
    }

    public boolean canHarvestBlock(BlockState blockIn) {
        int i = this.getTier().getHarvestLevel();
        return i >= blockIn.getHarvestLevel();
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getEntityWorld().isRainingAt(target.getPosition()) && getLightningModifier(stack) == 1)
        {
            LightningBoltEntity ent = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,attacker.world);
            //ent.func_233576_c_(Vector3d.func_237492_c_(new BlockPos(target.getPosX(),target.getPosY(),target.getPosZ())));
            ent.setPosition(target.getPosX(),target.getPosY(),target.getPosZ());
            target.getEntityWorld().addEntity(ent);
        }
        if (getDazeModifier(stack) != 0)
        {
            if (attacker instanceof PlayerEntity)
            {
                PlayerEntity player = (PlayerEntity) attacker;
                if (player.getCooledAttackStrength(0) >= (1f))
                {
                    target.addPotionEffect(new EffectInstance(Effects.SLOWNESS,getDazeModifier(stack)*10, getDazeModifier(stack)*2));
                } else {
                    target.addPotionEffect(new EffectInstance(Effects.SLOWNESS,getDazeModifier(stack)*10, 1));
                }
            } else {
                target.addPotionEffect(new EffectInstance(Effects.SLOWNESS,getDazeModifier(stack)*10, 1));
            }

        }
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    public void getExcavationResult(BlockPos pos, World worldIn, PlayerEntity player, ItemStack stack) {
        BlockRayTraceResult raytraceresult = rayTrace(worldIn, player, RayTraceContext.FluidMode.ANY);
        List<BlockPos> positions = new ArrayList<>();
        if (getExcavateModifier(stack) == 1)
        {
            switch (raytraceresult.getFace())
            {
                case EAST:
                case WEST:
                    positions.addAll(Arrays.asList(pos,pos.north(),pos.south(),pos.up(),pos.down()));
                    break;
                case DOWN:
                case UP:
                    positions.addAll(Arrays.asList(pos,pos.north(),pos.south(),pos.west(),pos.east()));
                    break;
                case NORTH:
                case SOUTH:
                    positions.addAll(Arrays.asList(pos,pos.east(),pos.west(),pos.up(),pos.down()));
                    break;
                default:
                    positions.add(pos);
            }

        } else {
            switch (raytraceresult.getFace())
            {
                case EAST:
                case WEST:
                    positions.addAll(Arrays.asList(pos,pos.north(),pos.south(),pos.up(),pos.down(),pos.north().up(),pos.south().up(),pos.north().down(),pos.south().down()));
                    break;
                case DOWN:
                case UP:
                    positions.addAll(Arrays.asList(pos,pos.north(),pos.south(),pos.west(),pos.east(),pos.north().east(),pos.south().east(),pos.north().west(),pos.south().west()));
                    break;
                case NORTH:
                case SOUTH:
                    positions.addAll(Arrays.asList(pos,pos.east(),pos.west(),pos.up(),pos.down(),pos.up().east(),pos.down().east(),pos.up().west(),pos.down().west()));
                    break;
                default:
                    positions.add(pos);
            }
        }
        for (BlockPos p: positions)
        {
            onBlockDestroyed(stack,worldIn,worldIn.getBlockState(p),p, player);
        }
    }


    public static int getLightningModifier(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.LIGHTNING_ASPECT, stack);
    }

    public static int getDazeModifier(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.DAZE, stack);
    }

    public static int getExcavateModifier(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.EXCAVATE, stack);
    }

    public static int getAtomizeModifier(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ATOMIZE, stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.EFFICIENCY || enchantment == Enchantments.SILK_TOUCH || enchantment == Enchantments.FORTUNE ) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack,enchantment);
    }


    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCrouching() && context.getWorld().getBlockState(context.getPos()).getBlock() instanceof AnvilBlock) {
            World worldIn = context.getWorld();
            BlockPos pos = context.getPos();
            BlockState anvil = worldIn.getBlockState(pos);
            if (anvil.getBlock() == Blocks.CHIPPED_ANVIL && (context.getItem().getMaxDamage() - context.getItem().getDamage()) >= 100) {
                worldIn.setBlockState(pos,Blocks.ANVIL.getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING,anvil.get(HorizontalBlock.HORIZONTAL_FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.ENTITY_IRON_GOLEM_REPAIR,SoundCategory.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItem().damageItem(100, context.getPlayer(), (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                return ActionResultType.SUCCESS;
            } else if (anvil.getBlock() == Blocks.DAMAGED_ANVIL && (context.getItem().getMaxDamage() - context.getItem().getDamage()) >= 100) {
                worldIn.setBlockState(pos,Blocks.CHIPPED_ANVIL.getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING,anvil.get(HorizontalBlock.HORIZONTAL_FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.ENTITY_IRON_GOLEM_REPAIR,SoundCategory.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItem().damageItem(100, context.getPlayer(), (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                return ActionResultType.SUCCESS;
            }
        }
        return super.onItemUse(context);
    }
}
