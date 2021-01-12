package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.entities.RankineBoatEntity;
import com.cannolicatfish.rankine.fluids.ModFluids;
import com.cannolicatfish.rankine.items.*;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerContainer;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerItem;
import com.cannolicatfish.rankine.items.pendants.*;
import com.cannolicatfish.rankine.items.tools.*;
import com.cannolicatfish.rankine.util.alloys.*;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Arrays;

public class ModItems {

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ProjectRankine.MODID);

    private static <T extends Item> T add(String name, T item) {
        REGISTRY.register(name, () -> item);
        return item;
    }

//METALLURGY TAB
//=============================================================================
//CRUSHED ORES
    public static final Item MALACHITE = add("malachite", new SimpleTooltipItem(Arrays.asList("Composition: Cu2CO3(OH)2", "Used as a source for copper or as pigment"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item AZURITE = add("azurite", new SimpleTooltipItem(Arrays.asList("Composition: Cu3(CO3)2(OH)2", "Used as a source for copper or as pigment"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CHALCOPYRITE = add("chalcopyrite", new SimpleTooltipItem(Arrays.asList("Composition: CuFeS2", "Used as a source for copper and iron"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CASSITERITE = add("cassiterite", new SimpleTooltipItem(Arrays.asList("Composition: SnO2", "Used as a source for tin"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ALUMINA = add("alumina", new SimpleTooltipItem(Arrays.asList("Composition: Al2O3", "Used as a source for aluminum"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SPHALERITE = add("sphalerite", new SimpleTooltipItem(Arrays.asList("Composition: ZnS", "Used as a source for zinc"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNETITE = add("magnetite", new SimpleTooltipItem(Arrays.asList("Composition: Fe3O4", "Used as a source for iron"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PENTLANDITE = add("pentlandite", new SimpleTooltipItem(Arrays.asList("Composition: Ni9S8", "Used as a source for nickel"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNESITE = add("magnesite", new SimpleTooltipItem(Arrays.asList("Composition: MgCO3", "Used as a source for magnesium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item GALENA = add("galena", new SimpleTooltipItem(Arrays.asList("Composition: PbS", "Used as a source for lead"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item VANADINITE = add("vanadinite", new SimpleTooltipItem(Arrays.asList("Composition: Pb5(VO4)3Cl", "Used as a source for vanadium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item BISMUTHINITE = add("bismuthinite", new SimpleTooltipItem(Arrays.asList("Composition: Bi2S3", "Used as a source for bismuth"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ACANTHITE = add("acanthite", new SimpleTooltipItem(Arrays.asList("Composition: Ag2S", "Used as a source for silver"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PYROLUSITE = add("pyrolusite", new SimpleTooltipItem(Arrays.asList("Composition: MnO2", "Used as a source for manganese"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CHROMITE = add("chromite", new SimpleTooltipItem(Arrays.asList("Composition: FeCr2O4", "Used as a source for chromium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MOLYBDENITE = add("molybdenite", new SimpleTooltipItem(Arrays.asList("Composition: MoS2", "Used as a source for molybdenum"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item WOLFRAMITE = add("wolframite", new SimpleTooltipItem(Arrays.asList("Composition: FeWO4", "Used as a source for tungsten"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TITANIA = add("titania", new SimpleTooltipItem(Arrays.asList("Composition: TiO2", "Used as a source for titanium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item COLUMBITE = add("columbite", new SimpleTooltipItem(Arrays.asList("Composition: FeNb2O6", "Used as a source for niobium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TANTALITE = add("tantalite", new SimpleTooltipItem(Arrays.asList("Composition: FeTa2O6", "Used as a source for tantalum"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item COBALTITE = add("cobaltite", new SimpleTooltipItem(Arrays.asList("Composition: CoAsS", "Used as a source for cobalt"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PETALITE = add("petalite", new SimpleTooltipItem(Arrays.asList("Composition: LiAlSi4O10", "Used as a source for lithium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CELESTINE = add("celestine", new SimpleTooltipItem(Arrays.asList("Composition: SrSO4", "Used as a source for strontium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PLATINUM_ARSENIDE = add("platinum_arsenide", new SimpleTooltipItem(Arrays.asList("Composition: PtAs2", "Used as a source for platinum"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CERIUM_MONAZITE = add("cerium_monazite", new SimpleTooltipItem(Arrays.asList("Composition: CePO4", "Used as a source for cerium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item LANTHANUM_MONAZITE = add("lanthanum_monazite", new SimpleTooltipItem(Arrays.asList("Composition: LaPO4", "Used as a source for lanthanum"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item NEODYMIUM_MONAZITE = add("neodymium_monazite", new SimpleTooltipItem(Arrays.asList("Composition: NdPO4", "Used as a source for neodymium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SAMARIUM_MONAZITE = add("samarium_monazite", new SimpleTooltipItem(Arrays.asList("Composition: SmPO4", "Used as a source for samarium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ZIRCON = add("zircon", new SimpleTooltipItem(Arrays.asList("Composition: ZrSiO4", "Used as a source for zirconium and for ultra-high refractory bricks"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item THORITE = add("thorite", new SimpleTooltipItem(Arrays.asList("Composition: ThSiO4", "Used as a source for thorium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item URANINITE = add("uraninite", new SimpleTooltipItem(Arrays.asList("Composition: UO2", "Used as a source for uranium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item GREENOCKITE = add("greenockite", new SimpleTooltipItem(Arrays.asList("Composition: CdS", "Used as a source for cadmium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item STIBNITE = add("stibnite", new SimpleTooltipItem(Arrays.asList("Composition: Sb2S3", "Used as a source for antimony"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item XENOTIME = add("xenotime", new SimpleTooltipItem(Arrays.asList("Composition: YPO4", "Used as a source for yttrium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item BARITE = add("barite", new SimpleTooltipItem(Arrays.asList("Composition: BaSO4", "Used as a source for barium"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item LIGNITE = add("lignite", new FuelItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals),1200));
    public static final Item SUBBITUMINOUS_COAL = add("subbituminous_coal", new FuelItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals),1600));
    public static final Item BITUMINOUS_COAL = add("bituminous_coal", new FuelItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals),2400));
    public static final Item ANTHRACITE_COAL = add("anthracite_coal", new FuelItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals), 3200));
    public static final Item COKE = add("coke", new FuelItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals), 3200));
    public static final Item PYRITE = add("pyrite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CRYOLITE = add("cryolite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNESIA = add("magnesia", new SimpleTooltipItem(Arrays.asList("Composition: MgO", "Used for high refractory bricks"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CALCITE = add("calcite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item DOLOMITE = add("dolomite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item FELDSPAR = add("feldspar", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PLAGIOCLASE_FELDSPAR = add("plagioclase_feldspar", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item OLIVINE = add("olivine", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PYROXENE = add("pyroxene", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MICA = add("mica", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item AMPHIBOLE = add("amphibole", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item BORAX = add("borax", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SALT = add("salt", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PINK_SALT = add("pink_salt", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SALTPETER = add("saltpeter", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item FLUORITE = add("fluorite", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item GRAPHITE = add("graphite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CALCIUM_SILICATE = add("calcium_silicate", new SimpleTooltipItem(Arrays.asList("Composition: Ca2SiO4"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SILICON_CARBIDE = add("silicon_carbide", new SimpleTooltipItem(Arrays.asList("Composition: SiC", "Used as a source for silicon and for LEDs"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CINNABAR = add("cinnabar", new SimpleTooltipItem(Arrays.asList("Composition: HgS", "Used as a source for mercury and redstone"), new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item RUBY = add("ruby", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SAPPHIRE = add("sapphire", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item OPAL = add("opal", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item AQUAMARINE = add("aquamarine", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TOURMALINE = add("tourmaline", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item TIGER_IRON = add("tiger_iron", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item GARNET = add("garnet", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PERIDOT = add("peridot", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item BLOOM_IRON = add("bloom_iron", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SLAG = add("slag", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item BONE_CHAR = add("bone_char", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PERLITE = add("perlite", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item QUICKLIME = add("quicklime", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item POZZOLAN = add("pozzolan", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item DRY_MORTAR = add("dry_mortar", new DryMortarItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MORTAR = add("mortar", new MortarItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item POZZOLANIC_MORTAR = add("pozzolanic_mortar", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ELEMENT = add("element", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineMetals)));
    public static final Item CLAY_BRICK = add("clay_brick", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item REFRACTORY_BRICK = add("refractory_brick", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNESIUM_REFRACTORY_BRICK = add("magnesium_refractory_brick", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ZIRCON_REFRACTORY_BRICK = add("zircon_refractory_brick", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));

    //ALLOY INGOTS
    public static final Item CAST_IRON_ALLOY = add("cast_iron_alloy", new AlloyItem("96Fe-4C",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem BRONZE_ALLOY = add("bronze_alloy", new AlloyItem(new BronzeAlloyUtils().getDefComposition(),new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem ALUMINUM_BRONZE_ALLOY = add("aluminum_bronze_alloy", new AlloyItem("90Cu-10Al",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PEWTER_ALLOY = add("pewter_alloy", new AlloyItem("90Sn-10Sb",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem BRASS_ALLOY = add("brass_alloy", new AlloyItem("60Cu-40Zn",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item STEEL_ALLOY = add("steel_alloy", new AlloyItem("99Fe-1C",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem STAINLESS_STEEL_ALLOY = add("stainless_steel_alloy", new AlloyItem(new StainlessSteelAlloyUtils().getDefComposition(),new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item SOLDER_ALLOY = add("solder_alloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ALNICO_ALLOY = add("alnico_alloy", new AlloyItem("50Fe-25Co-15Ni-10Al",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem NITINOL_ALLOY = add("nitinol_alloy", new AlloyItem("50Ni-50Ti",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem AMALGAM_ALLOY = add("amalgam_alloy", new AlloyItem(new AmalgamAlloyUtils().getDefComposition(),new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem CUPRONICKEL_ALLOY = add("cupronickel_alloy", new AlloyItem("85Cu-15Ni",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNESIUM_ALLOY = add("magnesium_alloy", new AlloyItem("85Mg-12Al-3Zn",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));;
    public static final Item STERLING_SILVER_ALLOY = add("sterling_silver_alloy", new AlloyItem("93Ag-7Cu",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item NICKEL_SILVER_ALLOY = add("nickel_silver_alloy", new AlloyItem("60Cu-20Ni-20Zn",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));;
    public static final Item INVAR_ALLOY = add("invar_alloy", new AlloyItem("64Fe-36Ni",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MISCHMETAL_ALLOY = add("mischmetal_alloy", new AlloyItem("50Ce-24La-20Nd-6Fe",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item FERROCERIUM_ALLOY = add("ferrocerium_alloy", new AlloyItem("42Ce-24La-22Fe-8Mg-4Nd",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item NICKEL_SUPERALLOY = add("nickel_superalloy", new AlloyItem("70Ni-20Cr-10Co",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem TUNGSTEN_HEAVY_ALLOY = add("tungsten_heavy_alloy", new AlloyItem("90W-7Ni-3Fe",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem COBALT_SUPERALLOY = add("cobalt_superalloy", new AlloyItem("60Co-20Cr-10Ni-10Ta",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item ROSE_METAL_ALLOY = add("rose_metal_alloy", new AlloyItem("50Bi-25Pb-25Sn",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item GALINSTAN_ALLOY = add("galinstan_alloy", new AlloyItem("70Ga-20In-10Sn",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNALIUM_ALLOY = add("magnalium_alloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item DURALUMIN_ALLOY = add("duralumin_alloy", new AlloyItem("95Al-3Cu-1Mg-1Mn",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item OSMIRIDIUM_ALLOY = add("osmiridium_alloy", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem ROSE_GOLD_ALLOY = add("rose_gold_alloy", new AlloyItem("75Au-22Cu-3Ag", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem WHITE_GOLD_ALLOY = add("white_gold_alloy", new AlloyItem("90Au-10Zn",new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem GREEN_GOLD_ALLOY = add("green_gold_alloy", new AlloyItem("50Au-50Ag", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem BLUE_GOLD_ALLOY = add("blue_gold_alloy", new AlloyItem("75Au-25Fe", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem PURPLE_GOLD_ALLOY = add("purple_gold_alloy", new AlloyItem("80Au-20Al", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem BLACK_GOLD_ALLOY = add("black_gold_alloy", new AlloyItem("75Au-25Co", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final AlloyItem METEORIC_IRON = add("meteoric_iron", new AlloyItem("90Fe-10Ni", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineMetals)));
    public static final Item PIG_IRON_INGOT = add("pig_iron_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item WROUGHT_IRON_INGOT = add("wrought_iron_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PIG_IRON_NUGGET = add("pig_iron_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item WROUGHT_IRON_NUGGET = add("wrought_iron_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));

    //ELEMENT INGOTS
    public static final Item HYDROGEN_INGOT = add("hydrogen_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item HELIUM_INGOT = add("helium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LITHIUM_INGOT = add("lithium_ingot", new ReactiveItem(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BERYLLIUM_INGOT = add("beryllium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BORON_INGOT = add("boron_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CARBON_INGOT = add("carbon_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NITROGEN_INGOT = add("nitrogen_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item OXYGEN_INGOT = add("oxygen_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item FLUORINE_INGOT = add("fluorine_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NEON_INGOT = add("neon_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SODIUM_INGOT = add("sodium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNESIUM_INGOT = add("magnesium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ALUMINUM_INGOT = add("aluminum_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SILICON = add("silicon", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PHOSPHORUS = add("phosphorus", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SULFUR = add("sulfur", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CHLORINE_INGOT = add("chlorine_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ARGON_INGOT = add("argon_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item POTASSIUM_INGOT = add("potassium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
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
    public static final Item RUBIDIUM_INGOT = add("rubidium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
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
    public static final Item CESIUM_INGOT = add("cesium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BARIUM_INGOT = add("barium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LANTHANUM_INGOT = add("lanthanum_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CERIUM_INGOT = add("cerium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PRASEODYMIUM_INGOT = add("praseodymium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NEODYMIUM_INGOT = add("neodymium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PROMETHIUM_INGOT = add("promethium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SAMARIUM_INGOT = add("samarium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item EUROPIUM_INGOT = add("europium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item GADOLINIUM_INGOT = add("gadolinium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TERBIUM_INGOT = add("terbium_ingot", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
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

    //ELEMENT NUGGETS
    public static final Item HYDROGEN_NUGGET = add("hydrogen_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item HELIUM_NUGGET = add("helium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LITHIUM_NUGGET = add("lithium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BERYLLIUM_NUGGET = add("beryllium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BORON_NUGGET = add("boron_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CARBON_NUGGET = add("carbon_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NITROGEN_NUGGET = add("nitrogen_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item OXYGEN_NUGGET = add("oxygen_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item FLUORINE_NUGGET = add("fluorine_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NEON_NUGGET = add("neon_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SODIUM_NUGGET = add("sodium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MAGNESIUM_NUGGET = add("magnesium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ALUMINUM_NUGGET = add("aluminum_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SILICON_NUGGET = add("silicon_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PHOSPHORUS_NUGGET = add("phosphorus_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SULFUR_NUGGET = add("sulfur_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CHLORINE_NUGGET = add("chlorine_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ARGON_NUGGET = add("argon_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item POTASSIUM_NUGGET = add("potassium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CALCIUM_NUGGET = add("calcium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SCANDIUM_NUGGET = add("scandium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TITANIUM_NUGGET = add("titanium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item VANADIUM_NUGGET = add("vanadium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CHROMIUM_NUGGET = add("chromium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MANGANESE_NUGGET = add("manganese_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item COBALT_NUGGET = add("cobalt_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NICKEL_NUGGET = add("nickel_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item COPPER_NUGGET = add("copper_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ZINC_NUGGET = add("zinc_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item GALLIUM_NUGGET = add("gallium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item GERMANIUM_NUGGET = add("germanium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ARSENIC_NUGGET = add("arsenic_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SELENIUM_NUGGET = add("selenium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BROMINE_NUGGET = add("bromine_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item KRYPTON_NUGGET = add("krypton_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RUBIDIUM_NUGGET = add("rubidium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item STRONTIUM_NUGGET = add("strontium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item YTTRIUM_NUGGET = add("yttrium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ZIRCONIUM_NUGGET = add("zirconium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NIOBIUM_NUGGET = add("niobium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MOLYBDENUM_NUGGET = add("molybdenum_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TECHNETIUM_NUGGET = add("technetium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RUTHENIUM_NUGGET = add("ruthenium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RHODIUM_NUGGET = add("rhodium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PALLADIUM_NUGGET = add("palladium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SILVER_NUGGET = add("silver_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CADMIUM_NUGGET = add("cadmium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item INDIUM_NUGGET = add("indium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TIN_NUGGET = add("tin_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ANTIMONY_NUGGET = add("antimony_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TELLURIUM_NUGGET = add("tellurium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item IODINE_NUGGET = add("iodine_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item XENON_NUGGET = add("xenon_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CESIUM_NUGGET = add("cesium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BARIUM_NUGGET = add("barium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LANTHANUM_NUGGET = add("lanthanum_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CERIUM_NUGGET = add("cerium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PRASEODYMIUM_NUGGET = add("praseodymium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NEODYMIUM_NUGGET = add("neodymium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PROMETHIUM_NUGGET = add("promethium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SAMARIUM_NUGGET = add("samarium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item EUROPIUM_NUGGET = add("europium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item GADOLINIUM_NUGGET = add("gadolinium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TERBIUM_NUGGET = add("terbium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item DYSPROSIUM_NUGGET = add("dysprosium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item HOLMIUM_NUGGET = add("holmium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ERIBIUM_NUGGET = add("eribium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item THULIUM_NUGGET = add("thulium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item YTTERBIUM_NUGGET = add("ytterbium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LUTETIUM_NUGGET = add("lutetium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item HAFNIUM_NUGGET = add("hafnium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TANTALUM_NUGGET = add("tantalum_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TUNGSTEN_NUGGET = add("tungsten_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RHENIUM_NUGGET = add("rhenium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item OSMIUM_NUGGET = add("osmium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item IRIDIUM_NUGGET = add("iridium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PLATINUM_NUGGET = add("platinum_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MERCURY_NUGGET = add("mercury_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item THALLIUM_NUGGET = add("thallium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LEAD_NUGGET = add("lead_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BISMUTH_NUGGET = add("bismuth_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item POLONIUM_NUGGET = add("polonium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ASTATINE_NUGGET = add("astatine_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RADON_NUGGET = add("radon_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item FRANCIUM_NUGGET = add("francium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RADIUM_NUGGET = add("radium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ACTINIUM_NUGGET = add("actinium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item THORIUM_NUGGET = add("thorium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PROTACTINIUM_NUGGET = add("protactinium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item URANIUM_NUGGET = add("uranium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NEPTUNIUM_NUGGET = add("neptunium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item PLUTONIUM_NUGGET = add("plutonium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item AMERICIUM_NUGGET = add("americium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CURIUM_NUGGET = add("curium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BERKELIUM_NUGGET = add("berkelium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item CALIFORNIUM_NUGGET = add("californium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item EINSTEINIUM_NUGGET = add("einsteinium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item FERMIUM_NUGGET = add("fermium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MENDELEVIUM_NUGGET = add("mendelevium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NOBELIUM_NUGGET = add("nobelium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LAWRENCIUM_NUGGET = add("lawrencium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item RUTHERFORDIUM_NUGGET = add("rutherfordium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item DUBNIUM_NUGGET = add("dubnium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item SEABORGIUM_NUGGET = add("seaborgium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item BOHRIUM_NUGGET = add("bohrium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item HASSIUM_NUGGET = add("hassium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MEITERIUM_NUGGET = add("meiterium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item DARMSTADTIUM_NUGGET = add("darmstadtium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item ROENTGENIUM_NUGGET = add("roentgenium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item COPERNICIUM_NUGGET = add("copernicium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item NIHONIUM_NUGGET = add("nihonium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item FLEROVIUM_NUGGET = add("flerovium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item MOSCOVIUM_NUGGET = add("moscovium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item LIVERMORIUM_NUGGET = add("livermorium_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item TENNESSINE_NUGGET = add("tennessine_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));
    public static final Item OGANESSON_NUGGET = add("oganesson_nugget", new Item(new Item.Properties().group(ProjectRankine.setup.rankineMetals)));



//RANKINE MISC CREATIVE TAB --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //NORMAL TOOLS
    public static final Item  STONE_HAMMER = add("stone_hammer", new ItemHammer(2, -3.2F, RankineToolMaterials.FLINT, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINT_PICKAXE = add("flint_pickaxe", new PickaxeItem(RankineToolMaterials.FLINT, 1, -2.8F, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINT_AXE = add("flint_axe", new AxeItem(RankineToolMaterials.FLINT, 4.0F, -3.2F, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINT_SHOVEL = add("flint_shovel", new ShovelItem(RankineToolMaterials.FLINT, 1.5F, -3.0F, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINT_SPEAR = add("flint_spear", new ItemSpear(RankineToolMaterials.FLINT, 2, -2.9F, ModEntityTypes.FLINT_SPEAR,new ResourceLocation("rankine:textures/entity/flint_spear.png"),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINT_KNIFE = add("flint_knife", new ItemKnife(RankineToolMaterials.FLINT, 1, -1.5F, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  FLINT_HOE = add("flint_hoe", new HoeItem(RankineToolMaterials.FLINT, 0, -3.0F, new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item  PEWTER_SWORD = add("pewter_sword", new AlloySword(RankineToolMaterials.BRONZE, 3, -2.4F, new PewterAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  PEWTER_SHOVEL = add("pewter_shovel", new AlloyShovel(RankineToolMaterials.BRONZE, 1.5F, -3.0F, new PewterAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  PEWTER_PICKAXE = add("pewter_pickaxe", new AlloyPickaxe(RankineToolMaterials.BRONZE, 1, -2.8F, new PewterAlloyUtils(), (new Item.Properties()).group(ProjectRankine.setup.rankineTools)));
    public static final Item  PEWTER_AXE = add("pewter_axe", new AlloyAxe(RankineToolMaterials.BRONZE, 4.0F, -3.2F, new PewterAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  PEWTER_HAMMER = add("pewter_hammer", new AlloyHammer(RankineToolMaterials.BRONZE,2, -3.2F, new PewterAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  PEWTER_HOE = add("pewter_hoe", new AlloyHoe(RankineToolMaterials.BRONZE, 0,-2.0F, new PewterAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  PEWTER_SPEAR = add("pewter_spear", new AlloySpear(RankineToolMaterials.BRONZE, 2, -2.9F, new PewterAlloyUtils(),ModEntityTypes.PEWTER_SPEAR,new ResourceLocation("rankine:textures/entity/pewter_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item  BRONZE_SWORD = add("bronze_sword", new AlloySword(RankineToolMaterials.BRONZE, 3, -2.4F, new BronzeAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_SHOVEL = add("bronze_shovel", new AlloyShovel(RankineToolMaterials.BRONZE, 1.5F, -3.0F, new BronzeAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_PICKAXE = add("bronze_pickaxe", new AlloyPickaxe(RankineToolMaterials.BRONZE, 1, -2.8F, new BronzeAlloyUtils(), (new Item.Properties()).group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_AXE = add("bronze_axe", new AlloyAxe(RankineToolMaterials.BRONZE, 4.0F, -3.2F, new BronzeAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_HAMMER = add("bronze_hammer", new AlloyHammer(RankineToolMaterials.BRONZE,2, -3.2F, new BronzeAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_HOE = add("bronze_hoe", new AlloyHoe(RankineToolMaterials.BRONZE, -1,-2.0F, new BronzeAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BRONZE_SPEAR = add("bronze_spear", new AlloySpear(RankineToolMaterials.BRONZE, 2, -2.9F, new BronzeAlloyUtils(),ModEntityTypes.BRONZE_SPEAR,new ResourceLocation("rankine:textures/entity/bronze_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  IRON_SPEAR = add("iron_spear", new ItemSpear(ItemTier.IRON, 2, -2.9F, ModEntityTypes.IRON_SPEAR,new ResourceLocation("rankine:textures/entity/iron_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  IRON_HAMMER = add("iron_hammer", new ItemHammer(2, -3.2F, ItemTier.IRON, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  METAL_SCRAPER = add("metal_scraper", new ItemScraper(new Item.Properties().maxStackSize(1).maxDamage(256).group(ProjectRankine.setup.rankineTools)));

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

    public static final Item  STAINLESS_STEEL_SWORD = add("stainless_steel_sword", new AlloySword(RankineToolMaterials.STAINLESS, 3, -2.4F, new StainlessSteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STAINLESS_STEEL_SHOVEL = add("stainless_steel_shovel", new AlloyShovel(RankineToolMaterials.STAINLESS, 1.5F, -3.0F, new StainlessSteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STAINLESS_STEEL_PICKAXE = add("stainless_steel_pickaxe", new AlloyPickaxe(RankineToolMaterials.STAINLESS, 1, -2.8F, new StainlessSteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STAINLESS_STEEL_AXE = add("stainless_steel_axe", new AlloyAxe(RankineToolMaterials.STAINLESS, 4.0F, -3.2F, new StainlessSteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STAINLESS_STEEL_HAMMER = add("stainless_steel_hammer", new AlloyHammer(RankineToolMaterials.STAINLESS,2, -3.2F,  new StainlessSteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STAINLESS_STEEL_HOE = add("stainless_steel_hoe", new AlloyHoe(RankineToolMaterials.STAINLESS, -3,0.0F, new StainlessSteelAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  STAINLESS_STEEL_SPEAR = add("stainless_steel_spear", new AlloySpear(RankineToolMaterials.STAINLESS, 2, -2.9F, new StainlessSteelAlloyUtils(),ModEntityTypes.STAINLESS_STEEL_SPEAR,new ResourceLocation("rankine:textures/entity/stainless_steel_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item  NICKEL_SUPERALLOY_SWORD = add("nickel_superalloy_sword", new AlloySword(RankineToolMaterials.NICKEL_SA, 3, -2.4F, new NickelSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  NICKEL_SUPERALLOY_SHOVEL = add("nickel_superalloy_shovel", new AlloyShovel(RankineToolMaterials.NICKEL_SA, 1.5F, -3.0F, new NickelSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  NICKEL_SUPERALLOY_PICKAXE = add("nickel_superalloy_pickaxe", new AlloyPickaxe(RankineToolMaterials.NICKEL_SA, 1, -2.8F, new NickelSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  NICKEL_SUPERALLOY_AXE = add("nickel_superalloy_axe", new AlloyAxe(RankineToolMaterials.NICKEL_SA, 4.0F, -3.2F, new NickelSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  NICKEL_SUPERALLOY_HAMMER = add("nickel_superalloy_hammer", new AlloyHammer(RankineToolMaterials.NICKEL_SA,2, -3.2F,  new NickelSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  NICKEL_SUPERALLOY_HOE = add("nickel_superalloy_hoe", new AlloyHoe(RankineToolMaterials.NICKEL_SA, -3,0.0F, new NickelSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  NICKEL_SUPERALLOY_SPEAR = add("nickel_superalloy_spear", new AlloySpear(RankineToolMaterials.NICKEL_SA, 2, -2.9F, new NickelSuperalloyUtils(),ModEntityTypes.NICKEL_SUPERALLOY_SPEAR,new ResourceLocation("rankine:textures/entity/nickel_superalloy_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item  COBALT_SUPERALLOY_SWORD = add("cobalt_superalloy_sword", new AlloySword(RankineToolMaterials.COBALT_SA, 3, -2.4F, new CobaltSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  COBALT_SUPERALLOY_SHOVEL = add("cobalt_superalloy_shovel", new AlloyShovel(RankineToolMaterials.COBALT_SA, 1.5F, -3.0F, new CobaltSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  COBALT_SUPERALLOY_PICKAXE = add("cobalt_superalloy_pickaxe", new AlloyPickaxe(RankineToolMaterials.COBALT_SA, 1, -2.8F, new CobaltSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  COBALT_SUPERALLOY_AXE = add("cobalt_superalloy_axe", new AlloyAxe(RankineToolMaterials.COBALT_SA, 4.0F, -3.2F, new CobaltSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  COBALT_SUPERALLOY_HAMMER = add("cobalt_superalloy_hammer", new AlloyHammer(RankineToolMaterials.COBALT_SA,2, -3.2F,  new CobaltSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  COBALT_SUPERALLOY_HOE = add("cobalt_superalloy_hoe", new AlloyHoe(RankineToolMaterials.COBALT_SA, -3,0.0F, new CobaltSuperalloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  COBALT_SUPERALLOY_SPEAR = add("cobalt_superalloy_spear", new AlloySpear(RankineToolMaterials.COBALT_SA, 2, -2.9F, new CobaltSuperalloyUtils(),ModEntityTypes.COBALT_SUPERALLOY_SPEAR,new ResourceLocation("rankine:textures/entity/cobalt_superalloy_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item  TUNGSTEN_HEAVY_ALLOY_SWORD = add("tungsten_heavy_alloy_sword", new AlloySword(RankineToolMaterials.TUNGSTEN, 3, -2.4F, new TungstenHeavyAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  TUNGSTEN_HEAVY_ALLOY_SHOVEL = add("tungsten_heavy_alloy_shovel", new AlloyShovel(RankineToolMaterials.TUNGSTEN, 1.5F, -3.0F, new TungstenHeavyAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  TUNGSTEN_HEAVY_ALLOY_PICKAXE = add("tungsten_heavy_alloy_pickaxe", new AlloyPickaxe(RankineToolMaterials.TUNGSTEN, 1, -2.8F, new TungstenHeavyAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  TUNGSTEN_HEAVY_ALLOY_AXE = add("tungsten_heavy_alloy_axe", new AlloyAxe(RankineToolMaterials.TUNGSTEN, 4.0F, -3.2F, new TungstenHeavyAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  TUNGSTEN_HEAVY_ALLOY_HAMMER = add("tungsten_heavy_alloy_hammer", new AlloyHammer(RankineToolMaterials.TUNGSTEN,2, -3.2F,  new TungstenHeavyAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  TUNGSTEN_HEAVY_ALLOY_HOE = add("tungsten_heavy_alloy_hoe", new AlloyHoe(RankineToolMaterials.TUNGSTEN, -3,0.0F, new TungstenHeavyAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  TUNGSTEN_HEAVY_ALLOY_SPEAR = add("tungsten_heavy_alloy_spear", new AlloySpear(RankineToolMaterials.TUNGSTEN, 2, -2.9F, new TungstenHeavyAlloyUtils(),ModEntityTypes.TUNGSTEN_HEAVY_ALLOY_SPEAR,new ResourceLocation("rankine:textures/entity/tungsten_heavy_alloy_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

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

    public static final Item  BLACK_GOLD_SWORD = add("black_gold_sword", new AlloySword(RankineToolMaterials.BLACK_GOLD,3, -2.4F, new BlackGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLACK_GOLD_SHOVEL = add("black_gold_shovel", new AlloyShovel(RankineToolMaterials.BLACK_GOLD,1.5F, -3.0F, new BlackGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLACK_GOLD_PICKAXE = add("black_gold_pickaxe", new AlloyPickaxe(RankineToolMaterials.BLACK_GOLD, 1, -2.8F, new BlackGoldAlloyUtils(), (new Item.Properties()).group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLACK_GOLD_AXE = add("black_gold_axe", new AlloyAxe(RankineToolMaterials.BLACK_GOLD,4.0F, -3.2F, new BlackGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLACK_GOLD_HAMMER = add("black_gold_hammer", new AlloyHammer(RankineToolMaterials.BLACK_GOLD,2, -3.2F, new BlackGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLACK_GOLD_HOE = add("black_gold_hoe", new AlloyHoe(RankineToolMaterials.BLACK_GOLD,0,-3.0F,new BlackGoldAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  BLACK_GOLD_SPEAR = add("black_gold_spear", new AlloySpear(RankineToolMaterials.BLACK_GOLD,2, -2.9F, new BlackGoldAlloyUtils(),ModEntityTypes.BLACK_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/black_gold_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

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

    public static final Item  ALLOY_SWORD = add("alloy_sword", new AlloySword(RankineToolMaterials.ALLOY, 3, -2.4F, new GenericAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  ALLOY_SHOVEL = add("alloy_shovel", new AlloyShovel(RankineToolMaterials.ALLOY, 1.5F, -3.0F, new GenericAlloyUtils(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  ALLOY_PICKAXE = add("alloy_pickaxe", new AlloyPickaxe(RankineToolMaterials.ALLOY, 1, -2.8F, new GenericAlloyUtils(), (new Item.Properties()).group(ProjectRankine.setup.rankineTools)));
    public static final Item  ALLOY_AXE = add("alloy_axe", new AlloyAxe(RankineToolMaterials.ALLOY, 4.0F, -3.2F, new GenericAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  ALLOY_HAMMER = add("alloy_hammer", new AlloyHammer(RankineToolMaterials.ALLOY,2, -3.2F, new GenericAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  ALLOY_HOE = add("alloy_hoe", new AlloyHoe(RankineToolMaterials.ALLOY, 0,-2.0F, new GenericAlloyUtils(),new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item  ALLOY_SPEAR = add("alloy_spear", new AlloySpear(RankineToolMaterials.ALLOY, 2, -2.9F, new GenericAlloyUtils(),ModEntityTypes.PEWTER_SPEAR,new ResourceLocation("rankine:textures/entity/iron_spear.png"), new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item STEEL_GOLD_PAN = add("steel_gold_pan", new ItemGoldPan(new Item.Properties().maxStackSize(1).maxDamage(63).group(ProjectRankine.setup.rankineTools)));
    public static final Item STEEL_CROWBAR = add("steel_crowbar", new ItemCrowbar(1.5f, -2.2F, RankineToolMaterials.STEEL,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item TITANIUM_CROWBAR = add("titanium_crowbar", new ItemCrowbar(1.5f, -1.8F, RankineToolMaterials.TITANIUM,new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item THERMOMETER = add("thermometer", new ThermometerItem(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item PROSPECTING_STICK = add("prospecting_stick", new OreDetector(Config.PROSPECTING_STICK_RANGE.get(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item ORE_DETECTOR = add("ore_detector", new OreDetector(Config.ORE_DETECTOR_RANGE.get(), new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item GLASS_CUTTER = add("glass_cutter", new GlassCutterItem(new Item.Properties().maxDamage(256).group(ProjectRankine.setup.rankineTools)));
    public static final Item HARDNESS_TESTER = add("hardness_tester", new ItemHardnessTester(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item ORE_CYCLER = add("ore_cycler", new ItemOreCycler(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item ELEMENT_INDEXER = add("element_indexer", new ElementIndexerItem(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item BRIGADINE_HELMET = add("brigandine_helmet", new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.HEAD, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item BRIGADINE_CHESTPLATE = add("brigandine_chestplate", new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.CHEST, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item BRIGADINE_LEGGINGS = add("brigandine_leggings", new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.LEGS, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item BRIGADINE_BOOTS = add("brigandine_boots", new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.FEET, new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item HASTE_PENDANT = add("haste_pendant", new HastePendantItem(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item LUCK_PENDANT = add("luck_pendant", new LuckPendantItem(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item HEALTH_PENDANT = add("health_pendant", new HealthPendantItem(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item SPEED_PENDANT = add("speed_pendant", new SpeedPendantItem(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item LEVITATION_PENDANT = add("levitation_pendant", new LevitationPendantItem(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item REPULSION_PENDANT = add("repulsion_pendant", new RepulsionPendantItem(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));


    //BUCKETS
    public static final Item  WOOD_BUCKET = add("wood_bucket", new WoodBucket(() -> Fluids.EMPTY, (new Item.Properties().containerItem(ModItems.WOOD_BUCKET)).maxStackSize(16).group(ProjectRankine.setup.rankineTools)));
    public static final Item  WATER_WOOD_BUCKET = add("water_wood_bucket", new WoodBucket(() -> Fluids.WATER, (new Item.Properties().containerItem(ModItems.WOOD_BUCKET)).maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  MILK_WOOD_BUCKET = add("milk_wood_bucket", new MilkWoodBucket(new Item.Properties().group(ProjectRankine.setup.rankineTools).containerItem(ModItems.WOOD_BUCKET).maxStackSize(1)));
    public static final Item  BRASS_BUCKET = add("brass_bucket", new BrassBucket(() -> Fluids.EMPTY, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(16).group(ProjectRankine.setup.rankineTools)));
    public static final Item  WATER_BRASS_BUCKET = add("water_brass_bucket", new BrassBucket(() -> Fluids.WATER, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  LAVA_BRASS_BUCKET = add("lava_brass_bucket", new BrassBucket(() -> Fluids.LAVA, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  LIQUID_MERCURY_BRASS_BUCKET = add("liquid_mercury_brass_bucket", new BrassBucket(() -> ModFluids.LIQUID_MERCURY, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  MILK_BRASS_BUCKET = add("milk_brass_bucket", new MilkBrassBucket(new Item.Properties().group(ProjectRankine.setup.rankineTools).containerItem(ModItems.WOOD_BUCKET).maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  LIQUID_MERCURY_BUCKET = add("liquid_mercury_bucket", new BucketItem(() -> ModFluids.LIQUID_MERCURY, (new Item.Properties().containerItem(Items.BUCKET)).maxStackSize(1).group(ProjectRankine.setup.rankineTools)));


    //TEMPLATES
    public static final Item  SHOVEL_TEMPLATE = add("shovel_template", new ItemTemplate(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  HOE_TEMPLATE = add("hoe_template", new ItemTemplate(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  SPEAR_TEMPLATE = add("spear_template", new ItemTemplate(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  PICKAXE_TEMPLATE = add("pickaxe_template", new ItemTemplate(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  SWORD_TEMPLATE = add("sword_template", new ItemTemplate(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  AXE_TEMPLATE = add("axe_template", new ItemTemplate(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  HAMMER_TEMPLATE = add("hammer_template", new ItemTemplate(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  PENDANT_TEMPLATE = add("pendant_template", new ItemTemplate(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  ALLOY_TEMPLATE = add("alloy_template", new AlloyTemplate(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item  TRIPLE_ALLOY_TEMPLATE = add("triple_alloy_template", new TripleAlloyTemplate(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));

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
    public static final Item BEAVER = add("beaver_spawn_egg", new SpawnEggItem(ModEntityTypes.BEAVER,0x765F4C, 0x463F39,new Item.Properties().group(ProjectRankine.setup.rankineTools)));

    public static final Item FIRE_CLAY_BALL = add("fire_clay_ball", new SimpleTooltipItem("Derived from fire clay or from crafting. Used for refractory bricks.", new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item DRIED_BAMBOO = add("dried_bamboo", new FuelItem(new Item.Properties().group(ProjectRankine.setup.rankineTools), 50));
    public static final Item BEAVER_PELT = add("beaver_pelt", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item PUMICE_SOAP = add("pumice_soap", new ItemPumiceSoap(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item BIOMASS = add("biomass", new FuelItem(new Item.Properties().group(ProjectRankine.setup.rankineTools),25));
    public static final Item COMPRESSED_BIOMASS = add("compressed_biomass", new FuelItem(new Item.Properties().group(ProjectRankine.setup.rankineTools),200));
    public static final Item COMPOST = add("compost", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item PINEAPPLE_SLEEVES = add("pineapple_sleeves", new Item(new Item.Properties().food(ModFoods.PINEAPPLE_SLEEVES)));
    public static final Item COCONUT = add("coconut", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.COCONUT)));
    public static final Item CHEESE = add("cheese", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.CHEESE)));
    public static final Item PINA_COLADA = add("pina_colada", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.PINA_COLADA)));
    public static final Item CAST_IRON_ROD = add("cast_iron_rod", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item STEEL_ROD = add("steel_rod", new Item(new Item.Properties().group(ProjectRankine.setup.rankineTools)));
    public static final Item GRAPHITE_ELECTRODE = add("graphite_electrode", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item SADDLE_TREE = add("saddle_tree", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item COPPER_WIRE = add("copper_wire", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item BATTERY = add("battery0", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item THORIUM_ARROW = add("thorium_arrow", new ThoriumArrowItem(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item LITHIUM_POWER_CELL = add("lithium_power_cell", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item YAG_ROD = add("yag_rod", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item SPARK_LIGHTER = add("spark_lighter", new ItemSparkLighter(new Item.Properties().maxStackSize(1).maxDamage(600).group(ProjectRankine.setup.rankineTools)));
    public static final Item ELEMENT_TRANSMUTER = add("element_transmuter", new Item(new Item.Properties().maxStackSize(64).group(ProjectRankine.setup.rankineTools)));
    public static final Item PACKAGED_TOOL = add("packaged_tool", new PackagedToolItem(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item BIOME_INDICATOR_GENERIC = add("biome_indicator_generic", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item BIOME_INDICATOR_RIVER = add("biome_indicator_river", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));
    public static final Item BIOME_INDICATOR_OCEAN = add("biome_indicator_ocean", new Item(new Item.Properties().maxStackSize(1).group(ProjectRankine.setup.rankineTools)));



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

    @ObjectHolder("rankine:element_indexer")
    public static ContainerType<ElementIndexerContainer> ELEMENT_INDEXER_CONTAINER;

    public static Item getBlockAsItem(Block block)
    {
        ItemStack stack = new ItemStack(block);
        return stack.getItem();
    }
}

