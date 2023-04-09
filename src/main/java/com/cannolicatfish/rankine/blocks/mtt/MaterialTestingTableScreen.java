package com.cannolicatfish.rankine.blocks.mtt;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.ElementEquation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MaterialTestingTableScreen extends AbstractContainerScreen<MaterialTestingTableContainer> {
    private ResourceLocation GUI = new ResourceLocation(ProjectRankine.MODID, "textures/gui/mtb.png");
    private final int textureXSize;
    private final int textureYSize;
    private ElementRecipe element = null;
    private AlloyingRecipe alloy = null;
    public MaterialTestingTableScreen(MaterialTestingTableContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        this.imageWidth = 256;
        this.imageHeight = 256;
        this.textureXSize = 256;
        this.textureYSize = 256;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.GUI);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        blit(p_230450_1_, x, y, 0, 0, this.imageWidth, this.imageHeight, this.textureXSize, this.textureYSize);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int p_230451_2_, int p_230451_3_) {
        Level worldIn = Minecraft.getInstance().level;
        if (worldIn != null)
        {
            if (element != this.menu.getElementRecipeForSlotItem(worldIn)) {
                element = this.menu.getElementRecipeForSlotItem(worldIn);
            }

            if (alloy != this.menu.getAlloyRecipeForSlotItem(worldIn)) {
                alloy = this.menu.getAlloyRecipeForSlotItem(worldIn);
            }
        }
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (element != null)
        {
            int stat = this.menu.getToolItem(worldIn);
            if (stat >= 0 && stat <= 9) {
                String statStr = StatType.values()[stat].toString();
                drawString(matrixStack,Minecraft.getInstance().font,new TranslatableComponent("block.rankine.material_testing_bench."+ statStr.toLowerCase(Locale.ROOT) +".test"),12,32,0xffffff);
                int ymod = 0;

                List<TextComponent> strings = checkStatRange(element,StatType.values()[stat]);
                for (TextComponent s : strings) {
                    int textX = s.getText().contains("->") ? 24 : 12;
                    drawString(matrixStack,Minecraft.getInstance().font,s,textX,44 + ymod,0xffffff);
                    ymod += 12;
                }

                drawString(matrixStack,Minecraft.getInstance().font,element.getName().toUpperCase(Locale.ROOT) + " (" + element.getSymbol() + ")",32,10,0xffffff);
                //drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("element."+ element.getId() +".preview"),32,20,0xffffff);
            } else if (stat == 10) {
                drawString(matrixStack,Minecraft.getInstance().font,new TranslatableComponent("block.rankine.material_testing_bench.enchantments.test"),12,32,0xffffff);
                int ymod = 0;
                List<TextComponent> strings = listEnchantments(element);
                for (TextComponent s : strings) {
                    int textX = s.getText().contains("->") ? 24 : 12;
                    drawString(matrixStack,Minecraft.getInstance().font,s,textX,44 + ymod,0xffffff);
                    ymod += 12;
                }
            } else if (stat == 11) {
                drawString(matrixStack,Minecraft.getInstance().font,new TranslatableComponent("block.rankine.material_testing_bench.exam.test"),12,32,0xffffff);
                int ymod = 0;
                List<TextComponent> strings = listFacts(element);
                for (TextComponent s : strings) {
                    int textX = s.getText().contains("->") ? 24 : 12;
                    drawString(matrixStack,Minecraft.getInstance().font,s,textX,44 + ymod,0xffffff);
                    ymod += 12;
                }
            } else {
                drawString(matrixStack,Minecraft.getInstance().font,"Insert a tool to test for properties.",12,44,0xffffff);
            }

            drawString(matrixStack,Minecraft.getInstance().font,element.getName().toUpperCase(Locale.ROOT) + " (" + element.getSymbol() + ")",32,10,0xffffff);
            //drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("element."+ element.getId() +".preview"),32,20,0xffffff);
        } else if (alloy != null) {
            int stat = this.menu.getToolItem(worldIn);
            if (stat >= 0 && stat <= 9) {
                String statStr = StatType.values()[stat].toString();
                drawString(matrixStack,Minecraft.getInstance().font,new TranslatableComponent("block.rankine.material_testing_bench."+ statStr.toLowerCase(Locale.ROOT) +".test"),12,32,0xffffff);
                int ymod = 0;
                ItemStack stack = this.menu.getSlotItem(worldIn);
                List<TextComponent> strings = checkAlloyDetails(stack,alloy,StatType.values()[stat],worldIn);
                for (TextComponent s : strings) {
                    int textX = s.getText().contains("->") ? 24 : 12;
                    drawString(matrixStack,Minecraft.getInstance().font,s,textX,44 + ymod,0xffffff);
                    ymod += 12;
                }

                drawString(matrixStack,Minecraft.getInstance().font,new TranslatableComponent(stack.getDescriptionId()).getString().toUpperCase(Locale.ROOT),32,10,0xffffff);
                //drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("alloy."+ alloy.getId() +".preview"),32,20,0xffffff);
            }
        }
    }

    private List<TextComponent> checkAlloyDetails(ItemStack alloy, AlloyingRecipe alloyRecipe, StatType stat, Level worldIn) {
        List<TextComponent> dets = new ArrayList<>();

        ListTag details = IAlloyItem.getElementNBT(alloy);
        boolean b = stat.equals(StatType.CORROSION_RESISTANCE) || stat.equals(StatType.HEAT_RESISTANCE) || stat.equals(StatType.KNOCKBACK_RESISTANCE) || stat.equals(StatType.TOUGHNESS);
        boolean b1 = stat.equals(StatType.DURABILITY) || stat.equals(StatType.HARVEST_LEVEL) || stat.equals(StatType.ENCHANTABILITY);
        for (int i = 0; i < details.size(); i++) {
            CompoundTag nbt = details.getCompound(i);
            ResourceLocation elem = new ResourceLocation(nbt.getString("id"));
            int amount = nbt.getShort("percent");
            if (worldIn != null) {
                Recipe<?> recipe = worldIn.getRecipeManager().byKey(elem).orElse(null);
                if (recipe instanceof ElementRecipe) {

                    ElementRecipe e = (ElementRecipe) recipe;
                    String additional;
                    float s = e.getStat(stat.ordinal(),amount);
                    if (b) {
                        additional = s*100 + "%";
                    } else if (b1) {
                        additional = String.valueOf(Math.round(s));
                    } else {
                        additional = String.valueOf(s);
                    }
                    dets.add(new TextComponent(new TranslatableComponent("block.rankine.material_testing_bench."+stat.toString().toLowerCase(Locale.ROOT)).getString() + ": " + e.getName().toUpperCase(Locale.ROOT) + " (" + amount + "%) = " + additional));
                }
            }
        }

        String additional;
        float s = alloyRecipe.getBonusStat(stat.ordinal());
        if (b) {
            additional = s*100 + "%";
        } else if (b1) {
            additional = String.valueOf(Math.round(s));
        } else {
            additional = String.valueOf(s);
        }
        dets.add(new TextComponent(new TranslatableComponent("block.rankine.material_testing_bench."+stat.toString().toLowerCase(Locale.ROOT)).getString() + ": BONUS = " + additional));
        return dets;
    }

    private List<TextComponent> checkStatRange(ElementRecipe recipe, StatType stat) {
        ElementEquation eq = recipe.getStatEquation(stat.ordinal());
        ElementEquation.FormulaType[] eqTypes = eq.getFormulaTypes();

        int[] breaks = eq.getBreaks();
        int[] bounds = Arrays.copyOf(new int[]{0},breaks.length+1);
        System.arraycopy(breaks,0,bounds,1,breaks.length);
        List<Float> dur = new ArrayList<>();
        for (int x : bounds) {
            dur.add(recipe.getStat(stat.ordinal(),x));
        }
        List<TextComponent> outDur = new ArrayList<>();
        if (dur.size() > 1) {
            for (int d = 1; d < dur.size(); d++) {
                outDur.add(new TextComponent(new TranslatableComponent("block.rankine.material_testing_bench."+stat.toString().toLowerCase(Locale.ROOT)).getString() + ": " + eqTypes[d - 1] + ", " + (bounds[d-1] + 1) + "% - " + bounds[d] + "%"));
                if (stat.equals(StatType.CORROSION_RESISTANCE) || stat.equals(StatType.HEAT_RESISTANCE) || stat.equals(StatType.KNOCKBACK_RESISTANCE) || stat.equals(StatType.TOUGHNESS)) {
                    outDur.add(new TextComponent(dur.get(d-1)*100 + "%" + " -> " + dur.get(d)*100 + "%"));
                } else if (stat.equals(StatType.DURABILITY) || stat.equals(StatType.HARVEST_LEVEL) || stat.equals(StatType.ENCHANTABILITY)) {
                    outDur.add(new TextComponent(Math.round(dur.get(d-1)) + " -> " + Math.round(dur.get(d))));
                } else {
                    outDur.add(new TextComponent(dur.get(d-1) + " -> " + dur.get(d)));
                }

            }
        } else {
            outDur.add(new TextComponent(new TranslatableComponent("block.rankine.material_testing_bench."+ stat.toString().toLowerCase(Locale.ROOT) +".error").getString()));
        }

        // Durability: {eqType}, {i0}% - {i1}%,
        return outDur;
    }

    private List<TextComponent> listEnchantments(ElementRecipe recipe) {
        List<String> enchantments = recipe.getEnchantments();
        List<String> enchantmentTypes = recipe.getEnchantmentTypes();
        List<Float> enchantmentFactors = recipe.getEnchantmentFactors();

        List<TextComponent> outDur = new ArrayList<>();
        if (enchantments.size() >= 1) {
            for (int d = 0; d < enchantments.size(); d++) {
                outDur.add(new TextComponent(enchantmentTypes.get(d) + ": " + enchantments.get(d) + " (Reqs: "+Math.round(100*enchantmentFactors.get(d))+"%)"));
            }
        } else {
            outDur.add(new TextComponent(new TranslatableComponent("block.rankine.material_testing_bench.enchantments.error").getString()));
        }
        return outDur;
    }

    private List<TextComponent> listFacts(ElementRecipe recipe) {
        ResourceLocation s = element.getId();

        List<TextComponent> outDur = new ArrayList<>();
        outDur.add(new TextComponent(new TranslatableComponent("element."+ s +".desc0").getString()));
        outDur.add(new TextComponent(new TranslatableComponent("element."+ s +".desc1").getString()));
        outDur.add(new TextComponent(new TranslatableComponent("element."+ s+".desc2").getString()));
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
