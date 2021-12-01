package com.cannolicatfish.rankine.blocks.mtt;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.ElementEquation;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;


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
    private AlloyingRecipe alloy = null;
    public MaterialTestingTableScreen(MaterialTestingTableContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.xSize = 256;
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
        World worldIn = Minecraft.getInstance().world;
        if (worldIn != null)
        {
            if (element != this.container.getElementRecipeForSlotItem(worldIn)) {
                element = this.container.getElementRecipeForSlotItem(worldIn);
            }

            if (alloy != this.container.getAlloyRecipeForSlotItem(worldIn)) {
                alloy = this.container.getAlloyRecipeForSlotItem(worldIn);
            }
        }
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (element != null)
        {
            int stat = this.container.getToolItem(worldIn);
            if (stat >= 0 && stat <= 9) {
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
                //drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("element."+ element.getId() +".preview"),32,20,0xffffff);
            } else if (stat == 10) {
                drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("block.rankine.material_testing_bench.enchantments.test"),12,32,0xffffff);
                int ymod = 0;
                List<StringTextComponent> strings = listEnchantments(element);
                for (StringTextComponent s : strings) {
                    int textX = s.getText().contains("->") ? 24 : 12;
                    drawString(matrixStack,Minecraft.getInstance().fontRenderer,s,textX,44 + ymod,0xffffff);
                    ymod += 12;
                }
            } else if (stat == 11) {
                drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("block.rankine.material_testing_bench.exam.test"),12,32,0xffffff);
                int ymod = 0;
                List<StringTextComponent> strings = listFacts(element);
                for (StringTextComponent s : strings) {
                    int textX = s.getText().contains("->") ? 24 : 12;
                    drawString(matrixStack,Minecraft.getInstance().fontRenderer,s,textX,44 + ymod,0xffffff);
                    ymod += 12;
                }
            } else {
                drawString(matrixStack,Minecraft.getInstance().fontRenderer,"Insert a tool to test for properties.",12,44,0xffffff);
            }

            drawString(matrixStack,Minecraft.getInstance().fontRenderer,element.getName().toUpperCase(Locale.ROOT) + " (" + element.getSymbol() + ")",32,10,0xffffff);
            //drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("element."+ element.getId() +".preview"),32,20,0xffffff);
        } else if (alloy != null) {
            int stat = this.container.getToolItem(worldIn);
            if (stat >= 0 && stat <= 9) {
                String statStr = StatType.values()[stat].toString();
                drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("block.rankine.material_testing_bench."+ statStr.toLowerCase(Locale.ROOT) +".test"),12,32,0xffffff);
                int ymod = 0;
                ItemStack stack = this.container.getSlotItem(worldIn);
                List<StringTextComponent> strings = checkAlloyDetails(stack,alloy,StatType.values()[stat],worldIn);
                for (StringTextComponent s : strings) {
                    int textX = s.getText().contains("->") ? 24 : 12;
                    drawString(matrixStack,Minecraft.getInstance().fontRenderer,s,textX,44 + ymod,0xffffff);
                    ymod += 12;
                }

                drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent(stack.getTranslationKey()).getString().toUpperCase(Locale.ROOT),32,10,0xffffff);
                //drawString(matrixStack,Minecraft.getInstance().fontRenderer,new TranslationTextComponent("alloy."+ alloy.getId() +".preview"),32,20,0xffffff);
            }
        }
    }

    private List<StringTextComponent> checkAlloyDetails(ItemStack alloy, AlloyingRecipe alloyRecipe, StatType stat, World worldIn) {
        List<StringTextComponent> dets = new ArrayList<>();

        ListNBT details = IAlloyItem.getElementNBT(alloy);
        boolean b = stat.equals(StatType.CORROSION_RESISTANCE) || stat.equals(StatType.HEAT_RESISTANCE) || stat.equals(StatType.KNOCKBACK_RESISTANCE) || stat.equals(StatType.TOUGHNESS);
        boolean b1 = stat.equals(StatType.DURABILITY) || stat.equals(StatType.HARVEST_LEVEL) || stat.equals(StatType.ENCHANTABILITY);
        for (int i = 0; i < details.size(); i++) {
            CompoundNBT nbt = details.getCompound(i);
            ResourceLocation elem = new ResourceLocation(nbt.getString("id"));
            int amount = nbt.getShort("percent");
            if (worldIn != null) {
                IRecipe<?> recipe = worldIn.getRecipeManager().getRecipe(elem).orElse(null);
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
                    dets.add(new StringTextComponent(new TranslationTextComponent("block.rankine.material_testing_bench."+stat.toString().toLowerCase(Locale.ROOT)).getString() + ": " + e.getName().toUpperCase(Locale.ROOT) + " (" + amount + "%) = " + additional));
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
        dets.add(new StringTextComponent(new TranslationTextComponent("block.rankine.material_testing_bench."+stat.toString().toLowerCase(Locale.ROOT)).getString() + ": BONUS = " + additional));
        return dets;
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

    private List<StringTextComponent> listEnchantments(ElementRecipe recipe) {
        List<String> enchantments = recipe.getEnchantments();
        List<String> enchantmentTypes = recipe.getEnchantmentTypes();
        List<Float> enchantmentFactors = recipe.getEnchantmentFactors();

        List<StringTextComponent> outDur = new ArrayList<>();
        if (enchantments.size() >= 1) {
            for (int d = 0; d < enchantments.size(); d++) {
                outDur.add(new StringTextComponent(enchantmentTypes.get(d) + ": " + enchantments.get(d) + " (Reqs: "+Math.round(100*enchantmentFactors.get(d))+"%)"));
            }
        } else {
            outDur.add(new StringTextComponent(new TranslationTextComponent("block.rankine.material_testing_bench.enchantments.error").getString()));
        }
        return outDur;
    }

    private List<StringTextComponent> listFacts(ElementRecipe recipe) {
        ResourceLocation s = element.getId();

        List<StringTextComponent> outDur = new ArrayList<>();
        outDur.add(new StringTextComponent(new TranslationTextComponent("element."+ s +".desc0").getString()));
        outDur.add(new StringTextComponent(new TranslationTextComponent("element."+ s +".desc1").getString()));
        outDur.add(new StringTextComponent(new TranslationTextComponent("element."+ s+".desc2").getString()));
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
