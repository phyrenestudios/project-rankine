package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineSoundEvents;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.Item.Properties;

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

    @Override
    public Component getName(ItemStack stack) {
        if (stack.getTag() != null){
            String s = stack.getTag().getString("gas");
            Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s));
            if (!s.isEmpty() && b instanceof GasBlock) {
                return new TextComponent(new TranslatableComponent(this.getDescriptionId(stack)).getString() + " (" + new TranslatableComponent(b.getDescriptionId()).getString() + ") ");
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
        if (context.getPlayer() != null && (stack.getTag() != null && !stack.getTag().getString("gas").isEmpty())) {
            Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(stack.getTag().getString("gas")));
            if (b instanceof GasBlock && context.getLevel().getBlockState(pos.relative(opp)).getBlock() instanceof AirBlock) {
                if (!worldIn.isClientSide) {
                    worldIn.setBlock(pos.relative(opp),b.defaultBlockState(),3);
                    stack.getTag().putString("gas","");
                    stack.getTag().putInt("color",0);
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
        if (bl instanceof GasBlock && bl.getRegistryName() != null && (stack.getTag() == null || stack.getTag().getString("gas").isEmpty())) {
            if (!worldIn.isClientSide) {
                if (playerIn.getItemInHand(other).getItem() == Items.GLASS_BOTTLE) {
                    playerIn.getItemInHand(other).shrink(1);
                    playerIn.getInventory().add(new ItemStack(getGasBottle(((GasBlock) bl).getGasEnum())));
                } else {
                    stack.getOrCreateTag().putString("gas",bl.getRegistryName().toString());
                    stack.getTag().putInt("color",((GasBlock) bl).getGas().getColor());
                }
                worldIn.removeBlock(pos,false);
            }
            playerIn.playSound(RankineSoundEvents.SHULKER_GAS_VACUUM_ABSORB.get(),1.0F, 1.0F);
            return InteractionResultHolder.success(stack);
        }

        return super.use(worldIn, playerIn, handIn);
    }

    public static int getColor(ItemStack stack) {
        if (stack.getTag() != null && stack.getTag().getInt("color") != 0) {
            return stack.getTag().getInt("color");
        }
        return 16777215;
    }

    public Item getGasBottle(GasUtilsEnum gas) {
        switch (gas) {
            case HYDROGEN:
                return RankineItems.HYDROGEN_GAS_BOTTLE.get();
            case HELIUM:
                return RankineItems.HELIUM_GAS_BOTTLE.get();
            case NITROGEN:
                return RankineItems.NITROGEN_GAS_BOTTLE.get();
            case OXYGEN:
                return RankineItems.OXYGEN_GAS_BOTTLE.get();
            case FLUORINE:
                return RankineItems.FLUORINE_GAS_BOTTLE.get();
            case NEON:
                return RankineItems.NEON_GAS_BOTTLE.get();
            case CHLORINE:
                return RankineItems.CHLORINE_GAS_BOTTLE.get();
            case ARGON:
                return RankineItems.ARGON_GAS_BOTTLE.get();
            case KRYPTON:
                return RankineItems.KRYPTON_GAS_BOTTLE.get();
            case XENON:
                return RankineItems.XENON_GAS_BOTTLE.get();
            case RADON:
                return RankineItems.RADON_GAS_BOTTLE.get();
            case OGANESSON:
                return RankineItems.OGANESSON_GAS_BOTTLE.get();
            case AMMONIA:
                return RankineItems.AMMONIA_GAS_BOTTLE.get();
            case CARBON_DIOXIDE:
                return RankineItems.CARBON_DIOXIDE_GAS_BOTTLE.get();
            case HYDROGEN_CHLORIDE:
                return RankineItems.HYDROGEN_CHLORIDE_GAS_BOTTLE.get();
            case HYDROGEN_FLUORIDE:
                return RankineItems.HYDROGEN_FLUORIDE_GAS_BOTTLE.get();
            case HYDROGEN_SULFIDE:
                return RankineItems.HYDROGEN_SULFIDE_GAS_BOTTLE.get();
            case SULFUR_DIOXIDE:
                return RankineItems.SULFUR_DIOXIDE_GAS_BOTTLE.get();
            default:
                return Items.GLASS_BOTTLE;
        }
    }


}
