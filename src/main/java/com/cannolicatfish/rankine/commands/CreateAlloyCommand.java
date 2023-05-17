package com.cannolicatfish.rankine.commands;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.OldAlloyingRecipe;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;

import java.util.Collection;
import java.util.Optional;

public class CreateAlloyCommand {

    public static final SuggestionProvider<CommandSourceStack> ALLOY_RECIPE_SUGGESTER = (p_198477_0_, p_198477_1_) -> {
        Collection<OldAlloyingRecipe> collection = p_198477_0_.getSource().getServer().getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ALLOYING.get());
        return SharedSuggestionProvider.suggestResource(collection.stream().map(OldAlloyingRecipe::getId), p_198477_1_);
    };

    public static final SuggestionProvider<CommandSourceStack> MODIFIER_RECIPE_SUGGESTER = (p_198477_0_, p_198477_1_) -> {
        Collection<AlloyModifierRecipe> collection = p_198477_0_.getSource().getServer().getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ALLOY_MODIFIER.get());
        return SharedSuggestionProvider.suggestResource(collection.stream().map(AlloyModifierRecipe::getId), p_198477_1_);
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext p_214447_) {
        dispatcher.register(Commands.literal("givealloy").requires((p_198496_0_) -> {
            return p_198496_0_.hasPermission(2);
        }).then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("item", ItemArgument.item(p_214447_)).executes((p_198492_0_) -> {
            Item in = ItemArgument.getItem(p_198492_0_, "item").getItem();
            return giveItem(p_198492_0_.getSource(), ItemArgument.getItem(p_198492_0_, "item"),in instanceof IAlloyItem ? ((IAlloyItem) in).getDefaultComposition() : "", in instanceof IAlloyItem ? ((IAlloyItem) in).getDefaultRecipe() : new ResourceLocation(""), EntityArgument.getPlayers(p_198492_0_, "targets"), 1, null,16777215);
        }).then(Commands.argument("composition", StringArgumentType.string()).executes((p_198493_0_) -> {
            Item in = ItemArgument.getItem(p_198493_0_, "item").getItem();
            return giveItem(p_198493_0_.getSource(), ItemArgument.getItem(p_198493_0_, "item"), StringArgumentType.getString(p_198493_0_, "composition"), in instanceof IAlloyItem ? ((IAlloyItem) in).getDefaultRecipe() : new ResourceLocation(""),  EntityArgument.getPlayers(p_198493_0_, "targets"), 1, null,16777215);
        }).then(Commands.argument("recipe", ResourceLocationArgument.id()).suggests(ALLOY_RECIPE_SUGGESTER).executes((p_198494_0_) -> {
            return giveItem(p_198494_0_.getSource(), ItemArgument.getItem(p_198494_0_, "item"), StringArgumentType.getString(p_198494_0_, "composition"), ResourceLocationArgument.getId(p_198494_0_, "recipe"), EntityArgument.getPlayers(p_198494_0_, "targets"), 1, null,16777215);
        }).then(Commands.argument("count", IntegerArgumentType.integer(1)).executes((p_198495_0_) -> {
            return giveItem(p_198495_0_.getSource(), ItemArgument.getItem(p_198495_0_, "item"), StringArgumentType.getString(p_198495_0_, "composition"), ResourceLocationArgument.getId(p_198495_0_, "recipe"), EntityArgument.getPlayers(p_198495_0_, "targets"), IntegerArgumentType.getInteger(p_198495_0_, "count"), null,16777215);
        }).then(Commands.argument("modifier", ResourceLocationArgument.id()).suggests(MODIFIER_RECIPE_SUGGESTER).executes((p_198496_0_) -> {
            return giveItem(p_198496_0_.getSource(), ItemArgument.getItem(p_198496_0_, "item"), StringArgumentType.getString(p_198496_0_, "composition"), ResourceLocationArgument.getId(p_198496_0_, "recipe"), EntityArgument.getPlayers(p_198496_0_, "targets"), IntegerArgumentType.getInteger(p_198496_0_, "count"), ResourceLocationArgument.getId(p_198496_0_, "modifier"), 16777215);
        }).then(Commands.argument("color", IntegerArgumentType.integer(1)).executes((p_198497_0_) -> {
            return giveItem(p_198497_0_.getSource(), ItemArgument.getItem(p_198497_0_, "item"), StringArgumentType.getString(p_198497_0_, "composition"), ResourceLocationArgument.getId(p_198497_0_, "recipe"), EntityArgument.getPlayers(p_198497_0_, "targets"), IntegerArgumentType.getInteger(p_198497_0_, "count"), ResourceLocationArgument.getId(p_198497_0_, "modifier"), IntegerArgumentType.getInteger(p_198497_0_, "color"));
        })))))))));
    }

    private static int giveItem(CommandSourceStack source, ItemInput itemIn, String data, ResourceLocation recipe, Collection<ServerPlayer> targets, int count, ResourceLocation modifier, int color) throws CommandSyntaxException {
        for(ServerPlayer serverplayerentity : targets) {
            int i = count;

            while(i > 0) {
                int j = Math.min(itemIn.getItem().getMaxStackSize(), i);
                i -= j;
                ItemStack itemstack = itemIn.createItemStack(j, false);
                if (itemstack.getItem() instanceof IAlloyItem) {
                    OldAlloyingRecipe alloy = null;
                    if (!recipe.equals(new ResourceLocation("")) || color != 16777215) {
                        if (!recipe.equals(new ResourceLocation(""))) {
                            Optional<? extends Recipe<?>> r = serverplayerentity.getCommandSenderWorld().getRecipeManager().byKey(recipe);
                            if (r.isPresent() && r.get() instanceof OldAlloyingRecipe) {
                                alloy = (OldAlloyingRecipe) r.get();
                                ((IAlloyItem) itemstack.getItem()).createAlloyNBT(itemstack,serverplayerentity.level,data,recipe,alloy.getLocalName());
                                if (color != 16777215) {
                                    IAlloyItem.addColorNBT(itemstack,Math.min(Math.max(color,0),16777215));
                                } else {
                                    IAlloyItem.addColorNBT(itemstack,alloy.getColor());
                                }
                            }
                        } else {
                            ((IAlloyItem) itemstack.getItem()).createAlloyNBT(itemstack,serverplayerentity.level,data,recipe,null);
                            IAlloyItem.addColorNBT(itemstack,Math.min(Math.max(color,0),16777215));
                        }

                    }

                    if (itemstack.getItem() instanceof IAlloyTieredItem) {
                        IAlloyTieredItem tieredItem = ((IAlloyTieredItem) itemstack.getItem());
                        AlloyModifierRecipe mod = null;
                        if (modifier != null) {
                            Optional<? extends Recipe<?>> modR = serverplayerentity.getCommandSenderWorld().getRecipeManager().byKey(modifier);
                            if (modR.isPresent() && modR.get() instanceof AlloyModifierRecipe) {
                                mod = (AlloyModifierRecipe) modR.get();
                            }
                        }
                        tieredItem.initStats(itemstack,tieredItem.getElementMap(data,serverplayerentity.level),tieredItem.getAlloyingRecipe(recipe,serverplayerentity.level),mod);
                        tieredItem.applyAlloyEnchantments(itemstack,serverplayerentity.level);
                    }

                } 

                boolean flag = serverplayerentity.getInventory().add(itemstack);
                if (flag && itemstack.isEmpty()) {
                    itemstack.setCount(1);
                    ItemEntity itementity1 = serverplayerentity.drop(itemstack, false);
                    if (itementity1 != null) {
                        itementity1.makeFakeItem();
                    }

                    serverplayerentity.level.playSound((Player)null, serverplayerentity.getX(), serverplayerentity.getY(), serverplayerentity.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((serverplayerentity.getRandom().nextFloat() - serverplayerentity.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    serverplayerentity.inventoryMenu.broadcastChanges();
                } else {
                    ItemEntity itementity = serverplayerentity.drop(itemstack, false);
                    if (itementity != null) {
                        itementity.setNoPickUpDelay();
                        itementity.setTarget(serverplayerentity.getUUID());
                    }
                }
            }
        }

        if (targets.size() == 1) {
            source.sendSuccess(Component.translatable("commands.give.success.single", count, itemIn.createItemStack(count, false).getDisplayName(), targets.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(Component.translatable("commands.give.success.single", count, itemIn.createItemStack(count, false).getDisplayName(), targets.size()), true);
        }

        return targets.size();
    }
}
