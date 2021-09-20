package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class RankineClientEventHandler {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        ClientWorld world = Minecraft.getInstance().world;
        if (world != null) {
            List<AlloyingRecipe> alloyingRecipes = new ArrayList<>(world.getRecipeManager().getRecipesForType(RankineRecipeTypes.ALLOYING));
            AlloyCustomHelper.setAlloyingRecipes(alloyingRecipes);

            List<ElementRecipe> elementRecipes = new ArrayList<>(world.getRecipeManager().getRecipesForType(RankineRecipeTypes.ELEMENT));
            AlloyCustomHelper.setElementRecipes(elementRecipes);

            List<AlloyCraftingRecipe> alloyCraftingRecipes = world.getRecipeManager().getRecipesForType(IRecipeType.CRAFTING).stream()
                    .filter((iCraftingRecipe -> iCraftingRecipe instanceof AlloyCraftingRecipe))
                    .map(AlloyCraftingRecipe.class::cast)
                    .collect(Collectors.toList());
            AlloyCustomHelper.setCraftingRecipes(alloyCraftingRecipes);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void fovUpdate(FOVUpdateEvent event) {
        PlayerEntity player = event.getEntity();
        ModifiableAttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (Config.GENERAL.MOVEMENT_MODIFIERS.get() && movementSpeed != null) {
            if (movementSpeed.hasModifier(RankineAttributes.GRASS_PATH_MS) || movementSpeed.hasModifier(RankineAttributes.SAND_MS) || movementSpeed.hasModifier(RankineAttributes.BRICKS_MS) ||
                    movementSpeed.hasModifier(RankineAttributes.ROMAN_CONCRETE_MS) || movementSpeed.hasModifier(RankineAttributes.DIRT_MS) || movementSpeed.hasModifier(RankineAttributes.POLISHED_STONE_MS) ||
                    movementSpeed.hasModifier(RankineAttributes.WOODEN_MS) || movementSpeed.hasModifier(RankineAttributes.CONCRETE_MS) || movementSpeed.hasModifier(RankineAttributes.SNOW_MS) ||
                    movementSpeed.hasModifier(RankineAttributes.MUD_MS)) {
                event.setNewfov(Minecraft.getInstance().gameSettings.fovScaleEffect * (player.isSprinting() ? 1.3f : 1));
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void renderOverlay(RenderGameOverlayEvent event) {
        if (Minecraft.getInstance().player != null && event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
            PlayerEntity player = Minecraft.getInstance().player;
            ItemStack stack = player.getHeldItemOffhand().getItem() instanceof KnifeItem ? player.getHeldItemOffhand() : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
                int i = stack.getItem().getUseDuration(stack) - player.getItemInUseCount();
                if (i < (10 + EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.PREPARATION,stack))) {
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    RenderSystem.disableDepthTest();
                    RenderSystem.depthMask(false);
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    RenderSystem.disableAlphaTest();
                    Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("rankine:textures/misc/parry_overlay.png"));
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferbuilder = tessellator.getBuffer();
                    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                    bufferbuilder.pos(0.0D, (double)event.getWindow().getScaledHeight(), -90.0D).tex(0.0F, 1.0F).endVertex();
                    bufferbuilder.pos((double)event.getWindow().getScaledWidth(), (double)event.getWindow().getScaledHeight(), -90.0D).tex(1.0F, 1.0F).endVertex();
                    bufferbuilder.pos((double)event.getWindow().getScaledWidth(), 0.0D, -90.0D).tex(1.0F, 0.0F).endVertex();
                    bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0F, 0.0F).endVertex();
                    tessellator.draw();
                    RenderSystem.depthMask(true);
                    RenderSystem.enableDepthTest();
                    RenderSystem.enableAlphaTest();
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
                }
            }

        }
    }

}