package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
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
        if (event.getType() == RenderGameOverlayEvent.ElementType.HELMET && Minecraft.getInstance().player != null) {
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
        if (event.getType() == RenderGameOverlayEvent.ElementType.HELMET && Minecraft.getInstance().player != null) {
            PlayerEntity player = Minecraft.getInstance().player;
            World worldIn = player.getEntityWorld();

            Block bl = worldIn.getBlockState(new BlockPos(player.getPosX(),player.getPosYEye(),player.getPosZ())).getBlock();
            if (bl instanceof GasBlock) {
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.defaultBlendFunc();
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableAlphaTest();
                Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("rankine:textures/block/"+bl.getRegistryName().getPath()+".png"));
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

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void renderOverlayBlockEvent(RenderBlockOverlayEvent event) {
        if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER && event.getBlockForOverlay().getBlock().getRegistryName().getNamespace().equals("rankine")) {
            Minecraft minecraftIn = Minecraft.getInstance();
            RenderSystem.enableTexture();
            minecraftIn.getTextureManager().bindTexture(new ResourceLocation("rankine:textures/block/"+event.getBlockForOverlay().getBlock().getRegistryName().getPath()+"_overlay.png"));
            BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
            float f = minecraftIn.player.getBrightness();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            float f1 = 4.0F;
            float f2 = -1.0F;
            float f3 = 1.0F;
            float f4 = -1.0F;
            float f5 = 1.0F;
            float f6 = -0.5F;
            float f7 = -minecraftIn.player.rotationYaw / 64.0F;
            float f8 = minecraftIn.player.rotationPitch / 64.0F;
            Matrix4f matrix4f = event.getMatrixStack().getLast().getMatrix();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
            bufferbuilder.pos(matrix4f, -1.0F, -1.0F, -0.5F).color(f, f, f, 0.1F).tex(4.0F + f7, 4.0F + f8).endVertex();
            bufferbuilder.pos(matrix4f, 1.0F, -1.0F, -0.5F).color(f, f, f, 0.1F).tex(0.0F + f7, 4.0F + f8).endVertex();
            bufferbuilder.pos(matrix4f, 1.0F, 1.0F, -0.5F).color(f, f, f, 0.1F).tex(0.0F + f7, 0.0F + f8).endVertex();
            bufferbuilder.pos(matrix4f, -1.0F, 1.0F, -0.5F).color(f, f, f, 0.1F).tex(4.0F + f7, 0.0F + f8).endVertex();
            bufferbuilder.finishDrawing();
            WorldVertexBufferUploader.draw(bufferbuilder);
            RenderSystem.disableBlend();
            event.setCanceled(true);
        }
    }


}