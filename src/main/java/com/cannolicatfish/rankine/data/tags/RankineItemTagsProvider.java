package com.cannolicatfish.rankine.data.tags;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class RankineItemTagsProvider extends ItemTagsProvider {
    public RankineItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blocks, ExistingFileHelper fileHelper) {
        super(output, registries, blocks, ProjectRankine.MODID, fileHelper);
    }

    public String getName() {
        return "Project Rankine - Item Tags";
    }


    @Override
    protected void addTags(HolderLookup.Provider provider) {


        copy(RankineTags.Blocks.ORES_LEAD, RankineTags.Items.ORES_LEAD);
        copy(RankineTags.Blocks.ORES_SILVER, RankineTags.Items.ORES_SILVER);
        copy(RankineTags.Blocks.ORES_BISMUTH, RankineTags.Items.ORES_BISMUTH);
        copy(RankineTags.Blocks.ORES_TUNGSTEN, RankineTags.Items.ORES_TUNGSTEN);
        copy(RankineTags.Blocks.ORES_ALUMINUM, RankineTags.Items.ORES_ALUMINUM);
        copy(RankineTags.Blocks.ORES_TIN, RankineTags.Items.ORES_TIN);
        copy(RankineTags.Blocks.ORES_TITANIUM, RankineTags.Items.ORES_TITANIUM);
        copy(RankineTags.Blocks.ORES_MAGNESIUM, RankineTags.Items.ORES_MAGNESIUM);
        copy(RankineTags.Blocks.ORES_MANGANESE, RankineTags.Items.ORES_MANGANESE);
        copy(RankineTags.Blocks.ORES_ZINC, RankineTags.Items.ORES_ZINC);
        copy(RankineTags.Blocks.ORES_STRONTIUM, RankineTags.Items.ORES_STRONTIUM);
        copy(RankineTags.Blocks.ORES_LITHIUM, RankineTags.Items.ORES_LITHIUM);
        copy(RankineTags.Blocks.ORES_SODIUM, RankineTags.Items.ORES_SODIUM);
        copy(RankineTags.Blocks.ORES_ARSENIC, RankineTags.Items.ORES_ARSENIC);
        copy(RankineTags.Blocks.ORES_GALLIUM, RankineTags.Items.ORES_GALLIUM);
        copy(RankineTags.Blocks.ORES_INDIUM, RankineTags.Items.ORES_INDIUM);
        copy(RankineTags.Blocks.ORES_SELENIUM, RankineTags.Items.ORES_SELENIUM);
        copy(RankineTags.Blocks.ORES_TELLURIUM, RankineTags.Items.ORES_TELLURIUM);
        copy(RankineTags.Blocks.ORES_SULFUR, RankineTags.Items.ORES_SULFUR);
        copy(RankineTags.Blocks.ORES_MERCURY, RankineTags.Items.ORES_MERCURY);
        copy(RankineTags.Blocks.ORES_CHROMIUM, RankineTags.Items.ORES_CHROMIUM);
        copy(RankineTags.Blocks.ORES_COBALT, RankineTags.Items.ORES_COBALT);
        copy(RankineTags.Blocks.ORES_NICKEL, RankineTags.Items.ORES_NICKEL);
        copy(RankineTags.Blocks.ORES_YTTRIUM, RankineTags.Items.ORES_YTTRIUM);
        copy(RankineTags.Blocks.ORES_ZIRCONIUM, RankineTags.Items.ORES_ZIRCONIUM);
        copy(RankineTags.Blocks.ORES_MOLYBDENUM, RankineTags.Items.ORES_MOLYBDENUM);
        copy(RankineTags.Blocks.ORES_CADMIUM, RankineTags.Items.ORES_CADMIUM);
        copy(RankineTags.Blocks.ORES_URANIUM, RankineTags.Items.ORES_URANIUM);
        copy(RankineTags.Blocks.ORES_ANTIMONY, RankineTags.Items.ORES_ANTIMONY);
        copy(RankineTags.Blocks.ORES_RHENIUM, RankineTags.Items.ORES_RHENIUM);
        copy(RankineTags.Blocks.ORES_PHOSPHORUS, RankineTags.Items.ORES_PHOSPHORUS);
        copy(RankineTags.Blocks.ORES_GRAPHITE, RankineTags.Items.ORES_GRAPHITE);
        copy(RankineTags.Blocks.ORES_SALT, RankineTags.Items.ORES_SALT);
        copy(BlockTags.COPPER_ORES, ItemTags.COPPER_ORES);
        copy(BlockTags.IRON_ORES, ItemTags.IRON_ORES);
        copy(BlockTags.GOLD_ORES, ItemTags.GOLD_ORES);
        copy(BlockTags.COAL_ORES, ItemTags.COAL_ORES);
        copy(BlockTags.DIAMOND_ORES, ItemTags.DIAMOND_ORES);
        copy(BlockTags.REDSTONE_ORES, ItemTags.REDSTONE_ORES);
        copy(BlockTags.LAPIS_ORES, ItemTags.LAPIS_ORES);
        copy(BlockTags.EMERALD_ORES, ItemTags.EMERALD_ORES);
        copy(Tags.Blocks.ORES_QUARTZ, Tags.Items.ORES_QUARTZ);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);

        copy(RankineTags.Blocks.STONES_DACITE, RankineTags.Items.STONES_DACITE);
        copy(RankineTags.Blocks.STONES_ANDESITE, RankineTags.Items.STONES_ANDESITE);
        copy(RankineTags.Blocks.STONES_BASALT, RankineTags.Items.STONES_BASALT);
        copy(RankineTags.Blocks.STONES_GRANITE, RankineTags.Items.STONES_GRANITE);
        copy(RankineTags.Blocks.STONES_SANDSTONE, RankineTags.Items.STONES_SANDSTONE);
        copy(Tags.Blocks.SANDSTONE, Tags.Items.SANDSTONE);
        copy(RankineTags.Blocks.STONES_PEGMATITE, RankineTags.Items.STONES_PEGMATITE);
        copy(RankineTags.Blocks.STONES_BRECCIA, RankineTags.Items.STONES_BRECCIA);
        copy(RankineTags.Blocks.STONES_PERIDOTITE, RankineTags.Items.STONES_PERIDOTITE);
        copy(RankineTags.Blocks.STONES_PHYLITE, RankineTags.Items.STONES_PHYLITE);
        copy(RankineTags.Blocks.STONES_PORPHYRY, RankineTags.Items.STONES_PORPHYRY);
        copy(RankineTags.Blocks.STONES_PUMICE, RankineTags.Items.STONES_PUMICE);
        copy(RankineTags.Blocks.STONES_SCORIA, RankineTags.Items.STONES_SCORIA);
        copy(RankineTags.Blocks.STONES_SCHIST, RankineTags.Items.STONES_SCHIST);
        copy(RankineTags.Blocks.STONES_DOLOMITE, RankineTags.Items.STONES_DOLOMITE);
        copy(RankineTags.Blocks.STONES_MARBLE, RankineTags.Items.STONES_MARBLE);
        copy(RankineTags.Blocks.STONES_GABBRO, RankineTags.Items.STONES_GABBRO);
        copy(RankineTags.Blocks.STONES_MARLSTONE, RankineTags.Items.STONES_MARLSTONE);
        copy(RankineTags.Blocks.STONES_MUDSTONE, RankineTags.Items.STONES_MUDSTONE);
        copy(RankineTags.Blocks.STONES_RHYOLITE, RankineTags.Items.STONES_RHYOLITE);
        copy(RankineTags.Blocks.STONES_SYENITE, RankineTags.Items.STONES_SYENITE);
        copy(RankineTags.Blocks.STONES_PHONOLITE, RankineTags.Items.STONES_PHONOLITE);
        copy(RankineTags.Blocks.STONES_GRANODIORITE, RankineTags.Items.STONES_GRANODIORITE);
        copy(RankineTags.Blocks.STONES_KIMBERLITE, RankineTags.Items.STONES_KIMBERLITE);
        copy(RankineTags.Blocks.STONES_KOMATIITE, RankineTags.Items.STONES_KOMATIITE);
        copy(RankineTags.Blocks.STONES_GNEISS, RankineTags.Items.STONES_GNEISS);
        copy(RankineTags.Blocks.STONES_LIMESTONE, RankineTags.Items.STONES_LIMESTONE);
        copy(RankineTags.Blocks.STONES_SKARN, RankineTags.Items.STONES_SKARN);
        copy(RankineTags.Blocks.STONES_MARIPOSITE, RankineTags.Items.STONES_MARIPOSITE);
        copy(RankineTags.Blocks.STONES_QUARTZITE, RankineTags.Items.STONES_QUARTZITE);
        copy(RankineTags.Blocks.STONES_CHALK, RankineTags.Items.STONES_CHALK);
        copy(RankineTags.Blocks.STONES_SOAPSTONE, RankineTags.Items.STONES_SOAPSTONE);
        copy(RankineTags.Blocks.STONES_SHALE, RankineTags.Items.STONES_SHALE);
        copy(RankineTags.Blocks.STONES_SILTSTONE, RankineTags.Items.STONES_SILTSTONE);
        copy(RankineTags.Blocks.STONES_SERPENTINITE, RankineTags.Items.STONES_SERPENTINITE);
        copy(RankineTags.Blocks.STONES_ECLOGITE, RankineTags.Items.STONES_ECLOGITE);
        copy(RankineTags.Blocks.STONES_SLATE, RankineTags.Items.STONES_SLATE);
        copy(RankineTags.Blocks.STONES_SHONKINITE, RankineTags.Items.STONES_SHONKINITE);


        /*for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            copy(BlockTags.create(new ResourceLocation("rankine", Wood.getBaseName() + "_logs")), ItemTags.create(new ResourceLocation("rankine", Wood.getBaseName() + "_logs")));
        }
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(RankineTags.Blocks.STRIPPED_LOGS, RankineTags.Items.STRIPPED_LOGS);
        copy(RankineTags.Blocks.STRIPPED_WOOD, RankineTags.Items.STRIPPED_WOOD);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.LOGS, ItemTags.LOGS);*/


        copy(Tags.Blocks.STORAGE_BLOCKS_COAL, Tags.Items.STORAGE_BLOCKS_COAL);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_COKE, RankineTags.Items.STORAGE_BLOCKS_COKE);
        copy(Tags.Blocks.STORAGE_BLOCKS_DIAMOND, Tags.Items.STORAGE_BLOCKS_DIAMOND);

        copy(RankineTags.Blocks.STORAGE_BLOCKS_GRAPHITE, RankineTags.Items.STORAGE_BLOCKS_GRAPHITE);

        copy(RankineTags.Blocks.STORAGE_BLOCKS_HYDROGEN, RankineTags.Items.STORAGE_BLOCKS_HYDROGEN);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_HELIUM, RankineTags.Items.STORAGE_BLOCKS_HELIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_LITHIUM, RankineTags.Items.STORAGE_BLOCKS_LITHIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_BERYLLIUM, RankineTags.Items.STORAGE_BLOCKS_BERYLLIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_BORON, RankineTags.Items.STORAGE_BLOCKS_BORON);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CARBON, RankineTags.Items.STORAGE_BLOCKS_CARBON);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NITROGEN, RankineTags.Items.STORAGE_BLOCKS_NITROGEN);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_OXYGEN, RankineTags.Items.STORAGE_BLOCKS_OXYGEN);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_FLUORINE, RankineTags.Items.STORAGE_BLOCKS_FLUORINE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NEON, RankineTags.Items.STORAGE_BLOCKS_NEON);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_SODIUM, RankineTags.Items.STORAGE_BLOCKS_SODIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_MAGNESIUM, RankineTags.Items.STORAGE_BLOCKS_MAGNESIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ALUMINUM, RankineTags.Items.STORAGE_BLOCKS_ALUMINUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_SILICON, RankineTags.Items.STORAGE_BLOCKS_SILICON);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_PHOSPHORUS, RankineTags.Items.STORAGE_BLOCKS_PHOSPHORUS);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_SULFUR, RankineTags.Items.STORAGE_BLOCKS_SULFUR);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CHLORINE, RankineTags.Items.STORAGE_BLOCKS_CHLORINE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ARGON, RankineTags.Items.STORAGE_BLOCKS_ARGON);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_POTASSIUM, RankineTags.Items.STORAGE_BLOCKS_POTASSIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CALCIUM, RankineTags.Items.STORAGE_BLOCKS_CALCIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_SCANDIUM, RankineTags.Items.STORAGE_BLOCKS_SCANDIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TITANIUM, RankineTags.Items.STORAGE_BLOCKS_TITANIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_VANADIUM, RankineTags.Items.STORAGE_BLOCKS_VANADIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CHROMIUM, RankineTags.Items.STORAGE_BLOCKS_CHROMIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_MANGANESE, RankineTags.Items.STORAGE_BLOCKS_MANGANESE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_COBALT, RankineTags.Items.STORAGE_BLOCKS_COBALT);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NICKEL, RankineTags.Items.STORAGE_BLOCKS_NICKEL);
        //copy(RankineTags.Blocks.STORAGE_BLOCKS_COPPER, RankineTags.Items.STORAGE_BLOCKS_COPPER);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ZINC, RankineTags.Items.STORAGE_BLOCKS_ZINC);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_GALLIUM, RankineTags.Items.STORAGE_BLOCKS_GALLIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_GERMANIUM, RankineTags.Items.STORAGE_BLOCKS_GERMANIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ARSENIC, RankineTags.Items.STORAGE_BLOCKS_ARSENIC);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_SELENIUM, RankineTags.Items.STORAGE_BLOCKS_SELENIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_BROMINE, RankineTags.Items.STORAGE_BLOCKS_BROMINE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_KRYPTON, RankineTags.Items.STORAGE_BLOCKS_KRYPTON);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_RUBIDIUM, RankineTags.Items.STORAGE_BLOCKS_RUBIDIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_STRONTIUM, RankineTags.Items.STORAGE_BLOCKS_STRONTIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_YTTRIUM, RankineTags.Items.STORAGE_BLOCKS_YTTRIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ZIRCONIUM, RankineTags.Items.STORAGE_BLOCKS_ZIRCONIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NIOBIUM, RankineTags.Items.STORAGE_BLOCKS_NIOBIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_MOLYBDENUM, RankineTags.Items.STORAGE_BLOCKS_MOLYBDENUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TECHNETIUM, RankineTags.Items.STORAGE_BLOCKS_TECHNETIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_RUTHENIUM, RankineTags.Items.STORAGE_BLOCKS_RUTHENIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_RHODIUM, RankineTags.Items.STORAGE_BLOCKS_RHODIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_PALLADIUM, RankineTags.Items.STORAGE_BLOCKS_PALLADIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_SILVER, RankineTags.Items.STORAGE_BLOCKS_SILVER);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CADMIUM, RankineTags.Items.STORAGE_BLOCKS_CADMIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_INDIUM, RankineTags.Items.STORAGE_BLOCKS_INDIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TIN, RankineTags.Items.STORAGE_BLOCKS_TIN);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ANTIMONY, RankineTags.Items.STORAGE_BLOCKS_ANTIMONY);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TELLURIUM, RankineTags.Items.STORAGE_BLOCKS_TELLURIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_IODINE, RankineTags.Items.STORAGE_BLOCKS_IODINE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_XENON, RankineTags.Items.STORAGE_BLOCKS_XENON);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CESIUM, RankineTags.Items.STORAGE_BLOCKS_CESIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_BARIUM, RankineTags.Items.STORAGE_BLOCKS_BARIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_LANTHANUM, RankineTags.Items.STORAGE_BLOCKS_LANTHANUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CERIUM, RankineTags.Items.STORAGE_BLOCKS_CERIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_PRASEODYMIUM, RankineTags.Items.STORAGE_BLOCKS_PRASEODYMIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NEODYMIUM, RankineTags.Items.STORAGE_BLOCKS_NEODYMIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_PROMETHIUM, RankineTags.Items.STORAGE_BLOCKS_PROMETHIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_SAMARIUM, RankineTags.Items.STORAGE_BLOCKS_SAMARIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_EUROPIUM, RankineTags.Items.STORAGE_BLOCKS_EUROPIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_GADOLINIUM, RankineTags.Items.STORAGE_BLOCKS_GADOLINIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TERBIUM, RankineTags.Items.STORAGE_BLOCKS_TERBIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_DYSPROSIUM, RankineTags.Items.STORAGE_BLOCKS_DYSPROSIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_HOLMIUM, RankineTags.Items.STORAGE_BLOCKS_HOLMIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ERBIUM, RankineTags.Items.STORAGE_BLOCKS_ERBIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_THULIUM, RankineTags.Items.STORAGE_BLOCKS_THULIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_YTTERBIUM, RankineTags.Items.STORAGE_BLOCKS_YTTERBIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_LUTETIUM, RankineTags.Items.STORAGE_BLOCKS_LUTETIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_HAFNIUM, RankineTags.Items.STORAGE_BLOCKS_HAFNIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TANTALUM, RankineTags.Items.STORAGE_BLOCKS_TANTALUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TUNGSTEN, RankineTags.Items.STORAGE_BLOCKS_TUNGSTEN);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_RHENIUM, RankineTags.Items.STORAGE_BLOCKS_RHENIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_OSMIUM, RankineTags.Items.STORAGE_BLOCKS_OSMIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_IRIDIUM, RankineTags.Items.STORAGE_BLOCKS_IRIDIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_PLATINUM, RankineTags.Items.STORAGE_BLOCKS_PLATINUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_MERCURY, RankineTags.Items.STORAGE_BLOCKS_MERCURY);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_THALLIUM, RankineTags.Items.STORAGE_BLOCKS_THALLIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_LEAD, RankineTags.Items.STORAGE_BLOCKS_LEAD);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_BISMUTH, RankineTags.Items.STORAGE_BLOCKS_BISMUTH);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_POLONIUM, RankineTags.Items.STORAGE_BLOCKS_POLONIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ASTATINE, RankineTags.Items.STORAGE_BLOCKS_ASTATINE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_RADON, RankineTags.Items.STORAGE_BLOCKS_RADON);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_FRANCIUM, RankineTags.Items.STORAGE_BLOCKS_FRANCIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_RADIUM, RankineTags.Items.STORAGE_BLOCKS_RADIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ACTINIUM, RankineTags.Items.STORAGE_BLOCKS_ACTINIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_THORIUM, RankineTags.Items.STORAGE_BLOCKS_THORIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_PROTACTINIUM, RankineTags.Items.STORAGE_BLOCKS_PROTACTINIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_URANIUM, RankineTags.Items.STORAGE_BLOCKS_URANIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NEPTUNIUM, RankineTags.Items.STORAGE_BLOCKS_NEPTUNIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_PLUTONIUM, RankineTags.Items.STORAGE_BLOCKS_PLUTONIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_AMERICIUM, RankineTags.Items.STORAGE_BLOCKS_AMERICIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CURIUM, RankineTags.Items.STORAGE_BLOCKS_CURIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_BERKELIUM, RankineTags.Items.STORAGE_BLOCKS_BERKELIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CALIFORNIUM, RankineTags.Items.STORAGE_BLOCKS_CALIFORNIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_EINSTEINIUM, RankineTags.Items.STORAGE_BLOCKS_EINSTEINIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_FERMIUM, RankineTags.Items.STORAGE_BLOCKS_FERMIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_MENDELEVIUM, RankineTags.Items.STORAGE_BLOCKS_MENDELEVIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NOBELIUM, RankineTags.Items.STORAGE_BLOCKS_NOBELIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_LAWRENCIUM, RankineTags.Items.STORAGE_BLOCKS_LAWRENCIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_RUTHERFORDIUM, RankineTags.Items.STORAGE_BLOCKS_RUTHERFORDIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_DUBNIUM, RankineTags.Items.STORAGE_BLOCKS_DUBNIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_SEABORGIUM, RankineTags.Items.STORAGE_BLOCKS_SEABORGIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_BOHRIUM, RankineTags.Items.STORAGE_BLOCKS_BOHRIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_HASSIUM, RankineTags.Items.STORAGE_BLOCKS_HASSIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_MEITNERIUM, RankineTags.Items.STORAGE_BLOCKS_MEITNERIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_DARMSTADTIUM, RankineTags.Items.STORAGE_BLOCKS_DARMSTADTIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ROENTGENIUM, RankineTags.Items.STORAGE_BLOCKS_ROENTGENIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_COPERNICIUM, RankineTags.Items.STORAGE_BLOCKS_COPERNICIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NIHONIUM, RankineTags.Items.STORAGE_BLOCKS_NIHONIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_FLEROVIUM, RankineTags.Items.STORAGE_BLOCKS_FLEROVIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_MOSCOVIUM, RankineTags.Items.STORAGE_BLOCKS_MOSCOVIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_LIVERMORIUM, RankineTags.Items.STORAGE_BLOCKS_LIVERMORIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TENNESSINE, RankineTags.Items.STORAGE_BLOCKS_TENNESSINE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_OGANESSON, RankineTags.Items.STORAGE_BLOCKS_OGANESSON);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ENDOSITUM, RankineTags.Items.STORAGE_BLOCKS_ENDOSITUM);

        copy(RankineTags.Blocks.STORAGE_BLOCKS_PEWTER, RankineTags.Items.STORAGE_BLOCKS_PEWTER);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_BRONZE, RankineTags.Items.STORAGE_BLOCKS_BRONZE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_BRASS, RankineTags.Items.STORAGE_BLOCKS_BRASS);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CAST_IRON, RankineTags.Items.STORAGE_BLOCKS_CAST_IRON);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_INVAR, RankineTags.Items.STORAGE_BLOCKS_INVAR);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CUPRONICKEL, RankineTags.Items.STORAGE_BLOCKS_CUPRONICKEL);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CONSTANTAN, RankineTags.Items.STORAGE_BLOCKS_CONSTANTAN);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_DURALUMIN, RankineTags.Items.STORAGE_BLOCKS_DURALUMIN);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_MAGNESIUM_ALLOY, RankineTags.Items.STORAGE_BLOCKS_MAGNESIUM_ALLOY);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_STERLING_SILVER, RankineTags.Items.STORAGE_BLOCKS_STERLING_SILVER);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NICKEL_SILVER, RankineTags.Items.STORAGE_BLOCKS_NICKEL_SILVER);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ALNICO, RankineTags.Items.STORAGE_BLOCKS_ALNICO);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_STEEL, RankineTags.Items.STORAGE_BLOCKS_STEEL);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_STAINLESS_STEEL, RankineTags.Items.STORAGE_BLOCKS_STAINLESS_STEEL);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NITINOL, RankineTags.Items.STORAGE_BLOCKS_NITINOL);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ROSE_METAL, RankineTags.Items.STORAGE_BLOCKS_ROSE_METAL);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_MISCHMETAL, RankineTags.Items.STORAGE_BLOCKS_MISCHMETAL);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_FERROCERIUM, RankineTags.Items.STORAGE_BLOCKS_FERROCERIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_GALINSTAN, RankineTags.Items.STORAGE_BLOCKS_GALINSTAN);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_OSMIRIDIUM, RankineTags.Items.STORAGE_BLOCKS_OSMIRIDIUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_SODIUM_POTASSIUM_ALLOY, RankineTags.Items.STORAGE_BLOCKS_SODIUM_POTASSIUM_ALLOY);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NEPTUNIUM_ALLOY, RankineTags.Items.STORAGE_BLOCKS_NEPTUNIUM_ALLOY);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_AMALGAM, RankineTags.Items.STORAGE_BLOCKS_AMALGAM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ENDER_AMALGAM, RankineTags.Items.STORAGE_BLOCKS_ENDER_AMALGAM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ROSE_GOLD, RankineTags.Items.STORAGE_BLOCKS_ROSE_GOLD);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_GREEN_GOLD, RankineTags.Items.STORAGE_BLOCKS_GREEN_GOLD);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ELECTRUM, RankineTags.Items.STORAGE_BLOCKS_ELECTRUM);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_PURPLE_GOLD, RankineTags.Items.STORAGE_BLOCKS_PURPLE_GOLD);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_WHITE_GOLD, RankineTags.Items.STORAGE_BLOCKS_WHITE_GOLD);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_BLUE_GOLD, RankineTags.Items.STORAGE_BLOCKS_BLUE_GOLD);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_BLACK_GOLD, RankineTags.Items.STORAGE_BLOCKS_BLACK_GOLD);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NICKEL_SUPERALLOY, RankineTags.Items.STORAGE_BLOCKS_NICKEL_SUPERALLOY);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_COBALT_SUPERALLOY, RankineTags.Items.STORAGE_BLOCKS_COBALT_SUPERALLOY);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TUNGSTEN_HEAVY_ALLOY, RankineTags.Items.STORAGE_BLOCKS_TUNGSTEN_HEAVY_ALLOY);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TITANIUM_ALLOY, RankineTags.Items.STORAGE_BLOCKS_TITANIUM_ALLOY);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_NIOBIUM_ALLOY, RankineTags.Items.STORAGE_BLOCKS_NIOBIUM_ALLOY);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_ZIRCONIUM_ALLOY, RankineTags.Items.STORAGE_BLOCKS_ZIRCONIUM_ALLOY);

        copy(RankineTags.Blocks.STORAGE_BLOCKS_AMBER, RankineTags.Items.STORAGE_BLOCKS_AMBER);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_AQUAMARINE, RankineTags.Items.STORAGE_BLOCKS_AQUAMARINE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_CINNABAR, RankineTags.Items.STORAGE_BLOCKS_CINNABAR);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_FLUORITE, RankineTags.Items.STORAGE_BLOCKS_FLUORITE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_GARNET, RankineTags.Items.STORAGE_BLOCKS_GARNET);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_OPAL, RankineTags.Items.STORAGE_BLOCKS_OPAL);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_PERIDOT, RankineTags.Items.STORAGE_BLOCKS_PERIDOT);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_RUBY, RankineTags.Items.STORAGE_BLOCKS_RUBY);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_SAPPHIRE, RankineTags.Items.STORAGE_BLOCKS_SAPPHIRE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TOPAZ, RankineTags.Items.STORAGE_BLOCKS_TOPAZ);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_TOURMALINE, RankineTags.Items.STORAGE_BLOCKS_TOURMALINE);
        copy(RankineTags.Blocks.STORAGE_BLOCKS_PEARL, RankineTags.Items.STORAGE_BLOCKS_PEARL);

        tag(RankineTags.Items.NUGGETS_HYDROGEN).add(RankineItems.HYDROGEN_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_HELIUM).add(RankineItems.HELIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_LITHIUM).add(RankineItems.LITHIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_BERYLLIUM).add(RankineItems.BERYLLIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_BORON).add(RankineItems.BORON_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CARBON).add(RankineItems.CARBON_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NITROGEN).add(RankineItems.NITROGEN_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_OXYGEN).add(RankineItems.OXYGEN_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_FLUORINE).add(RankineItems.FLUORINE_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NEON).add(RankineItems.NEON_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_SODIUM).add(RankineItems.SODIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_MAGNESIUM).add(RankineItems.MAGNESIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ALUMINUM).add(RankineItems.ALUMINUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_SILICON).add(RankineItems.SILICON_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_PHOSPHORUS).add(RankineItems.PHOSPHORUS_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_SULFUR).add(RankineItems.SULFUR_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CHLORINE).add(RankineItems.CHLORINE_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ARGON).add(RankineItems.ARGON_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_POTASSIUM).add(RankineItems.POTASSIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CALCIUM).add(RankineItems.CALCIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_SCANDIUM).add(RankineItems.SCANDIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_TITANIUM).add(RankineItems.TITANIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_VANADIUM).add(RankineItems.VANADIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CHROMIUM).add(RankineItems.CHROMIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_MANGANESE).add(RankineItems.MANGANESE_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_COBALT).add(RankineItems.COBALT_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NICKEL).add(RankineItems.NICKEL_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_COPPER).add(RankineItems.COPPER_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ZINC).add(RankineItems.ZINC_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_GALLIUM).add(RankineItems.GALLIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_GERMANIUM).add(RankineItems.GERMANIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ARSENIC).add(RankineItems.ARSENIC_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_SELENIUM).add(RankineItems.SELENIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_BROMINE).add(RankineItems.BROMINE_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_KRYPTON).add(RankineItems.KRYPTON_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_RUBIDIUM).add(RankineItems.RUBIDIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_STRONTIUM).add(RankineItems.STRONTIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_YTTRIUM).add(RankineItems.YTTRIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ZIRCONIUM).add(RankineItems.ZIRCONIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NIOBIUM).add(RankineItems.NIOBIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_MOLYBDENUM).add(RankineItems.MOLYBDENUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_TECHNETIUM).add(RankineItems.TECHNETIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_RUTHENIUM).add(RankineItems.RUTHENIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_RHODIUM).add(RankineItems.RHODIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_PALLADIUM).add(RankineItems.PALLADIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_SILVER).add(RankineItems.SILVER_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CADMIUM).add(RankineItems.CADMIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_INDIUM).add(RankineItems.INDIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_TIN).add(RankineItems.TIN_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ANTIMONY).add(RankineItems.ANTIMONY_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_TELLURIUM).add(RankineItems.TELLURIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_IODINE).add(RankineItems.IODINE_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_XENON).add(RankineItems.XENON_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CESIUM).add(RankineItems.CESIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_BARIUM).add(RankineItems.BARIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_LANTHANUM).add(RankineItems.LANTHANUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CERIUM).add(RankineItems.CERIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_PRASEODYMIUM).add(RankineItems.PRASEODYMIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NEODYMIUM).add(RankineItems.NEODYMIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_PROMETHIUM).add(RankineItems.PROMETHIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_SAMARIUM).add(RankineItems.SAMARIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_EUROPIUM).add(RankineItems.EUROPIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_GADOLINIUM).add(RankineItems.GADOLINIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_TERBIUM).add(RankineItems.TERBIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_DYSPROSIUM).add(RankineItems.DYSPROSIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_HOLMIUM).add(RankineItems.HOLMIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ERBIUM).add(RankineItems.ERBIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_THULIUM).add(RankineItems.THULIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_YTTERBIUM).add(RankineItems.YTTERBIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_LUTETIUM).add(RankineItems.LUTETIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_HAFNIUM).add(RankineItems.HAFNIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_TANTALUM).add(RankineItems.TANTALUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_TUNGSTEN).add(RankineItems.TUNGSTEN_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_RHENIUM).add(RankineItems.RHENIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_OSMIUM).add(RankineItems.OSMIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_IRIDIUM).add(RankineItems.IRIDIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_PLATINUM).add(RankineItems.PLATINUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_MERCURY).add(RankineItems.MERCURY_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_THALLIUM).add(RankineItems.THALLIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_LEAD).add(RankineItems.LEAD_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_BISMUTH).add(RankineItems.BISMUTH_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_POLONIUM).add(RankineItems.POLONIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ASTATINE).add(RankineItems.ASTATINE_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_RADON).add(RankineItems.RADON_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_FRANCIUM).add(RankineItems.FRANCIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_RADIUM).add(RankineItems.RADIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ACTINIUM).add(RankineItems.ACTINIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_THORIUM).add(RankineItems.THORIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_PROTACTINIUM).add(RankineItems.PROTACTINIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_URANIUM).add(RankineItems.URANIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NEPTUNIUM).add(RankineItems.NEPTUNIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_PLUTONIUM).add(RankineItems.PLUTONIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_AMERICIUM).add(RankineItems.AMERICIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CURIUM).add(RankineItems.CURIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_BERKELIUM).add(RankineItems.BERKELIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CALIFORNIUM).add(RankineItems.CALIFORNIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_EINSTEINIUM).add(RankineItems.EINSTEINIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_FERMIUM).add(RankineItems.FERMIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_MENDELEVIUM).add(RankineItems.MENDELEVIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NOBELIUM).add(RankineItems.NOBELIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_LAWRENCIUM).add(RankineItems.LAWRENCIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_RUTHERFORDIUM).add(RankineItems.RUTHERFORDIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_DUBNIUM).add(RankineItems.DUBNIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_SEABORGIUM).add(RankineItems.SEABORGIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_BOHRIUM).add(RankineItems.BOHRIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_HASSIUM).add(RankineItems.HASSIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_MEITNERIUM).add(RankineItems.MEITNERIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_DARMSTADTIUM).add(RankineItems.DARMSTADTIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ROENTGENIUM).add(RankineItems.ROENTGENIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_COPERNICIUM).add(RankineItems.COPERNICIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NIHONIUM).add(RankineItems.NIHONIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_FLEROVIUM).add(RankineItems.FLEROVIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_MOSCOVIUM).add(RankineItems.MOSCOVIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_LIVERMORIUM).add(RankineItems.LIVERMORIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_TENNESSINE).add(RankineItems.TENNESSINE_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_OGANESSON).add(RankineItems.OGANESSON_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NETHERITE).add(RankineItems.NETHERITE_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ENDOSITUM).add(RankineItems.ENDOSITUM_NUGGET.get());

        tag(RankineTags.Items.NUGGETS_PEWTER).add(RankineItems.PEWTER_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_BRONZE).add(RankineItems.BRONZE_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_BRASS).add(RankineItems.BRASS_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CAST_IRON).add(RankineItems.CAST_IRON_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_INVAR).add(RankineItems.INVAR_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CUPRONICKEL).add(RankineItems.CUPRONICKEL_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_CONSTANTAN).add(RankineItems.CUPRONICKEL_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_DURALUMIN).add(RankineItems.DURALUMIN_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_MAGNESIUM_ALLOY).add(RankineItems.MAGNESIUM_ALLOY_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_STERLING_SILVER).add(RankineItems.STERLING_SILVER_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NICKEL_SILVER).add(RankineItems.NICKEL_SILVER_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ALNICO).add(RankineItems.ALNICO_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_STEEL).add(RankineItems.STEEL_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_STAINLESS_STEEL).add(RankineItems.STAINLESS_STEEL_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NITINOL).add(RankineItems.NITINOL_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ROSE_METAL).add(RankineItems.ROSE_METAL_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_MISCHMETAL).add(RankineItems.MISCHMETAL_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_FERROCERIUM).add(RankineItems.FERROCERIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_GALINSTAN).add(RankineItems.GALINSTAN_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_OSMIRIDIUM).add(RankineItems.OSMIRIDIUM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_SODIUM_POTASSIUM_ALLOY).add(RankineItems.SODIUM_POTASSIUM_ALLOY_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NEPTUNIUM_ALLOY).add(RankineItems.NEPTUNIUM_ALLOY_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_AMALGAM).add(RankineItems.AMALGAM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ENDER_AMALGAM).add(RankineItems.ENDER_AMALGAM_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ROSE_GOLD).add(RankineItems.ROSE_GOLD_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_GREEN_GOLD).add(RankineItems.GREEN_GOLD_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ELECTRUM).add(RankineItems.GREEN_GOLD_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_PURPLE_GOLD).add(RankineItems.PURPLE_GOLD_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_WHITE_GOLD).add(RankineItems.WHITE_GOLD_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_BLUE_GOLD).add(RankineItems.BLUE_GOLD_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_BLACK_GOLD).add(RankineItems.BLACK_GOLD_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NICKEL_SUPERALLOY).add(RankineItems.NICKEL_SUPERALLOY_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_COBALT_SUPERALLOY).add(RankineItems.COBALT_SUPERALLOY_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_TUNGSTEN_HEAVY_ALLOY).add(RankineItems.TUNGSTEN_HEAVY_ALLOY_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_TITANIUM_ALLOY).add(RankineItems.TITANIUM_ALLOY_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_NIOBIUM_ALLOY).add(RankineItems.NIOBIUM_ALLOY_NUGGET.get());
        tag(RankineTags.Items.NUGGETS_ZIRCONIUM_ALLOY).add(RankineItems.ZIRCONIUM_ALLOY_NUGGET.get());

        tag(RankineTags.Items.INGOTS_HYDROGEN).add(RankineItems.HYDROGEN_INGOT.get());
        tag(RankineTags.Items.INGOTS_HELIUM).add(RankineItems.HELIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_LITHIUM).add(RankineItems.LITHIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_BERYLLIUM).add(RankineItems.BERYLLIUM_INGOT.get());
        tag(RankineTags.Items.BORON).add(RankineItems.BORON.get());
        tag(RankineTags.Items.CARBON).add(RankineItems.CARBON.get());
        tag(RankineTags.Items.INGOTS_NITROGEN).add(RankineItems.NITROGEN_INGOT.get());
        tag(RankineTags.Items.INGOTS_OXYGEN).add(RankineItems.OXYGEN_INGOT.get());
        tag(RankineTags.Items.INGOTS_FLUORINE).add(RankineItems.FLUORINE_INGOT.get());
        tag(RankineTags.Items.INGOTS_NEON).add(RankineItems.NEON_INGOT.get());
        tag(RankineTags.Items.INGOTS_SODIUM).add(RankineItems.SODIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_MAGNESIUM).add(RankineItems.MAGNESIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_ALUMINUM).add(RankineItems.ALUMINUM_INGOT.get());
        tag(RankineTags.Items.SILICON).add(RankineItems.SILICON.get());
        tag(RankineTags.Items.PHOSPHORUS).add(RankineItems.PHOSPHORUS.get());
        tag(RankineTags.Items.SULFUR).add(RankineItems.SULFUR.get());
        tag(RankineTags.Items.INGOTS_CHLORINE).add(RankineItems.CHLORINE_INGOT.get());
        tag(RankineTags.Items.INGOTS_ARGON).add(RankineItems.ARGON_INGOT.get());
        tag(RankineTags.Items.INGOTS_POTASSIUM).add(RankineItems.POTASSIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_CALCIUM).add(RankineItems.CALCIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_SCANDIUM).add(RankineItems.SCANDIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_TITANIUM).add(RankineItems.TITANIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_VANADIUM).add(RankineItems.VANADIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_CHROMIUM).add(RankineItems.CHROMIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_MANGANESE).add(RankineItems.MANGANESE_INGOT.get());
        tag(RankineTags.Items.INGOTS_COBALT).add(RankineItems.COBALT_INGOT.get());
        tag(RankineTags.Items.INGOTS_NICKEL).add(RankineItems.NICKEL_INGOT.get());
        tag(RankineTags.Items.INGOTS_ZINC).add(RankineItems.ZINC_INGOT.get());
        tag(RankineTags.Items.INGOTS_GALLIUM).add(RankineItems.GALLIUM_INGOT.get());
        tag(RankineTags.Items.GERMANIUM).add(RankineItems.GERMANIUM.get());
        tag(RankineTags.Items.ARSENIC).add(RankineItems.ARSENIC.get());
        tag(RankineTags.Items.SELENIUM).add(RankineItems.SELENIUM.get());
        tag(RankineTags.Items.BROMINE).add(RankineItems.BROMINE.get());
        tag(RankineTags.Items.INGOTS_KRYPTON).add(RankineItems.KRYPTON_INGOT.get());
        tag(RankineTags.Items.INGOTS_RUBIDIUM).add(RankineItems.RUBIDIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_STRONTIUM).add(RankineItems.STRONTIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_YTTRIUM).add(RankineItems.YTTRIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_ZIRCONIUM).add(RankineItems.ZIRCONIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_NIOBIUM).add(RankineItems.NIOBIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_MOLYBDENUM).add(RankineItems.MOLYBDENUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_TECHNETIUM).add(RankineItems.TECHNETIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_RUTHENIUM).add(RankineItems.RUTHENIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_RHODIUM).add(RankineItems.RHODIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_PALLADIUM).add(RankineItems.PALLADIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_SILVER).add(RankineItems.SILVER_INGOT.get());
        tag(RankineTags.Items.INGOTS_CADMIUM).add(RankineItems.CADMIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_INDIUM).add(RankineItems.INDIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_TIN).add(RankineItems.TIN_INGOT.get());
        tag(RankineTags.Items.ANTIMONY).add(RankineItems.ANTIMONY.get());
        tag(RankineTags.Items.TELLURIUM).add(RankineItems.TELLURIUM.get());
        tag(RankineTags.Items.IODINE).add(RankineItems.IODINE.get());
        tag(RankineTags.Items.INGOTS_XENON).add(RankineItems.XENON_INGOT.get());
        tag(RankineTags.Items.INGOTS_CESIUM).add(RankineItems.CESIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_BARIUM).add(RankineItems.BARIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_LANTHANUM).add(RankineItems.LANTHANUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_CERIUM).add(RankineItems.CERIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_PRASEODYMIUM).add(RankineItems.PRASEODYMIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_NEODYMIUM).add(RankineItems.NEODYMIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_PROMETHIUM).add(RankineItems.PROMETHIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_SAMARIUM).add(RankineItems.SAMARIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_EUROPIUM).add(RankineItems.EUROPIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_GADOLINIUM).add(RankineItems.GADOLINIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_TERBIUM).add(RankineItems.TERBIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_DYSPROSIUM).add(RankineItems.DYSPROSIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_HOLMIUM).add(RankineItems.HOLMIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_ERBIUM).add(RankineItems.ERBIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_THULIUM).add(RankineItems.THULIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_YTTERBIUM).add(RankineItems.YTTERBIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_LUTETIUM).add(RankineItems.LUTETIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_HAFNIUM).add(RankineItems.HAFNIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_TANTALUM).add(RankineItems.TANTALUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_TUNGSTEN).add(RankineItems.TUNGSTEN_INGOT.get());
        tag(RankineTags.Items.INGOTS_RHENIUM).add(RankineItems.RHENIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_OSMIUM).add(RankineItems.OSMIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_IRIDIUM).add(RankineItems.IRIDIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_PLATINUM).add(RankineItems.PLATINUM_INGOT.get());
        tag(RankineTags.Items.MERCURY).add(RankineItems.MERCURY.get());
        tag(RankineTags.Items.INGOTS_THALLIUM).add(RankineItems.THALLIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_LEAD).add(RankineItems.LEAD_INGOT.get());
        tag(RankineTags.Items.INGOTS_BISMUTH).add(RankineItems.BISMUTH_INGOT.get());
        tag(RankineTags.Items.INGOTS_POLONIUM).add(RankineItems.POLONIUM_INGOT.get());
        tag(RankineTags.Items.ASTATINE).add(RankineItems.ASTATINE.get());
        tag(RankineTags.Items.INGOTS_RADON).add(RankineItems.RADON_INGOT.get());
        tag(RankineTags.Items.INGOTS_FRANCIUM).add(RankineItems.FRANCIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_RADIUM).add(RankineItems.RADIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_ACTINIUM).add(RankineItems.ACTINIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_THORIUM).add(RankineItems.THORIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_PROTACTINIUM).add(RankineItems.PROTACTINIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_URANIUM).add(RankineItems.URANIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_NEPTUNIUM).add(RankineItems.NEPTUNIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_PLUTONIUM).add(RankineItems.PLUTONIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_AMERICIUM).add(RankineItems.AMERICIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_CURIUM).add(RankineItems.CURIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_BERKELIUM).add(RankineItems.BERKELIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_CALIFORNIUM).add(RankineItems.CALIFORNIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_EINSTEINIUM).add(RankineItems.EINSTEINIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_FERMIUM).add(RankineItems.FERMIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_MENDELEVIUM).add(RankineItems.MENDELEVIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_NOBELIUM).add(RankineItems.NOBELIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_LAWRENCIUM).add(RankineItems.LAWRENCIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_RUTHERFORDIUM).add(RankineItems.RUTHERFORDIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_DUBNIUM).add(RankineItems.DUBNIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_SEABORGIUM).add(RankineItems.SEABORGIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_BOHRIUM).add(RankineItems.BOHRIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_HASSIUM).add(RankineItems.HASSIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_MEITNERIUM).add(RankineItems.MEITNERIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_DARMSTADTIUM).add(RankineItems.DARMSTADTIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_ROENTGENIUM).add(RankineItems.ROENTGENIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_COPERNICIUM).add(RankineItems.COPERNICIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_NIHONIUM).add(RankineItems.NIHONIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_FLEROVIUM).add(RankineItems.FLEROVIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_MOSCOVIUM).add(RankineItems.MOSCOVIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_LIVERMORIUM).add(RankineItems.LIVERMORIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_TENNESSINE).add(RankineItems.TENNESSINE_INGOT.get());
        tag(RankineTags.Items.INGOTS_OGANESSON).add(RankineItems.OGANESSON_INGOT.get());
        tag(RankineTags.Items.INGOTS_ENDOSITUM).add(RankineItems.ENDOSITUM_INGOT.get());

        tag(RankineTags.Items.INGOTS_PEWTER).add(RankineItems.PEWTER_INGOT.get());
        tag(RankineTags.Items.INGOTS_BRONZE).add(RankineItems.BRONZE_INGOT.get());
        tag(RankineTags.Items.INGOTS_BRASS).add(RankineItems.BRASS_INGOT.get());
        tag(RankineTags.Items.INGOTS_CAST_IRON).add(RankineItems.CAST_IRON_INGOT.get());
        tag(RankineTags.Items.INGOTS_INVAR).add(RankineItems.INVAR_INGOT.get());
        tag(RankineTags.Items.INGOTS_CUPRONICKEL).add(RankineItems.CUPRONICKEL_INGOT.get());
        tag(RankineTags.Items.INGOTS_CONSTANTAN).add(RankineItems.CUPRONICKEL_INGOT.get());
        tag(RankineTags.Items.INGOTS_DURALUMIN).add(RankineItems.DURALUMIN_INGOT.get());
        tag(RankineTags.Items.INGOTS_MAGNESIUM_ALLOY).add(RankineItems.MAGNESIUM_ALLOY_INGOT.get());
        tag(RankineTags.Items.INGOTS_STERLING_SILVER).add(RankineItems.STERLING_SILVER_INGOT.get());
        tag(RankineTags.Items.INGOTS_NICKEL_SILVER).add(RankineItems.NICKEL_SILVER_INGOT.get());
        tag(RankineTags.Items.INGOTS_ALNICO).add(RankineItems.ALNICO_INGOT.get());
        tag(RankineTags.Items.INGOTS_STEEL).add(RankineItems.STEEL_INGOT.get());
        tag(RankineTags.Items.INGOTS_STAINLESS_STEEL).add(RankineItems.STAINLESS_STEEL_INGOT.get());
        tag(RankineTags.Items.INGOTS_NITINOL).add(RankineItems.NITINOL_INGOT.get());
        tag(RankineTags.Items.INGOTS_ROSE_METAL).add(RankineItems.ROSE_METAL_INGOT.get());
        tag(RankineTags.Items.INGOTS_MISCHMETAL).add(RankineItems.MISCHMETAL_INGOT.get());
        tag(RankineTags.Items.INGOTS_FERROCERIUM).add(RankineItems.FERROCERIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_GALINSTAN).add(RankineItems.GALINSTAN_INGOT.get());
        tag(RankineTags.Items.INGOTS_OSMIRIDIUM).add(RankineItems.OSMIRIDIUM_INGOT.get());
        tag(RankineTags.Items.INGOTS_SODIUM_POTASSIUM_ALLOY).add(RankineItems.SODIUM_POTASSIUM_ALLOY_INGOT.get());
        tag(RankineTags.Items.INGOTS_NEPTUNIUM_ALLOY).add(RankineItems.NEPTUNIUM_ALLOY_INGOT.get());
        tag(RankineTags.Items.INGOTS_AMALGAM).add(RankineItems.AMALGAM_INGOT.get());
        tag(RankineTags.Items.INGOTS_ENDER_AMALGAM).add(RankineItems.ENDER_AMALGAM_INGOT.get());
        tag(RankineTags.Items.INGOTS_ROSE_GOLD).add(RankineItems.ROSE_GOLD_INGOT.get());
        tag(RankineTags.Items.INGOTS_GREEN_GOLD).add(RankineItems.GREEN_GOLD_INGOT.get());
        tag(RankineTags.Items.INGOTS_ELECTRUM).add(RankineItems.GREEN_GOLD_INGOT.get());
        tag(RankineTags.Items.INGOTS_PURPLE_GOLD).add(RankineItems.PURPLE_GOLD_INGOT.get());
        tag(RankineTags.Items.INGOTS_WHITE_GOLD).add(RankineItems.WHITE_GOLD_INGOT.get());
        tag(RankineTags.Items.INGOTS_BLUE_GOLD).add(RankineItems.BLUE_GOLD_INGOT.get());
        tag(RankineTags.Items.INGOTS_BLACK_GOLD).add(RankineItems.BLACK_GOLD_INGOT.get());
        tag(RankineTags.Items.INGOTS_NICKEL_SUPERALLOY).add(RankineItems.NICKEL_SUPERALLOY_INGOT.get());
        tag(RankineTags.Items.INGOTS_COBALT_SUPERALLOY).add(RankineItems.COBALT_SUPERALLOY_INGOT.get());
        tag(RankineTags.Items.INGOTS_TUNGSTEN_HEAVY_ALLOY).add(RankineItems.TUNGSTEN_HEAVY_ALLOY_INGOT.get());
        tag(RankineTags.Items.INGOTS_TITANIUM_ALLOY).add(RankineItems.TITANIUM_ALLOY_INGOT.get());
        tag(RankineTags.Items.INGOTS_NIOBIUM_ALLOY).add(RankineItems.NIOBIUM_ALLOY_INGOT.get());
        tag(RankineTags.Items.INGOTS_ZIRCONIUM_ALLOY).add(RankineItems.ZIRCONIUM_ALLOY_INGOT.get());
        tag(RankineTags.Items.SOLDER).add(RankineItems.SOLDER.get());


        //FORGE
        /*
        tag(Tags.Items.NUGGETS).addTags(RankineTags.Items.NUGGETS_HYDROGEN, RankineTags.Items.NUGGETS_HELIUM, RankineTags.Items.NUGGETS_LITHIUM, RankineTags.Items.NUGGETS_BERYLLIUM, RankineTags.Items.NUGGETS_BORON, RankineTags.Items.NUGGETS_CARBON, RankineTags.Items.NUGGETS_NITROGEN, RankineTags.Items.NUGGETS_OXYGEN, RankineTags.Items.NUGGETS_FLUORINE, RankineTags.Items.NUGGETS_NEON, RankineTags.Items.NUGGETS_SODIUM, RankineTags.Items.NUGGETS_MAGNESIUM, RankineTags.Items.NUGGETS_ALUMINUM, RankineTags.Items.NUGGETS_SILICON, RankineTags.Items.NUGGETS_PHOSPHORUS, RankineTags.Items.NUGGETS_SULFUR, RankineTags.Items.NUGGETS_CHLORINE, RankineTags.Items.NUGGETS_ARGON, RankineTags.Items.NUGGETS_POTASSIUM, RankineTags.Items.NUGGETS_CALCIUM, RankineTags.Items.NUGGETS_SCANDIUM, RankineTags.Items.NUGGETS_TITANIUM, RankineTags.Items.NUGGETS_VANADIUM, RankineTags.Items.NUGGETS_CHROMIUM, RankineTags.Items.NUGGETS_MANGANESE, RankineTags.Items.NUGGETS_COBALT, RankineTags.Items.NUGGETS_NICKEL, RankineTags.Items.NUGGETS_COPPER, RankineTags.Items.NUGGETS_ZINC, RankineTags.Items.NUGGETS_GALLIUM, RankineTags.Items.NUGGETS_GERMANIUM, RankineTags.Items.NUGGETS_ARSENIC, RankineTags.Items.NUGGETS_SELENIUM, RankineTags.Items.NUGGETS_BROMINE, RankineTags.Items.NUGGETS_KRYPTON, RankineTags.Items.NUGGETS_RUBIDIUM, RankineTags.Items.NUGGETS_STRONTIUM, RankineTags.Items.NUGGETS_YTTRIUM, RankineTags.Items.NUGGETS_ZIRCONIUM, RankineTags.Items.NUGGETS_NIOBIUM, RankineTags.Items.NUGGETS_MOLYBDENUM, RankineTags.Items.NUGGETS_TECHNETIUM, RankineTags.Items.NUGGETS_RUTHENIUM, RankineTags.Items.NUGGETS_RHODIUM, RankineTags.Items.NUGGETS_PALLADIUM, RankineTags.Items.NUGGETS_SILVER, RankineTags.Items.NUGGETS_CADMIUM, RankineTags.Items.NUGGETS_INDIUM, RankineTags.Items.NUGGETS_TIN, RankineTags.Items.NUGGETS_ANTIMONY, RankineTags.Items.NUGGETS_TELLURIUM, RankineTags.Items.NUGGETS_IODINE, RankineTags.Items.NUGGETS_XENON, RankineTags.Items.NUGGETS_CESIUM, RankineTags.Items.NUGGETS_BARIUM, RankineTags.Items.NUGGETS_LANTHANUM, RankineTags.Items.NUGGETS_CERIUM, RankineTags.Items.NUGGETS_PRASEODYMIUM, RankineTags.Items.NUGGETS_NEODYMIUM, RankineTags.Items.NUGGETS_PROMETHIUM, RankineTags.Items.NUGGETS_SAMARIUM, RankineTags.Items.NUGGETS_EUROPIUM, RankineTags.Items.NUGGETS_GADOLINIUM, RankineTags.Items.NUGGETS_TERBIUM, RankineTags.Items.NUGGETS_DYSPROSIUM, RankineTags.Items.NUGGETS_HOLMIUM, RankineTags.Items.NUGGETS_ERBIUM, RankineTags.Items.NUGGETS_THULIUM, RankineTags.Items.NUGGETS_YTTERBIUM, RankineTags.Items.NUGGETS_LUTETIUM, RankineTags.Items.NUGGETS_HAFNIUM, RankineTags.Items.NUGGETS_TANTALUM, RankineTags.Items.NUGGETS_TUNGSTEN, RankineTags.Items.NUGGETS_RHENIUM, RankineTags.Items.NUGGETS_OSMIUM, RankineTags.Items.NUGGETS_IRIDIUM, RankineTags.Items.NUGGETS_PLATINUM, RankineTags.Items.NUGGETS_MERCURY, RankineTags.Items.NUGGETS_THALLIUM, RankineTags.Items.NUGGETS_LEAD, RankineTags.Items.NUGGETS_BISMUTH, RankineTags.Items.NUGGETS_POLONIUM, RankineTags.Items.NUGGETS_ASTATINE, RankineTags.Items.NUGGETS_RADON, RankineTags.Items.NUGGETS_FRANCIUM, RankineTags.Items.NUGGETS_RADIUM, RankineTags.Items.NUGGETS_ACTINIUM, RankineTags.Items.NUGGETS_THORIUM, RankineTags.Items.NUGGETS_PROTACTINIUM, RankineTags.Items.NUGGETS_URANIUM, RankineTags.Items.NUGGETS_NEPTUNIUM, RankineTags.Items.NUGGETS_PLUTONIUM, RankineTags.Items.NUGGETS_AMERICIUM, RankineTags.Items.NUGGETS_CURIUM, RankineTags.Items.NUGGETS_BERKELIUM, RankineTags.Items.NUGGETS_CALIFORNIUM, RankineTags.Items.NUGGETS_EINSTEINIUM, RankineTags.Items.NUGGETS_FERMIUM, RankineTags.Items.NUGGETS_MENDELEVIUM, RankineTags.Items.NUGGETS_NOBELIUM, RankineTags.Items.NUGGETS_LAWRENCIUM, RankineTags.Items.NUGGETS_RUTHERFORDIUM, RankineTags.Items.NUGGETS_DUBNIUM, RankineTags.Items.NUGGETS_SEABORGIUM, RankineTags.Items.NUGGETS_BOHRIUM, RankineTags.Items.NUGGETS_HASSIUM, RankineTags.Items.NUGGETS_MEITNERIUM, RankineTags.Items.NUGGETS_DARMSTADTIUM, RankineTags.Items.NUGGETS_ROENTGENIUM, RankineTags.Items.NUGGETS_COPERNICIUM, RankineTags.Items.NUGGETS_NIHONIUM, RankineTags.Items.NUGGETS_FLEROVIUM, RankineTags.Items.NUGGETS_MOSCOVIUM, RankineTags.Items.NUGGETS_LIVERMORIUM, RankineTags.Items.NUGGETS_TENNESSINE, RankineTags.Items.NUGGETS_OGANESSON, RankineTags.Items.NUGGETS_NETHERITE, RankineTags.Items.NUGGETS_ENDOSITUM, RankineTags.Items.NUGGETS_PEWTER, RankineTags.Items.NUGGETS_BRONZE, RankineTags.Items.NUGGETS_BRASS, RankineTags.Items.NUGGETS_CAST_IRON, RankineTags.Items.NUGGETS_INVAR, RankineTags.Items.NUGGETS_CUPRONICKEL, RankineTags.Items.NUGGETS_DURALUMIN, RankineTags.Items.NUGGETS_MAGNESIUM_ALLOY, RankineTags.Items.NUGGETS_STERLING_SILVER, RankineTags.Items.NUGGETS_NICKEL_SILVER, RankineTags.Items.NUGGETS_ALNICO, RankineTags.Items.NUGGETS_STEEL, RankineTags.Items.NUGGETS_STAINLESS_STEEL, RankineTags.Items.NUGGETS_NITINOL, RankineTags.Items.NUGGETS_ROSE_METAL, RankineTags.Items.NUGGETS_MISCHMETAL, RankineTags.Items.NUGGETS_FERROCERIUM, RankineTags.Items.NUGGETS_GALINSTAN, RankineTags.Items.NUGGETS_OSMIRIDIUM, RankineTags.Items.NUGGETS_SODIUM_POTASSIUM_ALLOY, RankineTags.Items.NUGGETS_AMALGAM, RankineTags.Items.NUGGETS_ENDER_AMALGAM, RankineTags.Items.NUGGETS_ROSE_GOLD, RankineTags.Items.NUGGETS_GREEN_GOLD, RankineTags.Items.NUGGETS_CONSTANTAN, RankineTags.Items.NUGGETS_ELECTRUM, RankineTags.Items.NUGGETS_PURPLE_GOLD, RankineTags.Items.NUGGETS_WHITE_GOLD, RankineTags.Items.NUGGETS_BLUE_GOLD, RankineTags.Items.NUGGETS_BLACK_GOLD, RankineTags.Items.NUGGETS_NICKEL_SUPERALLOY, RankineTags.Items.NUGGETS_COBALT_SUPERALLOY, RankineTags.Items.NUGGETS_TUNGSTEN_HEAVY_ALLOY, RankineTags.Items.NUGGETS_TITANIUM_ALLOY);
        tag(Tags.Items.INGOTS).addTags(RankineTags.Items.INGOTS_HYDROGEN, RankineTags.Items.INGOTS_HELIUM, RankineTags.Items.INGOTS_LITHIUM, RankineTags.Items.INGOTS_BERYLLIUM, RankineTags.Items.BORON, RankineTags.Items.CARBON, RankineTags.Items.INGOTS_NITROGEN, RankineTags.Items.INGOTS_OXYGEN, RankineTags.Items.INGOTS_FLUORINE, RankineTags.Items.INGOTS_NEON, RankineTags.Items.INGOTS_SODIUM, RankineTags.Items.INGOTS_MAGNESIUM, RankineTags.Items.INGOTS_ALUMINUM, RankineTags.Items.SILICON, RankineTags.Items.PHOSPHORUS, RankineTags.Items.SULFUR, RankineTags.Items.INGOTS_CHLORINE, RankineTags.Items.INGOTS_ARGON, RankineTags.Items.INGOTS_POTASSIUM, RankineTags.Items.INGOTS_CALCIUM, RankineTags.Items.INGOTS_SCANDIUM, RankineTags.Items.INGOTS_TITANIUM, RankineTags.Items.INGOTS_VANADIUM, RankineTags.Items.INGOTS_CHROMIUM, RankineTags.Items.INGOTS_MANGANESE, RankineTags.Items.INGOTS_COBALT, RankineTags.Items.INGOTS_NICKEL, RankineTags.Items.INGOTS_COPPER, RankineTags.Items.INGOTS_ZINC, RankineTags.Items.INGOTS_GALLIUM, RankineTags.Items.GERMANIUM, RankineTags.Items.ARSENIC, RankineTags.Items.SELENIUM, RankineTags.Items.BROMINE, RankineTags.Items.INGOTS_KRYPTON, RankineTags.Items.INGOTS_RUBIDIUM, RankineTags.Items.INGOTS_STRONTIUM, RankineTags.Items.INGOTS_YTTRIUM, RankineTags.Items.INGOTS_ZIRCONIUM, RankineTags.Items.INGOTS_NIOBIUM, RankineTags.Items.INGOTS_MOLYBDENUM, RankineTags.Items.INGOTS_TECHNETIUM, RankineTags.Items.INGOTS_RUTHENIUM, RankineTags.Items.INGOTS_RHODIUM, RankineTags.Items.INGOTS_PALLADIUM, RankineTags.Items.INGOTS_SILVER, RankineTags.Items.INGOTS_CADMIUM, RankineTags.Items.INGOTS_INDIUM, RankineTags.Items.INGOTS_TIN, RankineTags.Items.ANTIMONY, RankineTags.Items.TELLURIUM, RankineTags.Items.IODINE, RankineTags.Items.INGOTS_XENON, RankineTags.Items.INGOTS_CESIUM, RankineTags.Items.INGOTS_BARIUM, RankineTags.Items.INGOTS_LANTHANUM, RankineTags.Items.INGOTS_CERIUM, RankineTags.Items.INGOTS_PRASEODYMIUM, RankineTags.Items.INGOTS_NEODYMIUM, RankineTags.Items.INGOTS_PROMETHIUM, RankineTags.Items.INGOTS_SAMARIUM, RankineTags.Items.INGOTS_EUROPIUM, RankineTags.Items.INGOTS_GADOLINIUM, RankineTags.Items.INGOTS_TERBIUM, RankineTags.Items.INGOTS_DYSPROSIUM, RankineTags.Items.INGOTS_HOLMIUM, RankineTags.Items.INGOTS_ERBIUM, RankineTags.Items.INGOTS_THULIUM, RankineTags.Items.INGOTS_YTTERBIUM, RankineTags.Items.INGOTS_LUTETIUM, RankineTags.Items.INGOTS_HAFNIUM, RankineTags.Items.INGOTS_TANTALUM, RankineTags.Items.INGOTS_TUNGSTEN, RankineTags.Items.INGOTS_RHENIUM, RankineTags.Items.INGOTS_OSMIUM, RankineTags.Items.INGOTS_IRIDIUM, RankineTags.Items.INGOTS_PLATINUM, RankineTags.Items.MERCURY, RankineTags.Items.INGOTS_THALLIUM, RankineTags.Items.INGOTS_LEAD, RankineTags.Items.INGOTS_BISMUTH, RankineTags.Items.INGOTS_POLONIUM, RankineTags.Items.ASTATINE, RankineTags.Items.INGOTS_RADON, RankineTags.Items.INGOTS_FRANCIUM, RankineTags.Items.INGOTS_RADIUM, RankineTags.Items.INGOTS_ACTINIUM, RankineTags.Items.INGOTS_THORIUM, RankineTags.Items.INGOTS_PROTACTINIUM, RankineTags.Items.INGOTS_URANIUM, RankineTags.Items.INGOTS_NEPTUNIUM, RankineTags.Items.INGOTS_PLUTONIUM, RankineTags.Items.INGOTS_AMERICIUM, RankineTags.Items.INGOTS_CURIUM, RankineTags.Items.INGOTS_BERKELIUM, RankineTags.Items.INGOTS_CALIFORNIUM, RankineTags.Items.INGOTS_EINSTEINIUM, RankineTags.Items.INGOTS_FERMIUM, RankineTags.Items.INGOTS_MENDELEVIUM, RankineTags.Items.INGOTS_NOBELIUM, RankineTags.Items.INGOTS_LAWRENCIUM, RankineTags.Items.INGOTS_RUTHERFORDIUM, RankineTags.Items.INGOTS_DUBNIUM, RankineTags.Items.INGOTS_SEABORGIUM, RankineTags.Items.INGOTS_BOHRIUM, RankineTags.Items.INGOTS_HASSIUM, RankineTags.Items.INGOTS_MEITNERIUM, RankineTags.Items.INGOTS_DARMSTADTIUM, RankineTags.Items.INGOTS_ROENTGENIUM, RankineTags.Items.INGOTS_COPERNICIUM, RankineTags.Items.INGOTS_NIHONIUM, RankineTags.Items.INGOTS_FLEROVIUM, RankineTags.Items.INGOTS_MOSCOVIUM, RankineTags.Items.INGOTS_LIVERMORIUM, RankineTags.Items.INGOTS_TENNESSINE, RankineTags.Items.INGOTS_OGANESSON, RankineTags.Items.INGOTS_ENDOSITUM, RankineTags.Items.INGOTS_PEWTER, RankineTags.Items.INGOTS_BRONZE, RankineTags.Items.INGOTS_BRASS, RankineTags.Items.INGOTS_CAST_IRON, RankineTags.Items.INGOTS_INVAR, RankineTags.Items.INGOTS_CUPRONICKEL, RankineTags.Items.INGOTS_CONSTANTAN, RankineTags.Items.INGOTS_ELECTRUM, RankineTags.Items.INGOTS_DURALUMIN, RankineTags.Items.INGOTS_MAGNESIUM_ALLOY, RankineTags.Items.INGOTS_STERLING_SILVER, RankineTags.Items.INGOTS_NICKEL_SILVER, RankineTags.Items.INGOTS_ALNICO, RankineTags.Items.INGOTS_STEEL, RankineTags.Items.INGOTS_STAINLESS_STEEL, RankineTags.Items.INGOTS_NITINOL, RankineTags.Items.INGOTS_ROSE_METAL, RankineTags.Items.INGOTS_MISCHMETAL, RankineTags.Items.INGOTS_FERROCERIUM, RankineTags.Items.INGOTS_GALINSTAN, RankineTags.Items.INGOTS_OSMIRIDIUM, RankineTags.Items.INGOTS_SODIUM_POTASSIUM_ALLOY, RankineTags.Items.INGOTS_AMALGAM, RankineTags.Items.INGOTS_ENDER_AMALGAM, RankineTags.Items.INGOTS_ROSE_GOLD, RankineTags.Items.INGOTS_GREEN_GOLD, RankineTags.Items.INGOTS_PURPLE_GOLD, RankineTags.Items.INGOTS_WHITE_GOLD, RankineTags.Items.INGOTS_BLUE_GOLD, RankineTags.Items.INGOTS_BLACK_GOLD, RankineTags.Items.INGOTS_NICKEL_SUPERALLOY, RankineTags.Items.INGOTS_COBALT_SUPERALLOY, RankineTags.Items.INGOTS_TUNGSTEN_HEAVY_ALLOY, RankineTags.Items.INGOTS_TITANIUM_ALLOY, RankineTags.Items.INGOTS_NIOBIUM_ALLOY, RankineTags.Items.INGOTS_ZIRCONIUM_ALLOY);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

        tag(RankineTags.Items.ALLOY_NUGGETS).addTags(RankineTags.Items.NUGGETS_PEWTER, RankineTags.Items.NUGGETS_BRONZE, RankineTags.Items.NUGGETS_BRASS, RankineTags.Items.NUGGETS_CAST_IRON, RankineTags.Items.NUGGETS_INVAR, RankineTags.Items.NUGGETS_CUPRONICKEL, RankineTags.Items.NUGGETS_DURALUMIN, RankineTags.Items.NUGGETS_MAGNESIUM_ALLOY, RankineTags.Items.NUGGETS_STERLING_SILVER, RankineTags.Items.NUGGETS_NICKEL_SILVER, RankineTags.Items.NUGGETS_ALNICO, RankineTags.Items.NUGGETS_STEEL, RankineTags.Items.NUGGETS_STAINLESS_STEEL, RankineTags.Items.NUGGETS_NITINOL, RankineTags.Items.NUGGETS_ROSE_METAL, RankineTags.Items.NUGGETS_MISCHMETAL, RankineTags.Items.NUGGETS_FERROCERIUM, RankineTags.Items.NUGGETS_GALINSTAN, RankineTags.Items.NUGGETS_OSMIRIDIUM, RankineTags.Items.NUGGETS_SODIUM_POTASSIUM_ALLOY, RankineTags.Items.NUGGETS_AMALGAM, RankineTags.Items.NUGGETS_ENDER_AMALGAM, RankineTags.Items.NUGGETS_ROSE_GOLD, RankineTags.Items.NUGGETS_GREEN_GOLD, RankineTags.Items.NUGGETS_PURPLE_GOLD, RankineTags.Items.NUGGETS_WHITE_GOLD, RankineTags.Items.NUGGETS_BLUE_GOLD, RankineTags.Items.NUGGETS_BLACK_GOLD, RankineTags.Items.NUGGETS_NICKEL_SUPERALLOY, RankineTags.Items.NUGGETS_COBALT_SUPERALLOY, RankineTags.Items.NUGGETS_TUNGSTEN_HEAVY_ALLOY, RankineTags.Items.NUGGETS_TITANIUM_ALLOY, RankineTags.Items.NUGGETS_CONSTANTAN, RankineTags.Items.NUGGETS_ELECTRUM);
        tag(RankineTags.Items.ALLOY_INGOTS).addTags(RankineTags.Items.INGOTS_PEWTER, RankineTags.Items.INGOTS_BRONZE, RankineTags.Items.INGOTS_BRASS, RankineTags.Items.INGOTS_CAST_IRON, RankineTags.Items.INGOTS_INVAR, RankineTags.Items.INGOTS_CUPRONICKEL, RankineTags.Items.STORAGE_BLOCKS_CONSTANTAN, RankineTags.Items.INGOTS_ELECTRUM, RankineTags.Items.INGOTS_DURALUMIN, RankineTags.Items.INGOTS_MAGNESIUM_ALLOY, RankineTags.Items.INGOTS_STERLING_SILVER, RankineTags.Items.INGOTS_NICKEL_SILVER, RankineTags.Items.INGOTS_ALNICO, RankineTags.Items.INGOTS_STEEL, RankineTags.Items.INGOTS_STAINLESS_STEEL, RankineTags.Items.INGOTS_NITINOL, RankineTags.Items.INGOTS_ROSE_METAL, RankineTags.Items.INGOTS_MISCHMETAL, RankineTags.Items.INGOTS_FERROCERIUM, RankineTags.Items.INGOTS_GALINSTAN, RankineTags.Items.INGOTS_OSMIRIDIUM, RankineTags.Items.INGOTS_SODIUM_POTASSIUM_ALLOY, RankineTags.Items.INGOTS_AMALGAM, RankineTags.Items.INGOTS_ENDER_AMALGAM, RankineTags.Items.INGOTS_ROSE_GOLD, RankineTags.Items.INGOTS_GREEN_GOLD, RankineTags.Items.INGOTS_PURPLE_GOLD, RankineTags.Items.INGOTS_WHITE_GOLD, RankineTags.Items.INGOTS_BLUE_GOLD, RankineTags.Items.INGOTS_BLACK_GOLD, RankineTags.Items.INGOTS_NICKEL_SUPERALLOY, RankineTags.Items.INGOTS_COBALT_SUPERALLOY, RankineTags.Items.INGOTS_TUNGSTEN_HEAVY_ALLOY, RankineTags.Items.INGOTS_TITANIUM_ALLOY);
        copy(RankineTags.Blocks.ALLOY_STORAGE_BLOCKS, RankineTags.Items.ALLOY_STORAGE_BLOCKS);

        tag(RankineTags.Items.WOODEN_TOOLS).add(Items.WOODEN_AXE).add(Items.WOODEN_PICKAXE).add(Items.WOODEN_HOE).add(Items.WOODEN_SHOVEL).add(Items.WOODEN_SWORD);
        tag(RankineTags.Items.STONE_TOOLS).add(Items.STONE_AXE).add(Items.STONE_PICKAXE).add(Items.STONE_HOE).add(Items.STONE_SHOVEL).add(Items.STONE_SWORD);
        tag(RankineTags.Items.IRON_TOOLS).add(Items.IRON_AXE).add(Items.IRON_PICKAXE).add(Items.IRON_HOE).add(Items.IRON_SHOVEL).add(Items.IRON_SWORD);
        tag(RankineTags.Items.GOLDEN_TOOLS).add(Items.GOLDEN_AXE).add(Items.GOLDEN_PICKAXE).add(Items.GOLDEN_HOE).add(Items.GOLDEN_SHOVEL).add(Items.GOLDEN_SWORD);
        tag(RankineTags.Items.DIAMOND_TOOLS).add(Items.DIAMOND_AXE).add(Items.DIAMOND_PICKAXE).add(Items.DIAMOND_HOE).add(Items.DIAMOND_SHOVEL).add(Items.DIAMOND_SWORD);
        tag(RankineTags.Items.NETHERITE_TOOLS).add(Items.NETHERITE_AXE).add(Items.NETHERITE_PICKAXE).add(Items.NETHERITE_HOE).add(Items.NETHERITE_SHOVEL).add(Items.NETHERITE_SWORD);
        tag(RankineTags.Items.ALLOY_TOOLS).add(RankineItems.ALLOY_AXE.get(), RankineItems.ALLOY_PICKAXE.get(), RankineItems.ALLOY_HOE.get(), RankineItems.ALLOY_SHOVEL.get(), RankineItems.ALLOY_SWORD.get(), RankineItems.ALLOY_HAMMER.get(), RankineItems.ALLOY_SPEAR.get());
        tag(RankineTags.Items.FLINT_TOOLS).add(RankineItems.FLINT_AXE.get(), RankineItems.FLINT_PICKAXE.get(), RankineItems.FLINT_HOE.get(), RankineItems.FLINT_SHOVEL.get(), RankineItems.FLINT_KNIFE.get(), RankineItems.FLINT_SPEAR.get());
        tag(RankineTags.Items.PEWTER_TOOLS).add(RankineItems.PEWTER_AXE.get(), RankineItems.PEWTER_PICKAXE.get(), RankineItems.PEWTER_HOE.get(), RankineItems.PEWTER_SHOVEL.get(), RankineItems.PEWTER_SWORD.get(), RankineItems.PEWTER_HAMMER.get(), RankineItems.PEWTER_SPEAR.get(), RankineItems.PEWTER_KNIFE.get());
        tag(RankineTags.Items.BRONZE_TOOLS).add(RankineItems.BRONZE_AXE.get(), RankineItems.BRONZE_PICKAXE.get(), RankineItems.BRONZE_HOE.get(), RankineItems.BRONZE_SHOVEL.get(), RankineItems.BRONZE_SWORD.get(), RankineItems.BRONZE_HAMMER.get(), RankineItems.BRONZE_SPEAR.get());
        tag(RankineTags.Items.INVAR_TOOLS).add(RankineItems.INVAR_AXE.get(), RankineItems.INVAR_PICKAXE.get(), RankineItems.INVAR_HOE.get(), RankineItems.INVAR_SHOVEL.get(), RankineItems.INVAR_SWORD.get(), RankineItems.INVAR_HAMMER.get(), RankineItems.INVAR_SPEAR.get());
        tag(RankineTags.Items.TITANIUM_ALLOY_TOOLS).add(RankineItems.TITANIUM_ALLOY_AXE.get(), RankineItems.TITANIUM_ALLOY_PICKAXE.get(), RankineItems.TITANIUM_ALLOY_HOE.get(), RankineItems.TITANIUM_ALLOY_SHOVEL.get(), RankineItems.TITANIUM_ALLOY_SWORD.get(), RankineItems.TITANIUM_ALLOY_HAMMER.get(), RankineItems.TITANIUM_ALLOY_SPEAR.get());
        tag(RankineTags.Items.STEEL_TOOLS).add(RankineItems.STEEL_AXE.get(), RankineItems.STEEL_PICKAXE.get(), RankineItems.STEEL_HOE.get(), RankineItems.STEEL_SHOVEL.get(), RankineItems.STEEL_SWORD.get(), RankineItems.STEEL_HAMMER.get(), RankineItems.STEEL_SPEAR.get());
        tag(RankineTags.Items.STAINLESS_STEEL_TOOLS).add(RankineItems.STAINLESS_STEEL_AXE.get(), RankineItems.STAINLESS_STEEL_PICKAXE.get(), RankineItems.STAINLESS_STEEL_HOE.get(), RankineItems.STAINLESS_STEEL_SHOVEL.get(), RankineItems.STAINLESS_STEEL_SWORD.get(), RankineItems.STAINLESS_STEEL_HAMMER.get(), RankineItems.STAINLESS_STEEL_SPEAR.get());
        tag(RankineTags.Items.NICKEL_SUPERALLOY_TOOLS).add(RankineItems.NICKEL_SUPERALLOY_AXE.get(), RankineItems.NICKEL_SUPERALLOY_PICKAXE.get(), RankineItems.NICKEL_SUPERALLOY_HOE.get(), RankineItems.NICKEL_SUPERALLOY_SHOVEL.get(), RankineItems.NICKEL_SUPERALLOY_SWORD.get(), RankineItems.NICKEL_SUPERALLOY_HAMMER.get(), RankineItems.NICKEL_SUPERALLOY_SPEAR.get());
        tag(RankineTags.Items.COBALT_SUPERALLOY_TOOLS).add(RankineItems.COBALT_SUPERALLOY_AXE.get(), RankineItems.COBALT_SUPERALLOY_PICKAXE.get(), RankineItems.COBALT_SUPERALLOY_HOE.get(), RankineItems.COBALT_SUPERALLOY_SHOVEL.get(), RankineItems.COBALT_SUPERALLOY_SWORD.get(), RankineItems.COBALT_SUPERALLOY_HAMMER.get(), RankineItems.COBALT_SUPERALLOY_SPEAR.get());
        tag(RankineTags.Items.TUNGSTEN_HEAVY_ALLOY_TOOLS).add(RankineItems.TUNGSTEN_HEAVY_ALLOY_AXE.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_PICKAXE.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_HOE.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_SHOVEL.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_SWORD.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_HAMMER.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_SPEAR.get());
        tag(RankineTags.Items.AMALGAM_TOOLS).add(RankineItems.AMALGAM_AXE.get(), RankineItems.AMALGAM_PICKAXE.get(), RankineItems.AMALGAM_HOE.get(), RankineItems.AMALGAM_SHOVEL.get(), RankineItems.AMALGAM_SWORD.get(), RankineItems.AMALGAM_HAMMER.get(), RankineItems.AMALGAM_SPEAR.get());
        tag(RankineTags.Items.ENDER_AMALGAM_TOOLS).add(RankineItems.ENDER_AMALGAM_AXE.get(), RankineItems.ENDER_AMALGAM_PICKAXE.get(), RankineItems.ENDER_AMALGAM_HOE.get(), RankineItems.ENDER_AMALGAM_SHOVEL.get(), RankineItems.ENDER_AMALGAM_SWORD.get(), RankineItems.ENDER_AMALGAM_HAMMER.get(), RankineItems.ENDER_AMALGAM_SPEAR.get());
        tag(RankineTags.Items.ROSE_GOLD_TOOLS).add(RankineItems.ROSE_GOLD_AXE.get(), RankineItems.ROSE_GOLD_PICKAXE.get(), RankineItems.ROSE_GOLD_HOE.get(), RankineItems.ROSE_GOLD_SHOVEL.get(), RankineItems.ROSE_GOLD_SWORD.get(), RankineItems.ROSE_GOLD_HAMMER.get(), RankineItems.ROSE_GOLD_SPEAR.get());
        tag(RankineTags.Items.GREEN_GOLD_TOOLS).add(RankineItems.GREEN_GOLD_AXE.get(), RankineItems.GREEN_GOLD_PICKAXE.get(), RankineItems.GREEN_GOLD_HOE.get(), RankineItems.GREEN_GOLD_SHOVEL.get(), RankineItems.GREEN_GOLD_SWORD.get(), RankineItems.GREEN_GOLD_HAMMER.get(), RankineItems.GREEN_GOLD_SPEAR.get());
        tag(RankineTags.Items.BLUE_GOLD_TOOLS).add(RankineItems.BLUE_GOLD_AXE.get(), RankineItems.BLUE_GOLD_PICKAXE.get(), RankineItems.BLUE_GOLD_HOE.get(), RankineItems.BLUE_GOLD_SHOVEL.get(), RankineItems.BLUE_GOLD_SWORD.get(), RankineItems.BLUE_GOLD_HAMMER.get(), RankineItems.BLUE_GOLD_SPEAR.get());
        tag(RankineTags.Items.WHITE_GOLD_TOOLS).add(RankineItems.WHITE_GOLD_AXE.get(), RankineItems.WHITE_GOLD_PICKAXE.get(), RankineItems.WHITE_GOLD_HOE.get(), RankineItems.WHITE_GOLD_SHOVEL.get(), RankineItems.WHITE_GOLD_SWORD.get(), RankineItems.WHITE_GOLD_HAMMER.get(), RankineItems.WHITE_GOLD_SPEAR.get());
        tag(RankineTags.Items.PURPLE_GOLD_TOOLS).add(RankineItems.PURPLE_GOLD_AXE.get(), RankineItems.PURPLE_GOLD_PICKAXE.get(), RankineItems.PURPLE_GOLD_HOE.get(), RankineItems.PURPLE_GOLD_SHOVEL.get(), RankineItems.PURPLE_GOLD_SWORD.get(), RankineItems.PURPLE_GOLD_HAMMER.get(), RankineItems.PURPLE_GOLD_SPEAR.get());
        tag(RankineTags.Items.BLACK_GOLD_TOOLS).add(RankineItems.BLACK_GOLD_AXE.get(), RankineItems.BLACK_GOLD_PICKAXE.get(), RankineItems.BLACK_GOLD_HOE.get(), RankineItems.BLACK_GOLD_SHOVEL.get(), RankineItems.BLACK_GOLD_SWORD.get(), RankineItems.BLACK_GOLD_HAMMER.get(), RankineItems.BLACK_GOLD_SPEAR.get());
        tag(RankineTags.Items.OSMIRIDIUM_TOOLS).add(RankineItems.OSMIRIDIUM_AXE.get(), RankineItems.OSMIRIDIUM_PICKAXE.get(), RankineItems.OSMIRIDIUM_HOE.get(), RankineItems.OSMIRIDIUM_SHOVEL.get(), RankineItems.OSMIRIDIUM_SWORD.get(), RankineItems.OSMIRIDIUM_HAMMER.get(), RankineItems.OSMIRIDIUM_SPEAR.get());

        tag(RankineTags.Items.HELMETS).add(Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.IRON_HELMET, Items.GOLD_NUGGET, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET, RankineItems.BRIGADINE_HELMET.get(), RankineItems.DIVING_HELMET.get(), RankineItems.CONDUIT_DIVING_HELMET.get(), RankineItems.ALLOY_HELMET.get());
        tag(RankineTags.Items.CHESTPLATES).add(Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE, Items.GOLD_NUGGET, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE, RankineItems.BRIGADINE_CHESTPLATE.get(), RankineItems.DIVING_CHESTPLATE.get(), RankineItems.CONDUIT_DIVING_CHESTPLATE.get(), RankineItems.ALLOY_CHESTPLATE.get());
        tag(RankineTags.Items.LEGGINGS).add(Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.IRON_LEGGINGS, Items.GOLD_NUGGET, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS, RankineItems.BRIGADINE_LEGGINGS.get(), RankineItems.DIVING_LEGGINGS.get(), RankineItems.CONDUIT_DIVING_LEGGINGS.get(), RankineItems.ALLOY_LEGGINGS.get());
        tag(RankineTags.Items.BOOTS).add(Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS, Items.GOLD_NUGGET, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS, RankineItems.BRIGADINE_BOOTS.get(), RankineItems.DIVING_BOOTS.get(), RankineItems.CONDUIT_DIVING_BOOTS.get(), RankineItems.ALLOY_BOOTS.get());
        tag(RankineTags.Items.AXES).add(RankineItems.FLINT_AXE.get(), RankineItems.PEWTER_AXE.get(), RankineItems.ALLOY_AXE.get(), RankineItems.BRONZE_AXE.get(), RankineItems.INVAR_AXE.get(), RankineItems.STEEL_AXE.get(), RankineItems.STAINLESS_STEEL_AXE.get(), RankineItems.NICKEL_SUPERALLOY_AXE.get(), RankineItems.COBALT_SUPERALLOY_AXE.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_AXE.get(), RankineItems.ROSE_GOLD_AXE.get(), RankineItems.BLUE_GOLD_AXE.get(), RankineItems.GREEN_GOLD_AXE.get(), RankineItems.WHITE_GOLD_AXE.get(), RankineItems.PURPLE_GOLD_AXE.get(), RankineItems.BLACK_GOLD_AXE.get(), RankineItems.AMALGAM_AXE.get(), RankineItems.ENDER_AMALGAM_AXE.get(), RankineItems.TITANIUM_ALLOY_AXE.get(), RankineItems.NIOBIUM_ALLOY_AXE.get(), RankineItems.ZIRCONIUM_ALLOY_AXE.get(), RankineItems.OSMIRIDIUM_AXE.get());
        tag(RankineTags.Items.PICKAXES).add(RankineItems.FLINT_PICKAXE.get(), RankineItems.PEWTER_PICKAXE.get(), RankineItems.ALLOY_PICKAXE.get(), RankineItems.BRONZE_PICKAXE.get(), RankineItems.INVAR_PICKAXE.get(), RankineItems.STEEL_PICKAXE.get(), RankineItems.STAINLESS_STEEL_PICKAXE.get(), RankineItems.NICKEL_SUPERALLOY_PICKAXE.get(), RankineItems.COBALT_SUPERALLOY_PICKAXE.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_PICKAXE.get(), RankineItems.ROSE_GOLD_PICKAXE.get(), RankineItems.BLUE_GOLD_PICKAXE.get(), RankineItems.GREEN_GOLD_PICKAXE.get(), RankineItems.WHITE_GOLD_PICKAXE.get(), RankineItems.PURPLE_GOLD_PICKAXE.get(), RankineItems.BLACK_GOLD_PICKAXE.get(), RankineItems.AMALGAM_PICKAXE.get(), RankineItems.ENDER_AMALGAM_PICKAXE.get(), RankineItems.TITANIUM_ALLOY_PICKAXE.get(), RankineItems.NIOBIUM_ALLOY_PICKAXE.get(), RankineItems.ZIRCONIUM_ALLOY_PICKAXE.get(), RankineItems.OSMIRIDIUM_PICKAXE.get());
        tag(RankineTags.Items.SHOVELS).add(RankineItems.FLINT_SHOVEL.get(), RankineItems.PEWTER_SHOVEL.get(), RankineItems.ALLOY_SHOVEL.get(), RankineItems.BRONZE_SHOVEL.get(), RankineItems.INVAR_SHOVEL.get(), RankineItems.STEEL_SHOVEL.get(), RankineItems.STAINLESS_STEEL_SHOVEL.get(), RankineItems.NICKEL_SUPERALLOY_SHOVEL.get(), RankineItems.COBALT_SUPERALLOY_SHOVEL.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_SHOVEL.get(), RankineItems.ROSE_GOLD_SHOVEL.get(), RankineItems.BLUE_GOLD_SHOVEL.get(), RankineItems.GREEN_GOLD_SHOVEL.get(), RankineItems.WHITE_GOLD_SHOVEL.get(), RankineItems.PURPLE_GOLD_SHOVEL.get(), RankineItems.BLACK_GOLD_SHOVEL.get(), RankineItems.AMALGAM_SHOVEL.get(), RankineItems.ENDER_AMALGAM_SHOVEL.get(), RankineItems.TITANIUM_ALLOY_SHOVEL.get(), RankineItems.NIOBIUM_ALLOY_SHOVEL.get(), RankineItems.ZIRCONIUM_ALLOY_SHOVEL.get(), RankineItems.OSMIRIDIUM_SHOVEL.get());
        tag(RankineTags.Items.HOES).add(RankineItems.FLINT_HOE.get(), RankineItems.PEWTER_HOE.get(), RankineItems.ALLOY_HOE.get(), RankineItems.BRONZE_HOE.get(), RankineItems.INVAR_HOE.get(), RankineItems.STEEL_HOE.get(), RankineItems.STAINLESS_STEEL_HOE.get(), RankineItems.NICKEL_SUPERALLOY_HOE.get(), RankineItems.COBALT_SUPERALLOY_HOE.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_HOE.get(), RankineItems.ROSE_GOLD_HOE.get(), RankineItems.BLUE_GOLD_HOE.get(), RankineItems.GREEN_GOLD_HOE.get(), RankineItems.WHITE_GOLD_HOE.get(), RankineItems.PURPLE_GOLD_HOE.get(), RankineItems.BLACK_GOLD_HOE.get(), RankineItems.AMALGAM_HOE.get(), RankineItems.ENDER_AMALGAM_HOE.get(), RankineItems.TITANIUM_ALLOY_HOE.get(), RankineItems.NIOBIUM_ALLOY_HOE.get(), RankineItems.ZIRCONIUM_ALLOY_HOE.get(), RankineItems.OSMIRIDIUM_HOE.get());
        tag(RankineTags.Items.SPEARS).add(RankineItems.FLINT_SPEAR.get(), RankineItems.PEWTER_SPEAR.get(), RankineItems.ALLOY_SPEAR.get(), RankineItems.BRONZE_SPEAR.get(), RankineItems.INVAR_SPEAR.get(), RankineItems.STEEL_SPEAR.get(), RankineItems.STAINLESS_STEEL_SPEAR.get(), RankineItems.NICKEL_SUPERALLOY_SPEAR.get(), RankineItems.COBALT_SUPERALLOY_SPEAR.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_SPEAR.get(), RankineItems.ROSE_GOLD_SPEAR.get(), RankineItems.BLUE_GOLD_SPEAR.get(), RankineItems.GREEN_GOLD_SPEAR.get(), RankineItems.WHITE_GOLD_SPEAR.get(), RankineItems.PURPLE_GOLD_SPEAR.get(), RankineItems.BLACK_GOLD_SPEAR.get(), RankineItems.AMALGAM_SPEAR.get(), RankineItems.ENDER_AMALGAM_SPEAR.get(), RankineItems.TITANIUM_ALLOY_SPEAR.get(), RankineItems.NIOBIUM_ALLOY_SPEAR.get(), RankineItems.ZIRCONIUM_ALLOY_SPEAR.get(), RankineItems.OSMIRIDIUM_SPEAR.get());
        tag(RankineTags.Items.KNIVES).add(RankineItems.FLINT_KNIFE.get(), RankineItems.PEWTER_KNIFE.get(), RankineItems.ALLOY_KNIFE.get(), RankineItems.BRONZE_KNIFE.get(), RankineItems.INVAR_KNIFE.get(), RankineItems.STEEL_KNIFE.get(), RankineItems.STAINLESS_STEEL_KNIFE.get(), RankineItems.NICKEL_SUPERALLOY_KNIFE.get(), RankineItems.COBALT_SUPERALLOY_KNIFE.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_KNIFE.get(), RankineItems.ROSE_GOLD_KNIFE.get(), RankineItems.BLUE_GOLD_KNIFE.get(), RankineItems.GREEN_GOLD_KNIFE.get(), RankineItems.WHITE_GOLD_KNIFE.get(), RankineItems.PURPLE_GOLD_KNIFE.get(), RankineItems.BLACK_GOLD_KNIFE.get(), RankineItems.AMALGAM_KNIFE.get(), RankineItems.ENDER_AMALGAM_KNIFE.get(), RankineItems.TITANIUM_ALLOY_KNIFE.get(), RankineItems.NIOBIUM_ALLOY_KNIFE.get(), RankineItems.ZIRCONIUM_ALLOY_KNIFE.get(), RankineItems.OSMIRIDIUM_KNIFE.get());
        tag(RankineTags.Items.CROWBARS).add(RankineItems.PEWTER_CROWBAR.get(), RankineItems.ALLOY_CROWBAR.get(), RankineItems.BRONZE_CROWBAR.get(), RankineItems.INVAR_CROWBAR.get(), RankineItems.STEEL_CROWBAR.get(), RankineItems.STAINLESS_STEEL_CROWBAR.get(), RankineItems.NICKEL_SUPERALLOY_CROWBAR.get(), RankineItems.COBALT_SUPERALLOY_CROWBAR.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_CROWBAR.get(), RankineItems.ROSE_GOLD_CROWBAR.get(), RankineItems.BLUE_GOLD_CROWBAR.get(), RankineItems.GREEN_GOLD_CROWBAR.get(), RankineItems.WHITE_GOLD_CROWBAR.get(), RankineItems.PURPLE_GOLD_CROWBAR.get(), RankineItems.BLACK_GOLD_CROWBAR.get(), RankineItems.AMALGAM_CROWBAR.get(), RankineItems.ENDER_AMALGAM_CROWBAR.get(), RankineItems.TITANIUM_ALLOY_CROWBAR.get(), RankineItems.NIOBIUM_ALLOY_CROWBAR.get(), RankineItems.ZIRCONIUM_ALLOY_CROWBAR.get(), RankineItems.OSMIRIDIUM_CROWBAR.get());
        tag(RankineTags.Items.SWORDS).add(RankineItems.PEWTER_SWORD.get(), RankineItems.ALLOY_SWORD.get(), RankineItems.BRONZE_SWORD.get(), RankineItems.INVAR_SWORD.get(), RankineItems.STEEL_SWORD.get(), RankineItems.STAINLESS_STEEL_SWORD.get(), RankineItems.NICKEL_SUPERALLOY_SWORD.get(), RankineItems.COBALT_SUPERALLOY_SWORD.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_SWORD.get(), RankineItems.ROSE_GOLD_SWORD.get(), RankineItems.BLUE_GOLD_SWORD.get(), RankineItems.GREEN_GOLD_SWORD.get(), RankineItems.WHITE_GOLD_SWORD.get(), RankineItems.PURPLE_GOLD_SWORD.get(), RankineItems.BLACK_GOLD_SWORD.get(), RankineItems.AMALGAM_SWORD.get(), RankineItems.ENDER_AMALGAM_SWORD.get(), RankineItems.TITANIUM_ALLOY_SWORD.get(), RankineItems.NIOBIUM_ALLOY_SWORD.get(), RankineItems.ZIRCONIUM_ALLOY_SWORD.get(), RankineItems.OSMIRIDIUM_SWORD.get());
        tag(RankineTags.Items.BLUNDERBUSSES).add(RankineItems.PEWTER_BLUNDERBUSS.get(), RankineItems.ALLOY_BLUNDERBUSS.get(), RankineItems.BRONZE_BLUNDERBUSS.get(), RankineItems.INVAR_BLUNDERBUSS.get(), RankineItems.STEEL_BLUNDERBUSS.get(), RankineItems.STAINLESS_STEEL_BLUNDERBUSS.get(), RankineItems.NICKEL_SUPERALLOY_BLUNDERBUSS.get(), RankineItems.COBALT_SUPERALLOY_BLUNDERBUSS.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_BLUNDERBUSS.get(), RankineItems.ROSE_GOLD_BLUNDERBUSS.get(), RankineItems.BLUE_GOLD_BLUNDERBUSS.get(), RankineItems.GREEN_GOLD_BLUNDERBUSS.get(), RankineItems.WHITE_GOLD_BLUNDERBUSS.get(), RankineItems.PURPLE_GOLD_BLUNDERBUSS.get(), RankineItems.BLACK_GOLD_BLUNDERBUSS.get(), RankineItems.AMALGAM_BLUNDERBUSS.get(), RankineItems.ENDER_AMALGAM_BLUNDERBUSS.get(), RankineItems.TITANIUM_ALLOY_BLUNDERBUSS.get(), RankineItems.NIOBIUM_ALLOY_BLUNDERBUSS.get(), RankineItems.ZIRCONIUM_ALLOY_BLUNDERBUSS.get(), RankineItems.OSMIRIDIUM_BLUNDERBUSS.get());
        tag(RankineTags.Items.HAMMERS).add(RankineItems.WOODEN_HAMMER.get(), RankineItems.STONE_HAMMER.get(), RankineItems.PEWTER_HAMMER.get(), RankineItems.ALLOY_HAMMER.get(), RankineItems.BRONZE_HAMMER.get(), RankineItems.INVAR_HAMMER.get(), RankineItems.STEEL_HAMMER.get(), RankineItems.STAINLESS_STEEL_HAMMER.get(), RankineItems.NICKEL_SUPERALLOY_HAMMER.get(), RankineItems.COBALT_SUPERALLOY_HAMMER.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_HAMMER.get(), RankineItems.ROSE_GOLD_HAMMER.get(), RankineItems.BLUE_GOLD_HAMMER.get(), RankineItems.GREEN_GOLD_HAMMER.get(), RankineItems.WHITE_GOLD_HAMMER.get(), RankineItems.PURPLE_GOLD_HAMMER.get(), RankineItems.BLACK_GOLD_HAMMER.get(), RankineItems.AMALGAM_HAMMER.get(), RankineItems.ENDER_AMALGAM_HAMMER.get(), RankineItems.TITANIUM_ALLOY_HAMMER.get(), RankineItems.NIOBIUM_ALLOY_HAMMER.get(), RankineItems.ZIRCONIUM_ALLOY_HAMMER.get(), RankineItems.OSMIRIDIUM_HAMMER.get());

        tag(RankineTags.Items.GOLD_PANS).add(RankineItems.WOODEN_GOLD_PAN.get(), RankineItems.PEWTER_GOLD_PAN.get(), RankineItems.STEEL_GOLD_PAN.get());
        tag(RankineTags.Items.SLUICING_TOOLS).addTags(RankineTags.Items.GOLD_PANS, RankineTags.Items.HAMMERS);
        tag(RankineTags.Items.BOWS).add(Items.BOW);
        tag(RankineTags.Items.SHIELDS).add(Items.SHIELD);

        tag(RankineTags.Items.MTT_DURABILITY_TOOLS).add(Items.PISTON);
        tag(RankineTags.Items.MTT_MINING_SPEED_TOOLS).add(RankineItems.SPEEDOMETER.get());
        tag(RankineTags.Items.MTT_HARVEST_LEVEL_TOOLS).add(RankineItems.HARDNESS_TESTER.get());
        tag(RankineTags.Items.MTT_ENCHANTABILITY_TOOLS).add(Items.LAPIS_LAZULI);
        tag(RankineTags.Items.MTT_ATTACK_DAMAGE_TOOLS).add(Items.GRINDSTONE);
        tag(RankineTags.Items.MTT_ATTACK_SPEED_TOOLS).add(Items.TARGET);
        tag(RankineTags.Items.MTT_CORROSION_RESISTANCE_TOOLS).add(Items.WATER_BUCKET);
        tag(RankineTags.Items.MTT_HEAT_RESISTANCE_TOOLS).add(Items.FLINT_AND_STEEL, Items.LAVA_BUCKET);
        tag(RankineTags.Items.MTT_KNOCKBACK_RESISTANCE_TOOLS).add(Items.SHIELD);
        tag(RankineTags.Items.MTT_TOUGHNESS_TOOLS).add(Items.ARMOR_STAND);
        tag(RankineTags.Items.MTT_ENCHANTMENT_TOOLS).add(RankineItems.MERCURY.get());
        tag(RankineTags.Items.MTT_EXAM_TOOLS).add(Items.BOOK, Items.WRITABLE_BOOK);
        tag(RankineTags.Items.MTT_TOOLS).addTags(RankineTags.Items.MTT_DURABILITY_TOOLS, RankineTags.Items.MTT_MINING_SPEED_TOOLS, RankineTags.Items.MTT_HARVEST_LEVEL_TOOLS, RankineTags.Items.MTT_DURABILITY_TOOLS, RankineTags.Items.MTT_ENCHANTABILITY_TOOLS, RankineTags.Items.MTT_ATTACK_DAMAGE_TOOLS, RankineTags.Items.MTT_ATTACK_SPEED_TOOLS, RankineTags.Items.MTT_CORROSION_RESISTANCE_TOOLS, RankineTags.Items.MTT_HEAT_RESISTANCE_TOOLS, RankineTags.Items.MTT_KNOCKBACK_RESISTANCE_TOOLS, RankineTags.Items.MTT_TOUGHNESS_TOOLS, RankineTags.Items.MTT_ENCHANTMENT_TOOLS, RankineTags.Items.MTT_EXAM_TOOLS);

        copy(RankineTags.Blocks.BRICKS, RankineTags.Items.BRICKS);
        copy(RankineTags.Blocks.BRICKS_SLAB, RankineTags.Items.BRICKS_SLABS);
        copy(RankineTags.Blocks.BRICKS_STAIRS, RankineTags.Items.BRICKS_STAIRS);
        copy(RankineTags.Blocks.BRICKS_WALL, RankineTags.Items.BRICKS_WALLS);
        copy(RankineTags.Blocks.SHEETMETAL, RankineTags.Items.SHEETMETAL);
        //copy(RankineTags.Blocks.SHEETMETAL_SALBS, RankineTags.Items.SHEETMETAL_SALBS);
        /*for (Block blk : RankineLists.SHEETMETALS) {
            String name = name(blk);
            String baseName = Arrays.asList(name.split("_sheetmetal")).get(0);
            tag(ItemTags.create(new ResourceLocation("forge", "sheetmetals/"+baseName))).add(blk.asItem());
        }
        for (Block blk : RankineLists.ALLOY_SHEETMETALS) {
            String name = name(blk);
            String baseName = Arrays.asList(name.split("_sheetmetal")).get(0);
            tag(ItemTags.create(new ResourceLocation("forge", "sheetmetals/"+baseName))).add(blk.asItem());
        }
        copy(RankineTags.Blocks.METAL_BARS, RankineTags.Items.METAL_BARS);
        copy(RankineTags.Blocks.METAL_POLES, RankineTags.Items.METAL_POLES);
        copy(RankineTags.Blocks.COBBLES, RankineTags.Items.COBBLES);

        for (Item item : Stream.of(RankineLists.ELEMENT_INGOTS, RankineLists.ALLOY_INGOTS,
                Arrays.asList(RankineItems.OPAL.get(), RankineItems.TOURMALINE.get(), RankineItems.AQUAMARINE.get(), RankineItems.TOPAZ.get(), RankineItems.RUBY.get(), RankineItems.SAPPHIRE.get(), RankineItems.GARNET.get(), RankineItems.PERIDOT.get(), RankineItems.LONSDALEITE_DIAMOND.get())).flatMap(Collection::stream).collect(Collectors.toList())) {
            tag(ItemTags.BEACON_PAYMENT_ITEMS).add(item);
        }

        //plants
        for (Block blk : RankineLists.WALL_MUSHROOMS) {
            tag(Tags.Items.MUSHROOMS).add(blk.asItem());
        }
        tag(RankineTags.Items.SEEDS_GRASS).add(RankineItems.GRASS_SEEDS.get());
        tag(RankineTags.Items.SEEDS_FLOWER).add(RankineItems.FLOWER_SEEDS.get());
        tag(RankineTags.Items.SEEDS_ASPARAGUS).add(RankineItems.ASPARAGUS_SEEDS.get());
        tag(RankineTags.Items.SEEDS_CORN).add(RankineItems.CORN_SEEDS.get());
        tag(RankineTags.Items.SEEDS_COTTON).add(RankineItems.COTTON_SEEDS.get());
        tag(RankineTags.Items.SEEDS_RICE).add(RankineItems.RICE_SEEDS.get());
        tag(RankineTags.Items.SEEDS_JUTE).add(RankineItems.JUTE_SEEDS.get());
        tag(RankineTags.Items.SEEDS_CAMPHOR_BASIL).add(RankineItems.CAMPHOR_BASIL_SEEDS.get());
        tag(RankineTags.Items.SEEDS_BARLEY).add(RankineItems.BARLEY_SEEDS.get());
        tag(RankineTags.Items.SEEDS_RYE).add(RankineItems.RYE_SEEDS.get());
        tag(RankineTags.Items.SEEDS_SORGHUM).add(RankineItems.SORGHUM_SEEDS.get());
        tag(RankineTags.Items.SEEDS_MILLET).add(RankineItems.MILLET_SEEDS.get());
        tag(RankineTags.Items.SEEDS_OATS).add(RankineItems.OAT_SEEDS.get());
        tag(RankineTags.Items.SEEDS_SOYBEAN).add(RankineItems.OAT_SEEDS.get());
        tag(Tags.Items.SEEDS).addTags(RankineTags.Items.SEEDS_GRASS, RankineTags.Items.SEEDS_FLOWER, RankineTags.Items.SEEDS_ASPARAGUS, RankineTags.Items.SEEDS_CORN, RankineTags.Items.SEEDS_COTTON, RankineTags.Items.SEEDS_RICE, RankineTags.Items.SEEDS_JUTE, RankineTags.Items.SEEDS_CAMPHOR_BASIL, RankineTags.Items.SEEDS_BARLEY, RankineTags.Items.SEEDS_RYE, RankineTags.Items.SEEDS_SORGHUM, RankineTags.Items.SEEDS_OATS, RankineTags.Items.SEEDS_MILLET, RankineTags.Items.SEEDS_SOYBEAN);

        tag(RankineTags.Items.CROPS_ASPARAGUS).add(RankineItems.ASPARAGUS.get());
        tag(RankineTags.Items.CROPS_CORN).add(RankineItems.CORN_EAR.get());
        tag(RankineTags.Items.CROPS_COTTON).add(RankineItems.COTTON.get());
        tag(RankineTags.Items.CROPS_RICE).add(RankineItems.RICE.get());
        tag(RankineTags.Items.CROPS_JUTE).add(RankineItems.JUTE.get());
        tag(RankineTags.Items.CROPS_CAMPHOR_BASIL).add(RankineItems.CAMPHOR_BASIL_LEAF.get());
        tag(RankineTags.Items.CROPS_BARLEY).add(RankineItems.BARLEY.get());
        tag(RankineTags.Items.CROPS_RYE).add(RankineItems.RYE.get());
        tag(RankineTags.Items.CROPS_SORGHUM).add(RankineItems.SORGHUM.get());
        tag(RankineTags.Items.CROPS_MILLET).add(RankineItems.MILLET.get());
        tag(RankineTags.Items.CROPS_OATS).add(RankineItems.OATS.get());
        tag(RankineTags.Items.CROPS_SOYBEAN).add(RankineItems.SOYBEANS.get());

        tag(RankineTags.Items.PINEAPPLE).add(RankineItems.PINEAPPLE.get());

        tag(RankineTags.Items.BERRIES_BLACKBERRY).add(RankineItems.BLACKBERRIES.get());
        tag(RankineTags.Items.BERRIES_BLUEBERRY).add(RankineItems.BLUEBERRIES.get());
        tag(RankineTags.Items.BERRIES_CRANBERRY).add(RankineItems.CRANBERRIES.get());
        tag(RankineTags.Items.BERRIES_ELDERBERRY).add(RankineItems.ELDERBERRIES.get());
        tag(RankineTags.Items.BERRIES_JUNIPER).add(RankineItems.JUNIPER_BERRIES.get());
        tag(RankineTags.Items.BERRIES_RASPBERRY).add(RankineItems.RASPBERRIES.get());
        tag(RankineTags.Items.BERRIES_SNOWBERRY).add(RankineItems.SNOWBERRIES.get());
        tag(RankineTags.Items.BERRIES_STRAWBERRY).add(RankineItems.STRAWBERRIES.get());
        tag(RankineTags.Items.BERRIES_POKEBERRY).add(RankineItems.POKEBERRIES.get());
        tag(RankineTags.Items.BERRIES_SWEET_BERRY).add(Items.SWEET_BERRIES);
        tag(RankineTags.Items.BERRIES).addTags(RankineTags.Items.BERRIES_BLACKBERRY, RankineTags.Items.BERRIES_BLUEBERRY, RankineTags.Items.BERRIES_CRANBERRY, RankineTags.Items.BERRIES_ELDERBERRY, RankineTags.Items.BERRIES_JUNIPER, RankineTags.Items.BERRIES_RASPBERRY, RankineTags.Items.BERRIES_SNOWBERRY, RankineTags.Items.BERRIES_SWEET_BERRY, RankineTags.Items.BERRIES_POKEBERRY, RankineTags.Items.BERRIES_STRAWBERRY);
        tag(RankineTags.Items.GRAIN).add(RankineItems.BARLEY.get(), RankineItems.OATS.get(), RankineItems.CORN_EAR.get(), RankineItems.SORGHUM.get(), RankineItems.MILLET.get(), RankineItems.RYE.get(), RankineItems.RICE.get());
        tag(RankineTags.Items.CARBS).add(RankineItems.BARLEY.get(), RankineItems.OATS.get(), RankineItems.CORN_EAR.get(), RankineItems.SORGHUM.get(), RankineItems.MILLET.get(), RankineItems.RYE.get(), RankineItems.RICE.get());
        tag(RankineTags.Items.FLOUR_PLANTS).add(RankineItems.BARLEY.get(), RankineItems.OATS.get(), RankineItems.CORN_EAR.get(), RankineItems.SORGHUM.get(), RankineItems.MILLET.get(), RankineItems.RYE.get(), RankineItems.RICE.get());
        tag(RankineTags.Items.FIBER_JUTE).add(RankineItems.JUTE.get());
        tag(RankineTags.Items.FIBER_PLANT).add(RankineItems.PLANT_FIBER.get());
        tag(RankineTags.Items.FIBER).addTag(RankineTags.Items.FIBER_JUTE).add(RankineItems.PLANT_FIBER.get());
        tag(RankineTags.Items.VEGETABLES_SOYBEANS).add(RankineItems.SOYBEANS.get());
        tag(RankineTags.Items.VEGETABLES).addTag(RankineTags.Items.VEGETABLES_SOYBEANS);
        tag(RankineTags.Items.FIRMTOFU).add(RankineItems.TOFU.get());
        //getOrCreateBuilder(RankineTags.Items.SOYMILK).add(RankineItems.SOY_MILK.get());
        //getOrCreateBuilder(RankineTags.Items.VINEGAR).add(RankineItems.COCONUT.get());
        tag(RankineTags.Items.JELLIES).add(RankineItems.FRUIT_JAM.get());
        tag(RankineTags.Items.NUTS_COCONUT).add(RankineItems.COCONUT.get());
        tag(RankineTags.Items.NUTS_WALNUT).add(RankineItems.BLACK_WALNUT.get());
        tag(RankineTags.Items.NUTS).add(RankineItems.COCONUT.get(), RankineItems.BLACK_WALNUT.get());

        //getOrCreateBuilder(RankineTags.Items.FISH).add(Items.SALMON,Items.SALMON,RankineItems.TUNA.get());


        tag(RankineTags.Items.BREEDABLES_COW).addTags(RankineTags.Items.CROPS_BARLEY, RankineTags.Items.CROPS_RYE, RankineTags.Items.CROPS_SORGHUM, RankineTags.Items.CROPS_MILLET, RankineTags.Items.CROPS_OATS, Tags.Items.CROPS_WHEAT, RankineTags.Items.CROPS_CORN);
        tag(RankineTags.Items.BREEDABLES_SHEEP).addTags(RankineTags.Items.CROPS_BARLEY, RankineTags.Items.CROPS_RYE, RankineTags.Items.CROPS_SORGHUM, RankineTags.Items.CROPS_MILLET, RankineTags.Items.CROPS_OATS, Tags.Items.CROPS_WHEAT, RankineTags.Items.CROPS_CORN);
        tag(RankineTags.Items.BREEDABLES_PIG).addTags(RankineTags.Items.CROPS_SOYBEAN, RankineTags.Items.CROPS_ASPARAGUS, RankineTags.Items.CROPS_CORN, Tags.Items.CROPS_BEETROOT, Tags.Items.CROPS_POTATO, Tags.Items.CROPS_CARROT);
        tag(RankineTags.Items.BREEDABLES_CHICKEN).addTag(Tags.Items.SEEDS);
        tag(RankineTags.Items.BREEDABLES_FOX).addTag(RankineTags.Items.BERRIES);
        tag(RankineTags.Items.BREEDABLES_CAT).add(Items.SALMON, Items.COD, RankineItems.TUNA.get());
        tag(RankineTags.Items.BREEDABLES_HORSE).add(Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.GOLDEN_CARROT);
        tag(RankineTags.Items.BREEDABLES_RABBIT).add(Items.DANDELION, Items.CARROT, Items.GOLDEN_CARROT);
        tag(RankineTags.Items.BREEDABLES_LLAMA).addTag(RankineTags.Items.GRAIN).addTag(RankineTags.Items.BALES);


        tag(RankineTags.Items.FLOUR).add(RankineItems.WHEAT_GRAIN.get(), RankineItems.BARLEY_GRAIN.get(), RankineItems.RYE_GRAIN.get(), RankineItems.CORN_GRAIN.get(), RankineItems.OAT_GRAIN.get(), RankineItems.MILLET_GRAIN.get(), RankineItems.RICE_GRAIN.get(), RankineItems.SORGHUM_GRAIN.get());


        tag(RankineTags.Items.FELDSPAR).add(RankineItems.ORTHOCLASE_FELDSPAR.get(), RankineItems.ORTHOCLASE_FELDSPAR.get());
        tag(RankineTags.Items.SALT).add(RankineItems.SODIUM_CHLORIDE.get(), RankineItems.PINK_SALT.get());
        tag(RankineTags.Items.FLINT).add(Items.FLINT);

        tag(RankineTags.Items.SALTPETER).add(RankineItems.SALTPETER.get());
        tag(RankineTags.Items.COKE).add(RankineItems.COKE.get());
        tag(RankineTags.Items.CHEESE).add(RankineItems.CHEESE.get());
        tag(RankineTags.Items.DOUGH).add(RankineItems.DOUGH.get());
        tag(RankineTags.Items.GRAPHITE).add(RankineItems.GRAPHITE.get());
        tag(RankineTags.Items.ASH).add(RankineItems.ASH.get(), RankineItems.BONE_ASH.get(), RankineItems.POZZOLANA.get());
        tag(RankineTags.Items.RUBBER).add(RankineItems.VULCANIZED_RUBBER.get());
        tag(RankineTags.Items.SAWDUST).add(RankineItems.SAWDUST.get());
        tag(RankineTags.Items.SLAG).add(RankineItems.SLAG.get());
        tag(RankineTags.Items.TRONA).add(RankineItems.TRONA.get());
        tag(RankineTags.Items.SILICON).add(RankineItems.SILICON.get());
        tag(RankineTags.Items.SILICON_CARBIDE).add(RankineItems.SILICON_CARBIDE.get());
        tag(RankineTags.Items.ASTATINE).add(RankineItems.ASTATINE.get());
        tag(RankineTags.Items.SULFUR).add(RankineItems.SULFUR.get());
        tag(RankineTags.Items.PHOSPHORUS).add(RankineItems.PHOSPHORUS.get());
        tag(RankineTags.Items.GEMS_AMBER).add(RankineItems.AMBER.get());
        tag(RankineTags.Items.GEMS_AQUAMARINE).add(RankineItems.AQUAMARINE.get());
        tag(RankineTags.Items.GEMS_CINNABAR).add(RankineItems.CINNABAR.get());
        tag(RankineTags.Items.GEMS_FLUORITE).add(RankineItems.FLUORITE.get());
        tag(RankineTags.Items.GEMS_GARNET).add(RankineItems.GARNET.get());
        tag(RankineTags.Items.GEMS_OPAL).add(RankineItems.OPAL.get());
        tag(RankineTags.Items.GEMS_PERIDOT).add(RankineItems.PERIDOT.get());
        tag(RankineTags.Items.GEMS_RUBY).add(RankineItems.RUBY.get());
        tag(RankineTags.Items.GEMS_SAPPHIRE).add(RankineItems.SAPPHIRE.get());
        tag(RankineTags.Items.GEMS_TOPAZ).add(RankineItems.TOPAZ.get());
        tag(RankineTags.Items.GEMS_TOURMALINE).add(RankineItems.TOURMALINE.get());
        tag(RankineTags.Items.GEMS_PEARL).add(RankineItems.PEARL.get());
        tag(Tags.Items.GEMS_DIAMOND).add(RankineItems.LONSDALEITE_DIAMOND.get());
        tag(Tags.Items.GEMS).addTags(RankineTags.Items.GEMS_AMBER, RankineTags.Items.GEMS_AQUAMARINE, RankineTags.Items.GEMS_CINNABAR, RankineTags.Items.GEMS_FLUORITE, RankineTags.Items.GEMS_GARNET, RankineTags.Items.GEMS_OPAL, RankineTags.Items.GEMS_PERIDOT, RankineTags.Items.GEMS_RUBY, RankineTags.Items.GEMS_SAPPHIRE, RankineTags.Items.GEMS_TOPAZ, RankineTags.Items.GEMS_TOURMALINE, RankineTags.Items.GEMS_PEARL);

        tag(RankineTags.Items.ROPE).add(RankineItems.ROPE.get());
        tag(Tags.Items.STRING).add(RankineItems.ROPE.get());
        tag(RankineTags.Items.CLAY_BALLS).add(RankineItems.FIRE_CLAY_BALL.get(), Items.CLAY_BALL, RankineItems.PORCELAIN_CLAY_BALL.get(), RankineItems.KAOLINITE.get(), RankineItems.HALLOYSITE.get());

        tag(RankineTags.Items.RODS_GRAPHITE).add(RankineItems.GRAPHITE_ELECTRODE.get());
        tag(RankineTags.Items.RODS_CARBON).add(RankineItems.HARD_CARBON_ELECTRODE.get());
        tag(Tags.Items.RODS).addTags(RankineTags.Items.RODS_GRAPHITE, RankineTags.Items.RODS_CARBON).add(RankineItems.ALLOY_ROD.get());

        tag(RankineTags.Items.ALKALI_HYDROXIDES).add(RankineItems.LITHIUM_HYDROXIDE.get(), RankineItems.SODIUM_HYDROXIDE.get(), RankineItems.POTASSIUM_HYDROXIDE.get(), RankineItems.RUBIDIUM_HYDROXIDE.get(), RankineItems.CESIUM_HYDROXIDE.get(), RankineItems.FRANCIUM_HYDROXIDE.get());
        tag(RankineTags.Items.ELECTROMAGNETS).add(RankineItems.SIMPLE_ELECTROMAGNET.get(), RankineItems.ALNICO_ELECTROMAGNET.get(), RankineItems.RARE_EARTH_ELECTROMAGNET.get());
        tag(RankineTags.Items.BATTERIES).add(RankineItems.SILVER_ZINC_BATTERY.get(), RankineItems.MAGNESIUM_BATTERY.get(), RankineItems.LEAD_ACID_BATTERY.get(), RankineItems.VANADIUM_REDOX_BATTERY.get(), RankineItems.ZINC_BROMINE_BATTERY.get(), RankineItems.SODIUM_SULFUR_BATTERY.get(), RankineItems.LITHIUM_ION_BATTERY.get());
        tag(RankineTags.Items.RTG).add(RankineItems.STRONTIUM_RTG.get(), RankineItems.POLONIUM_RTG.get(), RankineItems.PLUTONIUM_RTG.get(), RankineItems.AMERICIUM_RTG.get(), RankineItems.CURIUM_RTG.get());

        copy(RankineTags.Blocks.HARDENED_GLASS, RankineTags.Items.HARDENED_GLASS);
        copy(RankineTags.Blocks.CLAY, RankineTags.Items.CLAY);
        copy(RankineTags.Blocks.SILT, RankineTags.Items.SILT);
        copy(RankineTags.Blocks.BOOKSHELVES, Tags.Items.BOOKSHELVES);
        copy(RankineTags.Blocks.CONSTRUCTION_SAND, RankineTags.Items.CONSTRUCTION_SAND);
        copy(RankineTags.Blocks.GRASS_BLOCKS, RankineTags.Items.GRASS_BLOCKS);
        copy(Tags.Blocks.OBSIDIAN, Tags.Items.OBSIDIAN);
        copy(RankineTags.Blocks.PATH_BLOCKS, RankineTags.Items.PATH_BLOCKS);
        copy(RankineTags.Blocks.MUD, RankineTags.Items.MUD);
        copy(RankineTags.Blocks.COARSE_DIRT, RankineTags.Items.COARSE_DIRT);
        copy(Tags.Blocks.GRAVEL, Tags.Items.GRAVEL);
        copy(BlockTags.DIRT, ItemTags.DIRT);
        copy(RankineTags.Blocks.TUFF, RankineTags.Items.TUFF);
        copy(RankineTags.Blocks.MYCELIUM, RankineTags.Items.MYCELIUM);
        copy(RankineTags.Blocks.PODZOL, RankineTags.Items.PODZOL);
        copy(RankineTags.Blocks.CONCRETE, RankineTags.Items.CONCRETE);
        copy(RankineTags.Blocks.CONCRETE_POWDER, RankineTags.Items.CONCRETE_POWDER);
        copy(RankineTags.Blocks.LEDS, RankineTags.Items.LEDS);
        copy(RankineTags.Blocks.BALES, RankineTags.Items.BALES);
        copy(RankineTags.Blocks.MINERAL_WOOL, RankineTags.Items.MINERAL_WOOL);
        copy(RankineTags.Blocks.GEODES, RankineTags.Items.GEODES);

        copy(RankineTags.Blocks.IGNEOUS_STONES, RankineTags.Items.IGNEOUS_STONES);
        copy(RankineTags.Blocks.METAMORPHIC_STONES, RankineTags.Items.METAMORPHIC_STONES);
        copy(RankineTags.Blocks.SEDIMENTARY_STONES, RankineTags.Items.SEDIMENTARY_STONES);


        copy(BlockTags.STONE_BRICKS, ItemTags.STONE_BRICKS);
        copy(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
        copy(BlockTags.DOORS, ItemTags.DOORS);
        copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.FENCE_GATES, RankineTags.Items.FENCE_GATES);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        copy(BlockTags.STONE_PRESSURE_PLATES, RankineTags.Items.STONE_PRESSURE_PLATES);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.SAND, ItemTags.SAND);
        copy(Tags.Blocks.SAND, Tags.Items.SAND);
        copy(BlockTags.ICE, RankineTags.Items.ICE);
        copy(RankineTags.Blocks.POLISHED_STONE, RankineTags.Items.POLISHED_STONE);
        copy(Tags.Blocks.STONE, Tags.Items.STONE);
        copy(Tags.Blocks.SAND, Tags.Items.SAND);
        copy(Tags.Blocks.GLASS, Tags.Items.GLASS);
        copy(Tags.Blocks.COBBLESTONE_NORMAL, Tags.Items.COBBLESTONE_NORMAL);
        copy(Tags.Blocks.COBBLESTONE, Tags.Items.COBBLESTONE);
        //copy(BlockTags.CAMPFIRES, RankineTags.Items.CAMPFIRES);
        tag(RankineTags.Items.CAMPFIRES).add(Items.CAMPFIRE, Items.SOUL_CAMPFIRE);
        tag(RankineTags.Items.CANNONBALLS).add(Items.FIRE_CHARGE, RankineItems.CANNONBALL.get(), RankineItems.CARCASS.get(), RankineItems.ENDERBALL.get());
        tag(RankineTags.Items.MUSHROOMS).add(RankineItems.HONEY_MUSHROOM.get(), RankineItems.SULFUR_SHELF_MUSHROOM.get(), RankineItems.OYSTER_MUSHROOM.get(), RankineItems.LIONS_MANE_MUSHROOM.get(), RankineItems.ARTIST_CONK_MUSHROOM.get(), RankineItems.TINDER_CONK_MUSHROOM_BLOCK.get(), RankineItems.CINNABAR_POLYPORE_MUSHROOM.get(), RankineItems.TURKEY_TAIL_MUSHROOM.get(), Items.BROWN_MUSHROOM, Items.RED_MUSHROOM);
        tag(RankineTags.Items.EDIBLE_MUSHROOMS).add(RankineItems.HONEY_MUSHROOM.get(), RankineItems.SULFUR_SHELF_MUSHROOM.get(), RankineItems.OYSTER_MUSHROOM.get(), RankineItems.LIONS_MANE_MUSHROOM.get(), Items.BROWN_MUSHROOM, Items.RED_MUSHROOM);

        tag(RankineTags.Items.CRAFTING_METAL_NUGGETS).addTag(Tags.Items.NUGGETS_IRON).addTag(RankineTags.Items.NUGGETS_ALUMINUM).addTag(RankineTags.Items.NUGGETS_COBALT).addTag(RankineTags.Items.NUGGETS_MANGANESE).addTag(RankineTags.Items.NUGGETS_TITANIUM).addTag(RankineTags.Items.NUGGETS_BISMUTH).addTag(RankineTags.Items.NUGGETS_LEAD).addTag(RankineTags.Items.NUGGETS_NICKEL).addTag(RankineTags.Items.NUGGETS_TUNGSTEN).addTag(RankineTags.Items.NUGGETS_MAGNESIUM);
        tag(RankineTags.Items.CRAFTING_METAL_INGOTS).addTag(Tags.Items.INGOTS_IRON).addTag(RankineTags.Items.INGOTS_ALUMINUM).addTag(RankineTags.Items.INGOTS_COBALT).addTag(RankineTags.Items.INGOTS_MANGANESE).addTag(RankineTags.Items.INGOTS_TITANIUM).addTag(RankineTags.Items.INGOTS_BISMUTH).addTag(RankineTags.Items.INGOTS_LEAD).addTag(RankineTags.Items.INGOTS_NICKEL).addTag(RankineTags.Items.INGOTS_TUNGSTEN).addTag(RankineTags.Items.INGOTS_MAGNESIUM);
        tag(RankineTags.Items.CRAFTING_METAL_BLOCKS).addTag(Tags.Items.STORAGE_BLOCKS_IRON).addTag(RankineTags.Items.STORAGE_BLOCKS_ALUMINUM).addTag(RankineTags.Items.STORAGE_BLOCKS_COBALT).addTag(RankineTags.Items.STORAGE_BLOCKS_MANGANESE).addTag(RankineTags.Items.STORAGE_BLOCKS_TITANIUM).addTag(RankineTags.Items.STORAGE_BLOCKS_BISMUTH).addTag(RankineTags.Items.STORAGE_BLOCKS_LEAD).addTag(RankineTags.Items.STORAGE_BLOCKS_NICKEL).addTag(RankineTags.Items.STORAGE_BLOCKS_TUNGSTEN).addTag(RankineTags.Items.STORAGE_BLOCKS_MAGNESIUM);
        tag(RankineTags.Items.MAGNETIC_NUGGETS).addTag(Tags.Items.NUGGETS_IRON).addTag(RankineTags.Items.NUGGETS_ALUMINUM).addTag(RankineTags.Items.NUGGETS_COBALT).addTag(RankineTags.Items.NUGGETS_MANGANESE).addTag(RankineTags.Items.NUGGETS_TITANIUM).addTag(RankineTags.Items.NUGGETS_BISMUTH).addTag(RankineTags.Items.NUGGETS_LEAD).addTag(RankineTags.Items.NUGGETS_NICKEL).addTag(RankineTags.Items.NUGGETS_TUNGSTEN);
        tag(RankineTags.Items.MAGNETIC_INGOTS).addTag(Tags.Items.INGOTS_IRON).addTag(RankineTags.Items.INGOTS_ALUMINUM).addTag(RankineTags.Items.INGOTS_COBALT).addTag(RankineTags.Items.INGOTS_MANGANESE).addTag(RankineTags.Items.INGOTS_TITANIUM).addTag(RankineTags.Items.INGOTS_BISMUTH).addTag(RankineTags.Items.INGOTS_LEAD).addTag(RankineTags.Items.INGOTS_NICKEL).addTag(RankineTags.Items.INGOTS_TUNGSTEN);
        tag(RankineTags.Items.MAGNETIC_BLOCKS).addTag(Tags.Items.STORAGE_BLOCKS_IRON).addTag(RankineTags.Items.STORAGE_BLOCKS_ALUMINUM).addTag(RankineTags.Items.STORAGE_BLOCKS_COBALT).addTag(RankineTags.Items.STORAGE_BLOCKS_MANGANESE).addTag(RankineTags.Items.STORAGE_BLOCKS_TITANIUM).addTag(RankineTags.Items.STORAGE_BLOCKS_BISMUTH).addTag(RankineTags.Items.STORAGE_BLOCKS_LEAD).addTag(RankineTags.Items.STORAGE_BLOCKS_NICKEL).addTag(RankineTags.Items.STORAGE_BLOCKS_TUNGSTEN);
        tag(RankineTags.Items.COLORED_GOLD_INGOTS).add(RankineItems.ROSE_GOLD_INGOT.get(), RankineItems.GREEN_GOLD_INGOT.get(), RankineItems.WHITE_GOLD_INGOT.get(), RankineItems.BLUE_GOLD_INGOT.get(), RankineItems.PURPLE_GOLD_INGOT.get(), RankineItems.BLACK_GOLD_INGOT.get());
        tag(RankineTags.Items.COLORED_GOLD_TOOLS).addTags(RankineTags.Items.ROSE_GOLD_TOOLS, RankineTags.Items.GREEN_GOLD_TOOLS, RankineTags.Items.WHITE_GOLD_TOOLS, RankineTags.Items.BLUE_GOLD_TOOLS, RankineTags.Items.PURPLE_GOLD_TOOLS, RankineTags.Items.BLACK_GOLD_TOOLS);
        tag(RankineTags.Items.CRUDE_TOOLS).addTags(RankineTags.Items.WOODEN_TOOLS, RankineTags.Items.STONE_TOOLS, RankineTags.Items.GOLDEN_TOOLS, RankineTags.Items.COLORED_GOLD_TOOLS, RankineTags.Items.FLINT_TOOLS, RankineTags.Items.PEWTER_TOOLS, RankineTags.Items.OSMIRIDIUM_TOOLS, RankineTags.Items.AMALGAM_TOOLS);
        tag(RankineTags.Items.TREE_CHOPPERS).add(RankineItems.PEWTER_AXE.get(), RankineItems.ALLOY_AXE.get(), RankineItems.BRONZE_AXE.get(), RankineItems.INVAR_AXE.get(), RankineItems.STEEL_AXE.get(), RankineItems.STAINLESS_STEEL_AXE.get(), RankineItems.NICKEL_SUPERALLOY_AXE.get(), RankineItems.COBALT_SUPERALLOY_AXE.get(), RankineItems.TUNGSTEN_HEAVY_ALLOY_AXE.get(), RankineItems.ROSE_GOLD_AXE.get(), RankineItems.BLUE_GOLD_AXE.get(), RankineItems.GREEN_GOLD_AXE.get(), RankineItems.WHITE_GOLD_AXE.get(), RankineItems.PURPLE_GOLD_AXE.get(), RankineItems.BLACK_GOLD_AXE.get(), RankineItems.AMALGAM_AXE.get(), RankineItems.ENDER_AMALGAM_AXE.get(), RankineItems.OSMIRIDIUM_AXE.get(), RankineItems.TITANIUM_ALLOY_AXE.get(), RankineItems.NIOBIUM_ALLOY_AXE.get(), RankineItems.ZIRCONIUM_ALLOY_AXE.get());
        tag(RankineTags.Items.SILICON_DIOXIDE_BLOCKS).addTags(RankineTags.Items.STONES_QUARTZITE, RankineTags.Items.CONSTRUCTION_SAND, Tags.Items.STORAGE_BLOCKS_QUARTZ);
        tag(RankineTags.Items.FLUXES).addTags(RankineTags.Items.STONES_LIMESTONE, RankineTags.Items.STONES_DOLOMITE).add(RankineItems.DOLOMITE.get(), RankineItems.CALCITE.get(), RankineItems.QUICKLIME.get());
        tag(RankineTags.Items.GLASS_INPUTS).addTags(RankineTags.Items.SILT, Tags.Items.SAND, Tags.Items.GEMS_QUARTZ);
        tag(RankineTags.Items.TORCH).add(Items.TORCH, Items.REDSTONE_TORCH, Items.SOUL_TORCH);


        tag(ItemTags.STONE_CRAFTING_MATERIALS).add(RankineItems.SKARN.get(), RankineItems.BRECCIA.get());
        tag(ItemTags.FISHES).add(RankineItems.TUNA.get(), RankineItems.COOKED_TUNA.get());
        tag(ItemTags.ARROWS).add(RankineItems.ROPE_COIL_ARROW.get(), RankineItems.ALLOY_ARROW.get());
        tag(ItemTags.COALS).add(RankineItems.LIGNITE.get(), RankineItems.SUBBITUMINOUS_COAL.get(), RankineItems.BITUMINOUS_COAL.get(), RankineItems.ANTHRACITE_COAL.get());
        tag(ItemTags.STONE_TOOL_MATERIALS).add(RankineItems.SKARN.get(), RankineItems.BRECCIA.get());
        tag(ItemTags.PIGLIN_LOVED).addTag(RankineTags.Items.COLORED_GOLD_TOOLS).add(RankineItems.GREEN_GOLD_INGOT.get(), RankineItems.BLUE_GOLD_INGOT.get(), RankineItems.ROSE_GOLD_INGOT.get(), RankineItems.WHITE_GOLD_INGOT.get(), RankineItems.BLACK_GOLD_INGOT.get(), RankineItems.PURPLE_GOLD_INGOT.get(), RankineItems.GREEN_GOLD_BLOCK.get(), RankineItems.BLUE_GOLD_BLOCK.get(), RankineItems.ROSE_GOLD_BLOCK.get(), RankineItems.WHITE_GOLD_BLOCK.get(), RankineItems.BLACK_GOLD_BLOCK.get(), RankineItems.PURPLE_GOLD_BLOCK.get());
        tag(ItemTags.CLUSTER_MAX_HARVESTABLES).addTag(RankineTags.Items.PICKAXES);
        tag(ItemTags.FOX_FOOD).addTag(RankineTags.Items.BERRIES);






        /*
        this.copy(BlockTags.WOOL, ItemTags.WOOL);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.STONE_BRICKS, ItemTags.STONE_BRICKS);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        this.copy(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.DOORS, ItemTags.DOORS);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);


        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.WALLS, ItemTags.WALLS);
        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        //this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        //this.copy(BlockTags.LOGS, ItemTags.LOGS);
        this.copy(BlockTags.SAND, ItemTags.SAND);
        //this.copy(BlockTags.SMELTS_TO_GLASS, ItemTags.SMELTS_TO_GLASS);

        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        this.copy(BlockTags.FENCES, ItemTags.FENCES);
        this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
        this.copy(BlockTags.FLOWERS, ItemTags.FLOWERS);
        this.copy(BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS);
        this.copy(BlockTags.DIRT, ItemTags.DIRT);
        //this.copy(BlockTags.COMPLETES_FIND_TREE_TUTORIAL, ItemTags.COMPLETES_FIND_TREE_TUTORIAL);




    }

    private static ResourceLocation key(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }
    private static String name(Item item) {
        return key(item).getPath();
    }
    private static ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
    private static String name(Block blk) {
        return key(blk).getPath();
    }
}
*/
    }
}