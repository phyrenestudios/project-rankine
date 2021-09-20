package com.cannolicatfish.rankine.blocks.mtt;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.ElementEquation;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;


import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Stream;

public class MaterialTestingTableScreen extends ContainerScreen<MaterialTestingTableContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/mtb.png");
    private final int textureXSize;
    private final int textureYSize;
    private ElementRecipe element = null;
    public MaterialTestingTableScreen(MaterialTestingTableContainer container, PlayerInventory inv, ITextComponent name) {
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
            int stat = this.container.getToolItem(Minecraft.getInstance().world);
            String statStr = StatType.values()[stat].toString();
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("block.rankine.material_testing_bench."+ statStr.toLowerCase(Locale.ROOT) +".test"),12,32,0xffffff);
            int ymod = 0;

            List<StringTextComponent> strings = checkStatRange(element,StatType.values()[stat]);
            for (StringTextComponent s : strings) {
                int textX = s.getText().contains("->") ? 24 : 12;
                drawString(matrixStack,Minecraft.getInstance().fontRenderer,s,textX,44 + ymod,0xffffff);
                ymod += 12;
            }

            drawString(matrixStack,Minecraft.getInstance().fontRenderer,element.getName().toUpperCase(Locale.ROOT) + " (" + element.getSymbol() + ")",32,10,0xffffff);
            drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("element."+ element.getId() +".preview"),32,20,0xffffff);
        }
    }

    private List<StringTextComponent> checkStatRange(ElementRecipe recipe, StatType stat) {
        ElementEquation eq = recipe.getStatEquation(stat.ordinal());
        ElementEquation.FormulaType[] eqTypes = eq.getFormulaTypes();

        int[] breaks = eq.getBreaks();
        int[] bounds = Arrays.copyOf(new int[]{0},breaks.length+1);
        System.arraycopy(breaks,0,bounds,1,breaks.length);
        List<Float> dur = new ArrayList<>();
        for (int x : bounds) {
            dur.add(recipe.getStat(stat.ordinal(),x));
        }
        List<StringTextComponent> outDur = new ArrayList<>();
        if (dur.size() > 1) {
            for (int d = 1; d < dur.size(); d++) {
                outDur.add(new StringTextComponent(new TranslationTextComponent("block.rankine.material_testing_bench."+stat.toString().toLowerCase(Locale.ROOT)).getString() + ": " + eqTypes[d - 1] + ", " + (bounds[d-1] + 1) + "% - " + bounds[d] + "%"));
                if (stat.equals(StatType.CORROSION_RESISTANCE) || stat.equals(StatType.HEAT_RESISTANCE) || stat.equals(StatType.KNOCKBACK_RESISTANCE) || stat.equals(StatType.TOUGHNESS)) {
                    outDur.add(new StringTextComponent(dur.get(d-1)*100 + "%" + " -> " + dur.get(d)*100 + "%"));
                } else if (stat.equals(StatType.DURABILITY) || stat.equals(StatType.HARVEST_LEVEL) || stat.equals(StatType.ENCHANTABILITY)) {
                    outDur.add(new StringTextComponent(Math.round(dur.get(d-1)) + " -> " + Math.round(dur.get(d))));
                } else {
                    outDur.add(new StringTextComponent(dur.get(d-1) + " -> " + dur.get(d)));
                }

            }
        } else {
            outDur.add(new StringTextComponent(new TranslationTextComponent("block.rankine.material_testing_bench."+ stat.toString().toLowerCase(Locale.ROOT) +".error").getString()));
        }

        // Durability: {eqType}, {i0}% - {i1}%,
        return outDur;
    }

    private enum StatType {
        DURABILITY,
        MINING_SPEED,
        HARVEST_LEVEL,
        ENCHANTABILITY,
        ATTACK_DAMAGE,
        ATTACK_SPEED,
        CORROSION_RESISTANCE,
        HEAT_RESISTANCE,
        KNOCKBACK_RESISTANCE,
        TOUGHNESS
    }

    /*private List<StringTextComponent> checkDurabilityRange(ElementRecipe recipe) {
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
    }*/

}
