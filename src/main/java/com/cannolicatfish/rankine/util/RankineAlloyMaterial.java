package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.*;

public enum RankineAlloyMaterial {

    LIGNITE("rankine:materials/lignite",Arrays.asList(ModItems.LIGNITE, ModItems.getBlockAsItem(ModBlocks.LIGNITE_BLOCK)),
            Arrays.asList(PeriodicTableUtils.Element.CARBON, PeriodicTableUtils.Element.SULFUR),Arrays.asList(5,4),false),
    SUBBITUMINOUS("rankine:materials/subbituminous_coal",Arrays.asList(ModItems.SUBBITUMINOUS_COAL, ModItems.getBlockAsItem(ModBlocks.SUBBITUMINOUS_COAL_BLOCK)),
            Arrays.asList(PeriodicTableUtils.Element.CARBON, PeriodicTableUtils.Element.SULFUR),Arrays.asList(6,3),false),
    BITUMINOUS("rankine:materials/bituminous_coal",Arrays.asList(ModItems.BITUMINOUS_COAL, ModItems.getBlockAsItem(ModBlocks.BITUMINOUS_COAL_BLOCK)),
            Arrays.asList(PeriodicTableUtils.Element.CARBON, PeriodicTableUtils.Element.SULFUR),Arrays.asList(8,1),false),
    ANTHRACITE("rankine:materials/anthracite_coal",Arrays.asList(ModItems.ANTHRACITE_COAL, ModItems.getBlockAsItem(ModBlocks.ANTHRACITE_COAL_BLOCK)),
            Arrays.asList(PeriodicTableUtils.Element.CARBON, PeriodicTableUtils.Element.SULFUR),Arrays.asList(16,2),false),
    COKE("rankine:materials/coke",Arrays.asList(ModItems.COKE, ModItems.getBlockAsItem(ModBlocks.COKE_BLOCK)),Collections.singletonList(PeriodicTableUtils.Element.CARBON),
            Collections.singletonList(18),false),
    CHARCOAL("rankine:materials/charcoal",Collections.singletonList(Items.CHARCOAL),Collections.singletonList(PeriodicTableUtils.Element.CARBON),
            Collections.singletonList(9),false),
    PIG_IRON("rankine:materials/pig_iron",Arrays.asList(ModItems.PIG_IRON_INGOT, ModItems.getBlockAsItem(ModBlocks.PIG_IRON_BLOCK)),
            Arrays.asList(PeriodicTableUtils.Element.IRON, PeriodicTableUtils.Element.CARBON),Arrays.asList(7,2),false),
    WROUGHT_IRON("rankine:materials/wrought_iron",Arrays.asList(ModItems.ANTHRACITE_COAL, ModItems.getBlockAsItem(ModBlocks.ANTHRACITE_COAL_BLOCK)),
            Arrays.asList(PeriodicTableUtils.Element.IRON, PeriodicTableUtils.Element.CARBON),Arrays.asList(8,1),false);

    private final String tag;
    private final List<Item> items;
    private final List<PeriodicTableUtils.Element> elements;
    private final List<Integer> amounts;
    private final boolean containsNugget;

    private RankineAlloyMaterial(String tag, List<Item> items, List<PeriodicTableUtils.Element> elements, List<Integer> amounts, boolean containsNugget)
    {
        this.tag = tag;
        this.items = items;
        this.elements = elements;
        this.amounts = amounts;
        this.containsNugget = containsNugget;
    }

    private RankineAlloyMaterial(String tag, List<PeriodicTableUtils.Element> elements, List<Integer> amounts, boolean containsNugget)
    {
        this.tag = tag;
        this.items = null;
        this.elements = elements;
        this.amounts = amounts;
        this.containsNugget = containsNugget;
    }

    public static AbstractMap.SimpleEntry<List<PeriodicTableUtils.Element>,List<Integer>> getMaterial(Item item)
    {
        //System.out.println(item.getTags());
        for (RankineAlloyMaterial mat: RankineAlloyMaterial.values()) {
            if (mat.items.contains(item) && (mat.items.size() == 2 || mat.items.size() == 3))
            {
                //System.out.println("Material directly found!");
                switch (mat.items.indexOf(item)){
                    case 0:
                        if (mat.containsNugget)
                        {
                            List<Integer> newamts = new ArrayList<>();
                            for (int i: mat.amounts)
                            {
                                if (i % 9 == 0)
                                {
                                    newamts.add(i / 9);
                                }
                            }
                            if (!newamts.isEmpty())
                            {
                                return new AbstractMap.SimpleEntry<>(mat.elements,newamts);
                            }

                        } else {
                            return new AbstractMap.SimpleEntry<>(mat.elements,mat.amounts);
                        }
                    case 1:
                        if (mat.containsNugget)
                        {
                            return new AbstractMap.SimpleEntry<>(mat.elements,mat.amounts);
                        } else {
                            List<Integer> newamts = new ArrayList<>();
                            for (int i: mat.amounts)
                            {
                                newamts.add(i * 9);
                            }
                            return new AbstractMap.SimpleEntry<>(mat.elements,newamts);
                        }
                    case 2:
                        List<Integer> newamts = new ArrayList<>();
                        for (int i: mat.amounts)
                        {
                            newamts.add(i * 9);
                        }
                        return new AbstractMap.SimpleEntry<>(mat.elements,newamts);
                }
                return new AbstractMap.SimpleEntry<>(mat.elements,mat.amounts); // Make it so different items have different values
            }
            if (item.getTags().contains(new ResourceLocation(mat.tag))) {

                if (item.getRegistryName().toString().contains("nugget"))
                {
                    //System.out.println("Material (nugget) found!");
                    List<Integer> newamts = new ArrayList<>();
                    for (int i: mat.amounts)
                    {
                        if (i % 9 == 0)
                        {
                            newamts.add(i / 9);
                        }
                    }
                    if (!newamts.isEmpty())
                    {
                        return new AbstractMap.SimpleEntry<>(mat.elements,newamts);
                    }

                }
                else if (item.getRegistryName().toString().contains("block"))
                {
                    //System.out.println("Material (block) found!");
                    List<Integer> newamts = new ArrayList<>();
                    for (int i: mat.amounts)
                    {
                        newamts.add(i * 9);
                    }
                    return new AbstractMap.SimpleEntry<>(mat.elements,newamts);
                }
                else
                {
                    //System.out.println("Material (base item) found!");
                    return new AbstractMap.SimpleEntry<>(mat.elements,mat.amounts);
                }
            }
        }
        return new AbstractMap.SimpleEntry<>(null,null);
    }
}
