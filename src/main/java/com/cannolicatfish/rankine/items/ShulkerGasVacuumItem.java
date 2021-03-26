package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.blocks.GasBlock;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BlockPos pos = playerIn.isCrouching() ? playerIn.getPosition() : playerIn.getPosition().up();
        Block bl = worldIn.getBlockState(pos).getBlock();
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (bl instanceof GasBlock && bl.getRegistryName() != null && (stack.getTag() == null || stack.getTag().getString("gas").isEmpty())) {
            if (!worldIn.isRemote) {
                stack.getOrCreateTag().putString("gas",bl.getRegistryName().toString());
                stack.getTag().putInt("color",((GasBlock) bl).getGas().getColor());
                worldIn.removeBlock(pos,false);
            }
            playerIn.playSound(SoundEvents.ENTITY_PUFFER_FISH_BLOW_UP,1.0F, 1.0F);
            return ActionResult.resultSuccess(stack);
        } else if (stack.getTag() != null && !stack.getTag().getString("gas").isEmpty()) {
            Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(stack.getTag().getString("gas")));
            if (b instanceof GasBlock && bl instanceof AirBlock) {
                if (!worldIn.isRemote) {
                    worldIn.setBlockState(pos,b.getDefaultState(),3);
                    stack.getTag().putString("gas","");
                    stack.getTag().putInt("color",0);
                }
                playerIn.playSound(SoundEvents.ENTITY_PUFFER_FISH_BLOW_OUT,1.0F, 1.0F);
                return ActionResult.resultSuccess(stack);
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public static int getColor(ItemStack stack) {
        if (stack.getTag() != null && stack.getTag().getInt("color") != 0) {
            return stack.getTag().getInt("color");
        }
        return 16777215;
    }


}
