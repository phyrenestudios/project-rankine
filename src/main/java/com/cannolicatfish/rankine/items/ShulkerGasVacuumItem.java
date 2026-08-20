package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.blocks.gases.GasBlock;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineSoundEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;

public class ShulkerGasVacuumItem extends Item {
    public ShulkerGasVacuumItem(Properties properties) {
        super(properties);
    }

    /** was NBT "gas" (absent or "" meant empty) */
    private static String getGas(ItemStack stack) {
        return stack.getOrDefault(RankineDataComponents.GAS.get(), "");
    }

    /**
     * True when the vacuum currently holds a gas — the single read shared by every consumer,
     * including the client-side {@code rankine:gas_held} model predicate, so the icon cannot
     * disagree with {@link #use}/{@link #useOn} about whether the item is full.
     * <p>
     * 1.18.2 asked {@code !stack.getTag().getString("gas").isEmpty()} (ClientProxy.java:88-90 at
     * 859f87773). The fill state now lives in {@link RankineDataComponents#GAS}, which is what
     * {@link #use} writes and {@link #useOn} clears.
     */
    public static boolean holdsGas(ItemStack stack) {
        return !getGas(stack).isEmpty();
    }

    @Override
    public Component getName(ItemStack stack) {
        String s = getGas(stack);
        if (!s.isEmpty()) {
            ResourceLocation rl = ResourceLocation.tryParse(s);
            Block b = rl != null ? BuiltInRegistries.BLOCK.get(rl) : null;
            if (b instanceof GasBlock) {
                return Component.literal(Component.translatable(this.getDescriptionId(stack)).getString() + " (" + Component.translatable(b.getDescriptionId()).getString() + ") ");
            }
        }
        return super.getName(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction opp = context.getClickedFace();
        ItemStack stack = context.getItemInHand();
        if (context.getPlayer() != null && !getGas(stack).isEmpty()) {
            ResourceLocation rl = ResourceLocation.tryParse(getGas(stack));
            Block b = rl != null ? BuiltInRegistries.BLOCK.get(rl) : null;
            if (b instanceof GasBlock && context.getLevel().getBlockState(pos.relative(opp)).getBlock() instanceof AirBlock) {
                if (!worldIn.isClientSide) {
                    worldIn.setBlock(pos.relative(opp),b.defaultBlockState(),3);
                    // was: putString("gas","") + putInt("color",0) — both are now cleared outright
                    stack.remove(RankineDataComponents.GAS.get());
                    stack.remove(RankineDataComponents.COLOR.get());
                }
                context.getPlayer().playSound(RankineSoundEvents.SHULKER_GAS_VACUUM_RELEASE.get(),1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        BlockPos pos = playerIn.isCrouching() ? playerIn.blockPosition() : playerIn.blockPosition().above();
        Block bl = worldIn.getBlockState(pos).getBlock();
        ItemStack stack = playerIn.getItemInHand(handIn);
        InteractionHand other = handIn == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(bl);
        if (bl instanceof GasBlock && blockId != null && getGas(stack).isEmpty()) {
            if (!worldIn.isClientSide) {
                if (playerIn.getItemInHand(other).getItem() == Items.GLASS_BOTTLE) {
                    playerIn.getItemInHand(other).shrink(1);
                    playerIn.getInventory().add(new ItemStack(((GasBlock) bl).getGasBottle()));
                } else {
                    stack.set(RankineDataComponents.GAS.get(), blockId.toString());
                    stack.set(RankineDataComponents.COLOR.get(), ((GasBlock) bl).getColor());
                }
                worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
            }
            playerIn.playSound(RankineSoundEvents.SHULKER_GAS_VACUUM_ABSORB.get(),1.0F, 1.0F);
            return InteractionResultHolder.success(stack);
        }

        return super.use(worldIn, playerIn, handIn);
    }

    /** was NBT "color" (absent or 0 -> white) */
    public static int getColor(ItemStack stack) {
        int color = stack.getOrDefault(RankineDataComponents.COLOR.get(), 0);
        if (color != 0) {
            return color;
        }
        return 16777215;
    }



}
