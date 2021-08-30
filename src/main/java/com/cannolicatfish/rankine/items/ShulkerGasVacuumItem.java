package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class ShulkerGasVacuumItem extends Item {
    public ShulkerGasVacuumItem(Properties properties) {
        super(properties);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (stack.getTag() != null){
            String s = stack.getTag().getString("gas");
            Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s));
            if (!s.isEmpty() && b instanceof GasBlock) {
                return new StringTextComponent(new TranslationTextComponent(this.getTranslationKey(stack)).getString() + " (" + new TranslationTextComponent(b.getTranslationKey()).getString() + ") ");
            }
        }
        return super.getDisplayName(stack);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();
        Direction opp = context.getFace();
        ItemStack stack = context.getItem();
        if (context.getPlayer() != null && (stack.getTag() != null && !stack.getTag().getString("gas").isEmpty())) {
            Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(stack.getTag().getString("gas")));
            if (b instanceof GasBlock && context.getWorld().getBlockState(pos.offset(opp)).getBlock() instanceof AirBlock) {
                if (!worldIn.isRemote) {
                    worldIn.setBlockState(pos.offset(opp),b.getDefaultState(),3);
                    stack.getTag().putString("gas","");
                    stack.getTag().putInt("color",0);
                }
                context.getPlayer().playSound(SoundEvents.ENTITY_PUFFER_FISH_BLOW_OUT,1.0F, 1.0F);
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.PASS;
        } else {
            return ActionResultType.PASS;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BlockPos pos = playerIn.isCrouching() ? playerIn.getPosition() : playerIn.getPosition().up();
        Block bl = worldIn.getBlockState(pos).getBlock();
        ItemStack stack = playerIn.getHeldItem(handIn);
        Hand other = handIn == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
        if (bl instanceof GasBlock && bl.getRegistryName() != null && (stack.getTag() == null || stack.getTag().getString("gas").isEmpty())) {
            if (!worldIn.isRemote) {
                if (playerIn.getHeldItem(other).getItem() == Items.GLASS_BOTTLE) {
                    playerIn.getHeldItem(other).shrink(1);
                    playerIn.inventory.addItemStackToInventory(new ItemStack(getGasBottle(((GasBlock) bl).getGasEnum())));
                } else {
                    stack.getOrCreateTag().putString("gas",bl.getRegistryName().toString());
                    stack.getTag().putInt("color",((GasBlock) bl).getGas().getColor());
                }
                worldIn.removeBlock(pos,false);
            }
            playerIn.playSound(SoundEvents.ENTITY_PUFFER_FISH_BLOW_UP,1.0F, 1.0F);
            return ActionResult.resultSuccess(stack);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
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
            default:
                return Items.GLASS_BOTTLE;
        }
    }


}
