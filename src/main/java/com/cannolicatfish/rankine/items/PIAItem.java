package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.util.CraftingInventoryFilled;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

import net.minecraft.item.Item.Properties;

public class PIAItem extends Item {
    public PIAItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        PlayerInventory inv = playerIn.inventory;
        ITag<Item> itemTag = TagCollectionManager.getInstance().getItems().getTag(new ResourceLocation("forge:nuggets"));
        if (itemTag != null && inv.contains(itemTag) && !worldIn.isClientSide)
        {
            for (ItemStack i : new Ingredient.TagList(itemTag).getItems()) {
                if (inv.findSlotMatchingItem(i) != -1) {
                    ItemStack invStack = inv.getItem(inv.findSlotMatchingItem(i));
                    int slot = inv.findSlotMatchingItem(i);

                    if (invStack.getCount() >= 9) {
                        CraftingInventory craft = new CraftingInventoryFilled(new Container(null, -1) {
                            @Override
                            public boolean stillValid(PlayerEntity playerIn) {
                                return false;
                            }
                        },3,3,NonNullList.withSize(9, i));
                        Optional<ICraftingRecipe> recipe = worldIn.getRecipeManager().getRecipeFor(IRecipeType.CRAFTING,craft,worldIn);

                        if (recipe.isPresent()) {
                            ItemStack newStack = recipe.get().assemble(craft);
                            invStack.shrink(9);
                            inv.placeItemBackInInventory(worldIn,newStack);
                        }
                    }

                }
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entityIn;
            List<ItemEntity> items = worldIn.getEntitiesOfClass(ItemEntity.class, entityIn.getBoundingBox().inflate(2, 2, 2));
            for (ItemEntity i : items) {
                i.playerTouch(player);
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
