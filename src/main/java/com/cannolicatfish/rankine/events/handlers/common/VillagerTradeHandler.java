package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineVillagerProfessions;
import com.cannolicatfish.rankine.util.RankineVillagerTrades;
import net.minecraft.block.Block;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.VillagerTradesEvent;

import java.util.List;

public class VillagerTradeHandler {
    public static void addVillagerTrades( VillagerTradesEvent event) {
        List<VillagerTrades.ITrade> level1 = event.getTrades().get(1);
        List<VillagerTrades.ITrade> level2 = event.getTrades().get(2);
        List<VillagerTrades.ITrade> level3 = event.getTrades().get(3);
        List<VillagerTrades.ITrade> level4 = event.getTrades().get(4);
        List<VillagerTrades.ITrade> level5 = event.getTrades().get(5);

        if (event.getType() == RankineVillagerProfessions.METALLURGIST) {
            level1.add(new BasicTrade(1, new ItemStack(RankineItems.ALLOY_TEMPLATE.get()),12,1,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TIN_INGOT.get(), 8), new ItemStack(Items.EMERALD),12,2,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.COPPER_INGOT.get(), 4), new ItemStack(Items.EMERALD),12,2,0.05f));
            level2.add(new BasicTrade(1, new ItemStack(RankineItems.ZINC_INGOT.get(), 2),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.METEORIC_IRON.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.COIN.get(), 16), new ItemStack(Items.EMERALD),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.MANGANESE_INGOT.get(), 2),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.MOLYBDENUM_INGOT.get(), 2),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.VANADIUM_INGOT.get(), 2),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.NIOBIUM_INGOT.get(), 2),12,10,0.05f));
            level4.add(new BasicTrade(6, new ItemStack(RankineItems.ELEMENT_INDEXER.get()),12,15,0.05f));
            level5.add(new BasicTrade(10, new ItemStack(RankineItems.ORE_DETECTOR.get()),12,30,0.05f));
            level5.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.STEEL_PICKAXE.get(),"76Fe-15Cr-4V-4W-1C","rankine:alloying/damascus_steel_alloying","item.rankine.damascus_steel_alloying",15,3,30,0.2f));
            level5.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.STEEL_SWORD.get(),"76Fe-15Cr-4V-4W-1C","rankine:alloying/damascus_steel_alloying","item.rankine.damascus_steel_alloying",15,3,30,0.2f));
        } else if (event.getType() == RankineVillagerProfessions.MINERALOGIST) {
            level1.add(new BasicTrade(1, new ItemStack(RankineItems.STIBNITE.get()),12,1,0.05f));
            level1.add(new BasicTrade(1, new ItemStack(RankineItems.PROSPECTING_STICK.get()),12,1,0.05f));
            level1.add(new BasicTrade(1, new ItemStack(RankineItems.HARDNESS_TESTER.get()),12,1,0.05f));
            level2.add(new BasicTrade(1, new ItemStack(RankineItems.CHALCOPYRITE.get()),12,5,0.05f));
            level2.add(new BasicTrade(1, new ItemStack(RankineItems.BORAX.get()),12,5,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.MICA.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.AMPHIBOLE.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PLAGIOCLASE_FELDSPAR.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level3.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.INVAR_HAMMER.get(),"90Fe-10Ni","rankine:invar_alloying","item.rankine.invar_alloying",8,3,10,0.2f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.ZIRCON.get()),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.BAUXITE.get()),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.MAGNESITE.get()),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.VANADINITE.get()),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.PETALITE.get()),12,15,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.OLIVINE.get(), 4), new ItemStack(Items.EMERALD),12,20,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PYROXENE.get(), 4), new ItemStack(Items.EMERALD),12,20,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(RankineItems.WOLFRAMITE.get()),12,30,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(RankineItems.COBALTITE.get()),12,30,0.05f));
            level5.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.STEEL_HAMMER.get(),"76Fe-15Cr-4V-4W-1C","rankine:alloying/damascus_steel_alloying","item.rankine.damascus_steel_alloying",15,3,30,0.2f));
        } else if (event.getType() == RankineVillagerProfessions.BOTANIST) {
            level1.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("rankine:flowers"),Items.DANDELION,3,1,12,10,0.05f));
            level1.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("minecraft:tall_flowers"),Items.ROSE_BUSH,2,1,12,10,0.05f));
            level1.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("forge:berries"),RankineItems.ELDERBERRIES.get(),2,1,12,10,0.05f));
            level2.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("minecraft:saplings"),Items.OAK_SAPLING,2,1,12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.BAMBOO, 4),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.VINE, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.LILY_PAD, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.RED_MUSHROOM, 4),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.BROWN_MUSHROOM, 4),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.SEA_PICKLE, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.KELP, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.SUGAR_CANE, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.CACTUS, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.DEAD_BUSH, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.NETHER_WART, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.POISONOUS_POTATO, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.ALOE.get(), 4),12,30,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.WARPED_NYLIUM, 1),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.CRIMSON_NYLIUM, 1),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.BRAIN_CORAL, 2),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.BUBBLE_CORAL, 2),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.FIRE_CORAL, 2),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.HORN_CORAL, 2),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.TUBE_CORAL, 2),12,15,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(Items.SHROOMLIGHT, 2),12,15,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(Items.MYCELIUM, 1),12,30,0.05f));
            level5.add(new BasicTrade(5, new ItemStack(Items.CHORUS_FLOWER, 1),12,30,0.05f));
            level5.add(new BasicTrade(10, new ItemStack(Items.WITHER_ROSE),12,30,0.05f));

        } else if (event.getType() == RankineVillagerProfessions.GEM_CUTTER) {
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.AQUAMARINE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.OPAL.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.GARNET.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.RUBY.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.SAPPHIRE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PERIDOT.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TOPAZ.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PEARL.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TOURMALINE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TIGER_IRON.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.LABRADORITE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.RHODONITE.get(), 1), new ItemStack(Items.EMERALD, 3),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.RHODOCHROSITE.get(), 1), new ItemStack(Items.EMERALD, 3),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.CHROME_ENSTATITE.get(), 1), new ItemStack(Items.EMERALD, 3),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.FLUORITE.get(), 3), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level4.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("forge:gems"),RankineItems.OPAL.get(),1,12,16,10,0.05f));
            level5.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.LONSDALEITE_DIAMOND.get(), 1), new ItemStack(Items.EMERALD, 6),12,20,0.05f));
            level5.add(new BasicTrade(20, new ItemStack(RankineItems.LONSDALEITE_DIAMOND.get(), 1),12,50,0.05f));
            level5.add((entity,rand) -> new MerchantOffer(new ItemStack(Items.NETHER_STAR, 1), new ItemStack(Items.EMERALD, 64),12,50,0.05f));

        } else if (event.getType() == RankineVillagerProfessions.ROCK_COLLECTOR) {
            level1.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("forge:stone"), RankineItems.ANORTHOSITE.get(), 16, 1, 16, 10, 0.05f));
            List<Block> rocks = BlockTags.getCollection().get(new ResourceLocation("forge:stone")).getAllElements();
            if (!rocks.isEmpty()) {
                for (Block rock : rocks) {
                    level2.add((entity, rand) -> new MerchantOffer(new ItemStack(rock.asItem(), 24), new ItemStack(Items.EMERALD, 1), 16, 10, 0.05f));
                }
            } else {
                level2.add((entity, rand) -> new MerchantOffer(new ItemStack(Items.SANDSTONE, 24), new ItemStack(Items.EMERALD, 1), 16, 10, 0.05f));
                level2.add((entity, rand) -> new MerchantOffer(new ItemStack(Items.RED_SANDSTONE, 12), new ItemStack(Items.EMERALD, 1), 16, 10, 0.05f));
            }
            level3.add(new BasicTrade(1, new ItemStack(Items.OBSIDIAN, 1), 12, 10, 0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.NETHERRACK, 8),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.END_STONE, 2),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.PURPUR_BLOCK, 2),12,10,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.PHOSPHORITE.get(), 2),12,10,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.IRONSTONE.get(), 2),12,10,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.METEORITE.get(), 2),12,10,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.ENSTATITE_CHONDRITE.get(), 2),12,10,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(RankineItems.ROMAN_CONCRETE.get(), 1),24,10,0.05f));
        }

        if (Config.GENERAL.VILLAGER_TRADES.get()) {
            if (event.getType() == VillagerProfession.MASON) {
                event.getTrades().get(1).add(new BasicTrade(1,new ItemStack(RankineItems.MORTAR.get(), 16),16,1,0.05f));
                event.getTrades().get(1).add(new BasicTrade(1,new ItemStack(RankineItems.REFRACTORY_BRICK.get(), 10),16,1,0.05f));
            } else if (event.getType() == VillagerProfession.CLERIC) {
                event.getTrades().get(1).add(new BasicTrade(1, new ItemStack(RankineItems.SALTPETER.get(),2),12,1,0.05f));
            }
        }

    }
}
