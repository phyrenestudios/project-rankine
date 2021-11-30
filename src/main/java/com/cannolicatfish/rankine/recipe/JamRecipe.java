package com.cannolicatfish.rankine.recipe;


import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class JamRecipe extends SpecialRecipe {
    public static final SpecialRecipeSerializer<JamRecipe> SERIALIZER = new SpecialRecipeSerializer<>(JamRecipe::new);
    public JamRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    public boolean matches(CraftingInventory inv, World worldIn) {
        boolean flag = false;
        boolean flag2 = false;
        boolean flag3 = false;
        int berryCount = 0;
        int sugarCount = 0;
        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == Items.SUGAR && !flag) {
                    sugarCount++;
                    if (sugarCount == 2) {
                        flag = true;
                    }
                } else if (itemstack.getItem().isIn(RankineTags.Items.BERRIES) && !flag2) {
                    berryCount++;
                    if (berryCount == 6) {
                        flag2 = true;
                    }
                } else {
                    if (itemstack.getItem() != Items.GLASS_BOTTLE || flag3) {
                        return false;
                    }

                    flag3 = true;
                }
            }
        }

        return flag && flag2 && flag3;
    }

    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack itemstack = ItemStack.EMPTY;

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack1 = inv.getStackInSlot(i);
            if (!itemstack1.isEmpty() && itemstack1.getItem().isIn(RankineTags.Items.BERRIES)) {
                itemstack = itemstack1;
                break;
            }
        }

        ItemStack itemstack2 = new ItemStack(RankineItems.FRUIT_JAM.get(), 1);
        /*
        if (itemstack.getItem() instanceof BlockItem && ((BlockItem)itemstack.getItem()).getBlock() instanceof FlowerBlock) {
            FlowerBlock flowerblock = (FlowerBlock)((BlockItem)itemstack.getItem()).getBlock();
            Effect effect = flowerblock.getStewEffect();
            SuspiciousStewItem.addEffect(itemstack2, effect, flowerblock.getStewEffectDuration());
        }*/

        return itemstack2;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canFit(int width, int height) {
        return width >= 2 && height >= 2;
    }

    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }


}
