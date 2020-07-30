package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.entities.RankineBoatEntity;
import com.cannolicatfish.rankine.fluids.ModFluids;
import com.cannolicatfish.rankine.items.*;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.items.tools.*;
import com.cannolicatfish.rankine.util.alloys.*;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
public class ModItems {

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ProjectRankine.MODID);

    private static <T extends Item> T add(String name, T item) {
        REGISTRY.register(name, () -> item);
        return item;
    }

//METALLURGY TAB
//=============================================================================
    //CRUSHED ORES
    public static final Item COPPER_HYDROXIDE = add("copper_hydroxide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item AZURITE = add("azurite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TIN_OXIDE = add("tin_oxide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ALUMINA = add("alumina", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ZINC_SULFIDE = add("zinc_sulfide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item IRON_OXIDE = add("iron_oxide", new SimpleTooltipItem("Fe3O4",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item NICKEL_SULFIDE = add("nickel_sulfide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNESIA = add("magnesia", new SimpleTooltipItem("MgO",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNESITE = add("magnesite", new SimpleTooltipItem("MgCO3",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item LEAD_SULFIDE = add("lead_sulfide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item VANADINITE = add("vanadinite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item BISMUTH_OXIDE = add("bismuth_oxide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SILVER_SULFIDE = add("silver_sulfide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MANGANESE_OXIDE = add("manganese_oxide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CHROMIUM_OXIDE = add("chromium_oxide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MOLYBDENUM_OXIDE = add("molybdenum_oxide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TUNGSTEN_OXIDE = add("tungsten_oxide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TITANIA = add("titania", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item NIOBIUM_OXIDE = add("niobium_oxide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TANTALUM_OXIDE = add("tantalum_oxide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item COBALTITE = add("cobaltite", new SimpleTooltipItem("CoAsS",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PLATINUM_ARSENIDE = add("platinum_arsenide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MONAZITE = add("monazite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ZIRCON = add("zircon", new SimpleTooltipItem("ZrSiO4",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item URANINITE = add("uraninite", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item FLUORITE = add("fluorite", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item GREENOCKITE = add("greenockite", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    //ELEMENT NUGGETS
    public static final Item COPPER_NUGGET = add("copper_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TIN_NUGGET = add("tin_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ALUMINUM_NUGGET = add("aluminum_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ZINC_NUGGET = add("zinc_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item NICKEL_NUGGET = add("nickel_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNESIUM_NUGGET = add("magnesium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item LEAD_NUGGET = add("lead_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item VANADIUM_NUGGET = add("vanadium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item BISMUTH_NUGGET = add("bismuth_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SILVER_NUGGET = add("silver_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MANGANESE_NUGGET = add("manganese_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CHROMIUM_NUGGET = add("chromium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MOLYBDENUM_NUGGET = add("molybdenum_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TUNGSTEN_NUGGET = add("tungsten_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TITANIUM_NUGGET = add("titanium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item NIOBIUM_NUGGET = add("niobium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TANTALUM_NUGGET = add("tantalum_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item COBALT_NUGGET = add("cobalt_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item IRIDIUM_NUGGET = add("iridium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PLATINUM_NUGGET = add("platinum_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item OSMIUM_NUGGET = add("osmium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ARSENIC_NUGGET = add("arsenic_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PIG_IRON_NUGGET = add("pig_iron_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item WROUGHT_IRON_NUGGET = add("wrought_iron_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item RHODIUM_NUGGET = add("rhodium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item RUTHENIUM_NUGGET = add("ruthenium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item LANTHANUM_NUGGET = add("lanthanum_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SODIUM_NUGGET = add("sodium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item POTASSIUM_NUGGET = add("potassium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CALCIUM_NUGGET = add("calcium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PALLADIUM_NUGGET = add("palladium_nugget", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item GALLIUM_NUGGET = add("gallium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item INDIUM_NUGGET = add("indium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CADMIUM_NUGGET = add("cadmium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SELENIUM_NUGGET = add("selenium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TELLURIUM_NUGGET = add("tellurium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item URANIUM_NUGGET = add("uranium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));



    //ELEMENT INGOTS
    public static final Item HYDROGEN_INGOT = add("hydrogen_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item HELIUM_INGOT = add("helium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final ReactiveItem LITHIUM_INGOT = add("lithium_ingot", new ReactiveItem(new Item.Properties().group(ProjectRankine.setup.rankineMetals),0.5f, false));
    public static final Item BERYLLIUM_INGOT = add("beryllium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BORON_INGOT = add("boron_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CARBON_INGOT = add("carbon_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NITROGEN_INGOT = add("nitrogen_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item OXYGEN_INGOT = add("oxygen_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item FLUORINE_INGOT = add("fluorine_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NEON_INGOT = add("neon_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final ReactiveItem SODIUM_INGOT = add("sodium_ingot", new ReactiveItem(new Item.Properties().group(ProjectRankine.setup.rankineMetals),1f, false));
    public static final Item MAGNESIUM_INGOT = add("magnesium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ALUMINUM_INGOT = add("aluminum_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SILICON = add("silicon", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PHOSPHORUS = add("phosphorus", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SULFUR = add("sulfur", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CHLORINE_INGOT = add("chlorine_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ARGON_INGOT = add("argon_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final ReactiveItem POTASSIUM_INGOT = add("potassium_ingot", new ReactiveItem(new Item.Properties().group(ProjectRankine.setup.rankineMetals), 1.5f, false));
    public static final Item CALCIUM_INGOT = add("calcium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SCANDIUM_INGOT = add("scandium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TITANIUM_INGOT = add("titanium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item VANADIUM_INGOT = add("vanadium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CHROMIUM_INGOT = add("chromium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MANGANESE_INGOT = add("manganese_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item COBALT_INGOT = add("cobalt_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NICKEL_INGOT = add("nickel_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item COPPER_INGOT = add("copper_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ZINC_INGOT = add("zinc_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item GALLIUM_INGOT = add("gallium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item GERMANIUM_INGOT = add("germanium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ARSENIC_INGOT = add("arsenic_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SELENIUM_INGOT = add("selenium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BROMINE_INGOT = add("bromine_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item KRYPTON_INGOT = add("krypton_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final ReactiveItem RUBIDIUM_INGOT = add("rubidium_ingot", new ReactiveItem(new Item.Properties().group(ProjectRankine.setup.rankineMetals),2f,true));
    public static final Item STRONTIUM_INGOT = add("strontium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item YTTRIUM_INGOT = add("yttrium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ZIRCONIUM_INGOT = add("zirconium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NIOBIUM_INGOT = add("niobium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MOLYBDENUM_INGOT = add("molybdenum_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TECHNETIUM_INGOT = add("technetium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RUTHENIUM_INGOT = add("ruthenium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RHODIUM_INGOT = add("rhodium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PALLADIUM_INGOT = add("palladium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SILVER_INGOT = add("silver_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CADMIUM_INGOT = add("cadmium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item INDIUM_INGOT = add("indium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TIN_INGOT = add("tin_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ANTIMONY_INGOT = add("antimony_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TELLURIUM_INGOT = add("tellurium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item IODINE_INGOT = add("iodine_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item XENON_INGOT = add("xenon_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final ReactiveItem CESIUM_INGOT = add("cesium_ingot", new ReactiveItem(new Item.Properties().group(ProjectRankine.setup.rankineMetals), 4f, true));
    public static final Item BARIUM_INGOT = add("barium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LANTHANUM_INGOT = add("lanthanum_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CERIUM_INGOT = add("cerium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PRASEODYMIUM_INGOT = add("praseodymium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NEODYMIUM_INGOT = add("neodymium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PROMETHIUM_INGOT = add("promethium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SAMARIUM_INGOT = add("samarium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item EUROPIUM_INGOT = add("europium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item GADOLINIUM_INGOT = add("gadolinium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TEBRIUM_INGOT = add("tebrium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item DYSPROSIUM_INGOT = add("dysprosium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item HOLMIUM_INGOT = add("holmium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ERIBIUM_INGOT = add("eribium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item THULIUM_INGOT = add("thulium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item YTTERBIUM_INGOT = add("ytterbium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LUTETIUM_INGOT = add("lutetium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item HAFNIUM_INGOT = add("hafnium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TANTALUM_INGOT = add("tantalum_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TUNGSTEN_INGOT = add("tungsten_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RHENIUM_INGOT = add("rhenium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item OSMIUM_INGOT = add("osmium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item IRIDIUM_INGOT = add("iridium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PLATINUM_INGOT = add("platinum_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MERCURY_INGOT = add("mercury_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item THALLIUM_INGOT = add("thallium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LEAD_INGOT = add("lead_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BISMUTH_INGOT = add("bismuth_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item POLONIUM_INGOT = add("polonium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ASTATINE = add("astatine", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RADON_INGOT = add("radon_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item FRANCIUM_INGOT = add("francium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RADIUM_INGOT = add("radium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ACTINIUM_INGOT = add("actinium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item THORIUM_INGOT = add("thorium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PROTACTINIUM_INGOT = add("protactinium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item URANIUM_INGOT = add("uranium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NEPTUNIUM_INGOT = add("neptunium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PLUTONIUM_INGOT = add("plutonium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item AMERICIUM_INGOT = add("americium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CURIUM_INGOT = add("curium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BERKELIUM_INGOT = add("berkelium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CALIFORNIUM_INGOT = add("californium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item EINSTEINIUM_INGOT = add("einsteinium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item FERMIUM_INGOT = add("fermium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MENDELEVIUM_INGOT = add("mendelevium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NOBELIUM_INGOT = add("nobelium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LAWRENCIUM_INGOT = add("lawrencium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RUTHERFORDIUM_INGOT = add("rutherfordium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item DUBNIUM_INGOT = add("dubnium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SEABORGIUM_INGOT = add("seaborgium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BOHRIUM_INGOT = add("bohrium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item HASSIUM_INGOT = add("hassium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MEITERIUM_INGOT = add("meiterium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item DARMSTADTIUM_INGOT = add("darmstadtium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ROENTGENIUM_INGOT = add("roentgenium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item COPERNICIUM_INGOT = add("copernicium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NIHONIUM_INGOT = add("nihonium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item FLEROVIUM_INGOT = add("flerovium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MOSCOVIUM_INGOT = add("moscovium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LIVERMORIUM_INGOT = add("livermorium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TENNESSINE_INGOT = add("tennessine_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item OGANESSON_INGOT = add("oganesson_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PIG_IRON_INGOT = add("pig_iron_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item WROUGHT_IRON_INGOT = add("wrought_iron_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CAST_IRON_INGOT = add("cast_iron_ingot", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    //ALLOY INGOTS
    public static final AlloyItem BRONZE_ALLOY = add("bronze_alloy", new AlloyItem(new BronzeAlloyUtils().getDefComposition(),new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem ALUMINUM_BRONZE_ALLOY = add("aluminum_bronze_alloy", new AlloyItem("90Cu-10Al",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem BRASS_ALLOY = add("brass_alloy", new AlloyItem("60Cu-40Zn",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem NICHROME_ALLOY = add("nichrome_alloy", new AlloyItem("80Ni-20Cr",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item STEEL_ALLOY = add("steel_alloy", new AlloyItem("98Fe-2C",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem STAINLESS_STEEL_ALLOY = add("stainless_steel_alloy", new AlloyItem(new StainlessSteelAlloyUtils().getDefComposition(),new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SOLDER_ALLOY = add("solder_alloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ALNICO_ALLOY = add("alnico_alloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem NITINOL_ALLOY = add("nitinol_alloy", new AlloyItem("50Ni-50Ti",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem AMALGAM_ALLOY = add("amalgam_alloy", new AlloyItem(new AmalgamAlloyUtils().getDefComposition(),new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem CUPRONICKEL_ALLOY = add("cupronickel_alloy", new AlloyItem("85Cu-15Ni",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item NICKEL_SILVER_ALLOY = add("nickel_silver_alloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));;
    public static final Item INVAR_ALLOY = add("invar_alloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item NICKEL_SUPERALLOY = add("nickel_superalloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ROSE_METAL_ALLOY = add("rose_metal_alloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNALIUM_ALLOY = add("magnalium_alloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item DURALUMIN_ALLOY = add("duralumin_alloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item OSMIRIDIUM_ALLOY = add("osmiridium_alloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem ROSE_GOLD_ALLOY = add("rose_gold_alloy", new AlloyItem("75Au-22Cu-3Ni", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem WHITE_GOLD_ALLOY = add("white_gold_alloy", new AlloyItem("90Au-10Ni",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem GREEN_GOLD_ALLOY = add("green_gold_alloy", new AlloyItem("50Au-50Ag", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem BLUE_GOLD_ALLOY = add("blue_gold_alloy", new AlloyItem("75Au-25Fe", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem PURPLE_GOLD_ALLOY = add("purple_gold_alloy", new AlloyItem("80Au-20Al", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem METEORIC_IRON = add("meteoric_iron", new AlloyItem("90Fe-10Ni", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));


    public static final Item CLAY_BRICK = add("clay_brick", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item REFRACTORY_BRICK = add("refractory_brick", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNESIUM_REFRACTORY_BRICK = add("magnesium_refractory_brick", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ZIRCON_REFRACTORY_BRICK = add("zircon_refractory_brick", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item LIGNITE = add("lignite", new FuelItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals),1200));
    public static final Item BITUMINOUS_COAL = add("bituminous_coal", new FuelItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals),2000));
    public static final Item COKE = add("coke", new FuelItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals), 2400));
    public static final Item ANTHRACITE_COAL = add("anthracite_coal", new FuelItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals), 2400));
    public static final Item CALCITE = add("calcite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item DOLOMITE = add("dolomite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item FELDSPAR = add("feldspar", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item OLIVINE = add("olivine", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PYROXENE = add("pyroxene", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item BLOOM_IRON = add("bloom_iron", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SLAG = add("slag", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SALT = add("salt", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PINK_SALT = add("pink_salt", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SALTPETER = add("saltpeter", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item GRAPHITE = add("graphite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CALCIUM_SILICATE = add("calcium_silicate", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SILICON_CARBIDE = add("silicon_carbide", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CRUSHED_COAL = add("crushed_coal", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CINNABAR = add("cinnabar", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item RUBY = add("ruby", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SAPPHIRE = add("sapphire", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item OPAL = add("opal", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item AQUAMARINE = add("aquamarine", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TIGER_IRON = add("tiger_iron", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item GARNET = add("garnet", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PERIDOT = add("peridot", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item QUICKLIME = add("quicklime", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item POZZOLAN = add("pozzolan", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MORTAR = add("mortar", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item POZZOLANIC_MORTAR = add("pozzolanic_mortar", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ELEMENT = add("element", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineMetals)));






//RANKINE TOOLS CREATIVE TAB --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //NORMAL TOOLS
    public static final Item  STONE_HAMMER = add("stone_hammer", new ItemHammer(2, -3.2F, RankineToolMaterials.FLINT, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BONE_SHOVEL = add("bone_shovel", new BoneShovel(ItemTier.STONE, 1.5F, -3.0F, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINT_PICKAXE = add("flint_pickaxe", new PickaxeItem(RankineToolMaterials.FLINT, 1, -2.8F, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINT_AXE = add("flint_axe", new AxeItem(RankineToolMaterials.FLINT, 4.0F, -3.2F, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINT_SHOVEL = add("flint_shovel", new ShovelItem(RankineToolMaterials.FLINT, 1.5F, -3.0F, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINT_SPEAR = add("flint_spear", new ItemSpear(RankineToolMaterials.FLINT, 2, -2.9F, ModEntityTypes.FLINT_SPEAR,new ResourceLocation("rankine:textures/entity/flint_spear.png"),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINT_KNIFE = add("flint_knife", new ItemKnife(RankineToolMaterials.FLINT, 1, -2F, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_SWORD = add("bronze_sword", new AlloySword(RankineToolMaterials.BRONZE, 3, -2.4F, new BronzeAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_SHOVEL = add("bronze_shovel", new AlloyShovel(RankineToolMaterials.BRONZE, 1.5F, -3.0F, new BronzeAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_PICKAXE = add("bronze_pickaxe", new AlloyPickaxe(RankineToolMaterials.BRONZE, 1, -2.8F, new BronzeAlloyUtils(), (new Item.Properties()).group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_AXE = add("bronze_axe", new AlloyAxe(RankineToolMaterials.BRONZE, 4.0F, -3.2F, new BronzeAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_HAMMER = add("bronze_hammer", new AlloyHammer(RankineToolMaterials.BRONZE,2, -3.2F, new BronzeAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_HOE = add("bronze_hoe", new AlloyHoe(RankineToolMaterials.BRONZE, -1,-2.0F, new BronzeAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_SPEAR = add("bronze_spear", new AlloySpear(RankineToolMaterials.BRONZE, 2, -2.9F, new BronzeAlloyUtils(),ModEntityTypes.BRONZE_SPEAR,new ResourceLocation("rankine:textures/entity/bronze_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  IRON_SPEAR = add("iron_spear", new ItemSpear(ItemTier.IRON, 2, -2.9F, ModEntityTypes.IRON_SPEAR,new ResourceLocation("rankine:textures/entity/iron_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  IRON_HAMMER = add("iron_hammer", new ItemHammer(2, -3.2F, ItemTier.IRON, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  IRON_SCRAPER = add("iron_scraper", new ItemScraper(new Item.Properties().maxStackSize(1).maxDamage(256).group(ProjectRankine.setup.rankineTools)));

    public static final Item  METEORIC_IRON_SWORD = add("meteoric_iron_sword", new AlloySword(RankineToolMaterials.METEORIC_IRON, 3, -2.4F, new MeteoricIronAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  METEORIC_IRON_SHOVEL = add("meteoric_iron_shovel", new AlloyShovel(RankineToolMaterials.METEORIC_IRON, 1.5F, -3.0F, new MeteoricIronAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  METEORIC_IRON_PICKAXE = add("meteoric_iron_pickaxe", new AlloyPickaxe(RankineToolMaterials.METEORIC_IRON, 1, -2.8F,new MeteoricIronAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  METEORIC_IRON_AXE = add("meteoric_iron_axe", new AlloyAxe(RankineToolMaterials.METEORIC_IRON, 4.0F, -3.2F, new MeteoricIronAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  METEORIC_IRON_HAMMER = add("meteoric_iron_hammer", new AlloyHammer(RankineToolMaterials.METEORIC_IRON, 2, -3.2F, new MeteoricIronAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  METEORIC_IRON_HOE = add("meteoric_iron_hoe", new AlloyHoe(RankineToolMaterials.METEORIC_IRON, -2, -1.0F, new MeteoricIronAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  METEORIC_IRON_SPEAR = add("meteoric_iron_spear", new AlloySpear(RankineToolMaterials.METEORIC_IRON,2, -2.9F, new MeteoricIronAlloyUtils(),ModEntityTypes.METEORIC_IRON_SPEAR,new ResourceLocation("rankine:textures/entity/meteoric_iron_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item  STEEL_SWORD = add("steel_sword", new AlloySword(RankineToolMaterials.STEEL, 3, -2.4F, new SteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STEEL_SHOVEL = add("steel_shovel", new AlloyShovel(RankineToolMaterials.STEEL, 1.5F, -3.0F, new SteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STEEL_PICKAXE = add("steel_pickaxe", new AlloyPickaxe(RankineToolMaterials.STEEL, 1, -2.8F, new SteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STEEL_AXE = add("steel_axe", new AlloyAxe(RankineToolMaterials.STEEL, 4.0F, -3.2F, new SteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STEEL_HAMMER = add("steel_hammer", new AlloyHammer(RankineToolMaterials.STEEL,2, -3.2F,  new SteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STEEL_HOE = add("steel_hoe", new AlloyHoe(RankineToolMaterials.STEEL, -3,0.0F, new SteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STEEL_SPEAR = add("steel_spear", new AlloySpear(RankineToolMaterials.STEEL, 2, -2.9F, new SteelAlloyUtils(),ModEntityTypes.STEEL_SPEAR,new ResourceLocation("rankine:textures/entity/steel_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    //GOLD TOOLS
    public static final Item  ROSE_GOLD_SWORD = add("rose_gold_sword", new AlloySword(RankineToolMaterials.ROSE_GOLD,3, -2.4F, new RoseGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  ROSE_GOLD_SHOVEL = add("rose_gold_shovel", new AlloyShovel(RankineToolMaterials.ROSE_GOLD,1.5F, -3.0F, new RoseGoldAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  ROSE_GOLD_PICKAXE = add("rose_gold_pickaxe", new AlloyPickaxe(RankineToolMaterials.ROSE_GOLD, 1, -2.8F, new RoseGoldAlloyUtils(), (new Item.Properties()).group(ProjectRankine.setup.rankineTools)));
    public static final Item  ROSE_GOLD_AXE = add("rose_gold_axe", new AlloyAxe(RankineToolMaterials.ROSE_GOLD,4.0F, -3.2F, new RoseGoldAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  ROSE_GOLD_HAMMER = add("rose_gold_hammer", new AlloyHammer(RankineToolMaterials.ROSE_GOLD,2, -3.2F, new RoseGoldAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  ROSE_GOLD_HOE = add("rose_gold_hoe", new AlloyHoe(RankineToolMaterials.ROSE_GOLD, 0,-3.0F, new RoseGoldAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  ROSE_GOLD_SPEAR = add("rose_gold_spear", new AlloySpear(RankineToolMaterials.ROSE_GOLD,2, -2.9F, new RoseGoldAlloyUtils(),ModEntityTypes.ROSE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/rose_gold_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item  WHITE_GOLD_SWORD = add("white_gold_sword", new AlloySword(RankineToolMaterials.WHITE_GOLD,3, -2.4F, new WhiteGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  WHITE_GOLD_SHOVEL = add("white_gold_shovel", new AlloyShovel(RankineToolMaterials.WHITE_GOLD,1.5F, -3.0F, new WhiteGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  WHITE_GOLD_PICKAXE = add("white_gold_pickaxe", new AlloyPickaxe(RankineToolMaterials.WHITE_GOLD, 1, -2.8F, new WhiteGoldAlloyUtils(), (new Item.Properties()).group(ProjectRankine.setup.rankineTools)));
    public static final Item  WHITE_GOLD_AXE = add("white_gold_axe", new AlloyAxe(RankineToolMaterials.WHITE_GOLD,4.0F, -3.2F,new WhiteGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  WHITE_GOLD_HAMMER = add("white_gold_hammer", new AlloyHammer(RankineToolMaterials.WHITE_GOLD,2, -3.2F,  new WhiteGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  WHITE_GOLD_HOE = add("white_gold_hoe", new AlloyHoe(RankineToolMaterials.WHITE_GOLD,0,-3.0F,new WhiteGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  WHITE_GOLD_SPEAR = add("white_gold_spear", new AlloySpear(RankineToolMaterials.WHITE_GOLD,2, -2.9F,new WhiteGoldAlloyUtils(),ModEntityTypes.WHITE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/white_gold_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item  GREEN_GOLD_SWORD = add("green_gold_sword", new AlloySword(RankineToolMaterials.GREEN_GOLD,3, -2.4F, new GreenGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  GREEN_GOLD_SHOVEL = add("green_gold_shovel", new AlloyShovel(RankineToolMaterials.GREEN_GOLD,1.5F, -3.0F, new GreenGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  GREEN_GOLD_PICKAXE = add("green_gold_pickaxe", new AlloyPickaxe(RankineToolMaterials.GREEN_GOLD, 1, -2.8F, new GreenGoldAlloyUtils(), (new Item.Properties()).group(ProjectRankine.setup.rankineTools)));
    public static final Item  GREEN_GOLD_AXE = add("green_gold_axe", new AlloyAxe(RankineToolMaterials.GREEN_GOLD,4.0F, -3.2F, new GreenGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  GREEN_GOLD_HAMMER = add("green_gold_hammer", new AlloyHammer(RankineToolMaterials.GREEN_GOLD,2, -3.2F,new GreenGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  GREEN_GOLD_HOE = add("green_gold_hoe", new AlloyHoe(RankineToolMaterials.GREEN_GOLD,0,-3.0F, new GreenGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  GREEN_GOLD_SPEAR = add("green_gold_spear", new AlloySpear(RankineToolMaterials.GREEN_GOLD,2, -2.9F, new GreenGoldAlloyUtils(),ModEntityTypes.GREEN_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/green_gold_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item  BLUE_GOLD_SWORD = add("blue_gold_sword", new AlloySword(RankineToolMaterials.BLUE_GOLD,3, -2.4F, new BlueGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLUE_GOLD_SHOVEL = add("blue_gold_shovel", new AlloyShovel(RankineToolMaterials.BLUE_GOLD,1.5F, -3.0F, new BlueGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLUE_GOLD_PICKAXE = add("blue_gold_pickaxe", new AlloyPickaxe(RankineToolMaterials.BLUE_GOLD, 1, -2.8F, new BlueGoldAlloyUtils(), (new Item.Properties()).group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLUE_GOLD_AXE = add("blue_gold_axe", new AlloyAxe(RankineToolMaterials.BLUE_GOLD,4.0F, -3.2F, new BlueGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLUE_GOLD_HAMMER = add("blue_gold_hammer", new AlloyHammer(RankineToolMaterials.BLUE_GOLD,2, -3.2F, new BlueGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLUE_GOLD_HOE = add("blue_gold_hoe", new AlloyHoe(RankineToolMaterials.BLUE_GOLD,0,-3.0F,new BlueGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLUE_GOLD_SPEAR = add("blue_gold_spear", new AlloySpear(RankineToolMaterials.BLUE_GOLD,2, -2.9F, new BlueGoldAlloyUtils(),ModEntityTypes.BLUE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/blue_gold_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item  PURPLE_GOLD_SWORD = add("purple_gold_sword", new AlloySword(RankineToolMaterials.PURPLE_GOLD,3, -2.4F, new PurpleGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  PURPLE_GOLD_SHOVEL = add("purple_gold_shovel", new AlloyShovel(RankineToolMaterials.PURPLE_GOLD,1.5F, -3.0F, new PurpleGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  PURPLE_GOLD_PICKAXE = add("purple_gold_pickaxe", new AlloyPickaxe(RankineToolMaterials.PURPLE_GOLD, 1, -2.8F, new PurpleGoldAlloyUtils(), (new Item.Properties()).group(ProjectRankine.setup.rankineTools)));
    public static final Item  PURPLE_GOLD_AXE = add("purple_gold_axe", new AlloyAxe(RankineToolMaterials.PURPLE_GOLD,4.0F, -3.2F, new PurpleGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  PURPLE_GOLD_HAMMER = add("purple_gold_hammer", new AlloyHammer(RankineToolMaterials.PURPLE_GOLD,2, -3.2F,new PurpleGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  PURPLE_GOLD_HOE = add("purple_gold_hoe", new AlloyHoe(RankineToolMaterials.PURPLE_GOLD,0,-3.0F, new PurpleGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  PURPLE_GOLD_SPEAR = add("purple_gold_spear", new AlloySpear(RankineToolMaterials.PURPLE_GOLD,2, -2.9F, new PurpleGoldAlloyUtils(),ModEntityTypes.PURPLE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/purple_gold_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    //AMALGAM TOOLS
    public static final Item  AMALGAM_SWORD = add("amalgam_sword", new AlloySword(RankineToolMaterials.AMALGAM, 3, -2.4F, new AmalgamAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  AMALGAM_SHOVEL = add("amalgam_shovel", new AlloyShovel(RankineToolMaterials.AMALGAM, 1.5F, -3.0F, new AmalgamAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  AMALGAM_PICKAXE = add("amalgam_pickaxe", new AlloyPickaxe(RankineToolMaterials.AMALGAM, 1, -2.8F,new AmalgamAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  AMALGAM_AXE = add("amalgam_axe", new AlloyAxe(RankineToolMaterials.AMALGAM, 4.0F, -3.2F, new AmalgamAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  AMALGAM_HAMMER = add("amalgam_hammer", new AlloyHammer(RankineToolMaterials.AMALGAM, 2, -3.2F, new AmalgamAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  AMALGAM_HOE = add("amalgam_hoe", new AlloyHoe(RankineToolMaterials.AMALGAM, 1,-1.0F, new AmalgamAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  AMALGAM_SPEAR = add("amalgam_spear", new AlloySpear(RankineToolMaterials.AMALGAM,2, -2.9F, new AmalgamAlloyUtils(),ModEntityTypes.AMALGAM_SPEAR,new ResourceLocation("rankine:textures/entity/amalgam_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item DIAMOND_HAMMER = add("diamond_hammer", new ItemHammer(2, -3.2F, ItemTier.DIAMOND,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item DIAMOND_SPEAR = add("diamond_spear", new ItemSpear(ItemTier.DIAMOND,2, -2.9F ,ModEntityTypes.DIAMOND_SPEAR,new ResourceLocation("rankine:textures/entity/diamond_spear.png"),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item NETHERITE_HAMMER = add("netherite_hammer", new ItemHammer(2, -3.2F, ItemTier.NETHERITE,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item NETHERITE_SPEAR = add("netherite_spear", new ItemSpear(ItemTier.NETHERITE,2, -2.9F ,ModEntityTypes.NETHERITE_SPEAR,new ResourceLocation("rankine:textures/entity/netherite_spear.png"),new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item  STEEL_GOLD_PAN = add("steel_gold_pan", new ItemGoldPan(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINTLOCK_PISTOL = add("flintlock_pistol", new FlintlockPistol(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  LEAD_SHOT = add("lead_shot", new LeadShotItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  THERMOMETER = add("thermometer", new ThermometerItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  METAL_DETECTOR = add("metal_detector", new MetalDetector(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item TRANSFORMATION_STAFF = add("transformation_staff", new TransformationStaffItem(new Item.Properties().maxDamage(1024).group(ProjectRankine.setup.rankineTools)));
    public static final Item GLASS_CUTTER = add("glass_cutter", new GlassCutterItem(new Item.Properties().maxDamage(256).group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRIGADINE_HELMET = add("brigandine_helmet", new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.HEAD, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRIGADINE_CHESTPLATE = add("brigandine_chestplate", new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.CHEST, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRIGADINE_LEGGINGS = add("brigandine_leggings", new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.LEGS, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRIGADINE_BOOTS = add("brigandine_boots", new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.FEET, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item GEM_CORE = add("gem_core", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item LUCK_PENDANT = add("luck_pendant", new PendantItem(new Item.Properties().maxDamage(300).group(ProjectRankine.setup.rankineTools)));
    public static final Item SPEED_PENDANT = add("speed_pendant", new PendantItem(new Item.Properties().maxDamage(300).group(ProjectRankine.setup.rankineTools)));



    //BUCKETS
    public static final Item  WOOD_BUCKET = add("wood_bucket", new WoodBucket(() -> Fluids.EMPTY, (new Item.Properties().containerItem(ModItems.WOOD_BUCKET)).maxStackSize(16).group(ProjectRankine.setup.rankineTools)));
    public static final Item  WATER_WOOD_BUCKET = add("water_wood_bucket", new WoodBucket(() -> Fluids.WATER, (new Item.Properties().containerItem(ModItems.WOOD_BUCKET)).maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  MILK_WOOD_BUCKET = add("milk_wood_bucket", new MilkWoodBucket(new Item.Properties().group(ProjectRankine.setup.rankineTools).containerItem(ModItems.WOOD_BUCKET).maxStackSize(1)));
    public static final Item  BRASS_BUCKET = add("brass_bucket", new BrassBucket(() -> Fluids.EMPTY, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(16).group(ProjectRankine.setup.rankineTools)));
    public static final Item  WATER_BRASS_BUCKET = add("water_brass_bucket", new BrassBucket(() -> Fluids.WATER, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  LAVA_BRASS_BUCKET = add("lava_brass_bucket", new BrassBucket(() -> Fluids.LAVA, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  LIQUID_MERCURY_BRASS_BUCKET = add("liquid_mercury_brass_bucket", new BrassBucket(() -> ModFluids.LIQUID_MERCURY, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  MILK_BRASS_BUCKET = add("milk_brass_bucket", new MilkBrassBucket(new Item.Properties().group(ProjectRankine.setup.rankineTools).containerItem(ModItems.WOOD_BUCKET).maxStackSize(1).group(ProjectRankine.setup.rankineTools)));


    //TEMPLATES
    public static final Item  SHOVEL_TEMPLATE = add("shovel_template", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  HOE_TEMPLATE = add("hoe_template", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  SPEAR_TEMPLATE = add("spear_template", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  PICKAXE_TEMPLATE = add("pickaxe_template", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  SWORD_TEMPLATE = add("sword_template", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  AXE_TEMPLATE = add("axe_template", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  HAMMER_TEMPLATE = add("hammer_template", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));

    public static final Item  CEDAR_BOAT = add("cedar_boat", new RankineBoatItem(RankineBoatEntity.Type.CEDAR, new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  COCONUT_PALM_BOAT = add("coconut_palm_boat", new RankineBoatItem(RankineBoatEntity.Type.COCONUT_PALM, new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  PINYON_PINE_BOAT = add("pinyon_pine_boat", new RankineBoatItem(RankineBoatEntity.Type.PINYON_PINE, new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  JUNIPER_BOAT = add("juniper_boat", new RankineBoatItem(RankineBoatEntity.Type.JUNIPER, new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  BALSAM_FIR_BOAT = add("balsam_fir_boat", new RankineBoatItem(RankineBoatEntity.Type.BALSAM_FIR, new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  MAGNOLIA_BOAT = add("magnolia_boat", new RankineBoatItem(RankineBoatEntity.Type.MAGNOLIA, new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  HEMLOCK_BOAT = add("eastern_hemlock_boat", new RankineBoatItem(RankineBoatEntity.Type.HEMLOCK, new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  BAMBOO_BOAT = add("bamboo_boat", new RankineBoatItem(RankineBoatEntity.Type.BAMBOO, new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));

    public static final Item MANTLE_GOLEM = add("mantle_golem_egg", new SpawnEggItem(ModEntityTypes.MANTLE_GOLEM,0xB2B16A, 0x7A592E,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item DIAMOND_MANTLE_GOLEM = add("diamond_mantle_golem_egg", new SpawnEggItem(ModEntityTypes.DIAMOND_MANTLE_GOLEM, 0x435184,0xA1FBE8,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item PERIDOT_MANTLE_GOLEM = add("peridot_mantle_golem_egg", new SpawnEggItem(ModEntityTypes.PERIDOT_MANTLE_GOLEM,0xFF423C, 0x6BBE1F,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item DESMOXYTE = add("desmoxyte_spawn_egg", new SpawnEggItem(ModEntityTypes.DESMOXYTE,0x2D4F64, 0xAC6D10,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item DEMONYTE = add("demonyte_spawn_egg", new SpawnEggItem(ModEntityTypes.DEMONYTE,0x161617, 0x512259,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item DRAGONYTE = add("dragonyte_spawn_egg", new SpawnEggItem(ModEntityTypes.DRAGONYTE,0xC28215, 0x8F1826,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item STEAMER = add("steamer_spawn_egg", new SpawnEggItem(ModEntityTypes.STEAMER,0xE7E7E7, 0x6B0000,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item BEAVER = add("beaver_spawn_egg", new SpawnEggItem(ModEntityTypes.BEAVER,0x765F4C, 0x463F39,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item SOLAR_FLARE = add("solar_flare_spawn_egg", new SpawnEggItem(ModEntityTypes.SOLAR_FLARE,0xFFD724, 0xFF9F14,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item SHROUDED_KING = add("shrouded_king_spawn_egg", new SpawnEggItem(ModEntityTypes.SHROUDED_KING,0x000000, 0xA82C00,new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item JAR_BLUE_FOXFIRE = add("jar_blue_foxfire", new BlueFoxfireJarItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item JAR_GREEN_FOXFIRE = add("jar_green_foxfire", new GreenFoxfireJarItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item FOUR_LEAFED_CLOVER = add("four_leafed_clover", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item CEDAR_BARK = add("cedar_bark", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item EASTERN_HEMLOCK_BARK = add("eastern_hemlock_bark", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.HEMLOCK_BARK)));
    public static final Item BIRCH_BARK = add("birch_bark", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item YELLOW_BIRCH_BARK = add("yellow_birch_bark", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item DRIED_BAMBOO = add("dried_bamboo", new FuelItem(new Item.Properties().group(ProjectRankine.setup.rankineTools), 100));
    public static final Item BEAVER_PELT = add("beaver_pelt", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item PUMICE_SOAP = add("pumice_soap", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item PINEAPPLE_SLEEVES = add("pineapple_sleeves", new Item(new Item.Properties().food(ModFoods.PINEAPPLE_SLEEVES)));
    public static final Item COCONUT = add("coconut", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.COCONUT)));
    public static final Item CHEESE = add("cheese", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.CHEESE)));
    public static final Item RED_GRAPES = add("red_grapes", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.RED_GRAPES)));
    public static final Item PINA_COLADA = add("pina_colada", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.PINA_COLADA)));

    public static final Item CEDAR_REMEDY = add("cedar_remedy", new RemedyItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item WINTERGREEN_REMEDY = add("wintergreen_remedy", new RemedyItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item HERBAL_REMEDY = add("herbal_remedy", new RemedyItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item BERRY_REMEDY = add("berry_remedy", new RemedyItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item BISMUTH_REMEDY = add("bismuth_remedy", new RemedyItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item COBALT_REMEDY = add("cobalt_remedy", new RemedyItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item SILVER_REMEDY = add("silver_remedy", new RemedyItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item OVERWORLD_CORE = add("overworld_core", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));


    public static final Item GRAPHITE_ELECTRODE = add("graphite_electrode", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item COPPER_WIRE = add("copper_wire", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    //public static final Item SURFACE_CONDENSER = add("surface_condenser", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    //public static final Item TURBINE_BLADE = add("turbine_blade", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item BATTERY = add("battery0", new ItemBattery(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item CAST_IRON_ROD = add("cast_iron_rod", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item STEEL_ROD = add("steel_rod", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item WOODEN_HOURGLASS = add("wooden_hourglass", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineMagic)));
    public static final Item GOLDEN_HOURGLASS = add("golden_hourglass", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineMagic)));
    public static final Item BONE_HOURGLASS = add("bone_hourglass", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineMagic)));
    public static final Item PHANTOM_HOURGLASS = add("phantom_hourglass", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineMagic)));
    public static final Item BLAZE_HOURGLASS = add("blaze_hourglass", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineMagic)));
    public static final Item ENDER_HOURGLASS = add("ender_hourglass", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineMagic)));
    public static final Item ELEMENT_TRANSMUTER = add("element_transmuter", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMagic)));


    @ObjectHolder("rankine:sphagnum_moss")
    public static Item SPHAGNUM_MOSS;

    @ObjectHolder("rankine:elderberries")
    public static Item ELDERBERRIES;

    @ObjectHolder("rankine:snowberries")
    public static Item SNOWBERRIES;

    @ObjectHolder("rankine:blueberries")
    public static Item BLUEBERRIES;

    @ObjectHolder("rankine:raspberries")
    public static Item RASPBERRIES;

    @ObjectHolder("rankine:blackberries")
    public static Item BLACKBERRIES;

    @ObjectHolder("rankine:cranberries")
    public static Item CRANBERRIES;

    @ObjectHolder("rankine:strawberries")
    public static Item STRAWBERRIES;

    @ObjectHolder("rankine:pineapple")
    public static Item PINEAPPLE;

    @ObjectHolder("rankine:banana_yucca")
    public static Item BANANA_YUCCA;

    public static Item getBlockAsItem(Block block)
    {
        ItemStack stack = new ItemStack(block);
        return stack.getItem();
    }
}

