package com.cannolicatfish.rankine.recipe;


import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

public class JamRecipe extends CustomRecipe {
    public static final SimpleRecipeSerializer<JamRecipe> SERIALIZER = new SimpleRecipeSerializer<>(JamRecipe::new);
    public JamRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    public boolean matches(CraftingContainer inv, Level worldIn) {
        boolean flag = false;
        boolean flag2 = false;
        boolean flag3 = false;
        int berryCount = 0;
        int sugarCount = 0;
        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == Items.SUGAR && !flag) {
                    sugarCount++;
                    if (sugarCount == 2) {
                        flag = true;
                    }
                } else if (RankineTags.Items.BERRIES.contains(itemstack.getItem()) && !flag2) {
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

    public ItemStack assemble(CraftingContainer inv) {
        ItemStack itemstack = ItemStack.EMPTY;

        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack itemstack1 = inv.getItem(i);
            if (!itemstack1.isEmpty() && RankineTags.Items.BERRIES.contains(itemstack1.getItem())) {
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
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }


}
