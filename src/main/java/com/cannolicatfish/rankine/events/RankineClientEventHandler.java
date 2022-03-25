package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import com.mojang.math.Matrix4f;
import net.minecraft.world.level.Level;
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
        ClientLevel world = Minecraft.getInstance().level;
        if (world != null) {
            List<AlloyingRecipe> alloyingRecipes = new ArrayList<>(world.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ALLOYING));
            AlloyCustomHelper.setAlloyingRecipes(alloyingRecipes);

            List<ElementRecipe> elementRecipes = new ArrayList<>(world.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ELEMENT));
            AlloyCustomHelper.setElementRecipes(elementRecipes);

            List<AlloyCraftingRecipe> alloyCraftingRecipes = world.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING).stream()
                    .filter((iCraftingRecipe -> iCraftingRecipe instanceof AlloyCraftingRecipe))
                    .map(AlloyCraftingRecipe.class::cast)
                    .collect(Collectors.toList());
            AlloyCustomHelper.setCraftingRecipes(alloyCraftingRecipes);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void fovUpdate(FOVUpdateEvent event) {
        Player player = event.getEntity();
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (Config.GENERAL.MOVEMENT_MODIFIERS.get() && movementSpeed != null) {
            if (movementSpeed.hasModifier(RankineAttributes.GRASS_PATH_MS) || movementSpeed.hasModifier(RankineAttributes.SAND_MS) || movementSpeed.hasModifier(RankineAttributes.BRICKS_MS) ||
                    movementSpeed.hasModifier(RankineAttributes.ROMAN_CONCRETE_MS) || movementSpeed.hasModifier(RankineAttributes.DIRT_MS) || movementSpeed.hasModifier(RankineAttributes.POLISHED_STONE_MS) ||
                    movementSpeed.hasModifier(RankineAttributes.WOODEN_MS) || movementSpeed.hasModifier(RankineAttributes.CONCRETE_MS) || movementSpeed.hasModifier(RankineAttributes.SNOW_MS) ||
                    movementSpeed.hasModifier(RankineAttributes.MUD_MS)) {
                event.setNewfov(Minecraft.getInstance().options.fovEffectScale * (player.isSprinting() ? 1.3f : 1));
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void renderOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.LAYER && Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            ItemStack stack = player.getOffhandItem().getItem() instanceof KnifeItem ? player.getOffhandItem() : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
                int i = stack.getItem().getUseDuration(stack) - player.getUseItemRemainingTicks();
                if (i < (10 + EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.PREPARATION,stack))) {
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    RenderSystem.disableDepthTest();
                    RenderSystem.depthMask(false);
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    //RenderSystem.disableAlphaTest();
                    Minecraft.getInstance().getTextureManager().bindForSetup(new ResourceLocation("rankine:textures/misc/parry_overlay.png"));
                    Tesselator tessellator = Tesselator.getInstance();
                    BufferBuilder bufferbuilder = tessellator.getBuilder();
                    bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                    bufferbuilder.vertex(0.0D, (double)event.getWindow().getGuiScaledHeight(), -90.0D).uv(0.0F, 1.0F).endVertex();
                    bufferbuilder.vertex((double)event.getWindow().getGuiScaledWidth(), (double)event.getWindow().getGuiScaledHeight(), -90.0D).uv(1.0F, 1.0F).endVertex();
                    bufferbuilder.vertex((double)event.getWindow().getGuiScaledWidth(), 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
                    bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
                    tessellator.end();
                    RenderSystem.depthMask(true);
                    RenderSystem.enableDepthTest();
                    //RenderSystem.enableAlphaTest();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
                }
            }
        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.LAYER && Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            Level worldIn = player.getCommandSenderWorld();

            Block bl = worldIn.getBlockState(new BlockPos(player.getX(),player.getEyeY(),player.getZ())).getBlock();
            if (bl instanceof GasBlock) {
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                //RenderSystem.disableAlphaTest();
                Minecraft.getInstance().getTextureManager().bindForSetup(new ResourceLocation("rankine:textures/block/"+bl.getRegistryName().getPath()+".png"));
                Tesselator tessellator = Tesselator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuilder();
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferbuilder.vertex(0.0D, (double)event.getWindow().getGuiScaledHeight(), -90.0D).uv(0.0F, 1.0F).endVertex();
                bufferbuilder.vertex((double)event.getWindow().getGuiScaledWidth(), (double)event.getWindow().getGuiScaledHeight(), -90.0D).uv(1.0F, 1.0F).endVertex();
                bufferbuilder.vertex((double)event.getWindow().getGuiScaledWidth(), 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
                bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
                tessellator.end();
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();
                //RenderSystem.enableAlphaTest();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
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
            minecraftIn.getTextureManager().bindForSetup(new ResourceLocation("rankine:textures/block/"+event.getBlockForOverlay().getBlock().getRegistryName().getPath()+"_overlay.png"));
            BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
            float f = minecraftIn.player.getBrightness();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            float f1 = 4.0F;
            float f2 = -1.0F;
            float f3 = 1.0F;
            float f4 = -1.0F;
            float f5 = 1.0F;
            float f6 = -0.5F;
            float f7 = -minecraftIn.player.getYRot() / 64.0F;
            float f8 = minecraftIn.player.getXRot() / 64.0F;
            Matrix4f matrix4f = event.getMatrixStack().last().pose();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
            bufferbuilder.vertex(matrix4f, -1.0F, -1.0F, -0.5F).color(f, f, f, 0.1F).uv(4.0F + f7, 4.0F + f8).endVertex();
            bufferbuilder.vertex(matrix4f, 1.0F, -1.0F, -0.5F).color(f, f, f, 0.1F).uv(0.0F + f7, 4.0F + f8).endVertex();
            bufferbuilder.vertex(matrix4f, 1.0F, 1.0F, -0.5F).color(f, f, f, 0.1F).uv(0.0F + f7, 0.0F + f8).endVertex();
            bufferbuilder.vertex(matrix4f, -1.0F, 1.0F, -0.5F).color(f, f, f, 0.1F).uv(4.0F + f7, 0.0F + f8).endVertex();
            bufferbuilder.end();
            BufferUploader.end(bufferbuilder);
            RenderSystem.disableBlend();
            event.setCanceled(true);
        }
    }


}