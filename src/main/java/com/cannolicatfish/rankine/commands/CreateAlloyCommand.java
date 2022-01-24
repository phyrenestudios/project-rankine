package com.cannolicatfish.rankine.commands;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;
import java.util.Optional;

public class CreateAlloyCommand {

    public static final SuggestionProvider<CommandSource> ALLOY_RECIPE_SUGGESTER = (p_198477_0_, p_198477_1_) -> {
        Collection<AlloyingRecipe> collection = p_198477_0_.getSource().getServer().getRecipeManager().getRecipesForType(RankineRecipeTypes.ALLOYING);
        return ISuggestionProvider.func_212476_a(collection.stream().map(AlloyingRecipe::getId), p_198477_1_);
    };

    public static final SuggestionProvider<CommandSource> MODIFIER_RECIPE_SUGGESTER = (p_198477_0_, p_198477_1_) -> {
        Collection<AlloyModifierRecipe> collection = p_198477_0_.getSource().getServer().getRecipeManager().getRecipesForType(RankineRecipeTypes.ALLOY_MODIFIER);
        return ISuggestionProvider.func_212476_a(collection.stream().map(AlloyModifierRecipe::getId), p_198477_1_);
    };

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("givealloy").requires((p_198496_0_) -> {
            return p_198496_0_.hasPermissionLevel(2);
        }).then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("item", ItemArgument.item()).executes((p_198492_0_) -> {
            Item in = ItemArgument.getItem(p_198492_0_, "item").getItem();
            return giveItem(p_198492_0_.getSource(), ItemArgument.getItem(p_198492_0_, "item"),in instanceof IAlloyItem ? ((IAlloyItem) in).getDefaultComposition() : "", in instanceof IAlloyItem ? ((IAlloyItem) in).getDefaultRecipe() : new ResourceLocation(""), EntityArgument.getPlayers(p_198492_0_, "targets"), 1, null,16777215);
        }).then(Commands.argument("composition", StringArgumentType.string()).executes((p_198493_0_) -> {
            Item in = ItemArgument.getItem(p_198493_0_, "item").getItem();
            return giveItem(p_198493_0_.getSource(), ItemArgument.getItem(p_198493_0_, "item"), StringArgumentType.getString(p_198493_0_, "composition"), in instanceof IAlloyItem ? ((IAlloyItem) in).getDefaultRecipe() : new ResourceLocation(""),  EntityArgument.getPlayers(p_198493_0_, "targets"), 1, null,16777215);
        }).then(Commands.argument("recipe", ResourceLocationArgument.resourceLocation()).suggests(ALLOY_RECIPE_SUGGESTER).executes((p_198494_0_) -> {
            return giveItem(p_198494_0_.getSource(), ItemArgument.getItem(p_198494_0_, "item"), StringArgumentType.getString(p_198494_0_, "composition"), ResourceLocationArgument.getResourceLocation(p_198494_0_, "recipe"), EntityArgument.getPlayers(p_198494_0_, "targets"), 1, null,16777215);
        }).then(Commands.argument("count", IntegerArgumentType.integer(1)).executes((p_198495_0_) -> {
            return giveItem(p_198495_0_.getSource(), ItemArgument.getItem(p_198495_0_, "item"), StringArgumentType.getString(p_198495_0_, "composition"), ResourceLocationArgument.getResourceLocation(p_198495_0_, "recipe"), EntityArgument.getPlayers(p_198495_0_, "targets"), IntegerArgumentType.getInteger(p_198495_0_, "count"), null,16777215);
        }).then(Commands.argument("modifier", ResourceLocationArgument.resourceLocation()).suggests(MODIFIER_RECIPE_SUGGESTER).executes((p_198496_0_) -> {
            return giveItem(p_198496_0_.getSource(), ItemArgument.getItem(p_198496_0_, "item"), StringArgumentType.getString(p_198496_0_, "composition"), ResourceLocationArgument.getResourceLocation(p_198496_0_, "recipe"), EntityArgument.getPlayers(p_198496_0_, "targets"), IntegerArgumentType.getInteger(p_198496_0_, "count"), ResourceLocationArgument.getResourceLocation(p_198496_0_, "modifier"), 16777215);
        }).then(Commands.argument("color", IntegerArgumentType.integer(1)).executes((p_198497_0_) -> {
            return giveItem(p_198497_0_.getSource(), ItemArgument.getItem(p_198497_0_, "item"), StringArgumentType.getString(p_198497_0_, "composition"), ResourceLocationArgument.getResourceLocation(p_198497_0_, "recipe"), EntityArgument.getPlayers(p_198497_0_, "targets"), IntegerArgumentType.getInteger(p_198497_0_, "count"), ResourceLocationArgument.getResourceLocation(p_198497_0_, "modifier"), IntegerArgumentType.getInteger(p_198497_0_, "color"));
        })))))))));
    }

    private static int giveItem(CommandSource source, ItemInput itemIn, String data, ResourceLocation recipe, Collection<ServerPlayerEntity> targets, int count, ResourceLocation modifier, int color) throws CommandSyntaxException {
        for(ServerPlayerEntity serverplayerentity : targets) {
            int i = count;

            while(i > 0) {
                int j = Math.min(itemIn.getItem().getMaxStackSize(), i);
                i -= j;
                ItemStack itemstack = itemIn.createStack(j, false);
                if (itemstack.getItem() instanceof IAlloyItem) {
                    AlloyingRecipe alloy = null;
                    if (!recipe.equals(new ResourceLocation("")) || color != 16777215) {
                        if (!recipe.equals(new ResourceLocation(""))) {
                            Optional<? extends IRecipe<?>> r = serverplayerentity.getEntityWorld().getRecipeManager().getRecipe(recipe);
                            if (r.isPresent() && r.get() instanceof AlloyingRecipe) {
                                alloy = (AlloyingRecipe) r.get();
                                ((IAlloyItem) itemstack.getItem()).createAlloyNBT(itemstack,serverplayerentity.world,data,recipe,alloy.getLocalName());
                                if (color != 16777215) {
                                    IAlloyItem.addColorNBT(itemstack,Math.min(Math.max(color,0),16777215));
                                } else {
                                    IAlloyItem.addColorNBT(itemstack,alloy.getColor());
                                }
                            }
                        } else {
                            ((IAlloyItem) itemstack.getItem()).createAlloyNBT(itemstack,serverplayerentity.world,data,recipe,null);
                            IAlloyItem.addColorNBT(itemstack,Math.min(Math.max(color,0),16777215));
                        }

                    }

                    if (itemstack.getItem() instanceof IAlloyToolOld) {
                        if (modifier != null) {
                            Optional<? extends IRecipe<?>> modR = serverplayerentity.getEntityWorld().getRecipeManager().getRecipe(modifier);
                            if (modR.isPresent() && modR.get() instanceof AlloyModifierRecipe) {
                                ((IAlloyToolOld) itemstack.getItem()).applyModifiersFromRecipe(itemstack,((AlloyModifierRecipe) modR.get()));
                            }
                        }
                        ((IAlloyToolOld) itemstack.getItem()).applyAlloyEnchantments(itemstack,serverplayerentity.world);
                    } else if (itemstack.getItem() instanceof IAlloyTieredItem) {
                        IAlloyTieredItem tieredItem = ((IAlloyTieredItem) itemstack.getItem());
                        AlloyModifierRecipe mod = null;
                        if (modifier != null) {
                            Optional<? extends IRecipe<?>> modR = serverplayerentity.getEntityWorld().getRecipeManager().getRecipe(modifier);
                            if (modR.isPresent() && modR.get() instanceof AlloyModifierRecipe) {
                                mod = (AlloyModifierRecipe) modR.get();
                            }
                        }
                        tieredItem.initStats(itemstack,tieredItem.getElementMap(data,serverplayerentity.world),tieredItem.getAlloyingRecipe(recipe,serverplayerentity.world),mod);
                        //((IAlloyArmor) itemstack.getItem()).applyAlloyEnchantments(itemstack,serverplayerentity.world);
                    }

                } else {
                    AlloyItem.addAlloy(itemstack,new AlloyData(data));
                }

                boolean flag = serverplayerentity.inventory.addItemStackToInventory(itemstack);
                if (flag && itemstack.isEmpty()) {
                    itemstack.setCount(1);
                    ItemEntity itementity1 = serverplayerentity.dropItem(itemstack, false);
                    if (itementity1 != null) {
                        itementity1.makeFakeItem();
                    }

                    serverplayerentity.world.playSound((PlayerEntity)null, serverplayerentity.getPosX(), serverplayerentity.getPosY(), serverplayerentity.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((serverplayerentity.getRNG().nextFloat() - serverplayerentity.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    serverplayerentity.container.detectAndSendChanges();
                } else {
                    ItemEntity itementity = serverplayerentity.dropItem(itemstack, false);
                    if (itementity != null) {
                        itementity.setNoPickupDelay();
                        itementity.setOwnerId(serverplayerentity.getUniqueID());
                    }
                }
            }
        }

        if (targets.size() == 1) {
            source.sendFeedback(new TranslationTextComponent("commands.give.success.single", count, itemIn.createStack(count, false).getTextComponent(), targets.iterator().next().getDisplayName()), true);
        } else {
            source.sendFeedback(new TranslationTextComponent("commands.give.success.single", count, itemIn.createStack(count, false).getTextComponent(), targets.size()), true);
        }

        return targets.size();
    }
}
