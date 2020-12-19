package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.ModEnchantments;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.recipe.PistonCrusherRecipes;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Set;

public class ItemHammer extends ToolItem {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.STONE, Blocks.COBBLESTONE, Blocks.SMOOTH_STONE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, ModBlocks.RED_GRANITE, ModBlocks.HORNBLENDE_ANDESITE, ModBlocks.GRANODIORITE, ModBlocks.LIMESTONE, ModBlocks.THOLEIITIC_BASALT, ModBlocks.RHYOLITE,
            ModBlocks.GNEISS, ModBlocks.MARBLE, ModBlocks.SHALE, ModBlocks.IRONSTONE, ModBlocks.ANORTHOSITE, ModBlocks.MAGNETITE_ORE, ModBlocks.MALACHITE_ORE, ModBlocks.BAUXITE_ORE, ModBlocks.CASSITERITE_ORE, ModBlocks.SPHALERITE_ORE, ModBlocks.CINNABAR_ORE, ModBlocks.PENTLANDITE_ORE,
            ModBlocks.LIGNITE_ORE, ModBlocks.SUBBITUMINOUS_ORE, ModBlocks.BITUMINOUS_ORE, ModBlocks.METEORITE);


    public ItemHammer(float attackDamageIn, float attackSpeedIn, IItemTier tier, Properties builder) {
        super(attackDamageIn, attackSpeedIn, tier, EFFECTIVE_ON, builder);
    }


    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if(PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getValue()[0] > 0f && this.getTier().getHarvestLevel() >= state.getBlock().getHarvestLevel(state))
        {
            if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
                stack.damageItem(1, entityLiving, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }
            if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                float f = 0.5F;
                double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                Item item;
                int val;

                if (getBlastModifier(stack) > 0)
                {
                    BlockRayTraceResult raytraceresult = rayTrace(worldIn, (PlayerEntity) entityLiving, RayTraceContext.FluidMode.ANY);

                    BlockPos exppos;
                    switch (raytraceresult.getFace())
                    {
                        case EAST:
                            exppos = new BlockPos(pos.getX() - (2 + getBlastModifier(stack)),pos.getY(),pos.getZ());
                            break;
                        case WEST:
                            exppos = new BlockPos(pos.getX() + (2 + getBlastModifier(stack)),pos.getY(),pos.getZ());
                            break;
                        case DOWN:
                            exppos = new BlockPos(pos.getX(),pos.getY() + (2 + getBlastModifier(stack)),pos.getZ());
                            break;
                        case UP:
                            exppos = new BlockPos(pos.getX(),pos.getY() - (2 + getBlastModifier(stack)),pos.getZ());
                            break;
                        case NORTH:
                            exppos = new BlockPos(pos.getX(),pos.getY(),pos.getZ() + (2 + getBlastModifier(stack)));
                            break;
                        case SOUTH:
                            exppos = new BlockPos(pos.getX(),pos.getY(),pos.getZ() - (2 + getBlastModifier(stack)));
                            break;
                        default:
                            exppos = pos;
                    }
                    System.out.println(raytraceresult.getFace());
                    System.out.println(exppos);
                    worldIn.removeBlock(exppos,false);
                    worldIn.createExplosion(null, exppos.getX(), exppos.getY() + 16 * .0625D, exppos.getZ(), 0.5F + getBlastModifier(stack), Explosion.Mode.DESTROY);
                    if (state.getBlockHardness(worldIn, pos) != 0.0F) {
                        stack.damageItem(getBlastModifier(stack), entityLiving, (p_220038_0_) -> {
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

    public boolean canHarvestBlock(BlockState blockIn) {
        Block block = blockIn.getBlock();
        return block == ModBlocks.LIMESTONE;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getEntityWorld().isRainingAt(target.getPosition()) && getLightningModifier(stack) == 1)
        {
            LightningBoltEntity ent = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,attacker.world);
            //ent.func_233576_c_(Vector3d.func_237492_c_(new BlockPos(target.getPosX(),target.getPosY(),target.getPosZ())));
            ent.setPosition(target.getPosX(),target.getPosY(),target.getPosZ());
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
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity && isSelected)
        {
            PlayerEntity player = (PlayerEntity) entityIn;
            if (player.swingingHand == Hand.OFF_HAND)
            {
                player.resetCooldown();
                player.swingingHand = Hand.MAIN_HAND;
            }
        }
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity)
    {
        if (entity instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) entity;
            World worldIn = player.getEntityWorld();
            RayTraceResult raytraceresult = rayTrace(worldIn, player, RayTraceContext.FluidMode.ANY);
            BlockPos pos;
            if (raytraceresult instanceof BlockRayTraceResult)
            {
                final BlockRayTraceResult rayTraceResult = (BlockRayTraceResult) raytraceresult;
                pos = rayTraceResult.getPos();
            } else
            {
                pos = new BlockPos(raytraceresult.getHitVec().x,raytraceresult.getHitVec().y,raytraceresult.getHitVec().z);
            }
            if (player.getCooledAttackStrength(0) >= (1f - .15*getSwingModifier(stack)))
            {
                player.resetCooldown();
                onBlockDestroyed(stack,worldIn,worldIn.getBlockState(pos),pos, player);
            }
        }
        return false;
    }

    public static int getBlastModifier(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(ModEnchantments.BLAST, stack);
    }

    public static int getAtomizeModifier(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(ModEnchantments.ATOMIZE, stack);
    }

    public static int getLightningModifier(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(ModEnchantments.LIGHTNING_ASPECT, stack);
    }

    public static int getSwingModifier(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SWING, stack);
    }

    public static int getDazeModifier(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(ModEnchantments.DAZE, stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == ModEnchantments.BLAST || enchantment == ModEnchantments.ATOMIZE || enchantment == ModEnchantments.LIGHTNING_ASPECT || enchantment == ModEnchantments.SWING || enchantment == ModEnchantments.DAZE || enchantment == Enchantments.UNBREAKING)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (itemstack.getDamage() >= itemstack.getMaxDamage()) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (this.getUseDuration(stack) - count == 13)
        {
            BlockPos pos = player.func_233580_cy_();
            player.getEntityWorld().playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS,0.4F, 1.0F, false);
        }
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        entityLiving.swing(Hand.MAIN_HAND,false);
        RayTraceResult raytraceresult = rayTrace(worldIn, (PlayerEntity) entityLiving, RayTraceContext.FluidMode.ANY);
        BlockPos pos;
        if (raytraceresult instanceof BlockRayTraceResult)
        {
            final BlockRayTraceResult rayTraceResult = (BlockRayTraceResult) raytraceresult;
            pos = rayTraceResult.getPos();
        } else
        {
            pos = new BlockPos(raytraceresult.getHitVec().x,raytraceresult.getHitVec().y,raytraceresult.getHitVec().z);
        }
        System.out.println(this.getUseDuration(stack));
        System.out.println(timeLeft);
        System.out.println(this.getUseDuration(stack) - timeLeft);
        if (this.getUseDuration(stack) - timeLeft >= 13)
        {
            worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS,1.0F, 1.0F, false);
            onBlockDestroyed(stack,worldIn,worldIn.getBlockState(pos),pos, entityLiving);
        } else
        {
            worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS,1.0F, 4.0F, false);
            worldIn.sendBlockBreakProgress(-1,pos,1);
        }

    }*/
}
