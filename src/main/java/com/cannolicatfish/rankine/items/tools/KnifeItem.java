package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.RankineBerryBushBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class KnifeItem extends SwordItem {
    private final float attackDamage;
    private final float attackSpeed;

    public KnifeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.attackSpeed = attackSpeedIn;
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote) {
            stack.damageItem(1, entityLiving, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }
        if (state.getBlock() == Blocks.DEAD_BUSH || state.getBlock() instanceof RankineBerryBushBlock || state.getBlock() instanceof SweetBerryBushBlock) {
            if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                float f = 0.5F;
                double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, new ItemStack(Items.STICK, 2+random.nextInt(6)));
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
                worldIn.removeBlock(pos,false);
            }
        } else if (state.getBlock() == Blocks.GRASS) {
            if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                float f = 0.5F;
                double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, new ItemStack(Items.GRASS,1));
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
                worldIn.removeBlock(pos,false);
            }
        } else if (state.getBlock() == Blocks.VINE) {
            if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                float f = 0.5F;
                double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, new ItemStack(Items.VINE,1));
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
                worldIn.removeBlock(pos,false);
            }
        } else if (state.getBlock() == Blocks.TALL_GRASS) {
            if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                float f = 0.5F;
                double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, new ItemStack(Items.GRASS,2));
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
                worldIn.removeBlock(pos,false);
            }
        } else if (state.getBlock() == Blocks.FERN) {
            if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                float f = 0.5F;
                double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, new ItemStack(Items.FERN,1));
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
                worldIn.removeBlock(pos,false);
            }
        } else if (state.getBlock() == Blocks.LARGE_FERN) {
            if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                float f = 0.5F;
                double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, new ItemStack(Items.LARGE_FERN,1));
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
                worldIn.removeBlock(pos,false);
            }
        }
        return true;
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity playerentity = context.getPlayer();
        World iworld = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState bs = iworld.getBlockState(blockpos);

        if (bs.getBlock() == Blocks.GRASS_BLOCK && context.getFace() == Direction.UP) {
            iworld.playSound(playerentity, blockpos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
            iworld.setBlockState(blockpos, Blocks.DIRT.getDefaultState(), 2);

            float f = 0.5F;
            double d0 = (double) (context.getWorld().rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double) (context.getWorld().rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double) (context.getWorld().rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(context.getWorld(), (double) blockpos.getX() + d0, (double) blockpos.getY() + d1 + 1, (double) blockpos.getZ() + d2, new ItemStack(Items.GRASS, 1));
            itementity.setDefaultPickupDelay();
            context.getWorld().addEntity(itementity);
            playerentity.getHeldItemMainhand().damageItem(2, playerentity, (p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(context.getHand());
            });
            return ActionResultType.SUCCESS;
        } else if (bs.getBlock() == RankineBlocks.AGED_CHEESE.get()) {
            if (bs.get(BlockStateProperties.BITES_0_6) <= 6) {
                iworld.setBlockState(blockpos, bs.with(BlockStateProperties.BITES_0_6, bs.get(BlockStateProperties.BITES_0_6) + 1));
                playerentity.addItemStackToInventory(new ItemStack(RankineItems.CHEESE.get(), 1));
            } else {
                iworld.removeBlock(blockpos, false);
            }
            playerentity.getHeldItemMainhand().damageItem(1, playerentity, (p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(context.getHand());
            });
            return ActionResultType.SUCCESS;
        } else if (bs.getBlock() == Blocks.CAKE) {
            if (bs.get(BlockStateProperties.BITES_0_6) <= 6) {
                iworld.setBlockState(blockpos, bs.with(BlockStateProperties.BITES_0_6, bs.get(BlockStateProperties.BITES_0_6) + 1));
                playerentity.addItemStackToInventory(new ItemStack(RankineItems.CAKE_SLICE.get(), 1));
            } else {
                iworld.removeBlock(blockpos, false);
            }
            playerentity.getHeldItemMainhand().damageItem(1, playerentity, (p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(context.getHand());
            });
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }



}



