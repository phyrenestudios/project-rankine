package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.util.CraftingInventoryFilled;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.Optional;

public class PIAItem extends Item {
    public PIAItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        Inventory inv = playerIn.getInventory();
        if (inv.contains(Tags.Items.NUGGETS) && !worldIn.isClientSide)
        {
            for (ItemStack i : new Ingredient.TagValue(Tags.Items.NUGGETS).getItems()) {
                if (inv.findSlotMatchingItem(i) != -1) {
                    ItemStack invStack = inv.getItem(inv.findSlotMatchingItem(i));
                    int slot = inv.findSlotMatchingItem(i);

                    if (invStack.getCount() >= 9) {
                        CraftingContainer craft = new CraftingInventoryFilled(new AbstractContainerMenu(null, -1) {
                            @Override
                            public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
                                return null;
                            }

                            @Override
                            public boolean stillValid(Player playerIn) {
                                return false;
                            }
                        },3,3,NonNullList.withSize(9, i));
                        Optional<CraftingRecipe> recipe = worldIn.getRecipeManager().getRecipeFor(RecipeType.CRAFTING,craft,worldIn);

                        if (recipe.isPresent()) {
                            ItemStack newStack = recipe.get().assemble(craft, RegistryAccess.EMPTY);
                            invStack.shrink(9);
                            inv.placeItemBackInInventory(newStack);
                        }
                    }

                }
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof Player){
            Player player = (Player) entityIn;
            List<ItemEntity> items = worldIn.getEntitiesOfClass(ItemEntity.class, entityIn.getBoundingBox().inflate(2, 2, 2));
            for (ItemEntity i : items) {
                i.playerTouch(player);
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
