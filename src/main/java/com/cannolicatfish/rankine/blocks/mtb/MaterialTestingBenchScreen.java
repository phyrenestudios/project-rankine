package com.cannolicatfish.rankine.blocks.mtb;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.ElementEquation;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.ArrayUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MaterialTestingBenchScreen extends ContainerScreen<MaterialTestingBenchContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/mtb.png");
    private final int textureXSize;
    private final int textureYSize;
    private ElementRecipe element = null;
    public MaterialTestingBenchScreen(MaterialTestingBenchContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.xSize = 230;
        this.ySize = 256;
        this.textureXSize = 256;
        this.textureYSize = 256;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.minecraft.getTextureManager().bindTexture(this.GUI);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        blit(p_230450_1_, x, y, 0, 0, this.xSize, this.ySize, this.textureXSize, this.textureYSize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int p_230451_2_, int p_230451_3_) {
        if (Minecraft.getInstance().world != null && element != this.container.getSlotItem(Minecraft.getInstance().world))
        {
            element = this.container.getSlotItem(Minecraft.getInstance().world);
        }
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (element != null)
        {
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("block.rankine.material_testing_bench.durability.test"),12,32,0xffffff);
            int ymod = 0;
            List<StringTextComponent> strings = checkDurabilityRange(element);
            for (StringTextComponent s : strings) {
                int textX = s.getText().contains("->") ? 24 : 12;
                drawString(matrixStack,Minecraft.getInstance().fontRenderer,s,textX,44 + ymod,0xffffff);
                ymod += 12;
            }


            /*Enchantment e = element.getToolEnchantment(100);
            if (e != null) {
                drawCenteredString(matrixStack,Minecraft.getInstance().fontRenderer,e.getDisplayName(1).getString(),125,66,0x55FF55);
            }*/

            drawString(matrixStack,Minecraft.getInstance().fontRenderer,element.getName().toUpperCase(Locale.ROOT) + " (" + element.getSymbol() + ")",32,10,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("element."+ element.getId() +".preview"),32,20,0xffffff);
        }
        /*for (String s : this.container.getOutputString().getKey()) {
            drawCenteredString(p_230451_1_, Minecraft.getInstance().fontRenderer, s, 116, 65 + ymod, this.container.getOutputString().getValue());
            ymod += 10;
        }*/
    }


    private List<StringTextComponent> checkDurabilityRange(ElementRecipe recipe) {
        ElementEquation eq = recipe.getDurabilityFormula();
        ElementEquation.FormulaType[] eqTypes = eq.getFormulaTypes();

        int[] bounds = eq.getBreaks();
        bounds = ArrayUtils.insert(0,bounds,0);
        List<Integer> dur = new ArrayList<>();
        for (int x : bounds) {
            dur.add(recipe.getDurability(x));
        }
        List<StringTextComponent> outDur = new ArrayList<>();
        if (dur.size() > 1) {
            for (int d = 1; d < dur.size(); d++) {
                outDur.add(new StringTextComponent(new TranslationTextComponent("block.rankine.material_testing_bench.durability").getString() + ": " + eqTypes[d - 1] + ", " + (bounds[d-1] + 1) + "% - " + bounds[d] + "%"));
                outDur.add(new StringTextComponent(dur.get(d-1) + " -> " + dur.get(d)));
            }
        } else {
            outDur.add(new StringTextComponent(new TranslationTextComponent("block.rankine.material_testing_bench.durability.error").getString()));
        }

        // Durability: {eqType}, {i0}% - {i1}%,
        return outDur;
    }
}
